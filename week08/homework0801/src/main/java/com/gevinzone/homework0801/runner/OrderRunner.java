package com.gevinzone.homework0801.runner;

import com.gevinzone.homework0801.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class OrderRunner implements ApplicationRunner {
    @Autowired
    private OrderService orderService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        run();
    }

    private void run() {
//        orderService.insertOrder();
//        orderService.insertOrderWithException();
        int total = 100_000;
        orderService.insertOrdersBatch(total);
    }
}
