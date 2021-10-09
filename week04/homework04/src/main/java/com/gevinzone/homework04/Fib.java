package com.gevinzone.homework04;

import java.util.ArrayList;
import java.util.List;

public class Fib {
    private final int input;
    private final List<Integer> cache;

    public Fib(int input) {
        this(input, null);
    }

    public Fib(int input, List<Integer> cache) {
        this.input = input;
        this.cache = cache != null ? cache : new ArrayList<>(50);;
        initCache();
    }

    private void initCache() {
        if (!cache.isEmpty()) {
            cache.clear();
        }
        cache.add(1);
        cache.add(1);
    }

    public void calculate(){
        if (cache.size() <= input) {
            int i = cache.size();
            for (; i <= input; i++) {
                int val = cache.get(i - 1) + cache.get(i - 2);
                cache.add(val);
            }
        }
    }

    public int getResult() {
        if (cache.size() <= input) {
            calculate();
        }
        return cache.get(input);
    }
}
