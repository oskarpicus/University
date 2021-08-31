#include "teste.h"
#include "domain.h"
#include "repository.h"
#include "valid.h"
#include "service.h"
#include <iostream>
#include <assert.h>
#include "WishList.h"

void Teste::test_Oferta() const
{
	Oferta o = Oferta("denumire", "destinatie", "tip", 40);
	assert(o.get_denumire() == "denumire");
	assert(o.get_destinatie() == "destinatie");
	assert(o.get_tip() == "tip");
	assert(o.get_pret() == 40);
}

void Teste::test_get_pret() const
{
	Oferta o = Oferta("denumire", "destinatie", "tip", 40);
	assert(o.get_pret() == 40);
}

void Teste::test_get_denumire() const
{
	Oferta o = Oferta("denumire", "destinatie", "tip", 40);
	assert(o.get_denumire()=="denumire");
}
void Teste::test_get_tip() const
{
	Oferta o = Oferta("denumire", "destinatie", "tip", 40);
	assert(o.get_tip()=="tip");
}
void Teste::test_get_destinatie() const
{
	Oferta o = Oferta("denumire", "destinatie", "tip", 40);
	assert(o.get_destinatie() == "destinatie");
}

void Teste::test_set_pret() const
{
	Oferta o = Oferta("denumire", "destinatie", "tip", 40);
	o.set_pret(100);
	assert(o.get_pret() == 100);
}

void Teste::test_set_denumire() const
{
	Oferta o = Oferta("denumire", "destinatie", "tip", 40);
	o.set_denumire("Vacanta");
	assert(o.get_denumire() == "Vacanta");
}

void Teste::test_set_destinatie() const
{
	Oferta o = Oferta("denumire", "destinatie", "tip", 40);
	o.set_destinatie("Venetia");
	assert(o.get_destinatie() == "Venetia");
}

void Teste::test_set_tip() const
{
	Oferta o = Oferta("denumire", "destinatie", "tip", 40);
	o.set_tip("cultura");
	assert(o.get_tip() == "cultura");
}

void Teste::test_operator_egal() const
{
	Oferta o1 = Oferta("a", "a", "a", 1), o2 = Oferta("a", "a", "a", 1), o3 = Oferta("b", "a", "a", 1);
	assert(o1 == o2);
	assert(!(o1 == o3));
}

void Teste::test_repo_adauga() const
{
	Repo repo;
	Oferta o1 = Oferta("a", "a", "a", 1), o2 = Oferta("b", "b", "a", 2), o3 = Oferta("c", "a", "a", 3);
	repo.repo_adauga(o1);
	repo.repo_adauga(o2);
	repo.repo_adauga(o3);
	try {
		repo.repo_adauga(o3);
		assert(false);
	}
	catch (RepoError& e)
	{
		assert(e.get_mesaj() == "Oferta a fost adaugata deja!\n");
	}
	assert(repo.repo_dim() == 3);
	/*assert(repo.repo_get_oferta(0) == o1);
	assert(repo.repo_get_oferta(1) == o2);
	assert(repo.repo_get_oferta(2) == o3);*/
	assert(repo.repo_cauta(o1));
	assert(repo.repo_cauta(o2));
	assert(repo.repo_cauta(o3));
} 

void Teste::test_repo_cauta() const
{
	Repo repo;
	Oferta o1 = Oferta("a", "b", "c", 1), o2 = Oferta("e", "f", "g", 2);
	repo.repo_adauga(o1);
	assert(repo.repo_dim() == 1);
	assert(repo.repo_cauta(o1) == true);
	assert(repo.repo_cauta(o2) == false);
	assert(repo.repo_dim() == 1);
}

void Teste::test_repo_get_all() const
{
	Repo repo;
	Oferta o1 = Oferta("a", "b", "c", 1);
	repo.repo_adauga(o1);
	//const vector<Oferta>& v = repo.repo_get_all();
	//assert(v[0].get_denumire()=="a"&&v[0].get_destinatie()=="b"&&v[0].get_pret()==1&&v[0].get_tip()=="c");
}


