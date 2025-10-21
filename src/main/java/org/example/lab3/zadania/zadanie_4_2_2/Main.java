package org.example.lab3.zadania.zadanie_4_2_2;

public class Main {
    public static void main(String[] args) {
        Bufor<Integer> bufor = new Bufor<>(5);

        Thread producent = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    bufor.wstaw(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });


        Thread konsument = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    bufor.pobierz();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producent.start();
        konsument.start();
    }
}
