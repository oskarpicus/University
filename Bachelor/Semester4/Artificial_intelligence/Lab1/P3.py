def dotProduct(l, k):
    """
    Method for computing the scalar product between 2 vectors (recursion)
    :param l: list, the first vector
    :param k: list, the second vector
    :return: l•k
    """
    if len(l) == 0 and len(k) == 0:
        return 0
    return l[0]*k[0]+dotProduct(l[1:],k[1:])


def dotProductIterative(l, k):
    """
    Method for computing the scalar product between 2 vectors (iterative)
    :param l: list, the first vector
    :param k: list, the second vector
    :return: l•k
    """
    result = 0
    for counter in range(0,len(l)):
        result += l[counter]*k[counter]
    return result


assert dotProduct([1,0,2,0,3],[1,2,0,3,1]) == 4
assert dotProductIterative([1,0,2,0,3],[1,2,0,3,1]) == 4