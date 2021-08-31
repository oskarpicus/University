#pragma once
#include "service.h"
#include <string>
using std::string;
class UI {
private:
	Service& service;

	/*
	Primeste de la intrarea standard un tip de oferta
	pre: adevarat
	post: tip: string citit de la tastatura
	*/
	string citeste_tip() const;

	/*
	Primeste de la intrarea standard o destinatie de oferta
	pre: adevarat
	post: destinatie: string citit de la tastatura
	*/
	string citeste_destinatie() const;

	/*
	Primeste de la intrarea standard o denumire de oferta
	pre: adevarat
	post: denumire: string citit de la tastatura
	*/
	string citeste_denumire() const;

	/*
	Primeste de la intrarea standard un pret de oferta
	pre: adevarat
	post: pret: int citit de la tastatura
	*/
	int citeste_pret() const;

	/*
	Primeste de la intrarea standard un nume de fisier
	pre: adevarat
	post: fisier: string citit de la tastatura
	*/
	string citeste_fisier() const;

public:

	UI(Service& service) : service{ service } {};

	//Metoda principala - primeste si executa comenzi de la utilizator
	void run();

	//Metoda pentru a adauga o oferta
	void ui_adauga();

	//Metoda pentru a sterge o oferta
	void ui_sterge();

	//Metoda pentru a modifica o oferta
	void ui_modifica();

	//Metoda pentru a cauta o oferta
	void ui_cauta();

	//Metoda pentru afisarea tuturor ofertelor memorate
	void ui_afiseaza();

	//Metoda pentru filtrarea ofertelor
	void ui_filtrare();

	//Metoda pentru sortarea ofertelor
	void ui_sortare();

	//Metoda ce afiseaza si primeste comenzi legate de wishlist
	void ui_submeniu_wishlist();

	//Metoda pentru golirea wishlist-ului
	void ui_golire_wishlist();

	//Metoda pentru adaugarea unei oferte in wishlist
	void ui_adauga_wishlist();

	//Metoda pentru exportarea wishlist-ului intr-un fisier CSV
	void ui_exporta_wishlist();

	//Metoda pentru generarea random a unui wishlist
	void ui_generare_wishlist();

	//Metoda in vederea undo
	void ui_undo();
};