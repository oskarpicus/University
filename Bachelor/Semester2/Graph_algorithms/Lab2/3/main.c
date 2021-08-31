#include<stdio.h>
#include<stdlib.h>
#include<limits.h>
#define CAPACITATE 100
typedef struct Nod {
	int x, y; //coordonatele, un nod reprezinta un spatiu
}Nod;

typedef struct Vector { //SD Vector Dinamic
	Nod* v;
	int lg; //lungime vector
	int capacitate;
}Vector;

void adauga(Vector* vector, Nod* nod)
/*
Adauga un nou nod in vectorul de noduri
pre: vector: Vector*, nod: Nod*
post: vector->v' = vector->v + nod
daca vector->lg=vector->capacitate ==> redimensionare (se dubleaza capacitatea)
*/
{
	if (vector->lg == vector->capacitate)
	{
		//redimensionare
		Nod* vnou = (Nod*)malloc(sizeof(Nod) * vector->capacitate * 2);
		for (int i = 0; i < vector->lg; i++)
			vnou[i] = vector->v[i];
		free(vector->v);
		vector->v = vnou;
		vector->capacitate *= 2;
	}
	vector->v[vector->lg] = *nod;
	vector->lg++;
}

int pozitie(Vector* vector, Nod* nod)
/*
Functie pentru determinarea pozitiei in vector a unui nod
pre: vector: Vector*, nod: Nod*
post: i astfel incat vector->v[i]=nod
*/
{
	int poz = -1;
	for (int i = 0; i < vector->lg && poz==-1; i++) 
		if(vector->v[i].x==nod->x && vector->v[i].y==nod->y)
			poz = i;
	return poz;
}


int main()
{
	FILE* f, * g;
	f = fopen("in.txt", "r");
	g = fopen("out.txt", "w");
	Nod start, finish;
	Vector vector;
	vector.lg = 0;
	vector.capacitate = CAPACITATE;
	vector.v = (Nod*)malloc(sizeof(Nod) * vector.capacitate);
	int poz_start=0, poz_finish=0;


	char c='/';
	for (int i = 0; c!=EOF; i++) //citim pana se termina fisierul
	{
		for(int j=0;(c=fgetc(f))!='\n' && c!=EOF;j++) //citim pana ajungem la new line si adaugam noduri (=spatii) in vector 
			if (c == 'S') { start.x = i; start.y = j; poz_start = vector.lg; adauga(&vector, &start); }
			else if (c == 'F') { finish.x = i; finish.y = j; poz_finish = vector.lg; adauga(&vector, &finish); }
			else if (c == ' ')
			{
				Nod nod; nod.x = i; nod.y = j; adauga(&vector, &nod);
			}
	}

	int varfuri = vector.lg,inceput=0,l=1;
	Nod* coada = (Nod*)malloc(sizeof(Nod) * varfuri);
	int* lungime = (int*)malloc(sizeof(int) * varfuri);
	Nod* parinte = (Nod*)malloc(sizeof(Nod) * varfuri);

	//algoritmul lui Moore
	//initializam lungimile cu infinit (0 pentru nodul sursa)
	for (int i = 0; i < varfuri; i++) lungime[i] = INT_MAX;
	lungime[poz_start] = 0;

	coada[0] = start;
	while (inceput != l)
	{
		Nod nod = coada[inceput];
		inceput++;
		int poz = pozitie(&vector, &nod); //pozitia lui nod in vector
		for (int i = 0; i < vector.lg; i++)
		{
			Nod nod_curent = vector.v[i];
			if ((nod_curent.x == nod.x && nod_curent.y == nod.y - 1) || (nod_curent.x == nod.x - 1 && nod_curent.y == nod.y) || (nod_curent.x == nod.x + 1 && nod_curent.y == nod.y) || (nod_curent.x == nod.x && nod_curent.y == nod.y + 1)) //este vecin, deci are muchie (este adiacent)
			{
				int p = pozitie(&vector, &nod_curent);
				if (lungime[p] == INT_MAX)
				{
					parinte[p] = nod;
					lungime[p] = lungime[poz] + 1;
					coada[l] = nod_curent;
					l++;
				}
			}
		}
	}
	
	//Moore drum
	int k = lungime[poz_finish];
	l = k;
	if (k == INT_MAX)
	{
		//nu exista solutie la labirint
		printf("Nu exista solutie\n");
		return 1;
	}
	Nod* u = (Nod*)malloc(sizeof(Nod) * (k + 1));
	u[k] = finish;
	while (k != 0)
	{
		u[k - 1] = parinte[pozitie(&vector,&u[k])];
		k--;
	}
	fprintf(g,"%d\n", l+1);
	for (int i = 0; i <= l; i++)
		fprintf(g,"%d %d\n", u[i].x, u[i].y);


	free(coada);
	free(lungime);
	free(parinte);
	free(u);
	free(vector.v);
	fclose(f);
	fclose(g);
	return 0;
}