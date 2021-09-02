from sklearn.linear_model import LogisticRegression


class ToolLogisticRegression:
    def __init__(self, trainInput: list, trainOutput: list, parameters: dict):
        self.__parameters = parameters
        self.__coefficients = []
        self.__reg = LogisticRegression(max_iter=parameters["nrEpochs"])
        self.__train(trainInput, trainOutput)

    def __train(self, trainInput: list, trainOutput: list):
        self.__reg.fit(trainInput, trainOutput)
        self.__coefficients = self.__reg.coef_.tolist()
        for i, term in enumerate(self.__reg.intercept_.tolist()):
            self.__coefficients[i].insert(0, term)

    @property
    def coefficients(self):
        return self.__coefficients

    def predict(self, validInput: list) -> list:
        return self.__reg.predict(validInput).tolist()