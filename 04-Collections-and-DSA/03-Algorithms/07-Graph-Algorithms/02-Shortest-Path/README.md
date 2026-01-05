# Shortest Path Algorithms

## Introduction

Shortest path algorithms are fundamental in computer science and are used to find the minimum distance or cost between nodes in a graph. These algorithms are essential in real-world applications like GPS navigation, network routing, social network analysis, and game development. Understanding when and how to use each algorithm is crucial for optimizing performance and correctness.

## Algorithm Overview

### 1. Dijkstra's Algorithm

**Concept**: Dijkstra's algorithm finds the shortest path from a source node to all other nodes in a weighted graph with non-negative edge weights using a greedy approach.

**Key Characteristics**:
- Works only with non-negative weights
- Time Complexity: O((V + E) log V) with min-heap
- Space Complexity: O(V)
- Greedy algorithm - always picks the nearest unvisited node

**How It Works**:
1. Initialize distances to all nodes as infinity, except source (0)
2. Use a priority queue to always process the node with minimum distance
3. For each node, update distances to its neighbors if a shorter path is found
4. Continue until all nodes are visited or queue is empty

### 2. Bellman-Ford Algorithm

**Concept**: Bellman-Ford finds shortest paths from a source node and can handle negative edge weights. It also detects negative cycles.

**Key Characteristics**:
- Handles negative weights (unlike Dijkstra)
- Detects negative cycles
- Time Complexity: O(V × E)
- Space Complexity: O(V)
- More flexible but slower than Dijkstra

**How It Works**:
1. Initialize distances to all nodes as infinity, except source (0)
2. Relax all edges V-1 times (update distances if shorter path found)
3. Check for negative cycles by attempting one more relaxation
4. If any distance updates in step 3, a negative cycle exists

### 3. Floyd-Warshall Algorithm

**Concept**: Floyd-Warshall is an all-pairs shortest path algorithm that finds shortest paths between all pairs of vertices.

**Key Characteristics**:
- Solves all-pairs shortest path problem
- Handles negative weights (but not negative cycles)
- Time Complexity: O(V³)
- Space Complexity: O(V²)
- Uses dynamic programming

**How It Works**:
1. Initialize distance matrix with direct edge weights
2. For each intermediate vertex k, update distances by checking if path through k is shorter
3. Distance[i][j] = min(Distance[i][j], Distance[i][k] + Distance[k][j])
4. Continue for all vertices as intermediate points

### 4. A* Algorithm Basics

**Concept**: A* is an informed search algorithm that combines Dijkstra with heuristic guidance for pathfinding in games and navigation.

**Key Characteristics**:
- f(n) = g(n) + h(n), where g(n) is cost from start, h(n) is heuristic estimate
- More efficient than Dijkstra when heuristic is good
- Time Complexity: Depends on heuristic, typically O(E) in practice
- Requires heuristic function (e.g., Euclidean distance)
- Used extensively in game AI and GPS

## Comparison Table

| Feature | Dijkstra | Bellman-Ford | Floyd-Warshall | A* |
|---------|----------|--------------|-----------------|-----|
| Negative Weights | ❌ No | ✅ Yes | ✅ Yes | ❌ No |
| Negative Cycle | ❌ | ✅ Detects | ❌ | ❌ |
| Single Source | ✅ Yes | ✅ Yes | ❌ All-pairs | ✅ Yes |
| Time Complexity | O((V+E)logV) | O(VE) | O(V³) | O(E) (with good heuristic) |
| Best For | Non-negative graphs | Negative weights | All-pairs problem | Informed search |

## Complete Java Implementation

### Graph Representation Classes

