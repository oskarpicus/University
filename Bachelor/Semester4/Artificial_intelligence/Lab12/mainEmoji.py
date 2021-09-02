from sklearn.linear_model import SGDClassifier
from utils.data import splitData
from utils.loadEmojis import loadEmojis
from utils.evaluation import multiClassEvaluation
from random import seed


# 0 - happy, 1 - sad
seed(93349775246114)
inputData, outputData = loadEmojis()

trainInput, trainOutput, validInput, validOutput = splitData(inputData, outputData, 8/10)

classifier = SGDClassifier(random_state=42, max_iter=1000, tol=1e-3, verbose=True)
classifier.fit(inputData, outputData)

validComputedOutput = list(classifier.predict(validInput))

print(f"REAL     {validOutput}")
print(f"COMPUTED {validComputedOutput}")

accuracy, precision, recall = multiClassEvaluation(validOutput, validComputedOutput, [0, 1])
print(f"accuracy {accuracy}\nprecision {precision}\nrecall {recall}")