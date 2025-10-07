package org.example.lab1.unsynchronizedRace;

public class IncrementTask implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            UnsafeCounterDemo.counter++;
        }
    }
}

