from math import log
from numpy import exp


def difference(real: float, computed: float) -> float:
    return computed - real


def exponential(real: float, computed: float) -> float:
    return exp(-real * computed)


def crossEntropyLoss(real: float, computed: float) -> float:
    if real == 0:
        return -log(computed if computed != 0 else 0.00000000000001)
    return -log(1 - computed if computed != 1 else 0.99999999999999)