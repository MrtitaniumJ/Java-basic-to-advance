# Breadth-First Search (BFS) and Depth-First Search (DFS) Algorithms

## Overview

BFS and DFS are fundamental graph traversal algorithms used to explore and analyze graph structures. BFS explores vertices level by level from a source, while DFS explores as far as possible along each branch before backtracking. These algorithms form the foundation for many advanced graph algorithms including cycle detection, topological sorting, and shortest path finding.

## Table of Contents
1. [Algorithm Fundamentals](#algorithm-fundamentals)
2. [BFS - Breadth-First Search](#bfs---breadth-first-search)
3. [DFS - Depth-First Search](#dfs---depth-first-search)
4. [Graph Representations](#graph-representations)
5. [Applications](#applications)
6. [Complexity Analysis](#complexity-analysis)
7. [Complete Java Implementation](#complete-java-implementation)
8. [Real-World Examples](#real-world-examples)
9. [Comparison](#comparison)

## Algorithm Fundamentals

Both BFS and DFS are used to traverse connected and disconnected graphs, visiting each vertex exactly once. The primary difference lies in the order of traversal:

- **BFS (Breadth-First)**: Uses a Queue (FIFO) to explore vertices level by level
- **DFS (Depth-First)**: Uses a Stack (LIFO) to explore as deep as possible before backtracking

Key concepts:
- **Visited Set**: Maintains visited vertices to avoid cycles
- **Graph Representation**: Adjacency List or Adjacency Matrix
- **Time Complexity**: O(V + E) for both algorithms
- **Space Complexity**: O(V) for visited tracking

## BFS - Breadth-First Search

### How BFS Works

1. Start from a source vertex
2. Add it to a queue and mark as visited
3. While queue is not empty:
   - Dequeue a vertex
   - Process it
   - Enqueue all unvisited adjacent vertices
   - Mark them as visited

### Characteristics

- **Explores level by level**: All vertices at distance k are explored before vertices at distance k+1
- **Finds shortest paths**: In unweighted graphs, BFS finds the shortest path
- **Requires Queue**: FIFO data structure ensures level-by-level exploration
- **Memory intensive**: May require O(V) space for the queue

### Use Cases

- Finding shortest path in unweighted graphs
- Social network friend recommendations (closest connections)
- Web crawler implementations
- GPS navigation (level-by-level exploration)
- Bipartite graph detection

## DFS - Depth-First Search

### How DFS Works

1. Start from a source vertex and mark as visited
2. Explore one adjacent unvisited vertex recursively
3. When no unvisited neighbors exist, backtrack
4. Continue until all reachable vertices are visited

### DFS Approaches

#### Recursive DFS
```
def dfs(vertex, visited, graph):
    visited.add(vertex)
    for neighbor in graph[vertex]:
        if neighbor not in visited:
            dfs(neighbor, visited, graph)
```

#### Iterative DFS
Uses an explicit stack instead of recursion, achieving the same result with more control.

### Characteristics

- **Explores deeply**: Goes as deep as possible before backtracking
- **Memory efficient**: Uses less memory than BFS for certain graph types
- **Detects cycles**: Can easily identify back edges indicating cycles
- **Topological sorting**: Foundation for topological sort algorithms

### Use Cases

- Cycle detection in directed and undirected graphs
- Topological sorting
- Strongly connected components (Tarjan's or Kosaraju's algorithm)
- Maze solving
- Expression evaluation (parenthesis matching)
- Backtracking problems

## Graph Representations

### Adjacency List
```
0 -> 1, 2
1 -> 0, 3
2 -> 0, 3
3 -> 1, 2, 4
4 -> 3
```

**Advantages**: Space-efficient O(V + E), better for sparse graphs

### Adjacency Matrix
```
  0 1 2 3 4
0 0 1 1 0 0
1 1 0 0 1 0
2 1 0 0 1 0
3 0 1 1 0 1
4 0 0 0 1 0
```

**Advantages**: Fast edge lookup O(1), better for dense graphs

## Applications

### 1. Connected Components
Finding all connected components in an undirected graph.

### 2. Cycle Detection
Detecting cycles in directed and undirected graphs.

### 3. Topological Sort
Ordering vertices in a directed acyclic graph (DAG).

### 4. Path Finding
Finding paths between two vertices in a graph.

### 5. Bipartite Checking
Determining if a graph is bipartite (2-colorable).

## Complexity Analysis

### Time Complexity
- **BFS**: O(V + E) - visits each vertex once, explores each edge once
- **DFS (Recursive)**: O(V + E) - same as BFS
- **DFS (Iterative)**: O(V + E) - same as BFS

### Space Complexity
- **BFS**: O(V) - queue can contain at most all vertices
- **DFS (Recursive)**: O(V) - recursion stack in worst case (linear graph)
- **DFS (Iterative)**: O(V) - explicit stack contains at most all vertices

## Complete Java Implementation

### Graph Class with BFS and DFS

```java
import java.util.*;

/**
 * Comprehensive Graph class implementing both BFS and DFS algorithms
 * Supports both Adjacency List and Adjacency Matrix representations
 */
public class GraphTraversal {
    
    /**
     * Graph representation using Adjacency List
     * Time: O(V+E), Space: O(V+E) - better for sparse graphs
     */
    public static class Graph {
        private int vertices;
        private List<List<Integer>> adjacencyList;
        private int[][] adjacencyMatrix;
        private boolean useList;
        
        // Constructor for Adjacency List representation
        public Graph(int vertices, boolean useList) {
            this.vertices = vertices;
            this.useList = useList;
            
            if (useList) {
                adjacencyList = new ArrayList<>();
                for (int i = 0; i < vertices; i++) {
                    adjacencyList.add(new ArrayList<>());
                }
            } else {
                adjacencyMatrix = new int[vertices][vertices];
            }
        }
        
        // Add edge to graph (undirected)
        public void addEdge(int u, int v) {
            if (useList) {
                adjacencyList.get(u).add(v);
                adjacencyList.get(v).add(u);
            } else {
                adjacencyMatrix[u][v] = 1;
                adjacencyMatrix[v][u] = 1;
            }
        }
        
        // Add directed edge
        public void addDirectedEdge(int u, int v) {
            if (useList) {
                adjacencyList.get(u).add(v);
            } else {
                adjacencyMatrix[u][v] = 1;
            }
        }
        
        /**
         * BFS Implementation using Queue
         * Time: O(V+E), Space: O(V)
         */
        public List<Integer> bfs(int startVertex) {
            List<Integer> result = new ArrayList<>();
            boolean[] visited = new boolean[vertices];
            Queue<Integer> queue = new LinkedList<>();
            
            queue.add(startVertex);
            visited[startVertex] = true;
            
            while (!queue.isEmpty()) {
                int vertex = queue.poll();
                result.add(vertex);
                
                if (useList) {
                    for (int adjacent : adjacencyList.get(vertex)) {
                        if (!visited[adjacent]) {
                            queue.add(adjacent);
                            visited[adjacent] = true;
                        }
                    }
                } else {
                    for (int i = 0; i < vertices; i++) {
                        if (adjacencyMatrix[vertex][i] == 1 && !visited[i]) {
                            queue.add(i);
                            visited[i] = true;
                        }
                    }
                }
            }
            return result;
        }
        
        /**
         * DFS - Recursive Implementation
         * Time: O(V+E), Space: O(V) recursion stack
         */
        public List<Integer> dfsRecursive(int startVertex) {
            List<Integer> result = new ArrayList<>();
            boolean[] visited = new boolean[vertices];
            dfsHelper(startVertex, visited, result);
            return result;
        }
        
        private void dfsHelper(int vertex, boolean[] visited, List<Integer> result) {
            visited[vertex] = true;
            result.add(vertex);
            
            if (useList) {
                for (int adjacent : adjacencyList.get(vertex)) {
                    if (!visited[adjacent]) {
                        dfsHelper(adjacent, visited, result);
                    }
                }
            } else {
                for (int i = 0; i < vertices; i++) {
                    if (adjacencyMatrix[vertex][i] == 1 && !visited[i]) {
                        dfsHelper(i, visited, result);
                    }
                }
            }
        }
        
        /**
         * DFS - Iterative Implementation using Stack
         * Time: O(V+E), Space: O(V)
         */
        public List<Integer> dfsIterative(int startVertex) {
            List<Integer> result = new ArrayList<>();
            boolean[] visited = new boolean[vertices];
            Stack<Integer> stack = new Stack<>();
            
            stack.push(startVertex);
            visited[startVertex] = true;
            
            while (!stack.isEmpty()) {
                int vertex = stack.pop();
                result.add(vertex);
                
                if (useList) {
                    for (int adjacent : adjacencyList.get(vertex)) {
                        if (!visited[adjacent]) {
                            stack.push(adjacent);
                            visited[adjacent] = true;
                        }
                    }
                } else {
                    for (int i = 0; i < vertices; i++) {
                        if (adjacencyMatrix[vertex][i] == 1 && !visited[i]) {
                            stack.push(i);
                            visited[i] = true;
                        }
                    }
                }
            }
            return result;
        }
        
        /**
         * Find all connected components in undirected graph
         * Time: O(V+E), Space: O(V)
         */
        public List<List<Integer>> findConnectedComponents() {
            List<List<Integer>> components = new ArrayList<>();
            boolean[] visited = new boolean[vertices];
            
            for (int i = 0; i < vertices; i++) {
                if (!visited[i]) {
                    List<Integer> component = new ArrayList<>();
                    dfsComponentHelper(i, visited, component);
                    components.add(component);
                }
            }
            return components;
        }
        
        private void dfsComponentHelper(int vertex, boolean[] visited, List<Integer> component) {
            visited[vertex] = true;
            component.add(vertex);
            
            if (useList) {
                for (int adjacent : adjacencyList.get(vertex)) {
                    if (!visited[adjacent]) {
                        dfsComponentHelper(adjacent, visited, component);
                    }
                }
            } else {
                for (int i = 0; i < vertices; i++) {
                    if (adjacencyMatrix[vertex][i] == 1 && !visited[i]) {
                        dfsComponentHelper(i, visited, component);
                    }
                }
            }
        }
        
        /**
         * Detect cycle in undirected graph
         * Time: O(V+E), Space: O(V)
         */
        public boolean hasCycle() {
            boolean[] visited = new boolean[vertices];
            
            for (int i = 0; i < vertices; i++) {
                if (!visited[i] && dfsCycleHelper(i, -1, visited)) {
                    return true;
                }
            }
            return false;
        }
        
        private boolean dfsCycleHelper(int vertex, int parent, boolean[] visited) {
            visited[vertex] = true;
            
            if (useList) {
                for (int adjacent : adjacencyList.get(vertex)) {
                    if (!visited[adjacent]) {
                        if (dfsCycleHelper(adjacent, vertex, visited)) {
                            return true;
                        }
                    } else if (adjacent != parent) {
                        return true;
                    }
                }
            } else {
                for (int i = 0; i < vertices; i++) {
                    if (adjacencyMatrix[vertex][i] == 1) {
                        if (!visited[i]) {
                            if (dfsCycleHelper(i, vertex, visited)) {
                                return true;
                            }
                        } else if (i != parent) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        
        /**
         * Find path between two vertices
         * Time: O(V+E), Space: O(V)
         */
        public List<Integer> findPath(int source, int destination) {
            List<Integer> path = new ArrayList<>();
            boolean[] visited = new boolean[vertices];
            dfsPathHelper(source, destination, visited, path);
            return path;
        }
        
        private boolean dfsPathHelper(int current, int destination, boolean[] visited, List<Integer> path) {
            if (current == destination) {
                path.add(current);
                return true;
            }
            
            visited[current] = true;
            
            if (useList) {
                for (int adjacent : adjacencyList.get(current)) {
                    if (!visited[adjacent]) {
                        if (dfsPathHelper(adjacent, destination, visited, path)) {
                            path.add(0, current);
                            return true;
                        }
                    }
                }
            } else {
                for (int i = 0; i < vertices; i++) {
                    if (adjacencyMatrix[current][i] == 1 && !visited[i]) {
                        if (dfsPathHelper(i, destination, visited, path)) {
                            path.add(0, current);
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        
        /**
         * Topological Sort for DAG using DFS
         * Time: O(V+E), Space: O(V)
         */
        public List<Integer> topologicalSort() {
            Stack<Integer> stack = new Stack<>();
            boolean[] visited = new boolean[vertices];
            
            for (int i = 0; i < vertices; i++) {
                if (!visited[i]) {
                    topologicalSortHelper(i, visited, stack);
                }
            }
            
            List<Integer> result = new ArrayList<>();
            while (!stack.isEmpty()) {
                result.add(stack.pop());
            }
            return result;
        }
        
        private void topologicalSortHelper(int vertex, boolean[] visited, Stack<Integer> stack) {
            visited[vertex] = true;
            
            if (useList) {
                for (int adjacent : adjacencyList.get(vertex)) {
                    if (!visited[adjacent]) {
                        topologicalSortHelper(adjacent, visited, stack);
                    }
                }
            } else {
                for (int i = 0; i < vertices; i++) {
                    if (adjacencyMatrix[vertex][i] == 1 && !visited[i]) {
                        topologicalSortHelper(i, visited, stack);
                    }
                }
            }
            stack.push(vertex);
        }
    }
    
    // Main method demonstrating all functionality
    public static void main(String[] args) {
        System.out.println("=== BFS and DFS Algorithm Demonstration ===\n");
        
        // Example 1: Undirected Graph - Adjacency List
        System.out.println("1. Undirected Graph (Adjacency List):");
        Graph graphList = new Graph(6, true);
        graphList.addEdge(0, 1);
        graphList.addEdge(0, 2);
        graphList.addEdge(1, 3);
        graphList.addEdge(2, 3);
        graphList.addEdge(3, 4);
        graphList.addEdge(4, 5);
        
        System.out.println("BFS from vertex 0: " + graphList.bfs(0));
        System.out.println("DFS Recursive from vertex 0: " + graphList.dfsRecursive(0));
        System.out.println("DFS Iterative from vertex 0: " + graphList.dfsIterative(0));
        System.out.println("Has cycle: " + graphList.hasCycle());
        System.out.println("Path from 0 to 5: " + graphList.findPath(0, 5));
        
        // Example 2: Directed Graph - Adjacency Matrix
        System.out.println("\n2. Directed Graph (Adjacency Matrix):");
        Graph graphMatrix = new Graph(4, false);
        graphMatrix.addDirectedEdge(0, 1);
        graphMatrix.addDirectedEdge(0, 2);
        graphMatrix.addDirectedEdge(1, 3);
        graphMatrix.addDirectedEdge(2, 3);
        
        System.out.println("Topological Sort: " + graphMatrix.topologicalSort());
        
        // Example 3: Connected Components
        System.out.println("\n3. Connected Components:");
        Graph graphComponents = new Graph(7, true);
        graphComponents.addEdge(0, 1);
        graphComponents.addEdge(0, 2);
        graphComponents.addEdge(3, 4);
        graphComponents.addEdge(5, 6);
        
        System.out.println("Connected Components: " + graphComponents.findConnectedComponents());
    }
}
```

## Real-World Examples

### Social Network - Friend Recommendations
BFS finds friends within a certain distance, ideal for "people you might know" features.

### GPS Navigation Systems
BFS explores locations level-by-level to find the shortest route before considering longer alternatives.

### Maze Solving
DFS naturally backtracks when hitting dead ends, making it perfect for maze navigation.

### Compiler Optimization
DFS detects strongly connected components in control flow graphs for code optimization.

### Web Crawler
BFS explores web pages level-by-level, discovering links at increasing distances from the starting page.

## Comparison

| Aspect | BFS | DFS |
|--------|-----|-----|
| **Data Structure** | Queue (FIFO) | Stack (LIFO) |
| **Exploration Order** | Level-by-level | Deep exploration |
| **Shortest Path** | Finds in unweighted graphs | May not find shortest |
| **Cycle Detection** | Yes (undirected/directed) | Yes (better for directed) |
| **Memory Usage** | Higher for wide graphs | Higher for deep graphs |
| **Recursion** | Not naturally recursive | Naturally recursive |
| **Best For** | Shortest paths, level-order | Cycles, topological sort |
| **Implementation** | Iterative | Recursive or Iterative |

## Key Takeaways

1. **BFS is optimal for unweighted shortest paths**: Always finds the shortest path in unweighted graphs
2. **DFS is efficient for exploring all paths**: Better for problems requiring backtracking
3. **Both have O(V+E) complexity**: The choice depends on problem requirements
4. **Graph representation matters**: Adjacency list for sparse, matrix for dense graphs
5. **Visited set is crucial**: Prevents infinite loops and ensures each vertex is processed once

## Practice Problems

1. Find the shortest path in a maze (BFS)
2. Detect cycle in a directed graph (DFS)
3. Count connected components (DFS)
4. Topological sort (DFS)
5. Find all paths between two vertices (DFS with backtracking)

## References

- Graph Theory Fundamentals
- Algorithm Design Manual by Skiena
- Introduction to Algorithms (CLRS)
