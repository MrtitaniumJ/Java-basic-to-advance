# Graphs in Java

## Simple Explanation

Think of a **Graph** like:
- **Social Network**: People are points (nodes), and friendships are connections (edges). Facebook friends form an undirected graph, while Twitter followers form a directed graph.
- **City Map**: Cities are nodes, and roads are edges. The distance between cities can be the "weight" of the edge.
- **Website Links**: Web pages are nodes, and hyperlinks are directed edges connecting them.

A graph is a collection of connected items where items can have multiple connections to other items.

## Professional Definition

A **Graph** is a non-linear data structure consisting of:
- **Vertices (V)**: Also called nodes or points
- **Edges (E)**: Connections between vertices

Formally: **G = (V, E)** where V is a set of vertices and E is a set of edges.

## Graph Types

### 1. **Directed vs Undirected**
- **Undirected Graph**: Edges have no direction (bidirectional) - e.g., Facebook friendship
- **Directed Graph (Digraph)**: Edges have direction - e.g., Twitter follow, one-way streets

### 2. **Weighted vs Unweighted**
- **Unweighted Graph**: All edges are equal
- **Weighted Graph**: Edges have weights/costs - e.g., distance between cities

### 3. **Other Types**
- **Cyclic**: Contains at least one cycle
- **Acyclic**: No cycles (DAG - Directed Acyclic Graph)
- **Connected**: Path exists between every pair of vertices
- **Disconnected**: Some vertices are not reachable from others
- **Complete**: Every vertex is connected to every other vertex

## Graph Representations

### 1. Adjacency Matrix

A 2D array where `matrix[i][j] = 1` (or weight) if there's an edge from vertex i to vertex j.

**Advantages**: 
- O(1) edge lookup
- Simple implementation

**Disadvantages**: 
- O(V²) space complexity
- Inefficient for sparse graphs

```java
class GraphAdjacencyMatrix {
    private int[][] adjMatrix;
    private int numVertices;
    
    // Constructor
    public GraphAdjacencyMatrix(int numVertices) {
        this.numVertices = numVertices;
        adjMatrix = new int[numVertices][numVertices];
    }
    
    // Add edge (undirected)
    public void addEdge(int i, int j) {
        adjMatrix[i][j] = 1;
        adjMatrix[j][i] = 1; // Remove this line for directed graph
    }
    
    // Add weighted edge
    public void addWeightedEdge(int i, int j, int weight) {
        adjMatrix[i][j] = weight;
        adjMatrix[j][i] = weight; // Remove for directed
    }
    
    // Remove edge
    public void removeEdge(int i, int j) {
        adjMatrix[i][j] = 0;
        adjMatrix[j][i] = 0;
    }
    
    // Check if edge exists
    public boolean isEdge(int i, int j) {
        return adjMatrix[i][j] != 0;
    }
    
    // Print graph
    public void printGraph() {
        System.out.println("Adjacency Matrix:");
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    // Get all neighbors of a vertex
    public void printNeighbors(int vertex) {
        System.out.print("Neighbors of " + vertex + ": ");
        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[vertex][i] != 0) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }
}
```

### 2. Adjacency List

An array of lists where each index represents a vertex and contains a list of adjacent vertices.

**Advantages**: 
- O(V + E) space complexity
- Efficient for sparse graphs
- Faster to iterate over neighbors

**Disadvantages**: 
- O(V) edge lookup in worst case

```java
import java.util.*;

class GraphAdjacencyList {
    private int numVertices;
    private LinkedList<Integer>[] adjList;
    
    // Constructor
    @SuppressWarnings("unchecked")
    public GraphAdjacencyList(int numVertices) {
        this.numVertices = numVertices;
        adjList = new LinkedList[numVertices];
        
        for (int i = 0; i < numVertices; i++) {
            adjList[i] = new LinkedList<>();
        }
    }
    
    // Add edge (undirected)
    public void addEdge(int source, int dest) {
        adjList[source].add(dest);
        adjList[dest].add(source); // Remove for directed graph
    }
    
    // Remove edge
    public void removeEdge(int source, int dest) {
        adjList[source].remove(Integer.valueOf(dest));
        adjList[dest].remove(Integer.valueOf(source));
    }
    
    // Check if edge exists
    public boolean hasEdge(int source, int dest) {
        return adjList[source].contains(dest);
    }
    
    // Get neighbors
    public LinkedList<Integer> getNeighbors(int vertex) {
        return adjList[vertex];
    }
    
    // Print graph
    public void printGraph() {
        for (int i = 0; i < numVertices; i++) {
            System.out.print("Vertex " + i + ": ");
            for (Integer neighbor : adjList[i]) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }
}
```

### 3. Weighted Graph (Adjacency List)

