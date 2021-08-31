#pragma once
#include "Dictionar.h"

class IteratorDictionar
{
	friend class Dictionar;
private:

	//constructorul primeste o referinta catre Container
	//iteratorul va referi primul element din container
	IteratorDictionar(const Dictionar& d);

	//contine o referinta catre containerul pe care il itereaza
	const Dictionar& dict;
	/* aici e reprezentarea specifica a iteratorului */
	Nod* curent; //retine pozitia curenta in interiorul dictionarului - adresa Nodului curent din lista asociata

public:

	//reseteaza pozitia iteratorului la inceputul containerului
	void prim(); //teta(1)

	//muta iteratorul in container
	// arunca exceptie daca iteratorul nu e valid
	void urmator(); //teta(1)

	//verifica daca iteratorul e valid (indica un element al containerului)
	bool valid() const; //teta(1)


	//returneaza valoarea elementului din container referit de iterator
	//arunca exceptie daca iteratorul nu e valid
	TElem element() const; //teta(1)
};