```java
import java.util.*;

// Edge class for representing connections
class Edge {
    int destination;
    int weight;
    
    public Edge(int destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }
}

// Node class for Dijkstra with comparable
class Node implements Comparable<Node> {
    int vertex;
    int distance;
    
    public Node(int vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }
    
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.distance, other.distance);
    }
}

// Graph representation using adjacency list
class ShortestPathGraph {
    private int vertices;
    private List<List<Edge>> adjacencyList;
    private final int INF = Integer.MAX_VALUE / 2; // Avoid overflow
    
    public ShortestPathGraph(int vertices) {
        this.vertices = vertices;
        this.adjacencyList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }
    
    public void addEdge(int source, int destination, int weight) {
        adjacencyList.get(source).add(new Edge(destination, weight));
    }
    
    public void addUndirectedEdge(int u, int v, int weight) {
        adjacencyList.get(u).add(new Edge(v, weight));
        adjacencyList.get(v).add(new Edge(u, weight));
    }
    
    // Dijkstra's Algorithm
    public int[] dijkstra(int source) {
        int[] distances = new int[vertices];
        Arrays.fill(distances, INF);
        distances[source] = 0;
        
        PriorityQueue<Node> minHeap = new PriorityQueue<>();
        minHeap.offer(new Node(source, 0));
        boolean[] visited = new boolean[vertices];
        
        while (!minHeap.isEmpty()) {
            Node current = minHeap.poll();
            
            if (visited[current.vertex]) continue;
            visited[current.vertex] = true;
            
            // Explore all neighbors
            for (Edge edge : adjacencyList.get(current.vertex)) {
                int newDistance = distances[current.vertex] + edge.weight;
                
                // Relaxation: update if shorter path found
                if (newDistance < distances[edge.destination]) {
                    distances[edge.destination] = newDistance;
                    minHeap.offer(new Node(edge.destination, newDistance));
                }
            }
        }
        
        return distances;
    }
    
    // Bellman-Ford Algorithm
    public int[] bellmanFord(int source) {
        int[] distances = new int[vertices];
        Arrays.fill(distances, INF);
        distances[source] = 0;
        
        // Relax all edges V-1 times
        for (int i = 0; i < vertices - 1; i++) {
            for (int u = 0; u < vertices; u++) {
                if (distances[u] == INF) continue;
                
                for (Edge edge : adjacencyList.get(u)) {
                    int newDistance = distances[u] + edge.weight;
                    if (newDistance < distances[edge.destination]) {
                        distances[edge.destination] = newDistance;
                    }
                }
            }
        }
        
        // Check for negative cycles
        for (int u = 0; u < vertices; u++) {
            if (distances[u] == INF) continue;
            
            for (Edge edge : adjacencyList.get(u)) {
                if (distances[u] + edge.weight < distances[edge.destination]) {
                    System.out.println("⚠️ Negative cycle detected!");
                    return null;
                }
            }
        }
        
        return distances;
    }
    
    // Floyd-Warshall Algorithm (All-Pairs Shortest Path)
    public int[][] floydWarshall() {
        int[][] distance = new int[vertices][vertices];
        
        // Initialize distances
        for (int i = 0; i < vertices; i++) {
            Arrays.fill(distance[i], INF);
            distance[i][i] = 0;
        }
        
        // Set direct edges
        for (int u = 0; u < vertices; u++) {
            for (Edge edge : adjacencyList.get(u)) {
                distance[u][edge.destination] = 
                    Math.min(distance[u][edge.destination], edge.weight);
            }
        }
        
        // Main DP: try all intermediate vertices
        for (int k = 0; k < vertices; k++) {
            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    if (distance[i][k] != INF && distance[k][j] != INF) {
                        distance[i][j] = Math.min(distance[i][j],
                            distance[i][k] + distance[k][j]);
                    }
                }
            }
        }
        
        return distance;
    }
    
    // A* Algorithm (Basic Implementation)
    public int[] aStar(int source, int destination, int[] heuristic) {
        int[] gScore = new int[vertices]; // Cost from start
        int[] fScore = new int[vertices]; // Estimated total cost
        Arrays.fill(gScore, INF);
        Arrays.fill(fScore, INF);
        gScore[source] = 0;
        fScore[source] = heuristic[source];
        
        PriorityQueue<Node> openSet = new PriorityQueue<>((a, b) ->
            Integer.compare(fScore[a.vertex], fScore[b.vertex]));
        openSet.offer(new Node(source, fScore[source]));
        boolean[] visited = new boolean[vertices];
        
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            
            if (current.vertex == destination) {
                System.out.println("✅ Destination reached!");
                return gScore;
            }
            
            if (visited[current.vertex]) continue;
            visited[current.vertex] = true;
            
            for (Edge edge : adjacencyList.get(current.vertex)) {
                int neighbor = edge.destination;
                int tentativeGScore = gScore[current.vertex] + edge.weight;
                
                if (tentativeGScore < gScore[neighbor]) {
                    gScore[neighbor] = tentativeGScore;
                    fScore[neighbor] = tentativeGScore + heuristic[neighbor];
                    openSet.offer(new Node(neighbor, fScore[neighbor]));
                }
            }
        }
        
        System.out.println("❌ Destination unreachable!");
        return gScore;
    }
    
    // Print distances from source
    public void printDistances(int[] distances, String algorithm) {
        System.out.println("\n" + algorithm + " Results:");
        System.out.println("=".repeat(50));
        for (int i = 0; i < distances.length; i++) {
            String distance = (distances[i] == INF) ? "∞ (Unreachable)" 
                                                     : String.valueOf(distances[i]);
            System.out.printf("Node %d: %s%n", i, distance);
        }
    }
    
    // Print all-pairs distances
    public void printAllPairsDistances(int[][] distances) {
        System.out.println("\nFloyd-Warshall All-Pairs Results:");
        System.out.println("=".repeat(60));
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances[i].length; j++) {
                String value = (distances[i][j] == INF) ? "∞" 
                                                        : String.valueOf(distances[i][j]);
                System.out.printf("%4s ", value);
            }
            System.out.println();
        }
    }
}
```

### Practical Example: GPS Navigation

