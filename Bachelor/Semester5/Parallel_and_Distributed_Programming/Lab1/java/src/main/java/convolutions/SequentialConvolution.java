package convolutions;

public class SequentialConvolution extends AbstractConvolution implements Convolution {
    @Override
    public Integer[][] convolute(Integer[][] pixels, Double[][] kernel) {
        Integer[][] result = new Integer[pixels.length][pixels[0].length];

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                result[i][j] = super.applyTransformation(pixels, kernel, i, j);
            }
        }

        return result;
    }
}
