#pragma once

typedef struct Pixel {
        int R, G, B;

        Pixel() {}

        Pixel(int R, int G, int B) {
                this->R = R;
                this->G = G;
                this->B = B;
        }

} Pixel;

typedef struct Image {
        Pixel** pixels;
        int width;
        int height;

        Image() {}

        Image(Pixel** pixels, int width, int height) {
                this->pixels = pixels;
                this->width = width;
                this->height = height;
        }

} Image;

Image readImage(std::string filename);

void writeImage(std::string filename, Image image);

void flatten(Image image, int* r, int* g, int* b);

Image deflatten(int* r, int* g, int *b, int height, int width);
