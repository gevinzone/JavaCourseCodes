package io.kimmking.kmq.core;

import io.kimmking.kmq.demo.Order;
import io.kimmking.kmq.service.DefaultProducerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProducerServiceTest {
    @Autowired
    private DefaultProducerService defaultProducerService;

    @Test
    public void testDefaultSendWithoutHeader() {
        Order order = new Order(1000L, System.currentTimeMillis(), "USD2CNY", 6.51d);
        boolean result = defaultProducerService.sendDefaultWithoutHeader(order);
        Assertions.assertTrue(result);
    }
}
