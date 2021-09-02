def multiClassEvaluation(realLabels, computedLabels, labelNames):
    accMean, precisionMean, recallMean, nrLabels = 0, 0, 0, len(labelNames)
    for label in labelNames:
        TP = sum([1 if real == label and computed == label else 0 for real, computed in zip(realLabels, computedLabels)])
        TN = sum([1 if real != label and computed != label else 0 for real, computed in zip(realLabels, computedLabels)])
        FP = sum([1 if real != label and computed == label else 0 for real, computed in zip(realLabels, computedLabels)])
        FN = sum([1 if real == label and computed != label else 0 for real, computed in zip(realLabels, computedLabels)])
        acc = (TP + TN) / (TP + TN + FP + FN)
        precision = TP / (TP + FP)
        recall = TP / (TP + FN)
        accMean += acc
        precisionMean += precision
        recallMean += recall
    return accMean / nrLabels, precisionMean / nrLabels, recallMean / nrLabels


real = ['pants', 'pants', 'hat', 'shirt', 'hat', 'shirt', 'pants']
computed = ['pants', 'hat', 'shirt', 'shirt', 'hat', 'pants', 'pants']
labels = ['pants', 'hat', 'shirt']
print(multiClassEvaluation(real, computed, labels))
print(multiClassEvaluation(real, real, labels))
computed = ['hat', 'hat', 'shirt', 'hat', 'pants', 'pants', 'hat']
print(multiClassEvaluation(real, computed, labels))