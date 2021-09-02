def convertToMatrix(locus):
    graph = {}
    for i in locus.items():
        node1, node2 = i
        if node1 not in graph:
            graph.update({node1: [node2]})
        elif node2 not in graph[node1]:
            graph[node1].append(node2)
        if node2 not in graph:
            graph.update({node2: [node1]})
        elif node1 not in graph[node2]:
            graph[node2].append(node1)
    return graph


def DFS(locus):
    graph = convertToMatrix(locus)
    nrCommunities = 1
    visited = []
    notVisited = list(graph.keys())
    stack = [notVisited[0]]  # start node
    communities = {}
    firstTime = True
    while stack:
        current = stack.pop(len(stack)-1)
        if current not in visited:
            visited.append(current)
            notVisited.remove(current)
            if firstTime:
                communities.update({nrCommunities: [current]})
                firstTime = False
            else:
                communities[nrCommunities].append(current)

            for neighbour in graph[current]:
                stack.append(neighbour)

        if not stack and notVisited:
            firstTime = True
            nrCommunities += 1
            stack.append(notVisited[0])
    return communities