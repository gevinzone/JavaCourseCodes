package com.gevinzone.homework04;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 执行2个子任务
 */
public class Method6 {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        int input = 36;
        FutureTask<Integer> futureTask1 = new FutureTask<>(new FibCallableTask2(input - 1));
        FutureTask<Integer> futureTask2 = new FutureTask<>(new FibCallableTask2(input - 2));
        executorService.submit(futureTask1);
        executorService.submit(futureTask2);
        int result = futureTask1.get() + futureTask2.get();

        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

}