```java
import java.util.*;

// Edge class for weighted graphs
class Edge {
    int destination;
    int weight;
    
    public Edge(int destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }
    
    @Override
    public String toString() {
        return "(" + destination + ", w:" + weight + ")";
    }
}

class WeightedGraph {
    private int numVertices;
    private LinkedList<Edge>[] adjList;
    
    @SuppressWarnings("unchecked")
    public WeightedGraph(int numVertices) {
        this.numVertices = numVertices;
        adjList = new LinkedList[numVertices];
        
        for (int i = 0; i < numVertices; i++) {
            adjList[i] = new LinkedList<>();
        }
    }
    
    // Add weighted edge
    public void addEdge(int source, int dest, int weight) {
        adjList[source].add(new Edge(dest, weight));
        // For undirected graph, add reverse edge
        // adjList[dest].add(new Edge(source, weight));
    }
    
    // Get neighbors with weights
    public LinkedList<Edge> getNeighbors(int vertex) {
        return adjList[vertex];
    }
    
    // Print graph
    public void printGraph() {
        for (int i = 0; i < numVertices; i++) {
            System.out.print("Vertex " + i + ": ");
            for (Edge edge : adjList[i]) {
                System.out.print(edge + " ");
            }
            System.out.println();
        }
    }
}
```

## Graph Traversal Algorithms

### 1. Breadth-First Search (BFS)

BFS explores the graph level by level, visiting all neighbors before moving to the next level.

**Use Cases**:
- Finding shortest path in unweighted graphs
- Finding connected components
- Level-order traversal
- Finding if a path exists

**Time Complexity**: O(V + E)  
**Space Complexity**: O(V)

```java
import java.util.*;

class BFS {
    private int numVertices;
    private LinkedList<Integer>[] adjList;
    
    @SuppressWarnings("unchecked")
    public BFS(int numVertices) {
        this.numVertices = numVertices;
        adjList = new LinkedList[numVertices];
        for (int i = 0; i < numVertices; i++) {
            adjList[i] = new LinkedList<>();
        }
    }
    
    public void addEdge(int source, int dest) {
        adjList[source].add(dest);
    }
    
    // BFS traversal starting from a given vertex
    public void bfsTraversal(int startVertex) {
        boolean[] visited = new boolean[numVertices];
        Queue<Integer> queue = new LinkedList<>();
        
        visited[startVertex] = true;
        queue.add(startVertex);
        
        System.out.print("BFS Traversal: ");
        
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");
            
            // Visit all unvisited neighbors
            for (int neighbor : adjList[vertex]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }
    
    // BFS to find shortest path in unweighted graph
    public void shortestPath(int start, int end) {
        boolean[] visited = new boolean[numVertices];
        int[] parent = new int[numVertices];
        Queue<Integer> queue = new LinkedList<>();
        
        Arrays.fill(parent, -1);
        visited[start] = true;
        queue.add(start);
        
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            
            if (vertex == end) {
                break;
            }
            
            for (int neighbor : adjList[vertex]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = vertex;
                    queue.add(neighbor);
                }
            }
        }
        
        // Reconstruct path
        if (!visited[end]) {
            System.out.println("No path exists from " + start + " to " + end);
            return;
        }
        
        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = parent[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        
        System.out.println("Shortest path from " + start + " to " + end + ": " + path);
        System.out.println("Path length: " + (path.size() - 1));
    }
    
    // BFS for disconnected graph (visit all components)
    public void bfsForDisconnectedGraph() {
        boolean[] visited = new boolean[numVertices];
        
        System.out.println("BFS for all components:");
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                bfsUtil(i, visited);
                System.out.println();
            }
        }
    }
    
    private void bfsUtil(int startVertex, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        visited[startVertex] = true;
        queue.add(startVertex);
        
        System.out.print("Component: ");
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");
            
            for (int neighbor : adjList[vertex]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    }
    
    // Check if graph is bipartite using BFS
    public boolean isBipartite() {
        int[] colors = new int[numVertices];
        Arrays.fill(colors, -1);
        
        for (int i = 0; i < numVertices; i++) {
            if (colors[i] == -1) {
                if (!isBipartiteUtil(i, colors)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isBipartiteUtil(int start, int[] colors) {
        Queue<Integer> queue = new LinkedList<>();
        colors[start] = 0;
        queue.add(start);
        
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            
            for (int neighbor : adjList[vertex]) {
                if (colors[neighbor] == -1) {
                    colors[neighbor] = 1 - colors[vertex];
                    queue.add(neighbor);
                } else if (colors[neighbor] == colors[vertex]) {
                    return false;
                }
            }
        }
        return true;
    }
}
```

### 2. Depth-First Search (DFS)

DFS explores as far as possible along each branch before backtracking.

**Use Cases**:
- Cycle detection
- Topological sorting
- Finding strongly connected components
- Maze solving
- Path finding

**Time Complexity**: O(V + E)  
**Space Complexity**: O(V)

