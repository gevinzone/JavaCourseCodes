package com.gevinzone.foreignexchange.configuration;

import org.dromara.hmily.spring.HmilyApplicationContextAware;
import org.dromara.hmily.spring.annotation.RefererAnnotationBeanPostProcessor;
import org.dromara.hmily.spring.aop.SpringHmilyTransactionAspect;
import org.dromara.hmily.core.service.HmilyTransactionHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.*;

@Configuration
//@ComponentScan({"org.dromara.hmily"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class HmilyConfiguration {
//    @Bean
//    @Primary
//    public BeanPostProcessor refererAnnotationBeanPostProcessor() {
//        return new RefererAnnotationBeanPostProcessor();
//    }
//
//    @Bean
//    public SpringHmilyTransactionAspect getSpringHmilyTransactionAspect() {
//        return new SpringHmilyTransactionAspect();
//    }
//
//    @Bean
//    public HmilyApplicationContextAware getHmilyApplicationContextAware() {
//        return new HmilyApplicationContextAware();
//    }

}
