package org.example.lab3;

public class TestMPNKWB {
    private static final int M = 13;  // Number of producers
    private static final int N = 20;  // Number of consumers
    private static final int W = 5;  // Buffer capacity

    public static void main(String[] args) {
        System.out.println("=== Test MPNKWB: " + M + " Producers, " + N + " Consumers, " + W + "-element Buffer ===\n");

        BufferN buffer = new BufferN(W);

        Thread[] producers = new Thread[M];
        Thread[] consumers = new Thread[N];

        // Create and start producers
        for (int i = 0; i < M; i++) {
            final int id = i + 1;
            producers[i] = new Thread(() -> {
                try {
                    while (true) {
                        buffer.put();
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
                        buffer.take();
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
