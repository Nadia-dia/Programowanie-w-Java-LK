import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Seans implements Serializable {
    private String tytul;
    private String dzien;
    private String godzina;
    private int ograniczeniaWiekowe;
    private HashMap<Character, HashMap<Integer, Boolean>> miejsca;

    public Seans(String tytul, String dzien, String godzina, int ograniczeniaWiekowe, int liczbaRzedow, int miejscaWRzedzie) {
        this.tytul = tytul;
        this.dzien = dzien;
        this.godzina = godzina;
        this.ograniczeniaWiekowe = ograniczeniaWiekowe;
        this.miejsca = new HashMap<>();

        for (char rzad = 'A'; rzad < 'A' + liczbaRzedow; rzad++) {
            HashMap<Integer, Boolean> miejscaWRzedzieMap = new HashMap<>();
            for (int miejsce = 1; miejsce <= miejscaWRzedzie; miejsce++) {
                miejscaWRzedzieMap.put(miejsce, false); // Wszystkie miejsca są wolne, czyli false
            }
            miejsca.put(rzad, miejscaWRzedzieMap);
        }
    }

    public boolean rezerwujMiejsca(Set<String> wybraneMiejsca) {
        Set<String> zajete = new HashSet<>();

        for (String miejsce : wybraneMiejsca) {
            char rzad = miejsce.charAt(0); // Pierwszy znak to rząd (np. 'A', 'B', 'C')
            int nrMiejsca = Integer.parseInt(miejsce.substring(1)); // Reszta to numer miejsca (np. 1, 2, 3...)

            if (miejsca.containsKey(rzad) && miejsca.get(rzad).containsKey(nrMiejsca)) {
                if (miejsca.get(rzad).get(nrMiejsca)) {
                    zajete.add(miejsce); // Jeśli miejsce jest już zajęte, dodajemy je do listy zajętych
                }
            } else {
                zajete.add(miejsce); // Jeśli rząd lub miejsce nie istnieje, dodajemy je do listy zajętych
            }
        }

        if (!zajete.isEmpty()) {
            System.out.println("Niektóre miejsca są już zajęte: " + zajete);
            return false;
        }

        for (String miejsce : wybraneMiejsca) {
            char rzad = miejsce.charAt(0);
            int nrMiejsca = Integer.parseInt(miejsce.substring(1));
            miejsca.get(rzad).put(nrMiejsca, true);
        }

        return true;
    }

    @Override
    public String toString() {
        return "Seans: " + tytul + " | " + dzien + " | " + godzina + " | Wiek: " + ograniczeniaWiekowe;
    }

    public HashMap<Character, HashMap<Integer, Boolean>> getMiejsca() {

        return miejsca;
    }
}
