from random import sample


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