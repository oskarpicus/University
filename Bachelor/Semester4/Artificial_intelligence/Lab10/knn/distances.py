from math import sqrt


def euclideanDistance(vector1: list, vector2: list) -> float:
    term = sum((x-y)**2 for x, y in zip(vector1, vector2))
    return sqrt(term)