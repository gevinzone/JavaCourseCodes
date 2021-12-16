package io.kimmking.kmq.service;

import io.kimmking.kmq.core.KmqMessage;

public interface ConsumerService {
    KmqMessage poll(String topic);
    KmqMessage poll(String topic, long timeout);
}
