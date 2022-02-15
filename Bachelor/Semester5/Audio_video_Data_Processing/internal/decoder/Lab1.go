package decoder

import (
	"PDAV/internal/encoder"
	"PDAV/internal/model"
)

// generateRBGImage computes the corresponding RBG image from collections of Y, Cb, Cr blocks.
func (jpeg *Jpeg) generateRBGImage(y map[*encoder.Position][][]float64, cb map[*encoder.Position][][]float64, cr map[*encoder.Position][][]float64) *model.Image {
	blockSize := 0

	// perform up-sampling on the U and V matrices
	for key, value := range cb {
		cb[key] = jpeg.upSample(value)
		blockSize = len(cb[key])
	}
	for key, value := range cr {
		cr[key] = jpeg.upSample(value)
	}

	width, height := jpeg.getDimensions(y)

	// reconstruct the image
	pixels := make([][]*model.Pixel, height)
	for i := 0; i < height; i++ {
		pixels[i] = make([]*model.Pixel, width)
	}

	for position, blockY := range y {
		blockU, blockV := cb[position], cr[position]

		currentLine := 0
		for i := position.Line; i < position.Line+blockSize; i++ {
			currentColumn := 0
			for j := position.Column; j < position.Column+blockSize; j++ {
				pixels[i][j] = &model.Pixel{
					Item1: blockY[currentLine][currentColumn],
					Item2: blockU[currentLine][currentColumn],
					Item3: blockV[currentLine][currentColumn],
				}
				pixels[i][j].YUVtoRGB()
				currentColumn++
			}
			currentLine++
		}
	}

	return &model.Image{
		Pixels: pixels,
		Width:  width,
		Height: height,
	}
}

// upSample performs an up-sampling on a matrix, by doubling its values.
func (jpeg *Jpeg) upSample(matrix [][]float64) [][]float64 {
	result := make([][]float64, 2*len(matrix))

	for i := 0; i < len(matrix); i++ {
		result[i] = make([]float64, 2*len(matrix[i]))
		result[i+len(matrix)] = make([]float64, 2*len(matrix[i]))
	}

	currentLine := 0
	for i := 0; i < len(matrix); i++ {
		currentColumn := 0
		for j2 := 0; j2 < len(matrix[i]); j2++ {
			result[currentLine][currentColumn] = matrix[i][j2]
			result[currentLine+1][currentColumn] = matrix[i][j2]
			result[currentLine][currentColumn+1] = matrix[i][j2]
			result[currentLine+1][currentColumn+1] = matrix[i][j2]
			currentColumn += 2
		}
		currentLine += 2
	}

	return result
}

// getDimensions computes the width and the height of an image, given its Y blocks.
func (jpeg *Jpeg) getDimensions(y map[*encoder.Position][][]float64) (width int, height int) {
	maxLine, maxCol, length := 0, 0, 0

	for key, block := range y {
		length = len(block)
		if maxLine < key.Line {
			maxLine = key.Line
		}
		if maxCol < key.Column {
			maxCol = key.Column
		}
	}

	width = maxCol + length
	height = maxLine + length
	return width, height
}
