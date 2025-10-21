package org.example.lab3;

public class Buffer1 {
    private int value = 0;

    public synchronized void put() throws InterruptedException {
        while (value != 0) {
            wait();
            System.out.println(Thread.currentThread().getName() + " woken up");
        }
        value++;
        System.out.println(Thread.currentThread().getName() + " put: value = " + value);
        notify();
    }

    public synchronized void take() throws InterruptedException {
        while (value != 1) {
            wait();
            System.out.println(Thread.currentThread().getName() + " woken up");
        }
        value--;
        System.out.println(Thread.currentThread().getName() + " took: value = " + value);
        notify();
    }
}
