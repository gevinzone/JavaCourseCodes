package com.gevinzone.homework1101;

import com.gevinzone.homework1101.lettuce.LettuceLock;
import com.gevinzone.homework1101.redistemplate.TemplateLock;
import io.lettuce.core.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;

@SpringBootApplication
public class Homework1101Application {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Homework1101Application.class, args);
        StringRedisTemplate template = context.getBean(StringRedisTemplate.class);

        TemplateLock lock = context.getBean(TemplateLock.class);

        templateOps(template);
        useLock(lock);

    }

    private static void templateOps(StringRedisTemplate template) {
        template.opsForValue().set("redisTemplate", "string from redis template");
        System.out.println((template.opsForValue().get("redisTemplate")));

        template.opsForHash().put("hash", "first", "first from redis template");
        System.out.println(template.opsForHash().get("hash", "first"));
    }

    private static void useLock(TemplateLock lock) {
        ConcurrentService service = new ConcurrentService(lock);
        int count = 20;
        int result = service.multiThreadAdd(count);
        System.out.println(result);
        System.out.print(result == count);
    }

}
