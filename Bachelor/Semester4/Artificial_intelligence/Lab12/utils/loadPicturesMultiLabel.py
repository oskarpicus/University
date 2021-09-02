from skimage.io import imread
from os import listdir
from utils.loadEmojis import __convertToList


def loadPictures(classes: list, categories: dict) -> tuple:
    directory = "data/yalefaces"
    files = listdir(directory)
    inputData, outputData = [], []
    for url in files:
        currentOutput = [0 for _ in range(len(classes))]
        extension = url.split(".")[1]
        isRelevant = False
        for i, category in enumerate(categories):
            if extension in categories[category]:
                currentOutput[i] = 1
                isRelevant = True
        if isRelevant:
            inputData.append(imread(f"{directory}/{url}", as_gray=True).flatten())
            outputData.append(currentOutput)
    return __convertToList(inputData), outputData