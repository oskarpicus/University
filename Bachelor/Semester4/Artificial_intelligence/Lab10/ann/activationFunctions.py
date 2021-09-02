from numpy import exp


def identity(data: float):
    return data


def sigmoid(data: float):
    return 1 / (1 + exp(-data))