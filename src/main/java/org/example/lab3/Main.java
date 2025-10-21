package org.example.lab3;

import java.util.Random;

public class Main {
    private static final int M = 5; // liczba producentów
    private static final int N = 5; // liczba konsumentów
    private static final int W = 5;  // rozmiar buffora

//    // JEDEN PRODUCENT, JEDEN KONSUMENT, JEDEN BUFOR
//    public static void main1(String[] args) {
//        Buffer1 buffer = new Buffer1();
//        Thread producer = new Thread(() -> {
//            try {
//                while(true) {
//                    buffer.put();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "Producer Thread");
//
//        Thread consumer = new Thread(() -> {
//            try {
//                while(true) {
//                    buffer.take();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },  "Consumer Thread");
//
//        producer.start();
//        consumer.start();
//    }
//
//    // WIELU PRODUCENTÓW, WIELU KONSUMENTÓW, JEDEN BUFFOR
//    public static void main2(String[] args) {
//        Buffer1 buffer = new Buffer1();
//        Thread[] producers = new Thread[M];
//        Thread[] consumers = new Thread[N];
//
//        for (int i = 0; i < M; i++) {
//            producers[i] = new Thread(() -> {
//                try {
//                    while (true) {
//                        buffer.put();
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }, "Producent " + (i+1));
//            producers[i].start();
//        }
//
//        for (int i = 0; i < N; i++) {
//            consumers[i] = new Thread(() -> {
//                try {
//                    while (true) {
//                        buffer.take();
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            },  "Consumer " + (i+1));
//            consumers[i].start();
//        }
//    }
//
//    // WIELU PRODUCENTÓW, WIELU KONSUMENTÓW, WIELOELEMENTOWY BUFOR
//    public static void main3 (String[] args) {
//        BufferN buffer = new BufferN(W);
//        Thread[] producers = new Thread[M];
//        Thread[] consumers = new Thread[N];
//
//        for (int i = 0; i < M; i++) {
//            producers[i] = new Thread(() -> {
//                try {
//                    while (true) {
//                        buffer.put();
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }, "Producent " + (i+1));
//            producers[i].start();
//        }
//
//        for (int i = 0; i < N; i++) {
//            consumers[i] = new Thread(() -> {
//                try {
//                    while (true) {
//                        buffer.take();
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            },  "Consumer " + (i+1));
//            consumers[i].start();
//        }
//    }

    // WIELU PRODUCENTÓW, WIELU KONSUMENTÓW, WIELOELEMENTOWY BUFOR (LOCK)
    public static void main(String[] args) {
        BufferNTargeted buffer = new BufferNTargeted(W);
        Thread[] producers = new Thread[M];
        Thread[] consumers = new Thread[N];
        Random rand = new Random();


        for (int i = 0; i < M; i++) {
            producers[i] = new Thread(() -> {
                try {
                    while (true) {
                        buffer.produce(rand.nextInt(W/2));
                    }
                } catch (InterruptedException e) {
                    System.err.println(Thread.currentThread().getName() + " interrupted");
                    Thread.currentThread().interrupt();
                }
            }, "Producent" + (i+1));
            producers[i].start();
        }

        for (int i = 0; i < N; i++) {
            consumers[i] = new Thread(() -> {
                try {
                    while (true) {
                        buffer.consume(rand.nextInt(W/2));
                    }
                } catch (InterruptedException e) {
                    System.err.println(Thread.currentThread().getName() + " interrupted");
                    Thread.currentThread().interrupt();
                }
            }, "Konsument" + (i+1));
            consumers[i].start();
        }
    }
}
