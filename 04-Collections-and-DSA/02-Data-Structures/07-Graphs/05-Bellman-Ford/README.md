# Bellman-Ford Algorithm - Shortest Path with Negative Weights

## Overview

The Bellman-Ford algorithm is a single-source shortest path algorithm that, unlike Dijkstra's, can handle graphs with negative edge weights. Developed by Richard Bellman and Lester Ford in the late 1950s, it's essential for scenarios where costs can be negative (profit, loss, etc.). Importantly, it can detect negative cycles, a critical feature for many real-world applications.

## Algorithm Explanation

Bellman-Ford solves the **single-source shortest path problem** in a weighted directed graph where edge weights can be negative. The algorithm works by relaxing all edges repeatedly, gradually improving distance estimates until convergence. It also detects negative-weight cycles, which make shortest paths undefined.

### Key Concepts

- **Edge Relaxation**: Updating shortest distance estimate if a better path is found
- **Iteration Process**: Repeat relaxation V-1 times to guarantee convergence
- **Negative Cycle Detection**: Check if further improvements possible after V-1 iterations
- **Robustness**: Works correctly regardless of weight sign or graph density

## How Bellman-Ford Algorithm Works

1. **Initialize** distances to all vertices as infinity, except source = 0
2. **Relax all edges V-1 times**:
   - For each edge (u, v) with weight w:
     - If distance[u] + w < distance[v]:
       - Update distance[v]
       - Update parent[v]
3. **Check for negative cycles**:
   - Iterate through all edges once more
   - If any edge can still be relaxed, negative cycle exists
4. **Return** shortest distances, paths, and cycle status

### Pseudocode

```
function bellmanFord(graph, source):
    distance = array of size V, initialized to INFINITY
    parent = array of size V, initialized to -1
    distance[source] = 0
    
    edges = getAllEdges(graph)
    
    // Relax edges V-1 times
    for i = 1 to V-1:
        for each edge (u, v, weight) in edges:
            if distance[u] != INFINITY and distance[u] + weight < distance[v]:
                distance[v] = distance[u] + weight
                parent[v] = u
    
    // Check for negative cycles
    for each edge (u, v, weight) in edges:
        if distance[u] != INFINITY and distance[u] + weight < distance[v]:
            return "Negative cycle detected"
    
    return distance, parent
```

## Complete Java Implementation

