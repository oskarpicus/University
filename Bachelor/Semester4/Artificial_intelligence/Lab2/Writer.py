from TSP import TSP


class Writer:
    def __init__(self, filename):
        self.__filename = filename
        self.__solutionFilename = self.__getSolutionFilename()
        self.__tsp = TSP(self.filename)

    def compute(self):
        shortestPath = self.__tsp.shortestPath()
        cycle = self.__tsp.cycle()
        print("Cycle " + str(cycle))
        print("Shortest path "+str(shortestPath))
        n = len(cycle[0])
        f = open(self.__getSolutionFilename(), "w")
        f.write(str(n)+"\n")
        f.write(self.__convertListToString(cycle[0])+"\n")
        f.write(str(cycle[1])+"\n")
        f.write(str(len(shortestPath[0]))+"\n")
        f.write(self.__convertListToString(shortestPath[0]) + "\n")
        f.write(str(shortestPath[1]))

    def __convertListToString(self, parameter):
        """
        Method to create a CSV string of a list
        :param parameter: list
        :return: CSV string representation of parameter
        """
        result = ""
        for element in parameter:
            result += str(element)+","
        return result[:-1]

    @property
    def filename(self):
        return self.__filename

    def solutionFilename(self):
        return self.__solutionFilename

    def __getSolutionFilename(self):
        """
        Method for obtaining the path to the solution file
        :return: string containing the path to the solution file
        """
        words = self.__filename.split(".")
        return ".".join(words[:-1])+"_solution"+"."+words[-1]