#include "IteratorColectie.h"
#include "Colectie.h"
#include "TAD.h"


IteratorColectie::IteratorColectie(const Colectie& c) : col(c) { //complexitate teta(1)
	/* de adaugat */
	//this->col = c;
	this->i = 0;
	this->f = 1;
}

void IteratorColectie::prim() { //complexitate teta(1)
	/* de adaugat */
	this->i = 0;
}


void IteratorColectie::urmator() { //complexitate amortizata teta(1)
	/* de adaugat */ 
	if (this->f < col.frecventa[i])
		(this->f)++; //complexitate teta(1)
	else {
		int nr = 0;
		for (int j = this->i + 1; j < col.dimensiune; j++) //complexitate amortizata teta(1)
		{
			//complexitate teta(1)
			nr++;
			(this->i)++;
			if (this->col.frecventa[this->i] > 0)
				break;
		}
		if (nr == 0) (this->i)++; //complexitate teta(1)
		this->f = 1; //complexitate teta(1)
		/*if (valid() == false)
			throw 20;*/
	}
	
}


bool IteratorColectie::valid() const { //complexitate teta(1)
	/* de adaugat */
	return this->i < col.dimensiune;
	return false;
}



TElem IteratorColectie::element() const { //complexitate teta(1)
	/* de adaugat */
	/*if (valid() == false)
		throw 20;*/
	return next(col.minim, this->i);
	return -1;
}