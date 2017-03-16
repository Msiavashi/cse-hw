from Graph import *


def construct_graph(file_path):
    with open(file_path, "r") as file:
        nodes, edges = file.readline().split()
        graph = Graph(nodes, edges)
        id = 0
        for line in file.readlines():
            args = line.split()
            graph[args[0]] = [(args[1], args[2], id)]
            graph[args[1]] = [(args[0], args[2], id)]
            id += 1
        return graph


graph = construct_graph("graph.txt")
all_ways = []
def backtrack(source, to_be_visited,current_node, weight = 0):
    connected_nodes = graph[str(current_node)]
    to_be_visited.remove(int(current_node))

    if not to_be_visited :
        for node, w, _ in connected_nodes:
            if node == source:
                all_ways.append(int(weight) + int(w))
                return
        return

    for node, w, _ in connected_nodes:
        if int(node) in to_be_visited:
            backtrack(source, to_be_visited[:], node, int(weight) + int(w))


backtrack('1', range(1, int(graph.num_of_nodes) + 1), '1')
print min(all_ways)