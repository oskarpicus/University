#include <chrono>
#include <iostream>
#include <vector>
#include <fstream>
#include <string>
using namespace std;

void readMatrices(string filename, vector<vector<int>>& pixels, vector<vector<double>>& kernel) {
    std::ifstream in(filename);
    if (!in) {
        throw "file not found";
    }

    int nrLines, nrColumns;
    in >> nrLines >> nrColumns;

    pixels = std::vector<std::vector<int>>(nrLines);

    for (int i = 0; i < nrLines; i++) {
        pixels[i] = std::vector<int>(nrColumns);
        for (int j = 0; j < nrColumns; j++) {
            in >> pixels[i][j];
        }
    }

    in >> nrLines >> nrColumns;

    kernel = std::vector<std::vector<double>>(nrLines);

    for (int i = 0; i < nrLines; i++) {
        kernel[i] = std::vector<double>(nrColumns);
        for (int j = 0; j < nrColumns; j++) {
            in >> kernel[i][j];
        }
    }
}

bool isValidIndex(int index, int dimension) {
    return index >= 0 && index < dimension;
}

template<typename T>
T getNeighbour(std::vector<std::vector<T>>& matrix, int i, int j) {
    int nrLines = matrix.size(), nrColumns = matrix[0].size();

    if (i < 0 && isValidIndex(j, nrColumns)) {
        return matrix[0][j];
    }
    if (isValidIndex(i, nrLines) && j < 0) {
        return matrix[i][0];
    }
    if (i >= nrLines && isValidIndex(j, nrColumns)) {
        return matrix[nrLines - 1][j];
    }
    if (isValidIndex(i, nrLines) && j >= nrColumns) {
        return matrix[i][nrColumns - 1];
    }

    // corners
    if (i < 0 && j < 0) {
        return matrix[0][0];
    }
    if (i < 0 && j > 0) {
        return matrix[0][nrColumns - 1];
    }
    if (i > 0 && j < 0) {
        return matrix[nrLines - 1][0];
    }
    return matrix[nrLines - 1][nrColumns - 1];
}

int applyTransformation(vector<vector<int>>& pixels, vector<vector<double>>& kernel, int m, int n) {
    int nrLines = (int)kernel.size(), nrColumns = (int)kernel[0].size();
    double result = 0;
    for (int k = -nrLines / 2; k <= nrLines / 2; k++) {
        for (int l = -nrColumns / 2; l <= nrColumns / 2; l++) {
            // verify if we are still in the actual matrices
            double kernelValue =
                    isValidIndex(k, nrLines) && isValidIndex(l, nrColumns)
                    ? kernel[k][l] : getNeighbour(kernel, k, l);
            double pixelValue =
                    isValidIndex(m - k, (int)pixels.size()) && isValidIndex(n - l, (int)pixels[0].size())
                    ? pixels[m - k][n - l] : getNeighbour(pixels, m - k, n - l);
            result += kernelValue * pixelValue;
        }
    }

    return (int) result;
}

vector<vector<int>> convolute(vector<vector<int>>& pixels, vector<vector<double>>& kernel) {
    vector<vector<int>> result(pixels.size());

    for (int i = 0; i < pixels.size(); i++) {
        result[i] = std::vector<int>(pixels[i].size());
        for (int j = 0; j < pixels[i].size(); j++) {
            result[i][j] = applyTransformation(pixels, kernel, i, j);
        }
    }

    return result;
}

void writeResult(string destination, vector<vector<int>>& pixels) {
    ofstream out(destination);
    if (!out) {
        throw "error writing to file";
    }
    for (std::vector<int> &line: pixels) {
        for (int &element: line) {
            out << element << " ";
        }
        out << "\n";
    }
}

int main(int argc, char** argv) {
    auto startTime = std::chrono::high_resolution_clock::now();

    vector<vector<int>> pixels;
    vector<vector<double>> kernel;

    readMatrices("../matrices/date.txt", pixels, kernel);

    auto result = convolute(pixels, kernel);

    writeResult("../matrices/result.txt", result);

    auto endTime = std::chrono::high_resolution_clock::now();

    auto diff = std::chrono::duration<double, std::milli>(endTime - startTime).count();
    std::cout<<diff;

    return 0;
}