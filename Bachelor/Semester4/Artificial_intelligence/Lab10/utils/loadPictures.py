from skimage.io import imread
from os import listdir
from re import search


def loadPictures() -> tuple:
    directory = "data/images"
    files = listdir(directory)
    inputData, outputData = [], []
    for url in files:
        image = imread(f"{directory}/{url}", as_gray=True)
        image = image.flatten()
        inputData.append(image)
        outputData.append(1 if search("sepia", url) else 0)
    return convertToList(inputData), outputData


def convertToList(inputData: list) -> list:
    result = [[] for _ in range(len(inputData))]
    counter = 0
    for line in inputData:
        for element in line:
            result[counter].append(element)
        counter += 1
    return result