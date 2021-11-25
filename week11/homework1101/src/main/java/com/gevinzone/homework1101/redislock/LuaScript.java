package com.gevinzone.homework1101.redislock;

public class LuaScript {
    public static final String releaseLock = "" +
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "   return redis.call('del', KEYS[1]) " +
            "else " +
            "   return 0 " +
            "end";
}
