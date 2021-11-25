package com.gevinzone.homework1101.lettuce;

import com.gevinzone.homework1101.ConcurrentService;
import com.gevinzone.homework1101.WarehouseService;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisHashCommands;
import io.lettuce.core.api.sync.RedisStringCommands;


public class LettuceDemo {
    public static void main(String[] args) {
        RedisClient client = RedisClient.create("redis://localhost:6379");

//        lettuceOpt(client);
//        useLock(client);
        changeStock(client);
    }

    private static void lettuceOpt(RedisClient client) {
        StatefulRedisConnection<String, String> connection = client.connect();
        RedisStringCommands<String, String> stringCommands = connection.sync();

        stringCommands.set("hello", "world");
        System.out.println(stringCommands.get("hello"));

        RedisHashCommands<String, String> hashCommands = connection.sync();
        hashCommands.hset("lettuce1", "key1", "1");
        int res = Integer.parseInt(hashCommands.hget("lettuce1", "key1"));
        System.out.println(res);
    }

    private static void useLock(RedisClient client) {
        ConcurrentService service = new ConcurrentService(new LettuceLock(client));
        int count = 20;
        int result = service.multiThreadAdd(count);
        System.out.println(result);
        System.out.print(result == count);
    }

    private static void changeStock(RedisClient client) {
        int threads = 10, initStock = 100, delta = 5;
        int expected = initStock - delta * threads;

        LettuceWarehouseOps dataOps = new LettuceWarehouseOps(client);
        WarehouseService service = new WarehouseService(dataOps, initStock);
        int stockLeft = service.multiThreadDecreaseStock(threads, delta);
        System.out.println("********************************");
        System.out.printf("stock left: %s, equals %s? %s \n", stockLeft, expected, stockLeft==50);
    }
}
