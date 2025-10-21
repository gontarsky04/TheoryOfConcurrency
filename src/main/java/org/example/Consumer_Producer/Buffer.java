package org.example.Consumer_Producer;

public class Buffer {
    private int data = 0;

    public synchronized boolean produce() {
        if (data == 0) {
            data = 1;
            this.print("Producer");
            return true;
        }
        return false;
    }

    public synchronized boolean consume() {
        if (data == 1) {
            data = 0;
            this.print("Consumer");
            return true;
        }
        return false;
    }

    public synchronized void print(String origin) {
        System.out.println(origin + " - Current buffer state: " + data);
    }
}


