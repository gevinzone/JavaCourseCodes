package com.gevinzone.homework1101.redisson;

import org.redisson.api.RedissonClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        RedissonClient client = context.getBean(RedissonClient.class);
        RedissonConcurrentService service = new RedissonConcurrentService(client);
        int threads = 20;
        int result = service.concurrentAdd(threads);
        System.out.println(result);
        System.out.println(result == threads);
    }
}
