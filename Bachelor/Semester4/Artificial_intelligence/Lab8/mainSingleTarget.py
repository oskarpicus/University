from data import normalise, splitData
from loadData import loadData
from MyBatchGDSingleTarget import MyBatchGDSingleTarget
from ToolBatchGDSingleTarget import ToolBatchGDSingleTarget
from evaluate import *


features = ['Economy..GDP.per.Capita.', 'Freedom']
target = 'Happiness.Score'

inputs, outputs = loadData('data/world-happiness-report-2017.csv', features, target)
trainInput, trainOutput, validInput, validOutput = splitData(inputs, outputs, 8/10)

normalise(trainInput, validInput)

parameters = {"nrEpochs": 5000, "learningRate": 0.01}

ml = MyBatchGDSingleTarget(parameters, trainInput, trainOutput)
print(f"MANUAL: {ml.coefficients}")
validComputedOutput = [model(ml.coefficients, i) for i in validInput]
print(f"Error: {meanAbsoluteError(validOutput, validComputedOutput)}")


ml = ToolBatchGDSingleTarget(parameters, trainInput, trainOutput)
print(f"\nTOOL: {ml.coefficients}")
validComputedOutput = [model(ml.coefficients, i) for i in validInput]
print(f"Error: {meanAbsoluteError(validOutput, validComputedOutput)}")