```java
import java.util.*;

public class BellmanFordAlgorithm {
    
    // Inner class to represent edge
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
    
    // Graph representation using edge list
    static class Graph {
        int vertices;
        List<Edge> edges;
        
        Graph(int vertices) {
            this.vertices = vertices;
            edges = new ArrayList<>();
        }
        
        // Add weighted edge
        void addEdge(int source, int destination, int weight) {
            edges.add(new Edge(source, destination, weight));
        }
        
        // Result class to hold algorithm output
        static class BellmanFordResult {
            int[] distance;
            int[] parent;
            boolean hasNegativeCycle;
            
            BellmanFordResult(int[] distance, int[] parent, boolean hasNegativeCycle) {
                this.distance = distance;
                this.parent = parent;
                this.hasNegativeCycle = hasNegativeCycle;
            }
        }
        
        // Bellman-Ford algorithm implementation
        BellmanFordResult bellmanFord(int source) {
            // Initialize distances and parent
            int[] distance = new int[vertices];
            int[] parent = new int[vertices];
            
            Arrays.fill(distance, Integer.MAX_VALUE);
            Arrays.fill(parent, -1);
            distance[source] = 0;
            
            // Relax all edges vertices-1 times
            for (int i = 0; i < vertices - 1; i++) {
                for (Edge edge : edges) {
                    // Relax edge if valid path exists
                    if (distance[edge.source] != Integer.MAX_VALUE &&
                        distance[edge.source] + edge.weight < distance[edge.destination]) {
                        distance[edge.destination] = distance[edge.source] + edge.weight;
                        parent[edge.destination] = edge.source;
                    }
                }
            }
            
            // Check for negative-weight cycle
            boolean hasNegativeCycle = false;
            for (Edge edge : edges) {
                if (distance[edge.source] != Integer.MAX_VALUE &&
                    distance[edge.source] + edge.weight < distance[edge.destination]) {
                    hasNegativeCycle = true;
                    break;
                }
            }
            
            return new BellmanFordResult(distance, parent, hasNegativeCycle);
        }
        
        // Print results
        void printSolution(BellmanFordResult result, int source) {
            System.out.println("\n=== Bellman-Ford Algorithm Results ===");
            System.out.println("Source: " + source);
            
            if (result.hasNegativeCycle) {
                System.out.println("\n⚠️  NEGATIVE CYCLE DETECTED!");
                System.out.println("Shortest paths are undefined due to negative cycle.");
                return;
            }
            
            System.out.println("\nVertex\tDistance\tPath");
            System.out.println("------\t--------\t----");
            
            for (int i = 0; i < vertices; i++) {
                System.out.print(i + "\t");
                if (result.distance[i] == Integer.MAX_VALUE) {
                    System.out.print("INFINITY\t");
                } else {
                    System.out.print(result.distance[i] + "\t\t");
                }
                
                if (i == source) {
                    System.out.println(source);
                } else if (result.distance[i] == Integer.MAX_VALUE) {
                    System.out.println("No path");
                } else {
                    System.out.println(reconstructPath(result.parent, i, source));
                }
            }
        }
        
        // Reconstruct path
        String reconstructPath(int[] parent, int destination, int source) {
            List<Integer> path = new ArrayList<>();
            int current = destination;
            
            while (current != -1) {
                path.add(0, current);
                current = parent[current];
            }
            
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < path.size(); i++) {
                if (i > 0) sb.append(" -> ");
                sb.append(path.get(i));
            }
            return sb.toString();
        }
    }
    
    public static void main(String[] args) {
        // Example 1: Graph with negative weights but no negative cycle
        System.out.println("=== Example 1: Graph with Negative Weights ===");
        Graph graph1 = new Graph(5);
        graph1.addEdge(0, 1, -1);
        graph1.addEdge(0, 2, 4);
        graph1.addEdge(1, 2, 3);
        graph1.addEdge(1, 3, 2);
        graph1.addEdge(1, 4, 2);
        graph1.addEdge(3, 2, 5);
        graph1.addEdge(3, 1, 1);
        graph1.addEdge(4, 3, -3);
        
        Graph.BellmanFordResult result1 = graph1.bellmanFord(0);
        graph1.printSolution(result1, 0);
        
        // Example 2: Detecting negative cycle
        System.out.println("\n\n=== Example 2: Negative Cycle Detection ===");
        Graph graph2 = new Graph(4);
        graph2.addEdge(0, 1, 1);
        graph2.addEdge(1, 2, -3);
        graph2.addEdge(2, 3, 2);
        graph2.addEdge(3, 1, -1); // Creates negative cycle: 1->2->3->1 = -1-3+2 = -2
        
        Graph.BellmanFordResult result2 = graph2.bellmanFord(0);
        graph2.printSolution(result2, 0);
        
        // Example 3: Arbitrage detection (financial application)
        System.out.println("\n\n=== Example 3: Currency Arbitrage Detection ===");
        // Vertices: 0=USD, 1=EUR, 2=GBP, 3=JPY
        Graph graph3 = new Graph(4);
        graph3.addEdge(0, 1, -1); // USD to EUR: rate = e^-1
        graph3.addEdge(1, 2, -2); // EUR to GBP
        graph3.addEdge(2, 3, 1);  // GBP to JPY
        graph3.addEdge(3, 0, -1); // JPY to USD
        
        Graph.BellmanFordResult result3 = graph3.bellmanFord(0);
        graph3.printSolution(result3, 0);
        
        // Example 4: Network delay/cost analysis
        System.out.println("\n\n=== Example 4: Network Cost Analysis ===");
        Graph graph4 = new Graph(6);
        graph4.addEdge(0, 1, 6);
        graph4.addEdge(0, 2, 7);
        graph4.addEdge(1, 2, 8);
        graph4.addEdge(1, 3, -4);
        graph4.addEdge(2, 3, 9);
        graph4.addEdge(2, 4, -2);
        graph4.addEdge(3, 4, 7);
        graph4.addEdge(3, 5, 2);
        graph4.addEdge(4, 5, 10);
        graph4.addEdge(4, 1, -5);
        
        Graph.BellmanFordResult result4 = graph4.bellmanFord(0);
        graph4.printSolution(result4, 0);
    }
}
```

