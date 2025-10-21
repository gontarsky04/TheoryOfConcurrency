package org.example.lab3.zadania.zadanie_4_2_2;

public class Bufor<T> {
    private final T[] bufor;
    private final int N;
    private int ile = 0;
    private int doWlozenia = 0;
    private int doWyjecia = 0;

    @SuppressWarnings("unchecked")
    public Bufor(int rozmiar) {
        this.N = rozmiar;
        this.bufor = (T[]) new Object[N];
    }

    public synchronized void wstaw(T element) throws InterruptedException {
        while (ile == N) {
            wait();
        }
        bufor[doWlozenia] = element;
        doWlozenia = (doWlozenia + 1) % N;
        ile++;
        notifyAll();
        System.out.println("Producent wstawil: " + element);
        wypiszStan();
    }

    public synchronized T pobierz() throws InterruptedException {
        while (ile == 0) {
            wait();
        }
        T element = bufor[doWyjecia];
        bufor[doWyjecia] = null;
        doWyjecia = (doWyjecia + 1) % N;
        ile--;
        notifyAll();
        System.out.println("Konsument pobral: " + element);
        wypiszStan();
        return element;
    }

    private void wypiszStan() {
        System.out.print("   Bufor: [");
        for (int i = 0; i < N; i++) {
            if (bufor[i] != null)
                System.out.print(bufor[i]);
            else
                System.out.print("_");
            if (i < N - 1) System.out.print(", ");
        }
        System.out.println("]  (zajete: " + ile + "/" + N + ")");
    }
}
