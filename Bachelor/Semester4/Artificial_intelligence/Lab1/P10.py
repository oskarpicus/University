def onesLine(matrix):
    """
    Method for determining the line with the most elements equal to 1 in a matrix
    :param matrix: list of lists of ints, binary matrix with sorted lines
    :return: the index of the line with the most elements equal to 1 in matrix
    """
    result = None
    minimum = float('inf')
    for counter, line in enumerate(matrix):
        nrZeros = 0
        for element in line:
            if element == 1:
                break
            nrZeros += 1
        if nrZeros < minimum:
            minimum = nrZeros
            result = counter
    return result+1


assert onesLine([[0,0,0,1,1],[0,1,1,1,1],[0,0,1,1,1]]) == 2
assert onesLine([ [0,1,1], [0,0,0], [1,1,1], [0,0,1] ]) == 3