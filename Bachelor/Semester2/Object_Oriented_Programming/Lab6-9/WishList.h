#pragma once
#include "Lista.h"
#include <string>
#include "domain.h"
#include <algorithm>

class WishListError {
	//Clasa ce va acoperi erorile la nivel de repository
	string msg;
public:

	//constructor
	WishListError(string mesaj) :msg{ mesaj } {
	}

	/*
	Getter pentru mesajul unui obiect de tip RepoError
	pre: adevarat
	post: RepoError.msg: string
	*/
	string get_mesaj() { return this->msg; }
};


class WishList {
private:
	vector<Oferta> wishlist;
public:

	//constructor
	WishList() = default;

	/*
	Metoda pentru stergerea tuturor ofertelor din wishlist
	pre: adevarat
	post: wishlist = multimea vida
	*/
	void golire_wishlist() noexcept;

	/*
	Metoda pentru adaugarea unei oferte in wishlist
	pre: o: Oferta
	post: wishlist' = wishlist + { o }
	arunca RepoErrorWishList daca o apartine de dinainte wishlist
	*/
	void adauga_wishlist(const Oferta o);

	/*
	Metoda pentru obtinerea wishlist-ului
	pre: adevarat
	post: Repo.wishlist: vector<Oferta>
	*/
	vector<Oferta> get_wishlist() const { return wishlist; };

	/*
	Metoda pentru obtinerea numarului de oferte din wishlist
	pre: adevarat
	post: nr: int, nr = wishlist.size
	*/
	const int get_wishlist_size() const noexcept { return wishlist.size(); }

	//stergem constructorul de copiere
	WishList(const WishList& ot) = delete;
};