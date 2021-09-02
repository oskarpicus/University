from sklearn import linear_model


class OrdinaryLeastSquares:
    def __init__(self, trainDataInput, trainDataOutput):
        self.__regression = linear_model.LinearRegression()
        self.__train(trainDataInput, trainDataOutput)

    def __train(self, trainDataInput, trainDataOutput):
        self.__regression.fit(trainDataInput, trainDataOutput)

    @property
    def coefficients(self):
        return self.__regression.intercept_, self.__regression.coef_