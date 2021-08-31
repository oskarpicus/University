#pragma once
#include "domain.h"
#include "Lista.h"
#include<map>

class RepoError {
	//Clasa ce va acoperi erorile la nivel de repository
	string msg;
public:

	//constructor
	RepoError(string mesaj) :msg{ mesaj } {
	}

	/*
	Getter pentru mesajul unui obiect de tip RepoError
	pre: adevarat
	post: RepoError.msg: string
	*/
	string get_mesaj() { return this->msg; }
};


class Repository {
public:
	virtual void repo_adauga(const Oferta& o)=0;
	virtual void repo_sterge(const Oferta& o) = 0;
	virtual void repo_modifica(const Oferta& o1, const Oferta& o2) = 0;
	virtual bool repo_cauta(const Oferta& o) const = 0;
	virtual int repo_dim() const = 0 ;
	virtual const Lista<Oferta> repo_get_all() = 0;
	virtual const Oferta repo_get_oferta(string denumire) const=0;
};


class Repo: public Repository
{
private:
	//Lista<Oferta> lista;
	map<string, Oferta> lista;

public:

	//constructor
	Repo ()= default; 

	/*
	Metoda pentru adaugarea unei oferte in lista
	pre: o: Oferta
	post: Repo.lista'=Repo.lista+{o}
	arunca RepoError daca o apartine de dinainte Repo.lista
	*/
	void repo_adauga(const Oferta& o) override;

	/*
	Metoda pentru stergerea unei oferte in lista
	pre: o: Oferta
	post: Repo.lista'=Repo.lista-{o}, daca o apartine Repo.lista
	arunca RepoError, daca o nu apartine Repo.lista
	*/
	 void repo_sterge(const Oferta& o) override ;

	/*
	Metoda pentru modificarea unei oferte in lista
	pre: o1,o2: Oferta
	post: Repo.lista'[pozitia unde apare o1]=o2
	arunca RepoError, daca o1 nu apartine Repo.lista
	*/
	 void repo_modifica(const Oferta& o1, const Oferta& o2) override ;

	/*
	Metoda pentru cautarea unei oferte in lista
	pre: o: Oferta
	post: true, daca o apartine Repo.lista, false, altfel
	*/
	bool repo_cauta(const Oferta& o)  const override;

	/*
	Metoda pentru accesarea dimensiunii listei de oferte
	pre: adevarat
	post: Repo.lista.size: int
	*/
	int repo_dim() const override;

	/*
	Metoda pentru accesarea unui element de pe o anumita pozitie
	pre: pozitie: int
	post: Repo.lista[pozitie]: Oferta
	*/
	//Oferta repo_get_oferta(int& poz) const;

	/*
	Metoda pentru a accesa toate ofertele memorate
	pre: adevarat
	post: Repo.lista: vector<Oferta>
	*/
	const Lista<Oferta> repo_get_all() override;


	/*
	Metoda pentru obtinerea unei oferte dupa denumire
	pre: denumire: string
	post: o apartine lista, o.denumire = denumire
	arunca RepoError daca nu exista o
	*/
	const Oferta repo_get_oferta(string denumire) const override;


	//stergem mecanismul de copiere de entitati de tip Repo
	Repo(const Repo& ot) = delete;

	 virtual ~Repo() = default;
};


class RepoFile : public Repo {
private:
	string fisier;

	/*
	Metoda pentru citirea in fisier a datelor
	pre: adevarat
	post: Repo.lista va fi populata cu entitatile din fisier
	*/
	void load_from_file();

	/*
	Metoda pentru scrierea datelor in fisier CSV
	pre: adevarat
	post: fisier va fi populat cu entitatile din Repo.lista
	*/
	void write_to_file();

public:
	/*Constuctor; pre: fisier: string*/
	RepoFile(string fisier) : Repo{}, fisier{ fisier }{ load_from_file(); } //constructor pentru RepoFile

	/*Metoda polimorfica de adaugare in repository
	pre: o: Oferta
	post: repo.lista'=repo.lista+{o}, se actualizeaza fisierul
	*/
	void repo_adauga(const Oferta& o) override {
		Repo::repo_adauga(o);
		write_to_file();
	}

	/*
	Metoda polimorfica de stergere din repository
	pre: o: Oferta
	post: repo.lista'=repo.lista-{o}, se actualizeaza fisierul
	*/
	void repo_sterge(const Oferta& o) override {
		Repo::repo_sterge(o);
		write_to_file();
	}

	/*
	Metoda polimorfica de a modifica o oferta din repository
	pre: o1: Oferta, o2: Oferta
	post: Repo.lista'[pozitia unde apare o1]=o2, se actualizeaza fisierul
	*/
	void repo_modifica(const Oferta& o1, const Oferta& o2) override {
		Repo::repo_modifica(o1, o2);
		write_to_file();
	}
};
