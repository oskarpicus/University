#pragma once
#include "Triplet.h"
typedef int TElem;
#define NR_NODURI_PER_LISTA 5
#define NULL_TELEMENT 0
#define CAPACITATE_INITIALA 13

class Nod {
private:
	int linie, coloana;
	TElem e; //informatia utila
	Nod* urm; //urmatorul nod
public:
	friend class Matrice;
	//constructor
	Nod(TElem e, int l, int c, Nod* urm) :e{ e }, linie{ l }, coloana{ c }, urm{ urm }{} //teta(1)

	//obtin informatia utila de la nod
	TElem element() const{
		return e;
	}  //teta(1)

	//trec la urmatorul nod
	Nod* urmator() const {
		return urm;
	} //teta(1)
};

class Matrice {

private:
	/* aici e reprezentarea */

	int linii, coloane;
	int m; //capacitatea
	int nr_noduri;
	Nod** l; //tablou cu pointeri la Nod

	//functie care ne da hashcode-ul unui element
	int hash_code(int l,int c) const;

	//functie de dispersie
	int d(int l,int c) const;

	void resize();

	bool prim(int n);
	
public:

	//constructor
	//se arunca exceptie daca nrLinii<=0 sau nrColoane<=0
	Matrice(int nrLinii, int nrColoane);


	//destructor
	~Matrice();

	//returnare element de pe o linie si o coloana
	//se arunca exceptie daca (i,j) nu e pozitie valida in Matrice
	//indicii se considera incepand de la 0
	TElem element(int i, int j) const;


	// returnare numar linii
	int nrLinii() const;

	// returnare numar coloane
	int nrColoane() const;


	// modificare element de pe o linie si o coloana si returnarea vechii valori
	// se arunca exceptie daca (i,j) nu e o pozitie valida in Matrice
	TElem modifica(int i, int j, TElem);

	TElem sumaDeasupraDiagonalaSecundara();


};
