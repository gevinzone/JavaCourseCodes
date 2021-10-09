package com.gevinzone.homework04;

import java.util.concurrent.Callable;

public class FibCallableTask implements Callable<Integer> {
    private final Fib fib;

    public FibCallableTask(Fib fib) {
        this.fib = fib;
    }

    @Override
    public Integer call() throws Exception {
        return fib.getResult();
    }

}
