#include<iostream>
#include "UI.h"
using std::cout;
 
void UI::run()
{
	bool ruleaza = true;
	while (ruleaza)
	{
		int cmd;
		cout << "\nSelectati functionalitatea dorita\n1-Adaugare oferta\n2-Stergere oferta\n3-Modificare oferta\n4-Cautare oferta\n5-Afisare oferte\n6-Filtrare oferte\n7-Sortare\n8-Submeniu wishlish\n9-Undo\n0-Iesire din aplicatie\n";
		cin >> cmd;
		try {
			switch (cmd)
			{
			case 0:
				ruleaza = false;
				break;
			case 1:
				this->ui_adauga();
				break;
			case 2:
				this->ui_sterge();
				break;
			case 3:
				this->ui_modifica();
				break;
			case 4:
				this->ui_cauta();
				break;
			case 5:
				this->ui_afiseaza();
				break;
			case 6:
				this->ui_filtrare();
				break;
			case 7:
				this->ui_sortare();
				break;
			case 8:
				this->ui_submeniu_wishlist();
				break;
			case 9:
				this->ui_undo();
				break;
			default:
				cout << "\nComanda invalida\n";
			}
		}
		catch (RepoError & e)
		{
			cout <<endl<< e.get_mesaj() << endl;
		}
		catch (ValidError & e)
		{
			cout << endl << e.get_msg() << endl;
		}
		catch (invalid_argument) //eroarea aruncata de stoi
		{
			cout << "\nDate invalide numeric!\n";
		}
		catch (OfertaError& e) {
			cout << endl << e.get_mesaj() << endl;
		}
	}
}

void UI::ui_adauga()
{
	cout << "\nDati datele ofertei de adaugat:\n";
	string denumire = citeste_denumire(), destinatie = citeste_destinatie(), tip = citeste_tip();
	const int pret = citeste_pret();
	this->service.service_adauga(denumire, destinatie, tip, pret);
	cout << "\nAdaugarea a fost facuta cu succes!\n";
}

void UI::ui_afiseaza()
{
	cout << "\nOfertele memorate:\nDenumire / destinatie / tip / pret\n";
	const Lista<Oferta>& v = this->service.service_get_all();
	for (const auto& i : v)
		cout << i.get_denumire() << " " << i.get_destinatie() << " " << i.get_tip() << " " << i.get_pret() << endl;
}

void UI::ui_cauta()
{
	cout << "\nDati datele ofertei de cautat:\n";
	string denumire = citeste_denumire(), destinatie = citeste_destinatie(), tip = citeste_tip();
	const int pret = citeste_pret();
	if (this->service.service_cauta(denumire, destinatie, tip, pret))
		cout << "\nOferta data este adaugata\n";
	else cout << "\nOferta data nu este adaugata\n";
}

void UI::ui_modifica()
{
	cout << "\nDati datele ofertei de modificat:\n";
	string denumire = citeste_denumire(), destinatie = citeste_destinatie(), tip = citeste_tip();
	const int pret = citeste_pret();
	cout << "\nDati datele actualizate ale ofertei\n";
	string denumire1 = citeste_denumire(), destinatie1 = citeste_destinatie(), tip1 = citeste_tip();
	const int pret1 = citeste_pret();
	this->service.service_modifica(denumire, destinatie, tip, pret, denumire1, destinatie1, tip1, pret1);
	cout << "\nModificarea a fost facuta cu succes!\n";
}

void UI::ui_sterge()
{
	cout << "\nDati datele ofertei de sters:\n";
	string denumire = citeste_denumire(), destinatie = citeste_destinatie(), tip = citeste_tip();
	const int pret = citeste_pret();
	this->service.service_sterge(denumire, destinatie, tip, pret);
	cout << "\nStergerea a fost facuta cu succes!\n";
}

void UI::ui_filtrare()
{
	cout << "\nDupa ce doriti sa filtrati:\n1-Dupa destinatie\n2-Dupa pret\n";
	int cmd;
	cin >> cmd;
	if (cmd != 1 && cmd != 2)
	{
		cout << "Alegere invalida\n";
		return;
	}
	Lista<Oferta> l;
	if (cmd == 1)
	{
		string destinatie{ citeste_destinatie() };
		l = this->service.service_filtrare_destinatie(destinatie);
	}
	else if (cmd == 2)
	{
		const int pret{ citeste_pret() };
		l = this->service.service_filtrare_pret(pret);
	}
	cout << "\nOfertele memorate dupa filtrare:\nDenumire / destinatie / tip / pret\n";
	for (const auto& i : l)
		cout << i.get_denumire() << " " << i.get_destinatie() << " " << i.get_tip() << " " << i.get_pret() << endl;
}

