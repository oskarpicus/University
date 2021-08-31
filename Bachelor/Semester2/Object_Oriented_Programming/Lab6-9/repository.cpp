#include "repository.h"
#include <fstream>

void Repo::repo_adauga(const Oferta& o)
{
	auto it = lista.find(o.get_denumire());
	if(it!=lista.end()) //oferta exista deja
		throw RepoError("Oferta a fost adaugata deja!\n");
	lista.insert(std::pair<string,Oferta>(o.get_denumire(), o));
}

bool Repo::repo_cauta(const Oferta& o) const
{
	auto it = lista.find(o.get_denumire());
	return it == lista.end() ? false : true;
}

void Repo::repo_sterge(const Oferta& o)
{

	auto it = lista.find(o.get_denumire());
	if(it==lista.end())
		throw RepoError("Oferta nu exista!\n");
	lista.erase(it);
}

void Repo::repo_modifica(const Oferta& o1, const Oferta& o2)
{

	auto it = lista.find(o1.get_denumire());
	if(it==lista.end()) 
		throw RepoError("Oferta nu exista!\n");
	if (o1.get_denumire() == o2.get_denumire())
		(*it).second = o2;
	else
		throw RepoError("Ofertele de modificat au denumire diferita!\n");
}

int Repo::repo_dim() const
{
	return lista.size();
}

const Lista<Oferta> Repo::repo_get_all() 
{
	Lista<Oferta> l;
	for (const auto& i: lista)
		l.push_back(i.second);
	return l;
}


const Oferta Repo::repo_get_oferta(string denumire) const {
	auto it = lista.find(denumire);
	if(it==lista.end()) //nu exista oferta
		throw RepoError("Oferta cu aceasta denumire nu exista!\n");
	return (*it).second;
}

void RepoFile::load_from_file() {
	ifstream in(this->fisier);
	if (!in.is_open()) { //verify if the stream is opened		
		throw RepoError("Nu am putut deschide fisierul " + this->fisier);
	}
	while (!in.eof()) {
		string denumire, destinatie, tip;
		int pret;
		in >> denumire >> destinatie >> tip >> pret;
		if (in.eof())
			break;
		Oferta o{ denumire,destinatie,tip,pret };
		Repo::repo_adauga(o);
	}
	in.close();
}
 
void RepoFile::write_to_file() {
	ofstream out(this->fisier);
	auto toate = Repo::repo_get_all();
	for (auto& oferta : toate) {
		out << oferta.get_denumire() << endl << oferta.get_destinatie() << endl << oferta.get_tip() << endl << oferta.get_pret() << endl;
	}
	out.close();
}