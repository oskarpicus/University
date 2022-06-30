package domain;

import java.util.Date;
import java.util.List;

public class Spectacol implements Entity<Integer> {
    private int id;
    private Date data;
    private String titlu;
    private float pret;
    private List<Integer> locuriVandute;
    private float sold;
    private List<Vanzare> vanzari;

    public Spectacol(int id, Date data, String titlu, float pret, float sold) {
        this.id = id;
        this.data = data;
        this.titlu = titlu;
        this.pret = pret;
        this.sold = sold;
    }

    public Spectacol(int id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public List<Integer> getLocuriVandute() {
        return locuriVandute;
    }

    public void setLocuriVandute(List<Integer> locuriVandute) {
        this.locuriVandute = locuriVandute;
    }

    public float getSold() {
        return sold;
    }

    public void setSold(float sold) {
        this.sold = sold;
    }

    public List<Vanzare> getVanzari() {
        return vanzari;
    }

    public void setVanzari(List<Vanzare> vanzari) {
        this.vanzari = vanzari;
    }

    @Override
    public String toString() {
        return "Spectacol{" +
                "id=" + id +
                ", data=" + data +
                ", titlu='" + titlu + '\'' +
                ", pret=" + pret +
                ", locuriVandute=" + locuriVandute +
                ", sold=" + sold +
                '}';
    }
}
