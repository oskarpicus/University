#include <iostream>
#include <mpi.h>
#include <chrono>
#include "NumberReader.h"
#include "Number.h"
#include "NumbersAddition.h"
#include "NumberWriter.h"
using namespace std;

uint16_t* completeWithZero(uint16_t* v, int currentLength, int desiredLength) {
	uint16_t* result = new uint16_t[currentLength + desiredLength];
	for (int i = 0; i < currentLength; i++) {
		result[i] = v[i];
	}
	for (int i = currentLength; i < desiredLength; i++) {
		result[i] = 0;
	}
	delete[] v;
	return result;
}

int getNextDiv(int n, int p) {
	while (n % p != 0) {
		n++;
	}
	n++;
	while (n % p != 0) {
		n++;
	}
	return n;
}

int main(int argc, char** argv) {
	auto startTime = std::chrono::high_resolution_clock::now();

	MPI_Status status;
	Number* nr1 = nullptr, * nr2 = nullptr, * result = nullptr;
	int n;

	MPI_Init(NULL, NULL);

	int p, rank;
	MPI_Comm_size(MPI_COMM_WORLD, &p);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	if (rank == 0) {
		// read the numbers
		NumberReader reader = NumberReader();
		nr1 = reader.readNumber("../../Numar1.txt");
		nr2 = reader.readNumber("../../Numar2.txt");

		// equal the sizes and make them divisible with p
		int dim1 = nr1->getLength(), dim2 = nr2->getLength();
		int max = dim1 > dim2 ? dim1 : dim2;
		int dimension = getNextDiv(max, p);

		if (dim1 != dimension) {
			nr1->setDigits(completeWithZero(nr1->getDigits(), nr1->getLength(), dimension));
			nr1->setLength(dimension);
		}
		if (dim2 != dimension) {
			nr2->setDigits(completeWithZero(nr2->getDigits(), nr2->getLength(), dimension));
			nr2->setLength(dimension);
		}

		// send the total number of digits to the other processes
		n = nr1->getLength();
		for (int i = 1; i < p; i++) {
			MPI_Send(&n, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
		}
	}
	else {
		// receive the total number of digits from the master process
		MPI_Recv(&n, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
	}

	// send the digits
	uint16_t* digitsNr1, * digitsNr2;
	if (rank == 0) {
		digitsNr1 = nr1->getDigits();
		digitsNr2 = nr2->getDigits();
	}
	else {
		digitsNr1 = new uint16_t[n];
		digitsNr2 = new uint16_t[n];
	}

	uint16_t* auxNr1 = new uint16_t[n / p];
	uint16_t* auxNr2 = new uint16_t[n / p];

	MPI_Scatter(digitsNr1, n / p, MPI_UNSIGNED_SHORT, auxNr1, n / p, MPI_UNSIGNED_SHORT, 0, MPI_COMM_WORLD);
	MPI_Scatter(digitsNr2, n / p, MPI_UNSIGNED_SHORT, auxNr2, n / p, MPI_UNSIGNED_SHORT, 0, MPI_COMM_WORLD);

	if (rank == 0) {
		delete nr1;
		delete nr2;
	}

	nr1 = new Number(auxNr1, n / p);
	nr2 = new Number(auxNr2, n / p);

	NumbersAddition addition = NumbersAddition();
	result = addition.add(nr1, nr2);

	// receive the carry
	int carry = 0;
	if (rank != 0) {
		MPI_Recv(&carry, 1, MPI_INT, rank - 1, 0, MPI_COMM_WORLD, &status);
		if (carry != 0) {
			uint16_t* oneDigit = new uint16_t[1];
			oneDigit[0] = 1;
			Number* one = new Number(oneDigit, 1);
			result = addition.add(result, one);
			delete one;
		}
	}

	// send the next carry
	if (rank != p - 1) {
		carry = result->getCarry();
		MPI_Send(&carry, 1, MPI_INT, rank + 1, 0, MPI_COMM_WORLD);
	}

	uint16_t* digitsResult = new uint16_t[n];
	MPI_Gather(result->getDigits(), n / p, MPI_UNSIGNED_SHORT, digitsResult, n / p, MPI_UNSIGNED_SHORT, 0, MPI_COMM_WORLD);

	if (rank == 0) {
		Number resultAll = Number(digitsResult, n);
		NumberWriter().write(&resultAll, "Rezultat.txt");
	}

	if (rank != 0) {
		delete[] digitsNr1;
		delete[] digitsNr2;
	}

	MPI_Finalize();
	
	if (rank == 0) {
		auto endTime = std::chrono::high_resolution_clock::now();
		auto diff = std::chrono::duration<double, std::milli>(endTime - startTime).count();
		std::cout << diff;
	}

	return 0;
}