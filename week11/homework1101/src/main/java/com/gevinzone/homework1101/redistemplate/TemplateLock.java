package com.gevinzone.homework1101.redistemplate;

import com.gevinzone.homework1101.IRedisLock;
import com.gevinzone.homework1101.LuaScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Component
public class TemplateLock implements IRedisLock {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean tryLock(String key, String value, long millisecondsToExpire) {
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, value, millisecondsToExpire, TimeUnit.MILLISECONDS);
//        return result != null && result;
        return Boolean.TRUE.equals(result);
    }

    @Override
    public boolean releaseLock(String key, String value) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(LuaScript.releaseLock, Long.class);
        Long result = stringRedisTemplate.execute(script, Collections.singletonList(key), value);
        Long releaseSuccess = 1L;
        return releaseSuccess.equals(result);
    }
}
