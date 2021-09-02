from utils.loadData import loadData
from utils.data import mapClasses, splitData
from textCharacteristics.nGram import *
from kMeans.kMeans import KMeans
from kMeans.distances import euclideanDistance
from utils.evaluation import multiClassEvaluation
from random import seed


seed(487377788047)
features = ["Text"]
target = "Sentiment"
classes = ["negative", "positive"]

inputs, outputs = loadData("data/reviews_mixed.csv", features, target)
mapClasses(outputs, classes)

trainInput, trainOutput, validInput, validOutput = splitData(inputs, outputs, 8/10)

vocabulary = getVocabulary(trainInput, n=3)

print(vocabulary)
print(len(vocabulary))

trainInput = convert(trainInput, vocabulary)
validInput = convert(validInput, vocabulary)

print(trainInput)
print(validInput)

parameters = {"k": len(classes), "distance": euclideanDistance}
classifier = KMeans(parameters)
classifier.train(trainInput)

validComputedOutput = [classifier.predict(sample) for sample in validInput]

print(f"REAL     {validOutput}\nCOMPUTED {validComputedOutput}")

accuracy, precision, recall = multiClassEvaluation(validOutput, validComputedOutput, [0, 1])
print(f"accuracy {accuracy}\nprecision {precision}\nrecall {recall}")