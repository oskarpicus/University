#pragma once
#include "cheltuiala.h"
typedef struct {
	Cheltuiala* elemente;
	int dim;
	int capacitate;
}Vector;

/*
Metoda pentru crearea unei noi liste de tip Vector in care vor fi stocate cheltuielile
pre: adevarat
post: se creeaza o lista vida
*/
Vector creeaza_lista();

/*
Metoda pentru dealocarea spatiului de memorare ocupat de lista
pre: lista: Vector
post: se distruge lista
*/
void distruge_lista(Vector* lista);


/*
Metoda pentru accesarea dimensiunii unui element de tip Vector
pre: lista: Vector
post: lista.dim
*/
int dimensiune(Vector lista);

/*
Metoda pentru adaugarea la sfarsit a unui element
pre: lista: Vector, cheltuiala: Cheltuiala
post: append=0, daca cheltuiala apartine lista.elemente, altfel, lista'.dim=lista.dim+1, lista'[-1]=cheltuiala
daca lista.dim==lista.capacitate ==> redimensionare
*/
void append(Vector* lista,Cheltuiala cheltuiala);

/*
Metoda pentru modificarea unei cheltuieli
pre: lista: Vector*, c1,c2: Cheltuiala
post: modificare=0, daca c1 nu se gaseste in lista.elemente
altfel, modificare=1 si in lista.elemente, unde apare c1 va aparea c2
*/
int modificare(Vector* lista, Cheltuiala c1, Cheltuiala c2);

/*
Metoda pentru stergerea unei cheltuieli
pre: lista: Vector*, c: Cheltuiala
post: stergere=0, daca c nu se gaseste in lista.elemente
altfel, stergere=1 si c nu mai apare in lista.elemente
*/
int stergere(Vector* lista, Cheltuiala c);

/*
Creeaza o copie (deepcopy) a unei liste de cheltuieli
pre: l: Vector*
post: l'=l
*/
Vector copie_lista(Vector* l);


void test_creeaza_lista();

void test_distruge_lista();

void test_element();

void test_modifica_lista();

void test_dimensiune();

void test_append();

void test_modificare();

void test_stergere();

void test_copie_lista();

