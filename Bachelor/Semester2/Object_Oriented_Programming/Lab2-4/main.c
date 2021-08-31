/*
7. Administrator imobil
Creati o aplicatie care petmite gestiunea de cheltuieli lunare pentru apartamentele dintr-un bloc.
Fiecare cheltuiala are: numarul apartamentului, suma, tipul (apa, canal, incalzire, gaz).
Aplicatia permite:
a) Adaugarea de cheltuieli pentru un apartament
b) Modificarea unei cheltuieli existente (tipul, suma)
c) Stergere cheltuiala
d) Vizualizare lista de cheltuieli filtrat dupa o proprietate (suma, tipul,apartament)
e) Vizualizare lista de cheltuieli ordonat dupa suma sau tip (crescator/descrescator)*/

//UI
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
#include<stdio.h>
#include "cheltuiala.h"
#include "MyList.h"
#include "valid.h"
#include "service.h"
#include<string.h>

void test_all() {
	//Se apeleaza toate testele
	test_cmpTip();
	test_cmpSuma();
	test_sortare_tip();
	test_sortare_suma();
	test_copie_lista();
	test_copie();
	test_creaaza();
	test_distruge();
	test_get_nr_apartament();
	test_get_suma();
	test_get_tip();
	test_set_suma();
	test_creeaza_lista();
	test_distruge_lista();
	test_dimensiune();
	test_append();
	test_valid_nr_apartament();
	test_valid_suma();
	test_valid_tip();
	test_valid_cheltuiala();
	test_egal();
	test_creeaza_service();
	test_distruge_service();
	test_service_adaugare();
	test_get_all();
	test_atribuire();
	test_stergere();
	test_service_stergere();
	test_service_modificare(); 
	test_modificare(); 
	test_filtrare_nr_apartament();
	test_filtrare_suma();
	test_filtrare_tip();
 }


void citire_nr_apartament(int* nr_apartament)
/*
Metoda pentru citirea unui numar de apartament al unei cheltuieli
pre: nr_apartament: int*
post: se citeste valoarea lui nr_apartament
*/
{
	printf("\nDati numarul de apartament al cheltuielii\n");
	int nr=scanf("%d", nr_apartament);
	nr++;
}

void citire_suma(int* suma)
/*
Metoda pentru citirea unei sume de cheltuieli
pre: suma: int*
post: se citeste valoarea lui suma
*/
{
	printf("\nDati suma cheltuielii\n");
	int nr=scanf("%d", suma);
	nr++;
}

void citire_tip(char tip[])
/*
Metoda pentru citirea tipului unei cheltuieli
pre: nr_apartament: char*
post: se citeste valoarea lui tip
*/
{
	printf("\nDati tipul cheltuielii\n");
	int nr=scanf("%s", tip);
	nr++;
}

void ui_adaugare(Service* service)
/*
Metoda in vederea adaugarii unei cheltuieli
pre: service: Service*
post:
*/
{
	int nr_apartament, suma;
	char tip[30];
	citire_nr_apartament(&nr_apartament);
	citire_suma(&suma);
	citire_tip(tip);
	if(service_adaugare(service, nr_apartament, suma, tip)==1)
		printf("\nAdaugarea a fost facuta cu succes!\n");
	else
		printf("\nDate invalide!\n");
}

void ui_afisare(Vector lista)
/*
Metoda pentru afisarea cheltuielilor memorate
pre: service: Service*
post: se afiseaza cheltuielile memorate de service
*/
{
	//Vector lista = get_all(service);
	if (dimensiune(lista) != 0)
	{
		printf("\nCheltuielile memorate:\n");
		printf("Nr apartament / Suma / Tip\n");
		for (int i = 0; i < dimensiune(lista); i++)
			printf("%d %d %s\n", lista.elemente[i].nr_apartament, lista.elemente[i].suma, lista.elemente[i].tip);
		printf("\n");
	}
	else
		printf("Nu exista nicio cheltuiala \n\n");
}

