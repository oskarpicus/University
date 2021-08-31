#include "MD.h"
#include "IteratorMD.h"
#include <exception>
#include <iostream>

using namespace std;


MD::MD() { // teta(capacitate initiala)
	/* de adaugat */
	this->capacitate = CAPACITATE;
	this->prim = -1;
	this->ultim = -1;
	e = new TElem[capacitate];
	urm = new int[capacitate];
	prec = new int[capacitate];
	//initializare spatiu liber
	for (int i = 0; i < capacitate - 1; i++)
	{
		urm[i] = i + 1;
		prec[i] = -1;
	}
	urm[capacitate - 1] = -1;
	prec[capacitate - 1] = -1;
	primLiber = 0;
	nr_valori = 0;
}


void MD::adauga(TCheie c, TValoare v) { // teta(1) amortizat
	TElem e;
	e.first = c; e.second = v;
	int nod = creeazaNod(e); // teta(1) amortizat
	if (vid()) {
		//container vid - modificam prim si ultim
		prim = nod;
		ultim = nod;
		urm[prim] = -1;
		prec[prim] = -1;
	}
	else {
			//adaugam la sfarsit
			urm[ultim] = nod;
			prec[nod] = ultim;
			ultim = nod;
			urm[nod] = -1;
	}
	nr_valori++;
}


bool MD::sterge(TCheie c, TValoare v) { // O(nr_valori)
	/* de adaugat */
	TElem e;
	e.first = c; e.second = v;
	if (vid()) //container vid (vid - teta(1) )
		return false;

	int curent = prim;
	//parcurgem lista - cautam elementul de sters
	while (curent != -1 && this->e[curent] != e )
		curent = urm[curent];
	
	if(curent==-1) //nu s-a gasit elementul
		return false;
	
	TValoare veche = this->e[curent].second;

	if (curent == prim) //se sterge primul element
	{
		auto i = urm[prim];
		if(dim()!=1) // dim - teta(1)
			prec[urm[prim]] = -1; //urm[prim] devine prim, deci nu are predecesor
		dealoca(prim); //dealoca - teta(1)
		prim = i;
	}
	else if (curent == ultim) { //se sterge ultimul element
		auto i = prec[ultim];
		urm[prec[ultim]] = -1; //prec[ultim] devine ultim, deci nu are urmator
		dealoca(ultim); // dealoca - teta(1)
		ultim = i;
	}
	else {
		// elementul de sters se afla pe pozitia curent - modificam legaturile dintre prec[curent] si urm[curent]
		int anterior = prec[curent];
		urm[prec[curent]] = urm[curent];
		prec[urm[curent]] = anterior;
		dealoca(curent); // dealoca - teta(1)
	}
	nr_valori--;
	if (vid()) { // vid - teta(1)
		//container vid, modificam indicii ultim si prim, nu sunt valizi
		ultim = -1; prim = -1;
	}
	return true;
	//return veche;

	//caz favorabil: elementul de sters este e[prim] ==> teta(1)
	//caz defavorabil: elementul de sters nu exista ==> teta(nr_valori)
	//caz mediu - exista o probabilitate egala ca si e=pair<c,v> sa se afle pe pozitia 1,2,...,nr_valori 
	//==> T(nr_valori) = (1+2+...+nr_valori)/ nr_valori = nr_valori / 2 ==> teta(nr_valori)
}


vector<TValoare> MD::cauta(TCheie c) const { // O(nr_valori * complexitate(push_back) )
	/* de adaugat */
	vector<TValoare> v;
	int q = ultim;
	while (q != -1)
	{
		if (this->e[q].first == c)
			v.push_back(this->e[q].second); 
		//else if (!v.empty()) 
		//	break;
		q=prec[q];
	}
	return v;

	//caz favorabil - cheia nu se gaseste, deci nu se face niciun push_back, doar se parcurge ==> teta(nr_valori)
	//caz devavorabil -toate elementele au cheia c ==> T(nr_valori) = suma de la 1 la nr_valori din complexitate(push_back) 
	//==> teta(nr_valori * complexitate(push_back) )
	//caz mediu - daca cheia c apare de k ori, 0<k<nr_valori in dictionar ==> T(nr_valori) = k * complexitate(push_back)
	//==> O(nr_valori * complexitate(push_back) )
}


