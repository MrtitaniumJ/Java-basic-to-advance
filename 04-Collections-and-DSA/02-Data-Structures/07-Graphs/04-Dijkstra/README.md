# Dijkstra's Algorithm - Shortest Path in Weighted Graphs

## Overview

Dijkstra's algorithm is a fundamental graph algorithm that finds the shortest path between nodes in a weighted graph with non-negative edge weights. Developed by Edsger W. Dijkstra in 1956, it's one of the most important algorithms in computer science and remains the foundation for GPS navigation systems, network routing protocols, and game pathfinding.

## Algorithm Explanation

Dijkstra's algorithm solves the **single-source shortest path problem** in a weighted directed or undirected graph where all edge weights are non-negative. The algorithm maintains a distance table and progressively updates it by selecting the unvisited node with the minimum distance and relaxing its outgoing edges.

### Key Concepts

- **Relaxation**: The process of updating the shortest distance to a node if a better path is found
- **Priority Queue**: Used to efficiently select the next unvisited node with minimum distance
- **Distance Array**: Stores the shortest distance from source to each vertex
- **Parent Array**: Tracks the path by storing the previous node in the shortest path

## How Dijkstra's Algorithm Works

1. **Initialize** distances to all vertices as infinity, except source = 0
2. **Create** a min-heap priority queue and insert source with distance 0
3. **While** priority queue is not empty:
   - Extract vertex with minimum distance
   - For each neighboring vertex:
     - Calculate tentative distance = current distance + edge weight
     - If tentative distance < known distance:
       - Update the distance
       - Update the parent
       - Insert vertex into priority queue
4. **Return** shortest distances and paths

### Pseudocode

```
function dijkstra(graph, source):
    distance = array of size V, initialized to INFINITY
    parent = array of size V, initialized to -1
    distance[source] = 0
    
    priorityQueue = minHeap()
    priorityQueue.insert(source, 0)
    visited = set()
    
    while priorityQueue is not empty:
        currentNode, currentDist = priorityQueue.extractMin()
        
        if currentNode in visited:
            continue
        
        visited.add(currentNode)
        
        for each neighbor of currentNode:
            newDist = distance[currentNode] + weight(currentNode, neighbor)
            
            if newDist < distance[neighbor]:
                distance[neighbor] = newDist
                parent[neighbor] = currentNode
                priorityQueue.insert(neighbor, newDist)
    
    return distance, parent
```

## Complete Java Implementation

```java
import java.util.*;

public class DijkstraAlgorithm {
    
    // Inner class to represent graph edges
    static class Edge {
        int destination;
        int weight;
        
        Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }
    
    // Inner class for priority queue element
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
    
    // Graph representation using adjacency list
    static class Graph {
        int vertices;
        List<List<Edge>> adjacencyList;
        
        Graph(int vertices) {
            this.vertices = vertices;
            adjacencyList = new ArrayList<>();
            for (int i = 0; i < vertices; i++) {
                adjacencyList.add(new ArrayList<>());
            }
        }
        
        // Add weighted edge to graph
        void addEdge(int source, int destination, int weight) {
            adjacencyList.get(source).add(new Edge(destination, weight));
            // For undirected graph, uncomment:
            // adjacencyList.get(destination).add(new Edge(source, weight));
        }
        
        // Dijkstra's algorithm implementation
        void dijkstra(int source) {
            // Distance array - stores shortest distance from source
            int[] distance = new int[vertices];
            // Parent array - stores path
            int[] parent = new int[vertices];
            // Visited array
            boolean[] visited = new boolean[vertices];
            
            // Initialize distances and parent
            Arrays.fill(distance, Integer.MAX_VALUE);
            Arrays.fill(parent, -1);
            distance[source] = 0;
            
            // Priority queue stores Node objects (vertex, distance)
            PriorityQueue<Node> pq = new PriorityQueue<>();
            pq.offer(new Node(source, 0));
            
            while (!pq.isEmpty()) {
                Node current = pq.poll();
                int u = current.vertex;
                
                // Skip if already visited
                if (visited[u]) {
                    continue;
                }
                
                visited[u] = true;
                
                // Relax all adjacent edges
                for (Edge edge : adjacencyList.get(u)) {
                    int v = edge.destination;
                    int weight = edge.weight;
                    
                    // If better path found
                    if (!visited[v] && distance[u] != Integer.MAX_VALUE && 
                        distance[u] + weight < distance[v]) {
                        distance[v] = distance[u] + weight;
                        parent[v] = u;
                        pq.offer(new Node(v, distance[v]));
                    }
                }
            }
            
            // Print results
            printSolution(distance, source, parent);
        }
        
        // Print shortest paths and distances
        void printSolution(int[] distance, int source, int[] parent) {
            System.out.println("\n=== Dijkstra's Algorithm Results ===");
            System.out.println("Source: " + source);
            System.out.println("\nVertex\tDistance\tPath");
            System.out.println("------\t--------\t----");
            
            for (int i = 0; i < vertices; i++) {
                System.out.print(i + "\t");
                if (distance[i] == Integer.MAX_VALUE) {
                    System.out.print("INFINITY\t");
                } else {
                    System.out.print(distance[i] + "\t\t");
                }
                
                if (i == source) {
                    System.out.println(source);
                } else if (distance[i] == Integer.MAX_VALUE) {
                    System.out.println("No path");
                } else {
                    System.out.println(reconstructPath(parent, i, source));
                }
            }
        }
        
        // Reconstruct path from source to destination
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
        // Example 1: Simple graph
        System.out.println("=== Example 1: Simple Weighted Graph ===");
        Graph graph1 = new Graph(6);
        graph1.addEdge(0, 1, 4);
        graph1.addEdge(0, 2, 2);
        graph1.addEdge(1, 2, 1);
        graph1.addEdge(1, 3, 5);
        graph1.addEdge(2, 3, 8);
        graph1.addEdge(2, 4, 10);
        graph1.addEdge(3, 4, 2);
        graph1.addEdge(3, 5, 6);
        graph1.addEdge(4, 5, 3);
        
        graph1.dijkstra(0);
        
        // Example 2: Complex graph with multiple paths
        System.out.println("\n\n=== Example 2: Complex Network ===");
        Graph graph2 = new Graph(5);
        graph2.addEdge(0, 1, 7);
        graph2.addEdge(0, 2, 9);
        graph2.addEdge(0, 5, 14);
        graph2.addEdge(1, 2, 10);
        graph2.addEdge(1, 3, 15);
        graph2.addEdge(2, 3, 11);
        graph2.addEdge(2, 5, 2);
        graph2.addEdge(3, 4, 6);
        graph2.addEdge(4, 5, 9);
        
        graph2.dijkstra(0);
        
        // Example 3: Single source to multiple destinations
        System.out.println("\n\n=== Example 3: GPS Navigation Simulation ===");
        Graph graph3 = new Graph(4);
        graph3.addEdge(0, 1, 5);
        graph3.addEdge(0, 2, 3);
        graph3.addEdge(1, 2, 2);
        graph3.addEdge(1, 3, 6);
        graph3.addEdge(2, 3, 7);
        
        graph3.dijkstra(0);
    }
}
```

