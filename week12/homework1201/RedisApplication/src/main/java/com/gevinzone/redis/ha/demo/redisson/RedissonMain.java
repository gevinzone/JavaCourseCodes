package com.gevinzone.redis.ha.demo.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;


public class RedissonMain {
    public static void main(String[] args) {
        RedissonClient client = getSentinelClient();
        client.getAtomicLong("redissonLong").set(100);
        long value = client.getAtomicLong("redissonLong").get();
        System.out.println(value);
    }

    private static RedissonClient getSentinelClient() {
        Config config = new Config();
        config.useSentinelServers()
                .setMasterName("mymaster")
                .addSentinelAddress("redis://127.0.0.1:26379", "redis://127.0.0.1:26380");
        return Redisson.create(config);
    }
}
