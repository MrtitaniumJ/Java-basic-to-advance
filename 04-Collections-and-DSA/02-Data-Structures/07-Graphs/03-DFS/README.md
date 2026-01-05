# Depth-First Search (DFS) in Java

## Table of Contents
1. [Introduction](#introduction)
2. [DFS Algorithm](#dfs-algorithm)
3. [Recursive vs Iterative](#recursive-vs-iterative)
4. [Visual Representation](#visual-representation)
5. [Java Implementation](#java-implementation)
6. [Advanced Applications](#advanced-applications)
7. [Complexity Analysis](#complexity-analysis)
8. [Use Cases](#use-cases)
9. [Comparison with BFS](#comparison-with-bfs)

## Introduction

Depth-First Search (DFS) is a graph traversal algorithm that explores as far as possible along each branch before backtracking. DFS is crucial for solving problems like cycle detection, topological sorting, and finding strongly connected components. It can be implemented both recursively (using call stack) and iteratively (using explicit stack).

## DFS Algorithm

### Recursive Approach
1. Mark current vertex as visited
2. Process the vertex
3. For each unvisited adjacent vertex:
   - Recursively call DFS on that vertex
4. Backtrack when all neighbors explored

### Iterative Approach
1. Create an empty stack
2. Push starting vertex onto stack
3. While stack is not empty:
   - Pop a vertex
   - Mark as visited if not already
   - Process the vertex
   - Push all unvisited neighbors onto stack

## Recursive vs Iterative

| Feature | Recursive | Iterative |
|---------|-----------|-----------|
| Implementation | Simple, clean | More verbose |
| Stack | Call stack | Explicit stack |
| Memory | Risk of stack overflow | Bounded memory |
| Readability | High | Medium |
| Performance | Slightly faster | Slightly slower |

## Visual Representation

```
Graph:              DFS Order (starting from 0):
    0               0 → 1 → 3 → 4 → 2 → 5 → 6
   / \              (Depth-first exploration)
  1   2             
 / \ / \            Recursion Tree:
3   4   5           0
        \           ├─ 1
         6          │  ├─ 3
                    │  └─ 4
                    └─ 2
                       ├─ 5
                       └─ 6

Stack progression (iterative):
Initial: [0]
Step 1: Pop 0, Push 1,2 → [1, 2]
Step 2: Pop 2, Push 5,6 → [1, 5, 6]
Step 3: Pop 6 → [1, 5]
Step 4: Pop 5 → [1]
Step 5: Pop 1, Push 3,4 → [3, 4]
Step 6: Pop 4 → [3]
Step 7: Pop 3 → []
```

## Java Implementation

### Complete DFS Implementation with Multiple Approaches

```java
import java.util.*;

/**
 * Comprehensive DFS (Depth-First Search) implementation
 * Covers recursive, iterative, and advanced applications
 */
public class DFS {
    private int numVertices;
    private List<List<Integer>> adjacencyList;
    private int[] discoveryTime;
    private int[] finishTime;
    private int timeCounter;
    
    /**
     * Constructor
     */
    public DFS(int numVertices) {
        this.numVertices = numVertices;
        this.adjacencyList = new ArrayList<>();
        this.discoveryTime = new int[numVertices];
        this.finishTime = new int[numVertices];
        this.timeCounter = 0;
        
        for (int i = 0; i < numVertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }
    
    /**
     * Add edge to the graph
     */
    public void addEdge(int u, int v) {
        adjacencyList.get(u).add(v);
        // For undirected graph, add reverse edge
        adjacencyList.get(v).add(u);
    }
    
    /**
     * Add directed edge
     */
    public void addDirectedEdge(int u, int v) {
        adjacencyList.get(u).add(v);
    }
    
    /**
     * Basic recursive DFS traversal
     */
    public void dfsRecursive(int start) {
        boolean[] visited = new boolean[numVertices];
        System.out.println("\nDFS Recursive Traversal starting from vertex " + start + ":");
        System.out.print("Order: ");
        dfsRecursiveHelper(start, visited);
        System.out.println();
    }
    
    /**
     * Helper method for recursive DFS
     */
    private void dfsRecursiveHelper(int vertex, boolean[] visited) {
        visited[vertex] = true;
        System.out.print(vertex + " ");
        
        for (int neighbor : adjacencyList.get(vertex)) {
            if (!visited[neighbor]) {
                dfsRecursiveHelper(neighbor, visited);
            }
        }
    }
    
    /**
     * DFS traversal returning list of vertices
     */
    public List<Integer> dfsTraversal(int start) {
        boolean[] visited = new boolean[numVertices];
        List<Integer> result = new ArrayList<>();
        dfsTraversalHelper(start, visited, result);
        return result;
    }
    
    /**
     * Helper for DFS traversal
     */
    private void dfsTraversalHelper(int vertex, boolean[] visited, List<Integer> result) {
        visited[vertex] = true;
        result.add(vertex);
        
        for (int neighbor : adjacencyList.get(vertex)) {
            if (!visited[neighbor]) {
                dfsTraversalHelper(neighbor, visited, result);
            }
        }
    }
    
    /**
     * Iterative DFS using explicit stack
     */
    public void dfsIterative(int start) {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[numVertices];
        
        stack.push(start);
        System.out.println("\nDFS Iterative Traversal starting from vertex " + start + ":");
        System.out.print("Order: ");
        
        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            
            if (!visited[vertex]) {
                visited[vertex] = true;
                System.out.print(vertex + " ");
                
                // Push neighbors in reverse order to maintain same order as recursive
                List<Integer> neighbors = adjacencyList.get(vertex);
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    int neighbor = neighbors.get(i);
                    if (!visited[neighbor]) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        System.out.println();
    }
    
    /**
     * Detect cycle in undirected graph
     */
    public boolean hasCycleUndirected() {
        boolean[] visited = new boolean[numVertices];
        
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                if (hasCycleHelper(i, visited, -1)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Helper for cycle detection in undirected graph
     */
    private boolean hasCycleHelper(int vertex, boolean[] visited, int parent) {
        visited[vertex] = true;
        
        for (int neighbor : adjacencyList.get(vertex)) {
            if (!visited[neighbor]) {
                if (hasCycleHelper(neighbor, visited, vertex)) {
                    return true;
                }
            } else if (neighbor != parent) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Detect cycle in directed graph
     */
    public boolean hasCycleDirected() {
        int[] color = new int[numVertices]; // 0=white, 1=gray, 2=black
        
        for (int i = 0; i < numVertices; i++) {
            if (color[i] == 0) {
                if (hasCycleDirectedHelper(i, color)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Helper for cycle detection in directed graph
     */
    private boolean hasCycleDirectedHelper(int vertex, int[] color) {
        color[vertex] = 1; // gray
        
        for (int neighbor : adjacencyList.get(vertex)) {
            if (color[neighbor] == 1) {
                return true; // Back edge found
            } else if (color[neighbor] == 0) {
                if (hasCycleDirectedHelper(neighbor, color)) {
                    return true;
                }
            }
        }
        color[vertex] = 2; // black
        return false;
    }
    
    /**
     * Topological sorting using DFS
     */
    public List<Integer> topologicalSort() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[numVertices];
        
        for (int i = 0; i < numVertices; i++) {
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
    
    /**
     * Helper for topological sorting
     */
    private void topologicalSortHelper(int vertex, boolean[] visited, Stack<Integer> stack) {
        visited[vertex] = true;
        
        for (int neighbor : adjacencyList.get(vertex)) {
            if (!visited[neighbor]) {
                topologicalSortHelper(neighbor, visited, stack);
            }
        }
        
        stack.push(vertex);
    }
    
    /**
     * Find all vertices in a connected component
     */
    public List<Integer> findComponent(int start) {
        boolean[] visited = new boolean[numVertices];
        List<Integer> component = new ArrayList<>();
        dfsComponentHelper(start, visited, component);
        return component;
    }
    
    /**
     * Helper for finding components
     */
    private void dfsComponentHelper(int vertex, boolean[] visited, List<Integer> component) {
        visited[vertex] = true;
        component.add(vertex);
        
        for (int neighbor : adjacencyList.get(vertex)) {
            if (!visited[neighbor]) {
                dfsComponentHelper(neighbor, visited, component);
            }
        }
    }
    
    /**
     * Count connected components
     */
    public int countComponents() {
        boolean[] visited = new boolean[numVertices];
        int count = 0;
        
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                dfsComponentHelper(i, visited, new ArrayList<>());
                count++;
            }
        }
        
        return count;
    }
    
    /**
     * Find path between two vertices
     */
    public List<Integer> findPath(int start, int end) {
        boolean[] visited = new boolean[numVertices];
        List<Integer> path = new ArrayList<>();
        findPathHelper(start, end, visited, path);
        return path;
    }
    
    /**
     * Helper for finding path
     */
    private boolean findPathHelper(int current, int end, boolean[] visited, List<Integer> path) {
        visited[current] = true;
        path.add(current);
        
        if (current == end) {
            return true;
        }
        
        for (int neighbor : adjacencyList.get(current)) {
            if (!visited[neighbor]) {
                if (findPathHelper(neighbor, end, visited, path)) {
                    return true;
                }
            }
        }
        
        path.remove(path.size() - 1);
        return false;
    }
    
    /**
     * Compute discovery and finish times
     */
    public void computeTimes(int start) {
        boolean[] visited = new boolean[numVertices];
        timeCounter = 0;
        
        System.out.println("\nDiscovery and Finish Times:");
        computeTimesHelper(start, visited);
        
        for (int i = 0; i < numVertices; i++) {
            System.out.println("Vertex " + i + ": Discovery=" + discoveryTime[i] + 
                             ", Finish=" + finishTime[i]);
        }
    }
    
    /**
     * Helper for computing times
     */
    private void computeTimesHelper(int vertex, boolean[] visited) {
        visited[vertex] = true;
        discoveryTime[vertex] = ++timeCounter;
        
        for (int neighbor : adjacencyList.get(vertex)) {
            if (!visited[neighbor]) {
                computeTimesHelper(neighbor, visited);
            }
        }
        
        finishTime[vertex] = ++timeCounter;
    }
    
    /**
     * Find all strongly connected components (for directed graphs)
     */
    public List<List<Integer>> findStronglyConnectedComponents() {
        // Step 1: Compute finish times
        boolean[] visited = new boolean[numVertices];
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                fillStack(i, visited, stack);
            }
        }
        
        // Step 2: Create transpose graph
        List<List<Integer>> transpose = transposeGraph();
        visited = new boolean[numVertices];
        
        // Step 3: DFS on transpose in finish time order
        List<List<Integer>> components = new ArrayList<>();
        
        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            if (!visited[vertex]) {
                List<Integer> component = new ArrayList<>();
                dfsOnTranspose(vertex, visited, component, transpose);
                components.add(component);
            }
        }
        
        return components;
    }
    
    /**
     * Helper to fill stack with finish times
     */
    private void fillStack(int vertex, boolean[] visited, Stack<Integer> stack) {
        visited[vertex] = true;
        
        for (int neighbor : adjacencyList.get(vertex)) {
            if (!visited[neighbor]) {
                fillStack(neighbor, visited, stack);
            }
        }
        
        stack.push(vertex);
    }
    
    /**
     * Create transpose of graph
     */
    private List<List<Integer>> transposeGraph() {
        List<List<Integer>> transpose = new ArrayList<>();
        
        for (int i = 0; i < numVertices; i++) {
            transpose.add(new ArrayList<>());
        }
        
        for (int u = 0; u < numVertices; u++) {
            for (int v : adjacencyList.get(u)) {
                transpose.get(v).add(u);
            }
        }
        
        return transpose;
    }
    
    /**
     * DFS on transpose graph
     */
    private void dfsOnTranspose(int vertex, boolean[] visited, List<Integer> component,
                                List<List<Integer>> transpose) {
        visited[vertex] = true;
        component.add(vertex);
        
        for (int neighbor : transpose.get(vertex)) {
            if (!visited[neighbor]) {
                dfsOnTranspose(neighbor, visited, component, transpose);
            }
        }
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
        // Test 1: Basic DFS
        System.out.println("===== Basic DFS =====");
        DFS graph1 = new DFS(7);
        graph1.addEdge(0, 1);
        graph1.addEdge(0, 2);
        graph1.addEdge(1, 3);
        graph1.addEdge(1, 4);
        graph1.addEdge(2, 5);
        graph1.addEdge(2, 6);
        
        graph1.printGraph();
        graph1.dfsRecursive(0);
        graph1.dfsIterative(0);
        System.out.println("DFS Traversal: " + graph1.dfsTraversal(0));
        
        // Test 2: Cycle Detection
        System.out.println("\n===== Cycle Detection =====");
        DFS graph2 = new DFS(4);
        graph2.addEdge(0, 1);
        graph2.addEdge(1, 2);
        graph2.addEdge(2, 3);
        System.out.println("Has cycle (acyclic): " + graph2.hasCycleUndirected());
        
        DFS graph3 = new DFS(4);
        graph3.addEdge(0, 1);
        graph3.addEdge(1, 2);
        graph3.addEdge(2, 3);
        graph3.addEdge(3, 1);
        System.out.println("Has cycle (cyclic): " + graph3.hasCycleUndirected());
        
        // Test 3: Topological Sorting
        System.out.println("\n===== Topological Sorting =====");
        DFS graph4 = new DFS(6);
        graph4.addDirectedEdge(5, 2);
        graph4.addDirectedEdge(5, 0);
        graph4.addDirectedEdge(4, 0);
        graph4.addDirectedEdge(4, 1);
        graph4.addDirectedEdge(2, 3);
        graph4.addDirectedEdge(3, 1);
        System.out.println("Topological Sort: " + graph4.topologicalSort());
        
        // Test 4: Connected Components
        System.out.println("\n===== Connected Components =====");
        DFS graph5 = new DFS(6);
        graph5.addEdge(0, 1);
        graph5.addEdge(0, 2);
        graph5.addEdge(3, 4);
        System.out.println("Connected components: " + graph5.countComponents());
        System.out.println("Component of 0: " + graph5.findComponent(0));
        System.out.println("Component of 3: " + graph5.findComponent(3));
        
        // Test 5: Path Finding
        System.out.println("\n===== Path Finding =====");
        System.out.println("Path from 0 to 2: " + graph5.findPath(0, 2));
        System.out.println("Path from 0 to 4: " + graph5.findPath(0, 4));
        
        // Test 6: Discovery and Finish Times
        System.out.println("\n===== Discovery and Finish Times =====");
        DFS graph6 = new DFS(5);
        graph6.addDirectedEdge(0, 1);
        graph6.addDirectedEdge(1, 2);
        graph6.addDirectedEdge(2, 3);
        graph6.addDirectedEdge(3, 4);
        graph6.computeTimes(0);
    }
}
```

## Advanced Applications

1. **Cycle Detection:** Finding cycles in directed and undirected graphs
2. **Topological Sorting:** Ordering vertices for dependency resolution
3. **Strongly Connected Components:** Finding SCCs in directed graphs
4. **Bridge and Articulation Points:** Finding critical vertices
5. **Path Finding:** Finding any path between vertices
6. **Backtracking:** Solving puzzles and combinatorial problems
7. **Connected Components:** Grouping related vertices

## Complexity Analysis

| Aspect | Complexity |
|--------|-----------|
| **Time Complexity** | O(V + E) |
| **Space Complexity (Recursive)** | O(V) for recursion stack |
| **Space Complexity (Iterative)** | O(V) for explicit stack |
| **Visited Array** | O(V) |

**Explanation:**
- Each vertex visited once: O(V)
- Each edge explored once: O(E)
- Stack/recursion overhead: O(V) at most

## Use Cases

**Use DFS when:**
- Detecting cycles in graphs
- Topological sorting needed
- Finding connected components
- Path finding (any path acceptable)
- Backtracking problems (puzzles, sudoku)
- Finding strongly connected components
- Detecting bridges and articulation points
- Graph coloring problems

**Don't use DFS when:**
- Need shortest path (use BFS)
- Need level-order traversal
- Stack overflow risk (iterative works, or use BFS)
- Parallel processing needed (BFS better)

## Comparison with BFS

| Aspect | DFS | BFS |
|--------|-----|-----|
| Data Structure | Stack | Queue |
| Path | Any path | Shortest path |
| Space | Often less | O(V) |
| Implementation | Recursive/Iterative | Iterative |
| Time | O(V+E) | O(V+E) |
| Best For | Cycles, Topological, Components | Shortest path, Levels |
| Backtracking | Natural | Difficult |

