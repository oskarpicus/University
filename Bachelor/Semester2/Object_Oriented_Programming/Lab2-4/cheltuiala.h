#pragma once
typedef struct {
	int nr_apartament;
	int suma;
	char tip[30];
}Cheltuiala;

/*
Metoda pentru crearea unei noi cheltuieli de apartament
pre: nr_apartament: int, suma: int, tip: char *
post: cheltuiala
*/
Cheltuiala creeaza(int nr_apartament, int suma, char* tip);


/*
Metoda pentru dealocarea memoriei ocupata de o cheltuiala
pre: cheltuiala - Cheltuiala
post: se distruge cheltuiala
*/
void distruge(Cheltuiala* cheltuiala);

/*
Getter pentru numarul de apartament al unei cheltuieli
pre: cheltuiala - Cheltuiala
post: cheltuiala.nr_apartament
*/
int get_nr_apartament(Cheltuiala cheltuiala);


/*
Getter pentru suma unei cheltuieli
pre: cheltuiala - Cheltuiala
post: cheltuiala.suma
*/
int get_suma(Cheltuiala cheltuiala);

/*
Getter pentru tipul unei cheltuieli
pre: cheltuiala - Cheltuiala
post: cheltuiala.tip
*/
char* get_tip(Cheltuiala cheltuiala);

/*
Setter pentru suma unei cheltuieli
pre: cheltuiala - Cheltuiala, suma - int
post: cheltuiala'.suma=suma
*/
void set_suma(Cheltuiala* cheltuiala,int suma);


/*
Defineste egalitatea dintre doua cheltuieli
pre: c1,c2: Cheltuiala*
post: 0, daca c1.tip=c2.tip si c1.suma=c2.suma si c1.nr_apartament=c2.nr_apartament, 1, altfel
*/
int egal(Cheltuiala* c1, Cheltuiala* c2);

/*
Defineste atribuirea dintre doua cheltuieli
pre: c1,c2: Cheltuiala*
post: c1'=c2
*/
void atribuire(Cheltuiala* c1, Cheltuiala* c2);

/*
Creeaza o copie (deepcopy) a unei cheltuieli
pre: c: Cheltuiala*
post: c'=c
*/
Cheltuiala copie(Cheltuiala* c);

void test_creaaza();

void test_distruge();

void test_get_nr_apartament();

void test_get_suma();

void test_get_tip();

void test_set_suma();

void test_set_tip();

void test_egal();

void test_atribuire();

void test_copie();