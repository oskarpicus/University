from sklearn.datasets import load_linnerud
from data import splitData, normalise
from evaluate import multiTargetMeanAbsoluteError
from MyBatchGDMultiTarget import MyBatchGDMultiTarget
from ToolGDMultiTarget import ToolGDMultiTarget


parameters = {"nrEpochs": 5000, "learningRate": 0.01}

data, target = load_linnerud(return_X_y=True)
data, target = data.tolist(), target.tolist()

trainInput, trainOutput, validInput, validOutput = splitData(data, target, 8/10)
normalise(trainInput, validInput)

ml = ToolGDMultiTarget(parameters, trainInput, trainOutput)
print(f"TOOL\n{ml.coefficients}")
predicted = ml.predict(validInput)
print(f"Error: {multiTargetMeanAbsoluteError(validOutput, predicted)}\n")


ml = MyBatchGDMultiTarget(parameters, trainInput, trainOutput)
print(f"MANUAL\n{ml.coefficients}")
predicted = ml.predict(validInput)
print(f"Error: {multiTargetMeanAbsoluteError(validOutput, predicted)}")
