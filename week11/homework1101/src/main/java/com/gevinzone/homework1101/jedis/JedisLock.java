package com.gevinzone.homework1101.jedis;

import com.gevinzone.homework1101.IRedisLock;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;

public class JedisLock implements IRedisLock {
    private final Jedis jedis;

    public JedisLock(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public boolean tryLock(String key, String value, long millisecondsToExpire) {
        String result = jedis.set(key, value, SetParams.setParams().nx().px(millisecondsToExpire));
        String lockSuccess = "OK";
        return lockSuccess.equals(result);
    }

    @Override
    public boolean releaseLock(String key, String value) {
        String script = "" +
                "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                "   return redis.call('del', KEYS[1]) " +
                "else " +
                "   return 0 " +
                "end";
        Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(value));
        Long releaseSuccess = 1L;
        return releaseSuccess.equals(result);
    }
}
