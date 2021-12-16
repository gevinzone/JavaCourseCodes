package io.kimmking.kmq.demo;

import io.kimmking.kmq.core.KmqBroker;
import io.kimmking.kmq.core.KmqConsumer;

import java.util.HashMap;
import java.util.Map;

public class ConsumerContainer {
    private final Map<String, KmqConsumer> consumerMap = new HashMap<>(8);
    private KmqBroker kmqBroker;

    public ConsumerContainer(KmqBroker broker) {
        kmqBroker = broker;
    }

    public KmqConsumer getOrCreateConsumer(String topic) {
        if (!consumerMap.containsKey(topic)) {
            synchronized (this) {
                if (!consumerMap.containsKey(topic)) {
                    KmqConsumer consumer = kmqBroker.createConsumer();
                    consumer.subscribe(topic);
                    consumerMap.put(topic, consumer);
                }
            }
        }
        return consumerMap.get(topic);
    }
}
