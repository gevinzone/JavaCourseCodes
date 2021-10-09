package com.gevinzone.homework04;

public class FibRunnableTask implements Runnable {
    private final Fib fib;

    public FibRunnableTask(Fib fib) {
        this.fib = fib;
    }

    @Override
    public void run() {
        fib.calculate();
    }
}
