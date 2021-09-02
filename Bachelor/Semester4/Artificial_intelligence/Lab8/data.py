from random import sample
from math import sqrt


def splitData(inputs: list, outputs: list, trainPercentage: float):
    length, inputOutput = len(inputs), []
    for i, o in zip(inputs, outputs):
        inputOutput.append(i+[o])
    train = sample(inputOutput, int(trainPercentage * length))
    trainOutput, trainInput, validationInput, validationOutput = [], [], [], []
    for el in inputOutput:
        if el in train:
            trainOutput.append(el[-1])
            trainInput.append(el[:-1])
        else:
            validationInput.append(el[:-1])
            validationOutput.append(el[-1])
    return trainInput, trainOutput, validationInput, validationOutput


def normalise(trainInput: list, validInput: list):
    nrFeatures = len(trainInput[0])
    for featureIndex in range(nrFeatures):
        mean, std = getStatistics(trainInput, featureIndex)
        normaliseData(trainInput, featureIndex, mean, std)
        normaliseData(validInput, featureIndex, mean, std)


def getStatistics(data: list, featureIndex: int) -> list:
    std, nrSamples = 0, len(data)
    mean = sum(mySample[featureIndex] for mySample in data) / nrSamples
    std = sqrt(1 / (nrSamples - 1) * sum(((mySample[featureIndex] - mean) ** 2 for mySample in data)))
    return [mean, std]


def normaliseData(data: list, featureIndex: int, mean: float, std: float):
    for mySample in data:
        mySample[featureIndex] = (mySample[featureIndex] - mean) / std