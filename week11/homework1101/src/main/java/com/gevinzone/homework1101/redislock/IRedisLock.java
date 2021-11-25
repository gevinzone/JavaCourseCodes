package com.gevinzone.homework1101.redislock;

public interface IRedisLock {
    boolean tryLock(String key, String value, long millisecondsToExpire);

    boolean releaseLock(String key, String value);
}