```java
import java.util.*;

class DFS {
    private int numVertices;
    private LinkedList<Integer>[] adjList;
    
    @SuppressWarnings("unchecked")
    public DFS(int numVertices) {
        this.numVertices = numVertices;
        adjList = new LinkedList[numVertices];
        for (int i = 0; i < numVertices; i++) {
            adjList[i] = new LinkedList<>();
        }
    }
    
    public void addEdge(int source, int dest) {
        adjList[source].add(dest);
    }
    
    // DFS traversal (recursive)
    public void dfsTraversal(int startVertex) {
        boolean[] visited = new boolean[numVertices];
        System.out.print("DFS Traversal (Recursive): ");
        dfsRecursive(startVertex, visited);
        System.out.println();
    }
    
    private void dfsRecursive(int vertex, boolean[] visited) {
        visited[vertex] = true;
        System.out.print(vertex + " ");
        
        for (int neighbor : adjList[vertex]) {
            if (!visited[neighbor]) {
                dfsRecursive(neighbor, visited);
            }
        }
    }
    
    // DFS traversal (iterative using stack)
    public void dfsIterative(int startVertex) {
        boolean[] visited = new boolean[numVertices];
        Stack<Integer> stack = new Stack<>();
        
        stack.push(startVertex);
        System.out.print("DFS Traversal (Iterative): ");
        
        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            
            if (!visited[vertex]) {
                visited[vertex] = true;
                System.out.print(vertex + " ");
                
                // Push neighbors in reverse order for left-to-right traversal
                List<Integer> neighbors = new ArrayList<>(adjList[vertex]);
                Collections.reverse(neighbors);
                for (int neighbor : neighbors) {
                    if (!visited[neighbor]) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        System.out.println();
    }
    
    // DFS for disconnected graph
    public void dfsForDisconnectedGraph() {
        boolean[] visited = new boolean[numVertices];
        
        System.out.println("DFS for all components:");
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                System.out.print("Component: ");
                dfsRecursive(i, visited);
                System.out.println();
            }
        }
    }
    
    // Cycle detection in directed graph
    public boolean hasCycleDirected() {
        boolean[] visited = new boolean[numVertices];
        boolean[] recStack = new boolean[numVertices];
        
        for (int i = 0; i < numVertices; i++) {
            if (hasCycleUtil(i, visited, recStack)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean hasCycleUtil(int vertex, boolean[] visited, boolean[] recStack) {
        if (recStack[vertex]) {
            return true;
        }
        if (visited[vertex]) {
            return false;
        }
        
        visited[vertex] = true;
        recStack[vertex] = true;
        
        for (int neighbor : adjList[vertex]) {
            if (hasCycleUtil(neighbor, visited, recStack)) {
                return true;
            }
        }
        
        recStack[vertex] = false;
        return false;
    }
    
    // Topological Sort (only for DAG)
    public List<Integer> topologicalSort() {
        boolean[] visited = new boolean[numVertices];
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }
        
        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }
    
    private void topologicalSortUtil(int vertex, boolean[] visited, Stack<Integer> stack) {
        visited[vertex] = true;
        
        for (int neighbor : adjList[vertex]) {
            if (!visited[neighbor]) {
                topologicalSortUtil(neighbor, visited, stack);
            }
        }
        
        stack.push(vertex);
    }
    
    // Find all paths from source to destination
    public void findAllPaths(int start, int end) {
        boolean[] visited = new boolean[numVertices];
        List<Integer> path = new ArrayList<>();
        List<List<Integer>> allPaths = new ArrayList<>();
        
        findAllPathsUtil(start, end, visited, path, allPaths);
        
        System.out.println("All paths from " + start + " to " + end + ":");
        for (List<Integer> p : allPaths) {
            System.out.println(p);
        }
    }
    
    private void findAllPathsUtil(int current, int end, boolean[] visited, 
                                   List<Integer> path, List<List<Integer>> allPaths) {
        visited[current] = true;
        path.add(current);
        
        if (current == end) {
            allPaths.add(new ArrayList<>(path));
        } else {
            for (int neighbor : adjList[current]) {
                if (!visited[neighbor]) {
                    findAllPathsUtil(neighbor, end, visited, path, allPaths);
                }
            }
        }
        
        path.remove(path.size() - 1);
        visited[current] = false;
    }
}
```

## Shortest Path Algorithms

### 1. Dijkstra's Algorithm

Finds shortest path from a source vertex to all other vertices in a **weighted graph with non-negative weights**.

**Algorithm**: Uses a priority queue to always process the vertex with minimum distance.

**Time Complexity**: O((V + E) log V) with min-heap  
**Space Complexity**: O(V)

**When to Use**:
- Single-source shortest path
- Non-negative edge weights
- Real-world applications: GPS navigation, network routing

