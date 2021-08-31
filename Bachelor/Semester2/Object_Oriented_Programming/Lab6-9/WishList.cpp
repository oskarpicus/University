#include "WishList.h"


void WishList::golire_wishlist() noexcept {
	wishlist.clear();
}

void WishList::adauga_wishlist(const Oferta o) {
	auto it = find_if(wishlist.begin(), wishlist.end(), [o](const Oferta& o2) { return o == o2; });
	if (it!=wishlist.end())
		throw WishListError("Oferta exista deja in wishlist!\n");
	wishlist.push_back(o);
}