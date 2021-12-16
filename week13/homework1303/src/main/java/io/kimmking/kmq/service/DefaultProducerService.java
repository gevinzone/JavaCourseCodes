package io.kimmking.kmq.service;

import io.kimmking.kmq.core.KmqMessage;

public interface DefaultProducerService {
    boolean send(String topic, KmqMessage message);
    boolean sendWithoutHeader(String topic, Object message);
    boolean sendDefaultWithoutHeader(Object message);
    boolean sendDefault(KmqMessage message);
}