```java
import java.util.*;

class Dijkstra {
    private int numVertices;
    private LinkedList<Edge>[] adjList;
    
    static class Edge {
        int destination;
        int weight;
        
        Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }
    
    static class Node implements Comparable<Node> {
        int vertex;
        int distance;
        
        Node(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
        
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
    
    @SuppressWarnings("unchecked")
    public Dijkstra(int numVertices) {
        this.numVertices = numVertices;
        adjList = new LinkedList[numVertices];
        for (int i = 0; i < numVertices; i++) {
            adjList[i] = new LinkedList<>();
        }
    }
    
    public void addEdge(int source, int dest, int weight) {
        adjList[source].add(new Edge(dest, weight));
        // For undirected graph:
        // adjList[dest].add(new Edge(source, weight));
    }
    
    public void shortestPath(int source) {
        int[] distances = new int[numVertices];
        int[] parent = new int[numVertices];
        boolean[] visited = new boolean[numVertices];
        
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        distances[source] = 0;
        
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(source, 0));
        
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;
            
            if (visited[u]) continue;
            visited[u] = true;
            
            // Process all neighbors
            for (Edge edge : adjList[u]) {
                int v = edge.destination;
                int weight = edge.weight;
                
                // Relaxation step
                if (!visited[v] && distances[u] != Integer.MAX_VALUE 
                    && distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                    parent[v] = u;
                    pq.offer(new Node(v, distances[v]));
                }
            }
        }
        
        printSolution(source, distances, parent);
    }
    
    private void printSolution(int source, int[] distances, int[] parent) {
        System.out.println("Dijkstra's Shortest Paths from vertex " + source + ":");
        System.out.println("Vertex\tDistance\tPath");
        
        for (int i = 0; i < numVertices; i++) {
            System.out.print(i + "\t");
            if (distances[i] == Integer.MAX_VALUE) {
                System.out.println("INF\t\tNo path");
            } else {
                System.out.print(distances[i] + "\t\t");
                printPath(i, parent);
                System.out.println();
            }
        }
    }
    
    private void printPath(int vertex, int[] parent) {
        if (parent[vertex] == -1) {
            System.out.print(vertex);
            return;
        }
        printPath(parent[vertex], parent);
        System.out.print(" -> " + vertex);
    }
    
    // Get shortest path to a specific destination
    public List<Integer> getPath(int source, int dest) {
        int[] distances = new int[numVertices];
        int[] parent = new int[numVertices];
        boolean[] visited = new boolean[numVertices];
        
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        distances[source] = 0;
        
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(source, 0));
        
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;
            
            if (visited[u]) continue;
            visited[u] = true;
            
            if (u == dest) break;
            
            for (Edge edge : adjList[u]) {
                int v = edge.destination;
                int weight = edge.weight;
                
                if (!visited[v] && distances[u] != Integer.MAX_VALUE 
                    && distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                    parent[v] = u;
                    pq.offer(new Node(v, distances[v]));
                }
            }
        }
        
        List<Integer> path = new ArrayList<>();
        if (distances[dest] == Integer.MAX_VALUE) {
            return path; // No path exists
        }
        
        for (int at = dest; at != -1; at = parent[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}
```

### 2. Bellman-Ford Algorithm

Finds shortest paths from source to all vertices, **works with negative weights**, and **detects negative cycles**.

**Algorithm**: Relaxes all edges V-1 times.

**Time Complexity**: O(V × E)  
**Space Complexity**: O(V)

**When to Use**:
- Graph has negative edge weights
- Need to detect negative cycles
- Distributed systems (easier to implement)

```java
import java.util.*;

class BellmanFord {
    private int numVertices;
    private List<Edge> edges;
    
    static class Edge {
        int source;
        int destination;
        int weight;
        
        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }
    
    public BellmanFord(int numVertices) {
        this.numVertices = numVertices;
        this.edges = new ArrayList<>();
    }
    
    public void addEdge(int source, int dest, int weight) {
        edges.add(new Edge(source, dest, weight));
    }
    
    public void shortestPath(int source) {
        int[] distances = new int[numVertices];
        int[] parent = new int[numVertices];
        
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        distances[source] = 0;
        
        // Relax all edges V-1 times
        for (int i = 0; i < numVertices - 1; i++) {
            for (Edge edge : edges) {
                int u = edge.source;
                int v = edge.destination;
                int weight = edge.weight;
                
                if (distances[u] != Integer.MAX_VALUE 
                    && distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                    parent[v] = u;
                }
            }
        }
        
        // Check for negative-weight cycles
        boolean hasNegativeCycle = false;
        for (Edge edge : edges) {
            int u = edge.source;
            int v = edge.destination;
            int weight = edge.weight;
            
            if (distances[u] != Integer.MAX_VALUE 
                && distances[u] + weight < distances[v]) {
                hasNegativeCycle = true;
                System.out.println("Graph contains negative weight cycle!");
                break;
            }
        }
        
        if (!hasNegativeCycle) {
            printSolution(source, distances, parent);
        }
    }
    
    // Detect negative cycle
    public boolean hasNegativeCycle() {
        int[] distances = new int[numVertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[0] = 0; // Start from any vertex
        
        // Relax all edges V-1 times
        for (int i = 0; i < numVertices - 1; i++) {
            for (Edge edge : edges) {
                int u = edge.source;
                int v = edge.destination;
                int weight = edge.weight;
                
                if (distances[u] != Integer.MAX_VALUE 
                    && distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                }
            }
        }
        
        // Check for negative cycle
        for (Edge edge : edges) {
            int u = edge.source;
            int v = edge.destination;
            int weight = edge.weight;
            
            if (distances[u] != Integer.MAX_VALUE 
                && distances[u] + weight < distances[v]) {
                return true;
            }
        }
        return false;
    }
    
    private void printSolution(int source, int[] distances, int[] parent) {
        System.out.println("Bellman-Ford Shortest Paths from vertex " + source + ":");
        System.out.println("Vertex\tDistance\tPath");
        
        for (int i = 0; i < numVertices; i++) {
            System.out.print(i + "\t");
            if (distances[i] == Integer.MAX_VALUE) {
                System.out.println("INF\t\tNo path");
            } else {
                System.out.print(distances[i] + "\t\t");
                printPath(i, parent);
                System.out.println();
            }
        }
    }
    
    private void printPath(int vertex, int[] parent) {
        if (parent[vertex] == -1) {
            System.out.print(vertex);
            return;
        }
        printPath(parent[vertex], parent);
        System.out.print(" -> " + vertex);
    }
}
```

