from sklearn.metrics import mean_absolute_error


def meanAbsoluteErrorTool(real: list, computed: list):
    return mean_absolute_error(real, computed)


def meanAbsoluteError(real: list, computed: list):
    noSamples = len(real)
    sumTerms = 0
    for it1, it2 in zip(real, computed):
        sumTerms += abs(it1 - it2)
    return sumTerms / noSamples


def model(coefficients: list, features: list):
    return coefficients[0] + sum(c * f for c, f in zip(coefficients[1:], features))