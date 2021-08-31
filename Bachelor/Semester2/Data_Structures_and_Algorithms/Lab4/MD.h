#pragma once
#include<vector>
#include<utility>
#define CAPACITATE 1

using namespace std;

typedef int TCheie;
typedef int TValoare;

typedef std::pair<TCheie, TValoare> TElem;

class IteratorMD;

class MD
{
	friend class IteratorMD;

private:
	/* aici e reprezentarea */

	//se sterge prima valoare din lista inlantuita a spatiului liber (primLiber)
	//pre: primliber != -1
	//post: aloca=primliber, primliber = urm[primliber]
	int aloca(); //teta(1)

	//se adauga (in fata) o valoare din lista inlantuita a spatiului liber (inainte de primLiber)
	//pre: i: int, e[i] ocupat
	//post: se dealoca nodul cu indice i, primliber=i
	void dealoca(int i); //teta(1)

	//creeaza un nod in lista inlantuita unde se memorieaza informatia utila e
	//pre: e: TElem
	//post: i: int, reprezinta un nod in lista a.i. informatia utlia = e
	int creeazaNod(TElem e); //teta(1) amortizat

	//mareste capacitatea vectorilor
	//pre: adevarat
	//post: capacitate = capacitate * 2 (se dubleaza capacitatea vectorilor)
	void redimensionare(); //teta(1) amortizat

	int capacitate;
	int prim, primLiber, ultim;
	TElem* e;
	int* urm;
	int* prec;
	int nr_valori;

public:
	// constructorul implicit al MultiDictionarului
	MD(); // teta(capacitate initiala)

	// adauga o pereche (cheie, valoare) in MD	
	void adauga(TCheie c, TValoare v); // O(nr_valori)

	//cauta o cheie si returneaza vectorul de valori asociate
	vector<TValoare> cauta(TCheie c) const;// O(nr_valori)

	//sterge o cheie si o valoare 
	//returneaza adevarat daca s-a gasit cheia si valoarea de sters
	bool sterge(TCheie c, TValoare v); // O(nr_valori)

	//returneaza numarul de perechi (cheie, valoare) din MD 
	int dim() const; //teta(1)

	//verifica daca MultiDictionarul e vid 
	bool vid() const; //teta(1)

	// se returneaza iterator pe MD
	IteratorMD iterator() const; //teta(1)

	// destructorul MultiDictionarului	
	~MD(); //teta(1)

};
