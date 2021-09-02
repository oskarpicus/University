def stratifiedKFold(inputs: list, outputs: list, k: int) -> list:
    blocksInput = [[] for _ in range(k)]
    blocksOutput = [[] for _ in range(k)]
    nrSamples = len(inputs)
    counter = 0
    while counter < nrSamples:
        for i in range(k):
            if counter > nrSamples:
                break
            blocksInput[i].append(inputs[counter])
            blocksOutput[i].append(outputs[counter])
            counter += 1
    return [blocksInput, blocksOutput]