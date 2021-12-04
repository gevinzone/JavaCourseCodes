package com.gevinzone.homework1203;

import com.gevinzone.homework1203.entity.Order;
import com.gevinzone.homework1203.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class AppRunner implements ApplicationRunner {
    @Autowired
    private OrderService orderService1;
    @Autowired
    private OrderService orderService2;

    private final int core = Runtime.getRuntime().availableProcessors();
    ExecutorService executorService = Executors.newFixedThreadPool(core);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("********************");
        int producers = 3, consumers = 2;
        while (true) {
            produceOrder(producers);
//            sleep(1000);
            consumeOrder(consumers);
            sleep(1100);
        }
    }

    private Order createOrderEntity() {
        Date now = new Date();
        return Order.builder().status(0).createTime(now).updateTime(now).build();
    }

    private void produceOrder(int producers) {
        log.info("Start producing");
        for (int i = 0; i < producers; i++) {
            executorService.submit(() -> {
                orderService1.createOrder(createOrderEntity());
            });
        }
    }

    private void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    private void consumeOrder(int consumers) {
        log.info("Start consuming");
        for (int i = 0; i < consumers; i++) {
//            Order order = orderService1.processOrder();
            executorService.submit(() -> {
                Order order = orderService1.processOrder();
                log.info(order.toString());
            });
        }
    }
}
