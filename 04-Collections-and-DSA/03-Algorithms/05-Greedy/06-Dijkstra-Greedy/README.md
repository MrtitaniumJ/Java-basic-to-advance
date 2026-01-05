# Dijkstra's Algorithm - Greedy Approach to Shortest Path

## Problem Statement

Dijkstra's algorithm is a **greedy algorithm** that finds the **shortest path** between nodes in a weighted graph. It was conceived by computer scientist Edsger W. Dijkstra in 1956.

### Problem Definition
Given:
- A **weighted directed graph** G = (V, E)
- A **source vertex** s
- **Non-negative edge weights**

Find:
- The **shortest distance** from source s to all other vertices
- The **shortest path tree** showing the actual paths

### Problem Constraints
1. All edge weights must be **non-negative** (crucial for correctness)
2. Graph can be directed or undirected
3. Works for both connected and disconnected graphs
4. Does not work with negative edge weights (use Bellman-Ford instead)

### Problem Example
```
Graph:
    1
  A --- B
  |     |
2 | 5   | 3
  |     |
  C --- D
    4

Shortest paths from A:
  A to A: 0
  A to B: 1
  A to C: 2
  A to D: 4 (A→B→D or A→C→D)
```

## Greedy Choice Property

Dijkstra's algorithm embodies the greedy strategy at each step:

### Greedy Strategy
1. **Maintain a set** of visited vertices and their shortest distances
2. **Repeat**: From unvisited vertices, select the one with **minimum distance**
3. **Relax edges**: Update distances to its neighbors
4. **Continue** until all vertices are processed

### Why This Works (Greedy Correctness)
- **Greedy Choice**: Always select the unvisited vertex with smallest known distance
- **Optimality**: Once a vertex's shortest distance is finalized, it cannot improve
- **Proof by Exchange Argument**: If a shorter path existed through an unvisited vertex, the unvisited vertex itself would have a smaller distance

### Key Property
```
If vertex u is chosen as the next vertex with smallest distance,
then dist[u] is FINAL and cannot be improved.

This is because:
- Any path from s to any other unvisited vertex v would have to go through u
- Since all weights are non-negative, any such path would be longer
- Therefore, selecting u first guarantees its distance is optimal
```

### Greedy vs Other Approaches
- **Greedy (Dijkstra)**: O((V² + E) or O((V + E) log V) with heap), works with non-negative weights
- **Bellman-Ford**: O(V × E), handles negative weights
- **A***: Heuristic-based, often faster in practice but needs heuristic function
- **BFS**: Works only for unweighted graphs

## Java Implementation

### Graph and Edge Classes

```java
/**
 * Represents an edge in the graph
 */
public class Edge implements Comparable<Edge> {
    public int source;
    public int destination;
    public double weight;
    
    public Edge(int source, int destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
    
    // Sort by weight in ascending order
    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }
    
    @Override
    public String toString() {
        return String.format("Edge(%d→%d, weight=%.2f)", 
                           source, destination, weight);
    }
}
```

```java
/**
 * Represents vertex distance information
 */
public class VertexDistance implements Comparable<VertexDistance> {
    public int vertex;
    public double distance;
    public int previousVertex;  // For path reconstruction
    
    public VertexDistance(int vertex, double distance) {
        this.vertex = vertex;
        this.distance = distance;
        this.previousVertex = -1;
    }
    
    // Sort by distance in ascending order (for min-heap)
    @Override
    public int compareTo(VertexDistance other) {
        return Double.compare(this.distance, other.distance);
    }
    
    @Override
    public String toString() {
        return String.format("V%d(dist=%.2f)", vertex, distance);
    }
}
```