### 3. Floyd-Warshall Algorithm

Finds shortest paths between **all pairs of vertices**. Works with negative weights but not negative cycles.

**Algorithm**: Dynamic programming approach using intermediate vertices.

**Time Complexity**: O(V³)  
**Space Complexity**: O(V²)

**When to Use**:
- Need shortest paths between all pairs of vertices
- Dense graphs
- Small graphs (due to O(V³) time)
- Finding transitive closure

```java
import java.util.*;

class FloydWarshall {
    private int numVertices;
    private int[][] graph;
    private static final int INF = 99999;
    
    public FloydWarshall(int numVertices) {
        this.numVertices = numVertices;
        this.graph = new int[numVertices][numVertices];
        
        // Initialize graph with infinity
        for (int i = 0; i < numVertices; i++) {
            Arrays.fill(graph[i], INF);
            graph[i][i] = 0; // Distance to self is 0
        }
    }
    
    public void addEdge(int source, int dest, int weight) {
        graph[source][dest] = weight;
    }
    
    public int[][] allPairsShortestPath() {
        int[][] dist = new int[numVertices][numVertices];
        int[][] next = new int[numVertices][numVertices];
        
        // Initialize distance and next matrices
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                dist[i][j] = graph[i][j];
                if (graph[i][j] != INF && i != j) {
                    next[i][j] = j;
                } else {
                    next[i][j] = -1;
                }
            }
        }
        
        // Floyd-Warshall algorithm
        for (int k = 0; k < numVertices; k++) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF 
                        && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
        
        // Check for negative cycles
        for (int i = 0; i < numVertices; i++) {
            if (dist[i][i] < 0) {
                System.out.println("Graph contains negative weight cycle!");
                return null;
            }
        }
        
        printSolution(dist, next);
        return dist;
    }
    
    private void printSolution(int[][] dist, int[][] next) {
        System.out.println("Floyd-Warshall All-Pairs Shortest Paths:");
        System.out.println("\nDistance Matrix:");
        System.out.print("     ");
        for (int i = 0; i < numVertices; i++) {
            System.out.printf("%5d", i);
        }
        System.out.println();
        
        for (int i = 0; i < numVertices; i++) {
            System.out.printf("%5d", i);
            for (int j = 0; j < numVertices; j++) {
                if (dist[i][j] == INF) {
                    System.out.print("  INF");
                } else {
                    System.out.printf("%5d", dist[i][j]);
                }
            }
            System.out.println();
        }
        
        System.out.println("\nSample paths:");
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (i != j && dist[i][j] != INF) {
                    System.out.print("Path " + i + " -> " + j + ": ");
                    printPath(i, j, next);
                    System.out.println(" (distance: " + dist[i][j] + ")");
                }
            }
        }
    }
    
    private void printPath(int start, int end, int[][] next) {
        if (next[start][end] == -1) {
            System.out.print("No path");
            return;
        }
        
        List<Integer> path = new ArrayList<>();
        path.add(start);
        
        while (start != end) {
            start = next[start][end];
            path.add(start);
        }
        
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
    }
    
    // Get specific path between two vertices
    public List<Integer> getPath(int start, int end) {
        int[][] dist = new int[numVertices][numVertices];
        int[][] next = new int[numVertices][numVertices];
        
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                dist[i][j] = graph[i][j];
                if (graph[i][j] != INF && i != j) {
                    next[i][j] = j;
                } else {
                    next[i][j] = -1;
                }
            }
        }
        
        for (int k = 0; k < numVertices; k++) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF 
                        && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
        
        List<Integer> path = new ArrayList<>();
        if (next[start][end] == -1) {
            return path;
        }
        
        path.add(start);
        while (start != end) {
            start = next[start][end];
            path.add(start);
        }
        
        return path;
    }
}
```

