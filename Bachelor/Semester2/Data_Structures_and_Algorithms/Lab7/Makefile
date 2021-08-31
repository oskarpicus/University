
output: App.o DO.o Iterator.o TestScurt.o TestExtins.o
	g++ -Wall -g -std=c++17 App.o DO.o Iterator.o TestScurt.o TestExtins.o -o output

App.o: App.cpp
	g++ -Wall -g -c -std=c++17 App.cpp

DO.o: DO.h DO.cpp
	g++ -Wall -g -c -std=c++17 DO.cpp

Iterator.o: Iterator.h Iterator.cpp
	g++ -Wall -g -c -std=c++17 Iterator.cpp

TestScurt.o: TestScurt.h TestScurt.cpp
	g++ -Wall -g -c -std=c++17 TestScurt.cpp

TestExtins.o: TestExtins.h TestExtins.cpp
	g++ -Wall -g -c -std=c++17 TestExtins.cpp

clean:
	rm *.o output
