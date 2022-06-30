#include <iostream>
#include <mpi.h>
#include <chrono>
#include "NumberReader.h"
#include "Number.h"
#include "NumbersAddition.h"
#include "NumberWriter.h"
using namespace std;

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

int main(int argc, char** argv) {
	auto startTime = std::chrono::high_resolution_clock::now();

	MPI_Status status;
	Number* nr1 = nullptr, * nr2 = nullptr, * result = nullptr;

	MPI_Init(NULL, NULL);

	int p, rank;
	MPI_Comm_size(MPI_COMM_WORLD, &p);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	if (rank == 0) {
		NumberReader reader = NumberReader();
		nr1 = reader.readNumber("../../Numar1.txt");
		nr2 = reader.readNumber("../../Numar2.txt");

		// equal the sizes
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

		int n = nr1->getLength();
		int quotient = n / (p - 1), remainder = n % (p - 1), start = 0, end = 0;

		for (int i = 1; i < p; i++) {
			end = start + quotient + (remainder > 0 ? 1 : 0);
			remainder--;

			int dimNr = end - start;

			MPI_Send(&dimNr, 1, MPI_INT, i, 0, MPI_COMM_WORLD);

			MPI_Send(nr1->getDigits() + start, dimNr, MPI_UNSIGNED_SHORT, i, 0, MPI_COMM_WORLD);
			MPI_Send(nr2->getDigits() + start, dimNr, MPI_UNSIGNED_SHORT, i, 0, MPI_COMM_WORLD);

			start = end;
		}

		// retrieve the result
		uint16_t* digitsResult = new uint16_t[n + 1];
		int current = 0;
		for (int i = 1; i < p; i++) {
			int dimCurrentRes;
			MPI_Recv(&dimCurrentRes, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
			MPI_Recv(digitsResult + current, dimCurrentRes, MPI_UNSIGNED_SHORT, i, 0, MPI_COMM_WORLD, &status);
			current += dimCurrentRes;
		}

		result = new Number(digitsResult, current);

		NumberWriter().write(result, "Rezultat.txt");
	}
	else {
		int dim;
		MPI_Recv(&dim, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);

		uint16_t* digitsNr1, * digitsNr2;

		// get the numbers
		digitsNr1 = new uint16_t[dim];
		MPI_Recv(digitsNr1, dim, MPI_UNSIGNED_SHORT, 0, 0, MPI_COMM_WORLD, &status);
		digitsNr2 = new uint16_t[dim];
		MPI_Recv(digitsNr2, dim, MPI_UNSIGNED_SHORT, 0, 0, MPI_COMM_WORLD, &status);

		nr1 = new Number(digitsNr1, dim);
		nr2 = new Number(digitsNr2, dim);

		// perform the addition
		NumbersAddition addition = NumbersAddition();
		result = addition.add(nr1, nr2);

		// receive carry
		int carry = 0;
		if (rank != 1) {
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
			if (carry != 0) {
				result->decrementLength();
			}
		}

		// send the result to master
		int dimRes = result->getLength();
		MPI_Send(&dimRes, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
		MPI_Send(result->getDigits(), dimRes, MPI_UNSIGNED_SHORT, 0, 0, MPI_COMM_WORLD);
	}

	delete nr1;
	delete nr2;
	delete result;

	MPI_Finalize();

	if (rank == 0) {
		auto endTime = std::chrono::high_resolution_clock::now();
		auto diff = std::chrono::duration<double, std::milli>(endTime - startTime).count();
		std::cout << diff;
	}

	return 0;
}