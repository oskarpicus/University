from math import log, exp


def softmax(computed: list):
    result = []
    for i, sample in enumerate(computed):
        currentResult = []
        for aClass in sample:
            total = sum([exp(el) for el in aClass])
            currentResult.append([exp(el)/total for el in aClass])
        result.append(currentResult)
    return result


def crossEntropyLoss(real: list, computed: list):
    probabilities = softmax(computed)
    loss = 0
    for sampleReal, sampleComputed in zip(real, probabilities):
        for classReal, classComputed in zip(sampleReal, sampleComputed):
            loss += sum([-elReal*log(elComputed, 2) for elReal, elComputed in zip(classReal, classComputed)])
    return loss


print(softmax([[[5, 3], [7, 1]]]))
print(softmax([[[3.2, 1.3, 0.2, 0.8]]]))
print(softmax([[[0, 4, 6], [9, 2, 42], [1, 4, 7]]]))
print(crossEntropyLoss([[[1, 0], [0, 1]]], [[[3, 5], [1, 7]]]))