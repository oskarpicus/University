#include<stdio.h>
#include<stdlib.h>
int main()
{
	FILE* f, * g;
	f = fopen("in.txt", "r");
	g = fopen("out.txt", "w");
	int n, m,*vf,x,y,i;
	fscanf(f, "%d%d", &n, &m);
	vf = (int*)malloc(n * sizeof(int));
	for (i = 0;i < n;i++) vf[i] = 0;
	for (i = 0;i < m;i++) //citesc muchiile
	{
		fscanf(f, "%d%d", &x, &y);
		vf[x]++; vf[y]++;
	}
	for (i = 0;i < n;i++)
		if (vf[i] == 0)
			fprintf(g, "%d ", i);
	free(vf);
	fclose(f);
	fclose(g);
	return 0;
}