#include "service.h"
#include "valid.h"
#include<assert.h>
#include "sortare.h"
#include <stdlib.h>
#include<string.h>

Service creeaza_service()
{
	Service service;
	service.lista = creeaza_lista();
	return service;
}

void distruge_service(Service* service)
{
	distruge_lista(&service->lista);
}

int service_adaugare(Service* service, int nr_apartament, int suma, char* tip)
{
	Cheltuiala c = creeaza(nr_apartament, suma, tip);
	if (valid_cheltuiala(c) == 0)
	{
		distruge(&c);
		return 0;
	}
	append(&service->lista, c); 
	return 1; //adaugarea a fost facuta cu succes
}

Vector get_all(Service* service)
{
	return service->lista;
}


int service_modificare(Service* service, int nr1, int suma1, char* tip1, int nr2, int suma2, char* tip2)
{
	Cheltuiala c1 = creeaza(nr1, suma1, tip1), c2 = creeaza(nr2, suma2, tip2);
	int checks=0,ok=0;
	checks+=valid_cheltuiala(c1)+valid_cheltuiala(c2);
	if (checks == 2)
		ok = modificare(&service->lista, c1, c2);
	//distruge(&c1);
	//distruge(&c2);
	return ok;
}

int service_stergere(Service* service, int nr_apartament, int suma, char* tip)
{
	Cheltuiala c =creeaza(nr_apartament, suma, tip);
	int check = valid_cheltuiala(c);
	if (check == 1)
		check = stergere(&service->lista, c);
	distruge(&c);
	return check;
}


Vector filtrare_nr_apartament(Service* service,int nr_apartament)
{
	Vector rez = creeaza_lista();
	for (int i = 0; i < dimensiune(service->lista); i++)
		if (get_nr_apartament(service->lista.elemente[i]) == nr_apartament)
			append(&rez, service->lista.elemente[i]);
	return rez;
}

Vector filtrare_suma(Service* service,int suma)
{
	Vector rez = creeaza_lista();
	for (int i = 0; i < dimensiune(service->lista); i++)
		if (get_suma(service->lista.elemente[i]) == suma)
			append(&rez, service->lista.elemente[i]);
	return rez;
}

Vector filtrare_tip(Service* service,char tip[])
{
	Vector rez = creeaza_lista();
	for (int i = 0; i < dimensiune(service->lista); i++)
		if (strcmp(get_tip(service->lista.elemente[i]),tip)==0)
			append(&rez, service->lista.elemente[i]);
	return rez;
}


//Sortare

int cmpSuma(Cheltuiala* c1, Cheltuiala* c2)
{
	if (c1->suma > c2->suma) return 1;
	if (c1->suma < c2->suma) return -1;
	return 0;
}

int cmpTip(Cheltuiala* c1, Cheltuiala* c2)
{
	return strcmp(c1->tip, c2->tip);
}

Vector sortare_tip(Service* service,int ord)
{
	Vector l = copie_lista(&service->lista);
	sort(&l, cmpTip,ord);
	return l;
}

Vector sortare_suma(Service* service,int ord)
{
	Vector l = copie_lista(&service->lista);
	sort(&l, cmpSuma, ord);
	return l;
}

void test_sortare_tip()
{
	Service service = creeaza_service();
	service_adaugare(&service, 19, 412, "gaz");
	service_adaugare(&service, 10, 9, "apa");
	service_adaugare(&service, 2, 7, "canal");
	Vector l=sortare_tip(&service, 1);
	assert(strcmp(l.elemente[0].tip, "apa") == 0 && l.elemente[0].nr_apartament == 10 && l.elemente[0].suma == 9);
	assert(strcmp(l.elemente[1].tip, "canal") == 0 && l.elemente[1].nr_apartament == 2 && l.elemente[1].suma == 7);
	assert(strcmp(l.elemente[2].tip, "gaz") == 0 && l.elemente[2].nr_apartament == 19 && l.elemente[2].suma == 412);
	distruge_lista(&l);
	l = sortare_tip(&service, 2);
	assert(strcmp(l.elemente[0].tip, "gaz") == 0 && l.elemente[0].nr_apartament == 19 && l.elemente[0].suma == 412);
	assert(strcmp(l.elemente[1].tip, "canal") == 0 && l.elemente[1].nr_apartament == 2 && l.elemente[1].suma == 7);
	assert(strcmp(l.elemente[2].tip, "apa") == 0 && l.elemente[2].nr_apartament == 10 && l.elemente[2].suma == 9);
	distruge_lista(&l);
	distruge_service(&service);
}

void test_sortare_suma()
{
	Service service = creeaza_service();
	service_adaugare(&service, 19, 412, "gaz");
	service_adaugare(&service, 10, 9, "apa");
	service_adaugare(&service, 2, 7, "canal");
	Vector l = sortare_suma(&service, 1);
	assert(strcmp(l.elemente[0].tip, "canal") == 0 && l.elemente[0].nr_apartament == 2 && l.elemente[0].suma == 7);
	assert(strcmp(l.elemente[1].tip, "apa") == 0 && l.elemente[1].nr_apartament == 10 && l.elemente[1].suma == 9);
	assert(strcmp(l.elemente[2].tip, "gaz") == 0 && l.elemente[2].nr_apartament == 19 && l.elemente[2].suma == 412);
	distruge_lista(&l);
	l = sortare_suma(&service, 2);
	assert(strcmp(l.elemente[0].tip, "gaz") == 0 && l.elemente[0].nr_apartament == 19 && l.elemente[0].suma == 412);
	assert(strcmp(l.elemente[1].tip, "apa") == 0 && l.elemente[1].nr_apartament == 10 && l.elemente[1].suma == 9);
	assert(strcmp(l.elemente[2].tip, "canal") == 0 && l.elemente[2].nr_apartament == 2 && l.elemente[2].suma == 7);
	distruge_lista(&l);
	distruge_service(&service);
}

