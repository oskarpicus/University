from random import sample
from copy import deepcopy


class KMeans:
    def __init__(self, parameters: dict):
        self.__parameters = parameters
        self.__clusters = []
        self.__centroids = []
        self.__trainInput = None

    def train(self, trainInput: list):
        self.__trainInput = trainInput
        self.__initialise()
        while True:
            nrChanges = self.__clusterAssignment()
            self.__moveCentroids()
            if nrChanges == 0:
                break

    def predict(self, example: list) -> int:
        result, minimumDistance = None, float('inf')
        for cluster, centroid in enumerate(self.__centroids):
            distance = self.__parameters["distance"](example, centroid)
            if distance < minimumDistance:
                minimumDistance = distance
                result = cluster
        return result

    def __initialise(self):
        chosen = []
        self.__clusters = [float('-inf') for _ in range(len(self.__trainInput))]  # assigned clusters
        for i in range(self.__parameters["k"]):
            centroid = sample(self.__trainInput, k=1)[0]
            while centroid in chosen:
                centroid = sample(self.__trainInput, k=1)[0]
            self.__centroids.append(deepcopy(centroid))
            chosen.append(centroid)

    def __clusterAssignment(self) -> float:
        nrChanges = 0
        for indexExample, example in enumerate(self.__trainInput):
            clusterAssigned, minimumDistance = None, float('inf')

            for indexCentroid, centroid in enumerate(self.__centroids):
                distance = self.__parameters["distance"](example, centroid)
                if distance < minimumDistance:
                    minimumDistance = distance
                    clusterAssigned = indexCentroid

            if clusterAssigned != self.__clusters[indexExample]:  # there was some change
                nrChanges += 1
                self.__clusters[indexExample] = clusterAssigned
        return nrChanges

    def __moveCentroids(self):
        nrFeatures = len(self.__trainInput[0])
        for i in range(self.__parameters["k"]):
            assignedSamples = self.__assignedSamples(i)
            length = len(assignedSamples)
            for feature in range(nrFeatures):
                self.__centroids[i][feature] = sum(example[feature] for example in assignedSamples) / length

    def __assignedSamples(self, cluster: int) -> list:
        result = []
        for index, example in enumerate(self.__trainInput):
            if self.__clusters[index] == cluster:
                result.append(example)
        return result