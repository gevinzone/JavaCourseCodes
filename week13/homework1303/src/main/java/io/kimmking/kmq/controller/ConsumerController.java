package io.kimmking.kmq.controller;

import io.kimmking.kmq.core.KmqMessage;
import io.kimmking.kmq.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kmq/consumer")
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    @GetMapping
    public Object poll(String topic) {
        KmqMessage message = consumerService.poll(topic, 1000);
        return message == null ? null : message.getBody();
    }
}
