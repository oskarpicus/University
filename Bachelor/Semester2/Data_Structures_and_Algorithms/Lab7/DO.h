#pragma once

typedef int TCheie;
typedef int TValoare;

#define NULL_TVALOARE -1
#define CAPACITATE_INITIALA 10

#include<vector>
using namespace std;
#include <utility>
typedef std::pair<TCheie, TValoare> TElem;

class Iterator;

typedef bool(*Relatie)(TCheie, TCheie);

class DO {
	friend class Iterator;
private:
	/* aici e reprezentarea */

	int radacina, primLiber,capacitate,nr_noduri;
	TElem* element; //tabloul cu informatii utile
	int* stanga; //va reprezenta si lista inlantuita a spatiului liber
	int* dreapta;
	Relatie r;

	//se sterge prima valoare din lista inlantuita a spatiului liber (primLiber)
	//pre: primliber != -1
	//post: aloca=primliber, primliber = stanga[primliber]
	int aloca(); //teta(1)

	//se adauga (in fata) o valoare din lista inlantuita a spatiului liber (inainte de primLiber)
	//pre: i: int, element[i] ocupat
	//post: se dealoca nodul cu indice i, primliber=i
	void dealoca(int i); //teta(1)

	//creeaza un nod in lista inlantuita unde se memoreaza informatia utila e
	//pre: e: TElem
	//post: i: int, reprezinta un nod in lista a.i. informatia utlia = e
	int creeazaNod(TElem e); //teta(1) amortizat

	//mareste capacitatea vectorilor
	//pre: adevarat
	//post: capacitate = capacitate * 2 (se dubleaza capacitatea vectorilor)
	void redimensionare(); //teta(1) amortizat

	/*
	Functie pentru stergerea recursiva a unui nod cu o anumita cheie dintr-un arbore
	pre: p: int, reprezinta un nod, e: TCheie, cheia de sters,
	valoare: TValoare&, unde se va memora valoarea nodului care va fi sters, found: bool&, indicator daca am gasit nodul cu cheia e
	post: p: int, radacina arborelui rezultat a.i. se sterge nodul cu cheia e, iar inaltimea difera cu maxim o unitate
	*/
	int sterge_rec(int p, TCheie e,TValoare& valoare, bool& found);

	/*
	Functie pentru determinarea nodului cu informatie minima (in raport cu o relatie) dintr-un arbore
	pre: p: int, reprezinta un nod
	post: nod: int a.i. informatia utila in nod este minima in arborele de radacina p
	*/
	int minim(int p);


public:


	// constructorul implicit al dictionarului
	DO(Relatie r);


	// adauga o pereche (cheie, valoare) in dictionar
	//daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
	// daca nu exista cheia, adauga perechea si returneaza null: NULL_TVALOARE
	TValoare adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null: NULL_TVALOARE
	TValoare cauta(TCheie c) const;


	//sterge o cheie si returneaza valoarea asociata (daca exista) sau null: NULL_TVALOARE
	TValoare sterge(TCheie c);

	//returneaza numarul de perechi (cheie, valoare) din dictionar
	int dim() const;

	//verifica daca dictionarul e vid
	bool vid() const;

	// se returneaza iterator pe dictionar
	// iteratorul va returna perechile in ordine dupa relatia de ordine (pe cheie)
	Iterator iterator() const;

	vector<TCheie> multimeaCheilor() const;

	// destructorul dictionarului
	~DO();

};