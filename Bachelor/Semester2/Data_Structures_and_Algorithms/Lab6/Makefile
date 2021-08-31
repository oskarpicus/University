
output: App.o Matrice.o TestExtins.o TestScurt.o
	g++ -Wall -g -std=c++17 App.o Matrice.o TestExtins.o TestScurt.o -o output

App.o: App.cpp
	g++ -Wall -g -std=c++17 -c App.cpp

Matrice.o: Matrice.h Matrice.cpp
	g++ -Wall -g -std=c++17 -c Matrice.cpp

TestExtins.o: TestExtins.h TestExtins.cpp
	g++ -Wall -g -std=c++17 -c TestExtins.cpp

TestScurt.o: TestScurt.h TestScurt.cpp
	g++ -Wall -g -std=c++17 -c TestScurt.cpp

clean:
	rm *.o output
