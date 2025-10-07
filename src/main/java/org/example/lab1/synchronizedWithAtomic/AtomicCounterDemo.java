package org.example.lab1.synchronizedWithAtomic;

public class AtomicCounterDemo {
    public static void main(String[] args) throws InterruptedException {
        int numOfThreads = 10;
        int numOfOperations = 100;
        Counter counter = new Counter();
        Thread[] threads = new Thread[numOfThreads];

        for (int i = 0; i < numOfThreads; i++) {
            if(i < numOfThreads / 2) {
                threads[i] = new Thread( () -> {
                    for (int j = 0; j < numOfOperations; j++) {
                        counter.increment();
                    }
                });
            } else {
                threads[i] = new Thread( () -> {
                    for (int j = 0; j < numOfOperations; j++) {
                        counter.decrement();
                    }
                });
            }
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        counter.print();
    }
}
