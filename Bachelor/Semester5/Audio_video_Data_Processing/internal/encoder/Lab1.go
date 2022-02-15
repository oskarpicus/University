package encoder

import "PDAV/internal/model"

// computeYCbCr takes an image and generates its corresponding Y, Cb and Cr matrices.
// Each entry in the returned maps follows the structure (top-left corner) -> (matrix block).
func (jpeg *Jpeg) computeYCbCr(image *model.Image) (map[*Position][][]float64, map[*Position][][]float64, map[*Position][][]float64, error) {
	width, height := image.Width, image.Height

	y, u, v := make(map[*Position][][]float64), make(map[*Position][][]float64), make(map[*Position][][]float64)

	// generate the first type of blocks
	currentBlock := 0
	for i := 0; i < height; i += blockSize {
		for j := 0; j < width; j += blockSize {
			position := &Position{
				Line:   i,
				Column: j,
			}
			y[position], u[position], v[position] = jpeg.generateBlock(image.Pixels, i, j)
			currentBlock++
		}
	}

	// generate the sampled blocks
	u1, v1 := make(map[*Position][][]float64), make(map[*Position][][]float64)

	for key, value := range u {
		u1[key] = jpeg.transformBlock(value)
	}

	for key, value := range v {
		v1[key] = jpeg.transformBlock(value)
	}

	return y, u1, v1, nil
}

// generateBlock takes as input the top-left corner of a block and iterates over it.
// In the process, it generates the corresponding Y, U and V matrices
func (jpeg *Jpeg) generateBlock(pixels [][]*model.Pixel, offsetI int, offsetJ int) ([][]float64, [][]float64, [][]float64) {
	y, u, v := make([][]float64, blockSize), make([][]float64, blockSize), make([][]float64, blockSize)
	counterLine := 0

	for i := offsetI; i < blockSize+offsetI; i++ {
		y[counterLine], u[counterLine], v[counterLine] = make([]float64, blockSize), make([]float64, blockSize), make([]float64, blockSize)

		counterColumn := 0
		for j := offsetJ; j < blockSize+offsetJ; j++ {
			pixels[i][j].RGBtoYUV()

			y[counterLine][counterColumn] = pixels[i][j].Item1
			u[counterLine][counterColumn] = pixels[i][j].Item2
			v[counterLine][counterColumn] = pixels[i][j].Item3

			counterColumn++
		}

		counterLine++
	}

	return y, u, v
}

// transformBlock performs a subsampling of a block, substituting its values with the mean of its elements.
func (jpeg *Jpeg) transformBlock(block [][]float64) [][]float64 {
	dimensions := blockSize / smallBlockSize
	counterLine := 0
	result := make([][]float64, dimensions)

	for i := 0; i < blockSize; i += smallBlockSize {
		result[counterLine] = make([]float64, dimensions)

		counterColumn := 0
		for j := 0; j < blockSize; j += smallBlockSize {
			mean := jpeg.computeAverage(block, i, j)
			result[counterLine][counterColumn] = mean
			counterColumn++
		}

		counterLine++
	}

	return result
}

// computeAverage receives as input the top-left corner of a block and computes the average of its elements.
func (jpeg *Jpeg) computeAverage(block [][]float64, offsetI int, offsetJ int) float64 {
	result, counter := float64(0), float64(0)

	for i := offsetI; i < smallBlockSize+offsetI; i++ {
		for j := offsetJ; j < smallBlockSize+offsetJ; j++ {
			result += block[i][j]
			counter++
		}
	}

	return result / counter
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
