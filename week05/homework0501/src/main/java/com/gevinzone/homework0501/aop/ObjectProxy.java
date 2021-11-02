package com.gevinzone.homework0501.aop;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ObjectProxy {
    public Object createProxy(Object proxiedObject) {
        Class<?>[] interfaces = proxiedObject.getClass().getInterfaces();
        DynamicProxyHandler handler = new DynamicProxyHandler(proxiedObject);
        return Proxy.newProxyInstance(proxiedObject.getClass().getClassLoader(), interfaces, handler);
    }

    @AllArgsConstructor
    @Slf4j
    private static class DynamicProxyHandler implements InvocationHandler {
        private Object proxiedObject;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log.info("在方法调用前，基于动态代理做AOP");
            Object result = method.invoke(proxiedObject, args);
            log.info("在方法调用后，基于动态代理做AOP");
            return result;
        }
    }
}
