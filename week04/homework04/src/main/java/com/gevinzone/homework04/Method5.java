package com.gevinzone.homework04;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池 + CountDownLatch 同步线程
 */
public class Method5 {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        int input = 36;
        List<Integer> cache = new ArrayList<>(50);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        executorService.execute(new FibRunnableTaskExtend(new Fib(input, cache), countDownLatch));
        countDownLatch.await();
        int result = -1;
        if (cache.size() > input) {
            result = cache.get(input);
        }

        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}
