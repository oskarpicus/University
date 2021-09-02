from random import seed
from utils.loadPicturesMultiLabel import loadPictures
from utils.evaluation import multiLabelEvaluation
from sklearn.linear_model import SGDClassifier
from sklearn.multioutput import MultiOutputClassifier
from utils.data import splitData


seed(1)
classes = ["happy", "sad", "neutral"]
categories = {"happy": ["happy", "wink", "normal"],
              "sad": ["noglasses", "glasses", "sad"],
              "neutral": ["noglasses", "glasses", "normal"]}

inputData, outputData = loadPictures(classes, categories)

trainInput, trainOutput, validInput, validOutput = splitData(inputData, outputData, 8/10)

estimator = SGDClassifier(random_state=42, max_iter=1000, tol=1e-3)
multiLabelClassifier = MultiOutputClassifier(estimator)

multiLabelClassifier.fit(trainInput, trainOutput)

validComputedOutput = list(multiLabelClassifier.predict(validInput))

print(f"REAL     {validOutput}")
print(f"COMPUTED {validComputedOutput}")

multiLabelEvaluation(validOutput, validComputedOutput, classes)