```java
/**
 * Represents the weighted graph using adjacency list
 */
public class WeightedGraph {
    private int vertexCount;
    private List<List<Edge>> adjacencyList;
    
    public WeightedGraph(int vertexCount) {
        this.vertexCount = vertexCount;
        this.adjacencyList = new ArrayList<>();
        
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }
    
    public void addEdge(int source, int destination, double weight) {
        if (source >= 0 && source < vertexCount && 
            destination >= 0 && destination < vertexCount) {
            adjacencyList.get(source).add(
                new Edge(source, destination, weight));
        }
    }
    
    public void addUndirectedEdge(int u, int v, double weight) {
        addEdge(u, v, weight);
        addEdge(v, u, weight);
    }
    
    public List<Edge> getAdjacentEdges(int vertex) {
        if (vertex >= 0 && vertex < vertexCount) {
            return adjacencyList.get(vertex);
        }
        return new ArrayList<>();
    }
    
    public int getVertexCount() {
        return vertexCount;
    }
}
```

### Dijkstra's Algorithm Implementation

```java
/**
 * Result class to store shortest path information
 */
public class DijkstraResult {
    public double[] distances;
    public int[] previousVertices;
    public boolean[] visited;
    
    public DijkstraResult(double[] distances, int[] previousVertices, 
                         boolean[] visited) {
        this.distances = distances;
        this.previousVertices = previousVertices;
        this.visited = visited;
    }
    
    public void printDistances() {
        System.out.println("\nShortest Distances:");
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] == Double.POSITIVE_INFINITY) {
                System.out.printf("Vertex %d: UNREACHABLE\n", i);
            } else {
                System.out.printf("Vertex %d: %.2f\n", i, distances[i]);
            }
        }
    }
    
    public void printPaths(int source) {
        System.out.println("\nShortest Paths from Vertex " + source + ":");
        for (int i = 0; i < distances.length; i++) {
            System.out.print("  Path to Vertex " + i + ": ");
            if (distances[i] == Double.POSITIVE_INFINITY) {
                System.out.println("UNREACHABLE");
            } else {
                List<Integer> path = getPath(source, i);
                System.out.println(path + " (Distance: " + 
                                 String.format("%.2f", distances[i]) + ")");
            }
        }
    }
    
    private List<Integer> getPath(int source, int destination) {
        List<Integer> path = new ArrayList<>();
        int current = destination;
        
        // Reconstruct path
        while (current != -1) {
            path.add(0, current);
            if (current == source) break;
            current = previousVertices[current];
        }
        
        return path;
    }
}
```

