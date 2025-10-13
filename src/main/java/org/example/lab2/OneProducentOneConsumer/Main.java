package org.example.lab2.OneProducentOneConsumer;

public class Main {
    public static Thread[] producent = new Thread[2];
    public static Thread[] consument = new Thread[2];

    Buffer buffer = new Buffer();

    public static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        producent[0] = new Thread(() -> {
            buffer.increment()
        })
    }
}

// pierwwszy konsument wchodzi i sie wiesza
// drugi tez
// wchodzi pierwszy producent, produkuje i wywouje notify ale nie wpuszcza na razie go