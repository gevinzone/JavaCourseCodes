package io.kimmking.kmq.service.impl;

import io.kimmking.kmq.core.KmqBroker;
import io.kimmking.kmq.core.KmqMessage;
import io.kimmking.kmq.core.KmqProducer;
import io.kimmking.kmq.service.DefaultProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DefaultProducerServiceImpl implements DefaultProducerService {
    @Value("${Kmq.default.topic}")
    private String defaultTopic = "defaultTopic";

    @Autowired
    private KmqProducer kmqProducer;
    @Autowired
    private KmqBroker kmqBroker;

    @Override
    public boolean send(String topic, KmqMessage message) {
        kmqBroker.createTopic(topic);
        return kmqProducer.send(topic, message);
    }

    @Override
    public boolean sendWithoutHeader(String topic, Object message) {
        kmqBroker.createTopic(topic);
        return kmqProducer.send(topic, new KmqMessage<>(null, message));
    }

    @Override
    public boolean sendDefaultWithoutHeader(Object message) {
        return kmqProducer.send(defaultTopic, new KmqMessage<>(null, message));
    }

    @Override
    public boolean sendDefault(KmqMessage message) {
        return kmqProducer.send(defaultTopic, message);
    }
}
