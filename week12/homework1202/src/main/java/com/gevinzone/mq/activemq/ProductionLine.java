package com.gevinzone.mq.activemq;

import lombok.extern.slf4j.Slf4j;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ProductionLine {
    public static void consume(Session session, Destination destination) throws JMSException {
        MessageConsumer consumer = session.createConsumer(destination);
        final AtomicInteger count = new AtomicInteger(0);
        MessageListener listener = message -> {
            try {
                log.info("{} ===> receive from {}: {}", count.incrementAndGet(), destination.toString(), message);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw e;
            }
        };
        consumer.setMessageListener(listener);
    }

    public static void produce(Session session, Destination destination) throws JMSException {
        MessageProducer producer = session.createProducer(destination);
        int loop = 100;
        for (int i = 0; i < loop; i++) {
            TextMessage message = session.createTextMessage(i + " message");
            producer.send(message);
        }
    }
}
