import convolutions.Convolution;
import convolutions.SequentialConvolution;
import utils.MatricesFileReader;
import utils.MatrixFileWriter;


public class MainSequential {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        MatricesFileReader reader = new MatricesFileReader("date.txt");
        Integer[][] pixelMatrix = reader.getPixelMatrix();
        Double[][] kernelMatrix= reader.getKernelMatrix();

        Convolution convolution = new SequentialConvolution();
        var result = convolution.convolute(pixelMatrix, kernelMatrix);

        new MatrixFileWriter().write("results.txt", result);

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}
