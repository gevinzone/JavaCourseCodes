package com.gevinzone.config;

import com.gevinzone.bo.Klass;
import com.gevinzone.bo.Student;
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
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Student.class);
        rootBeanDefinition.getPropertyValues().add("id", 301);
        rootBeanDefinition.getPropertyValues().add("name", "Student301");
        beanDefinitionRegistry.registerBeanDefinition("student301", rootBeanDefinition);
        beanDefinitionRegistry.getBeanDefinition("student301");
        System.out.println(1);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        configurableListableBeanFactory.registerSingleton("student302", new Student(302, "student302"));
        configurableListableBeanFactory.registerSingleton("student303", new Student(303, "student303"));
        System.out.println(2);
        Klass class4 = new Klass("class4", null);
        class4.setSquadLeader(configurableListableBeanFactory.getBean("student302", Student.class));
        class4.addStudent(configurableListableBeanFactory.getBean("student302", Student.class));
        class4.addStudent(configurableListableBeanFactory.getBean("student303", Student.class));
        configurableListableBeanFactory.registerSingleton("class4", class4);
    }
}
