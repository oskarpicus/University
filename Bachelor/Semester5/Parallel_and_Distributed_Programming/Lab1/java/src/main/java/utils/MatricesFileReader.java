package utils;

import java.util.Objects;
import java.util.Scanner;

/**
 * {@code FileReader} is responsible for reading the input file and generate the corresponding pixel and kernel matrices
 */
public class MatricesFileReader {
    private final String filename;
    private Integer[][] pixelMatrix;
    private Double[][] kernelMatrix;

    /**
     *
     * @param filename: String, relative path to a file with the corresponding format located in the resources directory
     */
    public MatricesFileReader(String filename) {
        this.filename = filename;
        this.read();
    }

    /**
     * Method for reading the pixel and kernel matrices from the corresponding file.
     * This method will save its result in two private fields.
     */
    public void read() {
        try (Scanner reader = new Scanner(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)))) {
            int nrLines = reader.nextInt();
            int nrColumns = reader.nextInt();
            pixelMatrix = new Integer[nrLines][nrColumns];

            for(int i = 0; i < nrLines; i++) {
                for(int j = 0; j < nrColumns; j++) {
                    pixelMatrix[i][j] = reader.nextInt();
                }
            }

            nrLines = reader.nextInt();
            nrColumns = reader.nextInt();
            kernelMatrix = new Double[nrLines][nrColumns];

            for(int i = 0; i < nrLines; i++) {
                for(int j = 0; j < nrColumns; j++) {
                    kernelMatrix[i][j] = reader.nextDouble();
                }
            }
        }
    }

    /**
     * Method for retrieving the pixel matrix that was read previously
     * @return the pixel matrix
     */
    public Integer[][] getPixelMatrix() {
        return pixelMatrix;
    }

    /**
     * Method for retrieving the kernel matrix that was read previously
     * @return the kernel matrix
     */
    public Double[][] getKernelMatrix() {
        return kernelMatrix;
    }
}
