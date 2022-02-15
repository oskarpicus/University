package decoder

import (
	"PDAV/internal/model"
)

const (
	// bigBlockSize represents the dimension of a block in the first step of the encoding algorithm.
	bigBlockSize = 8

	// smallBlockSize determines the dimension of the block to compute the average of the pixel during subsampling.
	smallBlockSize = 2
)

var q = [8][8]float64{
	{6, 4, 4, 6, 10, 16, 20, 24},
	{5, 5, 6, 8, 10, 23, 24, 22},
	{6, 5, 6, 10, 16, 23, 28, 22},
	{6, 7, 9, 12, 20, 35, 32, 25},
	{7, 9, 15, 22, 27, 44, 41, 31},
	{10, 14, 22, 26, 32, 42, 45, 37},
	{20, 26, 31, 35, 41, 48, 48, 40},
	{29, 37, 38, 39, 45, 40, 41, 40},
}

// Jpeg represents a JPEG decoder.
type Jpeg struct {
}

// Decode receives as input the Y, U and V matrices and generates the corresponding image.
func (jpeg *Jpeg) Decode(encoded []int) *model.Image {
	yCoefficients, cbCoefficients, crCoefficients := jpeg.decodeCoefficients(encoded)

	y := jpeg.convertToMatrix(yCoefficients)
	cb := jpeg.convertToMatrix(cbCoefficients)
	cr := jpeg.convertToMatrix(crCoefficients)

	jpeg.deQuantize(y)
	jpeg.deQuantize(cb)
	jpeg.deQuantize(cr)

	y = jpeg.inverseDCT(y)
	cb = jpeg.inverseDCT(cb)
	cr = jpeg.inverseDCT(cr)

	for position, value := range cb {
		cb[position] = jpeg.subSample(value)
	}
	for position, value := range cr {
		cr[position] = jpeg.subSample(value)
	}

	return jpeg.generateRBGImage(y, cb, cr)
}
