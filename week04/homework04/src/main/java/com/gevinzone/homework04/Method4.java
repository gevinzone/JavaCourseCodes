package com.gevinzone.homework04;

import java.util.ArrayList;
import java.util.List;

/**
 * join 同步线程
 */
public class Method4 {
    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        int input = 36;
        List<Integer> cache = new ArrayList<>(50);
        Thread thread = new Thread(new FibRunnableTask(new Fib(input, cache)));
        thread.start();
        thread.join();
        int result = -1;
        if (cache.size() > input) {
            result = cache.get(input);
        }

        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}