## Algorithm Comparison: When to Use What?

| Algorithm | Use Case | Time Complexity | Space | Negative Weights | Negative Cycles |
|-----------|----------|-----------------|-------|------------------|-----------------|
| **BFS** | Shortest path in unweighted graphs | O(V+E) | O(V) | N/A | No |
| **DFS** | Path existence, cycle detection, topological sort | O(V+E) | O(V) | N/A | Yes (can detect) |
| **Dijkstra** | Single-source shortest path, non-negative weights | O((V+E)logV) | O(V) | No | N/A |
| **Bellman-Ford** | Single-source, negative weights allowed | O(V×E) | O(V) | Yes | Yes (can detect) |
| **Floyd-Warshall** | All-pairs shortest paths | O(V³) | O(V²) | Yes | Yes (can detect) |

### Decision Tree:

1. **Unweighted graph?** → Use BFS
2. **Need to detect cycles or do topological sort?** → Use DFS
3. **Single-source shortest path:**
   - **No negative weights?** → Use Dijkstra
   - **Has negative weights?** → Use Bellman-Ford
4. **All-pairs shortest paths?** → Use Floyd-Warshall
5. **Need to detect negative cycle?** → Use Bellman-Ford or Floyd-Warshall

## Complete Working Example

```java
public class GraphExamples {
    
    public static void main(String[] args) {
        System.out.println("=== Graph Traversal Examples ===\n");
        
        // BFS Example
        System.out.println("1. BFS Example:");
        BFS bfsGraph = new BFS(6);
        bfsGraph.addEdge(0, 1);
        bfsGraph.addEdge(0, 2);
        bfsGraph.addEdge(1, 3);
        bfsGraph.addEdge(1, 4);
        bfsGraph.addEdge(2, 4);
        bfsGraph.addEdge(3, 5);
        bfsGraph.addEdge(4, 5);
        
        bfsGraph.bfsTraversal(0);
        bfsGraph.shortestPath(0, 5);
        System.out.println();
        
        // DFS Example
        System.out.println("2. DFS Example:");
        DFS dfsGraph = new DFS(6);
        dfsGraph.addEdge(0, 1);
        dfsGraph.addEdge(0, 2);
        dfsGraph.addEdge(1, 3);
        dfsGraph.addEdge(1, 4);
        dfsGraph.addEdge(2, 4);
        dfsGraph.addEdge(3, 5);
        dfsGraph.addEdge(4, 5);
        
        dfsGraph.dfsTraversal(0);
        dfsGraph.dfsIterative(0);
        System.out.println();
        
        // Dijkstra Example
        System.out.println("3. Dijkstra's Algorithm:");
        Dijkstra dijkstra = new Dijkstra(5);
        dijkstra.addEdge(0, 1, 4);
        dijkstra.addEdge(0, 2, 1);
        dijkstra.addEdge(2, 1, 2);
        dijkstra.addEdge(1, 3, 1);
        dijkstra.addEdge(2, 3, 5);
        dijkstra.addEdge(3, 4, 3);
        
        dijkstra.shortestPath(0);
        System.out.println();
        
        // Bellman-Ford Example
        System.out.println("4. Bellman-Ford Algorithm:");
        BellmanFord bellmanFord = new BellmanFord(5);
        bellmanFord.addEdge(0, 1, 4);
        bellmanFord.addEdge(0, 2, 1);
        bellmanFord.addEdge(2, 1, 2);
        bellmanFord.addEdge(1, 3, 1);
        bellmanFord.addEdge(2, 3, 5);
        bellmanFord.addEdge(3, 4, 3);
        
        bellmanFord.shortestPath(0);
        System.out.println();
        
        // Floyd-Warshall Example
        System.out.println("5. Floyd-Warshall Algorithm:");
        FloydWarshall floydWarshall = new FloydWarshall(4);
        floydWarshall.addEdge(0, 1, 3);
        floydWarshall.addEdge(0, 3, 7);
        floydWarshall.addEdge(1, 0, 8);
        floydWarshall.addEdge(1, 2, 2);
        floydWarshall.addEdge(2, 0, 5);
        floydWarshall.addEdge(2, 3, 1);
        floydWarshall.addEdge(3, 0, 2);
        
        floydWarshall.allPairsShortestPath();
        System.out.println();
        
        // Graph Representations Example
        System.out.println("6. Graph Representations:");
        
        // Adjacency Matrix
        System.out.println("\nAdjacency Matrix:");
        GraphAdjacencyMatrix matrixGraph = new GraphAdjacencyMatrix(4);
        matrixGraph.addEdge(0, 1);
        matrixGraph.addEdge(0, 2);
        matrixGraph.addEdge(1, 2);
        matrixGraph.addEdge(2, 3);
        matrixGraph.printGraph();
        
        // Adjacency List
        System.out.println("\nAdjacency List:");
        GraphAdjacencyList listGraph = new GraphAdjacencyList(4);
        listGraph.addEdge(0, 1);
        listGraph.addEdge(0, 2);
        listGraph.addEdge(1, 2);
        listGraph.addEdge(2, 3);
        listGraph.printGraph();
        
        // Weighted Graph
        System.out.println("\nWeighted Graph:");
        WeightedGraph weightedGraph = new WeightedGraph(4);
        weightedGraph.addEdge(0, 1, 10);
        weightedGraph.addEdge(0, 2, 5);
        weightedGraph.addEdge(1, 2, 2);
        weightedGraph.addEdge(2, 3, 1);
        weightedGraph.printGraph();
    }
}
```