int MD::dim() const { //teta(1)
	/* de adaugat */
	return nr_valori;
}


bool MD::vid() const { //teta(1)
	/* de adaugat */
	return this->prim == -1;
}

IteratorMD MD::iterator() const { //teta(1)
	return IteratorMD(*this);
}


MD::~MD() { //teta(1)
	/* de adaugat */
	delete[] this->e;
	delete[] this->urm;
	delete[] this->prec;
}

int MD::aloca() //teta(1)
{
	int i = this->primLiber;
	this->primLiber = this->urm[this->primLiber];
	return i;
}

void MD::dealoca(int i) //teta(1)
{
	urm[i] = primLiber;
	prec[i] = -1;
	primLiber = i;
}

int MD::creeazaNod(TElem e) { //teta(1) amortizat
	if (primLiber == -1) //nu mai este spatiu
		redimensionare(); // teta(1) amortizat
	int i = aloca(); //teta(1)
	this->e[i] = e;
	urm[i] = -1;
	prec[i] = -1;
	return i;
}


void MD::redimensionare() { //teta(1) amortizat
	TElem* nou_e = new TElem[capacitate * 2];
	int* nou_urm = new int[capacitate * 2];
	int* nou_prec = new int[capacitate * 2];
	for (int i = 0; i < capacitate; i++)
	{	//copiez datele
		nou_e[i] = e[i];
		nou_urm[i] = urm[i];
		nou_prec[i] = prec[i];
	}
	for (int i = capacitate; i < capacitate * 2 - 1; i++)
	{	//initializez spatiul liber
		nou_urm[i] = i+1;
		nou_prec[i] = -1;
	}
	nou_urm[capacitate * 2 - 1] = -1;
	nou_prec[capacitate * 2 - 1] = -1;
	//actualizez
	primLiber = capacitate;
	capacitate *= 2;
	delete[] e;
	delete[] urm;
	delete[] prec;
	e = nou_e;
	urm = nou_urm;
	prec = nou_prec;
}




/*
void MD::adauga(TCheie c, TValoare v) { // O(nr_valori)
	TElem e;
	e.first = c; e.second = v;
	int nod = creeazaNod(e); // teta(1) amortizat
	if (vid()) {
		//container vid - modificam prim si ultim
		prim = nod;
		ultim = nod;
		urm[prim] = -1;
		prec[prim] = -1;
	}
	else {
		//parcurgem pana gasim cheia, daca o gasim, o adaugam langa celelalte elemente cu aceeasi cheie
		int curent = prim;
		while (this->e[curent].first != c && urm[curent] != -1)
			curent = urm[curent];
		int precedent = prec[curent];
		if (curent == prim) //adaugam la inceput
		{
			urm[nod] = prim;
			prec[prim] = nod;
			prim = nod;
		}
		else if (urm[curent] != -1) {
			//am gasit cheia, adaugam intre curent si precedent
			urm[nod] = curent;
			urm[precedent] = nod;
			prec[nod] = precedent;
			prec[curent] = nod;
		}
		else {
			//adaugam la sfarsit
			urm[ultim] = nod;
			prec[nod] = ultim;
			ultim = nod;
			urm[nod] = -1;
		}
	}
	nr_valori++;

	//caz favorabil: cheia se gaseste in e[prim] ==> teta(1)
	//caz defavorabil: cheia nu se gaseste ==> se parcurg toate elementele ==> teta(nr_valori)
	//caz mediu - exista o probabilitate egala ca si c sa se afle pe pozitia 1,2,...,nr_valori
	//==> T(nr_valori) = (1+2+...+nr_valori)/ nr_valori = nr_valori / 2 ==> teta(nr_valori)
}
*/