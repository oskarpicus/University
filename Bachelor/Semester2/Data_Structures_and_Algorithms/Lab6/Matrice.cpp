#include "Matrice.h"
#include<iostream>
#include <exception>

using namespace std;

//definim factorul de incarcare drept raportul alpha = nr_noduri / m ==> fiecare lista inlantuita are in medie alpha noduri

Matrice::Matrice(int nrLinii, int nrColoane) { // theta(CAPACITATE_INITIALA)
	/* de adaugat */
	if (nrLinii <= 0 || nrColoane <= 0)
		throw exception();

	//initializam matricea
	this->linii = nrLinii;
	this->coloane = nrColoane;

	//initializam tabela de dispersie
	this->m = CAPACITATE_INITIALA;
	this->nr_noduri = 0;
	this->l = new Nod * [m];
	for (int i = 0; i < m; i++)
		l[i] = nullptr; //liste vide (toate elementele sunt nule)
}


int Matrice::nrLinii() const { //theta(1)
	/* de adaugat */
	return this->linii;
}


int Matrice::nrColoane() const { //theta(1)
	/* de adaugat */
	return this->coloane;
}


TElem Matrice::element(int i, int j) const { // complexitate generala: O(alpha)
	/* de adaugat */
	if (i < 0 || j < 0 || i >= this->linii || j >= this->coloane)
		throw std::exception(); // (i,j) nu este o pozitie valida
	int indice = d(i,j); //unde vom cauta elementul de pe linia i si coloana j
	Nod* curent = this->l[indice]; //reprezinta capul listei inlantuite

	while (curent != nullptr && (curent->coloana !=j || curent->linie!=i)) //parcurgem pana mai avem noduri si nu am gasit nodul ce contine i si j
		curent = curent->urm;

	if (curent == nullptr) //nu am gasit nodul ==> are valoare nula
		return NULL_TELEMENT;
	else
		return curent->e;

	//caz favorabil - elementul cautat se afla in capul listei sale inlantuite sau datele de intrare sunt incorecte ==> theta(1)
	//caz defavorabil - elementul are valoarea 0, deci nu a fost creat un nod pentru acesta ==> theta(alpha)
	//caz mediu - elementul se poate afla pe una dintre pozitiile 1,2,..,alpha cu aceeasi probabilitate
	// ==> nr. pasi efectuati = (1+2+...+alpha)/alpha=alpha/2 ==> theta(alpha)
}



TElem Matrice::modifica(int i, int j, TElem e) { //complexitate generala: O(alpha)
	/* de adaugat */
	if (i < 0 || j < 0 || i >= this->linii || j >= this->coloane)
		throw std::exception(); // (i,j) nu este o pozitie valida

	if (nr_noduri / m >= NR_NODURI_PER_LISTA)
		resize(); //tetha(1) amortizat

	auto indice = d(i,j);
	Nod* curent = this->l[indice];
	if (curent == nullptr && e!=NULL_TELEMENT) //lista este vida si elementul nenul, adaugam la inceput
	{
		Nod* nod = new Nod{ e,i,j,nullptr };
		l[indice]=nod;
		nr_noduri++;
		return NULL_TELEMENT;
	}
	else {
		//parcurgem pana gasim nodul cu linia i si coloana j
		auto prev = curent;
		while (curent != nullptr && (curent->coloana!=j || curent->linie != i))
		{
			prev = curent;
			curent = curent->urm;
		}


		if (curent == nullptr && e!=NULL_TELEMENT) //am ajuns la finalul listei si nu am gasit, cream un nou nod daca elementul este nenul
			{
				Nod* nod = new Nod{ e,i,j,nullptr };
				prev->urm = nod;
				nr_noduri++;
				return NULL_TELEMENT;
			}
		else { //modificam valoarea veche si o returnam
			if (curent == nullptr) //inseamna ca e==NULL_TELEMENT
				return NULL_TELEMENT;
			auto veche = curent->e;
			curent->e=e;
			if (e == NULL_TELEMENT) //stergem nodul
			{
				if (l[indice] != curent) //nu este primul nod
					prev->urm = curent->urm;
				else
					l[indice] = curent->urm;
				delete curent;
				nr_noduri--;
			}
			return veche;
		}
	}

	//caz favorabil - datele de intrare sunt incorecte sau lista inlantuita aferenta elementului este vida
	//sau elementul este in capul listei sale inlantuite ==> theta(1) amortizat
	//caz defavorabil - elementul cautat nu se afla in lista sa inlantuita, deci trebuie creat un nod pentru acesta ==> theta(alpha)
	//caz mediu - theta(alpha)
}

