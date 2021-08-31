#include "valid.h"
#include<string.h>
#include<assert.h>


int valid_nr_apartament(Cheltuiala cheltuiala)
{
	//int nr = get_nr_apartament(cheltuiala);
	return get_nr_apartament(cheltuiala) <= 0 ? 0 : 1;
}

int valid_suma(Cheltuiala cheltuiala)
{
	//int nr = get_suma(cheltuiala);
	return get_suma(cheltuiala) <= 0 ? 0 : 1;
}

int valid_tip(Cheltuiala cheltuiala)
{
	char* tip = get_tip(cheltuiala);
	if (strcmp(tip, "apa") != 0)
		if (strcmp(tip, "gaz") != 0)
			if (strcmp(tip, "incalzire") != 0)
				if (strcmp(tip, "canal") != 0)
					return 0;
	return 1;
}

int valid_cheltuiala(Cheltuiala cheltuiala)
{
	int checks = 0;
	checks += valid_nr_apartament(cheltuiala);
	checks += valid_suma(cheltuiala);
	checks += valid_tip(cheltuiala);
	return checks == 3 ? 1 : 0;
}

void test_valid_nr_apartament()
{
	Cheltuiala c = creeaza(12, 10, "apa");
	assert(valid_nr_apartament(c) == 1);
	Cheltuiala c2 = creeaza(-32, 12, "gaz");
	assert(valid_nr_apartament(c2) == 0);
	distruge(&c);
	distruge(&c2);
}

void test_valid_suma()
{
	Cheltuiala c = creeaza(12, 10, "apa");
	assert(valid_suma(c) == 1);
	Cheltuiala c2 = creeaza(-32, -12, "gaz");
	assert(valid_suma(c2) == 0);
	distruge(&c);
	distruge(&c2);
}

void test_valid_tip()
{
	Cheltuiala c = creeaza(12, 10, "apa");
	assert(valid_tip(c) == 1);
	Cheltuiala c2 = creeaza(-32, 12, "r2qhfha");
	assert(valid_tip(c2) == 0);
	distruge(&c);
	distruge(&c2);
}

void test_valid_cheltuiala()
{
	Cheltuiala c = creeaza(12, 10, "apa");
	assert(valid_cheltuiala(c) == 1);
	Cheltuiala c2 = creeaza(-32, -12, "r2qhfha");
	assert(valid_cheltuiala(c2) == 0);
	distruge(&c);
	distruge(&c2);
}