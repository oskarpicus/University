from numpy import exp
from random import shuffle


class MyLogisticRegression:
    def __init__(self, trainInput, trainOutput, parameters, labels: list):
        self.__parameters = parameters
        self.__coefficients = []
        self.__train(trainInput, trainOutput, labels)

    def __train(self, trainInput: list, trainOutput: list, labels: list):
        for label in labels:
            self.__trainOneModel(trainInput, trainOutput, label)

    def __trainOneModel(self, trainInput: list, trainOutput: list, label: int):
        nrSamples, nrFeatures, learningRate = len(trainInput), len(trainInput[0]), self.__parameters["learningRate"]
        coefficients = [0 for _ in range(nrFeatures + 1)]
        for _ in range(self.__parameters["nrEpochs"]):
            shuffle(trainInput)
            for inputSample, outputSample in zip(trainInput, trainOutput):
                computed = coefficients[0] + sum(coefficient * feature for
                                                 coefficient, feature in zip(coefficients[1:], inputSample))
                computed = MyLogisticRegression.__sigmoid(computed)
                realOutput = 1 if outputSample == label else 0
                error = self.__parameters["lossFunction"](realOutput, computed)

                # update coefficients
                for index in range(len(coefficients)):
                    if index == 0:
                        coefficients[index] = coefficients[index] - learningRate * error
                    else:
                        coefficients[index] = coefficients[index] - learningRate * error * inputSample[index - 1]

        self.__coefficients.append(coefficients)

    @property
    def coefficients(self) -> list:
        return self.__coefficients

    def predict(self, validInput: list) -> list:
        return [self.__predictOneSample(sample) for sample in validInput]

    @staticmethod
    def __sigmoid(x):
        return 1 / (1 + exp(-x))

    def __predictOneSample(self, sample):
        maximum, maximumIndex = float('-inf'), None
        for i, pairCoefficients in enumerate(self.__coefficients):
            computed = sum(feature * coefficient for feature, coefficient in zip(sample, pairCoefficients))
            computed = MyLogisticRegression.__sigmoid(computed)
            if computed > maximum:
                maximum = computed
                maximumIndex = i
        return maximumIndex

    def predictIrisSetosa(self, validInput: list) -> list:
        coefficients = self.coefficients[0]
        result = []
        for inputSample in validInput:
            model = sum(c * i for c, i in zip(coefficients, inputSample))
            model = MyLogisticRegression.__sigmoid(model)
            if model < self.__parameters["threshold"]:
                result.append(0)
            else:
                result.append(1)
        return result