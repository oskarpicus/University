#include <chrono>
#include <iostream>
#include <vector>
#include <fstream>
#include <string>
#include <thread>
#define MAX 10000
using namespace std;

int nrLines, nrColumns;
int nrLinesKernel, nrColumnsKernel;

void readMatrices(string filename, int pixels[MAX][MAX], double kernel[MAX][MAX]) {
    std::ifstream in(filename);
    if (!in) {
        throw "file not found";
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

void writeResult(string destination, int pixels[MAX][MAX]) {
    ofstream out(destination);
    if (!out) {
        throw "error writing to file";
    }

    for (int i = 0 ; i < nrLines; i++) {
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
T getNeighbour(T matrix[MAX][MAX], int i, int j, int nrL, int nrC) {
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

int applyTransformation(int pixels[MAX][MAX], double kernel[MAX][MAX], int m, int n) {
    double result = 0;
    for (int k = -nrLinesKernel / 2; k <= nrLinesKernel / 2; k++) {
        for (int l = -nrColumnsKernel / 2; l <= nrColumnsKernel / 2; l++) {
            // verify if we are still in the actual matrices
            double kernelValue =
                    isValidIndex(k, nrLinesKernel) && isValidIndex(l, nrColumnsKernel)
                    ? kernel[k][l] : getNeighbour(kernel, k, l, nrLinesKernel, nrColumnsKernel);
            double pixelValue =
                    isValidIndex(m - k, nrLines) && isValidIndex(n - l, nrColumns)
                    ? pixels[m - k][n - l] : getNeighbour(pixels, m - k, n - l, nrLines, nrColumns);
            result += kernelValue * pixelValue;
        }
    }

    return (int) result;
}

void applyFilter(int pixels[MAX][MAX], double kernel[MAX][MAX], int result[MAX][MAX], int left, int right) {
    int startLine = left / nrColumns, startColumn = left % nrColumns;
    int endLine = right / nrColumns, endColumn = right % nrColumns;

    for (int i = startLine; i <= endLine; i++) {
        int endOfJ = (i == endLine ? endColumn : nrColumns);
        int startOfJ = (i == startLine ? startColumn : 0);
        for (int j = startOfJ; j < endOfJ; j++) {
            result[i][j] = applyTransformation(pixels, kernel, i, j);
        }
    }
}

void convolute(int pixels[MAX][MAX], double kernel[MAX][MAX], int p) {
    vector<thread> threads(p);

    int result[MAX][MAX];

    int nrElements = nrLines * nrColumns;

    int quotient = nrElements / p, remainder = nrElements % p;

    int start, end = 0;

    for (int i = 0; i < p; i++) {
        start = end;
        end = start + quotient + (remainder > 0 ? 1 : 0);
        remainder--;

        threads[i] = thread(applyFilter, ref(pixels), ref(kernel), ref(result), start, end);
    }

    for (int i = 0; i < p; i++) {
        threads[i].join();
    }

    writeResult("../matrices/result.txt", result);
}


int main(int argc, char** argv) {
    auto startTime = std::chrono::high_resolution_clock::now();

    int p = atoi(argv[1]);

    int pixels[MAX][MAX];
    double kernel[MAX][MAX];

    readMatrices("../matrices/date.txt", pixels, kernel);

    convolute(pixels, kernel, p);

    auto endTime = std::chrono::high_resolution_clock::now();

    auto diff = std::chrono::duration<double, std::milli>(endTime - startTime).count();
    std::cout<<diff;

    return 0;
}