## Examples and Use Cases

### Example 1: City Navigation
Finding the shortest route from city A to city B with distances as weights.

### Example 2: Network Routing
Finding optimal paths for data packets in computer networks with link costs.

### Example 3: Social Networks
Computing degrees of separation with relationship strength as weights.

## Edge Cases

1. **Disconnected Graph**: Unreachable vertices show distance as infinity
2. **Single Node**: Graph with one vertex returns distance 0
3. **Self-loops**: Usually ignored as they don't provide shorter paths
4. **Multiple Shortest Paths**: Algorithm finds one; multiple valid paths may exist

## Time and Space Complexity

| Implementation | Time Complexity | Space Complexity |
|---|---|---|
| Array-based | O(V²) | O(V) |
| Min-Heap | O((V + E) log V) | O(V + E) |
| Fibonacci Heap | O(E + V log V) | O(V) |

Where V = number of vertices, E = number of edges

## When to Use Dijkstra's Algorithm

- ✅ Shortest path with **non-negative weights**
- ✅ Single source to all destinations
- ✅ Sparse graphs (use heap implementation)
- ✅ Dense graphs (use array implementation)
- ❌ Negative edge weights (use Bellman-Ford)
- ❌ All-pairs shortest paths (use Floyd-Warshall)

## Real-World Applications

1. **GPS Navigation**: Calculating optimal routes with distances as weights
2. **Network Routing Protocols**: OSPF (Open Shortest Path First)
3. **Game Development**: Pathfinding for NPCs and AI
4. **Telecommunications**: Finding minimum delay paths
5. **Social Networks**: Computing network distances
6. **Flight Route Optimization**: Finding cheapest flights

## Comparison with Other Algorithms

| Algorithm | Time | Negative Weights | Use Case |
|---|---|---|---|
| **Dijkstra** | O((V+E) log V) | ❌ No | Single-source, non-negative |
| **Bellman-Ford** | O(VE) | ✅ Yes | Single-source, allows negative |
| **Floyd-Warshall** | O(V³) | ✅ Yes | All-pairs shortest paths |
| **BFS** | O(V+E) | ❌ Unweighted | Unweighted graphs |

## Key Takeaways

- Dijkstra's is the **most efficient for single-source shortest paths** with non-negative weights
- **Greedy approach** always picks the closest unvisited vertex
- **Priority queue implementation** makes it practical for large graphs
- **Cannot handle negative weights** - algorithm may produce incorrect results
- Understanding this algorithm is crucial for solving many graph problems in interviews and competitive programming
