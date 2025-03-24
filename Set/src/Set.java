import java.util.Arrays;

public class Set<T extends Comparable<T>>{
    private T[] set; //tablica, w której przechowywane będą obiekty
    private int pojemnosc; //maksymalna liczba elementów, możliwych do przechowywania
    private int rozmiar; //aktualna liczba przechowywanych elementów

    public Set(int pojemnosc){
        this.pojemnosc = pojemnosc;
        this.set= (T[]) new Comparable[pojemnosc];
        this.rozmiar = 0;
    }

    public void dodajElement(T element){
        if(rozmiar >= pojemnosc){
            throw new ArrayIndexOutOfBoundsException();
        }
        if(szukaj(element) != -1) { // obiekt wystapil w set
            return;
        }
        set[rozmiar++] = element;
        Arrays.sort(set, 0, rozmiar);
    }

    public int szukaj(T element){
        for(int i = 0; i < rozmiar; i++){
            if(set[i].compareTo(element) == 0){ // obiekt wystapil w set
                return i;
            }
        }
        return -1; // nie wystapil w set
    }

    public void usunElement(T element){
        int indeks = szukaj(element);
        if(indeks == -1){ // brak elementu w set
            return;
        }

        System.arraycopy(set, indeks+1, set, indeks, rozmiar-indeks-1);
        set[rozmiar--]=null;
    }

    public static <T extends Comparable<T>> Set<T> dodajElementy(Set<T> zbior1, Set<T> zbior2){
        Set<T> nowyZbior = new Set<>(zbior1.pojemnosc + zbior2.pojemnosc);
        for(int i = 0; i < zbior1.rozmiar; i++){
            nowyZbior.dodajElement(zbior1.set[i]);
        }
        for(int i = 0; i < zbior2.rozmiar; i++){
            nowyZbior.dodajElement(zbior2.set[i]);
        }
        return nowyZbior;
    }

    public static <T extends Comparable<T>> Set<T> odejmijElementy(Set<T> zbior1, Set<T> zbior2){
        Set<T> nowyZbior = new Set<>(zbior1.pojemnosc);
        for(int i = 0; i < zbior1.rozmiar; i++){
            if(zbior2.szukaj(zbior1.set[i]) == -1){ // element ze zbior1 nie wystepuje w zbior2
                nowyZbior.dodajElement(zbior1.set[i]);
            }
        }
        return nowyZbior;
    }

    public static <T extends Comparable<T>> Set<T> przeciecie(Set<T> zbior1, Set<T> zbior2){
        Set<T> nowyZbior = new Set<>(Math.min(zbior1.pojemnosc, zbior2.pojemnosc));
        for(int i = 0; i < zbior1.rozmiar; i++){
            if(zbior2.szukaj(zbior1.set[i]) != -1){ // element ze zbior1 wystepuje w zbior2
                nowyZbior.dodajElement(zbior1.set[i]);
            }
        }
        return nowyZbior;
    }

    @Override
    public String toString() {
        return "Set{" +
                " rozmiar=" + rozmiar +
                ", pojemnosc=" + pojemnosc +
                " set=" + Arrays.toString(Arrays.copyOf(set, rozmiar)) + '}';
    }

    public static void main(String[] args) {
        // Testy dla klasy Person
        System.out.println("Testy dla Person");
        try {
            Set<Person> family1 = new Set<>(6);
            family1.dodajElement(new Person("Ann", 42));
            family1.dodajElement(new Person("John", 46));
            family1.dodajElement(new Person("Kate", 6));
            family1.dodajElement(new Person("Chuck", 14));

            Set<Person> family2 = new Set<>(4);
            family2.dodajElement(new Person("Lucy", 31));
            family2.dodajElement(new Person("Peter", 36));
            family2.dodajElement(new Person("Kate", 6));

            System.out.println("Suma");
            Set<Person> suma = Set.<Person>dodajElementy(family1, family2);
            System.out.println("Roznica");
            Set<Person> roznica = Set.<Person>odejmijElementy(family1, family2);
            System.out.println("przeciecie");
            Set<Person> przeciecie = Set.<Person>przeciecie(family1, family2);

            System.out.println("Zbiór 1: " + family1);
            System.out.println("Zbiór 2: " + family2);
            System.out.println("Suma: " + suma);
            System.out.println("Różnica: " + roznica);
            System.out.println("Przecięcie: " + przeciecie);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Nie można dodać elementu: " + e.getMessage());
        }

        // Testy dla klasy Book
        System.out.println("Testy dla Book");
        try {
            Set<Book> shelf1 = new Set<>(15);
            shelf1.dodajElement(new Book("The Great Gatsby", "F.Scott Fitzegarld"));
            shelf1.dodajElement(new Book("Letters to Milena", "Frantz Kafka"));

            Set<Book> shelf2 = new Set<>(15);
            shelf2.dodajElement(new Book("Pride and Prejudice", "Jane Austin"));
            shelf2.dodajElement(new Book("Letters to Milena", "Frantz Kafka"));

            System.out.println("Suma");
            Set<Book> suma = Set.<Book>dodajElementy(shelf1, shelf2);
            System.out.println("Roznica");
            Set<Book> roznica = Set.<Book>odejmijElementy(shelf1, shelf2);
            System.out.println("przeciecie");
            Set<Book> przeciecie = Set.<Book>przeciecie(shelf1, shelf2);

            System.out.println("Zbiór 1: " + shelf1);
            System.out.println("Zbiór 2: " + shelf2);
            System.out.println("Suma: " + suma);
            System.out.println("Różnica: " + roznica);
            System.out.println("Przecięcie: " + przeciecie);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Nie można dodać elementu: " + e.getMessage());
        }

    }
}


class Person implements Comparable<Person>{
    private String name;
    private int age;

    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person o) {
        return Integer.compare(this.age, o.age);
    }

    @Override
    public String toString() {
        return "Person: " +
                name + '(' +
                age +
                ')';
    }
}

class Book implements Comparable<Book>{
    private String title;
    private String author;
    public Book(String title, String author){
        this.title = title;
        this.author = author;
    }

    @Override
    public int compareTo(Book o) {
        return this.title.compareTo(o.title);
    }

    @Override
    public String toString() {
        return "Book:" +
                title + '(' +
                author +
                ')';
    }
}
