package model;

import utils.linkedList.LinkedList;
import utils.linkedList.Node;

import java.util.Iterator;

public class Polynomial {
    private final LinkedList<Monomial> monomials;

    public Polynomial(LinkedList<Monomial> monomials) {
        this.monomials = monomials;
    }

    public LinkedList<Monomial> getMonomials() {
        return monomials;
    }

    public void add(Polynomial other) {
        LinkedList<Monomial> monomialsOther = other.getMonomials();
        Iterator<Node<Monomial>> iterator = monomialsOther.getIterator();
        while (iterator.hasNext()) {
            Node<Monomial> current = iterator.next();
            Node<Monomial> findResult = this.monomials.find(current.getElement());
            if (findResult == null) {
                this.monomials.add(current.getElement());
            } else {
                Monomial monomial = findResult.getElement();
                monomial.setCoefficient(monomial.getCoefficient() + current.getElement().getCoefficient());
            }
        }
    }
}
