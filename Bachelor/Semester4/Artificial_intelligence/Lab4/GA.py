from Chromosome import Chromosome
import random as rd


class GA:
    def __init__(self, graph, param=None):
        self.__param = param
        self.__graph = graph
        self.__population = []
        self.initialisation()

    def initialisation(self):
        for _ in range(self.__param["popSize"]):
            self.__population.append(Chromosome(self.__graph, self.__param))

    def bestChromosome(self):
        best = self.__population[0]
        for chromosome in self.__population:
            if chromosome.fitness < best.fitness:
                best = chromosome
        return best

    def selection(self):
        # turnir
        selected = []
        for _ in range(self.__param["k"]):
            selected.append(rd.choice(self.__population))
        selected.sort()
        return selected[0:self.__param["nrSelected"]]

    def elitism(self):
        for i in range(self.__param["noGen"]):
            newPopulation = [self.bestChromosome()]
            for _ in range(self.__param["popSize"]-1):
                p1, p2 = self.selection()
                offspring = p1.crossover(p2)
                offspring.mutation()
                offspring.computeFitness()
                newPopulation.append(offspring)
            self.__population = newPopulation
            print(f"Generation {i}")
            self.evaluateGeneration()
        print("Result: ")
        self.evaluateGeneration()
        return self.bestChromosome()

    def evaluateGeneration(self):
        best = self.bestChromosome()
        print(f"Cycle {list(best.permutation.values())} fitness {best.fitness}\n")