```java
public class GPSNavigationExample {
    public static void main(String[] args) {
        // Create a graph representing cities and distances
        ShortestPathGraph graph = new ShortestPathGraph(6);
        
        // Cities: 0=Start, 1=CityA, 2=CityB, 3=CityC, 4=CityD, 5=Destination
        graph.addUndirectedEdge(0, 1, 4);  // Start to CityA: 4 km
        graph.addUndirectedEdge(0, 2, 2);  // Start to CityB: 2 km
        graph.addUndirectedEdge(1, 2, 1);  // CityA to CityB: 1 km
        graph.addUndirectedEdge(1, 3, 5);  // CityA to CityC: 5 km
        graph.addUndirectedEdge(2, 3, 8);  // CityB to CityC: 8 km
        graph.addUndirectedEdge(2, 4, 10); // CityB to CityD: 10 km
        graph.addUndirectedEdge(3, 4, 2);  // CityC to CityD: 2 km
        graph.addUndirectedEdge(3, 5, 6);  // CityC to Destination: 6 km
        graph.addUndirectedEdge(4, 5, 3);  // CityD to Destination: 3 km
        
        System.out.println("🗺️  GPS Navigation Example");
        int[] dijkstraResult = graph.dijkstra(0);
        graph.printDistances(dijkstraResult, "Dijkstra's Algorithm");
        System.out.println("\n✨ Shortest route to Destination: " + 
                          dijkstraResult[5] + " km");
    }
}
```

### Network Routing Example

```java
public class NetworkRoutingExample {
    public static void main(String[] args) {
        // Router network with latency (ms)
        ShortestPathGraph network = new ShortestPathGraph(5);
        
        // Routers: 0=Main, 1=Router1, 2=Router2, 3=Router3, 4=Target
        network.addEdge(0, 1, 5);   // Main to R1: 5ms
        network.addEdge(0, 2, 10);  // Main to R2: 10ms
        network.addEdge(1, 3, 3);   // R1 to R3: 3ms
        network.addEdge(2, 3, 2);   // R2 to R3: 2ms
        network.addEdge(1, 4, 20);  // R1 to Target: 20ms
        network.addEdge(3, 4, 1);   // R3 to Target: 1ms
        
        System.out.println("🌐 Network Routing Example");
        int[] routingResult = network.dijkstra(0);
        network.printDistances(routingResult, "Optimal Routing Paths");
        System.out.println("\n📡 Lowest latency to target: " + 
                          routingResult[4] + "ms");
    }
}
```

## Complexity Analysis

### Time Complexity
- **Dijkstra**: O((V + E) log V) with binary heap, O(V²) with array
- **Bellman-Ford**: O(V × E) - slower but handles negatives
- **Floyd-Warshall**: O(V³) - independent of edge count
- **A***: O(E) best case, O(V²) worst case

### Space Complexity
- **All Algorithms**: O(V) for distance array + O(V + E) for graph storage

## Edge Cases & Handling

### 1. Negative Weights
- Dijkstra: **Fails** - use Bellman-Ford instead
- Bellman-Ford: **Handles correctly**
- Floyd-Warshall: **Handles if no negative cycles**

### 2. Unreachable Nodes
- Dijkstra & Bellman-Ford: Returns INF (Integer.MAX_VALUE/2)
- Floyd-Warshall: Returns INF in distance matrix
- Handle by checking if distance == INF before using

### 3. Negative Cycles
- Bellman-Ford: Detects and reports
- Floyd-Warshall: Causes incorrect results
- Solution: Skip negative weight edges or use Bellman-Ford

### 4. Self-Loops
- Should have weight 0 or be ignored
- In shortest path, self-loops don't improve paths

## When to Use Each Algorithm

**Choose Dijkstra when**:
- Graph has non-negative weights
- Need single-source shortest path
- Want optimal time complexity
- Memory efficiency is important

**Choose Bellman-Ford when**:
- Graph has negative weights
- Need to detect negative cycles
- Single-source path is sufficient
- Can tolerate slower performance

**Choose Floyd-Warshall when**:
- Need all-pairs shortest paths
- Graph is small to medium (V ≤ 500)
- Multiple queries needed
- Don't mind O(V³) complexity

**Choose A* when**:
- Have good heuristic function
- Need pathfinding in 2D/3D space
- Game AI or GPS navigation
- Early termination benefits (destination-focused)

## Key Takeaways

1. **Graph representation** affects algorithm efficiency - use adjacency lists for sparse graphs
2. **Choose algorithm** based on constraints: weights, number of sources, graph size
3. **Handle edge cases**: negative cycles, unreachable nodes, overflow
4. **Optimize with heuristics**: A* improves Dijkstra with domain knowledge
5. **Test thoroughly**: include negative weights, cycles, disconnected components

## References and Further Study

- Dijkstra's original 1959 paper
- Bellman-Ford for negative weight handling
- Floyd-Warshall for dense graphs
- A* in game development and robotics
- Real-world: Google Maps, Facebook social graph, network protocols

