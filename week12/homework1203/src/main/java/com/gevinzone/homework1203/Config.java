package com.gevinzone.homework1203;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;

@Configuration
@EnableConfigurationProperties({ActiveMQProperties.class})
public class Config {
    @Autowired
    private ActiveMQProperties activeMQProperties;
    @Autowired
    ActiveMQConnectionFactory activeMQConnectionFactory;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(activeMQProperties.getBrokerUrl());
//        factory.setTrustedPackages(Arrays.asList("com.gevinzone.homework1203.entity"));
        factory.setTrustAllPackages(true);
        return factory;
    }

    @Bean
    Destination orderQueueDestination() {
        return new ActiveMQQueue("gevin.orderQueue");
    }

    @Bean
    JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory);
        jmsTemplate.setReceiveTimeout(100);
        return jmsTemplate;
    }


}
