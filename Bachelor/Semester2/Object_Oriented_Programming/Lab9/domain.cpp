#include "domain.h"



int Oferta::get_pret() const noexcept{
	return this->pret;
}

string Oferta::get_tip() const
{
	return this->tip;
}
 
string Oferta::get_denumire() const
{
	return this->denumire;
}

string Oferta::get_destinatie() const
{
	return this->destinatie;
}

void Oferta::set_pret(int pret1) noexcept
{
	this->pret = pret1;
}

void Oferta::set_denumire(const string& denumire1)
{
	this->denumire = denumire1;
}

void Oferta::set_destinatie(const string& destinatie1)
{
	this->destinatie = destinatie1;
}

void Oferta::set_tip(const string& tip1)
{
	this->tip = tip1;
}

bool Oferta::operator ==(const Oferta& oferta) const
{
	return this->destinatie == oferta.get_destinatie() && this->pret == oferta.get_pret() && this->denumire == oferta.get_denumire() && this->tip == oferta.get_tip() ? true : false;
}
