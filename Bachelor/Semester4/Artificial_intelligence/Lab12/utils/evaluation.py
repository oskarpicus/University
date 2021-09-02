def multiClassEvaluation(realLabels: list, computedLabels: list, labelNames: list):
    accMean, precisionMean, recallMean, nrLabels = 0, 0, 0, len(labelNames)
    for label in labelNames:
        TP = sum([1 if real == label and computed == label else 0 for real, computed in zip(realLabels, computedLabels)])
        TN = sum([1 if real != label and computed != label else 0 for real, computed in zip(realLabels, computedLabels)])
        FP = sum([1 if real != label and computed == label else 0 for real, computed in zip(realLabels, computedLabels)])
        FN = sum([1 if real == label and computed != label else 0 for real, computed in zip(realLabels, computedLabels)])
        try:
            acc = (TP + TN) / (TP + TN + FP + FN)
        except ZeroDivisionError:
            acc = 0
        try:
            precision = TP / (TP + FP)
        except ZeroDivisionError:
            precision = 0
        try:
            recall = TP / (TP + FN)
        except ZeroDivisionError:
            recall = 0
        accMean += acc
        precisionMean += precision
        recallMean += recall
    return accMean / nrLabels, precisionMean / nrLabels, recallMean / nrLabels


def multiLabelEvaluation(real: list, computed: list, classes: list):
    for i, aClass in enumerate(classes):
        validInput = [result[i] for result in real]
        validComputed = [result[i] for result in computed]
        accuracy, precision, recall = multiClassEvaluation(validInput, validComputed, [0, 1])
        print(f"{aClass}\naccuracy {accuracy}\nprecision {precision}\nrecall {recall}\n")