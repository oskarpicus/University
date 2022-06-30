package convolutions;

import java.util.concurrent.CyclicBarrier;

public class ParallelConvolutionOneMatrix extends AbstractConvolution implements Convolution {
    // number of threads to use
    private final int p;
    private CyclicBarrier barrier;

    public ParallelConvolutionOneMatrix(int p) {
        this.p = p;
    }

    @Override
    public Integer[][] convolute(Integer[][] pixels, Double[][] kernel) {
        int nrLines = pixels.length, nrColumns = pixels[0].length;
        int nrElements = nrLines * nrColumns;

        // compute how many pixels each thread will transform, so each thread will approximately
        // have the same number of pixels
        int quotient = nrElements / p, remainder = nrElements % p;

        var threads = new ParallelConvolutionOneMatrix.ParallelConvolutionThread[p];
        barrier = new CyclicBarrier(p);

        // variables to represent the indexes of the elements that start and end the block in the matrix
        // they correspond to the linearised matrix
        int start, end = 0;

        for (int i = 0; i < p; i++) {
            start = end;
            end = start + quotient + (remainder > 0 ? 1 : 0);
            remainder--;

            threads[i] = new ParallelConvolutionOneMatrix.ParallelConvolutionThread(start, end, pixels, kernel);
            threads[i].start();
        }

        for (int i = 0; i < p; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return pixels;
    }

    public class ParallelConvolutionThread extends Thread {
        private final int left;
        private final int right;
        private final Integer[][] pixels;
        private final Double[][] kernel;

        public ParallelConvolutionThread(int left, int right, Integer[][] pixels, Double[][] kernel) {
            this.left = left;
            this.right = right;
            this.pixels = pixels;
            this.kernel = kernel;
        }

        @Override
        public void run() {
            super.run();

            // determine the locations in the original matrix
            int nrColumns = pixels[0].length;
            int startLine = left / nrColumns, startColumn = left % nrColumns;
            int endLine = right / nrColumns, endColumn = right % nrColumns;

            // determine where to copy from
            int startColumnResult = 0, endColumnResult = nrColumns - 1;
            int startLineResult = Math.max(startLine - kernel.length, 0);
            int endLineResult = Math.min(endLine + kernel.length, pixels.length - 1);

            // copy the elements required for the computation
            Integer[][] copy = new Integer[endLineResult - startLineResult + 1][nrColumns];

            // storing where the actual assigned values are in the copy
            int startTransformLine = 0, startTransformColumn = 0;

            int counterLine = 0;
            for (int i = startLineResult; i <= endLineResult; i++) {
                int counterColumn = 0;
                for (int j = startColumnResult; j <= endColumnResult; j++) {
                    if (i == startLine && j == startColumn) {
                        startTransformLine = counterLine;
                        startTransformColumn = counterColumn;
                    }
                    copy[counterLine][counterColumn] = pixels[i][j];
                    counterColumn++;
                }
                counterLine++;
            }

            try {
                ParallelConvolutionOneMatrix.this.barrier.await();
            } catch (Exception e) {
                return;
            }

            // apply transformations
            for (int i = startLine; i <= endLine; i++) {
                int endOfJ = (i == endLine ? endColumn : nrColumns);
                int startOfJ = (i == startLine ? startColumn : 0);
                for (int j = startOfJ; j < endOfJ; j++) {
                    pixels[i][j] = ParallelConvolutionOneMatrix.this.applyTransformation(copy, kernel, startTransformLine, startTransformColumn);
                    startTransformColumn++;
                }
                startTransformLine++;
                startTransformColumn = 0;
            }
        }
    }
}
