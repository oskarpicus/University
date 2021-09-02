from utils.loadData import loadData
from utils.crossValidation import stratifiedKFold
from utils.data import normalise, mapClasses
from regressors.MyBatchLogisticRegression import MyBatchLogisticRegression
from utils.evaluate import multiClassEvaluation


def mergeBlocks(data: list, indexToLeave: int) -> list:
    result = []
    for index in range(len(data)):
        if index != indexToLeave:
            for sample in data[index]:
                result.append(sample)
    return result


features = ["SepalLength", "SepalWidth", "PetalLength", "PetalWidth"]
target = "Class"
inputs, outputs = loadData("data/iris.data", features, target)
classes = ["Iris-setosa", "Iris-versicolor", "Iris-virginica"]
labels = [i for i in range(0, len(classes))]

parameters = {"nrEpochs": 500, "learningRate": 0.1}

normalise(inputs, [])
mapClasses(outputs, classes)

k = 5
blocksInputs, blockOutputs = stratifiedKFold(inputs, outputs, k=k)

accuracyMean, precisionMean, recallMean = 0, 0, 0

for i in range(k):
    currentInputs = mergeBlocks(blocksInputs, i)
    currentOutputs = mergeBlocks(blockOutputs, i)
    ml = MyBatchLogisticRegression(currentInputs, currentOutputs, parameters, labels)
    print(ml.coefficients)
    computedOutputs = ml.predict(blocksInputs[i])
    accuracy, precision, recall = multiClassEvaluation(currentOutputs, computedOutputs, list(set(currentOutputs)))
    accuracyMean += accuracy
    precisionMean += precision
    recallMean += recall

accuracyMean /= k
precisionMean /= k
recallMean /= k
print(f"accuracy {accuracyMean}\nprecision {precisionMean}\nrecall {recallMean}")