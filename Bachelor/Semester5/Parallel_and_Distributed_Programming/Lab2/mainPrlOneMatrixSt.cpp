//
// Created by oskar on 22/10/2021.
//

#include "My_Barrier.h"
#include <string>
#include <fstream>
#include <vector>
#include <iostream>
#include <thread>
#define MAX 10000
#define MAX_KERNEL 5
using namespace std;

int pixels[MAX][MAX];
double kernel[MAX_KERNEL][MAX_KERNEL];
int nrLines, nrColumns;
int nrLinesKernel, nrColumnsKernel;

void readMatrices(string filename) {
    std::ifstream in(filename);
    if (!in) {
        throw exception();
    }

    in >> nrLines >> nrColumns;

    for (int i = 0; i < nrLines; i++) {
        for (int j = 0; j < nrColumns; j++) {
            in >> pixels[i][j];
        }
    }

    in >> nrLinesKernel >> nrColumnsKernel;

    for (int i = 0; i < nrLinesKernel; i++) {
        for (int j = 0; j < nrColumnsKernel; j++) {
            in >> kernel[i][j];
        }
    }
}

void writeResult(string destination) {
    ofstream out(destination);
    if (!out) {
        throw exception();
    }

    for (int i = 0; i < nrLines; i++) {
        for (int j = 0; j < nrColumns; j++) {
            out << pixels[i][j] << " ";
        }
        out << "\n";
    }
}

bool isValidIndex(int index, int dimension) {
    return index >= 0 && index < dimension;
}

template<typename T>
T getNeighbour(T matrix[MAX_KERNEL][MAX_KERNEL], int i, int j, int nrL, int nrC) {
    if (i < 0 && isValidIndex(j, nrC)) {
        return matrix[0][j];
    }
    if (isValidIndex(i, nrL) && j < 0) {
        return matrix[i][0];
    }
    if (i >= nrL && isValidIndex(j, nrC)) {
        return matrix[nrLines - 1][j];
    }
    if (isValidIndex(i, nrL) && j >= nrC) {
        return matrix[i][nrColumns - 1];
    }

    // corners
    if (i < 0 && j < 0) {
        return matrix[0][0];
    }
    if (i < 0 && j > 0) {
        return matrix[0][nrC - 1];
    }
    if (i > 0 && j < 0) {
        return matrix[nrL - 1][0];
    }
    return matrix[nrL - 1][nrC - 1];
}

int getNeighbour(vector<vector<int>>& matrix, int i, int j, int nrL, int nrC) {
    if (i < 0 && isValidIndex(j, nrC)) {
        return matrix[0][j];
    }
    if (isValidIndex(i, nrL) && j < 0) {
        return matrix[i][0];
    }
    if (i >= nrL && isValidIndex(j, nrC)) {
        return matrix[nrL - 1][j];
    }
    if (isValidIndex(i, nrL) && j >= nrC) {
        return matrix[i][nrC - 1];
    }

    // corners
    if (i < 0 && j < 0) {
        return matrix[0][0];
    }
    if (i < 0 && j > 0) {
        return matrix[0][nrC - 1];
    }
    if (i > 0 && j < 0) {
        return matrix[nrL - 1][0];
    }
    return matrix[nrL - 1][nrC - 1];
}

int applyTransformation(vector<vector<int>>& pixels, int m, int n) {
    double result = 0;
    for (int k = -nrLinesKernel / 2; k <= nrLinesKernel / 2; k++) {
        for (int l = -nrColumnsKernel / 2; l <= nrColumnsKernel / 2; l++) {
            // verify if we are still in the actual matrices
            double kernelValue =
                isValidIndex(k, nrLinesKernel) && isValidIndex(l, nrColumnsKernel)
                ? kernel[k][l] : getNeighbour(kernel, k, l, nrLinesKernel, nrColumnsKernel);
            double pixelValue =
                isValidIndex(m - k, pixels.size()) && isValidIndex(n - l, pixels[0].size())
                ? pixels[m - k][n - l] : getNeighbour(pixels, m - k, n - l, pixels.size(), pixels[0].size());
            result += kernelValue * pixelValue;
        }
    }

    return (int)result;
}

void applyFilter(int left, int right, My_Barrier* barrier) {
    // determine the locations in the original matrix
    int startLine = left / nrColumns, startColumn = left % nrColumns;
    int endLine = right / nrColumns, endColumn = right % nrColumns;

    // determine where to copy from
    int startColumnResult = 0, endColumnResult = nrColumns - 1;
    int startLineResult = startLine - nrLinesKernel > 0 ? startLine - nrLinesKernel : 0;
    int endLineResult = endLine + nrLinesKernel < nrLines - 1 ? endLine + nrLinesKernel : nrLines - 1;

    // copy the elements required for the computation
    vector<vector<int>> copy(endLineResult - startLineResult + 1);

    // storing where the actual assigned values are in the copy
    int startTransformLine = 0, startTransformColumn = 0;

    int counterLine = 0;
    for (int i = startLineResult; i <= endLineResult; i++) {
        copy[counterLine] = vector<int>(nrColumns);
        int counterColumn = 0;
        for (int j = startColumnResult; j <= endColumnResult; j++) {
            if (i == startLine && j == startColumn) {
                startTransformLine = counterLine;
                startTransformColumn = counterColumn;
            }

            copy[counterLine][counterColumn] = pixels[i][j];
            counterColumn++;
        }
        counterLine++;
    }

    barrier->wait();

    // apply transformations
    for (int i = startLine; i <= endLine; i++) {
        int endOfJ = (i == endLine ? endColumn : nrColumns);
        int startOfJ = (i == startLine ? startColumn : 0);
        for (int j = startOfJ; j < endOfJ; j++) {
            pixels[i][j] = applyTransformation(copy, startTransformLine, startTransformColumn);
            startTransformColumn++;
        }
        startTransformLine++;
        startTransformColumn = 0;
    }
}

void convolute(int p) {
    vector<thread> threads(p);

    auto* barrier = new My_Barrier(p);

    int nrElements = nrLines * nrColumns;

    int quotient = nrElements / p, remainder = nrElements % p;

    int start, end = 0;

    for (int i = 0; i < p; i++) {
        start = end;
        end = start + quotient + (remainder > 0 ? 1 : 0);
        remainder--;

        threads[i] = thread(applyFilter, start, end, barrier);
    }

    for (int i = 0; i < p; i++) {
        threads[i].join();
    }

    delete barrier;

    writeResult("matrices/result.txt");
}

int main(int argc, char** argv) {
    auto startTime = std::chrono::high_resolution_clock::now();
    int p = atoi(argv[1]);

    readMatrices("matrices/date.txt");

    convolute(p);

    auto endTime = std::chrono::high_resolution_clock::now();

    auto diff = std::chrono::duration<double, std::milli>(endTime - startTime).count();
    std::cout << diff;

    return 0;
}