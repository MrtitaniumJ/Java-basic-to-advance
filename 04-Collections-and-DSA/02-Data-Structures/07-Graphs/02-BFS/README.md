# Breadth-First Search (BFS) in Java

## Table of Contents
1. [Introduction](#introduction)
2. [BFS Algorithm](#bfs-algorithm)
3. [How BFS Works](#how-bfs-works)
4. [Visual Representation](#visual-representation)
5. [Java Implementation](#java-implementation)
6. [Applications](#applications)
7. [Complexity Analysis](#complexity-analysis)
8. [Use Cases](#use-cases)
9. [Interview Tips](#interview-tips)

## Introduction

Breadth-First Search (BFS) is a fundamental graph traversal algorithm that explores vertices in layers, starting from a source vertex. It visits all neighbors at distance k before visiting vertices at distance k+1. BFS is essential for finding shortest paths in unweighted graphs and solving level-by-level problems.

## BFS Algorithm

BFS uses a Queue data structure to process vertices:

1. Create an empty queue
2. Mark the starting vertex as visited and add to queue
3. While queue is not empty:
   - Dequeue a vertex
   - For each unvisited neighbor:
     - Mark as visited
     - Enqueue the neighbor
   - Process the vertex (print, store, etc.)

## How BFS Works

BFS explores a graph layer by layer:

**Step-by-step Process:**
- Start from source vertex S
- Visit all vertices at distance 1 from S
- Visit all vertices at distance 2 from S
- Continue until all reachable vertices are visited

**Key Characteristics:**
- Uses FIFO queue
- Guarantees shortest path in unweighted graphs
- Explores level by level
- Time complexity: O(V + E)

## Visual Representation

```
Graph:              BFS Order (starting from 0):
    0               0 → 1, 2 → 3, 4 → 5
   / \              
  1   2             Level 0: [0]
 / \ / \            Level 1: [1, 2]
3   4   5           Level 2: [3, 4, 5]

Queue progression:
Initial: [0]
Step 1: Dequeue 0, Enqueue 1,2 → [1, 2]
Step 2: Dequeue 1, Enqueue 3,4 → [2, 3, 4]
Step 3: Dequeue 2, Enqueue 5 → [3, 4, 5]
Step 4: Dequeue 3 → [4, 5]
Step 5: Dequeue 4 → [5]
Step 6: Dequeue 5 → []
```

## Java Implementation

### Complete BFS Implementation with Multiple Approaches

```java
import java.util.*;

/**
 * Comprehensive BFS (Breadth-First Search) implementation
 * Covers basic BFS, shortest path, level order, and multiple variations
 */
public class BFS {
    private int numVertices;
    private List<List<Integer>> adjacencyList;
    private boolean[] visited;
    
    /**
     * Constructor
     */
    public BFS(int numVertices) {
        this.numVertices = numVertices;
        this.adjacencyList = new ArrayList<>();
        this.visited = new boolean[numVertices];
        
        for (int i = 0; i < numVertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }
    
    /**
     * Add edge to the graph
     */
    public void addEdge(int u, int v) {
        adjacencyList.get(u).add(v);
        adjacencyList.get(v).add(u); // For undirected graph
    }
    
    /**
     * Basic BFS traversal from a source vertex
     * Prints all reachable vertices
     */
    public void bfs(int start) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[numVertices];
        
        visited[start] = true;
        queue.add(start);
        
        System.out.println("\nBFS Traversal starting from vertex " + start + ":");
        System.out.print("Order: ");
        
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");
            
            // Explore all adjacent vertices
            for (int neighbor : adjacencyList.get(vertex)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }
    
    /**
     * BFS returning list of vertices in traversal order
     */
    public List<Integer> bfsTraversal(int start) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[numVertices];
        List<Integer> result = new ArrayList<>();
        
        visited[start] = true;
        queue.add(start);
        
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            result.add(vertex);
            
            for (int neighbor : adjacencyList.get(vertex)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Find shortest path between two vertices
     * Returns path as list of vertices
     */
    public List<Integer> shortestPath(int start, int end) {
        if (start == end) {
            return Arrays.asList(start);
        }
        
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[numVertices];
        int[] parent = new int[numVertices];
        Arrays.fill(parent, -1);
        
        visited[start] = true;
        queue.add(start);
        
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            
            for (int neighbor : adjacencyList.get(vertex)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = vertex;
                    queue.add(neighbor);
                    
                    if (neighbor == end) {
                        // Reconstruct path
                        List<Integer> path = new ArrayList<>();
                        int current = end;
                        while (current != -1) {
                            path.add(0, current);
                            current = parent[current];
                        }
                        return path;
                    }
                }
            }
        }
        
        return new ArrayList<>(); // No path found
    }
    
    /**
     * Find shortest distance between two vertices
     */
    public int shortestDistance(int start, int end) {
        if (start == end) {
            return 0;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[numVertices];
        int[] distance = new int[numVertices];
        Arrays.fill(distance, -1);
        
        visited[start] = true;
        distance[start] = 0;
        queue.add(start);
        
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            
            for (int neighbor : adjacencyList.get(vertex)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    distance[neighbor] = distance[vertex] + 1;
                    queue.add(neighbor);
                    
                    if (neighbor == end) {
                        return distance[end];
                    }
                }
            }
        }
        
        return -1; // No path found
    }
    
    /**
     * Get all vertices at a specific level from source
     */
    public List<Integer> verticesAtLevel(int start, int level) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[numVertices];
        int[] distance = new int[numVertices];
        Arrays.fill(distance, -1);
        List<Integer> result = new ArrayList<>();
        
        visited[start] = true;
        distance[start] = 0;
        queue.add(start);
        
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            
            if (distance[vertex] == level) {
                result.add(vertex);
            }
            
            for (int neighbor : adjacencyList.get(vertex)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    distance[neighbor] = distance[vertex] + 1;
                    queue.add(neighbor);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Check if graph is connected (all vertices reachable from start)
     */
    public boolean isConnected(int start) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[numVertices];
        
        visited[start] = true;
        queue.add(start);
        int count = 1;
        
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            
            for (int neighbor : adjacencyList.get(vertex)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                    count++;
                }
            }
        }
        
        return count == numVertices;
    }
    
    /**
     * BFS with custom processing for each vertex
     */
    public void bfsWithProcessing(int start, VertexProcessor processor) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[numVertices];
        
        visited[start] = true;
        queue.add(start);
        
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            processor.process(vertex);
            
            for (int neighbor : adjacencyList.get(vertex)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    }
    
    /**
     * Level order traversal (similar to binary tree)
     * Returns list of lists with vertices at each level
     */
    public List<List<Integer>> levelOrderTraversal(int start) {
        List<List<Integer>> result = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[numVertices];
        
        visited[start] = true;
        queue.add(start);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                int vertex = queue.poll();
                currentLevel.add(vertex);
                
                for (int neighbor : adjacencyList.get(vertex)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.add(neighbor);
                    }
                }
            }
            
            result.add(currentLevel);
        }
        
        return result;
    }
    
    /**
     * Count connected components in graph
     */
    public int countConnectedComponents() {
        boolean[] visited = new boolean[numVertices];
        int components = 0;
        
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                bfsComponent(i, visited);
                components++;
            }
        }
        
        return components;
    }
    
    /**
     * Helper for BFS during component counting
     */
    private void bfsComponent(int start, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        visited[start] = true;
        queue.add(start);
        
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            
            for (int neighbor : adjacencyList.get(vertex)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    }
    
    /**
     * Check if graph is bipartite using BFS
     */
    public boolean isBipartite() {
        int[] color = new int[numVertices];
        Arrays.fill(color, -1);
        
        for (int i = 0; i < numVertices; i++) {
            if (color[i] == -1) {
                if (!isBipartiteHelper(i, color)) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Helper for bipartite check
     */
    private boolean isBipartiteHelper(int start, int[] color) {
        Queue<Integer> queue = new LinkedList<>();
        color[start] = 0;
        queue.add(start);
        
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            
            for (int neighbor : adjacencyList.get(vertex)) {
                if (color[neighbor] == -1) {
                    color[neighbor] = 1 - color[vertex];
                    queue.add(neighbor);
                } else if (color[neighbor] == color[vertex]) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Functional interface for processing vertices
     */
    @FunctionalInterface
    public interface VertexProcessor {
        void process(int vertex);
    }
    
    /**
     * Print graph structure
     */
    public void printGraph() {
        System.out.println("\nGraph Adjacency List:");
        for (int i = 0; i < numVertices; i++) {
            System.out.print("Vertex " + i + " → ");
            for (int neighbor : adjacencyList.get(i)) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Main method for demonstration
     */
    public static void main(String[] args) {
        // Create graph
        BFS graph = new BFS(7);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);
        graph.addEdge(2, 6);
        
        graph.printGraph();
        
        // Basic BFS
        graph.bfs(0);
        
        // BFS Traversal
        System.out.println("\nBFS Traversal Result: " + graph.bfsTraversal(0));
        
        // Shortest path
        System.out.println("\nShortest path from 0 to 6: " + graph.shortestPath(0, 6));
        System.out.println("Shortest distance from 0 to 6: " + graph.shortestDistance(0, 6));
        
        // Vertices at level
        System.out.println("\nVertices at level 2 from 0: " + graph.verticesAtLevel(0, 2));
        
        // Level order traversal
        System.out.println("\nLevel Order Traversal:\n" + graph.levelOrderTraversal(0));
        
        // Connectivity
        System.out.println("\nIs connected from 0: " + graph.isConnected(0));
        System.out.println("Connected components: " + graph.countConnectedComponents());
        
        // Bipartite check
        System.out.println("\nIs bipartite: " + graph.isBipartite());
        
        // Custom processing
        System.out.println("\nBFS with custom processing (square of vertex):");
        graph.bfsWithProcessing(0, v -> System.out.print((v * v) + " "));
        System.out.println();
    }
}
```

## Applications

1. **Shortest Path:** Find shortest path in unweighted graphs
2. **Web Crawling:** Exploring web pages level by level
3. **Social Networks:** Finding degrees of separation
4. **Level-order Traversal:** Tree level-order traversal
5. **Connected Components:** Finding all connected groups
6. **Bipartite Check:** Determining if graph is bipartite
7. **Peer-to-Peer Networks:** Broadcasting messages
8. **Maze Solving:** Finding shortest path through maze

## Complexity Analysis

| Aspect | Complexity |
|--------|-----------|
| **Time Complexity** | O(V + E) |
| **Space Complexity** | O(V) |
| **Queue Space** | O(V) |
| **Visit Array** | O(V) |

**Explanation:**
- Visit each vertex once: O(V)
- Explore each edge twice: O(E)
- Queue operations: O(1) per operation

## Use Cases

**Use BFS when:**
- Need shortest path in unweighted graph
- Exploring level-by-level
- Finding connected components
- Level order traversal needed
- Bipartite check required
- Broadcasting/message passing
- Finding all nodes at distance k

**Don't use BFS when:**
- Working with weighted graphs (use Dijkstra)
- Need minimal memory (DFS uses less stack)
- Depth exploration needed
- Detecting cycles in directed graphs (DFS better)

## Interview Tips

1. **Always reset visited array** between different BFS calls
2. **Queue vs Stack:** BFS uses queue, DFS uses stack
3. **Parent tracking:** For path reconstruction
4. **Distance array:** For tracking distances from source
5. **Time complexity:** Always mention O(V + E)
6. **Edge cases:** Empty graph, disconnected vertices, single vertex
7. **Variants:** Know shortest path, level order, bipartite check
8. **Implementation:** Practice without looking at notes

