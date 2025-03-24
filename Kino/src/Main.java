import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SystemRezerwacji system = new SystemRezerwacji();

        while (true) {
            System.out.println("\n🎥 SYSTEM REZERWACJI BILETÓW DO KINA 🎟️");
            System.out.println("1. Wyświetl dostępne seanse");
            System.out.println("2. Dodaj nowy seans");
            System.out.println("3. Zarezerwuj bilet");
            System.out.println("4. Wyświetl rezerwacje");
            System.out.println("5. Wyjście");
            System.out.print("Wybierz opcję: ");

            int opcja = scanner.nextInt();
            scanner.nextLine();

            switch (opcja) {
                case 1:
                    system.wyswietlSeanse();
                    break;

                case 2:
                    System.out.print("Podaj tytuł filmu: ");
                    String tytul = scanner.nextLine();
                    System.out.print("Podaj dzień seansu (np. 15-03-2025): ");
                    String dzien = scanner.nextLine();
                    System.out.print("Podaj godzinę seansu (np. 18:30): ");
                    String godzina = scanner.nextLine();
                    System.out.print("Podaj ograniczenie wiekowe: ");
                    int wiek = scanner.nextInt();
                    System.out.print("Podaj liczbę rzędów: ");
                    int rzedow = scanner.nextInt();
                    System.out.print("Podaj liczbę miejsc w rzędzie: ");
                    int miejsc = scanner.nextInt();
                    scanner.nextLine();

                    Seans nowySeans = new Seans(tytul, dzien, godzina, wiek, rzedow, miejsc);
                    system.dodajSeans(nowySeans);
                    System.out.println("✅ Seans dodany!");
                    break;

                case 3:
                    System.out.println("Dostępne seanse:");
                    system.wyswietlSeanse();
                    System.out.print("Podaj tytuł filmu: ");
                    String wybranyTytul = scanner.nextLine();

                    Seans wybranySeans = system.getSeanse().stream()
                            .filter(s -> s.toString().contains(wybranyTytul))
                            .findFirst()
                            .orElse(null);

                    if (wybranySeans == null) {
                        System.out.println("❌ Seans nie znaleziony!");
                        break;
                    }

                    System.out.println("Dostępne miejsca: ");
                    //keySet() pobiera zbiór rzędów (np. 'A', 'B', 'C'), po którym iterujemy
                    for (char rzad : wybranySeans.getMiejsca().keySet()) {
                        System.out.print(rzad + ": ");
                        //zwraca mapę miejsc dla danego rzędu
                        for (int m : wybranySeans.getMiejsca().get(rzad).keySet()) {
                            // jeśli miejsce nie jest zajętem to je wypisujemy
                            if (!wybranySeans.getMiejsca().get(rzad).get(m)) {
                                System.out.print(m + " ");
                            }
                        }
                        System.out.println();
                    }


                    System.out.print("Podaj imię: ");
                    String imie = scanner.nextLine();
                    System.out.print("Podaj nazwisko: ");
                    String nazwisko = scanner.nextLine();
                    System.out.print("Podaj e-mail: ");
                    String email = scanner.nextLine();
                    System.out.print("Podaj telefon: ");
                    String telefon = scanner.nextLine();

                    Set<String> miejscaRezerwacja = new HashSet<>();
                    System.out.print("Podaj liczbę miejsc do rezerwacji: ");
                    int liczbaMiejsc = scanner.nextInt();
                    scanner.nextLine();

                    for (int i = 0; i < liczbaMiejsc; i++) {
                        System.out.print("Podaj miejsce (np. A5): ");
                        String miejsce = scanner.nextLine().toUpperCase();
                        //czy istnieje taki rząd, czy numer miejsca istnieje w danym rzędzie, czy miejsce jest wolne (false oznacza wolne)
                        if (wybranySeans.getMiejsca().containsKey(miejsce.charAt(0)) &&
                                wybranySeans.getMiejsca().get(miejsce.charAt(0)).containsKey(Integer.parseInt(miejsce.substring(1))) &&
                                !wybranySeans.getMiejsca().get(miejsce.charAt(0)).get(Integer.parseInt(miejsce.substring(1)))) {
                            miejscaRezerwacja.add(miejsce);
                        } else {
                            System.out.println("❌ Miejsce " + miejsce + " jest zajęte lub nie istnieje. Wybierz inne.");
                            i--; // Cofamy iterację, aby użytkownik podał poprawne miejsce
                        }
                    }

                    Klient nowyKlient = new Klient(nazwisko, imie, email, telefon, wybranySeans, miejscaRezerwacja);
                    if (system.zarezerwujBilet(nowyKlient)) {
                        System.out.println("✅ Rezerwacja udana!");
                    } else {
                        System.out.println("❌ Rezerwacja nie powiodła się!");
                    }
                    break;

                case 4:
                    system.wyswietlRezerwacje();
                    break;

                case 5:
                    System.out.println("📴 Zamykanie systemu...");
                    scanner.close();
                    return;

                default:
                    System.out.println("❌ Niepoprawna opcja, spróbuj ponownie.");
            }
        }
    }
}
