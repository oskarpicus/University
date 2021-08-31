#include <QtWidgets/QApplication>
#include "repository.h"
#include "service.h"
#include "valid.h"
#include "UI.h"
#include "UIGUI.h"
#include "teste.h"
#include <iostream>
#define _CRTDBG_MAP_ALLOC  
#include <stdlib.h>  
#include <crtdbg.h>  
using namespace std;

int main(int argc, char *argv[])
{
    
    {
        QApplication a(argc, argv);
   
        Teste teste;
        teste.run_all();

        // Repo repo;
        RepoFile repo{ "fisier_csv.txt" };
        Valid valid;
        Service service{ repo,valid };
        //UI ui{ service };
       // ui.run();
        UIGUI ui{ service };
        ui.show();
        a.exec();
    }
    
    _CrtDumpMemoryLeaks();
    return 0;
}
