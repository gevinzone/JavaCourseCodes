package io.kimmking.kmq;

import io.kimmking.kmq.core.KmqBroker;
import io.kimmking.kmq.core.KmqConsumer;
import io.kimmking.kmq.core.KmqProducer;
import io.kimmking.kmq.demo.ConsumerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
//    @Value("${Kmq.default.topic}")
//    private String defaultTopic = "defaultTopic";
//
//    @Autowired
//    private KmqBroker kmqBroker;
//
//    @Bean
//    public KmqBroker kmqBroker() {
//        KmqBroker broker = new KmqBroker();
//        broker.createTopic(defaultTopic);
//        return broker;
//    }
//
//    @Bean
//    public KmqProducer defaultProducer() {
//        return kmqBroker.createProducer();
//    }
//
//    @Bean
//    public KmqConsumer defaultConsumer() {
//        KmqConsumer consumer = kmqBroker.createConsumer();
//        consumer.subscribe(defaultTopic);
//        return consumer;
//    }
//
//    @Bean
//    public ConsumerContainer consumerContainer() {
//        return new ConsumerContainer(kmqBroker);
//    }
}
