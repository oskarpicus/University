#include "lista.h"
#include <iostream>

using namespace std;

PNod creare_rec() {
    TElem x;
    cout << "x=";
    cin >> x;
    if (x == 0)
        return NULL;
    else {
        PNod p = new Nod();
        p->e = x;
        p->urm = creare_rec();
        return p;
    }
}

Lista creare() {
    Lista l;
    l._prim = creare_rec();
    return l;
}

void tipar_rec(PNod p) {
    if (p != NULL) {
        cout << p->e << " ";
        tipar_rec(p->urm);
    }
}

void tipar(Lista l) {
    tipar_rec(l._prim);
}

void distruge_rec(PNod p) {
    if (p != NULL) {
        distruge_rec(p->urm);
        delete p;
    }
}

void distruge(Lista l) {
    //se elibereaza memoria alocata nodurilor listei
    distruge_rec(l._prim);
}

void adauga_inceput(Lista& l, TElem el) {
    auto nou = new Nod();
    nou->e = el;
    nou->urm = l._prim;
    l._prim = nou;
}

Lista sublista(Lista l) {
    Lista rez;
    rez._prim = l._prim->urm;
    return rez;
}

bool eVida(Lista l) {
    return l._prim == nullptr;
}

TElem primElement(Lista l) {
    return l._prim->e;
}

bool egalitate_rec(Lista l, Lista k) {
    if (eVida(l) && eVida(k))
        return true;
    if ((eVida(l) && !eVida(k)) || (!eVida(l) && eVida(k)))
        return false;
    if (primElement(l) != primElement(k))
        return false;
    return egalitate_rec(sublista(l), sublista(k));
}

bool egalitate(Lista l, Lista k) {
    //return egalitate_rec(l._prim, k._prim);
    return egalitate_rec(l, k);
}


PNod intersectie_rec(Lista l, Lista k,Lista& rez) {
    if (eVida(k))
        return nullptr;
    if (apare(l, primElement(k))) {
        adauga_inceput(rez, primElement(k));
    }
    return intersectie_rec(l, sublista(k), rez);
}

Lista intersectie(Lista l, Lista k) {
    Lista rez;
    rez._prim = nullptr;
    //intersectie_rec(l, k._prim,rez);
    intersectie_rec(l, k, rez);
    return rez;
}

bool apare_rec(Lista l,TElem el) {
    if (eVida(l))
        return false;
    if (primElement(l) == el)
        return true;
    return apare_rec(sublista(l), el);
}

bool apare(Lista l, TElem el) {
    return apare_rec(l, el);
}



/*bool egalitate_rec(PNod p_l, PNod p_k) {
    if (p_l == nullptr && p_k == nullptr)
        return true;
    if ((p_l == nullptr && p_k != nullptr) || (p_l != nullptr && p_k == nullptr))
        return false;
    if (p_l->e != p_k->e)
        return false;
    return egalitate_rec(p_l->urm, p_k->urm);
}*/


/*PNod intersectie_rec(Lista l, PNod p_k, Lista& rez) {
    if (p_k == nullptr)
        return nullptr;
    if (apare(l, p_k->e))
    {
        adauga_inceput(rez, p_k->e);
        return intersectie_rec(l, p_k->urm, rez);
    }
    return intersectie_rec(l, p_k->urm,rez);
}*/