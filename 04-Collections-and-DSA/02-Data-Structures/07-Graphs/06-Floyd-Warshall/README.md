# Floyd-Warshall Algorithm - All-Pairs Shortest Paths

## Overview

The Floyd-Warshall algorithm computes shortest paths between **all pairs of vertices** in a weighted directed graph, handling both positive and negative edge weights (but not negative cycles). Developed by Robert Floyd in 1962 based on earlier work by Stephen Warshall, this dynamic programming masterpiece is fundamental for solving all-pairs shortest path problems and has numerous practical applications in optimization, network analysis, and game theory.

## Algorithm Explanation

Floyd-Warshall solves the **all-pairs shortest path problem** using dynamic programming. It builds up solutions by considering paths through intermediate vertices progressively. The algorithm's elegance lies in its simple, yet powerful DP approach: for each potential intermediate vertex k, it checks if routing through k provides a shorter path between any pair of vertices (i, j).

### Key Concepts

- **Dynamic Programming**: Building solutions by considering intermediate vertices
- **Relaxation**: Updating shortest paths by considering new intermediate vertices
- **Three-nested Loop**: O(V³) iterations over source, destination, and intermediate vertices
- **Adjacency Matrix**: Direct representation of graph distances
- **Negative Cycle Detection**: Checking diagonal for negative values

## How Floyd-Warshall Algorithm Works

1. **Initialize** distance matrix: Set direct edges to their weights, all others to infinity
2. **For each intermediate vertex k** from 0 to V-1:
   - **For each pair of vertices (i, j)**:
     - **If path through k is shorter**:
       - Update distance[i][j] = min(distance[i][j], distance[i][k] + distance[k][j])
3. **Check for negative cycles**: Any diagonal element < 0 indicates negative cycle
4. **Return** final distance matrix (and next matrix for path reconstruction)

### Pseudocode

```
function floydWarshall(graph):
    V = number of vertices
    distance = adjacency matrix (V x V)
    next = array to store next vertex in path (V x V)
    
    // Initialize
    for i = 0 to V-1:
        for j = 0 to V-1:
            if distance[i][j] is not INFINITY:
                next[i][j] = j
            else:
                next[i][j] = -1
    
    // Main DP: try all intermediate vertices
    for k = 0 to V-1:
        for i = 0 to V-1:
            for j = 0 to V-1:
                if distance[i][k] + distance[k][j] < distance[i][j]:
                    distance[i][j] = distance[i][k] + distance[k][j]
                    next[i][j] = next[i][k]
    
    // Check for negative cycles
    for i = 0 to V-1:
        if distance[i][i] < 0:
            return "Negative cycle detected"
    
    return distance, next
```

## Complete Java Implementation

