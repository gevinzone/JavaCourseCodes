package com.gevinzone.homework04;

import java.util.concurrent.CountDownLatch;

public class FibRunnableTaskExtend extends FibRunnableTask{
    private final CountDownLatch countDownLatch;

    public FibRunnableTaskExtend(Fib fib, CountDownLatch countDownLatch) {
        super(fib);
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        super.run();
        countDownLatch.countDown();
    }
}
