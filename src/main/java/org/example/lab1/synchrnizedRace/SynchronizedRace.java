package org.example.lab1.synchrnizedRace;

public class SynchronizedRace {
    public static int counter = 0;
    public static int numThreads = 100;

    public static void main(String[] args) {
        Buffor buffor = new Buffor();
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            if(i % 2 == 0) {
                threads[i] = new Thread(buffor::increment);
            } else {
                threads[i] = new Thread(buffor::decrement);
            }
            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {}
        }

        buffor.print();
    }
}
