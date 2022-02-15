package model

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
)

const (
	format  = "P3"
	creator = "# CREATOR me"
	maxByte = 255
)

// PpmFile represents an abstraction of a PPM file.
type PpmFile struct {
	// Filename represents the path to the file in the current file system.
	Filename string
}

// Read retrieves the corresponding image from a PPM file, computing its pixels, width and height.
func (p *PpmFile) Read() (*Image, error) {
	f, err := os.Open(p.Filename)
	if err != nil {
		return nil, err
	}

	defer func(f *os.File) {
		err := f.Close()
		if err != nil {
			log.Fatal(err.Error())
		}
	}(f)

	scanner := bufio.NewScanner(f)

	scanner.Scan() // read the format of the file
	scanner.Scan() // read the creator of the file

	// read the resolution of the image
	scanner.Scan()
	resolution := strings.Split(scanner.Text(), " ")
	width, err := strconv.Atoi(resolution[0])
	if err != nil {
		return nil, err
	}
	height, err := strconv.Atoi(resolution[1])
	if err != nil {
		return nil, err
	}

	// read the maximum byte value
	scanner.Scan()
	max, err := strconv.ParseFloat(scanner.Text(), 64)

	result := make([][]*Pixel, height)

	for i := 0; i < height; i++ {
		result[i] = make([]*Pixel, width)
		for j := 0; j < width; j++ {
			pixel, err := p.readPixel(scanner)
			if err != nil {
				return nil, err
			}

			if pixel.Item1 > max || pixel.Item2 > max || pixel.Item3 > max {
				return nil, fmt.Errorf("values are exceeding the limit")
			}

			result[i][j] = pixel
		}
	}

	return &Image{
		Pixels: result,
		Width:  width,
		Height: height,
	}, nil
}

// readPixel is responsible for reading a single pixel from an input.
func (p *PpmFile) readPixel(scanner *bufio.Scanner) (*Pixel, error) {
	scanner.Scan()
	red, err1 := strconv.Atoi(scanner.Text())
	scanner.Scan()
	green, err2 := strconv.Atoi(scanner.Text())
	scanner.Scan()
	blue, err3 := strconv.Atoi(scanner.Text())
	if err1 != nil || err2 != nil || err3 != nil {
		return nil, fmt.Errorf("cannot convert data")
	}

	return &Pixel{
		Item1: float64(red),
		Item2: float64(green),
		Item3: float64(blue),
	}, nil
}

// Write outputs the contents of an image into a PPM P3 format.
func (p *PpmFile) Write(image *Image) error {
	f, err := os.Create(p.Filename)
	if err != nil {
		return err
	}
	defer func(f *os.File) {
		err := f.Close()
		if err != nil {
			log.Fatal(err.Error())
		}
	}(f)

	w := bufio.NewWriter(f)

	// write the header of the file
	_, err1 := w.WriteString(format + "\n")
	_ = w.Flush()
	_, err2 := w.WriteString(creator + "\n")
	_ = w.Flush()
	_, err3 := w.WriteString(strconv.Itoa(image.Width) + " " + strconv.Itoa(image.Height) + "\n")
	_ = w.Flush()
	_, err4 := w.WriteString(strconv.Itoa(maxByte) + "\n")
	_ = w.Flush()
	if err1 != nil || err2 != nil || err3 != nil || err4 != nil {
		return fmt.Errorf("failed to write in file")
	}

	for i := 0; i < len(image.Pixels); i++ {
		for j := 0; j < len(image.Pixels[i]); j++ {
			_, err1 = w.WriteString(strconv.Itoa(int(image.Pixels[i][j].Item1)) + "\n")
			_ = w.Flush()
			_, err2 = w.WriteString(strconv.Itoa(int(image.Pixels[i][j].Item2)) + "\n")
			_ = w.Flush()
			_, err3 = w.WriteString(strconv.Itoa(int(image.Pixels[i][j].Item3)) + "\n")
			_ = w.Flush()

			if err1 != nil || err2 != nil || err3 != nil {
				return fmt.Errorf("failed to write in file")
			}
		}
	}

	return nil
}
