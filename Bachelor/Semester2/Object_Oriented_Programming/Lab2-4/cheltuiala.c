#include "cheltuiala.h"
#include<stdlib.h>
#include<string.h>
#include<assert.h>


Cheltuiala creeaza(int nr_apartament, int suma, char* tip)
{
	Cheltuiala cheltuiala; //neinitializata
	cheltuiala.nr_apartament = nr_apartament;
	cheltuiala.suma = suma;
	//int nrc = strlen(tip) + 1;
	strcpy(cheltuiala.tip, tip);
	return cheltuiala;
} 


void distruge(Cheltuiala* cheltuiala)
{
	//evitare folosire dupa distrugere
	cheltuiala->nr_apartament = -1;
	cheltuiala->suma = -1;
}

int get_nr_apartament(Cheltuiala cheltuiala)
{
	return cheltuiala.nr_apartament;
}

int get_suma(Cheltuiala cheltuiala)
{
	return cheltuiala.suma;
}

char* get_tip(Cheltuiala cheltuiala)
{
	return cheltuiala.tip;
}

void set_suma(Cheltuiala* cheltuiala, int suma)
{
	cheltuiala->suma = suma;
}


int egal(Cheltuiala* c1, Cheltuiala* c2)
{
	if (c1->nr_apartament != c2->nr_apartament || c1->suma != c2->suma || strcmp(c1->tip, c2->tip) != 0)
		return 1;
	return 0;
}

void atribuire(Cheltuiala* c1, Cheltuiala* c2)
{
	c1->nr_apartament = c2->nr_apartament;
	c1->suma = c2->suma;
	strcpy(c1->tip, c2->tip);
}

Cheltuiala copie(Cheltuiala* c)
{
	return creeaza(c->nr_apartament, c->suma, c->tip);
}

//Teste

void test_egal()
{
	Cheltuiala c1 = creeaza(12, 42, "apa"), c2 = creeaza(12, 42, "apa"), c3 = creeaza(10, 42, "apa");
	assert(egal(&c1, &c2) == 0);
	assert(egal(&c1, &c3) == 1);
	distruge(&c1);
	distruge(&c2);
	distruge(&c3);
}

void test_creaaza()
{
	Cheltuiala c = creeaza(12, 42, "apa");
	assert(c.nr_apartament == 12);
	assert(c.suma == 42);
	assert(strcmp(c.tip, "apa") == 0);
	distruge(&c);
}

void test_distruge()
{
	Cheltuiala c = creeaza(12, 42, "apa");
	distruge(&c);
	assert(c.nr_apartament == -1);
	assert(c.suma == -1);
}

void test_get_nr_apartament()
{
	Cheltuiala c;
	c.nr_apartament = 123;
	assert(get_nr_apartament(c) == 123);
	c = creeaza(12, 1, "apa");
	assert(get_nr_apartament(c) == 12);
	distruge(&c);
}

void test_get_suma()
{
	Cheltuiala c;
	c.suma = 190;
	assert(get_suma(c) == 190);
	c = creeaza(12, 429, "apa");
	assert(get_suma(c) == 429);
	distruge(&c);
}

void test_get_tip()
{
	Cheltuiala c;
	c = creeaza(12, 429, "gaz");
	assert(strcmp(get_tip(c),"gaz")==0);
	distruge(&c);
}

void test_set_suma()
{
	Cheltuiala c = creeaza(12, 429, "gaz");
	set_suma(&c, 2);
	assert(get_suma(c) == 2);
	distruge(&c);
}

void test_atribuire()
{
	Cheltuiala c1 = creeaza(12, 10, "apa"), c2 = creeaza(9, 1, "gaz");
	atribuire(&c1, &c2);
	assert(egal(&c1, &c2) == 0);
	distruge(&c1);
	distruge(&c2);
}

void test_copie()
{
	Cheltuiala c1 = creeaza(12, 10, "apa");
	Cheltuiala c = copie(&c1);
	assert(c.suma == c1.suma);
	assert(c.nr_apartament == c1.nr_apartament);
	assert(strcmp(c.tip, c1.tip) == 0);
	distruge(&c1);
	distruge(&c);
}