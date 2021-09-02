from utils.loadData import loadData
from utils.data import splitData, mapClasses, normalise
from utils.evaluation import multiClassEvaluation
from random import seed
from ann.ANN import ANN


seed(541250197)
features = ["SepalLength", "SepalWidth", "PetalLength", "PetalWidth"]
target = "Class"
inputs, outputs = loadData("data/iris.data", features, target)
classes = ["Iris-setosa", "Iris-versicolor", "Iris-virginica"]
labels = [i for i in range(0, len(classes))]

mapClasses(outputs, classes)

trainInput, trainOutput, validInput, validOutput = splitData(inputs, outputs, 8/10)
normalise(trainInput, validInput)

parameters = {"nrEpochs": 50, "learningRate": 0.1, "layers": [2]}

ann = ANN(parameters, trainInput, trainOutput, labels)
validComputedOutput = [ann.predict(sample) for sample in validInput]
eval = multiClassEvaluation(validOutput, validComputedOutput, labels)
print(f"accuracy {eval[0]}")