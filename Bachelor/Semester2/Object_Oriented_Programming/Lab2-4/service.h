#pragma once
#include "MyList.h"

typedef struct {
	Vector lista;
}Service;

/*
Metoda pentru crearea unui obiect de tip Service
pre: adevarat
post: service: Service
*/
Service creeaza_service();

/*
Metoda pentru a elibera spatiul alocat pentru un obiect de tip Service
pre: service: Service*
post: spatiul alocat pentru service este eliberat
*/
void distruge_service(Service* service);

/*
Metoda in vederea adaugarii unei noi cheltuieli
pre: service: Service*, nr_apartament, int, suma, int, tip, char*
post: 1, daca adaugarea a fost facuta cu succes, 0, altfel
*/
int service_adaugare(Service* service, int nr_apartament, int suma, char* tip);

/*
Metoda pentru obtinerea tuturor cheltuielilor
pre: service: Service*
post: lista: Vector
*/
Vector get_all(Service* service);

/*
Metoda pentru modificarea unei cheltuieli
pre: service: Service*, nr1,nr2,suma1,suma2: int, tip1,tip2: char*
post: 1, daca prima cheltuiala se regaseste in lista de cheltuieli, 0, altfel (sau daca sunt alte erori)
*/
int service_modificare(Service* service, int nr1, int suma1, char* tip1, int nr2, int suma2, char* tip2);

/*
Metoda pentru stergerea unei cheltuieli
pre: service: Service*, nr_apartament, int, suma, int, tip, char*
post: service_stergere=1, daca Cheltuiala(nr_apartament,suma,tip) exista si ca atare este stearsa, 0, in caz contrar
*/
int service_stergere(Service* service, int nr_apartament, int suma, char* tip);

/*
Metoda pentru sortarea cheltuielilor dupa tip
pre: service: Service*, ord: int (1 pt cresc, 2 pt descr)
post: service-> lista sortara dupa tip cresc sau descr (in functie de ord)
*/
Vector sortare_tip(Service* service,int ord);

/*
Metoda pentru sortarea cheltuielilor dupa suma
pre: service: Service*, ord: int (1 pt cresc, 2 pt descr)
post: service-> lista sortara dupa suma cresc sau descr (in functie de ord)
*/
Vector sortare_suma(Service* service,int ord);

/*
Metoda pentru filtrarea cheltuielilor dupa nr. de apartament
pre: service: Service*, nr_apartament: int
post: service.lista'= { service.lista.elemente[i] | 0<=i<dimensiune(service.lista) si service.lista.elemente[i].nr_apartament=nr_apartament }
*/
Vector filtrare_nr_apartament(Service* service,int nr_apartament);

/*
Metoda pentru filtrarea cheltuielilor dupa suma
pre: service: Service*, suma: int
post: service.lista'= { service.lista.elemente[i] | 0<=i<dimensiune(service.lista) si service.lista.elemente[i].suma=suma}
*/
Vector filtrare_suma(Service* service,int suma);


/*
Metoda pentru filtrarea cheltuielilor dupa nr. de apartament
pre: service: Service*, tip: char*
post: service.lista'= { service.lista.elemente[i] | 0<=i<dimensiune(service.lista) si service.lista.elemente[i].tip }
*/
Vector filtrare_tip(Service* service,char tip[]);

void test_creeaza_service();

void test_distruge_service();

void test_service_adaugare();

void test_get_all();

void test_service_modificare();

void test_service_stergere();

void test_cmpTip();

void test_cmpSuma();

void test_sortare_suma();

void test_sortare_tip();

void test_filtrare_nr_apartament();

void test_filtrare_suma();

void test_filtrare_tip();
