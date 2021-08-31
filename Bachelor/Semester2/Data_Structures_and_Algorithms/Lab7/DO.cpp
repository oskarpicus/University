#include "Iterator.h"
#include "DO.h"
#include <iostream>

#include <exception>
using namespace std;

DO::DO(Relatie r) { //complexitate generala: theta(CAPACITATE_INITIALA)
	/* de adaugat */
	primLiber = 0;
	radacina = -1;
	nr_noduri = 0;
	this->r = r;

	//initializam tablourile
	capacitate = CAPACITATE_INITIALA;
	element = new TElem[capacitate];
	stanga = new int[capacitate];
	dreapta = new int[capacitate];
	for (int i = 0; i < capacitate; i++) {
		stanga[i] = i+1; //initializam spatiul liber
		dreapta[i] = -1; //echivalentul de nullptr
	}
	stanga[capacitate - 1] = -1;
}

//adauga o pereche (cheie, valoare) in dictionar
//daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
//daca nu exista cheia, adauga perechea si returneaza null
TValoare DO::adauga(TCheie c, TValoare v) { //complexitate generala: O(inaltime arbore)
	/* de adaugat */
	auto curent = radacina;
	TElem elem = std::make_pair(c, v);

	if (this->radacina == -1) //adaug in radacina
	{
		radacina = creeazaNod(elem); 
		nr_noduri++;
		return NULL_TVALOARE;
	}

	auto p = radacina, prec = radacina;
	while (p != -1 && element[p].first != elem.first) { //cautam nodul cu acea cheie
		prec = p;
		if (this->r(elem.first, element[p].first))
			p = stanga[p];
		else
			p = dreapta[p];
	}

	if (p == -1) { // nu am gasit cheia, alocam un nou nod
		p = creeazaNod(elem);
		if (this->r(elem.first, element[prec].first)) //decidem ce fel de descendent (stang/drept) va fi pentru parintele sau (prec)
			stanga[prec] = p;
		else
			dreapta[prec] = p;
		nr_noduri++;
		return NULL_TVALOARE;
	}

	//cheia exista deja, actualizam
	auto veche = element[p].second;
	element[p].second = elem.second;
	return veche;

	//caz favorabil: cheia a mai fost adaugata si se afla in radacina ==> theta(1)
	//caz defavorabil: cheia nu a mai fost adaugata, se parcurge intreaga inaltime a arborelui, de ex., daca este degenerat ==> theta(inaltime arbore)
	//caz mediu: theta(inaltime_arbore)
}

//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
TValoare DO::cauta(TCheie c) const { //complexitate generala: O(inaltime_arbore)
	/* de adaugat */

	auto p = radacina;
	if (vid())
		return NULL_TVALOARE;
	while (p != -1 && element[p].first != c) {
		if (this->r(c, element[p].first))
			p = stanga[p];
		else
			p = dreapta[p];
	}
	return p == -1 ? NULL_TVALOARE : element[p].second;

	//caz favorabil: cheia se afla in radacina ==> theta(1)
	//caz defavorabil: cheia nu exista, se pot parcurge toate nodurile, de ex., daca arborele este degenerat,
	//iar cheia cautat reprezinta un minim (daca e degenerat pe partea stang) / maxim (partea dreapta) ==> theta(inaltime_arbore)
	//caz mediu: theta(inaltime_arbore)
}

//sterge o cheie si returneaza valoarea asociata (daca exista) sau null
TValoare DO::sterge(TCheie c) { //complexitate generala: O(inaltime_arbore)
	/* de adaugat */
	TValoare valoare = NULL_TVALOARE;
	bool found = false,ok=false;

	if (this->element[radacina].first == c) //trebuie sterg din radacina
		ok = true;

	auto nod= sterge_rec(this->radacina, c,valoare,found );
	if(found==false) //nu l-a gasit
		return NULL_TVALOARE;
	nr_noduri--;
	if (ok == true) //actualizam radacina
		radacina = nod;
	return valoare;
}

