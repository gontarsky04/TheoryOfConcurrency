package org.example.lab3;

public class BufferN {
    private int value = 0;
    private final int maxLimit;

    public BufferN(int maxLimit) {
        this.maxLimit = maxLimit;
    }

    public synchronized void put() throws InterruptedException {
        while (value >= maxLimit) {
            wait();
            System.out.println(Thread.currentThread().getName() + " woken up");
        }
        value++;
        System.out.println(Thread.currentThread().getName() + " incremented: value = " + value);
        notify();
    }

    public synchronized void take() throws InterruptedException {
        while (value <= 0) {
            wait();
            System.out.println(Thread.currentThread().getName() + " woken up");
        }
        value--;
        System.out.println(Thread.currentThread().getName() + " decremented: value = " + value);
        notify();
    }
}
