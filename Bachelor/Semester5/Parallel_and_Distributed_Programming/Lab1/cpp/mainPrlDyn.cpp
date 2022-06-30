#include <chrono>
#include <iostream>
#include <vector>
#include <fstream>
#include <string>
#include <thread>
using namespace std;

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

bool isValidIndex(int index, int dimension) {
    return index >= 0 && index < dimension;
}

template<typename T>
T getNeighbour(std::vector<std::vector<T>*>* matrix, int i, int j) {
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

int applyTransformation(vector<vector<int>*>* pixels, vector<vector<double>*>* kernel, int m, int n) {
    int nrLines = (int)kernel->size(), nrColumns = (int)kernel->at(0)->size();
    double result = 0;
    for (int k = -nrLines / 2; k <= nrLines / 2; k++) {
        for (int l = -nrColumns / 2; l <= nrColumns / 2; l++) {
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

    return (int) result;
}

void applyFilter(vector<vector<int>*>* pixels, vector<vector<double>*>* kernel, vector<vector<int>*>* result, int left, int right) {
    int nrColumns = (int)pixels->at(0)->size();
    int startLine = left / nrColumns, startColumn = left % nrColumns;
    int endLine = right / nrColumns, endColumn = right % nrColumns;

    for (int i = startLine; i <= endLine; i++) {
        int endOfJ = (i == endLine ? endColumn : nrColumns);
        int startOfJ = (i == startLine ? startColumn : 0);
        for (int j = startOfJ; j < endOfJ; j++) {
            (*(*result)[i])[j] = applyTransformation(pixels, kernel, i, j);
        }
    }
}

vector<vector<int>*>* convolute(vector<vector<int>*>* pixels, vector<vector<double>*>* kernel, int p) {
    vector<thread> threads(p);

    auto* result = new vector<vector<int>*>(pixels->size());
    for (int i = 0; i < pixels->size(); i++) {
        (*result)[i] = new vector<int>(pixels->at(i)->size());
    }

    int nrLines = (int)pixels->size(), nrColumns = (int)pixels->at(0)->size();
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

    return result;
}

void writeResult(string destination, vector<vector<int>*>* pixels) {
    ofstream out(destination);
    if (!out) {
        throw "error writing to file";
    }
    for (auto & line: *pixels) {
        for (int &element: *line) {
            out << element << " ";
        }
        out << "\n";
    }
}

int main(int argc, char** argv) {
    auto startTime = std::chrono::high_resolution_clock::now();

    int p = atoi(argv[1]);

    vector<vector<int>*>* pixels = nullptr;
    vector<vector<double>*>* kernel = nullptr;

    readMatrices("../matrices/date.txt", pixels, kernel);

    auto result = convolute(pixels, kernel, p);

    writeResult("../matrices/result.txt", result);

    for (int i = 0; i < pixels->size(); i++) {
        delete pixels->at(i);
        delete result->at(i);
    }

    for (auto & k : *kernel) {
        delete k;
    }

    delete kernel;
    delete pixels;
    delete result;

    auto endTime = std::chrono::high_resolution_clock::now();

    auto diff = std::chrono::duration<double, std::milli>(endTime - startTime).count();
    std::cout<<diff;

    return 0;
}