```java
public class DijkstraAlgorithm {
    
    /**
     * Dijkstra's algorithm using priority queue (min-heap)
     * Time Complexity: O((V + E) log V)
     * Space Complexity: O(V + E)
     * 
     * This is the most efficient implementation
     */
    public static DijkstraResult dijkstraWithHeap(WeightedGraph graph, 
                                                   int sourceVertex) {
        int n = graph.getVertexCount();
        
        double[] distances = new double[n];
        int[] previousVertices = new int[n];
        boolean[] visited = new boolean[n];
        
        // Initialize
        for (int i = 0; i < n; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
            previousVertices[i] = -1;
        }
        distances[sourceVertex] = 0;
        
        // Min-heap priority queue
        PriorityQueue<VertexDistance> pq = new PriorityQueue<>();
        pq.offer(new VertexDistance(sourceVertex, 0));
        
        while (!pq.isEmpty()) {
            VertexDistance current = pq.poll();
            int u = current.vertex;
            
            // Skip if already visited
            if (visited[u]) {
                continue;
            }
            
            visited[u] = true;
            
            // Relax edges from u
            for (Edge edge : graph.getAdjacentEdges(u)) {
                int v = edge.destination;
                double newDist = distances[u] + edge.weight;
                
                // If a shorter path is found, update
                if (newDist < distances[v]) {
                    distances[v] = newDist;
                    previousVertices[v] = u;
                    pq.offer(new VertexDistance(v, newDist));
                }
            }
        }
        
        return new DijkstraResult(distances, previousVertices, visited);
    }
    
    /**
     * Dijkstra's algorithm using array (simpler but slower)
     * Time Complexity: O(V²)
     * Space Complexity: O(V)
     * 
     * Better for dense graphs
     */
    public static DijkstraResult dijkstraWithArray(WeightedGraph graph, 
                                                    int sourceVertex) {
        int n = graph.getVertexCount();
        
        double[] distances = new double[n];
        int[] previousVertices = new int[n];
        boolean[] visited = new boolean[n];
        
        // Initialize
        for (int i = 0; i < n; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
            previousVertices[i] = -1;
        }
        distances[sourceVertex] = 0;
        
        // Process vertices
        for (int count = 0; count < n - 1; count++) {
            // Find minimum distance unvisited vertex
            int u = -1;
            double minDist = Double.POSITIVE_INFINITY;
            
            for (int i = 0; i < n; i++) {
                if (!visited[i] && distances[i] < minDist) {
                    minDist = distances[i];
                    u = i;
                }
            }
            
            if (u == -1) break;  // No more reachable vertices
            
            visited[u] = true;
            
            // Relax edges from u
            for (Edge edge : graph.getAdjacentEdges(u)) {
                int v = edge.destination;
                double newDist = distances[u] + edge.weight;
                
                if (newDist < distances[v]) {
                    distances[v] = newDist;
                    previousVertices[v] = u;
                }
            }
        }
        
        return new DijkstraResult(distances, previousVertices, visited);
    }
    
    /**
     * Detailed version with step-by-step tracking
     */
    public static DijkstraResult dijkstraDetailed(WeightedGraph graph, 
                                                   int sourceVertex) {
        int n = graph.getVertexCount();
        
        double[] distances = new double[n];
        int[] previousVertices = new int[n];
        boolean[] visited = new boolean[n];
        
        for (int i = 0; i < n; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
            previousVertices[i] = -1;
        }
        distances[sourceVertex] = 0;
        
        PriorityQueue<VertexDistance> pq = new PriorityQueue<>();
        pq.offer(new VertexDistance(sourceVertex, 0));
        
        System.out.println("\nDijkstra's Algorithm Execution:");
        System.out.println("Source Vertex: " + sourceVertex);
        System.out.println("=====================================");
        
        int step = 1;
        while (!pq.isEmpty()) {
            VertexDistance current = pq.poll();
            int u = current.vertex;
            
            if (visited[u]) {
                continue;
            }
            
            visited[u] = true;
            System.out.printf("\nStep %d: Process Vertex %d (Distance: %.2f)\n", 
                            step++, u, distances[u]);
            
            for (Edge edge : graph.getAdjacentEdges(u)) {
                int v = edge.destination;
                double newDist = distances[u] + edge.weight;
                
                System.out.printf("  Edge %d→%d (weight=%.2f): ", 
                                u, v, edge.weight);
                
                if (newDist < distances[v]) {
                    System.out.printf("Update distance to %d: %.2f → %.2f\n", 
                                    v, distances[v], newDist);
                    distances[v] = newDist;
                    previousVertices[v] = u;
                    pq.offer(new VertexDistance(v, newDist));
                } else {
                    System.out.println("No update (current distance is better)");
                }
            }
        }
        
        return new DijkstraResult(distances, previousVertices, visited);
    }
}
```

### Specialized Implementations

