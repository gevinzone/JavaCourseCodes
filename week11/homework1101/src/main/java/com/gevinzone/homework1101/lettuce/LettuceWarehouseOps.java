package com.gevinzone.homework1101.lettuce;

import com.gevinzone.homework1101.business.IRedisWarehouseOpt;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisHashCommands;

import java.util.HashMap;

public class LettuceWarehouseOps implements IRedisWarehouseOpt {
    private final RedisClient client;

    public LettuceWarehouseOps(RedisClient client) {
        this.client = client;
    }

    @Override
    public Long increaseHashValue(String key, String field, long delta) {
        try(StatefulRedisConnection<String, String> connection = client.connect()) {
            RedisHashCommands<String, String> hashCommands = connection.sync();
            return hashCommands.hincrby(key, field, delta);
        }
    }

    @Override
    public Long decreaseHashValue(String key, String field, long delta) {
        return increaseHashValue(key, field, -delta);
    }

    @Override
    public void insertHashMap(String key, HashMap<String, String> data) {
        try (StatefulRedisConnection<String, String> connection = client.connect()) {
            RedisHashCommands<String, String> hashCommands = connection.sync();
            hashCommands.hmset(key, data);
        }
    }

    @Override
    public String getHashMapValue(String key, String field) {
        try (StatefulRedisConnection<String, String> connection = client.connect()) {
            RedisHashCommands<String, String> hashCommands = connection.sync();
            return hashCommands.hget(key, field);
        }
    }
}
