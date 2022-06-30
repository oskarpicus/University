#include <iostream>
#include "ppm.h"
#define FILTER_HEIGHT 3
#define FILTER_WIDTH 3
#define BLOCK_SIZE 24

int filter[FILTER_HEIGHT][FILTER_WIDTH] =
{
    0, -1, 0,
    -1, 5, -1,
    0, -1, 0
};

__global__ void applySharpening(int height, int width, int* red, int* green, int* blue, int* filter, int* outRed, int* outGreen, int* outBlue) {
        int i = blockIdx.x * blockDim.x + threadIdx.x;
        int j = blockIdx.y * blockDim.y + threadIdx.y;

    if (i < height && j < width) {
        int newRed = 0, newGreen = 0, newBlue = 0;
        int lineKernel = 0, columnKernel = 0;

        for (int k = FILTER_HEIGHT / 2; k >= -FILTER_HEIGHT / 2; k--) {
            for (int l = FILTER_WIDTH / 2; l >= -FILTER_WIDTH / 2; l--) {
                int line, column;

                if (i - k < 0) {
                    line = 0;
                }
                else {
                    if (i - k > height - 1) {
                        line = height - 1;
                    }
                    else {
                        line = i - k;
                    }
                }

                if (j - l < 0) {
                    column = 0;
                }
                else {
                    if (j - l > width - 1) {
                        column = width - 1;
                    }
                    else {
                        column = j - l;
                    }
                }

                newRed += filter[lineKernel * FILTER_WIDTH + columnKernel] * red[line * width + column];
                newGreen += filter[lineKernel * FILTER_WIDTH + columnKernel]* green[line * width + column];
                newBlue += filter[lineKernel * FILTER_WIDTH + columnKernel] * blue[line * width + column];

                columnKernel++;
                if (columnKernel % FILTER_WIDTH == 0) {
                    columnKernel = 0;
                    lineKernel++;
                }
            }
        }

        newRed = newRed > 255 ? 255 : (newRed < 0 ? 0 : newRed);
        newGreen = newGreen > 255 ? 255 : (newGreen < 0 ? 0 : newGreen);
        newBlue = newBlue > 255 ? 255 : (newBlue < 0 ? 0 : newBlue);

        outRed[i * width + j] = newRed;
        outGreen[i * width + j] = newGreen;
        outBlue[i * width + j] = newBlue;
   }
}

int main()
{
    {
        Image image = readImage("nt-P3.ppm");
        int imageSize = image.width * image.height;

        int* h_red = (int*) malloc(sizeof(int) * image.height * image.width);
        int* h_green = (int*) malloc(sizeof(int) * image.height * image.width);
        int* h_blue  = (int*) malloc(sizeof(int) * image.height * image.width);

        flatten(image, h_red, h_green, h_blue);

        int* d_red, * d_green, * d_blue;
        int* d_outRed, * d_outGreen, * d_outBlue;
        int* d_filter;
        cudaMalloc((void**)&d_red, sizeof(int) * imageSize);
        cudaMalloc((void**)&d_green, sizeof(int) * imageSize);
        cudaMalloc((void**)&d_blue, sizeof(int) * imageSize);

        cudaMalloc((void**)&d_outRed, sizeof(int) * imageSize);
        cudaMalloc((void**)&d_outGreen, sizeof(int) * imageSize);
        cudaMalloc((void**)&d_outBlue, sizeof(int) * imageSize);

        cudaMalloc((void**)&d_filter, sizeof(int) * FILTER_WIDTH * FILTER_HEIGHT);

        cudaMemcpy(d_red, h_red, sizeof(int) * imageSize, cudaMemcpyHostToDevice);
        cudaMemcpy(d_green, h_green, sizeof(int) * imageSize, cudaMemcpyHostToDevice);
        cudaMemcpy(d_blue, h_blue, sizeof(int) * imageSize, cudaMemcpyHostToDevice);

        cudaMemcpy(d_filter, filter, sizeof(int) * FILTER_WIDTH * FILTER_HEIGHT, cudaMemcpyHostToDevice);

        dim3 dimBlock(BLOCK_SIZE, BLOCK_SIZE);
        dim3 dimGrid;
        dimGrid.x = image.width;// + dimBlock.x;
        dimGrid.y = image.height;// + dimBlock.y;

        applySharpening<<<dimGrid, dimBlock>>>(
                image.height,
                image.width,
                d_red,
                d_green,
                d_blue,
                d_filter,
                d_outRed,
                d_outGreen,
                d_outBlue
        );

        cudaDeviceSynchronize();

        std::cout << "Cuda status: " << cudaGetLastError() << std::endl;

        cudaMemcpy(h_red, d_outRed, sizeof(int) * imageSize, cudaMemcpyDeviceToHost);
        cudaMemcpy(h_green, d_outGreen, sizeof(int) * imageSize, cudaMemcpyDeviceToHost);
        cudaMemcpy(h_blue, d_outBlue, sizeof(int) * imageSize, cudaMemcpyDeviceToHost);

        Image result = deflatten(h_red, h_green, h_blue, image.height, image.width);
        writeImage("result.ppm", result);

        cudaFree(d_red);
        cudaFree(d_green);
        cudaFree(d_blue);
        cudaFree(d_outRed);
        cudaFree(d_outGreen);
        cudaFree(d_outBlue);

        free(h_red);
        free(h_green);
        free(h_blue);

        std::cout << "done\n";
    }
    return 0;
}
