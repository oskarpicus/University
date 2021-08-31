#pragma once
#include<algorithm>
#include<vector>
using namespace std;
/*template<typename TElem>
struct Nod {
	Nod* urm; //urmatorul nod
	TElem e; //informatia utila

	//constructor
	Nod(TElem e, Nod* urm) :e{ e }, urm{ urm } {};
	Nod(TElem e) :e{ e }, urm{ nullptr }{};
};

//forward declaration - clasa iterator e definit mai jos 
//Avem nevoie de asta fiindca clasa iterator este folosit in clasa MyLista dar definitia apare mai jos
template <typename TElem>
class ListaIterator;

template <typename TElem>
class Lista {

private:
	Nod<TElem>* prim; //primul nod

	Nod<TElem>* copieaza(Nod<TElem>* curent);

public:
	//constructor
	Lista() : prim{ nullptr } {};
	//Rule of three

	//constructor de copiere
	Lista(const Lista& ot);

	//defineste operatie de assignment dintre 2 liste
	void operator=(const Lista& ot);

	//destructor
	~Lista();

	//returneaza dimensiunea containerului
	int size() const noexcept;
	
	//adauga la sfarsit elementul
	void push_back(TElem el);

	//sterge elementul
	void erase(TElem el);

	//modifica unde apare el1 cu el2
	void modifica(TElem el1, TElem el2);

	//returneaza pozitia in care apare el
	int cauta(TElem el);

	//returneaza un iterator pozitionat pe primul element
	ListaIterator<TElem> begin() const { return ListaIterator<TElem>{prim}; }

	//returneaza un iterator cu nodul nullptr (dupa ultimul element)
	ListaIterator<TElem> end() const {
		return ListaIterator<TElem>{nullptr};
	}

	//acces dupa pozitie
	TElem& operator[](int poz) noexcept;
};

//iterator pentru lista inlantuita
template<typename TElem>
class ListaIterator {
private:
	Nod<TElem>* curent; //reprezinta cursorul
public:
	ListaIterator(Nod<TElem>* c) : curent{ c } {};

	bool operator!=(const ListaIterator& ot) noexcept {
		return curent != ot.curent;
	}

	//trece la urmatorul element din iteratie
	void operator++() noexcept {
		curent = curent->urm;
	}

	//returneaza elementul referit de curent
	TElem& operator*() noexcept { return curent->e; }
};



template<typename TElem>
Lista<TElem>::Lista(const Lista& ot)
{
	//copiem din ot in this
	prim = copieaza(ot.prim);
}

template<typename TElem>
void Lista<TElem>::operator=(const Lista& ot)
{
	//sterg pe this
	auto q = prim;
	while (q != nullptr)
	{
		auto aux = q->urm;
		delete q;
		q = aux;
	}
	prim = nullptr;
	prim = copieaza(ot.prim);
}

template <typename TElem>
Nod<TElem>* Lista<TElem>::copieaza(Nod<TElem>* current) {
	if (current == nullptr) {
		return nullptr;
	}
	Nod<TElem>* n = new Nod<TElem>{ current->e };
	n->urm = copieaza(current->urm);
	return n;
}


template <typename TElem>
Lista<TElem>::~Lista()
{
	auto q = prim;
	while (q != nullptr)
	{
		auto aux = q->urm;
		delete q;
		q = aux;
	}
	prim = nullptr;
}

template <typename TElem>
int Lista<TElem>::size() const noexcept
{
	int lg = 0;
	auto q = prim;
	while (q != nullptr)
	{
		lg++;
		q = q->urm;
	}
	return lg;
}

template <typename TElem>
int Lista<TElem>::cauta(TElem el)
{
	int i = 0;
	auto q = prim;
	while (q != nullptr)
	{
		if (q->e == el) //am gasit elementul
			return i;
		i++;
		q = q->urm;
	}
	return -1;
}

template <typename TElem>
void Lista<TElem>::push_back(TElem el)
{
	auto q = prim;
	while (q != nullptr && q->urm != nullptr) //parcurg pana ajung la final
		q = q->urm;
	if (q == nullptr) //lista este vida
		prim = new Nod<TElem>{ el,nullptr };
	else
		q->urm = new Nod<TElem>{ el,nullptr };
}

template <typename TElem>
void Lista<TElem>::erase(TElem el)
{
	auto q = prim, k = prim;
	if (prim->e == el) //se sterge primul element
	{
		auto urmator = prim->urm;
		delete prim;
		prim = urmator;
	}
	else {
		while (q != nullptr && q->urm != nullptr && !(q->e == el)) //parcurg pana gasesc elementul
		{
			k = q;
			q = q->urm;
		}
		//q = nodul in care se afla el
		k->urm = q->urm;
		delete q;
	}
}

template<typename TElem>
void Lista<TElem>::modifica(TElem el1, TElem el2)
{
	auto q = prim;
	while (q != nullptr && !(q->e == el1))
		q = q->urm;
	q->e = el2;
}

template<typename TElem>
TElem& Lista<TElem>::operator[](int poz) noexcept
{
	int i = 0;
	auto q = prim;
	while (q != nullptr)
	{
		if (i == poz)
			return q->e;
		i++;
		q = q->urm;
	}
	return q->e;
}*/

template<typename TElem>
class Lista : public vector<TElem>
{
public:

	/*
	Functie pentru a modifica unde apare el1 cu el2
	pre: el1, el2: TElem
	post: in locul in care apare el1, va fi inlocuit cu datele lui el2
	*/
	void modifica(TElem el1, TElem el2);

	/*Functie pentru cautarea unui element in lista
	pre: el: TElem
	post: cauta= -1, daca el nu apartine *this, 1, altfel
	*/
	int cauta(TElem el) const;

	/*Functie pentru stergea unui element
	pre: el: TElem
	post: el nu apartine lista
	*/
	void sterge(TElem el);
};

template<typename TElem>
void Lista<TElem>::modifica(TElem el1, TElem el2)
{
	auto iterator = find_if(this->begin(), this->end(), [el1](const TElem& el) {return el == el1; });
	if (iterator != this->end()) //el1 exista in lista
		*iterator = el2;
}

template<typename TElem>
int Lista<TElem>::cauta(TElem el) const
{
	auto it= find_if(this->begin(), this->end(), [el](const TElem& e) {return e == el; });
	if (it == this->end())
		return -1;
	return 1;
}

template<typename TElem>
void Lista<TElem>::sterge(TElem el)
{
	auto it = find_if(this->begin(), this->end(), [el](const TElem& e) {return e == el; });
	this->erase(it);
}