import random as r


class Ant:
    def __init__(self, graph, parameters, startNode):
        self.__graph = graph
        self.__parameters = parameters
        self.__visited = []
        self.__costPath = 0
        self.__startNode = startNode

    @property
    def visited(self):
        return self.__visited

    @property
    def costPath(self):
        return self.__costPath

    @property
    def startNode(self):
        return self.__startNode

    def set_startNode(self, value):
        self.__startNode = value

    def reset(self):
        self.__visited = []
        self.__costPath = 0

    def run(self):
        currentNode = self.__startNode
        self.__visited.append(currentNode)
        for _ in range(1, self.__graph.number_of_nodes()):
            probabilities = self.__computeProbabilities(currentNode)
            nextNode = self.__roulette(probabilities)
            self.__visited.append(nextNode)
            self.__costPath += self.__graph[currentNode][nextNode]["weight"]
            currentNode = nextNode
        self.__costPath += self.__graph[self.__visited[-1]][self.__visited[0]]["weight"]

    def __computeProbabilities(self, currentNode):
        alpha, beta = self.__parameters["alpha"], self.__parameters["beta"]
        probabilities, denominator = {}, 0

        for adjacentNode in self.__graph.neighbors(currentNode):
            if adjacentNode in self.visited:
                continue
            term = (self.__graph[currentNode][adjacentNode]["pheromone"] ** alpha) * \
                   ((1 / self.__graph[currentNode][adjacentNode]["weight"]) ** beta)
            denominator += term
            probabilities.update({adjacentNode: term})

        for adjacentNode in probabilities.keys():
            probabilities[adjacentNode] /= denominator

        return probabilities

    def __roulette(self, probabilities):
        number = r.random()
        probabilities = dict(sorted(probabilities.items(), key=lambda item: item[1]))
        for adjacentNode in probabilities.keys():
            if number < probabilities[adjacentNode]:
                return adjacentNode
        return list(probabilities.keys())[-1]