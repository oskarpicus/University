import numpy as np


class MyLeastSquares:
    def __init__(self, trainDataInput, trainDataOutput):
        self.__coefficients = []
        self.__train(trainDataInput, trainDataOutput)

    def __train(self, trainDataInput, trainDataOutput):
        y = np.array([trainDataOutput]).transpose()  # column matrix
        X = trainDataInput
        for row in X:
            row.insert(0, 1)
        X = np.array(X)
        Xt = X.transpose()
        XtX_1 = np.linalg.inv(np.matmul(Xt, X))
        self.__coefficients = np.matmul(np.matmul(XtX_1, Xt), y).tolist()  # column matrix
        self.__coefficients = [item for sublist in self.__coefficients for item in sublist]

    @property
    def coefficients(self):
        return self.__coefficients