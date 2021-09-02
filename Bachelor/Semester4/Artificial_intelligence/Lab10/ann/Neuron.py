from random import random


class Neuron:
    def __init__(self, parameters: dict, activation):
        self.__parameters = parameters
        self.__activation = activation
        self.__input = 0
        self.__output = 0
        self.__error = 0
        self.__previous = []
        self.__next = []
        self.__weights = {}  # weights with the previous layer

    @property
    def input(self):
        return self.__input

    @input.setter
    def input(self, value: float):
        self.__input = value

    @property
    def output(self):
        return self.__output

    @output.setter
    def output(self, value: float):
        self.__output = value

    @property
    def error(self):
        return self.__error

    @error.setter
    def error(self, value: float):
        self.__error = value

    @property
    def weights(self):
        return self.__weights

    def addNext(self, neuron: 'Neuron'):
        self.__next.append(neuron)

    def addPrevious(self, neuron: 'Neuron'):
        self.__previous.append(neuron)
        self.__weights.update({neuron: random()})
        # self.__weights.update({neuron: 0.5})

    def activate(self):
        result = sum(self.__weights[neuron] * neuron.output for neuron in self.__previous)
        self.__output = self.__activation(result)

    def backPropagation(self):
        self.computeError()
        for neuronNext in self.__next:
            neuronNext.weights[self] += self.__parameters["learningRate"] * self.output * neuronNext.error
            print(neuronNext.weights[self])

    def computeError(self):
        self.__error = 0
        for neuronNext in self.__next:
            self.__error += neuronNext.error * neuronNext.weights[self]