void Teste::test_repo_sterge() const
{
	Repo repo;
	Oferta o1 = Oferta("a", "a", "a", 1), o2 = Oferta("b", "b", "b", 2),o3=Oferta("c","c","c",3);
	repo.repo_adauga(o1);
	repo.repo_adauga(o2);
	repo.repo_adauga(o3);
	assert(repo.repo_dim() == 3);
	repo.repo_sterge(o1);
	assert(repo.repo_cauta(o1) == false);
	assert(repo.repo_dim() == 2);
	try {
		repo.repo_sterge(o1);
		assert(false);
	}
	catch (RepoError & e)
	{
		assert(e.get_mesaj() == "Oferta nu exista!\n");
	}
	repo.repo_sterge(o3);
	assert(repo.repo_cauta(o3) == false);
	assert(repo.repo_dim() == 1);
	repo.repo_sterge(o2);
	assert(repo.repo_cauta(o2) == false);
	assert(repo.repo_dim() == 0);
	try { //sterg din container vid
		repo.repo_sterge(o1);
		assert(false);
	}
	catch (RepoError& e)
	{
		assert(e.get_mesaj() == "Oferta nu exista!\n");
	}
}

void Teste::test_repo_modifica() const
{
	Repo repo;
	Oferta o1 = Oferta("a", "a", "a", 1), o2 = Oferta("b", "b", "b", 2),o3=Oferta("b","c","c",9),o4=Oferta("g","g","g",5);
	repo.repo_adauga(o1);
	repo.repo_adauga(o2);
	assert(repo.repo_cauta(o2) == true);
	try {
		repo.repo_modifica(o4, o1);
		assert(false);
	}
	catch (RepoError& e) {
		assert(e.get_mesaj() == "Oferta nu exista!\n");
	}
	try {
		repo.repo_modifica(o1, o2);
		assert(false);
	}
	catch (RepoError& e) {
		assert(e.get_mesaj() == "Ofertele de modificat au denumire diferita!\n");
	}
	repo.repo_modifica(o2, o3);
//	assert(repo.repo_cauta(o2) == false);
	assert(repo.repo_cauta(o3) == true);
	assert(repo.repo_get_oferta(o3.get_denumire()) == o3);
	assert(!(repo.repo_get_oferta(o3.get_denumire()) == o2));
}

void Teste::test_service_adauga() const
{
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	service.service_adauga("a", "a", "a", 20);
	service.service_adauga("b", "B", "B", 200);
	try {
		service.service_adauga("", "", "", 12);
		assert(false);
	}
	catch (ValidError) { assert(true); }
	assert(service.service_dim() == 2);
	assert(service.service_cauta("a", "a", "a", 20) == true);
	assert(service.service_cauta("b", "b", "b", 200) == true);
}

void Teste::test_service_modifica() const
{
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	service.service_adauga("a", "a", "a", 20);
	assert(service.service_cauta("a", "a", "a", 20) == true);
	try {
		service.service_modifica("a", "b", "a", 20, "b", "b", "b", 40);
		assert(false);
	}
	catch (RepoError & e)
	{
		assert(e.get_mesaj() == "Ofertele de modificat au denumire diferita!\n");
	}
	assert(service.service_cauta("a", "a", "a", 20) == true);
	service.service_modifica("a", "a", "a", 20, "a", "b", "b", 40);
	assert(service.service_cauta("a", "a", "a", 20) == true);
	//assert(service.service_cauta("b", "b", "b", 40) == true);
}

void Teste::test_service_sterge() const
{
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	service.service_adauga("a", "a", "a", 20);
	service.service_adauga("b", "b", "b", 40);
	assert(service.service_dim() == 2);
	try {
		service.service_sterge("aaa", "aaa", "aaa", 50);
		assert(false);
	}
	catch (RepoError & e)
	{
		assert(e.get_mesaj() == "Oferta nu exista!\n");
	}
	assert(service.service_dim() == 2);
	service.service_sterge("a", "a", "a", 20);
	assert(service.service_dim() == 1);
	service.service_sterge("b", "b", "b", 40);
	assert(service.service_dim() == 0);
}

void Teste::test_service_get_all() const
{
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	service.service_adauga("a", "b", "c", 20);
	const Lista<Oferta>& l = service.service_get_all();
	assert(l.size() == 1);
}


void Teste::test_service_cauta() const
{
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	service.service_adauga("a", "a", "a", 20);
	assert(service.service_cauta("a", "a", "a", 20) == true);
	//assert(service.service_cauta("a", "b", "a", 20) == false);
	assert(service.service_cauta("111", "asg", "asga", 400) == false);
}

