from utils.data import splitData, normalise
from utils.evaluation import multiClassEvaluation
from utils.loadPictures import loadPictures
from knn.distances import euclideanDistance
from knn.kNN import KNN
from random import seed


seed(2142867412879772221)
dataInput, dataOutput = loadPictures()

trainInput, trainOutput, validInput, validOutput = splitData(dataInput, dataOutput, 8/10)
normalise(trainInput, validInput)

parameters = {"distance": euclideanDistance, "k": 5}

knn = KNN(parameters)
knn.train(trainInput, trainOutput)

validComputedOutput = [knn.predict(sample) for sample in validInput]
print(validOutput)
print(validComputedOutput)

accuracy, precision, recall = multiClassEvaluation(validOutput, validComputedOutput, [0, 1])
print(f"accuracy {accuracy}\nprecision {precision}\nrecall {recall}")