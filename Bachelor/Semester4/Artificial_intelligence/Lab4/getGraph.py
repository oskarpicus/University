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
            '''if weight == 0:
                continue'''
            G.add_edge(node1, node2, weight=weight)
    f.close()
    return G


def compunere(p1, p2):
    new = {}
    for index in p1.keys():
        val1 = p1[index]
        val2 = p2[val1]
        new.update({index: val2})
    return new