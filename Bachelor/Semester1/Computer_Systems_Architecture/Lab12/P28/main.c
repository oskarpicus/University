/*++
28.Se citeste de la tastatura un sir de caractere (litere mici si litere mari, cifre, caractere speciale, etc). Sa se formeze 
un sir nou doar cu literele mici si un sir nou doar cu literele mari. Sa se afiseze cele 2 siruri rezultate pe ecran.
--*/


#include <stdio.h>
#define MAX 101

void * litere_mici(char s[], int l,char rez[]);
void * litere_mari(char s[], int l, char rez[]);

int lungime(char s[])
{
	int i = 0;
	while (s[i] != NULL)
		i++;
	return i;
}

int main()
{
	char s[MAX],rez[MAX];
	int l;
	rez[0] = 0;
	printf("Dati sirul \n");
	scanf("%s", s);
	l = lungime(s);
	printf("Sir cu litere mici: %s \n", litere_mici(s, l, rez));
	rez[0] = 0;
	printf("Sir cu litere mari: %s \n", litere_mari(s, l, rez));
	return 0;
}