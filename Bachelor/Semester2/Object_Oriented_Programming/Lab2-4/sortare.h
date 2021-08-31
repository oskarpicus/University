#pragma once

#include "MyList.h"

/*
   Tipul functie de comparare pentru 2 elemente
   pre: c1,c2 : Cheltuiala
   post: 0, daca c1==c2, 1, daca c1>c2, -1, daca c1<c2
*/
typedef int(*FunctieComparare)(Cheltuiala* c1, Cheltuiala* c2);

/**
* Sorteaza in place
* pre: l: Vector, cmpf: FunctieComparare (relatia dupa care se sorteaza), ord: int (cresc pt 1, descr pt 2)
*/
void sort(Vector* l, FunctieComparare cmpF,int ord);
