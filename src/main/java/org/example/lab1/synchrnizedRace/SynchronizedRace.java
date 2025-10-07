package org.example.lab1.synchrnizedRace;

public class SynchronizedRace {
    public static int counter = 0;

    public static void main(String[] args) {
        final int NUM_OF_THREADS = 1000;
        Buffor buffor = new Buffor();
        Thread[] threads = new Thread[NUM_OF_THREADS];

        for (int i = 0; i < NUM_OF_THREADS / 2; i++) {
            threads[i] = new Thread( () -> {
                while(!buffor.increment()) {}
            });
        }

        for (int i = NUM_OF_THREADS / 2; i < NUM_OF_THREADS; i++) {
            threads[i] = new Thread( () -> {
                while(!buffor.decrement()) {}
            });
        }

        for (Thread t: threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        buffor.print();
    }
}
