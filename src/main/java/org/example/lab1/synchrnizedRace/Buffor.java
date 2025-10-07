package org.example.lab1.synchrnizedRace;

public class Buffor {

    public synchronized void increment() {
        if(SynchronizedRace.counter == 0) {
            for(int i = 0; i < 10; i++) {
                SynchronizedRace.counter++;
            }
        }
    }

    public synchronized void decrement() {
        if(SynchronizedRace.counter > 0) {
            SynchronizedRace.counter--;
        }
    }

    public void print() {
        System.out.println("Counter: " + SynchronizedRace.counter);
    }
}
