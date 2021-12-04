package com.gevinzone.redis.ha.demo.redistemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class RedisApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RedisApplication.class, args);
        RedisTemplate redisTemplate = context.getBean("redisTemplate", RedisTemplate.class);
        redisTemplate.opsForValue().set("redisTemplateUpdateTime", Long.toString(System.currentTimeMillis()));
        System.out.println(redisTemplate.opsForValue().get("redisTemplateUpdateTime"));
        System.out.println(redisTemplate.opsForValue().get("uptime2"));
    }
}

