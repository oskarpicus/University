#include "Dictionar.h"
#include <iostream>
#include "IteratorDictionar.h"

Nod::Nod(TElem e, Nod* urm) //teta(1)
{
	this->e = e;
	this->urm = urm;
}

TElem Nod::element() //teta(1)
{
	return this->e;
}

Nod* Nod::urmator() //teta(1)
{
	return this->urm;
}


Dictionar::Dictionar() { //teta(1)
	/* de adaugat */
	this->prim = NULL;
	this->nr_chei = 0;
}

Dictionar::~Dictionar() { //teta(nr_chei)
	/* de adaugat */
	//se elibereaza memoria alocata nodurilor listei
	while (prim != NULL)
	{
		Nod* p = prim;
		prim = prim->urm;
		delete p;
	}
}

TValoare Dictionar::adauga(TCheie c, TValoare v) { //complexitate generala: O(nr_chei)
	/* de adaugat */
	TElem e;
	e.first = c; e.second = v;
	Nod* nou = new Nod(e, NULL); //NULL pentru ca adaugam la sfarsit
	if (vid()) //daca dictionarul este vid
	{
		this->prim = nou;
		this->nr_chei++;
	}
	else {

		//caz mediu
		//exista  probabilitati egale ca TCheie c sa se gaseasca in nodul nr. 1,2,...,nr_chei
		//T(nr_chei)=(1+2+...+nr_chei)/nr_chei = (nr_chei+1)/2 apartine teta(nr_chei)

		//caz favorabil - teta(1)

		Nod* curent = this->prim;
		Nod* ultim=this->prim;
		while (curent!= NULL) //parcurg nodurile
		{
			if (curent->e.first == c) //daca exista cheia deja
			{
				delete nou; //nu mai avem nevoie de acest nod nou alocat
				TValoare veche = curent->e.second; 
				curent->e.second = v; //actualizez vechea valoare
				return veche; //returnez vechea valoare
			}
			ultim = curent;
			curent = curent->urm;
		}

		//caz defavorabil - teta(nr_chei)
		//cheia nu exista deja, adaugam nodul nou
		ultim->urm = nou;
		this->nr_chei++;
	}
	return NULL_TVALOARE;
}



//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
TValoare Dictionar::cauta(TCheie c) const { //complexitate generala: O(nr_chei)
	/* de adaugat */
	Nod* curent = this->prim;
	while (curent != NULL) //parcurg nodurile
	{
		if (curent->e.first == c)
			return curent->e.second;
		curent = curent->urm;
	}
	return NULL_TVALOARE;
	//caz fav: se gaseste in primul nod ==> teta(1)
	//caz defav: nu se gaseste ==> teta(nr_chei)
	//caz mediu: calcule analog ==> teta(nr_chei)
}


TValoare Dictionar::sterge(TCheie c) { //complexitate generala O(nr_chei)
	/* de adaugat */
	if (vid()) //daca dictionarul este vid
		return NULL_TVALOARE;
	if (this->prim->e.first == c) //daca se sterge primul element (caz favorabil, teta(1) )
	{
		TValoare val = this->prim->e.second;
		Nod* urmator = this->prim->urm;
		delete prim;
		this->prim = urmator; //se modifica prim
		this->nr_chei--;
		return val; //returnez valoarea asociata
	}
	//parcurgem pana gasim cheia
	Nod* q = this->prim,*k=this->prim;
	while (q != NULL && q->e.first != c)
	{
		k = q; 
		q = q->urm;
	}
	if(q==NULL) //nu s-a gasit cheia
		return NULL_TVALOARE;
	//am gasit cheia
	TValoare val = q->e.second;
	k->urm = q->urm;
	nr_chei--;
	delete q;
	return val;

	//caz defavorabil: nu se gaseste cheia ==> teta(nr_chei)
	//caz mediu: calcule analog ==> teta(nr_chei)
}


int Dictionar::dim() const { //teta(1)
	/* de adaugat */
	return this->nr_chei;
}

bool Dictionar::vid() const { //teta(1)
	/* de adaugat */
	return this->prim == NULL;
}


IteratorDictionar Dictionar::iterator() const { //teta(1)
	return  IteratorDictionar(*this);
}

TValoare Dictionar::ceaMaiFrecventaValoare() const { //complexitate generala O(nr_chei ^2)
	TValoare* valori = (TValoare*)malloc(nr_chei*sizeof(TValoare));
	int nr_valori = 0;
	int* frecv = (int*)malloc(nr_chei * sizeof(int));
	Nod* curent = prim;

	for (int i = 0; i < nr_chei; i++) //initializez vectorii
	{
		frecv[i] = 0; valori[i] = NULL_TVALOARE;
	}

	while (curent != NULL)
	{
		int i = 0,ok=0;
		TValoare val_curent = curent->e.second;
		for (i = 0; i < nr_valori; i++)
			if (valori[i] == val_curent)
			{
				frecv[i]++;
				ok = 1;
				break;
			}
		if (ok==0) //valoare noua
		{
			valori[nr_valori] = val_curent;
			frecv[nr_valori] = 1;
			nr_valori++; 
		}
		curent = curent->urm;
	}

	int maxim = -1;
	TValoare val = NULL_TVALOARE;

	for(int i=0;i<nr_valori;i++)
		if (frecv[i] > maxim)
		{
			maxim = frecv[i];
			val = valori[i];
		}
	free(valori);
	free(frecv);
	return val;

	//caz favorabil - elementul cu frecventa maxima e pe prima pozitie ==> teta(nr_chei)
	//caz devavorabil - este pe ultima pozitie ==> teta(nr_chei^2)
}