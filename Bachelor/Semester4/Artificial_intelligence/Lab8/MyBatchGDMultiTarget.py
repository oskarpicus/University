from MyBatchGDSingleTarget import MyBatchGDSingleTarget
from evaluate import model


class MyBatchGDMultiTarget:
    def __init__(self, parameters: dict, trainInput: list, trainOutput: list):
        self.__parameters = parameters
        self.__coefficients = []
        self.__train(trainInput, trainOutput)

    def __train(self, trainInput: list, trainOutput: list):  # problem transformation
        nrTargets = len(trainOutput[0])
        for targetIndex in range(nrTargets):
            outputs = [out[targetIndex] for out in trainOutput]
            reg = MyBatchGDSingleTarget(self.__parameters, trainInput, outputs)
            self.__coefficients.append(reg.coefficients)

    @property
    def coefficients(self):
        return self.__coefficients

    def predict(self, data: list) -> list:
        nrSamples = len(data)
        result = [[] for _ in range(nrSamples)]
        for i, sample in enumerate(data):
            for coefficientSet in self.__coefficients:
                currentTarget = model(coefficientSet, sample)
                result[i].append(currentTarget)
        return result