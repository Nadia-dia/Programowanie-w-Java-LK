import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SystemRezerwacji {
    private List<Klient> klienci = new ArrayList<>();
    private List<Seans> seanse = new ArrayList<>();
    private static final String PLIK_KLIENCI = "klienci.dat";
    private static final String PLIK_SEANSE = "seanse.dat";

    public SystemRezerwacji() {
        wczytajDane();
    }

    public void dodajSeans(Seans seans) {
        getSeanse().add(seans);
        zapiszDane();
    }

    public boolean zarezerwujBilet(Klient klient) {
        for (Seans seans : getSeanse()) {
            if (seans.equals(klient.getSeans())) {
                //rezerwujMiejsca() z klasy Seans, sprawdza dostępność miejsc, zwraca trrue, jeśli są wolne
                if (seans.rezerwujMiejsca(klient.getMiejsca())) {
                    klienci.add(klient);
                    zapiszDane();
                    return true;
                } else {
                    System.out.println("Nie udało się zarezerwować wszystkich miejsc – niektóre są już zajęte.");
                    return false;
                }
            }
        }
        System.out.println("Nie znaleziono podanego seansu.");
        return false;
    }

    public void wyswietlRezerwacje() {
        wczytajDane();
        System.out.println("\nLista rezerwacji:");
        for (Klient klient : klienci) {
            System.out.println(klient);
        }
    }

    public void wyswietlSeanse() {
        wczytajDane();
        System.out.println("\nDostępne seanse:");
        for (Seans seans : getSeanse()) {
            System.out.println(seans);
        }
    }

    public void zapiszDane() {
        try (ObjectOutputStream outKlienci = new ObjectOutputStream(new FileOutputStream(PLIK_KLIENCI));
             ObjectOutputStream outSeanse = new ObjectOutputStream(new FileOutputStream(PLIK_SEANSE))) {
            outKlienci.writeObject(klienci);
            outSeanse.writeObject(getSeanse());
        } catch (IOException e) {
            System.out.println("Błąd zapisu do pliku: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void wczytajDane() {
        File plikKlienci = new File(PLIK_KLIENCI);
        if (plikKlienci.exists()) {
            try (ObjectInputStream inKlienci = new ObjectInputStream(new FileInputStream(PLIK_KLIENCI))) {
                klienci = (List<Klient>) inKlienci.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Błąd odczytu klientów: " + e.getMessage());
            }
        }

        File plikSeanse = new File(PLIK_SEANSE);
        if (plikSeanse.exists()) {
            try (ObjectInputStream inSeanse = new ObjectInputStream(new FileInputStream(PLIK_SEANSE))) {
                setSeanse((List<Seans>) inSeanse.readObject());
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Błąd odczytu seansów: " + e.getMessage());
            }
        }
    }

    public List<Seans> getSeanse() {
        return seanse;
    }

    public void setSeanse(List<Seans> seanse) {
        this.seanse = seanse;
    }
}