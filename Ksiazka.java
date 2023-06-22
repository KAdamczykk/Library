package mini;

import java.io.Serializable;

public class Ksiazka implements Serializable {
    protected String autor, rok, tytul;
    protected boolean wypozyczona = false;
    public Ksiazka(String autor, String tytul, String rok){
        this.autor = autor;
        this.rok = rok;
        this.tytul = tytul;
    }

    @Override
    public String toString() {
        return " " + autor + "  " + tytul + "  " + rok;
    }

    public String getAutor() {
        return autor;
    }
}
