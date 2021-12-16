package io.kimmking.kmq.service.impl;

import io.kimmking.kmq.core.KmqConsumer;
import io.kimmking.kmq.core.KmqMessage;
import io.kimmking.kmq.service.DefaultConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultConsumerServiceImpl implements DefaultConsumerService {

    @Autowired
    private KmqConsumer kmqConsumer;

    @Override
    public KmqMessage poll() {
        return kmqConsumer.poll();
    }

    @Override
    public KmqMessage poll(long timeout) {
        return kmqConsumer.poll(timeout);
    }
}
