#ifndef LISTA_H
#define LISTA_H


//tip de data generic (pentru moment este intreg)
typedef int TElem;

//referire a structurii Nod;
struct Nod;

//se defineste tipul PNod ca fiind adresa unui Nod
typedef Nod* PNod;

typedef struct Nod {
	TElem e;
	PNod urm;
};

typedef struct {
	//prim este adresa primului Nod din lista
	PNod _prim;
} Lista;


//operatii pe lista - INTERFATA

//crearea unei liste din valori citite pana la 0
Lista creare();

//tiparirea elementelor unei liste
void tipar(Lista l);

// destructorul listei
void distruge(Lista l);

//testeaza egalitatea dintre 2 liste
bool egalitate(Lista l, Lista k);

//determina intersectia dintre 2 multimi
Lista intersectie(Lista l, Lista k);

//verifica daca un element apare in lista
bool apare(Lista l, TElem el);

//adauga un nou element la inceputul unei liste
void adauga_inceput(Lista& l, TElem el);

//obtinem sublista, incepand cu al doilea element al lui l
Lista sublista(Lista l);

//functie pentru determinarea faptului ca lista este vida sau nu
bool eVida(Lista l);

//functie pentru accesarea primului element al unei liste
TElem primElement(Lista l);

#endif