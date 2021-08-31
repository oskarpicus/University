#include "lista.h"
#include <iostream>
#define _CRTDBG_MAP_ALLOC  
#include <stdlib.h>  
#include <crtdbg.h>
#include<assert.h>
using namespace std;

void test_egalitate() {
    Lista l = creare();
    Lista k = creare();
    cout << egalitate(l,k);
    distruge(l);
    distruge(k);
}

void test_intersectie() {
    Lista l=creare();
    Lista k=creare();
    auto rez = intersectie(l,k);
    tipar(rez);
    distruge(l);
    distruge(k);
    distruge(rez);
}

int main()
{
    test_egalitate();
    cout << "\n-----------\n";
    test_intersectie();
    _CrtDumpMemoryLeaks();
    return 0;
}