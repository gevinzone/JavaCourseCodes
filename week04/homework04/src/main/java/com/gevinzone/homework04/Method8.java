package com.gevinzone.homework04;


import com.sun.xml.internal.ws.util.CompletedFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用Completable Future
 */
public class Method8 {
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        int input = 36;
        ConcurrentHashMap<Integer, Integer> cache = new ConcurrentHashMap<>(50);
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(new FibSupplier(input - 1, cache));
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(new FibSupplier(input - 2, cache));
        CompletableFuture<Integer> f3 = f1.thenCombine(f2, Integer::sum);

        int result = f3.join(); //这是得到的返回值

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

    }
}
