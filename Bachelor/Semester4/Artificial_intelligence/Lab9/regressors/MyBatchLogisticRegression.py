from numpy import exp


class MyBatchLogisticRegression:
    def __init__(self, trainInput: list, trainOutput: list, parameters: dict, labels: list):
        self.__parameters = parameters
        self.__coefficients = []
        self.__train(trainInput, trainOutput, labels)

    @property
    def coefficients(self) -> list:
        return self.__coefficients

    def __train(self, trainInput: list, trainOutput: list, labels: list):
        for label in labels:
            self.__trainOneModel(trainInput, trainOutput, label)

    def __trainOneModel(self, trainInput: list, trainOutput: list, label: int):
        nrSamples, nrFeatures, learningRate = len(trainInput), len(trainInput[0]), self.__parameters["learningRate"]
        coefficients = [0 for _ in range(nrFeatures + 1)]
        for _ in range(self.__parameters["nrEpochs"]):
            errors = 0
            sums = [0 for _ in range(nrFeatures)]
            for inputSample, outputSample in zip(trainInput, trainOutput):
                computed = coefficients[0]
                computed += sum(coefficient * feature for coefficient, feature in zip(coefficients[1:], inputSample))
                computed = MyBatchLogisticRegression.__sigmoid(computed)
                realOutput = 1 if outputSample == label else 0
                error = computed - realOutput
                errors += error

                for featureIndex in range(nrFeatures):
                    sums[featureIndex] += inputSample[featureIndex] * error

            errors /= nrSamples
            for i in range(nrFeatures + 1):
                if i == 0:
                    coefficients[0] = coefficients[0] - learningRate * errors
                else:
                    coefficients[i] = coefficients[i] - learningRate * (sums[i - 1] / nrSamples)

        self.__coefficients.append(coefficients)

    @staticmethod
    def __sigmoid(x):
        return 1 / (1 + exp(-x))

    def predict(self, validInput: list) -> list:
        return [self.__predictOneSample(sample) for sample in validInput]

    def __predictOneSample(self, sample: list) -> int:
        maximum, maximumIndex = float("-inf"), 0
        for i, pairCoefficient in enumerate(self.__coefficients):
            computed = pairCoefficient[0]
            computed += sum(coefficient * feature for coefficient, feature in zip(pairCoefficient[1:], sample))
            computed = MyBatchLogisticRegression.__sigmoid(computed)
            if computed > maximum:
                maximum = computed
                maximumIndex = i
        return maximumIndex