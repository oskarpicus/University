from utils.loadData import loadData
from utils.data import splitData, normalise, mapClasses
from utils.evaluate import multiClassEvaluation
from regressors.ToolLogisticRegression import ToolLogisticRegression
from regressors.MyLogisticRegression import MyLogisticRegression
from utils.lossFunctions import *
from random import seed


seed(286611279)
features = ["SepalLength", "SepalWidth", "PetalLength", "PetalWidth"]
target = "Class"
inputs, outputs = loadData("data/iris.data", features, target)
classes = ["Iris-setosa", "Iris-versicolor", "Iris-virginica"]
labels = [i for i in range(0, len(classes))]

mapClasses(outputs, classes)

trainInput, trainOutput, validInput, validOutput = splitData(inputs, outputs, 8/10)
normalise(trainInput, validInput)

parameters = {"nrEpochs": 5000, "learningRate": 0.3, "threshold": 0.5, "lossFunction": difference}

# TOOL
ml = ToolLogisticRegression(trainInput, trainOutput, parameters)
validComputedOutput = ml.predict(validInput)
accuracy, precision, recall = multiClassEvaluation(validOutput, validComputedOutput, labels)
print("TOOL")
for coefficient in ml.coefficients:
    print(coefficient)
print(f"accuracy {accuracy}\nprecision {precision}\nrecall {recall}")

print(validComputedOutput)
# MANUAL
ml = MyLogisticRegression(trainInput, trainOutput, parameters, labels)
validComputedOutput = ml.predict(validInput)
print(validComputedOutput)
print("MANUAL")
for coefficient in ml.coefficients:
    print(coefficient)
accuracy, precision, recall = multiClassEvaluation(validOutput, validComputedOutput, labels)
print(f"accuracy {accuracy}\nprecision {precision}\nrecall {recall}")

# PREDICT IRIS SETOSA
correctOutput = [0 if sampleResult != 0 else 1 for sampleResult in validOutput]
computedOutput = ml.predictIrisSetosa(validInput)
accuracy, precision, recall = multiClassEvaluation(correctOutput, computedOutput, [1, 0])
print(f"\nPREDICT IRIS SETOSA\naccuracy {accuracy}\nprecision {precision}\nrecall {recall}")