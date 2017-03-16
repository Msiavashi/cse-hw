# sorry for bad object oriented ! i just got no idea how oop works in python xD :D

class Graph:
    def __init__(self, num_of_nodes, num_of_edges):
        self.num_of_nodes = num_of_nodes
        self.num_of_edges = num_of_edges
        self.graph = dict()

    def __setitem__(self, key, value):
        self.graph[key] = self.graph.get(key, []) + value

    def __getitem__(self, item):
        return self.graph[item]








