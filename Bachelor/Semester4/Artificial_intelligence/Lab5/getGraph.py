import networkx as nx


def getGraph(filename):
    G = nx.Graph()
    f = open(filename, "r")
    nrNodes = int(f.readline())
    for node1 in range(1, nrNodes+1):
        text = f.readline()
        for node2, weight in enumerate(text.split(",")):
            weight = int(weight)
            node2 += 1
            G.add_edge(node1, node2, weight=weight)
    f.close()
    return G