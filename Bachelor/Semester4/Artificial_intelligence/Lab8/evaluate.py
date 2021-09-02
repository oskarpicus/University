def meanAbsoluteError(real: list, computed: list) -> float:
    noSamples = len(real)
    error = 0
    for it1, it2 in zip(real, computed):
        error += abs(it1 - it2)
    return error / noSamples


def multiTargetMeanAbsoluteError(real: list, computed: list) -> float:
    noSamples, nrOutputs = len(real), len(real[0])
    sumTerms = 0
    for it1, it2 in zip(real, computed):
        s = sum([abs(el1-el2) for el1, el2 in zip(it1, it2)])
        sumTerms += s/nrOutputs
    return sumTerms / noSamples


def model(coefficients: list, features: list) -> float:
    return coefficients[0] + sum(c * f for c, f in zip(coefficients[1:], features))