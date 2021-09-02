from ann.Neuron import Neuron


class OutputNeuron(Neuron):
    def __init__(self, parameters: dict, activation, classIndex: int):
        super().__init__(parameters, activation)
        self.__classIndex = classIndex
        self.__target = 0

    @property
    def target(self):
        return self.__target

    @target.setter
    def target(self, value: float):
        self.__target = value

    def computeError(self):
        realResult = 1 if self.__target == self.__classIndex else 0
        self.error = -self.output + realResult