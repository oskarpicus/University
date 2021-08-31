#pragma once
#pragma once

#define NULL_TVALOARE -1
typedef int TCheie;
typedef int TValoare;

class IteratorDictionar;

#include <utility>
typedef std::pair<TCheie, TValoare> TElem;


class Nod {

private:
	TElem e; //informatia utlia
	Nod* urm; //adresa urmatorului nod

public:
	friend class Dictionar;

	//constructor
	Nod(TElem e, Nod* urm); //teta(1)

	//obtin informatia utila de la nod
	TElem element(); //teta(1)

	//trec la urmatorul nod
	Nod* urmator(); //teta(1)
};


class Dictionar {
	friend class IteratorDictionar;

private:
	/* aici e reprezentarea */
	Nod* prim;
	int nr_chei;


public:

	// constructorul implicit al dictionarului
	Dictionar(); //teta(1)

	// adauga o pereche (cheie, valoare) in dictionar	
	//daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
	// daca nu exista cheia, adauga perechea si returneaza null: NULL_TVALOARE
	TValoare adauga(TCheie c, TValoare v); //complexitate generala: O(nr_chei)

	//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null: NULL_TVALOARE
	TValoare cauta(TCheie c) const; //complexitate generala: O(nr_chei)

	//sterge o cheie si returneaza valoarea asociata (daca exista) sau null: NULL_TVALOARE
	TValoare sterge(TCheie c); //complexitate generala O(n)

	//returneaza numarul de perechi (cheie, valoare) din dictionar 
	int dim() const; //teta(1)

	//verifica daca dictionarul e vid 
	bool vid() const; //teta(1)

	// se returneaza iterator pe dictionar
	IteratorDictionar iterator() const; //teta(1)

	TValoare ceaMaiFrecventaValoare() const;

	// destructorul dictionarului	
	~Dictionar(); //teta(nr_chei)

};

