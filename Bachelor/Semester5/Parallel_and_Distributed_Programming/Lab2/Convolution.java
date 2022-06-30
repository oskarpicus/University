package convolutions;

public interface Convolution {
    /**
     * Method for applying a transformation on an image
     * @param pixels: Integer[][], the pixel matrix of the image
     * @param kernel: Double[][], the kernel matrix of the filter
     * @return the corresponding pixel matrix, after the transformation was applied
     */
    Integer[][] convolute(Integer[][] pixels, Double[][] kernel);
}
