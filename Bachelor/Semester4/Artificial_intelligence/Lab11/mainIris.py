from utils.loadData import loadData
from utils.data import normalise, splitData, mapClasses
from utils.evaluation import multiClassEvaluation
from kMeans.distances import euclideanDistance
from kMeans.kMeans import KMeans
from random import seed


seed(7013210226568)
features = ["SepalLength", "SepalWidth", "PetalLength", "PetalWidth"]
target = "Class"
inputs, outputs = loadData("data/iris.data", features, target)
classes = ["Iris-setosa", "Iris-versicolor", "Iris-virginica"]

mapClasses(outputs, classes)

trainInput, _, validInput, validOutput = splitData(inputs, outputs, 8/10)
normalise(trainInput, validInput)

parameters = {"k": 3, "distance": euclideanDistance}

classifier = KMeans(parameters)

classifier.train(trainInput)

validComputedOutput = [classifier.predict(sample) for sample in validInput]

print(validOutput)
print(validComputedOutput)

accuracy, precision, recall = multiClassEvaluation(validOutput, validComputedOutput, [0, 1, 2])
print(f"accuracy {accuracy}\nprecision {precision}\nrecall {recall}")