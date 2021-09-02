from skimage.feature import haar_like_feature
from skimage.transform import integral_image
from skimage.io import imread
from os import listdir
import numpy as np
from utils.loadEmojis import __convertToList


def loadHaar(happy: list, sad: list, featureList: list) -> tuple:
    directory = "data/yalefaces"
    files = listdir(directory)
    inputs, outputs = [], []
    for url in files:
        extension = url.split(".")[1]
        isHappy = extension in happy
        isSad = extension in sad
        if isHappy or isSad:
            image = imread(f"{directory}/{url}", as_gray=True)
            haarFeatures = __extractHaarFeatures(image, featureList)
            if not np.all(np.isfinite(haarFeatures)):
                continue
            inputs.append(haarFeatures)
            outputs.append(0 if isHappy else 1)
    return __convertToList(inputs), outputs


def __extractHaarFeatures(image, featureList):
    integralImage = integral_image(image)
    width, height = integralImage.shape[0], integralImage.shape[1]
    return haar_like_feature(integralImage, 0, 0, width, height, featureList)