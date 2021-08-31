/*
TAD Matrice - reprezentare sub forma unei matrice rare (triplete de forma <linie, coloană,
valoare> (valoare!=0), memorate folosind o TD cu rezolvare coliziuni prin liste independente.
*/

#include <iostream>
#include "Matrice.h"
#include "TestExtins.h"
#include "TestScurt.h"
#define _CRTDBG_MAP_ALLOC  
#include <stdlib.h>  
using namespace std;


int main() {

		testAll();
		testAllExtins();
		 
	cout << "End\n";
	return 0;
}
