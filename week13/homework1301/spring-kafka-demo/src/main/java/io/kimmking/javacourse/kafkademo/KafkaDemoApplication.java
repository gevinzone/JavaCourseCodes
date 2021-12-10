package io.kimmking.javacourse.kafkademo;

import io.kimmking.javacourse.kafkademo.entity.Order;
import io.kimmking.javacourse.kafkademo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class KafkaDemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(KafkaDemoApplication.class, args);
        OrderService orderService = context.getBean(OrderService.class);
        for (int i = 0; i < 5; i++) {
            log.info("Send order {}:", i);
            orderService.sendOrder(new Order(1000L + i,System.currentTimeMillis(),"USD2CNY", 6.5d));
        }
    }

}
