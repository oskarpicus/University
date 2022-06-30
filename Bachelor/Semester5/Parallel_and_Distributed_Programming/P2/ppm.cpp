#include <fstream>
#include <sstream>
#include <stdlib.h>
#include <iostream>
#include "ppm.h"

Image readImage(std::string filename) {
        std::ifstream in(filename);
        if (!in) {
                perror("Cannot open file");
                return Image(nullptr, 0, 0);
        }

        std::string buffer;

        std::getline(in, buffer);
        std::getline(in, buffer);
        std::getline(in, buffer);

        int width = atoi(buffer.substr(0, buffer.find(" ")).c_str());
        int height = atoi(buffer.substr(buffer.find(" "), buffer.length()).c_str());

        std::getline(in, buffer);

        Pixel** pixels = (Pixel**) malloc(height * sizeof(Pixel*));

        for (int i = 0; i < height; i++) {
                pixels[i] = (Pixel*)malloc(width * sizeof(Pixel));
                for (int j = 0; j < width; j++) {
                        std::getline(in, buffer);
                        int r = atoi(buffer.c_str());
                        std::getline(in, buffer);
                        int g = atoi(buffer.c_str());
                        std::getline(in, buffer);
                        int b = atoi(buffer.c_str());
                        Pixel pixel = Pixel(r, g, b);
                        pixels[i][j] = pixel;
                }
        }

        in.close();

        return Image(pixels, width, height);
}

void writeImage(std::string filename, Image image) {
        std::ofstream out(filename);

        out << "P3\n";
        out << "# CREATOR: Picus & Popovici\n";
        out << image.width << " " << image.height << "\n";
        out << "255\n";

        for (int i = 0; i < image.height; i++) {
                for (int j = 0; j < image.width; j++) {
                        Pixel pixel = image.pixels[i][j];
                        out << pixel.R << "\n" << pixel.G << "\n" << pixel.B << "\n";
                }
        }

        out.close();
}

void flatten(Image image, int* r, int* g, int* b) {
        for (int i = 0; i < image.height; i++) {
                for (int j = 0; j < image.width; j++) {
                        int position = i * image.width + j;
                        r[position] = image.pixels[i][j].R;
                        g[position] = image.pixels[i][j].G;
                        b[position] = image.pixels[i][j].B;
                }
        }
}

Image deflatten(int* r, int* g, int* b, int height, int width) {
        Pixel** pixels = (Pixel**) malloc(sizeof(Pixel*) * height);
        for (int i = 0; i < height; i++) {
                pixels[i] = (Pixel*) malloc(sizeof(Pixel) * width);
                for (int j = 0; j < width; j++) {
                        pixels[i][j] = Pixel(r[i * width + j], g[i * width + j], b[i * width + j]);
                }
        }

        return Image(pixels, width, height);
}
