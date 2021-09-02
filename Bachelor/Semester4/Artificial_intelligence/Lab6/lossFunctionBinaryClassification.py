from math import log


def crossEntropyLoss(real: list, computed: list):
    error = 0
    for itReal, itComputed in zip(real, computed):
        error += sum([-(realP*log(computedP, 2)+(1-realP)*log(1-computedP, 2))
                      for realP, computedP in zip(itReal, itComputed)])
    return error


print(crossEntropyLoss([[1, 0]], [[0.9, 0.1]]))
print(crossEntropyLoss([[1, 0]], [[0.1, 0.9]]))
print(crossEntropyLoss([[1, 0]], [[0.001, 0.999]]))
print(crossEntropyLoss([[1, 0]], [[0.5, 0.5]]))