import random as rd


rd.seed()


class Chromosome:
    def __init__(self, graph, param=None, init=True):
        self.__graph = graph
        self.__param = param
        self.__permutation = {}
        self.__fitness = 0
        if init:
            self.initialisation()
            self.computeFitness()

    def initialisation(self):
        # identity permutation
        for node in self.__graph.nodes():
            self.__permutation.update({node: node})

    def crossover(self, other):
        # permutation multiplying
        offspring = Chromosome(self.__graph, self.__param, init=False)
        for index in self.__permutation.keys():
            val1 = self.__permutation[index]
            val2 = other.__permutation[val1]
            offspring.__permutation.update({index: val2})
        return offspring

    def mutation(self):
        # randomly switch 2 values
        nodes = list(self.__permutation.keys())
        pos1 = rd.choice(nodes)
        pos2 = rd.choice(nodes)
        self.__permutation[pos1], self.__permutation[pos2] = self.__permutation[pos2], self.__permutation[pos1]

    def computeFitness(self):
        total = 0
        values = list(self.__permutation.values())
        previous = values[0]
        for node in values:
            total += self.__graph.get_edge_data(node, previous)["weight"]
            previous = node
        total += self.__graph.get_edge_data(values[-1], values[0])["weight"]
        self.__fitness = total
        return total

    @property
    def fitness(self):
        return self.__fitness

    @property
    def permutation(self):
        return self.__permutation

    def __str__(self):
        return "Perm: "+str(list(self.__permutation.values()))+" Fitness: "+str(self.fitness)

    def __lt__(self, other):
        return self.fitness < other.fitness

