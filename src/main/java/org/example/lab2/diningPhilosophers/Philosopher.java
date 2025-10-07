package org.example.lab2.diningPhilosophers;

public class Philosopher extends Thread {
    private final int id;
    private final DiningMonitor monitor;

    public Philosopher(int id, DiningMonitor monitor) {
        this.id = id;
        this.monitor = monitor;
    }

    public void run() {
        try {
            while (true) {
                think();
                monitor.takeForks(id);
                eat();
                monitor.putForks(id);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking.");
        Thread.sleep((long)(Math.random() * 1000));
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating.");
        Thread.sleep((long)(Math.random() * 1000));
    }

}
