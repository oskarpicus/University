#include <mpi.h>
#include <iostream>
#include <chrono>
#include "NumberReader.h"
#include "NumbersAddition.h"
#include "NumberWriter.h"

int main(int argc, char** argv) {
    auto startTime = std::chrono::high_resolution_clock::now();

    NumberReader reader = NumberReader();
    NumberWriter writer = NumberWriter();

    Number* nr1 = reader.readNumber("Numar1.txt");
    Number* nr2 = reader.readNumber("Numar2.txt");

    NumbersAddition addition = NumbersAddition();
    
    Number* result = addition.add(nr1, nr2);

    writer.write(result, "Rezultat.txt");

    delete nr1;
    delete nr2;
    delete result;

    auto endTime = std::chrono::high_resolution_clock::now();
    auto diff = std::chrono::duration<double, std::milli>(endTime - startTime).count();
    std::cout << diff;

    return 0;
}