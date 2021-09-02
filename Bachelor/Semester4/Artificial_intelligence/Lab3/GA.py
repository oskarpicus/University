import random as r
from Chromosome import Chromosome
import networkx as nw


class GA:
    def __init__(self, filename, param=None, problParam=None):
        self.__param = param
        self.__problParam = problParam
        self.__population = []
        self.__graph = nw.read_gml(filename, label=None)
        #self.elitism()
        self.generational()

    @property
    def population(self):
        return self.__population

    def initialisation(self):
        for _ in range(0, self.__param["popSize"]):
            c = Chromosome(self.__graph, self.__problParam)
            self.__population.append(c)

    def bestChromosome(self):
        best = self.__population[0]
        for c in self.__population:
            if c.fitness > best.fitness:
                best = c
        return best

    def worstChromosome(self):
        worst = self.population[0]
        for c in self.__population:
            if c.fitness > worst.fitness:
                worst = c
        return worst

    def selection(self):
        # turnir
        chromosomes = []
        for _ in range(0, self.__param["k"]):
            c = r.choice(self.__population)
            chromosomes.append(c)
        chromosomes.sort()
        return chromosomes[:self.__param["nrForSelection"]]

    def elitism(self):
        self.initialisation()
        for i in range(self.__param["noGen"]):
            best = self.bestChromosome()
            newPopulation = [best]
            for _ in range(self.__param["popSize"]-1):
                p1, p2 = self.selection()
                offspring = p1.crossover(p2)
                offspring.mutation()
                offspring.computeFitness()
                newPopulation.append(offspring)
            self.__population = newPopulation
            print(f"Generation {i}")
            #self.evaluateGeneration()
            print(f"Fitness: {best.fitness}, Nr. communities: {best.numberCommunities}\n")
        best = self.bestChromosome()
        print(f"Result: {best.membership} \nNr. communities {best.numberCommunities}")

    def generational(self):
        self.initialisation()
        for i in range(self.__param["noGen"]):
            newPopulation = []
            for _ in range(self.__param["popSize"]):
                p1, p2 = self.selection()
                offspring = p1.crossover(p2)
                offspring.mutation()
                offspring.computeFitness()
                newPopulation.append(offspring)
            self.__population = newPopulation
            print(f"Generation {i}")
            self.evaluateGeneration()
        best = self.bestChromosome()
        print(f"Result: {best.membership} \nNr. communities {best.numberCommunities}")

    def evaluateGeneration(self):
        c = self.bestChromosome()
        print(f"Fitness: {c.fitness}, Nr. communities: {c.numberCommunities}\n")
