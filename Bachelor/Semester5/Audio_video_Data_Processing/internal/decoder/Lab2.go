package decoder

import (
	"PDAV/internal/encoder"
	"math"
)

// deQuantize perform a de-quantisation, using a predetermined matrix on a collection of blocks.
func (jpeg *Jpeg) deQuantize(blocks map[*encoder.Position][][]float64) {
	for _, block := range blocks {
		for i := 0 ; i < len(block); i++ {
			for j := 0; j < len(block[i]); j++ {
				block[i][j] = block[i][j] * q[i][j]
			}
		}
	}
}

// inverseDCT receives as input a collection of 8x8 blocks and returns the collection.
// Applies on the elements of the collection an Inverse Discrete Cosine Transformation.
func (jpeg *Jpeg) inverseDCT(blocks map[*encoder.Position][][]float64) map[*encoder.Position][][]float64 {
	result := make(map[*encoder.Position][][]float64)

	for position, block := range blocks {
		result[position] = make([][]float64, len(block))

		for i := 0; i < len(block); i++ {
			result[position][i] = make([]float64, len(block[i]))
			for j := 0; j < len(block[i]); j++ {
				result[position][i][j] = jpeg.applyInverseDCT(block, i, j)
			}
		}
	}

	return result
}

// applyInverseDCT applies a single Inverse Discrete Cosine Transformation on an element identified by its line and column indexes.
func (jpeg *Jpeg) applyInverseDCT(matrix [][]float64, x, y int) float64 {
	alpha := func(u int) float64 {
		if u == 0 {
			return 1 / math.Sqrt(2)
		}
		return 1
	}

	result := 1.0 / 4.0
	sum := 0.0
	floatX, floatY := float64(x), float64(y)

	for u := 0; u < len(matrix); u++ {
		for v := 0; v < len(matrix[u]); v++ {
			floatU, floatV := float64(u), float64(v)
			sum += alpha(u) * alpha(v) * matrix[u][v] * math.Cos((2*floatX+1)*floatU*math.Pi/16) * math.Cos((2*floatY+1)*floatV*math.Pi/16)
		}
	}

	result *= sum
	result += 128  // center back

	return result
}

// subSample performs a subsampling of a block, substituting its values with the mean of its elements.
func (jpeg *Jpeg) subSample(block [][]float64) [][]float64 {
	dimensions := bigBlockSize / smallBlockSize
	counterLine := 0
	result := make([][]float64, dimensions)

	for i := 0; i < bigBlockSize; i += smallBlockSize {
		result[counterLine] = make([]float64, dimensions)

		counterColumn := 0
		for j := 0; j < bigBlockSize; j += smallBlockSize {
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
