package domain;

import java.util.List;

public class Sala implements Entity<Integer> {
    private int id;
    private int nrLocuri;
    private List<Spectacol> spectacole;

    public Sala(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    public int getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    public List<Spectacol> getSpectacole() {
        return spectacole;
    }

    public void setSpectacole(List<Spectacol> spectacole) {
        this.spectacole = spectacole;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "nrLocuri=" + nrLocuri +
                ", spectacole=" + spectacole +
                '}';
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
