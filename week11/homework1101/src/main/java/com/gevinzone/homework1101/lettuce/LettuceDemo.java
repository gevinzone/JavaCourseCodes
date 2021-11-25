package com.gevinzone.homework1101.lettuce;

import com.gevinzone.homework1101.ConcurrentService;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisHashCommands;
import io.lettuce.core.api.sync.RedisStringCommands;


public class LettuceDemo {
    public static void main(String[] args) {
        RedisClient client = RedisClient.create("redis://localhost:6379");

        lettuceOpt(client);
        useLock(client);
    }

    private static void lettuceOpt(RedisClient client) {
        StatefulRedisConnection<String, String> connection = client.connect();
        RedisStringCommands<String, String> stringCommands = connection.sync();

//        RedisHashCommands<String, String> hashCommands = connection.sync();

        stringCommands.set("hello", "world");
        System.out.println(stringCommands.get("hello"));
    }

    private static void useLock(RedisClient client) {
        ConcurrentService service = new ConcurrentService(new LettuceLock(client));
        int count = 20;
        int result = service.multiThreadAdd(count);
        System.out.println(result);
        System.out.print(result == count);
    }
}
