class Node:
    def __init__(self, index,g, counter=None, visited=None):
        self.__index = index
        self.__g = g
        self.__counter = counter
        self.__visited = visited

    @property
    def index(self):
        return self.__index

    @property
    def g(self):
        return self.__g

    @property
    def counter(self):
        return self.__counter

    @property
    def visited(self):
        return self.__visited

    def __lt__(self, other):
        return self.__g < other.__g

    def __str__(self):
        return str(self.index)+" "+str(self.g)

    def extract(self):
        return [self.index, self.g, self.counter, self.visited]