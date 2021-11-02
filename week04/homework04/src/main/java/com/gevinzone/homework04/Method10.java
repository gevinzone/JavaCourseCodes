package com.gevinzone.homework04;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程synchronized
 */
public class Method10 {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public static void main(String ... args) throws InterruptedException {
        long start=System.currentTimeMillis();

        int input = 36;
        List<Integer> cache = new ArrayList<>();
        cache.add(1);
        cache.add(1);
        CountDownLatch countDownLatch = new CountDownLatch(input - 1);
        for (int i = 2; i <= input; i++) {
            executorService.execute(new FibTask(cache, countDownLatch));
        }
        countDownLatch.await();
        int result = cache.get(cache.size() - 1);
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    private static class FibTask implements Runnable {
        private final List<Integer> cache;
        private final CountDownLatch countDownLatch;

        private FibTask(List<Integer> cache, CountDownLatch countDownLatch) {
            this.cache = cache;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            calculate();
            countDownLatch.countDown();
        }

        private void calculate() {
            synchronized (cache) {
                cache.add(cache.get(cache.size() - 1) + cache.get(cache.size() - 2));
            }
        }
    }
}