void UI::ui_sortare() {
	cout << "\nSortare:\n1-Dupa destinatie\n2-Dupa pret\n3-Dupa tip\n4-Dupa denumire\n";
	int cmd;
	cin >> cmd;
	if (cmd < 1 || cmd>4)
	{
		cout << "Alegere invalida\n";
		return;
	}
	Lista<Oferta> l;
	if (cmd == 1) l = service.sortare_destinatie();
	else if (cmd == 2) l = service.sortare_pret();
	else if (cmd == 3) l = service.sortare_tip();
	else l = service.sortare_denumire();
	cout << "\nOfertele memorate dupa sortare:\nDenumire / destinatie / tip / pret\n";
	for (const auto& i : l)
		cout << i.get_denumire() << " " << i.get_destinatie() << " " << i.get_tip() << " " << i.get_pret() << endl;
}

void UI::ui_submeniu_wishlist()
{
	int ruleaza = 1;
	while (ruleaza)
	{
		int cmd;
		cout << "\nSelectati functionalitatea dorita:\n1-Golire wishlist\n2-Adauga oferta\n3-Generare random de oferte\n4-Export wishlist in fisier\n0-Intoarcere in meniul principal\n";
		cin >> cmd;
		try {
			switch (cmd)
			{
			case 1:
				this->ui_golire_wishlist();
				break;
			case 2:
				this->ui_adauga_wishlist();
				break;
			case 3:
				this->ui_generare_wishlist();
				break;
			case 4:
				this->ui_exporta_wishlist();
				break;
			case 0:
				ruleaza = 0;
				break;
			default:
				cout << "Comanda invalida!\n";
			}
		}
		catch (RepoError& e)
		{
			cout << endl << e.get_mesaj() << endl;
		}
		catch (WishListError& e)
		{
			cout << endl << e.get_mesaj() << endl;
		}
		cout << "\nNumar oferte in wishlist: " << this->service.service_get_wishlist_size() << endl;
		cout << "Total pret: " << this->service.service_wishlist_pret_total() << endl;
	}
}

void UI::ui_undo() {
	this->service.undo();
	cout << "\nUndo reusit!\n";
}

void UI::ui_golire_wishlist() {
	this->service.service_golire_wishlist();
	cout << "\nWishList-ul a fost golit cu succes!\n";
}

void UI::ui_adauga_wishlist() {
	string denumire = this->citeste_denumire();
	this->service.service_adauga_wishlist(denumire);
	cout << "\nOferta a fost adaugata cu succes in wishlist!\n";
}

void UI::ui_exporta_wishlist() {
	int cmd;
	cout << "\nExport in:\n1 - Fisier CSV\n2 - Fisier HTML\n";
	cin >> cmd;
	if (cmd < 1 || cmd >2)
		cout << "\nAlegere invalida!\n";
	else {
		string fisier = this->citeste_fisier();
		if (cmd == 1)
			this->service.service_exporta_wishlist_CSV(fisier);
		else if (cmd == 2)
			this->service.service_exporta_wishlist_HTML(fisier);
		cout << "\nOfertele au fost exportate cu succes!\n";
	}
}

void UI::ui_generare_wishlist()  {
	cout << "Dati numarul de oferte de generat \n";
	int nr;
	cin >> nr;
	this->service.service_genereaza_wishlist(nr);
	cout << "\nOfertele au fost generate cu succes!\n";
}



string UI::citeste_tip() const
{
	cout << "Dati tipul ofertei\n";
	string tip;
	cin >> tip;
	return tip;
}
 
string UI::citeste_destinatie() const
{
	cout << "Dati destinatia ofertei\n";
	string destinatie;
	cin >> destinatie;
	return destinatie;
}

string UI::citeste_denumire() const
{
	cout << "Dati denumirea ofertei\n";
	string denumire;
	cin >> denumire;
	return denumire;
}

int UI::citeste_pret() const
{
	cout << "Dati pretul ofertei\n";
	string pret;
	cin >> pret;
	return stoi(pret);
}

string UI::citeste_fisier() const {
	cout << "Dati denumirea fisierului\n";
	string fisier;
	cin >> fisier;
	return fisier;
}