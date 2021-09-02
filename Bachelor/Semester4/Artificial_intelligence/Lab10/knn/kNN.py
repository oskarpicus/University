class KNN:
    def __init__(self, parameters: dict):
        self.__parameters = parameters
        self.__trainInput = None
        self.__trainOutput = None

    def train(self, trainInput: list, trainOutput: list):
        self.__trainInput = trainInput
        self.__trainOutput = trainOutput

    def predict(self, sample: list) -> int:
        sortedInputs, sortedOutputs = zip(*sorted(zip(self.__trainInput, self.__trainOutput),
                                                  key=lambda x: self.__parameters["distance"](x[0], sample)))
        k, k1 = self.__parameters["k"], self.__parameters["k"]
        votes = {}
        while k > 0:
            currentPrediction = sortedOutputs[k1 - k]
            if currentPrediction not in votes:
                votes.update({currentPrediction: 1})
            else:
                votes[currentPrediction] = 1
            k -= 1
        result, maximum = None, float("-inf")
        for key in votes.keys():
            value = votes[key]
            if value > maximum:
                result = key
        return result