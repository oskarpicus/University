package model;

import utils.interfaces.Updateable;

import java.util.Objects;

public class Monomial implements Comparable<Monomial>, Updateable<Monomial> {
    private int coefficient;
    private int exponential;

    public static final Monomial INVALID_MONOMIAL = new Monomial(0, -1);

    public Monomial(int coefficient, int exponential) {
        this.coefficient = coefficient;
        this.exponential = exponential;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getExponential() {
        return exponential;
    }

    public void setExponential(int exponential) {
        this.exponential = exponential;
    }

    @Override
    public int compareTo(Monomial o) {
        return Integer.compare(this.getExponential(), o.getExponential());
    }

    @Override
    public String toString() {
        return "Monomial{" +
                "coefficient=" + coefficient +
                ", exponential=" + exponential +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monomial monomial = (Monomial) o;
        return exponential == monomial.exponential;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exponential);
    }

    @Override
    public void update(Monomial element) {
        this.setCoefficient(element.getCoefficient() + this.getCoefficient());
    }
}
