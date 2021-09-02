from sklearn.linear_model import SGDRegressor


class ToolBatchGDSingleTarget:
    def __init__(self, parameters, trainDataInput, trainDataOutput):
        self.__parameters = parameters
        self.__reg = None
        self.__train(trainDataInput, trainDataOutput)

    def __train(self, trainDataInput, trainDataOutput):
        self.__reg = SGDRegressor()
        for _ in range(self.__parameters["nrEpochs"]):
            self.__reg.partial_fit(trainDataInput, trainDataOutput)

    @property
    def coefficients(self):
        return self.__reg.intercept_.tolist() + self.__reg.coef_.tolist()