```java
/**
 * Single-pair shortest path (faster for finding path between two specific vertices)
 */
public class DijkstraSinglePair {
    
    public static DijkstraResult dijkstraUntilDestination(
            WeightedGraph graph, int source, int destination) {
        int n = graph.getVertexCount();
        
        double[] distances = new double[n];
        int[] previousVertices = new int[n];
        boolean[] visited = new boolean[n];
        
        for (int i = 0; i < n; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
            previousVertices[i] = -1;
        }
        distances[source] = 0;
        
        PriorityQueue<VertexDistance> pq = new PriorityQueue<>();
        pq.offer(new VertexDistance(source, 0));
        
        while (!pq.isEmpty()) {
            VertexDistance current = pq.poll();
            int u = current.vertex;
            
            // Early termination when destination is reached
            if (u == destination) {
                visited[u] = true;
                break;
            }
            
            if (visited[u]) {
                continue;
            }
            
            visited[u] = true;
            
            for (Edge edge : graph.getAdjacentEdges(u)) {
                int v = edge.destination;
                double newDist = distances[u] + edge.weight;
                
                if (newDist < distances[v]) {
                    distances[v] = newDist;
                    previousVertices[v] = u;
                    pq.offer(new VertexDistance(v, newDist));
                }
            }
        }
        
        return new DijkstraResult(distances, previousVertices, visited);
    }
}
```

### Graph Utility Class

```java
public class GraphUtils {
    
    /**
     * Create a sample graph for testing
     */
    public static WeightedGraph createSampleGraph() {
        WeightedGraph graph = new WeightedGraph(6);
        
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 7);
        graph.addEdge(2, 3, 2);
        graph.addEdge(2, 4, 5);
        graph.addEdge(3, 4, 1);
        graph.addEdge(3, 5, 3);
        graph.addEdge(4, 5, 2);
        
        return graph;
    }
    
    /**
     * Validate that distances are non-negative
     */
    public static boolean validateDistances(double[] distances) {
        for (double d : distances) {
            if (d < 0 && d != Double.POSITIVE_INFINITY) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Validate shortest path calculation
     */
    public static boolean validatePath(WeightedGraph graph, 
                                       DijkstraResult result) {
        return validateDistances(result.distances);
    }
}
```

### Complete Test Driver

```java
public class DijkstraDemo {
    
    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("Dijkstra's Algorithm");
        System.out.println("===================================");
        
        // Test Case 1: Basic graph with single source
        testCase1();
        
        // Test Case 2: Larger graph
        testCase2();
        
        // Test Case 3: Single-pair shortest path
        testCase3();
        
        // Test Case 4: Disconnected vertices
        testCase4();
        
        // Test Case 5: Dense graph comparison
        testCase5();
    }
    
    private static void testCase1() {
        System.out.println("\n--- Test Case 1: Basic Graph ---");
        WeightedGraph graph = GraphUtils.createSampleGraph();
        DijkstraResult result = DijkstraAlgorithm.dijkstraDetailed(graph, 0);
        result.printDistances();
        result.printPaths(0);
    }
    
    private static void testCase2() {
        System.out.println("\n--- Test Case 2: Small Graph with Detailed Steps ---");
        WeightedGraph graph = new WeightedGraph(4);
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 5);
        graph.addEdge(1, 2, 3);
        graph.addEdge(1, 3, 7);
        graph.addEdge(2, 3, 1);
        
        DijkstraResult result = DijkstraAlgorithm.dijkstraDetailed(graph, 0);
        result.printDistances();
        result.printPaths(0);
    }
    
    private static void testCase3() {
        System.out.println("\n--- Test Case 3: Single-Pair Shortest Path ---");
        WeightedGraph graph = new WeightedGraph(5);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 8);
        graph.addEdge(2, 4, 10);
        graph.addEdge(3, 4, 2);
        
        System.out.println("Finding shortest path from 0 to 4...");
        DijkstraResult result = DijkstraSinglePair.dijkstraUntilDestination(graph, 0, 4);
        result.printPaths(0);
    }
    
    private static void testCase4() {
        System.out.println("\n--- Test Case 4: Disconnected Graph ---");
        WeightedGraph graph = new WeightedGraph(5);
        // Component 1
        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 2, 3);
        // Component 2 (disconnected)
        graph.addEdge(3, 4, 5);
        
        DijkstraResult result = DijkstraAlgorithm.dijkstraWithHeap(graph, 0);
        result.printDistances();
    }
    
    private static void testCase5() {
        System.out.println("\n--- Test Case 5: Algorithm Comparison ---");
        WeightedGraph graph = GraphUtils.createSampleGraph();
        
        System.out.println("\nUsing Priority Queue (O((V+E)logV)):");
        long start1 = System.nanoTime();
        DijkstraResult result1 = DijkstraAlgorithm.dijkstraWithHeap(graph, 0);
        long time1 = System.nanoTime() - start1;
        System.out.println("Time: " + time1 + " ns");
        result1.printDistances();
        
        System.out.println("\nUsing Array (O(V²)):");
        long start2 = System.nanoTime();
        DijkstraResult result2 = DijkstraAlgorithm.dijkstraWithArray(graph, 0);
        long time2 = System.nanoTime() - start2;
        System.out.println("Time: " + time2 + " ns");
        result2.printDistances();
    }
}
```

