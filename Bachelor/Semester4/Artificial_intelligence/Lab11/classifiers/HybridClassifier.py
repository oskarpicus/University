from kMeans.kMeans import KMeans
from sklearn.neural_network import MLPClassifier


class HybridClassifier:
    def __init__(self, parameters: dict, lexicon: dict, vocabulary: list):
        self.__parameters = parameters
        self.__unsupervisedClassifier = KMeans(parameters)
        self.__supervisedClassifier = MLPClassifier(hidden_layer_sizes=(20, 10), activation='relu',
                                                    max_iter=100, solver='sgd', verbose=False, random_state=1,
                                                    learning_rate_init=.1)
        self.__lexicon = lexicon
        self.__vocabulary = vocabulary

    def train(self, trainInput: list):
        epsilon = self.__parameters["epsilon"]
        supervisedExamples, supervisedOutputs, unsupervisedExamples = [], [], []
        for example in trainInput:
            nrP = self.__getTotalWordsByClass(example, "positive")
            nrN = self.__getTotalWordsByClass(example, "negative")
            ratio = nrP / nrN if nrN else float('inf')
            if 1 - epsilon < ratio < 1 + epsilon or nrP == nrN:  # to unsupervised
                unsupervisedExamples.append(example)
            else:  # to supervised
                supervisedExamples.append(example)
                supervisedOutputs.append(1 if nrP > nrN else 0)
        self.__unsupervisedClassifier.train(unsupervisedExamples)
        self.__supervisedClassifier.fit(supervisedExamples, supervisedOutputs)

    def __getTotalWordsByClass(self, example: list, c: str) -> int:
        result = 0
        for word in self.__lexicon[c]:
            try:
                index = self.__vocabulary.index(word)
                result += example[index]
            except ValueError:
                result += 0
        return result

    def predict(self, example: list) -> int:
        epsilon = self.__parameters["epsilon"]
        nrP = self.__getTotalWordsByClass(example, "positive")
        nrN = self.__getTotalWordsByClass(example, "negative")
        ratio = nrP / nrN if nrN else float('inf')
        if 1 - epsilon < ratio < 1 + epsilon or nrP == nrN:
            return self.__unsupervisedClassifier.predict(example)
        else:
            return list(self.__supervisedClassifier.predict([example]))[0]