```java
import java.util.*;

public class FloydWarshallAlgorithm {
    
    // Result class containing distance and next matrices
    static class FloydWarshallResult {
        int[][] distance;
        int[][] next;
        boolean hasNegativeCycle;
        
        FloydWarshallResult(int[][] distance, int[][] next, boolean hasNegativeCycle) {
            this.distance = distance;
            this.next = next;
            this.hasNegativeCycle = hasNegativeCycle;
        }
    }
    
    // Graph class using adjacency matrix representation
    static class Graph {
        int vertices;
        int[][] adjacencyMatrix;
        
        Graph(int vertices) {
            this.vertices = vertices;
            adjacencyMatrix = new int[vertices][vertices];
            
            // Initialize with infinity (using Integer.MAX_VALUE/2 to avoid overflow)
            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    if (i == j) {
                        adjacencyMatrix[i][j] = 0; // Distance to self is 0
                    } else {
                        adjacencyMatrix[i][j] = Integer.MAX_VALUE / 2;
                    }
                }
            }
        }
        
        // Add weighted edge
        void addEdge(int source, int destination, int weight) {
            adjacencyMatrix[source][destination] = weight;
        }
        
        // Floyd-Warshall algorithm implementation
        FloydWarshallResult floydWarshall() {
            // Create copies of adjacency matrix for distance and next matrices
            int[][] distance = new int[vertices][vertices];
            int[][] next = new int[vertices][vertices];
            
            // Initialize
            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    distance[i][j] = adjacencyMatrix[i][j];
                    if (adjacencyMatrix[i][j] != Integer.MAX_VALUE / 2 && i != j) {
                        next[i][j] = j;
                    } else {
                        next[i][j] = -1;
                    }
                }
            }
            
            // Floyd-Warshall: try each vertex as intermediate
            for (int k = 0; k < vertices; k++) {
                for (int i = 0; i < vertices; i++) {
                    for (int j = 0; j < vertices; j++) {
                        // Check if path through k is shorter
                        if (distance[i][k] != Integer.MAX_VALUE / 2 &&
                            distance[k][j] != Integer.MAX_VALUE / 2 &&
                            distance[i][k] + distance[k][j] < distance[i][j]) {
                            distance[i][j] = distance[i][k] + distance[k][j];
                            next[i][j] = next[i][k];
                        }
                    }
                }
            }
            
            // Check for negative cycles
            boolean hasNegativeCycle = false;
            for (int i = 0; i < vertices; i++) {
                if (distance[i][i] < 0) {
                    hasNegativeCycle = true;
                    break;
                }
            }
            
            return new FloydWarshallResult(distance, next, hasNegativeCycle);
        }
        
        // Reconstruct path between two vertices
        List<Integer> reconstructPath(int[][] next, int start, int end) {
            List<Integer> path = new ArrayList<>();
            
            if (next[start][end] == -1) {
                return path; // No path exists
            }
            
            path.add(start);
            int current = start;
            
            while (current != end) {
                current = next[current][end];
                path.add(current);
            }
            
            return path;
        }
        
        // Print all-pairs shortest paths
        void printAllShortestPaths(FloydWarshallResult result) {
            System.out.println("\n=== All-Pairs Shortest Paths (Floyd-Warshall) ===");
            
            if (result.hasNegativeCycle) {
                System.out.println("⚠️  NEGATIVE CYCLE DETECTED!");
                System.out.println("Shortest paths are undefined.");
                return;
            }
            
            // Print distance matrix
            System.out.println("\nDistance Matrix:");
            System.out.print("    ");
            for (int j = 0; j < vertices; j++) {
                System.out.printf("%5d ", j);
            }
            System.out.println();
            
            for (int i = 0; i < vertices; i++) {
                System.out.printf("%2d: ", i);
                for (int j = 0; j < vertices; j++) {
                    if (result.distance[i][j] == Integer.MAX_VALUE / 2) {
                        System.out.print("  INF ");
                    } else {
                        System.out.printf("%5d ", result.distance[i][j]);
                    }
                }
                System.out.println();
            }
            
            // Print selected paths
            System.out.println("\nShortest Paths (sample):");
            for (int i = 0; i < Math.min(3, vertices); i++) {
                for (int j = 0; j < Math.min(3, vertices); j++) {
                    if (i != j) {
                        List<Integer> path = reconstructPath(result.next, i, j);
                        System.out.print(i + " -> " + j + ": ");
                        if (path.isEmpty()) {
                            System.out.println("No path");
                        } else {
                            System.out.println(path + " (cost: " + result.distance[i][j] + ")");
                        }
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) {
        // Example 1: Simple graph
        System.out.println("=== Example 1: Simple Weighted Graph ===");
        Graph graph1 = new Graph(4);
        graph1.addEdge(0, 1, 3);
        graph1.addEdge(0, 3, 7);
        graph1.addEdge(1, 2, 1);
        graph1.addEdge(1, 0, 8);
        graph1.addEdge(2, 0, 4);
        graph1.addEdge(2, 3, 2);
        graph1.addEdge(3, 2, 1);
        
        FloydWarshallResult result1 = graph1.floydWarshall();
        graph1.printAllShortestPaths(result1);
        
        // Example 2: Graph with negative weights
        System.out.println("\n\n=== Example 2: Negative Weights (No Negative Cycle) ===");
        Graph graph2 = new Graph(5);
        graph2.addEdge(0, 1, -1);
        graph2.addEdge(0, 3, 4);
        graph2.addEdge(1, 2, 3);
        graph2.addEdge(2, 3, 5);
        graph2.addEdge(3, 1, 1);
        graph2.addEdge(3, 4, 2);
        
        FloydWarshallResult result2 = graph2.floydWarshall();
        graph2.printAllShortestPaths(result2);
        
        // Example 3: Detecting negative cycle
        System.out.println("\n\n=== Example 3: Negative Cycle Detection ===");
        Graph graph3 = new Graph(3);
        graph3.addEdge(0, 1, 1);
        graph3.addEdge(1, 2, -3);
        graph3.addEdge(2, 0, 1); // Creates cycle: 0->1->2->0 = 1-3+1 = -1
        
        FloydWarshallResult result3 = graph3.floydWarshall();
        graph3.printAllShortestPaths(result3);
        
        // Example 4: Network hop count analysis
        System.out.println("\n\n=== Example 4: Network Hop Count Optimization ===");
        Graph graph4 = new Graph(6);
        graph4.addEdge(0, 1, 1);
        graph4.addEdge(0, 2, 4);
        graph4.addEdge(1, 2, 2);
        graph4.addEdge(1, 3, 1);
        graph4.addEdge(2, 3, 5);
        graph4.addEdge(3, 4, 3);
        graph4.addEdge(3, 5, 6);
        graph4.addEdge(4, 5, 1);
        
        FloydWarshallResult result4 = graph4.floydWarshall();
        graph4.printAllShortestPaths(result4);
        
        // Example 5: Complete graph comparison
        System.out.println("\n\n=== Example 5: Complete Graph (Traveling Salesman) ===");
        Graph graph5 = new Graph(4);
        // Fully connected graph
        int[][] costs = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i != j) {
                    graph5.addEdge(i, j, costs[i][j]);
                }
            }
        }
        
        FloydWarshallResult result5 = graph5.floydWarshall();
        graph5.printAllShortestPaths(result5);
    }
}
```

