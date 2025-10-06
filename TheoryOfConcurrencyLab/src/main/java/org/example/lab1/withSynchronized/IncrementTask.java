package org.example.lab1.withSynchronized;

public class IncrementTask implements Runnable {
    @Override
    public synchronized void run() {
        if(UnsafeCounterDemo.counter == 0) {
            for (int i = 0; i < 10; i++) {
                synchronized (UnsafeCounterDemo.class) {
                    UnsafeCounterDemo.counter++;
                }
            }
        }
    }
}

