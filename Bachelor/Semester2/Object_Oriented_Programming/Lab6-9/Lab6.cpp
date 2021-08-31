/*
6 Agenție de turism
Creați o aplicație care permite:
· gestiunea unei liste de oferte turistice. Oferta: denumire, destinație, tip, preț
· adăugare, ștergere, modificare și afișare oferte turistice
· căutare oferta
· filtrare oferte turistice după: destinație, preț
· sortare oferte după: denumire, destinație, tip + preț
*/


#include <iostream>
#include "repository.h"
#include "service.h"
#include "valid.h"
#include "UI.h"
#include "teste.h"
#define _CRTDBG_MAP_ALLOC  
#include <stdlib.h>  
#include <crtdbg.h>  
using namespace std;

   
int main()
{
    Teste teste;
    teste.run_all();
    {
       // Repo repo;
        RepoFile repo{ "fisier_csv.txt" };
        Valid valid;
        Service service{ repo,valid };
        UI ui{ service };
        ui.run();
    }
    _CrtDumpMemoryLeaks();
    cout << "O zi buna!\n";
    return 0;
}
 