void Teste::test_valideaza_oferta() const
{
	Valid valid;
	Oferta o{ "a","b","c",10 };
	assert(valid.valideaza_oferta(o));
	try
	{
		o = { "","a","",10 };
		valid.valideaza_oferta(o);
		assert(false);
	}
	catch (ValidError & e) {
		assert(e.get_msg() == "Denumirea nu trebuie sa fie vida!\nTipul nu trebuie sa fie vid!\n");
	}
	try {
		o = { "a","b","as",-32 };
		valid.valideaza_oferta(o);
		assert(false);
	}
	catch (ValidError & e) {
		assert(e.get_msg() == "Pretul trebuie sa fie pozitiv!\n");
	}
	try {
		o = { "","","",-4 };
		valid.valideaza_oferta(o);
		assert(false);
	}
	catch (ValidError & e)
	{
		assert(e.get_msg() == "Denumirea nu trebuie sa fie vida!\nDestinatia nu trebuie sa fie vida!\nTipul nu trebuie sa fie vid!\nPretul trebuie sa fie pozitiv!\n");
	}
}

void Teste::test_valideaza_denumire() const
{
	Valid valid;
	Oferta o{ "a","b","c",10 };
	assert(valid.valideaza_denumire(o));
	o={ "","a","",10 };
	assert(!valid.valideaza_denumire(o));
}

void Teste::test_valideaza_tip() const
{
	Valid valid;
	Oferta o{ "a","b","c",10 };
	assert(valid.valideaza_tip(o));
	o = { "","a","",10 };
	assert(!valid.valideaza_tip(o));
}

void Teste::test_valideaza_pret() const
{
	Valid valid;
	Oferta o{ "a","b","c",10 };
	assert(valid.valideaza_pret(o));
	o = { "b","a","g",-23 };
	assert(!valid.valideaza_pret(o));
}

void Teste::test_valideaza_destinatie() const
{
	Valid valid;
	Oferta o{ "a","b","c",10 };
	assert(valid.valideaza_destinatie(o));
	o = { "gd","","asf",10 };
	assert(!valid.valideaza_destinatie(o));
}

void Teste::test_filtreaza_destinatie() const {
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	service.service_adauga("1", "1", "1", 1);
	service.service_adauga("2", "1", "5", 1);
	service.service_adauga("a", "a", "a", 7);
	assert(service.service_filtrare_destinatie("1").size() == 2);
	assert(service.service_filtrare_destinatie("a").size() == 1);
	assert(service.service_filtrare_destinatie("5").size() == 0);
}

void Teste::test_filtreaza_pret() const {
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	service.service_adauga("1", "1", "1", 1);
	service.service_adauga("a", "a", "a", 7);
	service.service_adauga("2", "1", "5", 1);
	assert(service.service_filtrare_pret(1).size() == 2);
	assert(service.service_filtrare_pret(7).size() == 1);
	assert(service.service_filtrare_pret(42).size() == 0);
}

void Teste::test_sortare_denumire() const {
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	service.service_adauga("a", "a", "a", 7);
	service.service_adauga("2", "1", "5", 1);
	service.service_adauga("1", "1", "1", 1);
	Lista<Oferta> l=service.sortare_denumire();
	int i = 0;
	for (auto& o : l)
	{
		if (i == 0)
			assert(o == Oferta("1", "1", "1", 1));
		else if (i == 1)
			assert(o == Oferta("2", "1", "5", 1));
		else assert(o == Oferta("a", "a", "a", 7));
		i++;
	}
}

void Teste::test_sortare_pret() const {
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	service.service_adauga("a", "a", "a", 7);
	service.service_adauga("2", "1", "5", 1);
	service.service_adauga("1", "1", "1", 5);
	Lista<Oferta> l = service.sortare_pret();
	int i = 0;
	for (auto& o : l)
	{
		if (i == 1)
			assert(o == Oferta("1", "1", "1", 5));
		else if (i == 0)
			assert(o == Oferta("2", "1", "5", 1));
		else assert(o == Oferta("a", "a", "a", 7));
		i++;
	}
}

