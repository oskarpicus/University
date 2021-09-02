from utils.loadData import loadData
from utils.data import splitData, mapClasses
from textCharacteristics.bagOfWords import convert, getVocabulary
from sklearn.neural_network import MLPClassifier
from utils.evaluation import multiClassEvaluation
from random import seed


seed(525004925458)
features = ["Text"]
target = "Sentiment"
classes = ["negative", "positive"]

inputs, outputs = loadData("data/reviews_mixed.csv", features, target)
mapClasses(outputs, classes)

trainInput, trainOutput, validInput, validOutput = splitData(inputs, outputs, 8/10)

vocabulary = getVocabulary(trainInput)
trainInput = convert(trainInput, vocabulary)
validInput = convert(validInput, vocabulary)

nrWords = len(vocabulary)
print(nrWords)
print(vocabulary)
print(trainInput)
print(validInput)

classifier = MLPClassifier(hidden_layer_sizes=(nrWords//2, nrWords//4), activation='relu',
                           max_iter=100, solver='sgd', verbose=False, random_state=1,
                           learning_rate_init=.1)
classifier.fit(trainInput, trainOutput)

validComputedOutput = list(classifier.predict(validInput))

print(f"REAL     {validOutput}\nCOMPUTED {validComputedOutput}")

accuracy, precision, recall = multiClassEvaluation(validOutput, validComputedOutput, [0, 1])
print(f"accuracy {accuracy}\nprecision {precision}\nrecall {recall}")