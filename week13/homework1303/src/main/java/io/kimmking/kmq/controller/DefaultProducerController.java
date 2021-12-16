package io.kimmking.kmq.controller;

import io.kimmking.kmq.demo.Order;
import io.kimmking.kmq.service.DefaultProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kmq/producer/default")
public class DefaultProducerController {
    @Autowired
    private DefaultProducerService defaultProducerService;

    @PostMapping
    public boolean sendMessageDefault(@RequestBody Order order) {
        return defaultProducerService.sendDefaultWithoutHeader(order);
    }

    @PostMapping("/topic")
    public boolean sendMessageWithoutHeader(String topic, @RequestBody Order order) {
        return defaultProducerService.sendWithoutHeader(topic, order);
    }
}
