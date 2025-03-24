import java.io.Serializable;
import java.util.Set;

public class Klient implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nazwisko;
    private String imie;
    private String email;
    private String telefon;
    private Seans seans;
    private Set<String> miejsca;

    public Klient(String nazwisko, String imie, String email, String telefon, Seans seans, Set<String> miejsca) {
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.email = email;
        this.telefon = telefon;
        this.seans = seans;
        this.miejsca = miejsca;
    }

    @Override
    public String toString() {
        return "Klient: " + imie + " " + nazwisko + ", Email: " + email + ", Telefon: " + telefon +
                ", Seans: " + seans + ", Miejsca: " + miejsca;
    }

    public Seans getSeans() {
        return seans;
    }

    public Set<String> getMiejsca() {
        return miejsca;
    }


}
