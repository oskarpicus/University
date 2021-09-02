from sklearn.multioutput import MultiOutputRegressor
from sklearn.linear_model import SGDRegressor


class ToolGDMultiTarget:
    def __init__(self, parameters, trainInput, trainOutput):
        self.__parameters = parameters
        self.__reg = None
        self.__coefficients = []
        self.__train(trainInput, trainOutput)

    def __train(self, trainInput, trainOutput):
        self.__reg = MultiOutputRegressor(estimator=SGDRegressor(max_iter=self.__parameters["nrEpochs"]))
        self.__reg.fit(trainInput, trainOutput)
        self.__computeCoefficients()

    def predict(self, data: list) -> list:
        return self.__reg.predict(data).tolist()

    def __computeCoefficients(self):
        for estimator in self.__reg.estimators_:
            coefficients = estimator.intercept_.tolist() + estimator.coef_.tolist()
            self.__coefficients.append(coefficients)

    @property
    def coefficients(self):
        return self.__coefficients