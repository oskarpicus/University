#pragma once
#include "repository.h"
#include "valid.h"
#include <random>
#include <memory>
#include <vector>
#include "WishList.h"
#include "Undo.h"
#include "Observer.h"

/*
Tipul functiei de comparare pentru 2 elemente
pre: o1,o2: Oferta
post: 0, daca o1=o2, 1, daca o1>o2, -1, daca o1<o2
*/
typedef int(*FunctieComparare)(const Oferta& o1, const Oferta& o2);

class Service:public Observable
{
private:
	Repository& repo;
	Valid& valid;
	WishList wishlist;
	vector<unique_ptr<Undo>> undolist;

	/*
	Functie pentru generarea unui string cu date random
	pre: adevarat
	post: s: string
	*/
	string random_string() const;

public:

	/*
	Constructor
	pre: repo: Repo, valid: Valid
	*/
	Service(Repo& repo, Valid& valid);

	/*
	Metoda pentru adaugarea unei viitoare oferte
	pre: denumire, destinatie, tip: string, pret: int
	post: Service.repo.lista'=Service.repo.lista + Oferta(denumire,destinatie,tip,pret)
	*/
	void service_adauga(const string& denumire, const string& destinatie, const string& tip, const int& pret);

	/*
	Metoda pentru stergerea unei oferte
	pre: denumire1, destinatie1, tip1, denumire2, destinatie2, tip2: string, pret1, pret2: int
	post: Service.repo.lista[pozitia in care se afla Oferta(denumire1,destinatie1,tip1,pret1)]=Oferta(denumire2,destinatie2,tip2,pret2)
	*/
	void service_modifica(const string& denumire1, const string& destinatie1, const string& tip1, const int& pret1, const string& denumire2, const string& destinatie2, const string& tip2, const int& pret2);

	/*
	Metoda pentru stergerea unei oferte
	pre: denumire, destinatie, tip: string, pret: int
	post: Service.repo.lista'=Service.repo.lista - Oferta(denumire,destinatie,tip,pret)
	*/
	void service_sterge(const string& denumire, const string& destinatie, const string& tip, const int& pret);

	/*
	Metoda pentru cautarea unei oferte
	pre: denumire, destinatie, tip: string, pret: int
	post: true, daca Oferta(denumire,destinatie,tip,pret) apare in Service.repo.lista, false, altfel
	*/
	bool service_cauta(const string& denumire, const string& destinatie, const string& tip, const int& pret) const;

	/*
	Metoda pentru determinarea numarului de oferte memorate
	pre: adevarat
	post: Service.repo.lista.dim
	*/
	int service_dim() const;

	/*
	Functie pentru filtrarea ofertelor dupa destinatie
	pre: destinatie: string
	post: l: Lista<Oferta> astfel incat oricare ar fi o din l, o.destinatie=destinatie
	*/
	const Lista<Oferta> service_filtrare_destinatie(string destinatie);

	/*
	Functie pentru filtrarea ofertelor dupa pret
	pre: pret: int
	post: l: Lista<Oferta> astfel incat oricare ar fi o din l, o.pret=pret
	*/
	const Lista<Oferta> service_filtrare_pret(int pret);

	/*
	Metoda pentru accesarea tuturor ofertelor memorate
	pre: adevarat
	post: Service.repo.lista: vector<Oferta>
	*/
	const Lista<Oferta> service_get_all();

	//Metoda pentru sortarea listei de oferte dupa denumire
	const Lista<Oferta> sortare_denumire();

	//Metoda pentru sortarea listei de oferte dupa tip
	const Lista<Oferta> sortare_tip();

	//Metoda pentru sortarea listei de oferte dupa destinatie
	const Lista<Oferta> sortare_destinatie();

	//Metoda pentru sortarea listei de oferte dupa pret
	const Lista<Oferta> sortare_pret();

	/*
	Metoda pentru stergerea tuturor ofertelor din wishlist
	pre: adevarat
	post: repo.wishlist = multimea vida
	*/
	void service_golire_wishlist() noexcept;

	/*
	Metoda pentru adaugarea unei oferte in wishlist
	pre: denumire: string
	post: repo.wishlist' = repo.wishlist + { o }
	*/
	void service_adauga_wishlist(const string denumire);

	/*
	Metoda pentru generarea random a unui numar de oferte
	pre: nr: int
	post: | repo.wishlist' | = | repo.wistlist | + nr
	toate ofertele adaugate in plus sunt aleatoare
	*/
	void service_genereaza_wishlist(int nr);

	/*
	Se salveaza ofertele din wishlist intr-un fisier CSV
	pre: nume_fisier: string
	post: nume_fisier contine toate ofertele din wishlist
	*/
	void service_exporta_wishlist_CSV(string nume_fisier) const;

	/*
	Se salveaza ofertele din wishlist intr-un fisier HTML
	pre: nume_fisier: string
	post: nume_fisier contine toate ofertele din wishlist
	*/
	void service_exporta_wishlist_HTML(string nume_fisier) const;

	/*
	Metoda pentru obtinerea numarului de oferte din wishlist
	pre: adevarat
	post: nr: int, nr = repo.wishlist.size
	*/
	const int service_get_wishlist_size() const noexcept { return wishlist.get_wishlist_size(); }

	/*
	Metoda pentru accesarea tuturor ofertelor din wishlist
	pre: adevarat
	post: wishlist: vector<Oferta>
	*/
	auto service_get_wishlist() const { return wishlist.get_wishlist(); }

	/*
	Metoda pentru determinarea pretului total a ofertelor din wishlist
	pre: adevarat
	post: service_wishlist_pret_total: int, egal cu suma preturilor tuturor ofertelor din this->wishlist
	*/
	int service_wishlist_pret_total();

	/*
	Metoda pentru undo, stergerea ultimei operatii efectuate
	pre: adevarat
	post: repo.lista revine la starea de dinaintea ultimei operatii efectuate
	@arunca OfertaError daca nu se mai poate face undo
	*/
	void undo();

	/*
	Metoda pentru obtinerea ofertei dupa o denumire
	pre: denumire: string
	post: o: Oferta, o apartine repo.lista, o.denumire=denumire
	*/
	const Oferta service_get_oferta(string denumire) {
		return this->repo.repo_get_oferta(denumire);
	}

	//stergem mecanismul de copiere de tip Service
	Service(Service& ot) = delete;
};