## Common Interview Questions

### 1. **Basic Graph Questions**

**Q1: How do you represent a graph?**
- Adjacency Matrix: 2D array, O(V²) space, O(1) edge lookup
- Adjacency List: Array of lists, O(V+E) space, better for sparse graphs

**Q2: What's the difference between BFS and DFS?**
- BFS: Level-by-level, uses queue, finds shortest path in unweighted graphs
- DFS: Goes deep first, uses stack/recursion, better for path finding and cycle detection

**Q3: When would you use BFS over DFS?**
- Finding shortest path in unweighted graphs
- Finding all nodes within one level/distance
- Level-order traversal

### 2. **Path Finding Questions**

**Q4: Find if path exists between two vertices**
```java
public boolean hasPath(int source, int dest, LinkedList<Integer>[] adjList) {
    boolean[] visited = new boolean[adjList.length];
    return dfsHasPath(source, dest, adjList, visited);
}

private boolean dfsHasPath(int current, int dest, LinkedList<Integer>[] adjList, 
                           boolean[] visited) {
    if (current == dest) return true;
    visited[current] = true;
    
    for (int neighbor : adjList[current]) {
        if (!visited[neighbor]) {
            if (dfsHasPath(neighbor, dest, adjList, visited)) {
                return true;
            }
        }
    }
    return false;
}
```

**Q5: Count all paths from source to destination**
```java
public int countPaths(int source, int dest, LinkedList<Integer>[] adjList) {
    boolean[] visited = new boolean[adjList.length];
    return countPathsUtil(source, dest, adjList, visited);
}

private int countPathsUtil(int current, int dest, LinkedList<Integer>[] adjList,
                           boolean[] visited) {
    if (current == dest) return 1;
    
    visited[current] = true;
    int count = 0;
    
    for (int neighbor : adjList[current]) {
        if (!visited[neighbor]) {
            count += countPathsUtil(neighbor, dest, adjList, visited);
        }
    }
    
    visited[current] = false; // Backtrack
    return count;
}
```

### 3. **Cycle Detection**

**Q6: Detect cycle in undirected graph**
```java
public boolean hasCycleUndirected(LinkedList<Integer>[] adjList) {
    boolean[] visited = new boolean[adjList.length];
    
    for (int i = 0; i < adjList.length; i++) {
        if (!visited[i]) {
            if (hasCycleUtil(i, visited, -1, adjList)) {
                return true;
            }
        }
    }
    return false;
}

private boolean hasCycleUtil(int vertex, boolean[] visited, int parent,
                             LinkedList<Integer>[] adjList) {
    visited[vertex] = true;
    
    for (int neighbor : adjList[vertex]) {
        if (!visited[neighbor]) {
            if (hasCycleUtil(neighbor, visited, vertex, adjList)) {
                return true;
            }
        } else if (neighbor != parent) {
            return true; // Cycle found
        }
    }
    return false;
}
```

### 4. **Connected Components**

**Q7: Count number of connected components**
```java
public int countConnectedComponents(LinkedList<Integer>[] adjList) {
    boolean[] visited = new boolean[adjList.length];
    int count = 0;
    
    for (int i = 0; i < adjList.length; i++) {
        if (!visited[i]) {
            dfs(i, adjList, visited);
            count++;
        }
    }
    return count;
}

private void dfs(int vertex, LinkedList<Integer>[] adjList, boolean[] visited) {
    visited[vertex] = true;
    for (int neighbor : adjList[vertex]) {
        if (!visited[neighbor]) {
            dfs(neighbor, adjList, visited);
        }
    }
}
```

### 5. **Advanced Questions**

