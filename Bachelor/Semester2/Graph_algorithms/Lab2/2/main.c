#include<limits.h>
#include<stdio.h>
#include <stdlib.h>
typedef struct Pereche {
	int nod1, nod2;
}Pereche; //reprezinta un arc
int main()
{
	FILE* f, * g;
	f = fopen("in.txt", "r");
	g = fopen("out.txt", "w");

	int varfuri, arce; //nr varfuri, nr arce
	int inceput = 0, l = 1; //indicele de inceput al cozii, respectiv lungimea sa
	int curent = 0,sursa=0; //nodul curent de cautat drum de la nodul sursa, nodul sursa
	fscanf(f, "%d%d", &varfuri, &arce);
	Pereche* v = (Pereche*)malloc(sizeof(Pereche) * arce);
	for (int i = 0; i < arce; i++)
		fscanf(f, "%d%d", &v[i].nod1, &v[i].nod2);

	int* coada = (int*)malloc(sizeof(int) * varfuri);
	int* lungime = (int*)malloc(sizeof(int) * varfuri);
	int* parinte = (int*)malloc(sizeof(int) * varfuri);
	int** a = (int**)malloc(sizeof(int*) * varfuri);
	for (int i = 0; i < varfuri; i++) 
	{ a[i] = (int*)malloc(sizeof(int) * varfuri);
	for (int j = 0; j < varfuri; j++)
		a[i][j] = 0;
	}


	while (sursa != varfuri)

	{
		//algoritmul lui Moore
	//initializam lungimile cu infinit (0 pentru nodul sursa)
		for (int i = 0; i < varfuri; i++)
			lungime[i] = INT_MAX;
		lungime[sursa] = 0;

		coada[0] = sursa; //introducem nodul sursa in coada
		inceput = 0;
		l = 1;
		while (inceput != l) //cat timp coada nu este vida
		{
			//scoatem din coada un nod - principiul FIFO
			int x = coada[inceput];
			inceput += 1;
			for (int i = 0; i < arce; i++)
			{
				if (v[i].nod1 == x) //daca gasim un nod adiacent cu x
				{
					int y = v[i].nod2;
					if (lungime[y] == INT_MAX)
					{
						//actualizam lungimea drumului si parintele nodului y
						parinte[y] = x;
						lungime[y] = lungime[x] + 1;
						coada[l] = y; //introducem pe y in coada
						l++;
					}
				}
			}
		}

		//determinam varfurile din care se poate ajunge din sursa
		for (int i = 0; i < varfuri; i++)
			if (lungime[i] != INT_MAX && lungime[i] >= 0)
				a[sursa][i] = 1;
			else
				a[sursa][i] = 0;
		sursa++;
	}

	//afisam rezultatul
	for (int i = 0; i < varfuri; i++)
	{
		for (int j = 0; j < varfuri; j++)
			fprintf(g, "%d ", a[i][j]);
		if (i != varfuri - 1)
			fprintf(g, "\n");
	}

	//eliberam memoria alocata
	free(coada);
	free(lungime);
	free(parinte);
	free(v);
	for (int i = 0; i < varfuri; i++) free(a[i]);
	free(a);
	fclose(f);
	fclose(g);
	return 0;
}