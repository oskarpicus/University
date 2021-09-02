from utils.loadHaar import loadHaar
from utils.data import splitData
from sklearn.linear_model import SGDClassifier
from utils.evaluation import multiClassEvaluation


happyCategories = ["happy", "wink", "normal"]
sadCategories = ["sad", "sleepy", "glasses"]
featureList = ['type-2-x', 'type-2-y']

inputData, outputData = loadHaar(happyCategories, sadCategories, featureList)
print("Finished loading images")

trainInput, trainOutput, validInput, validOutput = splitData(inputData, outputData, 8/10)

classifier = SGDClassifier(random_state=42, max_iter=2000, tol=1e-3)
classifier.fit(inputData, outputData)

validComputedOutput = list(classifier.predict(validInput))

print(f"REAL     {validOutput}")
print(f"COMPUTED {validComputedOutput}")

accuracy, precision, recall = multiClassEvaluation(validOutput, validComputedOutput, [0, 1])
print(f"accuracy {accuracy}\nprecision {precision}\nrecall {recall}")