from math import sqrt


def euclideanDistance(x: list, y: list) -> float:
    summation = sum((xi - yi)**2 for xi, yi in zip(x, y))
    return sqrt(summation)