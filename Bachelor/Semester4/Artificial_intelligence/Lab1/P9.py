def sumSubMatrix(matrix, pairs):
    """
    Method for computing the sum of every sub-matrix determined by a pair of points
    :param matrix: list of lists of ints
    :param pairs: list of pairs
    :return: list of all the sums of the sub-matrices of matrix determined by pairs
    """
    result = []
    for pair in pairs:
        currentSum = 0
        start = pair[0]
        end = pair[1]
        for i in range(start[0], end[0]+1):
            for j in range(start[1], end[1]+1):
                currentSum += matrix[i][j]
        result.append(currentSum)
    return result


matrix = [[0,2,5,4,1],[4,8,2,3,7],[6,3,4,6,2],[7,3,1,8,3],[1,5,7,9,4]]
print(sumSubMatrix(matrix,[[[1,1],[3,3]]]))
print(sumSubMatrix(matrix,[[[2,2],[4,4]]]))
print(sumSubMatrix(matrix,[[[0,0],[2,0]]]))
print(sumSubMatrix(matrix,[[[1,1],[1,1]]]))
assert sumSubMatrix(matrix,[ [[1,1],[3,3]], [ [2,2], [4,4] ]]) == [38,44]
assert sumSubMatrix(matrix,[[[1,1],[3,3]],[[0,0],[4,4]]]) == [38,105]