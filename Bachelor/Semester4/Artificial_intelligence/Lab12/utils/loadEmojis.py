from skimage.io import imread
import json


def loadEmojis() -> tuple:
    file, directory = open("data/emoji.json"), "data/img-apple-64"
    data = json.load(file)
    inputData, outputData = [], []
    for element in data:
        subcategory = element["subcategory"]
        if subcategory in happySubcategories:
            image = imread(f"{directory}/{element['image']}")
            image = image.flatten()
            inputData.append(image)
            outputData.append(0)
        elif subcategory in sadSubcategories:
            image = imread(f"{directory}/{element ['image']}")
            image = image.flatten()
            inputData.append(image)
            outputData.append(1)
    return __convertToList(inputData), outputData


def __convertToList(inputData: list) -> list:
    result = [[] for _ in range(len(inputData))]
    counter = 0
    for line in inputData:
        for element in line:
            result[counter].append(element)
        counter += 1
    return result


# 13 + 3 + 9 + 5
happySubcategories = ["face-smiling", "face-hat", "face-affection", "person-resting"]
# 11 + 24
sadSubcategories = ["face-unwell", "face-concerned"]