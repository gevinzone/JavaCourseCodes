package io.kimmking.kmq.core;

import io.kimmking.kmq.service.DefaultConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ConsumerServiceTest {
    @Autowired
    private DefaultConsumerService defaultConsumerService;

    @Test
    public void testPollMessage() {
        KmqMessage message = defaultConsumerService.poll(1000);
//        Assertions.assertNotNull(message.getBody());
        if (message != null) {
            log.info("message: {}", message);
            log.info("object: {}", message.getBody());
        }
    }
}
