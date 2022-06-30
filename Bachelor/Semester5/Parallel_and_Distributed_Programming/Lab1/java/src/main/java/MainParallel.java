import convolutions.Convolution;
import convolutions.ParallelConvolution;
import convolutions.SequentialConvolution;
import utils.MatricesFileReader;
import utils.MatrixFileWriter;

import java.util.Arrays;


public class MainParallel {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        MatricesFileReader reader = new MatricesFileReader("date.txt");
        Integer[][] pixelMatrix = reader.getPixelMatrix();
        Double[][] kernelMatrix = reader.getKernelMatrix();

        Convolution convolution = new ParallelConvolution(Integer.parseInt(args[0]));
        var result = convolution.convolute(pixelMatrix, kernelMatrix);

        new MatrixFileWriter().write("results.txt", result);

        long end = System.currentTimeMillis();

        // compute the same result, but sequentially
        convolution = new SequentialConvolution();
        var resultSeq = convolution.convolute(pixelMatrix, kernelMatrix);

        if (!Arrays.deepEquals(resultSeq, result)) {
            throw new RuntimeException("Algorithm is not correct");
        }

        System.out.println(end - start);
    }
}
