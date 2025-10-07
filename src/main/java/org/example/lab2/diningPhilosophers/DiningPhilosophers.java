package org.example.lab2.diningPhilosophers;

public class DiningPhilosophers {
    public static void main(String[] args) {
        DiningMonitor monitor = new DiningMonitor();
        for (int i = 0; i < 5; i++) {
            new Philosopher(i, monitor).start();
        }
    }
}
