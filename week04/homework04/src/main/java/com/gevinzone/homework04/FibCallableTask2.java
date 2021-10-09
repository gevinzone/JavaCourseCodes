package com.gevinzone.homework04;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class FibCallableTask2 implements Callable<Integer> {
    private final int num;
    private final ConcurrentHashMap<Integer, Integer> cache;

    public FibCallableTask2(int num) {
        this(num, null);
    }

    public FibCallableTask2(int num, ConcurrentHashMap<Integer, Integer> cache) {
        this.num = num;
        this.cache = cache != null ? cache : new ConcurrentHashMap<>(50);
    }

    @Override
    public Integer call() throws Exception {
        return calculate(num);
    }

    private Integer calculate(int num) {
        if (num < 2) {
            return 1;
        }
        if (!cache.containsKey(num)) {
            cache.put(num, calculate(num - 1) + calculate(num - 2));
        }
        return cache.get(num);
    }
}
