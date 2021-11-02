package com.gevinzone.homework0501.configuration;

import com.gevinzone.homework0501.bo.Student;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        System.out.println(" ==> postProcessBeanDefinitionRegistry: "+beanDefinitionRegistry.getBeanDefinitionCount());
//        System.out.println(" ==> postProcessBeanDefinitionRegistry: "+String.join(",",beanDefinitionRegistry.getBeanDefinitionNames()));
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Student.class);
        rootBeanDefinition.getPropertyValues().add("id", 101);
        rootBeanDefinition.getPropertyValues().add("name", "Student101");
        beanDefinitionRegistry.registerBeanDefinition("student101", rootBeanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println(" ==> postProcessBeanFactory: "+configurableListableBeanFactory.getBeanDefinitionCount());
//        System.out.println(" ==> postProcessBeanFactory: "+String.join(",",beanFactory.getBeanDefinitionNames()));
        configurableListableBeanFactory.registerSingleton("student102", new Student(102, "Student102"));
    }
}
