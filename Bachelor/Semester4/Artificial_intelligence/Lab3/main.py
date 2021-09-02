from Chromosome import Chromosome
from DFS import DFS
from UI import UI

'''g = nw.read_gml("dolphins/dolphins.gml", None)
print(g.nodes)
print(list(g.adj[0]))

r.seed()
print([0 if r.random() < 0.5 else 1 for _ in range(0,5)])

for i in {1:2,2:3,4:5}.items():
    print(i)

parameters = {"popSize": 10, "noGen": 3} '''
#print( DFS( {1:2,2:1,3:1,4:5,5:7,6:5,7:5,8:12,9:8,10:8,11:10,12:8} ))
ui = UI()
ui.run()