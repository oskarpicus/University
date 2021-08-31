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

	int varfuri, arce, sursa; //nr varfuri, nr arce, nodul sursa
	int inceput = 0, l = 1; //indicele de inceput al cozii, respectiv lungimea sa
	int curent = 0; //nodul curent de cautat drum de la nodul sursa
	fscanf(f, "%d%d%d", &varfuri, &arce, &sursa);
	Pereche* v = (Pereche*)malloc(sizeof(Pereche) * arce);
	for (int i = 0; i < arce; i++)
		fscanf(f, "%d%d", &v[i].nod1, &v[i].nod2);

	int* coada = (int*)malloc(sizeof(int) * varfuri);
	int* lungime = (int*)malloc(sizeof(int) * varfuri);
	int* parinte = (int*)malloc(sizeof(int) * varfuri);


	//algoritmul lui Moore
	//initializam lungimile cu infinit (0 pentru nodul sursa)
	for (int i = 0; i < varfuri; i++) lungime[i] = INT_MAX;
	lungime[sursa] = 0;

	coada[0] = sursa; //introducem nodul sursa in coada
	while (inceput!=l) //cat timp coada nu este vida
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


	for (int i = 0; i < varfuri; i++)
		printf("%d ", lungime[i]);



	//Moore drum
	while (curent != varfuri) //parcurgem toate varfurile
	{
		int k = lungime[curent];
		int l = k;
		if (k == INT_MAX)
		{
			//nu exista drum de la sursa la curent
			curent++;
			continue;
		}
		//construim in u un vector cu nodurile ce compun drumul de la sursa la curent
		int* u = (int*)malloc(sizeof(int) * (k+1)); 
		u[k] = curent;
		while (k != 0)
		{
			u[k - 1] = parinte[u[k]]; k--;
		}
		fprintf(g, "%d ", lungime[curent]+1);
		//for (int i = 0; i < varfuri && u[i]>=0; i++)
		//	fprintf(g,"%d ", u[i]);

		for (int i = 0; i <=l; i++)
			fprintf(g, "%d ", u[i]);

		fprintf(g,"\n");
		free(u);
		curent++;
	}

	free(coada);
	free(lungime);
	free(parinte);
	free(v);
	fclose(f);
	fclose(g);
	return 0;
}