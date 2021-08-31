
output: App.o Dictionar.o IteratorDictionar.o TestScurt.o TestExtins.o
	g++ -Wall -g -std=c++17 App.o Dictionar.o IteratorDictionar.o TestScurt.o TestExtins.o -o output

App.o: App.cpp
	g++ -Wall -g -std=c++17 -c App.cpp

Dictionar.o: Dictionar.h Dictionar.cpp
	g++ -Wall -g -std=c++17 -c Dictionar.cpp

IteratorDictionar.o: IteratorDictionar.h IteratorDictionar.cpp
	g++ -Wall -g -std=c++17 -c IteratorDictionar.cpp

TestScurt.o: TestScurt.h TestScurt.cpp
	g++ -Wall -g -std=c++17 -c TestScurt.cpp

TestExtins.o: TestExtins.h TestExtins.cpp
	g++ -Wall -g -std=c++17 -c TestExtins.cpp

clean:
	rm *.o output