void test_cmpSuma()
{
	Cheltuiala c1 = creeaza(1, 2, "apa"), c2 = creeaza(1, 2, "canal"), c3 = creeaza(1, 3, "gaz"), c4 = creeaza(1, 1, "incalzire");
	assert(cmpSuma(&c1, &c2) == 0);
	assert(cmpSuma(&c1, &c3) == -1);
	assert(cmpSuma(&c1, &c4) == 1);
}

void test_cmpTip()
{
	Cheltuiala c1 = creeaza(1, 2, "apa"), c2 = creeaza(1, 2, "apa"), c3 = creeaza(1, 3, "gaz"), c4 = creeaza(1, 1, "incalzire");
	assert(cmpTip(&c1, &c2) == 0);
	assert(cmpTip(&c1, &c3) == -1);
	assert(cmpTip(&c4, &c1) == 1);
}

void test_filtrare_nr_apartament()
{
	Service service = creeaza_service();
	service_adaugare(&service, 19, 412, "gaz");
	service_adaugare(&service, 10, 9, "apa");
	service_adaugare(&service, 19, 7, "canal");
	Vector l = filtrare_nr_apartament(&service, 19);
	assert(dimensiune(l) == 2);
	assert(l.elemente[0].nr_apartament == 19 && l.elemente[0].suma == 412 && strcmp(l.elemente[0].tip, "gaz") == 0);
	assert(l.elemente[1].nr_apartament == 19 && l.elemente[1].suma == 7 && strcmp(l.elemente[1].tip, "canal") == 0);
	distruge_lista(&l);
	l = filtrare_nr_apartament(&service, 100);
	assert(dimensiune(l) == 0);
	distruge_lista(&l);
	distruge_service(&service);
}

void test_filtrare_suma()
{
	Service service = creeaza_service();
	service_adaugare(&service, 19, 7, "gaz");
	service_adaugare(&service, 10, 9, "apa");
	service_adaugare(&service, 5, 7, "canal");
	Vector l = filtrare_suma(&service, 9);
	assert(dimensiune(l) == 1);
	assert(l.elemente[0].nr_apartament == 10 && l.elemente[0].suma == 9 && strcmp(l.elemente[0].tip, "apa") == 0);
	distruge_lista(&l);
	l = filtrare_suma(&service, 100);
	assert(dimensiune(l) == 0);
	distruge_lista(&l);
	distruge_service(&service);
}

void test_filtrare_tip()
{
	Service service = creeaza_service();
	service_adaugare(&service, 19, 7, "gaz");
	service_adaugare(&service, 5, 15, "gaz");
	service_adaugare(&service, 10, 9, "apa");
	Vector l = filtrare_tip(&service, "gaz");
	assert(dimensiune(l) == 2);
	assert(l.elemente[0].nr_apartament == 19 && l.elemente[0].suma == 7 && strcmp(l.elemente[0].tip, "gaz") == 0);
	assert(l.elemente[1].nr_apartament == 5 && l.elemente[1].suma == 15 && strcmp(l.elemente[1].tip, "gaz") == 0);
	distruge_lista(&l);
	l = filtrare_tip(&service, "incalzire");
	assert(dimensiune(l) == 0);
	distruge_lista(&l);
	distruge_service(&service);
}


void test_creeaza_service()
{
	Service service = creeaza_service();
	assert(service.lista.dim == 0);
	distruge_service(&service);
}

void test_distruge_service()
{
	Service service = creeaza_service();
	distruge_service(&service);
	assert(service.lista.capacitate == -1);
	assert(service.lista.elemente == NULL);
	assert(service.lista.dim == -1);
}

void test_service_adaugare()
{
	Service service = creeaza_service();
	assert(service_adaugare(&service, 14, 42, "apa") == 1);
	assert(service_adaugare(&service, 14, 42, "kkk") == 0);
	distruge_service(&service);
}

void test_get_all()
{
	Service service = creeaza_service();
	service_adaugare(&service, 10, 9, "apa");
	service_adaugare(&service, 19, 412, "gaz");
	service_adaugare(&service, 2, 7, "canal");
	Vector lista = get_all(&service);
	assert(lista.dim == 3);
	Cheltuiala c1 = creeaza(10, 9, "apa"), c2 = creeaza(19, 412, "gaz"), c3 = creeaza(2, 7, "canal");
	assert(egal(&c1, &lista.elemente[0]) == 0);
	assert(egal(&c2, &lista.elemente[1]) == 0);
	assert(egal(&c3, &lista.elemente[2]) == 0);
	distruge_service(&service);
	distruge(&c1);
	distruge(&c2);
	distruge(&c3);
}

void test_service_modificare()
{
	Service service = creeaza_service();
	service_adaugare(&service, 10, 9, "apa");
	service_adaugare(&service, 19, 412, "gaz");
	service_adaugare(&service, 2, 7, "canal");
	assert(service_modificare(&service, 11, 11, "apa", 32, 4, "gaz") == 0);
	assert(service_modificare(&service, 10, 9, "apa", 11, 11, "canal") == 1);
	distruge_service(&service);
}

void test_service_stergere()
{
	Service service = creeaza_service();
	service_adaugare(&service, 10, 9, "apa");
	service_adaugare(&service, 19, 412, "gaz");
	service_adaugare(&service, 2, 7, "canal");
	assert(service_stergere(&service, 19, 412, "g") == 0);
	assert(service_stergere(&service, 19, 412, "gaz") == 1);
	assert(dimensiune(service.lista) == 2);
	distruge_service(&service);
}