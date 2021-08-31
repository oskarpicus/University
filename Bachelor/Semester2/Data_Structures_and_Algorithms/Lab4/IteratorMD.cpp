#include "IteratorMD.h"
#include "MD.h"

using namespace std;

IteratorMD::IteratorMD(const MD& _md) : md(_md) { //teta(1)
	/* de adaugat */
	this->curent = md.prim;
}

TElem IteratorMD::element() const { //teta(1)
	/* de adaugat */
	if (!valid())
		throw std::exception();
	return md.e[curent];
}

bool IteratorMD::valid() const { //teta(1)
	/* de adaugat */
	return this->curent != -1;
}

void IteratorMD::urmator() { //teta(1)
	/* de adaugat */
	if (!valid())
		throw std::exception();
	this->curent = md.urm[curent];
}

void IteratorMD::precedent() { //teta(1)
	if (!valid()) //daca nu era deja valid iteratorul
		throw std::exception();
	this->curent = md.prec[curent];
	//cum lista de precedenti este initializata cu -1, va fi automat invalid daca era pozitionat pe primul element
	//caz favorabil = caz defavorabil
}

void IteratorMD::prim() { //teta(1)
	/* de adaugat */
	this->curent = md.prim;
}
