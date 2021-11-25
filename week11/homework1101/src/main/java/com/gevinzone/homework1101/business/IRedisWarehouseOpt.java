package com.gevinzone.homework1101.business;

import java.util.HashMap;

public interface IRedisWarehouseOpt {
    void increaseHashValue(String key, String field, long delta);
    void decreaseHashValue(String key, String field, long delta);
    void insertHashMap(String key, HashMap<String, String> data);
    String getHashMapValue(String key, String field);
}
