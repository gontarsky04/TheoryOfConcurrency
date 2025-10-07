package org.example.lab2.diningPhilosophers;

public class DiningMonitor {
    private final int numOfPhilosophers = 5;
    private final boolean[] forks = new boolean[numOfPhilosophers]; // true = available

    public DiningMonitor() {
        for (int i = 0; i < numOfPhilosophers; i++) {
            forks[i] = true; // all forks initially available
        }
    }

    public synchronized void takeForks(int i) throws InterruptedException {
        int left = i;
        int right = (i + 1) % numOfPhilosophers;

        // Wait until both forks are available
        while (!forks[left] || !forks[right]) {
            wait();
        }

        // Pick up forks
        forks[left] = false;
        forks[right] = false;

        System.out.println("Philosopher " + i + " takes forks");
    }

    public synchronized void putForks(int i) {
        int left = i;
        int right = (i + 1) % numOfPhilosophers;

        // Put down forks
        forks[left] = true;
        forks[right] = true;

        System.out.println("Philosopher " + i + " puts down forks");

        // Notify others that forks might be available
        notifyAll();
    }
}
