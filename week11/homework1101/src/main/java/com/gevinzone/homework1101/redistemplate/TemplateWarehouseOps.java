package com.gevinzone.homework1101.redistemplate;

import com.gevinzone.homework1101.business.IRedisWarehouseOpt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class TemplateWarehouseOps implements IRedisWarehouseOpt {
    @Autowired
    StringRedisTemplate template;

    @Override
    public Long increaseHashValue(String key, String field, long delta) {
        return template.opsForHash().increment(key, field, delta);
    }

    @Override
    public Long decreaseHashValue(String key, String field, long delta) {
        return template.opsForHash().increment(key, field, -delta);
    }

    @Override
    public void insertHashMap(String key, HashMap<String, String> data) {
        template.opsForHash().putAll(key, data);
    }

    @Override
    public String getHashMapValue(String key, String field) {
        Object result = template.opsForHash().get(key, field);
        return String.valueOf(result);
    }
}