void ui_modificare(Service* service)
/*
Metoda pentru modificarea unei cheltuieli
pre: service: Service*
post: 
*/
{
	printf("\nDati datele cheltuielii de modificat\n");
	int nr_apartament, suma;
	char tip[30];
	citire_nr_apartament(&nr_apartament);
	citire_suma(&suma);
	citire_tip(tip);
	printf("\nDati datele actualizate ale cheltuielii\n");
	int nr_apartament2, suma2;
	char tip2[30];
	citire_nr_apartament(&nr_apartament2);
	citire_suma(&suma2);
	citire_tip(tip2);
	int ok=service_modificare(service, nr_apartament, suma, tip, nr_apartament2, suma2, tip2);
	if (ok == 1)
		printf("Modificarea a fost facuta cu succes!\n");
	else printf("Cheltuiala data nu exista\n");
}

void ui_stergere(Service* service)
/*
Metoda pentru stergerea unei cheltuieli
pre: service: Service*
post:
*/
{
	printf("\nDati datele cheltuielii de sters\n");
	int nr_apartament, suma;
	char tip[30];
	citire_nr_apartament(&nr_apartament);
	citire_suma(&suma);
	citire_tip(tip);
	int check = service_stergere(service, nr_apartament, suma, tip);
	if (check == 0) printf("\nCheltuiala nu exista\n");
	else printf("\nCheltuiala a fost stearsa cu succes\n");
}


void ui_sortare_suma(Service* service)
{/* 
 Metoda pentru sortarea cheltuielilor dupa suma
 pre: service: Service*
 */
	int ord=0;
	while (ord != 1 && ord != 2)
	{
		printf("\n1-Crescator\n2-Descrescator\n");
		int nr=scanf("%d", &ord);
		nr++;
	}
	Vector l = sortare_suma(service, ord);
	printf("\nRezultatul sortarii:\n");
	ui_afisare(l);
	distruge_lista(&l);
}

void ui_sortare_tip(Service* service)
/*
Metoda pentru sortarea cheltuielilor dupa tip
pre: service: Service*
*/
{
	int ord = 0;
	while (ord != 1 && ord != 2)
	{
		printf("\n1-Crescator\n2-Descrescator\n");
		int nr=scanf("%d", &ord);
		nr++;
	}
	Vector l = sortare_tip(service, ord);
	printf("\nRezultatul sortarii:\n");
	ui_afisare(l);
	distruge_lista(&l);
}

void ui_filtrare(Service* service)
/*
Metoda pentru filtrarea cheltuielilor dupa un camp
pre: service: Service*
*/
{
	int cmd=0;
	while (!(1 <= cmd && cmd <= 3))
	{
		printf("\n1-Dupa nr. apartament\n2-Dupa suma\n3-Dupa tip\n");
		int nr=scanf("%d", &cmd);
		nr++;
	}
	Vector l;
	if (cmd == 1)
	{
		int nr_apartament;
		citire_nr_apartament(&nr_apartament);
		l = filtrare_nr_apartament(service, nr_apartament);
	}
	else if (cmd == 2)
	{
		int suma;
		citire_suma(&suma);
		l = filtrare_suma(service, suma);
	}
	else
	{
		char tip[10];
		citire_tip(tip);
		l = filtrare_tip(service, tip);
	}
	ui_afisare(l);
	distruge_lista(&l);
}



void run()
/*
Meniul
pre: adevarat
post: afiseaza si primeste comenzi de la utilizator
*/
{
	Service service = creeaza_service();
	int ruleaza = 1, cmd = 0;
	while (ruleaza != 0)
	{
		printf("1-Adaugare cheltuiala\n2-Modificare cheltuiala\n3-Stergere cheltuiala\n4-Filtrare cheltuieli dupa o proprietate\n5-Ordonare cheltuieli dupa suma\n6-Ordonare cheltuieli dupa tip\n7-Afisare cheltuieli\n0-Iesire\n");
		if (scanf("%d", &cmd) != 0)
		{
			switch (cmd)
			{
			case 1:
				ui_adaugare(&service);
				break;
			case 2:
				ui_modificare(&service);
				break;
			case 3:
				ui_stergere(&service);
				break;
			case 4:
				ui_filtrare(&service);
				break;
			case 5:
				ui_sortare_suma(&service);
				break;
			case 6:
				ui_sortare_tip(&service);
				break;
			case 7:
				ui_afisare(service.lista);
				break;
			case 0:
				ruleaza = 0;
				break;
			default:
				printf("Comanda invalida!\n");
				break; 
			}
		}
	}
	distruge_service(&service);
	
}
 
int main()
{
	test_all();
	run();
	_CrtDumpMemoryLeaks();
	return 0;
}

