#pragma once

#include "DO.h"
#include<exception>
using namespace std;

class Iterator { //iterator in inordine
	friend class DO;
private:
	//constructorul primeste o referinta catre Container
	//iteratorul va referi primul element din container
	Iterator(const DO& dictionar);

	//contine o referinta catre containerul pe care il itereaza
	const DO& dict;

	/* aici e reprezentarea specifica a iteratorului */
	int curent;
	int* stiva; //TAD Stiva reprezentat prin tablou unidimensional
	int stiva_curent;

	/*
	Functie pentru accesarea elementului din varful stivei
	pre: stiva nu este vida
	post: nod: int, se afla in varful stivei
	*/
	int stiva_top();

	/*
	Se sterge elementul din varful stivei
	pre: stiva nu este vida
	post: se sterge elementul din varful stivei
	stiva_curent' = stiva_curent - 1
	*/
	void stiva_pop();

	//se insereaza in varful stivei un element
	//pre: nod : int
	//post: stiva' = stiva + { nod }
	//stiva_curent' = stiva_curent + 1
	void stiva_push(int nod);

	/*
	Se verifica daca stiva este vida
	pre: adevarat
	post: true, daca stiva_curent=0, false, altfel
	*/
	bool stiva_empty();

public:

	//reseteaza pozitia iteratorului la inceputul containerului
	void prim();

	//muta iteratorul in container
	// arunca exceptie daca iteratorul nu e valid
	void urmator();

	//verifica daca iteratorul e valid (indica un element al containerului)
	bool valid() const;

	//returneaza valoarea elementului din container referit de iterator
	//arunca exceptie daca iteratorul nu e valid
	TElem element() const;

	//destructor
	~Iterator();
};
