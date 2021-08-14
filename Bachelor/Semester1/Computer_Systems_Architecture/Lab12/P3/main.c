/*++
3. Se dau doua siruri continand caractere. Sa se calculeze si sa se afiseze rezultatul 
concatenarii tuturor caracterelor tip cifra zecimala din cel de-al doilea sir dupa cele 
din primul sir si invers, rezultatul concatenarii primului sir dupa al doilea.
--*/


#include <stdio.h>
#define MAX 101
// functia declarata in fisierul modulAsm.asm
int asmConcat(char sir[], int l1,char sir2[], int l2,char rezultat[]);

// functia folosita pentru a citi un sir de la tastatura
void citireSir(char sir[])
{
    printf("Dati sirul: \n");
    scanf("%s", sir);
}
int lungimeSir(char sir[])
{
    int i = 0;
    while (sir[i] != NULL)
        i++;
    return i;
}

int main()
{
    char sir1[MAX], sir2[MAX],rezultat[2*MAX-1];
    int l1, l2,lrez=0;
    rezultat[0] = 0;

    citireSir(sir1);
    citireSir(sir2);
    l1 = lungimeSir(sir1);
    l2 = lungimeSir(sir2);
    lrez = asmConcat(sir1, l1, sir2, l2, rezultat);
    printf("Rezultatul primei concatenari are lungimea %d: %s \n", lrez,rezultat);
    lrez = asmConcat(sir2, l2, sir1, l1, rezultat);
    printf("Rezultatul celei de-a doua concatenari are lungimea %d: %s \n", lrez,rezultat);
    return 0;
}