## Complexity Analysis

### Time Complexity
- **With Min-Heap (Binary Heap)**: **O((V + E) log V)**
  - Extract-min: O(log V) × V times
  - Decrease-key: O(log V) × E times
  - Optimal for sparse graphs

- **With Array**: **O(V²)**
  - Find minimum: O(V) × V times
  - Better for dense graphs (E ≈ V²)

- **With Fibonacci Heap**: **O(E + V log V)**
  - Theoretical optimum but impractical

### Space Complexity
- **Distance Array**: O(V)
- **Previous Vertex Array**: O(V)
- **Priority Queue**: O(V)
- **Overall**: **O(V)**

## Advantages and Disadvantages

### Advantages
✓ Optimal solution guaranteed (greedy correctness proven)
✓ Efficient implementation with priority queue
✓ Handles dense and sparse graphs well
✓ Can find all-pairs shortest paths by running once from each vertex
✓ Widely used in real-world applications (GPS, networks)

### Disadvantages
✗ Requires non-negative edge weights
✗ Not suitable for dynamic graphs (needs recomputation)
✗ More complex than BFS for unweighted graphs
✗ Priority queue overhead for dense graphs

## Key Insights

1. **Greedy Correctness**: Proven by exchange argument - once a vertex's shortest distance is finalized, it cannot improve
2. **Non-Negative Weights**: Critical constraint - negative weights break the greedy correctness
3. **Data Structure Matters**: Choice of priority queue significantly impacts performance
4. **Single-Source**: Finds shortest paths from source to all other vertices
5. **Path Reconstruction**: Previous vertex array enables path reconstruction

## Comparison with Other Algorithms

| Algorithm | Time | Negative Weights | Use Case |
|-----------|------|-----------------|----------|
| Dijkstra | O((V+E)logV) | No | Non-negative weights |
| Bellman-Ford | O(VE) | Yes | Negative weights |
| BFS | O(V+E) | No | Unweighted graphs |
| A* | O((V+E)logV) | No | With heuristic |
| Floyd-Warshall | O(V³) | Yes | All-pairs shortest path |

## Related Problems

- **Bellman-Ford Algorithm**: Handles negative weights
- **A* Search**: Heuristic-based path finding
- **Prim's Algorithm**: Similar greedy approach for MST
- **Floyd-Warshall**: All-pairs shortest path
- **Topological Sort + DP**: For DAGs

## Applications

1. **GPS Navigation**: Finding shortest route
2. **Network Routing**: OSPF protocol uses Dijkstra
3. **Telephone Networks**: Path finding with minimum delay
4. **Game Development**: AI pathfinding
5. **Social Networks**: Finding closest users

## References

- "A Note on Two Problems in Connexion with Graphs" - Edsger W. Dijkstra (1959)
- Introduction to Algorithms (CLRS) - Single-Source Shortest Paths
- Algorithm Design Manual - Graph Problems
- https://en.wikipedia.org/wiki/Dijkstra's_algorithm
