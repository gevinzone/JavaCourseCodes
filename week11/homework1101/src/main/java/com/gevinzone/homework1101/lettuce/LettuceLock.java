package com.gevinzone.homework1101.lettuce;

import com.gevinzone.homework1101.redislock.IRedisLock;
import com.gevinzone.homework1101.redislock.LuaScript;
import io.lettuce.core.RedisClient;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisScriptingCommands;
import io.lettuce.core.api.sync.RedisStringCommands;


public class LettuceLock implements IRedisLock {
    private final RedisClient client;

    public LettuceLock(RedisClient client) {
        this.client = client;
    }

    @Override
    public boolean tryLock(String key, String value, long millisecondsToExpire) {
        try (StatefulRedisConnection<String, String> connection = client.connect()) {
            RedisStringCommands<String, String> stringCommands = connection.sync();
            String result = stringCommands.set(key, value, SetArgs.Builder.nx().px(millisecondsToExpire));
            String lockSuccess = "OK";
            return lockSuccess.equals(result);
        }
    }

    @Override
    public boolean releaseLock(String key, String value) {
        String script = LuaScript.releaseLock;
        try (StatefulRedisConnection<String, String> connection = client.connect()) {
            RedisScriptingCommands<String, String> scriptingCommands = connection.sync();
            Object result = scriptingCommands.eval(script, ScriptOutputType.INTEGER, new String[]{key}, value);
            Integer releaseSuccess = 1;
            return releaseSuccess.equals(result);
        }
    }
}
