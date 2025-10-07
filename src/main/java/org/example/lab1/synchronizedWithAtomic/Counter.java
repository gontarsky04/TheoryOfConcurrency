package org.example.lab1.synchronizedWithAtomic;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private final AtomicInteger value = new AtomicInteger(0);

    public void increment() {
        value.incrementAndGet();
    }

    public void decrement() {
        value.decrementAndGet();
    }

    public void print() {
        System.out.println(value.get());
    }
}