Matrice::~Matrice() { //theta(nr_noduri)

	for (int i = 0; i < m; i++) {
		Nod* curent = l[i];
		Nod* prev = curent;
		while (curent != nullptr) {
			prev = curent;
			curent = curent->urm;
			delete prev;
		}
	}
	delete[] l;
}

bool Matrice::prim(int n) { // O(n)
	if (n <= 1) return false;
	for (int i = 2; i <= n/2; i++)
		if (n % i == 0)
			return false;
	return true;
}

void Matrice::resize() {

	int veche = m;
	m++;
	while (prim(m) == false) //obtin urmatorul numar prim
		m++;

	Nod** l_nou = new Nod * [m];

	/*Nod** l_nou = new Nod * [m * 2];
	int veche = m;
	m *= 2;*/

	//initializez lista noua
	for (int i = 0; i < m; i++)
		l_nou[i] = nullptr;

	//parcurg lista veche si mut elementele
	for (int i = 0; i < veche; i++)
	{
		Nod* curent = l[i];
		//parcurg lista inlantuita de la l[i]
		while (curent != nullptr) {
			Nod* anterior = curent;

			int pozitie = d(curent->linie,curent->coloana); //unde va fi inserat nodul in lista noua, obtinut cu functia de dispersie (redispersare)
			Nod* nod_nou = new Nod(curent->e,curent->linie,curent->coloana, nullptr);
			//inseram noul nod la inceputul listei l[pozitie]
			nod_nou->urm = l_nou[pozitie]; 
			l_nou[pozitie] = nod_nou;

			curent = curent->urm; //trecem mai departe
			delete anterior;
		}
	}

	//actualizam
	delete[] l;
	l = l_nou;
}

//se converteste numarul ij in baza 128
int Matrice::hash_code(int l,int c) const{ //theta(1)
	return abs(l * 128 + c);
}


int Matrice::d(int l,int c) const { //theta(1)
	return hash_code(l,c) % m;
}

TElem Matrice::sumaDeasupraDiagonalaSecundara() {

	//stocam in care locatii putem avea elemente deasupra diagonalei principale
	//se initializeaza cu 0
	int* vf = new int[this->m]();

	for (int i = 0; i < this->linii; i++)
		for (int j = 0; j < this->coloane; j++)
			if (i + j < this->linii - 1)
				vf[d(i, j)]++;

	TElem suma = NULL_TELEMENT;
	for (int i = 0; i < m; i++) {
		if (vf[i] <= 0)
			continue;
		Nod* curent = this->l[i];
		while (curent != nullptr) {
			if (curent->linie + curent->coloana < this->linii - 1)
				suma += curent->e;
			curent = curent->urm;
		}
	}

	delete[] vf;
	return suma;

	//best case - nu exista elemente nenule deasupra diagonalei secundare ==> theta(nr_linii*nr_coloane)
	//worst case - fiecare lista are elemente deasupra diagonalei secundare ==> theta(nr_linii*nr_coloane) + theta(m* nr_noduri / m) ( theta(nr_noduri) ) = theta(nr_linii*nr_coloane+nr_noduri)
	//complexitate generala: O (nr_linii*nr_coloane + nr_noduri)
}
