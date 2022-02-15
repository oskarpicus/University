package encoder

import (
	"PDAV/internal/model"
)

const (
	// blockSize represents the dimension of a block in the first step of the encoding algorithm.
	blockSize = 8

	// smallBlockSize determines the dimension of the block to compute the average of the pixel during subsampling.
	smallBlockSize = 2
)

var q = [8][8]int{
	{6, 4, 4, 6, 10, 16, 20, 24},
	{5, 5, 6, 8, 10, 23, 24, 22},
	{6, 5, 6, 10, 16, 23, 28, 22},
	{6, 7, 9, 12, 20, 35, 32, 25},
	{7, 9, 15, 22, 27, 44, 41, 31},
	{10, 14, 22, 26, 32, 42, 45, 37},
	{20, 26, 31, 35, 41, 48, 48, 40},
	{29, 37, 38, 39, 45, 40, 41, 40},
}

// Jpeg represents a jpeg encoder.
type Jpeg struct {
}

// Position is an abstraction of a pair (line, column), representing a position in a matrix.
type Position struct {
	Line   int
	Column int
}

// Encode takes an image and generates its corresponding Y, U and V matrices.
// Each entry in the returned map follows the structure (top-left corner) -> (matrix block).
func (jpeg *Jpeg) Encode(image *model.Image) ([]int, error) {
	y, cb, cr, err := jpeg.computeYCbCr(image)
	if err != nil {
		return nil, err
	}

	// perform up-sampling
	for key, el := range cb {
		cb[key] = jpeg.upSample(el)
	}
	for key, el := range cr {
		cr[key] = jpeg.upSample(el)
	}

	y = jpeg.forwardDCT(y)
	cb = jpeg.forwardDCT(cb)
	cr = jpeg.forwardDCT(cr)

	jpeg.quantize(y)
	jpeg.quantize(cb)
	jpeg.quantize(cr)

	yCoefficients := jpeg.zigZagTraversal(y)
	cbCoefficients := jpeg.zigZagTraversal(cb)
	crCoefficients := jpeg.zigZagTraversal(cr)

	return jpeg.encodeCoefficients(yCoefficients, cbCoefficients, crCoefficients), nil
}
