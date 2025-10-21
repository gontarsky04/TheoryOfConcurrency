# Problem Producent-Konsument - Analiza Zakleszczeń

## Przegląd problemów
1. **1P1K1B** - 1 Producer, 1 Consumer, Buffer 1-elementowy
2. **MPNK1B** - M Producers, N Consumers, Buffer 1-elementowy
3. **MPNKWB** - M Producers, N Consumers, Buffer W-elementowy

---

## Analiza możliwości zakleszczenia (deadlock)

### 1. Problem **1P1K1B** (1 Producer, 1 Consumer, Buffer=1)

**Czy może wystąpić zakleszczenie?** **NIE** ✗

**Powód:**
Z tylko jednym producentem i jednym konsumentem, `notify()` zawsze budzi właściwy wątek (jedyny czekający). Nie ma rywalizacji między wątkami tego samego typu.

---

### 2. Problem **MPNK1B** (M Producers, N Consumers, Buffer=1)

**Czy może wystąpić zakleszczenie?** **TAK** ✓

#### Scenariusz zakleszczenia z `notify()`:

```
Stan początkowy: value = 1 (bufor pełny)

Krok 1: Producer-1 wywołuje put()
        - widzi value = 1 (bufor pełny)
        - wywołuje wait() → Producer-1 ŚPI

Krok 2: Producer-2 wywołuje put()
        - widzi value = 1 (bufor pełny)
        - wywołuje wait() → Producer-2 ŚPI

Krok 3: Consumer-1 wywołuje take()
        - value = 1, więc pobiera
        - value: 1 → 0
        - wywołuje notify()

⚠️  PROBLEM: notify() budzi LOSOWY wątek z puli czekających!

Krok 4: notify() budzi Producer-1 (zamiast producenta który mógłby kontynuować)
        - Producer-1 budzi się
        - sprawdza warunek: value = 0 (OK, może dodać)
        - value: 0 → 1
        - wywołuje notify()

Krok 5: notify() budzi Producer-2 (znowu producent zamiast konsumenta!)
        - Producer-2 budzi się
        - sprawdza warunek: value = 1 (bufor pełny!)
        - wywołuje wait() → Producer-2 znowu ŚPI

Krok 6: Consumer-1 wraca do pętli while(true), próbuje take()
        - value = 1, więc próbuje pobrać... ale może być wyprzedzony
        - albo wywołuje wait() bo inny wątek zmienił stan

REZULTAT:
- Producer-1: wait() (czeka)
- Producer-2: wait() (czeka)
- Consumer-1: wait() (czeka)
- Wszyscy inni: wait() (czekają)

→ **DEADLOCK!** Wszyscy śpią, nikt nie może obudzić innych.
```

#### Dlaczego to się dzieje?

1. **`notify()` budzi losowy wątek** z puli wszystkich czekających wątków
2. Może obudzić **niewłaściwy typ wątku**:
   - Producent budzi producenta (powinien obudzić konsumenta)
   - Konsument budzi konsumenta (powinien obudzić producenta)
3. Przy wielu wątkach każdego typu, seria niewłaściwych obudzeń może doprowadzić do sytuacji gdzie **wszystkie wątki czekają**

---

### 3. Problem **MPNKWB** (M Producers, N Consumers, Buffer=W)

**Czy może wystąpić zakleszczenie?** **TAK** ✓

#### Scenariusz zakleszczenia:

```
Konfiguracja: M=13 producentów, N=20 konsumentów, W=5 (maxLimit)

Stan początkowy: value = 5 (bufor pełny)

Krok 1: Wszyscy producenci próbują dodać
        - Producer-1...Producer-13: wszystkie wywołują wait()
        - Wszystkie 13 producentów ŚPI

Krok 2: Consumer-1 wywołuje take()
        - value: 5 → 4
        - notify()

Krok 3: notify() budzi Producer-1 (niewłaściwy typ!)
        - Producer-1: value = 4 < 5 (OK)
        - value: 4 → 5
        - notify()

Krok 4: notify() budzi Producer-2 (znowu producent!)
        - Producer-2: value = 5 >= maxLimit
        - wait() → Producer-2 znowu ŚPI

Krok 5: Konsumenci próbują take() ale value=5
        - niektórzy mogą czekać

Podobnie gdy value = 0 (bufor pusty):
        - Wszyscy konsumenci czekają
        - Producent dodaje element i notify() budzi... innego konsumenta
        - Ten konsument: value = 0, więc wait()
        - Producent może też czekać

→ **DEADLOCK** gdy wszyscy wątki wpadną w wait()
```

#### Dodatkowy problem w BufferN:

Przy większym buforze (W > 1), zakleszczenie jest **mniej prawdopodobne** ale **wciąż możliwe**:
- Bufor może być częściowo zapełniony (np. value = 3)
- Producenci i konsumenci mogą pracować jednocześnie
- ALE nadal `notify()` może obudzić niewłaściwy typ wątku w krytycznym momencie

---

## Rozwiązania problemu zakleszczenia

### **Rozwiązanie 1: Użyj `notifyAll()` zamiast `notify()`**

```java
public synchronized void put() throws InterruptedException {
    while (value >= maxLimit) {
        wait();
    }
    value++;
    notifyAll();  // Budzi WSZYSTKIE czekające wątki
}

public synchronized void take() throws InterruptedException {
    while (value <= 0) {
        wait();
    }
    value--;
    notifyAll();  // Budzi WSZYSTKIE czekające wątki
}
```

**Plusy:**
- Gwarantuje że właściwy wątek zostanie obudzony
- Eliminuje problem zakleszczenia
- Proste do implementacji

**Minusy:**
- Nieefektywne - budzi wszystkie wątki, nawet te które znowu zaśpią
- Zwiększone przełączanie kontekstu
- "Thundering herd" problem

---

### **Rozwiązanie 2: Osobne obiekty warunkowe**

```java
public class BufferWithSeparateConditions {
    private int value = 0;
    private final int maxLimit;

    private final Object notFull = new Object();   // dla producentów
    private final Object notEmpty = new Object();  // dla konsumentów

    public void put() throws InterruptedException {
        synchronized(notFull) {
            while (value >= maxLimit) {
                notFull.wait();
            }
        }

        synchronized(this) {
            value++;
        }

        synchronized(notEmpty) {
            notEmpty.notify();  // Budzi tylko konsumentów
        }
    }

    public void take() throws InterruptedException {
        synchronized(notEmpty) {
            while (value <= 0) {
                notEmpty.wait();
            }
        }

        synchronized(this) {
            value--;
        }

        synchronized(notFull) {
            notFull.notify();  // Budzi tylko producentów
        }
    }
}
```

**Plusy:**
- `notify()` budzi tylko właściwy typ wątku
- Bardziej efektywne niż `notifyAll()`

**Minusy:**
- Bardziej skomplikowane
- Wymaga starannej synchronizacji wielu obiektów
