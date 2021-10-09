package com.gevinzone.homework04;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Future + Runnable
 */
public class method3 {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start=System.currentTimeMillis();
        int input = 36;
        FibRunnableTask2.Result resultContainer = new FibRunnableTask2.Result();
        Future<FibRunnableTask2.Result> future = executorService.submit(new FibRunnableTask2(new Fib(input), resultContainer), resultContainer);
        int result = future.get().getValue();

        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}
