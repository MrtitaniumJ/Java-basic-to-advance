# Graph Algorithms

## Table of Contents
- [Introduction](#introduction)
- [Graph Representation](#graph-representation)
- [Types of Graph Algorithms](#types-of-graph-algorithms)
- [Algorithm Categories](#algorithm-categories)
  - [Graph Traversal (BFS & DFS)](#graph-traversal-bfs--dfs)
  - [Shortest Path Algorithms](#shortest-path-algorithms)
  - [Minimum Spanning Tree Algorithms](#minimum-spanning-tree-algorithms)
- [Comparison Table](#comparison-table)
- [When to Use Each Algorithm](#when-to-use-each-algorithm)
- [Complexity Analysis](#complexity-analysis)
- [Common Applications](#common-applications)
- [Navigation](#navigation)

## Introduction

Graph algorithms are fundamental computational procedures designed to solve problems on graph data structures. A graph consists of vertices (nodes) connected by edges (links), making it an ideal model for representing relationships, networks, connections, and hierarchical structures in computer science and real-world applications.

Graph algorithms form the backbone of many critical systems including social networks (friend connections), GPS navigation (road networks), web crawlers (hyperlink graphs), network routing protocols, dependency resolution in package managers, recommendation systems, and countless other applications. Understanding graph algorithms is essential for any software engineer, as they appear frequently in technical interviews and production systems.

The beauty of graph algorithms lies in their versatility—the same algorithmic principles can be applied to seemingly different problems. Whether you're finding the shortest route between cities, determining if a network is connected, detecting cycles in dependencies, or optimizing resource allocation, graph algorithms provide elegant solutions to complex problems.

## Graph Representation

Before diving into algorithms, it's crucial to understand how graphs are represented in memory:

### Adjacency Matrix
A 2D array where `matrix[i][j]` indicates an edge from vertex `i` to vertex `j`. Best for dense graphs and when you need O(1) edge lookup.

```java
int[][] adjacencyMatrix = new int[V][V];
// Space Complexity: O(V²)
```

### Adjacency List
An array of lists where each index represents a vertex, and the list contains its neighbors. Best for sparse graphs and memory efficiency.

```java
List<List<Integer>> adjacencyList = new ArrayList<>();
// Space Complexity: O(V + E)
```

### Edge List
A simple list of all edges in the graph. Useful for algorithms that process edges directly, like Kruskal's algorithm.

```java
List<Edge> edgeList = new ArrayList<>();
// Space Complexity: O(E)
```

## Types of Graph Algorithms

Graph algorithms can be categorized based on the problems they solve:

### 1. **Traversal Algorithms**
These algorithms systematically visit all vertices in a graph. They form the foundation for many other graph algorithms and are used to explore graph structure, detect connectivity, find paths, and identify components.

### 2. **Shortest Path Algorithms**
These algorithms find the minimum-cost path between vertices, where cost can represent distance, time, or any weighted metric. Critical for navigation systems, network routing, and optimization problems.

### 3. **Minimum Spanning Tree (MST) Algorithms**
These algorithms find a subset of edges that connects all vertices with minimum total edge weight, without forming cycles. Essential for network design, clustering, and optimization problems.

### 4. **Cycle Detection Algorithms**
Identify whether a graph contains cycles, useful for dependency resolution and deadlock detection.

### 5. **Topological Sorting**
Arranges vertices in a linear order respecting edge directions, critical for task scheduling and build systems.

### 6. **Connectivity Algorithms**
Determine if vertices are connected and identify strongly connected components in directed graphs.

## Algorithm Categories

### Graph Traversal (BFS & DFS)

**Breadth-First Search (BFS)** explores a graph level by level, visiting all neighbors of a vertex before moving to the next level. It uses a queue data structure and guarantees finding the shortest path in unweighted graphs.

**Key Characteristics:**
- Explores nodes layer by layer
- Uses queue (FIFO) for implementation
- Finds shortest path in unweighted graphs
- Better for finding nearby nodes
- Higher memory usage due to queue

**Depth-First Search (DFS)** explores a graph by going as deep as possible along each branch before backtracking. It can be implemented recursively or iteratively using a stack.

**Key Characteristics:**
- Explores as far as possible before backtracking
- Uses stack (LIFO) or recursion
- Better for path existence problems
- Lower memory usage in most cases
- Natural for tree-like structures

**[→ Explore BFS & DFS Details](01-BFS-DFS/)**

### Shortest Path Algorithms

Shortest path algorithms find the minimum-cost route between vertices in weighted or unweighted graphs. Different algorithms are optimized for different graph types and constraints.

**Dijkstra's Algorithm** finds shortest paths from a source vertex to all other vertices in graphs with non-negative edge weights. It's a greedy algorithm that uses a priority queue for efficient vertex selection.

**Bellman-Ford Algorithm** handles graphs with negative edge weights and can detect negative weight cycles. It's slower than Dijkstra but more versatile.

**Floyd-Warshall Algorithm** computes shortest paths between all pairs of vertices using dynamic programming. Ideal when you need the complete shortest path matrix.

**A* (A-Star) Algorithm** enhances Dijkstra's algorithm with heuristics to guide the search toward the target, making it extremely efficient for pathfinding in games and navigation systems.

**[→ Explore Shortest Path Algorithms](02-Shortest-Path/)**

### Minimum Spanning Tree Algorithms

MST algorithms find a tree that connects all vertices with minimum total edge weight. They're fundamental for network design, clustering, and optimization.

**Kruskal's Algorithm** sorts edges by weight and adds them one by one, using Union-Find to detect cycles. Works well for sparse graphs and when edges are pre-sorted.

**Prim's Algorithm** grows the MST by starting from a vertex and repeatedly adding the minimum-weight edge that connects a new vertex. More efficient for dense graphs when using a priority queue.

**Borůvka's Algorithm** is a parallel-friendly algorithm that simultaneously adds the minimum-weight outgoing edge from each component.

**[→ Explore MST Algorithms](03-MST-Algorithms/)**

## Comparison Table

| Algorithm | Type | Time Complexity | Space Complexity | Graph Type | Weighted | Handles Negative Weights | Best Use Case |
|-----------|------|-----------------|------------------|------------|----------|--------------------------|---------------|
| **BFS** | Traversal | O(V + E) | O(V) | Any | No | N/A | Shortest path in unweighted graphs, level-order traversal |
| **DFS** | Traversal | O(V + E) | O(V) | Any | No | N/A | Cycle detection, topological sort, path finding |
| **Dijkstra** | Shortest Path | O((V + E) log V) | O(V) | Any | Yes | No | GPS navigation, network routing |
| **Bellman-Ford** | Shortest Path | O(VE) | O(V) | Any | Yes | Yes | Detecting negative cycles, distance vector routing |
| **Floyd-Warshall** | Shortest Path | O(V³) | O(V²) | Any | Yes | Yes | All-pairs shortest paths, small dense graphs |
| **A*** | Shortest Path | O(E) | O(V) | Any | Yes | No | Game pathfinding, robotics |
| **Kruskal** | MST | O(E log E) | O(V) | Undirected | Yes | N/A | Sparse graphs, clustering |
| **Prim** | MST | O((V + E) log V) | O(V) | Undirected | Yes | N/A | Dense graphs, network design |

**Legend:**
- V = Number of vertices
- E = Number of edges
- MST = Minimum Spanning Tree

## When to Use Each Algorithm

### Use BFS When:
- Finding shortest path in **unweighted** graphs
- Finding all nodes within k distance
- Level-order traversal needed
- Checking bipartiteness of a graph
- Finding connected components

### Use DFS When:
- Detecting cycles in graphs
- Finding strongly connected components
- Topological sorting
- Solving puzzles (mazes, sudoku)
- Checking path existence

### Use Dijkstra When:
- Finding shortest path in **weighted graphs with non-negative weights**
- Single-source shortest path problem
- Real-time GPS navigation
- Network routing protocols (OSPF)

### Use Bellman-Ford When:
- Graph has **negative edge weights**
- Need to detect negative weight cycles
- Distributed systems (distance vector routing)
- Simple implementation more important than speed

### Use Floyd-Warshall When:
- Need **all-pairs** shortest paths
- Graph is small and dense
- Transitive closure needed
- Dynamic graph updates

### Use A* When:
- Have good heuristic function available
- Goal-directed search in games
- Robotics and AI pathfinding
- Need optimal path with better performance than Dijkstra

### Use Kruskal When:
- Graph is **sparse** (few edges)
- Edges are already sorted or easy to sort
- Need minimum spanning tree
- Building network connections

### Use Prim When:
- Graph is **dense** (many edges)
- Starting from specific vertex
- Need minimum spanning tree
- Priority queue available

## Complexity Analysis

Understanding time and space complexity is crucial for choosing the right algorithm:

### Time Complexity Considerations

**Graph Traversal (BFS/DFS):** O(V + E)
- Linear in the size of the graph
- Must visit every vertex and edge once
- Optimal for exploring graph structure

**Dijkstra's Algorithm:** O((V + E) log V) with priority queue
- Log factor comes from priority queue operations
- More efficient than O(V²) with simple array
- Excellent for sparse graphs

**Bellman-Ford:** O(VE)
- Relaxes all edges V-1 times
- Slower but handles negative weights
- Additional pass for negative cycle detection

**Floyd-Warshall:** O(V³)
- Three nested loops over all vertices
- Practical only for small graphs (V < 400)
- Solves all-pairs problem completely

**Kruskal's Algorithm:** O(E log E)
- Dominated by edge sorting
- Union-Find operations nearly constant
- Efficient for sparse graphs

**Prim's Algorithm:** O((V + E) log V) with binary heap
- Can be O(V²) with simple array
- Similar to Dijkstra in structure
- Better for dense graphs

### Space Complexity Considerations

Most graph algorithms require O(V) space for tracking visited vertices and storing results. Additional space includes:
- **Adjacency List:** O(V + E) - most space-efficient for sparse graphs
- **Adjacency Matrix:** O(V²) - simpler but memory-intensive
- **Priority Queue (Dijkstra/Prim):** O(V) - for vertex selection
- **Edge List (Kruskal):** O(E) - for sorting edges

## Common Applications

### Social Networks
- **BFS/DFS:** Finding connections, suggesting friends
- **Shortest Path:** Degrees of separation, friend recommendations
- **Community Detection:** Finding groups and clusters

### GPS and Navigation
- **Dijkstra/A*:** Turn-by-turn directions
- **Floyd-Warshall:** Distance matrices for route planning
- **Graph Traversal:** Road network exploration

### Compiler Design
- **DFS:** Dependency resolution, detecting circular dependencies
- **Topological Sort:** Build order determination
- **Cycle Detection:** Deadlock detection

### Network Design
- **MST Algorithms:** Optimal cable/pipeline laying
- **Shortest Path:** Data packet routing
- **Network Flow:** Bandwidth optimization

### Game Development
- **A*:** NPC pathfinding
- **DFS:** Maze generation and solving
- **BFS:** AI decision trees

### Web Crawling
- **BFS:** Crawling web pages level by level
- **DFS:** Deep exploration of website structure
- **Cycle Detection:** Avoiding infinite loops

## Navigation

### Subtopics
1. **[BFS & DFS Algorithms](01-BFS-DFS/)** - Graph traversal fundamentals
2. **[Shortest Path Algorithms](02-Shortest-Path/)** - Finding optimal routes
3. **[Minimum Spanning Tree Algorithms](03-MST-Algorithms/)** - Network optimization

### Related Topics
- **[← Back to Algorithms](../)** - Return to main algorithms section
- **[Data Structures](../../02-Data-Structures/)** - Graph representation structures
- **[Dynamic Programming](../04-Dynamic-Programming/)** - Related optimization techniques
- **[Collections Framework](../../01-Collections-Framework/)** - Java data structure implementations

---

**Next Steps:** Start with [BFS & DFS](01-BFS-DFS/) to build your foundation in graph traversal, then progress to [Shortest Path](02-Shortest-Path/) and [MST Algorithms](03-MST-Algorithms/) for advanced techniques.

**Pro Tip:** Practice implementing these algorithms from scratch to truly understand their mechanics. Then, learn to recognize which algorithm fits which problem—this pattern recognition is key to interview success and real-world problem-solving.
