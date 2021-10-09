package com.gevinzone.homework04;

import java.util.concurrent.*;

/**
 * 使用Future Task
 */
public class Method2 {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start=System.currentTimeMillis();
        int input = 36;
        FutureTask<Integer> futureTask = new FutureTask<>(new FibCallableTask(new Fib(input)));
        executorService.submit(futureTask);
        int result = futureTask.get();

        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}