## Examples and Use Cases

### Example 1: Network Topology
Computing minimum-hop or minimum-cost paths between all routers in a network.

### Example 2: Social Networks
Finding degrees of separation between all pairs of users.

### Example 3: Traveling Salesman Problem
Computing shortest routes through all cities for route optimization.

### Example 4: Game Maps
Precomputing distances between all level locations for intelligent NPC pathfinding.

### Example 5: Airport Networks
Computing cheapest flight routes between all airport pairs.

## Edge Cases

1. **Negative Cycles**: Diagonal matrix elements become negative
2. **Disconnected Components**: Unreachable vertices remain at infinity
3. **Single Vertex**: Returns [0] as distance matrix
4. **Dense vs. Sparse**: Performs equally well regardless (O(V³) always)
5. **Large Weights**: Use `Integer.MAX_VALUE/2` to prevent overflow in addition

## Time and Space Complexity

| Aspect | Complexity | Notes |
|---|---|---|
| **Time** | O(V³) | Three nested loops over vertices |
| **Space** | O(V²) | Distance and next matrices |
| **Initialization** | O(V²) | Matrix setup |
| **Negative Cycle Check** | O(V) | Diagonal iteration |

Regardless of edge count: O(V³) whether sparse (E=V) or dense (E=V²)

## When to Use Floyd-Warshall Algorithm

- ✅ **All-pairs shortest paths** required
- ✅ Negative edge weights present (without negative cycles)
- ✅ Negative cycle detection needed
- ✅ Small to medium graphs (V ≤ 500)
- ✅ Densely connected graphs
- ✅ Need simple, elegant solution
- ❌ Large graphs (V > 1000), too slow
- ❌ Single-source paths only (use Dijkstra's/Bellman-Ford)
- ❌ Sparse graphs with non-negative weights (use multiple Dijkstra's)

## Real-World Applications

1. **Network Routing Protocols**: Computing routing tables with all-pairs information
2. **Microservices Mesh**: Discovering optimal communication paths between services
3. **Game Development**: Precomputing NPC navigation distances for multiple locations
4. **Social Networks**: Computing degrees of separation between all users
5. **Airline Networks**: Computing minimum-cost routes between all airport pairs
6. **Supply Chain Optimization**: Finding optimal routing through distribution networks
7. **Telecommunications**: Network planning with all-pairs latency analysis

## Comparison with Other Algorithms

| Algorithm | Time | Space | All-Pairs | Negative | Best For |
|---|---|---|---|---|---|
| **Floyd-Warshall** | O(V³) | O(V²) | ✅ Yes | ✅ Yes | All-pairs, negative weights |
| **Dijkstra × V** | O(V(V+E)log V) | O(V+E) | ✅ Yes | ❌ No | All-pairs, non-negative |
| **Bellman-Ford × V** | O(V²E) | O(V) | ✅ Yes | ✅ Yes | All-pairs, very sparse |
| **Johnson's** | O(V²log V + VE) | O(V+E) | ✅ Yes | ✅ Yes | All-pairs, sparse |

## Key Takeaways

- **Floyd-Warshall is the go-to solution** for all-pairs shortest paths problems
- **Dynamic programming approach** builds solutions incrementally through intermediate vertices
- **O(V³) time complexity** is acceptable for moderate-sized graphs (V < 500)
- **Handles negative weights and detects negative cycles** - very versatile
- **Simple implementation** with elegant three-nested-loop structure
- **Adjacency matrix representation** is most natural for this algorithm
- Mastering this algorithm is essential for advanced graph problems and competitive programming interviews
