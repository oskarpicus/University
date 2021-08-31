#include "valid.h"

bool Valid::valideaza_oferta(const Oferta& o) const {
	string erori = "";
	if (!valideaza_denumire(o)) erori += "Denumirea nu trebuie sa fie vida!\n";
	if (!valideaza_destinatie(o)) erori += "Destinatia nu trebuie sa fie vida!\n";
	if (!valideaza_tip(o)) erori += "Tipul nu trebuie sa fie vid!\n";
	if (!valideaza_pret(o)) erori += "Pretul trebuie sa fie pozitiv!\n";
	if (erori == "")
		return true;
	throw ValidError(erori);}

bool Valid::valideaza_tip(const Oferta& o) const {
	return o.get_tip() == "" ? false : true;
}

bool Valid::valideaza_denumire(const Oferta& o) const {
	return o.get_denumire() != "";
}


bool Valid::valideaza_destinatie(const Oferta& o) const {
	return o.get_destinatie() != "";
}

bool Valid::valideaza_pret(const Oferta& o) const noexcept {
	return o.get_pret() > 0;
}