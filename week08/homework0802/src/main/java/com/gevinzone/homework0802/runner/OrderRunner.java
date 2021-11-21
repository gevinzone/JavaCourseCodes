package com.gevinzone.homework0802.runner;

import com.gevinzone.homework0802.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderRunner implements ApplicationRunner {
    @Autowired
    private OrderService orderService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("run order runner");
        run();
    }

    private void run() {
//        orderService.insertOrder();
//        orderService.insertOrderWithException();
//        int total = 1000;
//        orderService.insertOrdersBatch(total);
    }
}
