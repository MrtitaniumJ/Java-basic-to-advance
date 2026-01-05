# Graph Representation in Java

## Table of Contents
1. [Introduction](#introduction)
2. [What is a Graph](#what-is-a-graph)
3. [Graph Representations](#graph-representations)
4. [Java Implementation](#java-implementation)
5. [Operations](#operations)
6. [Complexity Analysis](#complexity-analysis)
7. [Use Cases](#use-cases)
8. [When to Use](#when-to-use)
9. [Best Practices](#best-practices)

## Introduction

Graphs are fundamental non-linear data structures used to represent relationships between entities. A graph consists of a set of vertices (nodes) connected by edges. Graphs are essential in solving real-world problems ranging from social networks to navigation systems. Understanding different graph representations is crucial for selecting the appropriate approach for various algorithms and applications.

## What is a Graph

A graph G = (V, E) consists of:
- **V (Vertices/Nodes)**: A set of distinct data points
- **E (Edges)**: A set of pairs of vertices that represent connections between nodes

### Graph Types
- **Undirected Graph**: Edges have no direction (symmetric relationships)
- **Directed Graph (Digraph)**: Edges have direction (asymmetric relationships)
- **Weighted Graph**: Edges have associated weights/costs
- **Unweighted Graph**: All edges have equal weight
- **Cyclic Graph**: Contains cycles
- **Acyclic Graph**: No cycles (DAG - Directed Acyclic Graph)

## Graph Representations

### 1. Adjacency Matrix

An adjacency matrix is a 2D array where:
- Element at [i][j] represents the edge between vertex i and vertex j
- For unweighted graphs: 0 (no edge) or 1 (edge exists)
- For weighted graphs: weight value or infinity (no edge)

**Advantages:**
- O(1) edge lookup time
- Simple to implement
- Suitable for dense graphs

**Disadvantages:**
- O(V²) space complexity regardless of edge count
- Inefficient for sparse graphs
- Expensive edge addition/removal

**Diagram:**
```
Graph:        Adjacency Matrix:
  0           0 1 2 3
 /|\          0 0 1 1 0
1 2 3         1 1 0 1 1
             2 1 1 0 1
             3 0 1 1 0
```

### 2. Adjacency List

An adjacency list stores a list of adjacent vertices for each vertex:
- Uses HashMap, ArrayList, or LinkedList
- Each vertex maps to a list of its neighbors

**Advantages:**
- O(V + E) space complexity
- Efficient for sparse graphs
- Better cache locality
- Easier graph iteration

**Disadvantages:**
- O(degree) edge lookup time
- Slightly more complex to implement
- Memory overhead from list pointers

**Diagram:**
```
Graph:        Adjacency List:
  0           0 → [1, 2]
 /|\          1 → [0, 2, 3]
1 2 3         2 → [0, 1, 3]
             3 → [1, 2]
```

### 3. Edge List

A simple list of all edges in the graph:
```
Graph:        Edge List:
  0           (0,1) → weight w1
 /|\          (0,2) → weight w2
1 2 3         (1,2) → weight w3
             (1,3) → weight w4
             (2,3) → weight w5
```

**Use Cases:** Edge-centric algorithms, minimum spanning trees

## Java Implementation

### Complete Graph Implementation with Multiple Representations

```java
import java.util.*;

/**
 * Comprehensive Graph implementation with multiple representations
 * Supports both directed and undirected graphs
 */
public class Graph {
    private int numVertices;
    private List<List<Integer>> adjacencyList;
    private int[][] adjacencyMatrix;
    private List<Edge> edgeList;
    private boolean isDirected;
    private boolean isWeighted;
    private Map<String, Integer> edgeWeights;
    
    /**
     * Constructor for unweighted graphs
     */
    public Graph(int numVertices, boolean isDirected) {
        this.numVertices = numVertices;
        this.isDirected = isDirected;
        this.isWeighted = false;
        this.adjacencyList = new ArrayList<>();
        this.adjacencyMatrix = new int[numVertices][numVertices];
        this.edgeList = new ArrayList<>();
        this.edgeWeights = new HashMap<>();
        
        // Initialize adjacency list
        for (int i = 0; i < numVertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        
        // Initialize adjacency matrix with zeros
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                adjacencyMatrix[i][j] = 0;
            }
        }
    }
    
    /**
     * Constructor for weighted graphs
     */
    public Graph(int numVertices, boolean isDirected, boolean isWeighted) {
        this(numVertices, isDirected);
        this.isWeighted = isWeighted;
        // Initialize matrix with infinity for weighted graphs
        if (isWeighted) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (i != j) {
                        adjacencyMatrix[i][j] = Integer.MAX_VALUE;
                    }
                }
            }
        }
    }
    
    /**
     * Add an edge to the graph
     */
    public void addEdge(int u, int v) {
        addEdge(u, v, 1);
    }
    
    /**
     * Add a weighted edge to the graph
     */
    public void addEdge(int u, int v, int weight) {
        if (u < 0 || u >= numVertices || v < 0 || v >= numVertices) {
            throw new IllegalArgumentException("Invalid vertex index");
        }
        
        // Add to adjacency list
        adjacencyList.get(u).add(v);
        
        // Add to adjacency matrix
        adjacencyMatrix[u][v] = weight;
        
        // Add to edge list
        edgeList.add(new Edge(u, v, weight));
        
        // Store weight
        edgeWeights.put(u + "-" + v, weight);
        
        // If undirected, add reverse edge
        if (!isDirected) {
            adjacencyList.get(v).add(u);
            adjacencyMatrix[v][u] = weight;
            edgeWeights.put(v + "-" + u, weight);
        }
    }
    
    /**
     * Remove an edge from the graph
     */
    public void removeEdge(int u, int v) {
        if (u < 0 || u >= numVertices || v < 0 || v >= numVertices) {
            throw new IllegalArgumentException("Invalid vertex index");
        }
        
        // Remove from adjacency list
        adjacencyList.get(u).remove(Integer.valueOf(v));
        
        // Remove from adjacency matrix
        adjacencyMatrix[u][v] = isWeighted ? Integer.MAX_VALUE : 0;
        
        // Remove from edge list
        edgeList.removeIf(e -> e.u == u && e.v == v);
        
        // Remove weight
        edgeWeights.remove(u + "-" + v);
        
        // If undirected, remove reverse edge
        if (!isDirected) {
            adjacencyList.get(v).remove(Integer.valueOf(u));
            adjacencyMatrix[v][u] = isWeighted ? Integer.MAX_VALUE : 0;
            edgeWeights.remove(v + "-" + u);
        }
    }
    
    /**
     * Check if edge exists between u and v
     */
    public boolean hasEdge(int u, int v) {
        if (u < 0 || u >= numVertices || v < 0 || v >= numVertices) {
            return false;
        }
        return adjacencyMatrix[u][v] != (isWeighted ? Integer.MAX_VALUE : 0);
    }
    
    /**
     * Get all adjacent vertices of a given vertex
     */
    public List<Integer> getAdjacents(int u) {
        if (u < 0 || u >= numVertices) {
            throw new IllegalArgumentException("Invalid vertex index");
        }
        return new ArrayList<>(adjacencyList.get(u));
    }
    
    /**
     * Get weight of edge between u and v
     */
    public int getEdgeWeight(int u, int v) {
        if (!hasEdge(u, v)) {
            return Integer.MAX_VALUE;
        }
        return adjacencyMatrix[u][v];
    }
    
    /**
     * Print adjacency list representation
     */
    public void printAdjacencyList() {
        System.out.println("\n=== Adjacency List Representation ===");
        for (int i = 0; i < numVertices; i++) {
            System.out.print("Vertex " + i + " → ");
            for (int neighbor : adjacencyList.get(i)) {
                if (isWeighted) {
                    System.out.print("[" + neighbor + ":" + adjacencyMatrix[i][neighbor] + "] ");
                } else {
                    System.out.print(neighbor + " ");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Print adjacency matrix representation
     */
    public void printAdjacencyMatrix() {
        System.out.println("\n=== Adjacency Matrix Representation ===");
        System.out.print("    ");
        for (int i = 0; i < numVertices; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        for (int i = 0; i < numVertices; i++) {
            System.out.print(i + " → ");
            for (int j = 0; j < numVertices; j++) {
                int val = adjacencyMatrix[i][j];
                if (val == Integer.MAX_VALUE) {
                    System.out.print("∞ ");
                } else {
                    System.out.print(val + " ");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Print edge list representation
     */
    public void printEdgeList() {
        System.out.println("\n=== Edge List Representation ===");
        for (Edge edge : edgeList) {
            if (isWeighted) {
                System.out.println("(" + edge.u + ", " + edge.v + ") → weight: " + edge.weight);
            } else {
                System.out.println("(" + edge.u + ", " + edge.v + ")");
            }
        }
    }
    
    /**
     * Get number of vertices
     */
    public int getNumVertices() {
        return numVertices;
    }
    
    /**
     * Get number of edges
     */
    public int getNumEdges() {
        return edgeList.size();
    }
    
    /**
     * Get in-degree of a vertex
     */
    public int getInDegree(int v) {
        if (!isDirected) {
            return adjacencyList.get(v).size();
        }
        int inDegree = 0;
        for (int i = 0; i < numVertices; i++) {
            if (hasEdge(i, v)) {
                inDegree++;
            }
        }
        return inDegree;
    }
    
    /**
     * Get out-degree of a vertex
     */
    public int getOutDegree(int v) {
        return adjacencyList.get(v).size();
    }
    
    /**
     * Check if graph is connected (for undirected graphs)
     */
    public boolean isConnected() {
        if (isDirected) {
            return false;
        }
        boolean[] visited = new boolean[numVertices];
        dfsHelper(0, visited);
        
        for (boolean v : visited) {
            if (!v) return false;
        }
        return true;
    }
    
    /**
     * Helper method for DFS
     */
    private void dfsHelper(int v, boolean[] visited) {
        visited[v] = true;
        for (int neighbor : adjacencyList.get(v)) {
            if (!visited[neighbor]) {
                dfsHelper(neighbor, visited);
            }
        }
    }
    
    /**
     * Inner class to represent an edge
     */
    private static class Edge {
        int u;
        int v;
        int weight;
        
        Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
    }
    
    /**
     * Demonstration and testing
     */
    public static void main(String[] args) {
        // Create undirected unweighted graph
        System.out.println("=== Undirected Unweighted Graph ===");
        Graph graph1 = new Graph(4, false);
        graph1.addEdge(0, 1);
        graph1.addEdge(0, 2);
        graph1.addEdge(1, 2);
        graph1.addEdge(1, 3);
        graph1.addEdge(2, 3);
        
        graph1.printAdjacencyList();
        graph1.printAdjacencyMatrix();
        graph1.printEdgeList();
        
        System.out.println("\nGraph Properties:");
        System.out.println("Vertices: " + graph1.getNumVertices());
        System.out.println("Edges: " + graph1.getNumEdges());
        System.out.println("Is Connected: " + graph1.isConnected());
        System.out.println("Degree of vertex 1: " + graph1.getOutDegree(1));
        System.out.println("Adjacents of vertex 0: " + graph1.getAdjacents(0));
        
        // Create directed weighted graph
        System.out.println("\n\n=== Directed Weighted Graph ===");
        Graph graph2 = new Graph(5, true, true);
        graph2.addEdge(0, 1, 4);
        graph2.addEdge(0, 2, 2);
        graph2.addEdge(1, 2, 1);
        graph2.addEdge(1, 3, 5);
        graph2.addEdge(2, 3, 8);
        graph2.addEdge(2, 4, 10);
        graph2.addEdge(3, 4, 2);
        
        graph2.printAdjacencyList();
        graph2.printAdjacencyMatrix();
        graph2.printEdgeList();
        
        System.out.println("\nGraph Properties:");
        System.out.println("Vertices: " + graph2.getNumVertices());
        System.out.println("Edges: " + graph2.getNumEdges());
        System.out.println("In-degree of vertex 2: " + graph2.getInDegree(2));
        System.out.println("Out-degree of vertex 1: " + graph2.getOutDegree(1));
        System.out.println("Weight of edge (0,1): " + graph2.getEdgeWeight(0, 1));
        
        // Test edge removal
        System.out.println("\n=== After Removing Edge (1, 3) ===");
        graph2.removeEdge(1, 3);
        graph2.printAdjacencyList();
    }
}
```

## Operations

| Operation | Time Complexity (Adj. List) | Time Complexity (Adj. Matrix) | Space Complexity |
|-----------|-------|-------|-------|
| Add Edge | O(1) | O(1) | O(1) |
| Remove Edge | O(degree) | O(1) | O(1) |
| Check Edge | O(degree) | O(1) | - |
| Get Adjacents | O(degree) | O(V) | O(degree) |
| Traverse (BFS/DFS) | O(V + E) | O(V²) | O(V) |

## Complexity Analysis

### Adjacency List
- **Time Complexity:**
  - Add edge: O(1)
  - Remove edge: O(degree)
  - Edge lookup: O(degree)
  - Iteration: O(V + E)
- **Space Complexity:** O(V + E)
- **Best for:** Sparse graphs, most algorithms

### Adjacency Matrix
- **Time Complexity:**
  - Add/Remove edge: O(1)
  - Edge lookup: O(1)
  - Iteration: O(V²)
- **Space Complexity:** O(V²)
- **Best for:** Dense graphs, quick edge lookups

## Use Cases

1. **Social Networks:** Representing friendships and connections
2. **Transportation Networks:** Roads, flights, public transit
3. **Computer Networks:** Router connections, data flow
4. **Recommendation Systems:** Product/user relationships
5. **Web Crawling:** Page links and structure
6. **Game Development:** Map connections and NPC relationships
7. **Biology:** Protein interactions, food chains
8. **Chemistry:** Molecular structures

## When to Use

- **Use Adjacency List when:**
  - Graph is sparse (E << V²)
  - Need to iterate over edges frequently
  - Memory is a concern
  - Most algorithms (BFS, DFS, Dijkstra)

- **Use Adjacency Matrix when:**
  - Graph is dense (E ≈ V²)
  - Need O(1) edge lookups
  - Fixed vertex set
  - Dense graph algorithms

## Best Practices

1. **Choose representation based on graph density:**
   - Sparse: Use adjacency list
   - Dense: Use adjacency matrix

2. **Validate vertex indices:** Always check bounds before operations

3. **Handle disconnected components:** Test connectivity before assuming single component

4. **Use appropriate data structures:** LinkedList for variable degree, ArrayList for fixed graphs

5. **Document graph properties:** Clearly state if directed/undirected, weighted/unweighted

6. **Test edge cases:** Empty graphs, single vertices, disconnected components

7. **Consider memory constraints:** Adjacency matrix uses O(V²) space

