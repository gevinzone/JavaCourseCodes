package com.gevinzone.homework04;

import java.util.concurrent.*;

/**
 * Method 6 + ConcurrentHashMap 共享缓存，进一步减少计算量
 */
public class Method7 {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        int input = 36;
        ConcurrentHashMap<Integer, Integer> cache = new ConcurrentHashMap<>();
        FutureTask<Integer> futureTask1 = new FutureTask<>(new FibCallableTask2(input - 1, cache));
        FutureTask<Integer> futureTask2 = new FutureTask<>(new FibCallableTask2(input - 2, cache));
        executorService.submit(futureTask1);
        executorService.submit(futureTask2);
        int result = futureTask1.get() + futureTask2.get();

        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }
}
