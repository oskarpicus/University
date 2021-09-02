from utils.data import myShuffle
from ann.Neuron import Neuron
from ann.OutputNeuron import OutputNeuron
from ann.activationFunctions import identity, sigmoid
from numpy import exp


class ANN:
    def __init__(self, parameters: dict, trainInput: list, trainOutput: list, classes: list):
        self.__parameters = parameters
        self.__neurons = {}
        self.__createNetwork(trainInput, classes)
        self.__train(trainInput, trainOutput)

    def __createNetwork(self, trainInput: list, classes: list):
        nrLayers = 1 + len(self.__parameters["layers"]) + 1
        nrFeatures = len(trainInput[0])
        for i in range(nrLayers):
            self.__neurons.update({i: []})

        # input layer
        for i in range(nrFeatures + 1):
            currentNeuron = Neuron(self.__parameters, identity)
            self.__neurons[0].append(currentNeuron)
        # bias
        self.__neurons[0][-1].input = 1
        self.__neurons[0][-1].output = 1

        # hidden layers
        previousLayer = 0
        for nrNeurons in self.__parameters["layers"]:
            for i in range(nrNeurons):
                currentNeuron = Neuron(self.__parameters, identity)
                self.__neurons[previousLayer + 1].append(currentNeuron)
                for neuronPrevious in self.__neurons[previousLayer]:
                    neuronPrevious.addNext(currentNeuron)
                    currentNeuron.addPrevious(neuronPrevious)
            previousLayer += 1

        # output layer
        for c in classes:
            neuron = OutputNeuron(self.__parameters, sigmoid, c)
            self.__neurons[nrLayers - 1].append(neuron)
            for neuronPrevious in self.__neurons[nrLayers - 2]:
                neuronPrevious.addNext(neuron)
                neuron.addPrevious(neuronPrevious)

    def __train(self, trainInput: list, trainOutput: list):
        layers = sorted(self.__neurons.keys())
        nrLayers = 1 + len(self.__parameters["layers"]) + 1
        for _ in range(self.__parameters["nrEpochs"]):
            trainInput, trainOutput = myShuffle(trainInput, trainOutput)
            for inputSample, outputSample in zip(trainInput, trainOutput):
                for neuron, feature in zip(self.__neurons[0], inputSample):  # entry layer
                    neuron.input = feature
                    neuron.output = feature

                for layer in layers:
                    if layer != 0:
                        for neuron in self.__neurons[layer]:
                            neuron.activate()
                            if layer == nrLayers - 1:  # output layer
                                neuron.target = outputSample

                for layer in reversed(layers):
                    for neuron in self.__neurons[layer]:
                        neuron.backPropagation()

    def __softmax(self):
        layers = sorted(self.__neurons.keys())
        total = sum(exp(neuron.output) for neuron in self.__neurons[layers[-1]])
        for outputNeuron in self.__neurons[layers[-1]]:
            outputNeuron.output = exp(outputNeuron.output) / total

    def predict(self, sample: list):
        layers = sorted(self.__neurons.keys())
        for neuron, feature in zip(self.__neurons[0], sample):
            neuron.input = feature

        for layer in layers:
            if layer != 0:
                for neuron in self.__neurons[layer]:
                    neuron.activate()

        maximum, result = float('-inf'), None
        for outputNeuron in self.__neurons[layers[-1]]:
            if maximum < outputNeuron.output:
                maximum = outputNeuron.output
                result = outputNeuron.target
        return result