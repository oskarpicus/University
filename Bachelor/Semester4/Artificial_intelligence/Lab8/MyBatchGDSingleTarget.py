class MyBatchGDSingleTarget:
    def __init__(self, parameters, trainDataInput, trainDataOutput):
        self.__parameters = parameters
        self.__coefficients = []
        self.__train(trainDataInput, trainDataOutput)

    def __train(self, trainDataInput, trainDataOutput):
        nrSamples, learningRate = len(trainDataInput), self.__parameters["learningRate"]
        meansFeatures = self.__meanFeatures(trainDataInput)
        self.__coefficients = [0 for _ in range(len(trainDataInput[0]) + 1)]
        for _ in range(self.__parameters["nrEpochs"]):
            error = 0
            for inputSample, realOutput in zip(trainDataInput, trainDataOutput):
                computed = self.__coefficients[0]
                computed += sum(coefficient * feature for coefficient, feature in
                                zip(self.__coefficients[1:], inputSample))
                error += computed - realOutput

            error /= nrSamples
            for i in range(len(self.__coefficients)):
                if i == 0:
                    self.__coefficients[0] = self.__coefficients[0] - learningRate * error
                else:
                    self.__coefficients[i] = self.__coefficients[i] - learningRate * error * meansFeatures[i - 1]

    def __meanFeatures(self, trainDataInput: list) -> dict:
        nrFeatures, nrSamples = len(trainDataInput[0]), len(trainDataInput)
        result = {}
        for i in range(nrFeatures):
            mean = sum(mySample[i] for mySample in trainDataInput) / nrSamples
            result.update({i: mean})
        return result

    @property
    def coefficients(self):
        return self.__coefficients