**Q8: Clone a graph**
```java
class Node {
    int val;
    List<Node> neighbors;
    
    public Node(int val) {
        this.val = val;
        neighbors = new ArrayList<>();
    }
}

public Node cloneGraph(Node node) {
    if (node == null) return null;
    
    Map<Node, Node> map = new HashMap<>();
    return cloneGraphDFS(node, map);
}

private Node cloneGraphDFS(Node node, Map<Node, Node> map) {
    if (map.containsKey(node)) {
        return map.get(node);
    }
    
    Node clone = new Node(node.val);
    map.put(node, clone);
    
    for (Node neighbor : node.neighbors) {
        clone.neighbors.add(cloneGraphDFS(neighbor, map));
    }
    
    return clone;
}
```

**Q9: Is graph bipartite?** (Already covered in BFS section)

**Q10: Find strongly connected components (Kosaraju's Algorithm)**
```java
public List<List<Integer>> findSCC(LinkedList<Integer>[] adjList) {
    int n = adjList.length;
    boolean[] visited = new boolean[n];
    Stack<Integer> stack = new Stack<>();
    
    // Step 1: Fill stack with finish times
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            fillOrder(i, visited, stack, adjList);
        }
    }
    
    // Step 2: Create transpose graph
    LinkedList<Integer>[] transpose = getTranspose(adjList);
    
    // Step 3: Do DFS on transpose in order of finish times
    Arrays.fill(visited, false);
    List<List<Integer>> sccList = new ArrayList<>();
    
    while (!stack.isEmpty()) {
        int v = stack.pop();
        if (!visited[v]) {
            List<Integer> component = new ArrayList<>();
            dfsSCC(v, visited, transpose, component);
            sccList.add(component);
        }
    }
    
    return sccList;
}

private void fillOrder(int v, boolean[] visited, Stack<Integer> stack,
                       LinkedList<Integer>[] adjList) {
    visited[v] = true;
    for (int neighbor : adjList[v]) {
        if (!visited[neighbor]) {
            fillOrder(neighbor, visited, stack, adjList);
        }
    }
    stack.push(v);
}

@SuppressWarnings("unchecked")
private LinkedList<Integer>[] getTranspose(LinkedList<Integer>[] adjList) {
    int n = adjList.length;
    LinkedList<Integer>[] transpose = new LinkedList[n];
    for (int i = 0; i < n; i++) {
        transpose[i] = new LinkedList<>();
    }
    
    for (int v = 0; v < n; v++) {
        for (int neighbor : adjList[v]) {
            transpose[neighbor].add(v);
        }
    }
    return transpose;
}

private void dfsSCC(int v, boolean[] visited, LinkedList<Integer>[] adjList,
                    List<Integer> component) {
    visited[v] = true;
    component.add(v);
    
    for (int neighbor : adjList[v]) {
        if (!visited[neighbor]) {
            dfsSCC(neighbor, visited, adjList, component);
        }
    }
}
```

## Practice Problems

1. **Number of Islands** (LeetCode 200)
2. **Course Schedule** (LeetCode 207) - Cycle detection & topological sort
3. **Word Ladder** (LeetCode 127) - BFS
4. **Network Delay Time** (LeetCode 743) - Dijkstra
5. **Cheapest Flights Within K Stops** (LeetCode 787) - Bellman-Ford
6. **Clone Graph** (LeetCode 133)
7. **Graph Valid Tree** (LeetCode 261)
8. **Minimum Height Trees** (LeetCode 310)
9. **Alien Dictionary** (LeetCode 269) - Topological sort
10. **Critical Connections in a Network** (LeetCode 1192) - Tarjan's algorithm

## Time and Space Complexities

| Operation | Adjacency Matrix | Adjacency List |
|-----------|------------------|----------------|
| Add Vertex | O(V²) | O(1) |
| Add Edge | O(1) | O(1) |
| Remove Vertex | O(V²) | O(V + E) |
| Remove Edge | O(1) | O(V) |
| Query Edge | O(1) | O(V) |
| Space | O(V²) | O(V + E) |

## Key Takeaways

1. **Choose the right representation**: Adjacency list for sparse graphs, matrix for dense graphs
2. **BFS for shortest paths**: In unweighted graphs
3. **DFS for connectivity**: Cycle detection, topological sorting, path finding
4. **Dijkstra for non-negative weights**: Fastest single-source shortest path
5. **Bellman-Ford for negative weights**: Can detect negative cycles
6. **Floyd-Warshall for all-pairs**: When you need distances between all vertices

## Common Mistakes to Avoid

1. Forgetting to mark vertices as visited (infinite loops)
2. Not handling disconnected graphs
3. Using Dijkstra with negative weights
4. Not checking for negative cycles in Bellman-Ford
5. Confusing directed and undirected graphs
6. Not initializing distance arrays properly (use Integer.MAX_VALUE)
7. Integer overflow when adding weights (check before addition)

---

**Next Topics**: 
- [Minimum Spanning Trees](../08-MST/)
- [Advanced Graph Algorithms](../09-Advanced-Graph/)
- [Graph Applications](../10-Graph-Applications/)
