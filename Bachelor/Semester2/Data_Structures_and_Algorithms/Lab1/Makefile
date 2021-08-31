
output: App.o TAD.o TestExtins.o TestScurt.o Colectie.o IteratorColectie.o
	g++ -Wall -g -std=c++17 App.o TAD.o TestExtins.o TestScurt.o Colectie.o IteratorColectie.o -o output

App.o: App.cpp
	g++ -Wall -g -std=c++17 -c App.cpp

TAD.o: TAD.h TAD.h
	g++ -Wall -g -std=c++17 -c TAD.cpp

TestExtins.o: TestExtins.h TestExtins.cpp
	g++ -Wall -g -std=c++17 -c TestExtins.cpp

TestScurt.o: TestScurt.h TestScurt.cpp
	g++ -Wall -g -std=c++17 -c TestScurt.cpp

Colectie.o: Colectie.h Colectie.cpp
	g++ -Wall -g -std=c++17 -c Colectie.cpp

IteratorColectie.o: IteratorColectie.h IteratorColectie.cpp
	g++ -Wall -g -std=c++17 -c IteratorColectie.cpp

clean:
	rm *.o output
