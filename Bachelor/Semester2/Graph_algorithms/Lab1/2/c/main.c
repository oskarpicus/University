#include<stdio.h>
#include<stdlib.h>
#include<limits.h>
int main()
{
	FILE* f,*g;
	f = fopen("in.txt", "r");
	g = fopen("out.txt", "w");
	int n, ** a, ** b, i, j, k;
	fscanf(f, "%d", &n); //nr noduri
	a = (int**)(malloc(n * sizeof(int*))); //mat de adiacenta
	b = (int**)(malloc(n * sizeof(int*))); //mat de distante
	for (i = 0;i < n;i++)
	{
		a[i] = (int*)(malloc(n * sizeof(int)));
		b[i] = (int*)(malloc(n * sizeof(int)));
		for (j = 0;j < n;j++)
		{
			fscanf(f, "%d", &a[i][j]);
			if (a[i][j] == 0)
				b[i][j] = INT_MAX; //initializez cu "infinit"
			else
				b[i][j] = 1;
		}
	}
	
	//algoritmul lui Warshall
	for (k = 0;k < n;k++)
		for (i = 0;i < n;i++)
			for (j = 0;j < n;j++)
				if (b[i][k] + b[k][j] < b[i][j] && b[i][k] != INT_MAX && b[k][j] != INT_MAX && i != j)
					b[i][j] = b[i][k] + b[k][j];
				else if (i == j) b[i][j] = 0;

	//afisez rezultatul
	for (i = 0;i < n;i++)
	{
		for (j = 0;j < n;j++)
					if (b[i][j] != INT_MAX)
				fprintf(g, "%d ", b[i][j]);
			else
				fprintf(g, "-1 ");
		fprintf(g, "\n");
	}

	//eliberez memoria alocata
	for(i = 0;i < n;i++)
	{
		free(a[i]); free(b[i]);
	}
	free(a);
	free(b);
	fclose(f);
	fclose(g);
	return 0;
}