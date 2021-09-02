from OrdinaryLeastSquares import *
from loadData import *
from splitData import *
from evaluate import *
from MyLeastSquares import *


def test(Coefficients, ValidOutput, ValidComputed):
    print(f"Model {Coefficients[0]} + {Coefficients[1]} * x1 + {Coefficients[2]} * x2")
    print(f"Error1 (with tool): {meanAbsoluteErrorTool(ValidOutput, ValidComputed)}")
    print(f"Error2 (manual)   : {meanAbsoluteError(ValidOutput, ValidComputed)}")


features = ['Economy..GDP.per.Capita.', 'Freedom']
target = 'Happiness.Score'
inputs, outputs = loadData('data/world-happiness-report-2017.csv', features, target)
trainInput, trainOutput, validInput, validOutput = splitData(inputs, outputs, 8/10)

# with tool
ml = OrdinaryLeastSquares(trainInput, trainOutput)
coefficients = [ml.coefficients[0]] + ml.coefficients[1].tolist()
validComputedOutput = [model(coefficients, f) for f in validInput]
print("TOOL:")
test(coefficients, validOutput, validComputedOutput)

# manual
ml = MyLeastSquares(trainInput, trainOutput)
coefficients = ml.coefficients
validComputedOutput = [model(coefficients, f) for f in validInput]
print("\nMANUAL:")
test(coefficients, validOutput, validComputedOutput)