void Teste::test_sortare_tip() const {
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	service.service_adauga("a", "a", "a", 7);
	service.service_adauga("2", "1", "5", 1);
	service.service_adauga("1", "1", "1", 5);
	Lista<Oferta> l = service.sortare_tip();
	int i = 0;
	for (auto& o : l)
	{
		if (i ==0)
			assert(o == Oferta("1", "1", "1", 5));
		else if (i == 1)
			assert(o == Oferta("2", "1", "5", 1));
		else assert(o == Oferta("a", "a", "a", 7));
		i++;
	}
}

void Teste::test_sortare_destinatie() const {
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	service.service_adauga("a", "a", "a", 7);
	service.service_adauga("2", "1", "5", 1);
	service.service_adauga("1", "0", "1", 5);
	Lista<Oferta> l = service.sortare_destinatie();
	int i = 0;
	for (auto& o : l)
	{
		if (i == 0)
			assert(o == Oferta("1", "0", "1", 5));
		else if (i == 1)
			assert(o == Oferta("2", "1", "5", 1));
		else assert(o == Oferta("a", "a", "a", 7));
		i++;
	}
}

void Teste::test_service_get_wishlist_size() const {
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	assert(service.service_get_wishlist_size() == 0); 
	service.service_genereaza_wishlist(5); //adaugam 5 oferte
	assert(service.service_get_wishlist_size() == 5);
	service.service_golire_wishlist(); //golim 
	assert(service.service_get_wishlist_size() == 0);
}

void Teste::test_repo_get_wishlist() const {
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	service.service_genereaza_wishlist(5);
	assert(service.service_get_wishlist_size() == 5);
	//const auto& lista = repo.repo_get_wishlist();
	//assert(lista.size() == 5);
}

void Teste::test_adauga_wishlist() const { //adauga din repo
	WishList wish;
	wish.adauga_wishlist(Oferta{ "1","1","1",1 });
	assert(wish.get_wishlist_size() == 1);
	try {
		wish.adauga_wishlist(Oferta{ "1","1","1",1 });
		assert(false);
	}
	catch (WishListError& e) {
		assert(e.get_mesaj() == "Oferta exista deja in wishlist!\n");
	}
	assert(wish.get_wishlist_size() == 1);
}

void Teste::test_service_adauga_wishlist() const {
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	assert(service.service_get_wishlist_size() == 0);
	try {
		service.service_adauga_wishlist("1");
		assert(false);
	}
	catch (RepoError& e) {
		assert(e.get_mesaj() == "Oferta cu aceasta denumire nu exista!\n");
	}
	assert(service.service_get_wishlist_size() == 0);
	service.service_adauga("1", "1", "1", 1);
	service.service_adauga_wishlist("1");
	assert(service.service_get_wishlist_size() == 1);
}

void Teste::test_service_golire() const {
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	assert(service.service_get_wishlist_size() == 0);
	service.service_genereaza_wishlist(5); //adaugam 5 oferte
	assert(service.service_get_wishlist_size() == 5);
	service.service_golire_wishlist();
	assert(service.service_get_wishlist_size() == 0);
}

void Teste::test_service_genereaza_wishlist() const {
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	service.service_genereaza_wishlist(5); //adaugam 5 oferte
	for (const auto& o : service.service_get_wishlist())
	{
		assert(1 <= o.get_pret() && o.get_pret() <= 3000);
		assert(1<=o.get_denumire().length() && o.get_denumire().length()<=15);
		assert(1 <= o.get_destinatie().length() && o.get_destinatie().length() <= 15);
		assert(1 <= o.get_destinatie().length() && o.get_destinatie().length() <= 15);
	}
}

void Teste::test_service_exporta_wishlist() const {
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	service.service_genereaza_wishlist(5); //adaugam 5 oferte
	service.service_exporta_wishlist_CSV("fisier_test.txt");
	ifstream f("fisier_test.txt");
	int i = 0;
	while (i!=5) {
		string str;
		f >> str;
		//verificam ca exista virgulele
		size_t found = str.find(",");
		assert(found != 0 && found != str.size());
		found = str.find(",", found + 1);
		assert(found != 0 && found != str.size());
		found = str.find(",", found + 1);
		assert(found != 0 && found != str.size());
		i++;
	}
	f.close();
	service.service_exporta_wishlist_HTML("fisier_test.html");
}

