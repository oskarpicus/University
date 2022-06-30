package convolutions;

abstract class AbstractConvolution implements Convolution {

    /**
     * Method for applying a singular transformation on a pixel
     * @param pixels: Integer[][], the pixel matrix
     * @param kernel: Double[][], the kernel matrix
     * @param m: int, the line index of the pixel to transform in the pixel matrix
     * @param n: int, the column index of the pixel to transform in the pixel matrix
     * @return the new value of the pixel, after the transformation
     */
    protected int applyTransformation(Integer[][] pixels, Double[][] kernel, int m, int n) {
        double result = 0;
        for (int k = -kernel.length/2; k <= kernel.length/2; k++) {
            for (int l = -kernel[0].length/2; l <= kernel[0].length/2; l++) {
                // verify if we are still in the actual matrices
                double kernelValue =
                        isValidIndex(k, kernel.length) && isValidIndex(l, kernel[0].length)
                                ? kernel[k][l] : getNeighbour(kernel, k, l);
                double pixelValue =
                        isValidIndex(m - k, pixels.length) && isValidIndex(n - l, pixels[0].length)
                                ? pixels[m - k][n - l] : getNeighbour(pixels, m - k, n - l);
                result += kernelValue * pixelValue;
            }
        }

        return (int) result;
    }

    /**
     * Method for obtaining the corresponding value of a position in a matrix, if it is not a valid one
     * @param matrix: T[][], the matrix to be considered
     * @param i: int, the line index of the position that is considered
     * @param j: int, the column index of the position that is considered
     * @param <T>: type parameter, indicates what kind of elements the matrix contains
     * @return the corresponding value, according to the preset rules, if a position in a matrix is outside bounds
     */
    private <T> T getNeighbour(T[][] matrix, int i, int j) {
        int nrLines = matrix.length, nrColumns = matrix[0].length;

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

    /**
     * Method for determining if an index in a valid one, taking in consideration the respective dimension of the matrix
     * @param index: int, the index to verify
     * @param dimension: int, the number of elements that can be memorised in the corresponding dimension
     * @return - true, if index is in the interval [0, dimension],
     *         - false, otherwise
     */
    private boolean isValidIndex(int index, int dimension) {
        return index >= 0 && index < dimension;
    }
}
