package decoder

import (
	"PDAV/internal/encoder"
	"math"
)

// decodeCoefficients decodes a collection of coefficients triplets.
// It's return value is equal to that of a zig-zag traversal on each matrix in the original collection.
func (jpeg *Jpeg) decodeCoefficients(encoded []int) (map[*encoder.Position][]float64, map[*encoder.Position][]float64, map[*encoder.Position][]float64) {
	y, cb, cr := make(map[*encoder.Position][]float64), make(map[*encoder.Position][]float64), make(map[*encoder.Position][]float64)

	var currentPosition *encoder.Position = nil
	for i := 0; i < len(encoded); i++ {
		currentPosition = &encoder.Position{
			Line:   encoded[i],
			Column: encoded[i+1],
		}

		i += 2

		y[currentPosition], i = jpeg.decodeCoefficientsBlock(encoded, i)
		cb[currentPosition], i = jpeg.decodeCoefficientsBlock(encoded, i)
		cr[currentPosition], i = jpeg.decodeCoefficientsBlock(encoded, i)
		i--
	}

	return y, cb, cr
}

// decodeCoefficientsBlock performs a single decoding, resulting the original zig-zag traversal of the matrix.
// Will also return the final scanned index in the array.
func (jpeg *Jpeg) decodeCoefficientsBlock(array []int, start int) ([]float64, int) {
	// the slice is initialised with 0 values
	result := make([]float64, bigBlockSize*bigBlockSize)

	startBlock := true
	current := start
	index := 0

	for ; current < len(array)-1 && index < bigBlockSize*bigBlockSize; current++ {
		// take the DC coefficient
		if startBlock {
			// skip the size
			current++
			result[index] = float64(array[current])
			index++
			startBlock = false
			continue
		}

		// double 0s, end of block
		if array[current] == 0 && array[current+1] == 0 {
			index += bigBlockSize*bigBlockSize - len(result)
			current += 1
			break
		}

		// will need to add the value
		if array[current] == 0 {
			// skip the 0 and the size
			current += 2
			result[index] = float64(array[current])
			index++
		} else {
			index += array[current]

			// skip the run-length and size
			current += 2
			// add the value
			result[index] = float64(array[current])
			index++
		}
	}

	return result, current + 1
}

func (jpeg *Jpeg) convertToMatrix(coefficients map[*encoder.Position][]float64) map[*encoder.Position][][]float64 {
	result := make(map[*encoder.Position][][]float64)

	for position, array := range coefficients {
		result[position] = jpeg.fromArrayToMatrix(array)
	}

	return result
}

func (jpeg *Jpeg) fromArrayToMatrix(array []float64) [][]float64 {
	dimension := int(math.Sqrt(float64(len(array))))
	result := make([][]float64, dimension)
	counter := 0

	for i := 0; i < dimension; i++ {
		result[i] = make([]float64, dimension)
	}

	// perform a scan for every diagonal
	for i := 0; i < 2*dimension-1; i++ {
		if i%2 == 1 { // go down-left
			// start position of traversal
			x, y := 0, i
			if i >= dimension {
				x = i - dimension + 1
				y = dimension - 1
			}

			for x < dimension && y >= 0 {
				result[x][y] = array[counter]
				counter++
				x++
				y--
			}
		} else { // go up-right
			x, y := i, 0
			if i >= dimension {
				x = dimension - 1
				y = i - dimension + 1
			}

			for x >= 0 && y < dimension {
				result[x][y] = array[counter]
				counter++
				x--
				y++
			}
		}
	}

	return result
}
