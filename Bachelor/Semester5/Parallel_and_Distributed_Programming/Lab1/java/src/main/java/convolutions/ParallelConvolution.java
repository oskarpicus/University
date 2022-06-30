package convolutions;

public class ParallelConvolution extends AbstractConvolution implements Convolution {
    // number of threads to use
    private final int p;

    public ParallelConvolution(int p) {
        this.p = p;
    }

    @Override
    public Integer[][] convolute(Integer[][] pixels, Double[][] kernel) {
        int nrLines = pixels.length, nrColumns = pixels[0].length;
        int nrElements = nrLines * nrColumns;

        // compute how many pixels each thread will transform, so each thread will approximately
        // have the same number of pixels
        int quotient = nrElements / p, remainder = nrElements % p;

        Integer[][] result = new Integer[nrLines][nrColumns];
        var threads = new ParallelConvolutionThread[p];

        // variables to represent the indexes of the elements that start and end the block in the matrix
        // they correspond to the linearised matrix
        int start, end = 0;

        for (int i = 0; i < p; i++) {
            start = end;
            end = start + quotient + (remainder > 0 ? 1 : 0);
            remainder--;

            threads[i] = new ParallelConvolutionThread(start, end, pixels, kernel, result);
            threads[i].start();
        }

        for (int i = 0; i < p; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    class ParallelConvolutionThread extends Thread {
        private final int left;
        private final int right;
        private final Integer[][] pixels;
        private final Double[][] kernel;
        private final Integer[][] result;

        public ParallelConvolutionThread(int left, int right, Integer[][] pixels, Double[][] kernel, Integer[][] result) {
            this.left = left;
            this.right = right;
            this.pixels = pixels;
            this.kernel = kernel;
            this.result = result;
        }

        /**
         * This method will apply the respective image transformation on a block of the pixel matrix,
         * defined via the {@code left} and {@code right} fields.
         */
        @Override
        public void run() {
            super.run();

            int nrColumns = pixels[0].length;
            int startLine = left / nrColumns, startColumn = left % nrColumns;
            int endLine = right / nrColumns, endColumn = right % nrColumns;

            for (int i = startLine; i <= endLine; i++) {
                int endOfJ = (i == endLine ? endColumn : nrColumns);
                int startOfJ = (i == startLine ? startColumn : 0);
                for (int j = startOfJ; j < endOfJ; j++) {
                    result[i][j] = ParallelConvolution.this.applyTransformation(pixels, kernel, i, j);
                }
            }
        }
    }
}
