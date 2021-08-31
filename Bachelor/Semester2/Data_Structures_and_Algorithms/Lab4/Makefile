
output: App.o IteratorMD.o MD.o TestExtins.o TestScurt.o
	g++ -Wall -g -std=c++17 App.o IteratorMD.o MD.o TestExtins.o TestScurt.o -o output

App.o: App.cpp
	g++ -Wall -g -c -std=c++17  App.cpp

IteratorMD.o: IteratorMD.h IteratorMD.cpp
	g++ -Wall -g -c -std=c++17  IteratorMD.cpp

MD.o: MD.h MD.cpp
	g++ -Wall -g -c -std=c++17  MD.cpp

TestExtins.o: TestExtins.h TestExtins.cpp
	g++ -Wall -g -c -std=c++17  TestExtins.cpp

TestScurt.o: TestScurt.h TestScurt.cpp
	g++ -Wall -g -c -std=c++17  TestScurt.cpp

clean:
	rm *.o output
