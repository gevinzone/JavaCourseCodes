package io.kimmking.javacourse.kafkademo.service.impl;

import com.alibaba.fastjson.JSON;
import io.kimmking.javacourse.kafkademo.entity.Order;
import io.kimmking.javacourse.kafkademo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private final String topic = "order-topic";
    @Override
    public void sendOrder(Order order) {
        kafkaTemplate.send(topic, order.getId().toString(), JSON.toJSONString(order));
    }

    @Override
    public void sendOrderDefault(Order order) {
        kafkaTemplate.sendDefault(order.getId().toString(), JSON.toJSONString(order));
    }

//    @KafkaListener(topics = topic)
//    private void handle(String orderJson) {
//        log.info("receives: {}", orderJson);
//        Order order = JSON.parseObject(orderJson, Order.class);
//        log.info("Order: {}", order);
//    }

//    @KafkaListener(topics = topic)
//    private void handle2(String orderJson, ConsumerRecord<String, String> record) {
//        log.info("receives2: {}, partition: {}, time stamp: {}", orderJson, record.partition(), record.timestamp());
//        Order order = JSON.parseObject(record.value(), Order.class);
//        log.info("Order: {}", order);
//    }
    @KafkaListener(topics = topic)
    private void handler3(String orderJson, Message<String> message) {
        MessageHeaders headers = message.getHeaders();
        log.info("receives3: {}, partition: {}, time stamp: {}", message.getPayload(),
                headers.get(KafkaHeaders.RECEIVED_PARTITION_ID), headers.get(KafkaHeaders.RECEIVED_TIMESTAMP));
        Order order = JSON.parseObject(message.getPayload(), Order.class);
        log.info("Order: {}", order);
    }
}
