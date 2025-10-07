package org.example.lab1.unsynchronizedRace;

public class DecrementTask implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            UnsafeCounterDemo.counter--;
        }
    }
}

