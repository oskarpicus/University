#include "MyList.h"
#include<stdlib.h>
#include<assert.h>
#include<string.h>
#include<stdio.h>
#define CAPACITATE 1

Vector creeaza_lista()
{
	Vector v; //neinitializat
	v.capacitate = CAPACITATE;
	v.dim = 0;
	v.elemente = (Cheltuiala*)malloc(sizeof(Cheltuiala) * v.capacitate);
	return v;
}
 
void distruge_lista(Vector* lista)
{
	for (int i = 0; i < lista->dim; i++)
		distruge(&(lista->elemente[i]));
	free(lista->elemente);
	//pentru a evita accesarea unei liste distruse
	lista->elemente = NULL;
	lista->dim = -1;
	lista->capacitate = -1;
}


int dimensiune(Vector lista)
{
	return lista.dim;
}

void append(Vector* lista, Cheltuiala cheltuiala)
{
	if (lista->dim == lista->capacitate)
	{
		Cheltuiala* nou = (Cheltuiala*)malloc(sizeof(Cheltuiala) * 2 * lista->capacitate);
		for (int i = 0; i < lista->dim; i++)
			nou[i] = lista->elemente[i];
		//dealocare vector vechi
		free(lista->elemente);
		lista->elemente = nou;
		lista->capacitate *= 2;
	}
	lista->elemente[lista->dim] = cheltuiala;
	lista->dim += 1;
}

int modificare(Vector* lista, Cheltuiala c1, Cheltuiala c2)
{
	for(int i=0;i<lista->dim;i++)
		if (egal(&lista->elemente[i], &c1) == 0)
		{
			atribuire(&lista->elemente[i], &c2);
			return 1;
		}
	return 0;
}

int stergere(Vector* lista, Cheltuiala c)
{
	int ok = 0,poz=0; //presupun ca nu exista
	for (int i = 0; i < dimensiune(*lista) && ok == 0; i++)
		if (egal(&c, &lista->elemente[i]) == 0)
		{
			ok = 1; poz = i;
		}
	if (ok == 0) return 0;
	for (int i = poz; i < dimensiune(*lista) - 1; i++)
		lista->elemente[i] = lista->elemente[i + 1];
	lista->dim -= 1;
	return 1;
}

Vector copie_lista(Vector* l)
{
	Vector lista = creeaza_lista();
	for (int i = 0; i < dimensiune(*l); i++)
	{
		Cheltuiala c = l->elemente[i];
		append(&lista, copie(&c));
	}
	return lista;
}

void test_modificare()
{
	Vector lista = creeaza_lista();
	Cheltuiala c1 = creeaza(12, 42, "apa"), c2 = creeaza(9, 2, "gaz"), c3 = creeaza(4, 19, "incalzire");
	append(&lista, c1);
	assert(modificare(&lista, c2, c1) == 0); //c2 nu se gaseste in lista
	append(&lista, c2);
	assert(modificare(&lista, c2, c3) == 1); //c2 se gaseste in lista, e inlocuit cu c3
	assert(egal(&(lista.elemente[1]),&c3) == 0);
	distruge_lista(&lista);
}

void test_creeaza_lista()
{
	Vector lista = creeaza_lista();
	assert(lista.capacitate==CAPACITATE);
	assert(lista.dim == 0);
	distruge_lista(&lista);
}

void test_distruge_lista()
{
	Vector lista = creeaza_lista();
	distruge_lista(&lista);
	assert(lista.capacitate == -1);
	assert(lista.elemente == NULL);
	assert(lista.dim == -1);
}



void test_dimensiune()
{
	Vector lista = creeaza_lista();
	assert(dimensiune(lista) == 0);
	Cheltuiala c = creeaza(12, 12, "apa");
	append(&lista, c);
	assert(dimensiune(lista) == 1);
	distruge_lista(&lista);
}

void test_append()
{
	Vector lista = creeaza_lista();
	Cheltuiala c = creeaza(12, 9, "apa"),c2=creeaza(10,2,"canal");
	append(&lista, c);
	append(&lista, c2);
	assert(lista.elemente[0].nr_apartament == 12 && lista.elemente[0].suma == 9 && strcmp(lista.elemente[0].tip, "apa") == 0);
	assert(lista.elemente[1].nr_apartament == 10 && lista.elemente[1].suma == 2 && strcmp(lista.elemente[1].tip, "canal") == 0);
	distruge_lista(&lista);
}

void test_stergere()
{
	Vector lista = creeaza_lista();
	Cheltuiala c = creeaza(12, 9, "apa"),c2=creeaza(10,10,"gaz");
	append(&lista, c);
	assert(stergere(&lista, c2) == 0);
	assert(stergere(&lista, c) == 1);
	assert(dimensiune(lista) == 0);
	distruge_lista(&lista);
	distruge(&c);
	distruge(&c2);
}

void test_copie_lista()
{
	Vector l = creeaza_lista();
	append(&l, creeaza(5, 5, "canal"));
	Vector l2 = copie_lista(&l);
	assert(dimensiune(l2) == dimensiune(l));
	distruge_lista(&l);
	distruge_lista(&l2);
}