#pragma once
#include "domain.h"
#include "repository.h"
 
//clasa abstracta pentru Undo
class Undo {
public:
	virtual void do_undo() = 0;
	virtual ~Undo() = default;
};

class UndoAdauga: public Undo {
private:
	Oferta oferta_adaugata;
	Repository& repo;
public:

	//Constructor; pre: repo: Repo, o: Oferta
	UndoAdauga(Repository& repo, const Oferta& o) : repo{ repo },oferta_adaugata { o }{}

	//Metoda pentru undo - se sterge oferta_adaugata (ultima oferta adaugata)
	void do_undo() override {
		this->repo.repo_sterge(oferta_adaugata);
	}
};

class UndoSterge : public Undo {
private:
	Oferta oferta_stearsa;
	Repository& repo;
public:
	//Constructor; pre: repo: Repo, o: Oferta
	UndoSterge(Repository& repo,const Oferta& o): repo{repo},oferta_stearsa{o}{}

	//Metoda pentru undo - se adauga oferta_stearsa (ultima oferta stearsa)
	void do_undo() override {
		this->repo.repo_adauga(oferta_stearsa);
	}
};

class UndoModifica : public Undo {
private:
	Oferta oferta_veche, oferta_curenta;
	Repository& repo;
public:
	//Constructor; pre: repo: Repo, o1: Oferta, o2: Oferta
	UndoModifica(Repository& repo,const Oferta& o1,const Oferta& o2): repo{repo},oferta_veche{o1},oferta_curenta{o2}{}

	//Metoda pentru undo - in loc de oferta_curent, va aparea oferta_veche
	void do_undo() override {
		this->repo.repo_modifica(oferta_curenta, oferta_veche);
	}
};