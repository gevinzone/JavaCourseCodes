package com.gevinzone.homework04;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class FibSupplier implements Supplier<Integer> {
    private final int num;
    private final ConcurrentHashMap<Integer, Integer> cache;

    public FibSupplier(int num) {
        this(num, null);
    }

    public FibSupplier(int num, ConcurrentHashMap<Integer, Integer> cache) {
        this.num = num;
        this.cache = cache != null ? cache : new ConcurrentHashMap<>(50);
    }
    @Override
    public Integer get() {
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
