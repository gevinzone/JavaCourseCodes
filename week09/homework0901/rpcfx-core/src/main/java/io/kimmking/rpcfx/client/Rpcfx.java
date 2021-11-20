package io.kimmking.rpcfx.client;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.api.*;
import io.kimmking.rpcfx.client.nettyclient.NettyHttpClient;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public final class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }

    static Map<String, Map<String, Object>> proxyMap = new HashMap<>();

    public static <T, filters> T createFromRegistry(final Class<T> serviceClass, final String zkUrl, Router router, LoadBalancer loadBalance, Filter filter) throws Exception {

        // 加filte之一

        // curator Provider list from zk
//        List<String> invokers = new ArrayList<>();
        // 1. 简单：从zk拿到服务提供的列表
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(zkUrl).namespace("rpcfx").retryPolicy(retryPolicy).build();
        client.start();

        String path = "/" + serviceClass.getName();
        List<String> invokers = new ArrayList<>(client.getChildren().forPath(path));

        // 2. 挑战：监听zk的临时节点，根据事件更新这个list（注意，需要做个全局map保持每个服务的提供者List）

        List<String> urls = router.route(invokers);

        String url = loadBalance.select(urls); // router, loadbalance

        return (T) create(serviceClass, url, filter);

    }

    public static <T, filters> T createFromRegistryWatched(final Class<T> serviceClass, final String zkUrl, Router router, LoadBalancer loadBalance, Filter filter) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(zkUrl).namespace("rpcfx").retryPolicy(retryPolicy).build();
        client.start();

        String path = "/" + serviceClass.getName();

        // 2. 挑战：监听zk的临时节点，根据事件更新这个list（注意，需要做个全局map保持每个服务的提供者List）
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true);
        pathChildrenCache.getListenable().addListener((zkClient, event) -> {
            if (!event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)
                    && !event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                return;
            }
            log.info("!!!!!" + event.getType().toString() + "!!!!!");
            List<String> invokers = new ArrayList<>(zkClient.getChildren().forPath(path));
            List<String> urls = router.route(invokers);
            String url = loadBalance.select(urls);
            if (proxyMap.containsKey(zkUrl) && proxyMap.get(zkUrl).containsKey(serviceClass.getName())) {
                log.info("**********update client*************");
                proxyMap.get(zkUrl).put(serviceClass.getName(), create(serviceClass, url, filter));
            }
        });
        pathChildrenCache.start();


        List<String> invokers = new ArrayList<>(client.getChildren().forPath(path));
        List<String> urls = router.route(invokers);
        String url = loadBalance.select(urls);

        return (T) create(serviceClass, url, filter);

    }

    public static <T> T getOrRegister(final Class<T> serviceClass, final String zkUrl, Router router, LoadBalancer loadBalance, Filter filter) throws Exception {
        if (proxyMap.containsKey(zkUrl) && proxyMap.get(zkUrl).containsKey(serviceClass.getName())) {
            return (T) proxyMap.get(zkUrl).get(serviceClass.getName());
        }
        proxyMap.putIfAbsent(zkUrl, new HashMap<>());
        proxyMap.get(zkUrl).put(serviceClass.getName(), createFromRegistryWatched(serviceClass, zkUrl, router, loadBalance, filter));
        return (T) proxyMap.get(zkUrl).get(serviceClass.getName());
    }

    public static <T> T create(final Class<T> serviceClass, final String url, Filter... filters) {

        // 0. 替换动态代理 -> 字节码生成
//        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url, filters));
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(serviceClass);
        enhancer.setCallback((MethodInterceptor) (obj, method, params, proxy) -> {
            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(serviceClass.getName());
            request.setMethod(method.getName());
            request.setParams(params);

            if (null!=filters) {
                for (Filter filter : filters) {
                    if (!filter.filter(request)) {
                        return null;
                    }
                }
            }

            RpcfxResponse response = post(request, url);
            return JSON.parse(response.getResult().toString());
        });
        return  (T) enhancer.create();
    }

    private static RpcfxResponse post(RpcfxRequest req, String url) throws Exception {
        MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");
        String reqJson = JSON.toJSONString(req);
        System.out.println("req json: "+reqJson);

        // 1.可以复用client
        // 2.尝试使用httpclient或者netty client
        URI uri = new URI(url);
//        NettyHttpClient client = new NettyHttpClient();
        NettyHttpClient client = NettyHttpClient.getDefaultClient();
        String respJson = client.post(uri, reqJson);
        System.out.println("resp json: "+respJson);
        return JSON.parseObject(respJson, RpcfxResponse.class);
    }

//    public static class RpcfxInvocationHandler implements InvocationHandler {
//
//        public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");
//
//        private final Class<?> serviceClass;
//        private final String url;
//        private final Filter[] filters;
//
//        public <T> RpcfxInvocationHandler(Class<T> serviceClass, String url, Filter... filters) {
//            this.serviceClass = serviceClass;
//            this.url = url;
//            this.filters = filters;
//        }
//
//        // 可以尝试，自己去写对象序列化，二进制还是文本的，，，rpcfx是xml自定义序列化、反序列化，json: code.google.com/p/rpcfx
//        // int byte char float double long bool
//        // [], data class
//
//        @Override
//        public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
//
//            // 加filter地方之二
//            // mock == true, new Student("hubao");
//
//            RpcfxRequest request = new RpcfxRequest();
//            request.setServiceClass(this.serviceClass.getName());
//            request.setMethod(method.getName());
//            request.setParams(params);
//
//            if (null!=filters) {
//                for (Filter filter : filters) {
//                    if (!filter.filter(request)) {
//                        return null;
//                    }
//                }
//            }
//
//            RpcfxResponse response = post(request, url);
//
//            // 加filter地方之三
//            // Student.setTeacher("cuijing");
//
//            // 这里判断response.status，处理异常
//            // 考虑封装一个全局的RpcfxException
//
//            return JSON.parse(response.getResult().toString());
//        }
//
//        private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
//            String reqJson = JSON.toJSONString(req);
//            System.out.println("req json: "+reqJson);
//
//            // 1.可以复用client
//            // 2.尝试使用httpclient或者netty client
//            OkHttpClient client = new OkHttpClient();
//            final Request request = new Request.Builder()
//                    .url(url)
//                    .post(RequestBody.create(JSONTYPE, reqJson))
//                    .build();
//            String respJson = client.newCall(request).execute().body().string();
//            System.out.println("resp json: "+respJson);
//            return JSON.parseObject(respJson, RpcfxResponse.class);
//        }
//
//
//
//    }

}
