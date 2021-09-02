filename = "solution.txt"


def writeSolution(result, graph):
    f = open(filename, "w")
    f.writelines(str(graph.number_of_nodes())+"\n")
    f.writelines(str(result[0])+"\n")
    f.writelines(str(result[1]))


def writeGraph(graph):
    f = open("graph.txt", "w")
    nrNodes = graph.number_of_nodes()
    f.writelines(str(nrNodes)+"\n")
    for node in graph.nodes:
        first = True
        for adjacent in graph[node]:
            if first:
                f.write(str(graph[node][adjacent]["weight"]))
                first = False
            else:
                f.write(','+str(graph[node][adjacent]["weight"]))
        f.write("\n")