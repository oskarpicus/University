from utils.loadPictures import loadPictures
from utils.data import splitData
from sklearn.neural_network import MLPClassifier
from utils.evaluation import multiClassEvaluation
from utils.data import normalise
from random import seed


def getStatistics(data: list):
    freq = {}
    for element in data:
        if element not in freq:
            freq[element] = 1
        else:
            freq[element] += 1
    print(f"0 - {freq[0]} length - {len(data)}")


seed(4956328850292439144)
dataInput, dataOutput = loadPictures()
normalise(dataInput, [])

trainInput, trainOutput, validInput, validOutput = splitData(dataInput, dataOutput, 8/10)
getStatistics(trainOutput)
getStatistics(validOutput)

classifier = MLPClassifier(hidden_layer_sizes=(500, 400, 300, 200, 100, 50, 25, ), activation='relu',
                           max_iter=100, solver='sgd', verbose=True, random_state=1,
                           learning_rate_init=.1)
classifier.fit(trainInput, trainOutput)
validComputedOutput = list(classifier.predict(validInput))

print(validOutput)
print(validComputedOutput)
accuracy, precision, recall = multiClassEvaluation(validOutput, validComputedOutput, [0, 1])
print(f"accuracy {accuracy}\nprecision {precision}\nrecall {recall}")