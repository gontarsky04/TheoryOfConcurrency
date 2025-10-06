package org.example.lab1.withAtomic;

import org.example.lab1.withAtomic.UnsafeCounterDemo;

public class DecrementTask implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            UnsafeCounterDemo.counter--; // unsafe update
        }
    }
}
