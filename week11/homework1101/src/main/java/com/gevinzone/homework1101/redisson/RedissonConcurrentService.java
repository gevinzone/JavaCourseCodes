package com.gevinzone.homework1101.redisson;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class RedissonConcurrentService {
    private int count = 0;
    private final RedissonClient client;

    public RedissonConcurrentService(RedissonClient client) {
        this.client = client;
    }

    public int concurrentAdd(int threadsNum) throws InterruptedException {
        RLock lock = client.getLock("redissonAddLock");
        Thread[] threads = new Thread[threadsNum];
        for (int i = 0; i < threadsNum; i++) {
            threads[i] = new Thread(() -> {
                try {
                    boolean locked = lock.tryLock(500, TimeUnit.MILLISECONDS);
                    if (locked) {
                        try {
                            count++;
                        } finally {
                            lock.unlock();
                        }
                    }
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }, "redisson-thread-" + i);
            threads[i].start();
            threads[i].join();
        }
        return count;
    }
}
