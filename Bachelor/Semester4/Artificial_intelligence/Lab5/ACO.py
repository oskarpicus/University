import threading
from Ant import Ant
import random as r
from itertools import tee


class ACO:
    def __init__(self, graph, parameters, dynamic=False):
        self.__graph = graph
        self.__parameters = parameters
        self.__dynamic = dynamic
        self.__nrNodes = self.__graph.number_of_nodes()

    def run(self):
        self.__initialisation()
        nodes = list(self.__graph.nodes())
        ants = [Ant(self.__graph, self.__parameters, startNode=r.choice(nodes)) for _ in
                range(self.__parameters["m"])]
        threads = []
        for _ in range(self.__parameters["nrIterations"]):
            for ant in ants:
                thread = threading.Thread(target=ant.run)
                threads.append(thread)
                thread.start()
            for thread in threads:
                thread.join()
            self.__updatePheromone(ants)
            for ant in ants:
                ant.reset()
            if self.__dynamic:
                self.__changeGraph(ants)
            threads = []
        return self.__getBestPath()

    def __initialisation(self):
        for edge in self.__graph.edges:
            i, j = edge
            if i != j:
                self.__graph[i][j]["pheromone"] = self.__parameters["initialPheromone"]
            else:
                self.__graph[i][j]["pheromone"] = 0
            self.__graph[i][j]["tau"] = 0

    def __updatePheromone(self, ants):
        # update pheromone
        Q = self.__parameters["q"]
        for ant in ants:
            a, b = tee(ant.visited)
            next(b, None)
            for node1, node2 in zip(a, b):
                self.__graph[node1][node2]["tau"] += Q / ant.costPath
            self.__graph[ant.visited[-1]][ant.visited[0]]["tau"] += Q / ant.costPath  # last edge
        # evaporate pheromone
        rho = self.__parameters["rho"]
        for edge in self.__graph.edges:
            i, j = edge
            self.__graph[i][j]["pheromone"] = (1-rho)*self.__graph[i][j]["pheromone"] + self.__graph[i][j]["tau"]
            self.__graph[i][j]["tau"] = 0

    def __getBestPath(self):
        # best pheromone first
        currentNode = list(self.__graph.nodes)[0]
        path, cost = [currentNode], 0
        for _ in range(1, self.__graph.number_of_nodes()):
            nextNode, maximPheromone = None, float('-inf')
            for adjacentNode in self.__graph.neighbors(currentNode):
                if adjacentNode not in path and maximPheromone < self.__graph[currentNode][adjacentNode]["pheromone"]:
                    nextNode = adjacentNode
                    maximPheromone = self.__graph[currentNode][adjacentNode]["pheromone"]

            path.append(nextNode)
            cost += self.__graph[currentNode][nextNode]["weight"]
            currentNode = nextNode

        cost += self.__graph[path[-1]][path[0]]["weight"]
        return [path, cost]

    def __changeGraph(self, ants):
        number = r.random()
        if self.__graph.number_of_nodes() > 3 and number < self.__parameters["changeGraph"]:
            nodes = list(self.__graph.nodes)
            if r.random() < self.__parameters["deleteNode"]:  # delete node
                nodeToDelete = r.choice(nodes)
                self.__graph.remove_node(nodeToDelete)
                print(f"Deleted node {nodeToDelete}")
                nodes.remove(nodeToDelete)

                for ant in ants:
                    if ant.startNode == nodeToDelete:
                        ant.set_startNode(r.choice(nodes))
            else:  # insert node
                edges, newNode = [], self.__nrNodes + 1
                self.__nrNodes += 1
                self.__graph.add_node(newNode)
                for node in self.__graph.nodes:
                    if node == newNode:
                        continue
                    weight = r.randint(self.__parameters["minimumCost"], self.__parameters["maximumCost"])
                    self.__graph.add_edge(node, newNode, weight=weight, pheromone=self.__parameters["initialPheromone"],
                                          tau=0)
                    edges.append([node, newNode, weight])
                print(f"Inserted node {newNode} with {edges}")

                nrAnts = int(self.__parameters["m"] * self.__parameters["newAnts"])
                for _ in range(nrAnts):
                    ants.remove(r.choice(ants))
                    ants.append(Ant(self.__graph, self.__parameters, startNode=newNode))