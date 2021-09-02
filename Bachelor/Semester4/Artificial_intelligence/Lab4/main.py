import Writer
from getGraph import *
from GA import *


filename = "data/easy_01_tsp.txt"
G = getGraph(filename)
param = {"noGen": 50, "popSize": 50, "k": 7, "nrSelected": 2}
ga = GA(G, param)
chromosome = ga.elitism()
Writer.writeSolution(filename, chromosome)