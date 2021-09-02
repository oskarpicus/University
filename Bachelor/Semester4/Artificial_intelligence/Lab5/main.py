from ACO import ACO
from getGraph import *
from Writer import *


filename = "data/test/hard_01_tsp.txt"
G = getGraph(filename)
parameters = {"initialPheromone": 1, "alpha": 1, "beta": 1, "rho": 0.5, "nrIterations": 100,
              "m": 10, "q": 1, "changeGraph": 0.03, "deleteNode": 0.7, "insertNode": 0.3,
              "newAnts": 0.2, "minimumCost": 1, "maximumCost": 20}
aco = ACO(G, parameters, dynamic=True)
result = aco.run()
print(f"Path {result[0]}, cost {result[1]}")
writeSolution(result, G)
writeGraph(G)