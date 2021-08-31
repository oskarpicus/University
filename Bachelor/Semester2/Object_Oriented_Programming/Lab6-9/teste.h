#pragma once
#include<fstream>
class Teste {
	/*
	Clasa de obiecte de tip Teste
	*/
private:
	void test_Oferta() const;
	void test_get_pret() const;
	void test_get_denumire() const;
	void test_get_tip() const;
	void test_get_destinatie() const;
	void test_set_pret() const;
	void test_set_denumire() const;
	void test_set_tip() const;
	void test_set_destinatie() const;
	void test_operator_egal() const;
	void test_repo_adauga() const;
	void test_repo_get_all() const;
	void test_repo_sterge() const;
	void test_repo_modifica() const;
	void test_repo_cauta() const;
	void test_service_adauga() const;
	void test_service_get_all() const;
	void test_service_modifica() const;
	void test_service_sterge() const;
	void test_service_cauta() const;
	void test_valideaza_oferta() const;
	void test_valideaza_denumire() const;
	void test_valideaza_tip() const;
	void test_valideaza_pret() const;
	void test_valideaza_destinatie() const;
	void test_filtreaza_destinatie() const;
	void test_filtreaza_pret() const;
	void test_sortare_denumire() const;
	void test_sortare_pret() const;
	void test_sortare_tip() const;
	void test_sortare_destinatie() const;
	void test_service_get_wishlist_size() const;
	void test_repo_get_wishlist() const;
	void test_adauga_wishlist() const;
	void test_service_adauga_wishlist() const;
	void test_service_golire() const;
	void test_service_genereaza_wishlist() const;
	void test_service_exporta_wishlist() const;
	void test_service_wishlist_pret_total() const;
	void test_undo() const;
	void test_fisiere() const;
public:
	void run_all() const;
};