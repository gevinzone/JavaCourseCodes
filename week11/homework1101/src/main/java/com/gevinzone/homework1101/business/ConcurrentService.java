package com.gevinzone.homework1101.business;

import com.gevinzone.homework1101.redislock.IRedisLock;

public class ConcurrentService {
    private int count = 0;
    // jedis 在多线程中会报错
    private final IRedisLock lock;

    public ConcurrentService(IRedisLock lock) {
        this.lock = lock;
    }

    public int multiThreadAdd(int threads) {
        String key = "multiThreadAddKey";
        for (int i = 0; i < threads; i++) {
            String value = String.valueOf(System.currentTimeMillis()) + i;
            new Thread(() -> {
                while (!lock.tryLock(key, value, 30000)) {
                    sleep(10);
                }
                count++;
                lock.releaseLock(key, value);
            }).start();
        }
        sleep(1000);
        return count;
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
