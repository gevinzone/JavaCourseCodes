package io.kimmking.rpcfx.demo.provider;

import io.kimmking.rpcfx.api.ServiceProviderDesc;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class RegisterBeanPostProcessor implements BeanPostProcessor {
    @Autowired
    private CuratorFramework zkClient;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try {
            if ("io.kimmking.rpcfx.demo.api.UserService".equals(beanName)) {
                registerService(zkClient, beanName);
            }
            if ("io.kimmking.rpcfx.demo.api.OrderService".equals(beanName)) {
                registerService(zkClient, beanName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bean;
    }

    private void registerService(CuratorFramework client, String service) throws Exception {
        ServiceProviderDesc userServiceDesc = ServiceProviderDesc.builder()
                .host(InetAddress.getLocalHost().getHostAddress())
                .port(8080).serviceClass(service).build();
        // String userServiceSescJson = JSON.toJSONString(userServiceSesc);

        try {
            if ( null == client.checkExists().forPath("/" + service)) {
                client.create().withMode(CreateMode.PERSISTENT).forPath("/" + service, "service".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        client.create().withMode(CreateMode.EPHEMERAL).
                forPath( "/" + service + "/" + userServiceDesc.getHost() + "_" + userServiceDesc.getPort(), "provider".getBytes());
    }
}
