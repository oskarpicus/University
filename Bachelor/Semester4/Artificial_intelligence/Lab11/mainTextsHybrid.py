from utils.loadData import loadData
from utils.data import splitData, mapClasses
from classifiers.HybridClassifier import HybridClassifier
from kMeans.distances import euclideanDistance
from textCharacteristics.bagOfWords import convert, getVocabulary
from utils.evaluation import multiClassEvaluation
from random import seed


seed(23718215794714)
features = ["Text"]
target = "Sentiment"
classes = ["negative", "positive"]

inputs, outputs = loadData("data/reviews_mixed.csv", features, target)
mapClasses(outputs, classes)

trainInput, trainOutput, validInput, validOutput = splitData(inputs, outputs, 8/10)

vocabulary = getVocabulary(trainInput)
trainInput = convert(trainInput, vocabulary)
validInput = convert(validInput, vocabulary)

parameters = {"k": len(classes), "distance": euclideanDistance, "epsilon": 0.01}
lexicon = {"positive": ["yes", "beautiful", "best", "comfortable", "lovely", "clean", "great", "well", "amazing"],
           "negative": ["no", "worst", "only", "not", "filthy", "repulsive", "uncomfortable", "dirty", "noisy", "issue"]}

classifier = HybridClassifier(parameters, lexicon, vocabulary)
classifier.train(trainInput)

validComputedOutput = [classifier.predict(example) for example in validInput]

print(f"REAL     {validOutput}\nCOMPUTED {validComputedOutput}")

accuracy, precision, recall = multiClassEvaluation(validOutput, validComputedOutput, [0, 1])
print(f"accuracy {accuracy}\nprecision {precision}\nrecall {recall}")