int DO::sterge_rec(int p, TCheie c,TValoare& valoare, bool& found) { //complexitate generala: O(inaltime_arbore)
	if (p == -1) //arbore vid
		return -1;
	else {
		if (this->r(c, element[p].first) && c!=element[p].first) //se sterge din subarborele stang
		{
			stanga[p] = sterge_rec(stanga[p], c,valoare,found );
			return p;
		}
		else if (!this->r(c, element[p].first) && c != element[p].first) { //se sterge din subarborele drept
			dreapta[p] = sterge_rec(dreapta[p], c,valoare,found );
			return p;
		}
		else {
			//am ajuns la nodul care trebuie sters
			if (found == false)
			{
				valoare = element[p].second;
				found = true;
			}
			if (stanga[p] != -1 && dreapta[p] != -1) //are si subarbore stang si drept
			{
				int temp = minim(dreapta[p]);
				element[p] = element[temp]; //mutam cheia minima
				dreapta[p] = sterge_rec(dreapta[p], element[p].first,valoare,found );
				return p;
			}
			else {
				int temp = p, repl;
				if (stanga[p] == -1) //are doar subarbore drept
					repl = dreapta[p]; 
				else repl = stanga[p]; //are doar subarbore stang
				dealoca(temp);
				return repl;
			}
		}
	}

	//caz favorabil: cheia de sters se afla in radacina ==> theta(1)
	//caz defavorabil: nu exista nodul cu aceasta cheie si valoare, cautarea sa poate implica traversarea intregului arbore de ex. daca
	//este degenerat ==> theta(inaltime_arbore)
	//caz mediu: theta(inaltime_arbore)
}

//returneaza numarul de perechi (cheie, valoare) din dictionar
int DO::dim() const { //theta(1)
	/* de adaugat */
	return nr_noduri;
}

//verifica daca dictionarul e vid
bool DO::vid() const { //theta(1)
	/* de adaugat */
	return radacina == -1;
}

Iterator DO::iterator() const { //O(inaltime arbore)
	return  Iterator(*this);
}

DO::~DO() { //theta(1)
	/* de adaugat */
	delete[] element;
	delete[] stanga;
	delete[] dreapta;
}

int DO::aloca() { //theta(1)

	int i = this->primLiber;
	primLiber = stanga[primLiber]; //actualizam primliber
	return i;
}


void DO::dealoca(int i) { //theta(1)
	stanga[i] = primLiber; 
	primLiber = i; //actualizam lista spatiului liber
	dreapta[i] = -1;
}

int DO::creeazaNod(TElem e) { //theta(1) amortizat

	if (primLiber == -1) //nu mai este spatiu
		redimensionare();
	int i = aloca();
	element[i] = e;
	stanga[i] = -1;
	dreapta[i] = -1;
	return i;
}



void DO::redimensionare() { //complexitate generala: theta(1) amortizat
	auto nou_element = new TElem[capacitate * 2];
	auto nou_stang = new int[capacitate * 2];
	auto nou_drept = new int[capacitate * 2];

	//copiem elementele
	for (int i = 0; i < capacitate; i++) {
		nou_element[i] = element[i];
		nou_stang[i] = stanga[i];
		nou_drept[i] = dreapta[i];
	}

	//initializam spatiul liber
	for (int i = capacitate; i < capacitate * 2; i++) {
		nou_stang[i] = i + 1; 
		nou_drept[i] = -1;
	}
	nou_stang[capacitate * 2 - 1] = -1;
	//actualizam
	primLiber = capacitate;
	capacitate *= 2;
	delete[] element;
	delete[] stanga;
	delete[] dreapta;
	element = nou_element;
	stanga = nou_stang;
	dreapta = nou_drept;
}

int DO::minim(int p) { //complexitate generala: O(inaltime_arbore)
	while (stanga[p] != -1)
		p = stanga[p];
	return p;

	//caz favorabil: arborele cu radacina p nu are descendenti stangi (este degenerat, pe partea dreapta) ==> theta(1)
	//caz defavorabil: este degenerat pe partea stanga ==> se parcurg toate nodurile ==> theta(inaltime_arbore)
	//caz mediu: theta(inaltime_arbore)
}

vector<TCheie> DO::multimeaCheilor() const {
	vector<TCheie> v;
	int* stiva = new int[nr_noduri];
	int p = this->radacina;
	int stiva_curent = 0;
	while (p!=-1 || stiva_curent!=0) { //parcurgere in inordine
		while (p != -1) {
			//adaugam in stiva
			stiva[stiva_curent] = p;
			stiva_curent++;
			p = stanga[p];
		}
		p = stiva[stiva_curent-1];
		stiva_curent--; //stergem
		v.push_back(element[p].first);
		p = dreapta[p];
	}

	delete[] stiva;
	return v;
}

