package io.kimmking.kmq.controller;

import io.kimmking.kmq.core.KmqMessage;
import io.kimmking.kmq.service.DefaultConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kmq/consumer/default")
public class DefaultConsumerController {
    @Autowired
    private DefaultConsumerService defaultConsumerService;

    @GetMapping
    public Object defaultPoll() {
        KmqMessage message = defaultConsumerService.poll(1000);
        return message == null ? null : message.getBody();
    }
}
