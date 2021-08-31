#include "Iterator.h"
#include "DO.h"

using namespace std;

//iterator in inordine

Iterator::Iterator(const DO& d) : dict(d) { //complexitate generala O(inaltime arbore)
	/* de adaugat */
	curent = dict.radacina;
	stiva_curent = 0;
	stiva = new int[dict.nr_noduri];
	while (curent != -1) { //adaugam ramura stanga
		stiva_push(curent);
		curent = dict.stanga[curent];
	}
	if (!stiva_empty()) //daca nu este vida
		curent = stiva_top();

	//caz favorabil: radacina este primul nod de iterat (deci nu are descendenti stangi) ==> theta(1)
	//caz defavorabil: ABC este degenerat (lant), fiecare nod este un descendent stang al radacinii ==> theta(inaltime arbore)
	//caz mediu: theta(inaltime arbore)
}

int Iterator::stiva_top() { //theta(1)
	return stiva[stiva_curent-1]; //extrag elementul din varful stivei
}

void Iterator::stiva_push(int nod) { //theta(1)
	//se adauga elementul la sfarsitul tabloului
	stiva[stiva_curent] = nod;
	stiva_curent++;
}

bool Iterator::stiva_empty() { //theta(1)
	return stiva_curent == 0;
}

void Iterator::stiva_pop() { //theta(1)
	stiva[stiva_curent-1]=-1; //marchez stergerea sa
	stiva_curent--;
}

Iterator::~Iterator() { //theta(1)
	delete[] stiva;
}

void Iterator::prim() { //complexitate generala: O(inaltime arbore)
	/* de adaugat */
	curent = dict.radacina;
	stiva_curent = 0;

	while (curent != -1) { //adaugam ramura stanga
		stiva_push(curent);
		curent = dict.stanga[curent];
	}
	if (!stiva_empty()) //daca nu este vida
		curent = stiva_top();
	else
		curent = -1;

	//caz favorabil: radacina este primul nod de iterat (deci nu are descendenti stangi) ==> theta(1)
	//caz defavorabil: ABC este degenerat (lant), fiecare nod este un descendent stang al radacinii ==> theta(inaltime arbore)
	//caz mediu: theta(inaltime arbore)
}

void Iterator::urmator() { //complexitate generala: O(inaltime arbore)
	/* de adaugat */
	if (!valid())
		throw exception();
	curent = this->stiva_top();
	stiva_pop();
	if (dict.dreapta[curent] != -1) {
		curent = dict.dreapta[curent]; //deplasez iteratorul
		while (curent != -1) {
			this->stiva_push(this->curent);
			this->curent = dict.stanga[curent];
		}
	}
	if (!this->stiva_empty()) 
		curent = stiva_top();
	else
		curent = -1;

	//caz favorabil: nodul curent nu are descendenti drepti ==> theta(1)
	//caz defavorabil: subarborele cu radacina drept descendentul drept al nodului curent este degenerat (pe stanga) ==> se adauga in stiva toate nodurile ==> theta(inaltime arbore)
	//caz mediu: theta(inaltime arbore)
}

bool Iterator::valid() const { //theta(1)
	/* de adaugat */
	return curent != -1;
}

TElem Iterator::element() const { //theta(1)
	/* de adaugat */
	if (!valid())
		throw exception();
	return dict.element[curent];
}

