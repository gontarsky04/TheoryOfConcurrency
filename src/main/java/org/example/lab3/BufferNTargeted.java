package org.example.lab3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferNTargeted {
    private int value = 0;
    private final int maxLimit;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public BufferNTargeted(int maxLimit) {
        this.maxLimit = maxLimit;
    }

    public void produce(int n) throws InterruptedException {
        lock.lock();
        try {
            while (value + n >= maxLimit) {
                notFull.await();
            }
            value+= n;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void consume(int n) throws InterruptedException {
        lock.lock();
        try {
            while (value - n <= 0) {
                notEmpty.await();
            }
            value -= n;
            notFull.signal();
        } finally {
            lock.unlock();
        }
    }
}
