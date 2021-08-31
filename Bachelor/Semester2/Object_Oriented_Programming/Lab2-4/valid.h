#pragma once
#include "cheltuiala.h"

/*
Metoda pentru validarea numarului de apartament al unei cheltuieli
pre: cheltuiala: Cheltuiala
post: 0, daca cheltuiala.nr_apartament<=0, 1, altfel
*/
int valid_nr_apartament(Cheltuiala cheltuiala);

/*
Metoda pentru validarea sumei unei cheltuieli
pre: cheltuiala: Cheltuiala
post: 0, daca cheltuiala.suma<=0, 1, altfel
*/
int valid_suma(Cheltuiala cheltuiala);

/*
Metoda pentru validarea sumei unei cheltuieli
pre: cheltuiala: Cheltuiala
post: 0, daca cheltuiala.tip nu apartine {apa,gaz,incalzire,canal}, 1, altfel
*/
int valid_tip(Cheltuiala cheltuiala);

/*
Metoda pentru validarea sumei unei cheltuieli
pre: cheltuiala: Cheltuiala
post: 0, daca cheltuiala nu este valida, 1, altfel
*/
int valid_cheltuiala(Cheltuiala cheltuiala);

void test_valid_nr_apartament();

void test_valid_suma();

void test_valid_tip();

void test_valid_cheltuiala();