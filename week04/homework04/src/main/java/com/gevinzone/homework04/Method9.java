package com.gevinzone.homework04;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;

/**
 * 使用Fork join 框架
 */
public class Method9 {
    public static void main(String[] args) {

        long start=System.currentTimeMillis();

        int input = 36;
        ConcurrentHashMap<Integer, Integer> cache = new ConcurrentHashMap<>(50);
        int result = new Fibonacci(input, cache).compute();

        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    private static class Fibonacci extends RecursiveTask<Integer> {
        private final int n;
        private final ConcurrentHashMap<Integer, Integer> cache;

        private Fibonacci(int n, ConcurrentHashMap<Integer, Integer> cache) {
            this.n = n;
            this.cache = cache;
        }

        @Override
        protected Integer compute() {
            if (n < 2) {
                return 1;
            }
            if (!cache.containsKey(n)) {
                Fibonacci f1 = new Fibonacci(n - 1, cache);
                f1.fork();
                Fibonacci f2 = new Fibonacci(n - 2, cache);
                cache.put(n, f2.compute() + f1.join());
            }
            return cache.get(n);
        }
    }
}
