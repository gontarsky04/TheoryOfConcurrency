package org.example.lab1.withAtomic;

import org.example.lab1.withSynchronized.DecrementTask;
import org.example.lab1.withSynchronized.IncrementTask;

import java.util.concurrent.atomic.AtomicInteger;

public class UnsafeCounterDemo {
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {

        int numThreads = Integer.parseInt(args[0]);
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            if (i % 2 == 0) {
                threads[i] = new Thread(new IncrementTask());
            } else {
                threads[i] = new Thread(new DecrementTask());
            }
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Final counter value: " + counter);
    }
}