## Examples and Use Cases

### Example 1: Network Routing with Cost
Finding optimal routes where costs include both delays and potential refunds (negative weights).

### Example 2: Currency Exchange Arbitrage
Detecting arbitrage opportunities by identifying negative cycles in exchange rate graphs.

### Example 3: Job Sequencing with Deadlines
Computing optimal scheduling with penalty costs (negative weights for missed deadlines).

### Example 4: Game Theory
Calculating optimal strategies with negative payoffs for certain moves.

## Edge Cases

1. **Negative Cycles**: Algorithm detects and reports them
2. **Disconnected Vertices**: Show infinite distance
3. **Self-loops with Negative Weight**: Detected as negative cycle if accessible
4. **Large Negative Weights**: Must use `Integer.MAX_VALUE` carefully
5. **Zero-weight Cycles**: Don't affect correctness but may indicate degeneracy

## Time and Space Complexity

| Aspect | Complexity | Notes |
|---|---|---|
| Time | O(VE) | Where V = vertices, E = edges |
| Space | O(V) | For distance and parent arrays |
| Relaxation Passes | V-1 | Plus 1 for cycle detection |
| Worst Case | O(V³) | When E = O(V²) in dense graphs |

## When to Use Bellman-Ford Algorithm

- ✅ Negative edge weights present
- ✅ Need to detect negative cycles
- ✅ Single-source shortest paths
- ✅ Small to medium-sized graphs
- ❌ Large graphs (too slow, O(VE))
- ❌ Only non-negative weights (use Dijkstra's)
- ❌ All-pairs shortest paths (use Floyd-Warshall)

## Real-World Applications

1. **Currency Exchange**: Detecting arbitrage opportunities in forex markets
2. **Network Routing**: EIGRP protocol uses Bellman-Ford concept
3. **Job Scheduling**: Optimal job ordering with penalties
4. **Robotics**: Path planning with both costs and penalties
5. **Financial Portfolio**: Computing optimal investment paths
6. **Game AI**: Computing optimal play strategies with negative consequences
7. **Logistics**: Route optimization with surcharges/discounts

## Comparison with Other Algorithms

| Algorithm | Time | Negative Weights | Negative Cycle Detection | Best For |
|---|---|---|---|---|
| **Dijkstra** | O((V+E)log V) | ❌ | ❌ | Non-negative weights |
| **Bellman-Ford** | O(VE) | ✅ | ✅ | Negative weights, cycle detection |
| **Floyd-Warshall** | O(V³) | ✅ | ✅ | All-pairs, small graphs |
| **SPFA** | O(E) avg | ✅ | ✅ | Sparse graphs, practical |

## Key Takeaways

- **Bellman-Ford is essential** when negative weights are present
- **V-1 iterations** guarantee convergence for acyclic graphs
- **Negative cycle detection** is crucial for many financial applications
- **Slower than Dijkstra's** but more versatile
- **SPFA variant** provides better practical performance on sparse graphs
- Understanding this algorithm is vital for advanced graph problems and competitive programming
