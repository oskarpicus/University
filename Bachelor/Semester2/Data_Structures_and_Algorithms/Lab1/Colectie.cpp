#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>
#include "TAD.h"
#define CAPACITATE 5

using namespace std;


Colectie::Colectie() { //complexitate teta(1)
	/* de adaugat */
	this->frecventa = new int[CAPACITATE];
	this->capacitate = CAPACITATE;
	this->dimensiune = 0; //lungime vector frecventa
	this->lg = 0; //nr obiecte din colectie
}


void Colectie::adauga(TElem elem) { //complexitate generala O(n)
	/* de adaugat */
	int ok = 0, nr;

	if (vida() == true) //complexitate teta(1)
	{
		this->minim = elem;
		this->maxim = elem;
		this->frecventa[0] = 1;
		this->dimensiune++;
		this->lg++;
	}
	else {
		//complexitate teta(1)
		if (this->minim <= elem && elem <= this->maxim) //se afla deja in vectorul de frecventa
		{
			this->lg++;
			this->frecventa[diferenta(elem,this->minim)]++;
		}
		else //nu se afla deja in vectorul de frecvente
		{//complexitate O(n)

			if (elem < this->minim) //complexitate teta(1)
			{
				//va trebui sa adaugam (this->minim) - elem celule pe pozitia 0
				ok = 0; nr = diferenta(this->minim, elem);
			}
			else { //va trebui sa adaugam (this->minim) - elem celule pe ultima pozitie
				ok = 1;
				nr = diferenta(elem, this->maxim);
			}

			if (this->dimensiune + nr > this->capacitate) //complexitate amortizata teta(1)
			{
				//nu mai avem spatiu ==> redimensionare
				int* vf = new int[(this->capacitate + nr) * 2];
				for (int i = 0; i < this->dimensiune; i++)
					vf[i] = this->frecventa[i];
				delete this->frecventa;
				this->frecventa = vf;
				this->capacitate = (this->capacitate + nr) * 2;
			}
			if (ok == 0) //adaugam pe pozitia 0
			{
				for (int i = 0; i < nr; i++) //complexitate amortizata teta(1)
				{
					for (int j = this->dimensiune - 1; j >= 0; j--) //complexitate O(n)
						this->frecventa[j + 1] = this->frecventa[j]; //deplasam elementele
					this->frecventa[0] = 0;
					this->dimensiune++;
				}
				this->frecventa[0] = 1; //marim frecventa noului minim (se va afla mereu pe prima pozitie)
				this->minim = elem; //actualizam minimul
			}
			else { //adaugam pe ultima pozitie
				for (int i = 0; i < nr; i++) //complexitate amortizata teta(1)
				{
					this->frecventa[this->dimensiune] = 0; //initializam noile celule
					this->dimensiune++;
				}
				this->frecventa[this->dimensiune - 1] = 1; //marim frecventa noului maxim (se va afla mereu pe ultima pozitie)
				this->maxim = elem; //actualizam maximul
			}
			this->lg++; //crestem lungimea colectiei
		}
	}
}


bool Colectie::sterge(TElem elem) { //complexitate generala O(n)
	/* de adaugat */
	if (this->minim < elem && elem < this->maxim) //complexitate teta(1)
	{
		if (cauta(elem) == false) //daca nu exista elementul, nu il putem sterge
			return false;
		this->frecventa[diferenta(elem,this->minim)]--; //scadem frecventa elementului
		this->lg--; //scadem lungimea colectiei
		return true;
	}
	else {
		if (elem == this->minim && this->frecventa[0] > 0) //daca este chiar minimul
		{
			this->frecventa[0]--;
			if (this->frecventa[0] == 0) //avem un nou minim
			{
				int i, j, k;
				//cautam noul minim <==> cel mai mic element cu frecventa nenula
				for (i = 0; i < this->dimensiune; i++) //complexitate O(n)
					if (this->frecventa[i] > 0)
					{
						this->minim = next(this->minim,i);
						break;
					}
				k = i;
				for (j = 0; j < k; j++)
				{
					//stergem cele k celule de pe primele pozitii (ele au frecventa 0)
					for (int i = 0; i < this->dimensiune - 1; i++) //complexitate O(n)
						this->frecventa[i] = this->frecventa[i + 1];
					this->dimensiune--;
				}
			}
			this->lg--; //actualizam lungimea colectiei
			return true;
		}
		else if (elem == this->maxim && this->frecventa[this->dimensiune - 1] > 0) //daca este chiar maximul
		{
			this->frecventa[this->dimensiune - 1]--;
			if (this->frecventa[this->dimensiune - 1] == 0) //avem un nou maxim
			{
				for (int i = this->dimensiune - 1; i >= 0; i--) //cautam un nou maxim, complexitate O(n)
					if (this->frecventa[i] > 0)
					{
						this->maxim = i - this->maxim;
						this->dimensiune = i + 1;
						break;
					}
			}
			this->lg--;
			return true;
		}
	}
	return false;
}


bool Colectie::cauta(TElem elem) const { //complexitate teta(1)
	/* de adaugat */
	if (this->minim <= elem && elem <= this->maxim && this->frecventa[diferenta(elem, this->minim)] > 0)
		return true;
	return false;
}

int Colectie::nrAparitii(TElem elem) const { //complexitate teta(1)
	/* de adaugat */
	if (this->minim <= elem && elem <= this->maxim && this->frecventa[diferenta(elem, this->minim)] > 0)
		return this->frecventa[elem - this->minim];
	return 0;
}


int Colectie::dim() const { //complexitate teta(1)
	/* de adaugat */
	return this->lg;
	return 0;
}


bool Colectie::vida() const { //complexitate teta(1)
	/* de adaugat */
	if (this->lg == 0)
		return true;
	return false;
}

IteratorColectie Colectie::iterator() const { //complexitate teta(1)
	return  IteratorColectie(*this);
}


Colectie::~Colectie() { //complexitate teta(1)
	/* de adaugat */
	delete frecventa;
}

