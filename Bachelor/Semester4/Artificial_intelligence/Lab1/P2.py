from math import sqrt


def distance(l1,l2):
    """
    Method for finding the distance between 2 points
    :param l1: list of 2 numbers, the first point
    :param l2: list of 2 numbers, the second point
    :return: dist(l1,l2)
    """
    first = (l1[0] - l2[0]) ** 2
    second = (l1[1] - l2[1]) ** 2
    return sqrt(first + second)


assert distance([3,0],[2,0]) == 1
assert distance([1,5],[4,1]) == 5
