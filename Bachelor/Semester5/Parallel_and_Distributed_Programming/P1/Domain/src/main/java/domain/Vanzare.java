package domain;

import java.util.Date;
import java.util.List;

public class Vanzare implements Entity<Integer> {
    private int id;
    private Spectacol spectacol;
    private Date data;
    private int nrBileteVandute;
    private List<Integer> listaLocuriVandute;
    private float suma;

    public Vanzare(int id, Date data, int nrBileteVandute, float suma, Spectacol spectacol) {
        this.id = id;
        this.data = data;
        this.nrBileteVandute = nrBileteVandute;
        this.suma = suma;
        this.spectacol = spectacol;
    }

    public Vanzare(Spectacol spectacol, Date data, int nrBileteVandute, List<Integer> listaLocuriVandute, float suma) {
        this.spectacol = spectacol;
        this.data = data;
        this.nrBileteVandute = nrBileteVandute;
        this.listaLocuriVandute = listaLocuriVandute;
        this.suma = suma;
    }

    public Spectacol getSpectacol() {
        return spectacol;
    }

    public void setSpectacol(Spectacol spectacol) {
        this.spectacol = spectacol;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getNrBileteVandute() {
        return nrBileteVandute;
    }

    public void setNrBileteVandute(int nrBileteVandute) {
        this.nrBileteVandute = nrBileteVandute;
    }

    public List<Integer> getListaLocuriVandute() {
        return listaLocuriVandute;
    }

    public void setListaLocuriVandute(List<Integer> listaLocuriVandute) {
        this.listaLocuriVandute = listaLocuriVandute;
    }

    public float getSuma() {
        return suma;
    }

    public void setSuma(float suma) {
        this.suma = suma;
    }

    @Override
    public String toString() {
        return "Vanzare{" +
                "spectacol=" + spectacol +
                ", data=" + data +
                ", nrBileteVandute=" + nrBileteVandute +
                ", listaLocuriVandute=" + listaLocuriVandute +
                ", suma=" + suma +
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
