package org.example.Consumer_Producer;

public class Main {
    static void main() {
        final int THREAD_PAIRS = 200;
        Buffer buffer = new Buffer();

        Thread[] producers = new Thread[THREAD_PAIRS];
        Thread[] consumers = new Thread[THREAD_PAIRS];

        // aktywne oczekiwanie zużywające procesor, pętle while

        // jedyny poprawny to, gdy wątek wykona operację tylko raz i poinformuje
        // następny wątek że już skończyć, volatile

        for (int i = 0; i < THREAD_PAIRS; i++) {
            producers[i] = new Thread(() -> {
                while (!buffer.produce()) {}
            });
        }

        for (int i = 0; i < THREAD_PAIRS; i++) {
            consumers[i] = new Thread(() -> {
                while (!buffer.consume()) {}
            });
        }

        for (int i = 0; i < THREAD_PAIRS; i++) {
            producers[i].start();
            consumers[i].start();
        }

        for (int i = 0; i < THREAD_PAIRS; i++) {
            try {
                producers[i].join();
                consumers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buffer.print("Main");
    }
}
