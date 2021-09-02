import random as r
from DFS import DFS


r.seed()


class Chromosome:
    def __init__(self, graph, problParam=None, init=True):
        self.__problParam = problParam
        self.__graph = graph
        self.__locus = {}  # locus representation
        self.__fitness = 0
        if init:
            self.initialisation()
            self.computeFitness()

    def initialisation(self):
        # we randomly select a neighbour for each node
        for i in self.__graph.nodes:
            neighbours = list(self.__graph.adj[i])
            self.__locus.update({i: r.choice(neighbours)})

    def mutation(self):
        # randomly switch a neighbour
        allNodes = list(self.__graph.nodes)
        nodeI = r.choice(allNodes)
        nodeK = r.choice(allNodes)
        while nodeK == nodeI or not self.__graph.has_edge(nodeI, nodeK):
            nodeK = r.choice(allNodes)
        self.__locus[nodeI] = nodeK

    def crossover(self, other):
        # Standard uniform crossover
        offspring = Chromosome(self.__graph, self.__problParam, init=False)
        for node in self.__graph.nodes:
            if r.random() < 0.5:  # ==> 0
                offspring.__locus.update({node: self.__locus[node]})
            else:  # ==> 1
                offspring.__locus.update({node: other.__locus[node]})
        return offspring

    def computeFitness(self):
        # Modularity
        communities = DFS(self.__locus)
        Q = 0.0
        nodes = list(self.__graph.nodes)
        M = 2 * self.__graph.number_of_edges()
        for i in nodes:
            for j in nodes:
                for numberCommunity in communities.keys():
                    if i in communities[numberCommunity] and j in communities[numberCommunity]:  # they are in the same community
                        adjacency = 0
                        if self.__graph.has_edge(i, j):
                            adjacency = 1
                        Q += adjacency - (self.__graph.degree[i] * self.__graph.degree[j] / M)
                        break
        Q /= M
        self.__fitness = Q

    @property
    def fitness(self):
        return self.__fitness

    @property
    def numberCommunities(self):
        return len(DFS(self.__locus))

    @property
    def membership(self):
        return DFS(self.__locus)

    def __str__(self):
        return "Chromosome: representation " + str( self.__locus ) + " fitness " + str( self.__fitness )

    def __lt__(self, other):
        return self.fitness > other.fitness