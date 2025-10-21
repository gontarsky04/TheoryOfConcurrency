package org.example.lab3;

import java.util.Random;

public class TestMPNKTargeted {
    private static final int M = 10;  // Number of producers
    private static final int N = 15;  // Number of consumers
    private static final int W = 5;   // Buffer capacity
    static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("=== Test MPNK Targeted Wake-ups: " + M + " Producers, " + N + " Consumers, " + W + "-element Buffer ===");
        System.out.println("Producers wake ONLY consumers, Consumers wake ONLY producers\n");

        BufferNTargeted buffer = new BufferNTargeted(W);

        Thread[] producers = new Thread[M];
        Thread[] consumers = new Thread[N];

        // Create and start producers
        for (int i = 0; i < M; i++) {
            final int id = i + 1;
            producers[i] = new Thread(() -> {
                try {
                    while (true) {
                        buffer.produce(random.nextInt(W/2));
                    }
                } catch (InterruptedException e) {
                    System.err.println(Thread.currentThread().getName() + " interrupted");
                    Thread.currentThread().interrupt();
                }
            }, "Producer-" + id);
            producers[i].start();
        }

        // Create and start consumers
        for (int i = 0; i < N; i++) {
            final int id = i + 1;
            consumers[i] = new Thread(() -> {
                try {
                    while (true) {
                        buffer.consume(random.nextInt(W/2));
                    }
                } catch (InterruptedException e) {
                    System.err.println(Thread.currentThread().getName() + " interrupted");
                    Thread.currentThread().interrupt();
                }
            }, "Consumer-" + id);
            consumers[i].start();
        }
    }
}
