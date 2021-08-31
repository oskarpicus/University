#pragma once
#include "domain.h"
#include <string.h>
class ValidError {
	//clasa de erori de tip Valid
private:
	string msg;
public:
	//constructor
	ValidError(string mesaj):msg{mesaj} {}

	//metoda pentru accesarea mesajului unei erori de tip valid
	string get_msg() {
		return this->msg;
	}
};

class Valid {
	//clasa de obiecte pentru verificarea corectitudinii datelor unei oferte
public:
	/*
	Metoda pentru validarea unei oferte
	pre: oferta: Oferta&
	post: true, daca campurile lui oferta sunt corecte d.p.d.v. logic
	arunca ValidError, altfel
	*/
	bool valideaza_oferta(const Oferta& o) const;

	/*
	Metoda pentru verificarea corectitudinii unui tip de oferta
	pre: oferta: Oferta&
	post: valideaza_tip=true, daca o.tip!="", false, altfel
	*/
	bool valideaza_tip(const Oferta& o) const;

	/*
	Metoda pentru verificarea corectitudinii unui pret de oferta
	pre: oferta: Oferta&
	post: valideaza_pret=true, daca o.pret>0, false, altfel
	*/
	bool valideaza_pret(const Oferta& o) const noexcept;
	 
	/*
	Metoda pentru verificarea corectitudinii unei denumiri de oferta
	pre: oferta: Oferta&
	post: valideaza_denumire=true, daca o.denumire!="", false, altfel
	*/
	bool valideaza_denumire(const Oferta& o) const;

	/*
	Metoda pentru verificarea corectitudinii unei destinatii de oferta
	pre: oferta: Oferta&
	post: valideaza_destinatie=true, daca o.destinatie!="", false, altfel
	*/
	bool valideaza_destinatie(const Oferta& o) const;
};