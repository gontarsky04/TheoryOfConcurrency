package org.example.lab1.synchrnizedRace;

public class Buffor {

    public synchronized boolean increment() {
        if(SynchronizedRace.counter == 0) {
            SynchronizedRace.counter++;
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean decrement() {
        if(SynchronizedRace.counter > 0) {
            SynchronizedRace.counter--;
            return true;
        } else {
            return false;
    }
    }

    public void print() {
        System.out.println("Counter: " + SynchronizedRace.counter);
    }
}