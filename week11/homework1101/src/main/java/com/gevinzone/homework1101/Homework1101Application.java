package com.gevinzone.homework1101;

import com.gevinzone.homework1101.lettuce.LettuceLock;
import com.gevinzone.homework1101.lettuce.LettuceWarehouseOps;
import com.gevinzone.homework1101.redistemplate.TemplateLock;
import com.gevinzone.homework1101.redistemplate.TemplateWarehouseOps;
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
        TemplateWarehouseOps warehouseOps = context.getBean(TemplateWarehouseOps.class);

        templateOps(template);
        useLock(lock);
        changeStock(warehouseOps);

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
        System.out.println(result == count);
    }

    private static void changeStock(TemplateWarehouseOps warehouseOps) {
        int threads = 10, initStock = 100, delta = 5;
        int expected = initStock - delta * threads;

        WarehouseService service = new WarehouseService(warehouseOps, initStock);
        int stockLeft = service.multiThreadDecreaseStock(threads, delta);
        System.out.println("\n********************************");
        System.out.printf("stock left: %s, equals %s? %s \n", stockLeft, expected, stockLeft==50);
    }

}
