from heapq import heappush, heappop
from Node import Node


class TSP:
    def __init__(self, filename):
        self.__n = 0
        self.__graph = {}
        self.__start = None
        self.__end = None
        self.__matrixToAdjacencyList(filename)

    def __matrixToAdjacencyList(self, filename):
        """
        Method for obtaining the adjacency list from the adjacency matrix in a file
        :param filename: the path to the file to be read, must respect the conventions
        :return: members of self are updated accordingly to the data in filename
        """
        self.__graph = {}
        f = open(filename, "r")
        self.__n = int(f.readline()[:-1])
        i = 1
        while i <= self.__n:
            text = f.readline()
            for counter, digit in enumerate(text.split(",")):
                digit = int(digit)
                counter += 1
                if digit == 0:
                    continue
                if i not in self.__graph:
                    self.__graph.update({i: [(counter, digit)]})
                else:
                    self.__graph[i].append((counter, digit))
            i += 1
        self.__start = int(f.readline()[:-1])
        self.__end = int(f.readline()[:-1])

    def __getCostVertex(self, node1, node2):
        """
        Method for finding the cost of the vertex between 2 nodes
        :param node1: the index of the first node
        :param node2: the index of the second node
        :return: cost(node1, node2)
        """
        for neighbour in self.__graph[node1]:
            if neighbour[0] == node2:
                return neighbour[1]

    def shortestPath(self):
        """
        Method for finding the shortest path between a start and end node (Dijkstra algorithm)
        :return: list of the shortest path and its cost
        """
        distance = [float('inf')] * self.__n
        distance[self.__start-1] = 0
        parents = [None] * self.__n
        visited = []
        queue = []
        heappush(queue, Node(self.__start, 0))
        while len(queue) != 0:
            currentNode = heappop(queue)
            currentIndex = currentNode.index
            currentEval = currentNode.g
            if currentIndex == self.__end:
                break
            if currentIndex in visited:
                continue
            visited.append(currentIndex)
            for neighbour in self.__graph[currentIndex]:
                index, costVertex = neighbour
                if distance[index-1] > costVertex + currentEval:  # found a better path
                    distance[index-1] = costVertex + currentEval
                    parents[index-1] = currentIndex
                    heappush(queue, Node(index, costVertex + currentEval))
        return [self.__getPath(parents)]+[distance[self.__end-1]]

    def cycle(self):
        """
        Method for finding the shortest path that traverses each node only once
        :return: list of the cycle and its cost
        """
        start = 1
        queue = []
        parentsResult = []
        distanceResult = float('inf')
        bestEvaluation = float('inf')
        heappush(queue, Node(start, 0, 1, [start]))
        while len(queue) != 0:
            currentNode = heappop(queue)
            currentIndex = currentNode.index
            currentEval = currentNode.g
            currentVisited = currentNode.visited
            currentCounter = currentNode.counter
            if currentCounter == self.__n and bestEvaluation != currentEval and bestEvaluation != float('inf'):
                break
            elif currentCounter == self.__n:
                vertex = self.__getCostVertex(currentVisited[-1], start)
                if distanceResult > currentEval+vertex:
                    distanceResult = currentEval+vertex
                    parentsResult = currentVisited
            for neighbour in self.__graph[currentIndex]:
                index, costVertex = neighbour
                if index in currentVisited:
                    continue
                heappush(queue, Node(index, currentEval+costVertex, currentCounter+1, currentVisited+[index]))
        return [parentsResult]+[distanceResult]

    def __getPath(self, parents):
        """
        Method for obtaining the path from start to end nodes, based on their parent nodes
        :param parents: list of parents of each node
        :return:
        """
        path = []
        current = self.__end
        while parents[current - 1] is not None:
            path.insert(0, current)
            current = parents[current - 1]
        path.insert(0, self.__start)
        return path
