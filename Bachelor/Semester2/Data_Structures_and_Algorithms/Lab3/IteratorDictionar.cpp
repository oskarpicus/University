#include "IteratorDictionar.h"
#include "Dictionar.h"

using namespace std;

IteratorDictionar::IteratorDictionar(const Dictionar& d) : dict(d) { //teta(1)
	/* de adaugat */
	this->curent = dict.prim;
}


void IteratorDictionar::prim() { //teta(1)
	/* de adaugat */
	curent = dict.prim;
}


void IteratorDictionar::urmator() { //teta(1)
	/* de adaugat */
	if (!valid())
		throw;
	curent = curent->urmator();
}


TElem IteratorDictionar::element() const { //teta(1)
	/* de adaugat */
	//return curent->element();
	if (!valid())
		throw;
	return (*curent).element();
}


bool IteratorDictionar::valid() const { //teta(1)
	/* de adaugat */
	return curent != NULL;
}

