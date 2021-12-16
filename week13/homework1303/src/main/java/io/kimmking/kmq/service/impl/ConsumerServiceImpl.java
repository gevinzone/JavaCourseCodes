package io.kimmking.kmq.service.impl;

import io.kimmking.kmq.core.KmqConsumer;
import io.kimmking.kmq.core.KmqMessage;
import io.kimmking.kmq.demo.ConsumerContainer;
import io.kimmking.kmq.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService {
//    @Autowired
//    private KmqBroker kmqBroker;
    @Autowired
    private ConsumerContainer consumerContainer;

    @Override
    public KmqMessage poll(String topic) {
//        KmqConsumer consumer = kmqBroker.createConsumer();
        KmqConsumer consumer = consumerContainer.getOrCreateConsumer(topic);
        consumer.subscribe(topic);
        return consumer.poll();
    }

    @Override
    public KmqMessage poll(String topic, long timeout) {
//        KmqConsumer consumer = kmqBroker.createConsumer();
        KmqConsumer consumer = consumerContainer.getOrCreateConsumer(topic);
        consumer.subscribe(topic);
        return consumer.poll(timeout);
    }
}
