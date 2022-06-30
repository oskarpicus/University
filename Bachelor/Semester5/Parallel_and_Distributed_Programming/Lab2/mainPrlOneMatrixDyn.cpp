//
// Created by oskar on 22/10/2021.
//

#include "My_Barrier.h"
#include <string>
#include <fstream>
#include <vector>
#include <iostream>
#include <thread>
using namespace std;

//struct My_Barrier{
//    My_Barrier(int p) {}
//    void wait(){}
//};

void readMatrices(string filename, vector<vector<int>*>*& pixels, vector<vector<double>*>*& kernel) {
    std::ifstream in(filename);
    if (!in) {
        throw "file not found";
    }

    int nrLines, nrColumns;
    in >> nrLines >> nrColumns;

    pixels = new vector<vector<int>*>(nrLines);

    for (int i = 0; i < nrLines; i++) {
        (*pixels)[i] = new vector<int>(nrColumns);
        for (int j = 0; j < nrColumns; j++) {
            in >> (*(*pixels)[i])[j];
        }
    }

    in >> nrLines >> nrColumns;

    kernel = new vector<vector<double>*>(nrLines);

    for (int i = 0; i < nrLines; i++) {
        (*kernel)[i] = new vector<double>(nrColumns);
        for (int j = 0; j < nrColumns; j++) {
            in >> (*(*kernel)[i])[j];
        }
    }
}

void writeResult(string destination, vector<vector<int>*>*& pixels) {
    ofstream out(destination);
    if (!out) {
        throw "error writing to file";
    }
    for (auto& line : *pixels) {
        for (int& element : *line) {
            out << element << " ";
        }
        out << "\n";
    }
}

bool isValidIndex(int index, int dimension) {
    return index >= 0 && index < dimension;
}

template<typename T>
T getNeighbour(vector<vector<T>*>*& matrix, int i, int j) {
    int nrLines = matrix->size(), nrColumns = matrix->at(0)->size();

    if (i < 0 && isValidIndex(j, nrColumns)) {
        return matrix->at(0)->at(j);
    }
    if (isValidIndex(i, nrLines) && j < 0) {
        return matrix->at(i)->at(0);
    }
    if (i >= nrLines && isValidIndex(j, nrColumns)) {
        return matrix->at(nrLines - 1)->at(j);
    }
    if (isValidIndex(i, nrLines) && j >= nrColumns) {
        return matrix->at(i)->at(nrColumns - 1);
    }

    // corners
    if (i < 0 && j < 0) {
        return matrix->at(0)->at(0);
    }
    if (i < 0 && j > 0) {
        return matrix->at(0)->at(nrColumns - 1);
    }
    if (i > 0 && j < 0) {
        return matrix->at(nrLines - 1)->at(0);
    }
    return matrix->at(nrLines - 1)->at(nrColumns - 1);
}

int applyTransformation(vector<vector<int>*>*& pixels, vector<vector<double>*>*& kernel, int m, int n) {
    int nrLinesKernel = (int)kernel->size(), nrColumnsKernel = (int)kernel->at(0)->size();
    int nrLines = (int)pixels->size(), nrColumns = (int)pixels->at(0)->size();

    double result = 0;
    for (int k = -nrLinesKernel / 2; k <= nrLinesKernel / 2; k++) {
        for (int l = -nrColumnsKernel / 2; l <= nrColumnsKernel / 2; l++) {
            // verify if we are still in the actual matrices
            double kernelValue =
                isValidIndex(k, nrLines) && isValidIndex(l, nrColumns)
                ? kernel->at(k)->at(l) : getNeighbour(kernel, k, l);
            double pixelValue =
                isValidIndex(m - k, (int)pixels->size()) && isValidIndex(n - l, (int)pixels->at(0)->size())
                ? pixels->at(m - k)->at(n - l) : getNeighbour(pixels, m - k, n - l);
            result += kernelValue * pixelValue;
        }
    }

    return (int)result;
}

void applyFilter(vector<vector<int>*>*& pixels, vector<vector<double>*>*& kernel, int left, int right, My_Barrier* barrier) {
    int nrColumns = (int)pixels->at(0)->size();
    int nrLines = (int)pixels->size();
    int nrLinesKernel = (int)kernel->size();

    // determine the locations in the original matrix
    int startLine = left / nrColumns, startColumn = left % nrColumns;
    int endLine = right / nrColumns, endColumn = right % nrColumns;

    // determine where to copy from
    int startColumnResult = 0, endColumnResult = nrColumns - 1;
    int startLineResult = startLine - nrLinesKernel > 0 ? startLine - nrLinesKernel : 0;
    int endLineResult = endLine + nrLinesKernel < nrLines - 1 ? endLine + nrLinesKernel : nrLines - 1;

    // copy the elements required for the computation
    auto* copy = new vector<vector<int>*>(endLineResult - startLineResult + 1);

    // storing where the actual assigned values are in the copy
    int startTransformLine = 0, startTransformColumn = 0;

    int counterLine = 0;
    for (int i = startLineResult; i <= endLineResult; i++) {
        (*copy)[counterLine] = new vector<int>(nrColumns);
        int counterColumn = 0;
        for (int j = startColumnResult; j <= endColumnResult; j++) {
            if (i == startLine && j == startColumn) {
                startTransformLine = counterLine;
                startTransformColumn = counterColumn;
            }

            (*(*copy)[counterLine])[counterColumn] = pixels->at(i)->at(j);
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
            (*(*pixels)[i])[j] = applyTransformation(copy, kernel, startTransformLine, startTransformColumn);
            startTransformColumn++;
        }
        startTransformLine++;
        startTransformColumn = 0;
    }

    for (auto& k : *copy) {
        delete k;
    }
    delete copy;
}

void convolute(vector<vector<int>*>*& pixels, vector<vector<double>*>*& kernel, int p) {
    vector<thread> threads(p);

    auto* barrier = new My_Barrier(p);

    int nrElements = (int)(pixels->size() * pixels->at(0)->size());

    int quotient = nrElements / p, remainder = nrElements % p;

    int start, end = 0;

    for (int i = 0; i < p; i++) {
        start = end;
        end = start + quotient + (remainder > 0 ? 1 : 0);
        remainder--;

        threads[i] = thread(applyFilter, ref(pixels), ref(kernel), start, end, barrier);
    }

    for (int i = 0; i < p; i++) {
        threads[i].join();
    }

    delete barrier;

    writeResult("matrices/result.txt", pixels);
}

int main(int argc, char** argv) {
    auto startTime = std::chrono::high_resolution_clock::now();
    int p = atoi(argv[1]);

    vector<vector<int>*>* pixels;
    vector<vector<double>*>* kernel;

    readMatrices("matrices/date.txt", pixels, kernel);

    convolute(pixels, kernel, p);

    for (auto& pixel : *pixels) {
        delete pixel;
    }
    delete pixels;

    for (auto& i : *kernel) {
        delete i;
    }
    delete kernel;

    auto endTime = std::chrono::high_resolution_clock::now();

    auto diff = std::chrono::duration<double, std::milli>(endTime - startTime).count();
    std::cout << diff;

    return 0;
}