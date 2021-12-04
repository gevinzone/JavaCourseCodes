package com.gevinzone.redis.ha.demo.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.sentinel.api.StatefulRedisSentinelConnection;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class LettuceMain {
    public static void main(String[] args) {
        sentinel();
        System.out.println("*************");
        getRedis();
    }

    private static void sentinel() {
        RedisURI redisUri = RedisURI.create("redis://localhost:26379");
        RedisClient client =  RedisClient.create(redisUri);

        StatefulRedisSentinelConnection<String, String>  connection = client.connectSentinel();

        Map<String, String> map = connection.sync().master("mymaster");
        System.out.println("************");
        System.out.println(map);
    }

    private static void getRedis() {
        RedisURI redisUri = RedisURI.Builder.sentinel("localhost", 26379, "mymaster").withSentinel("localhost", 26380).build();
        RedisClient client = RedisClient.create(redisUri);

        StatefulRedisConnection<String, String> connection = client.connect();
        String value = connection.sync().get("uptime2");
        System.out.println("***********");
        System.out.println(value);
    }
}