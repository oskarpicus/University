#include "sortare.h"

void sort(Vector* l, FunctieComparare cmpF,int ord)
{
	int ok = 0;
	do {
		ok = 0;
		for (int i = 0; i < dimensiune(*l) - 1; i++)
		{
			Cheltuiala c1 = l->elemente[i], c2 = l->elemente[i + 1];
			if ((ord == 1 && cmpF(&c1, &c2) > 0) || (ord == 2 && cmpF(&c1, &c2) < 0))
			{
				ok = 1;
				Cheltuiala aux;
				aux = l->elemente[i]; l->elemente[i] = l->elemente[i + 1]; l->elemente[i + 1] = aux;
			}
		}
	} while (ok == 1);
}