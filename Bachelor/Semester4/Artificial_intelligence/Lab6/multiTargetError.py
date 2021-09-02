import sklearn.metrics as sk
from math import sqrt


def meanAbsoluteError(real, computed):
    noSamples, nrOutputs = len(real), len(real[0])
    sumTerms = 0
    for it1, it2 in zip(real, computed):
        s = sum([abs(el1-el2) for el1, el2 in zip(it1, it2)])
        sumTerms += s/nrOutputs
    return sumTerms / noSamples


def rootMeanSquareError(real, computed):
    noSamples, nrOutputs = len(real), len(real[0])
    sumTerms = 0
    for it1, it2 in zip(real, computed):
        s = sum([(el1-el2)**2 for el1, el2 in zip(it1, it2)])
        sumTerms += s/nrOutputs
    return sqrt(sumTerms / noSamples)

# features: neighbourhood, nr. bedrooms, nr. bathrooms
# outputs: sale price, expected nr. of days until sold


def testMeanAbsoluteError():
    real = [[2, 3], [1, 2]]
    computed = [[4, 2], [1, 4]]
    assert meanAbsoluteError(real, computed) == sk.mean_absolute_error(real, computed)
    real, computed = [[1, 2], [2, 3], [50, 9], [23, 4]], [[1, 2], [2, 3], [50, 9], [23, 4]]
    assert meanAbsoluteError(real, computed) == 0
    computed = [[1, 2], [4, 5], [90, 2], [3, 32]]
    assert meanAbsoluteError(real, computed) == sk.mean_absolute_error(real, computed)
    real = [[1, 13], [2, 3], [4, 5], [2, 67], [23, 42], [71, 2]]
    computed = [[0, 13], [2, 5], [4, 5], [2, 9], [23, 42], [70, 5]]
    assert meanAbsoluteError(real, computed) == sk.mean_absolute_error(real, computed)


def testRootMeanSquareError():
    epsilon = 0.1
    real, computed = [[2, 3], [1, 2]], [[4, 2], [1, 4]]
    realResult = sk.mean_squared_error(real, computed, squared=False)
    assert realResult - epsilon <= rootMeanSquareError(real, computed) <= realResult + epsilon
    real, computed = [[1, 2], [2, 3], [50, 9], [23, 4]], [[1, 2], [2, 3], [50, 9], [23, 4]]
    assert rootMeanSquareError(real, computed) == 0


testMeanAbsoluteError()
testRootMeanSquareError()