void Teste::test_service_wishlist_pret_total() const {
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	assert(service.service_wishlist_pret_total() == 0);
	service.service_adauga("1", "1", "1", 1);
	service.service_adauga_wishlist("1");
	assert(service.service_wishlist_pret_total() == 1);
	service.service_adauga("2", "2", "2", 66);
	service.service_adauga_wishlist("2");
	assert(service.service_wishlist_pret_total() == 67);
	service.service_golire_wishlist();
	assert(service.service_wishlist_pret_total() == 0);
	service.service_genereaza_wishlist(2);
	assert(service.service_wishlist_pret_total() > 0);
}

void Teste::test_undo() const {
	Repo repo;
	Valid valid;
	Service service{ repo, valid };
	try {
		service.undo();
		assert(false);
	}
	catch (OfertaError& e) {
		assert(e.get_mesaj() == "Nu se mai poate face undo!\n");
	}
	service.service_adauga("1", "1", "1", 1);
	service.service_adauga("2", "2", "2", 2);
	assert(service.service_cauta("2", "2", "2", 2) == true);
	service.service_sterge("2", "2", "2", 2);
	assert(service.service_cauta("2","2","2",2) == false);
	service.undo();
	assert(service.service_cauta("2", "2", "2", 2) == true);
	service.service_modifica("2", "2", "2", 2, "2", "22", "22",22);
	assert(!(repo.repo_get_oferta("2") == Oferta("2", "2", "2", 2)));
	assert(repo.repo_get_oferta("2") == Oferta("2","22","22",22));
	service.undo();
	assert(!(repo.repo_get_oferta("2") == Oferta("2", "22", "22", 22)));
	assert(repo.repo_get_oferta("2") == Oferta("2", "2", "2", 2));
	service.undo();
	assert(service.service_cauta("2", "2", "2", 2) == false);
	service.undo();
	assert(service.service_dim() == 0);
	try {
		service.undo();
		assert(false);
	}
	catch (OfertaError& e) {
		assert(e.get_mesaj() == "Nu se mai poate face undo!\n");
	}
}

void Teste::test_fisiere() const {
	ofstream out("fisier_csv_test.txt");
	out.close(); //este un fisier gol
	RepoFile repo= { "fisier_csv_test.txt" };
	repo.repo_adauga(Oferta("a", "a", "a", 1));
	RepoFile repo2 = { "fisier_csv_test.txt" };
	assert(repo2.repo_cauta(Oferta("a", "a", "a", 1)));
	repo2.repo_modifica(Oferta("a", "a", "a", 1), Oferta("a", "A", "A", 11));
	RepoFile repo3 = { "fisier_csv_test.txt" };
	assert(repo3.repo_get_oferta("a") == Oferta("a", "A", "A", 11));
	assert(!(repo3.repo_get_oferta("a") == Oferta("a", "a", "a", 1)));
	repo3.repo_sterge(Oferta("a", "A", "A", 11));
	RepoFile repo4 = { "fisier_csv_test.txt" };
	assert(repo4.repo_cauta(Oferta("a", "A", "A", 11)) == false);
	assert(repo4.repo_dim() == 0);
	try {
		RepoFile r = { "abcdefg.txt" };
		assert(false);
	}
	catch (RepoError& e) {
		assert(e.get_mesaj() == "Nu am putut deschide fisierul abcdefg.txt");
	}
}

void Teste::run_all() const
{
	test_Oferta();
	test_get_pret();
	test_get_denumire();
	test_get_tip();
	test_get_destinatie();
	test_set_pret();
	test_set_denumire();
	test_set_tip();
	test_set_destinatie();
	test_operator_egal();
	test_repo_adauga();
	test_repo_get_all();
	test_repo_sterge();
	test_repo_modifica();
	test_repo_cauta();
	test_service_adauga();
	test_service_modifica();
	test_service_get_all();
	test_service_sterge();
	test_service_cauta();
	test_valideaza_oferta();
	test_valideaza_denumire();
	test_valideaza_tip();
	test_valideaza_pret();
	test_valideaza_destinatie();
	test_filtreaza_destinatie();
	test_filtreaza_pret();
	test_sortare_denumire();
	test_sortare_pret();
	test_sortare_tip();
	test_sortare_destinatie();
	test_service_get_wishlist_size();
	test_repo_get_wishlist();
	test_adauga_wishlist();
	test_service_adauga_wishlist();
	test_service_golire();
	test_service_genereaza_wishlist();
	test_service_exporta_wishlist();
	test_service_wishlist_pret_total();
	test_undo();
	test_fisiere();
}