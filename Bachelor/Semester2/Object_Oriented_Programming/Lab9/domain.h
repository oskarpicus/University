#pragma once

#include<vector>
#include<string>
using namespace std;

class OfertaError {
	//Clasa de obiecte de tip OfertaError
private:
	string mesaj;
public:
	//constructor
	OfertaError(string m):mesaj{m}{}

	/*
	Metoda pentru accesarea mesajului erorii
	pre: adevarat
	post: mesaj: string
	*/
	string get_mesaj() { return this->mesaj; }
};


class Oferta {
	/*
	Clasa de obiecte de tip Oferta Turistica
	Domain: denumire, destinatie,tip: String, pret: int
	*/
private:
	string denumire,destinatie,tip;
	int pret;

public:

	/*
	Constructorul pentru clasa Oferta
	pre: denumire, destinatie,tip: String, pret: int
	*/
	Oferta(const string& denumire, const string& destinatie, const string& tip, const int& pret) :denumire{ denumire }, destinatie{ destinatie }, tip{ tip }, pret{ pret }{}

	/*
	Getter pentru pretul unei oferte
	pre: adevarat
	post: Oferta.pret: int
	*/
	int get_pret() const noexcept;

	/*
	Getter pentru denumirea unei oferte
	pre: adevarat
	post: Oferta.denumire: string
	*/
	string get_denumire() const;

	/*
	Getter pentru destinatia unei oferte
	pre: adevarat
	post: Oferta.destinatie: string
	*/
	string get_destinatie() const;

	/*
	Getter pentru tipul unei oferte
	pre: adevarat
	post: Oferta.tip: string
	*/
	string get_tip() const;

	/*
	Setter pentru pretul unei oferte
	pre: pret1: int
	post: Oferta'.pret=pret1
	*/
	void set_pret(int pret1) noexcept;

	/*
	Setter pentru denumirea unei oferte
	pre: denumire1: string
	post: Oferta'.denumire=denumire1
	*/
	void set_denumire(const string& denumire1);

	/*
	Setter pentru destinatia unei oferte
	pre: destinatie1: string
	psot: Oferta'.destinatie=destinatie1
	*/
	void set_destinatie(const string& destinatie1);

	/*
	Setter pentru tipul unei oferte
	pre: tip: string
	post: Oferta'.tip=tip
	*/
	void set_tip(const string& tip1);

	/*
	Defineste relatia de egalitate dintre doua oferte
	pre: oferta: Oferta
	post: true, daca Oferta si oferta au toate campurile egale, false, altfel
	*/
	bool operator ==(const Oferta& oferta) const;

};