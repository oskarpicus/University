package encoder

import (
	"math"
	"math/bits"
)

// zigZagTraversal performs a zig-zag traversal on every matrix from a given collection of matrices.
func (jpeg *Jpeg) zigZagTraversal(blocks map[*Position][][]float64) map[*Position][]float64 {
	result := make(map[*Position][]float64)

	for position, matrix := range blocks {
		result[position] = jpeg.zigZagTraversalOneMatrix(matrix)
	}

	return result
}

// zigZagTraversalOneMatrix performs a single zig-zag traversal on a given matrix.
func (jpeg *Jpeg) zigZagTraversalOneMatrix(matrix [][]float64) []float64 {
	result := make([]float64, len(matrix)*len(matrix[0]))
	counter := 0
	m, n := len(matrix), len(matrix[0])

	// perform a scan for every diagonal
	for i := 0; i < n+m-1; i++ {
		if i%2 == 1 {  // go down-left
			// start position of traversal
			x, y := 0, i
			if i >= n {
				x = i - n + 1
				y = n - 1
			}

			for x < m && y >= 0 {
				result[counter] = matrix[x][y]
				counter++
				x++
				y--
			}
		} else {  // go up-right
			x, y := i, 0
			if i >= m {
				x = m - 1
				y = i - m + 1
			}

			for x >= 0 && y < n {
				result[counter] = matrix[x][y]
				counter++
				x--
				y++
			}
		}
	}

	return result
}

// encodeCoefficients encodes collections of coefficients in an array of triplets following the format (run-length, size, amplitude).
func (jpeg *Jpeg) encodeCoefficients(y, u, v map[*Position][]float64) []int {
	result := make([]int, 0)

	for position, yArray := range y {
		cbBlock, crBlock := u[position], v[position]

		result = append(result, position.Line)
		result = append(result, position.Column)

		result = append(result, jpeg.encodeCoefficientArray(yArray)...)
		result = append(result, jpeg.encodeCoefficientArray(cbBlock)...)
		result = append(result, jpeg.encodeCoefficientArray(crBlock)...)
	}

	return result
}

// encodeCoefficientArray performs a single encoding in triplets following the format (run-length, size, amplitude).
func (jpeg *Jpeg) encodeCoefficientArray(array []float64) []int {
	getSize := func (value float64) int {
		return bits.Len(uint(math.Abs(value)))
	}

	result := make([]int, 0)
	currentZeros := 0

	for index, value := range array {
		if index == 0 {  // DC Coefficient
			result = append(result, []int{getSize(value), int(value)}...)
			continue
		}

		if value == 0 {
			currentZeros++
			continue
		}

		result = append(result, []int{currentZeros, getSize(value), int(value)}...)
		currentZeros = 0
	}

	if currentZeros != 0 {
		result = append(result, []int{0, 0}...)
	}

	return result
}
