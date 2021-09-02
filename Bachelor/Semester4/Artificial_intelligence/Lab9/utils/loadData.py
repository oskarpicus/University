import csv


def loadData(filename: str, inputVariableNames: list, outputVariableName: str):
    data, dataNames = [], []
    with open(filename) as csv_file:
        csvReader = csv.reader(csv_file, delimiter=',')
        lineCount = 0
        for row in csvReader:
            if lineCount == 0:
                dataNames = row
            else:
                data.append(row)
            lineCount += 1
        inputs = [[] for _ in range(1, lineCount)]
        for feature in inputVariableNames:
            selectedVariable = dataNames.index(feature)
            for i in range(len(data)):
                inputs[i].append(float(data[i][selectedVariable]))
        selectedOutputVariable = dataNames.index(outputVariableName)
        outputs = [data[i][selectedOutputVariable] for i in range(len(data))]
        return inputs, outputs