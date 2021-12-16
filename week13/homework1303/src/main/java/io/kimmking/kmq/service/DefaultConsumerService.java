package io.kimmking.kmq.service;

import io.kimmking.kmq.core.KmqMessage;

public interface DefaultConsumerService {
    KmqMessage poll();
    KmqMessage poll(long timeout);
}
