#include<stdio.h>
#include<stdlib.h>
 typedef struct pereche
{
	int nod1, nod2;
}Pereche;
int main()
{
	FILE* f,*g,*h;
	f = fopen("list.txt", "r");
	g = fopen("adiacenta.txt", "w");
	h = fopen("incidenta.txt", "w");
	int n, m,i,ok,aux,j,k;
	fscanf(f, "%d%d", &n, &m);
	Pereche* v;
	int** a,**b;
	a = (int**)(malloc(n * sizeof(int*))); //matricea de adiacenta
	b = (int**)(malloc(n * sizeof(int*))); //matricea de incidenta
	v = (Pereche*)(malloc(m * sizeof(Pereche))); //aici pun perechile de noduri (muchii)
	for (i = 0;i < m;i++)
		fscanf(f, "%d %d", &v[i].nod1, &v[i].nod2); //citesc muchiile

	
	for (i = 0;i < n;i++) {

		b[i] = (int*)(malloc(m * sizeof(int))); //aloc spatiu pentru matricea de incidenta
		for (j = 0;j < m;j++)
			b[i][j] = 0; //initializez matricea de incidenta

		a[i] = (int*)(malloc(n * sizeof(int))); //aloc spatiu pentru matricea de adiacenta
		for (j = 0;j < n;j++)
			a[i][j] = 0; //initializez matricea de adiacenta
	}

	//ordonez perechile de muchii
	do { 
		ok = 0;
		for(i=0;i<m-1;i++)
			if(v[i].nod1>v[i+1].nod1||(v[i].nod1==v[i+1].nod1 && v[i].nod2>v[i+1].nod2))
			{
				ok = 1;
				aux = v[i].nod1; v[i].nod1 = v[i + 1].nod1; v[i+1].nod1 = aux;
				aux = v[i].nod2; v[i].nod2 = v[i + 1].nod2; v[i + 1].nod2 = aux;
			}
	} while (ok == 1);

	
	for (i = 0;i < m;i++) //parcurg muchiile (pt matricea de adiacenta)
	{
		a[v[i].nod1][v[i].nod2] = 1;
		a[v[i].nod2][v[i].nod1] = 1;
	}

	fprintf(g, "%d\n", n);
	fprintf(h, "%d\n", n);

	//afisez matricea de adiacenta
	for (i = 0;i < n;i++)
	{
		for (j = 0;j < n;j++)
			fprintf(g, "%d ", a[i][j]);
		fprintf(g, "\n");
	}

	k = 0;
	for (i = 0; i < n; i++)
		for (j =i+1; j < n; j++) //parcurg deasupra diagonalei principale matricei de adiacenta
			if (a[i][j] == 1)
			{
				b[i][k] = 1;
				b[j][k] = 1;
				k++;
			}

	//afisez matricea de incidenta
	for (i = 0;i < n;i++)
	{
		for (j = 0;j < m;j++)
			fprintf(h, "%d ", b[i][j]);
		fprintf(h, "\n");
	}

	fclose(g);
	fclose(h);

	g = fopen("adiacenta2.txt", "w");

	fprintf(g, "%d\n", n);

	for (i = 0; i < n; i++) for (j = 0; j < n; j++) a[i][j] = 0; //resetez matricea de adiacenta

	//construiesc matricea de adiacenta
	int nod1, nod2;
	for (j = 0; j < m; j++)
	{
		nod1 = -1; nod2 = -1;
		for (i = 0; i < n; i++)
			if (b[i][j] == 1)
				if (nod1 == -1) nod1 = i;
				else nod2 = i;

		a[nod1][nod2] = 1;
		a[nod2][nod1] = 1;
	}

	//afisez matricea de adiacenta
	for (i = 0; i < n; i++)
	{
		for (j = 0; j < n; j++)
			fprintf(g, "%d ", a[i][j]);
		fprintf(g, "\n");
	}

	fclose(g);
	fclose(f);
	f = fopen("lista.out", "w");

	fprintf(f, "%d %d\n", n, m);

	//obtin lista de adiacenta - muchiile vor fi ordonate
	for (i = 0; i < n; i++)
		for (j = i + 1; j < n; j++)
			if (a[i][j] == 1)
				fprintf(f, "%d %d\n", i, j);

	//eliberez memoria alocata
	for (i = 0; i < n; i++) free(a[i]);
	free(a);
	free(v);
	for (i = 0;i < n;i++) free(b[i]);
	free(b);
	fclose(f);
	return 0;
}