package encoder

import "math"

// forwardDCT receives as input a collection of 8x8 blocks and returns the collection.
// Applies on the elements of the collection a Forward Discrete Cosine Transformation.
func (jpeg *Jpeg) forwardDCT(blocks map[*Position][][]float64) map[*Position][][]float64 {
	result := make(map[*Position][][]float64)
	for position, block := range blocks {
		result[position] = make([][]float64, len(block))

		for i := 0; i < len(block); i++ {
			result[position][i] = make([]float64, len(block[i]))
			for j := 0; j < len(block[i]); j++ {
				result[position][i][j] = jpeg.applyDCT(block, i, j)
			}
		}
	}

	return result
}

// applyDCT applies a single Forward Discrete Cosine Transformation on an element identified by its line and column indexes.
func (jpeg *Jpeg) applyDCT(matrix [][]float64, u, v int) float64 {
	alpha := func(u int) float64 {
		if u == 0 {
			return 1 / math.Sqrt(2)
		}
		return 1
	}

	result := (1.0 / 4.0) * alpha(u) * alpha(v)
	floatU, floatV := float64(u), float64(v)

	sum := 0.0
	for x := 0; x < len(matrix); x++ {
		for y := 0; y < len(matrix[x]); y++ {
			floatX, floatY := float64(x), float64(y)
			// subtract 128 to center around 0
			sum += (matrix[x][y]-128) * math.Cos((2*floatX+1)*floatU*math.Pi/16) * math.Cos((2*floatY+1)*floatV*math.Pi/16)
		}
	}

	result *= sum
	return result
}

// quantize perform a quantization using a predetermined matrix on a collection of blocks.
func (jpeg *Jpeg) quantize(blocks map[*Position][][]float64) {
	for _, block := range blocks {
		for i := 0; i < len(block); i++ {
			for j := 0; j < len(block[i]); j++ {
				block[i][j] = float64(int(block[i][j]) / q[i][j])
			}
		}
	}
}
