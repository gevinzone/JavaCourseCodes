package com.gevinzone.homework04;

public class FibRunnableTask2 implements Runnable{
    private final Fib fib;
    private final Result result;

    public FibRunnableTask2(Fib fib, Result result) {
        this.fib = fib;
        this.result = result;
    }

    @Override
    public void run() {
        result.setValue(fib.getResult());
    }

    public static class Result {
        private int value;
        public void setValue(int value) {
            this.value = value;
        }
        public int getValue() {
            return this.value;
        }
    }
}
