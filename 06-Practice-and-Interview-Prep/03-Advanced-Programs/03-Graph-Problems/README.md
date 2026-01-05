# Graph Problems

## Overview

This program demonstrates fundamental graph algorithms essential for solving complex problems involving networks, relationships, and connectivity. Graph algorithms are crucial for:
- Social network analysis
- GPS navigation and routing
- Network optimization
- Game development
- Technical interview preparation

## Problem Analysis

### What We're Solving

#### 1. **Graph Representation**

**Adjacency List (Used Here):**
```
Vertex 0: [1, 2]
Vertex 1: [0, 3]
Vertex 2: [0, 3]
Vertex 3: [1, 2]
```

**Advantages:**
- Space: O(V + E)
- Efficient for sparse graphs
- Easy neighbor iteration

**Adjacency Matrix:**
```
   0 1 2 3
0 [0 1 1 0]
1 [1 0 0 1]
2 [1 0 0 1]
3 [0 1 1 0]
```

**Advantages:**
- O(1) edge lookup
- Good for dense graphs
- Space: O(V²)

#### 2. **Depth-First Search (DFS)**

**Strategy:** Explore as far as possible along each branch before backtracking

**Algorithm:**
1. Mark current vertex as visited
2. Visit all unvisited neighbors recursively
3. Backtrack when no unvisited neighbors remain

**Applications:**
- Topological sorting
- Detecting cycles
- Finding connected components
- Maze solving

**Complexity:**
- Time: O(V + E)
- Space: O(V) for recursion stack

**Example Trace (graph 0→1→3, 0→2):**
```
Start: 0
Visit 0, explore neighbors [1,2]
Visit 1, explore neighbors [0,3]
  (0 visited, skip)
  Visit 3, explore neighbors [1,2]
    (1 visited, skip)
    (2 visited, skip)
    Backtrack
Visit 2, explore neighbors [0,3]
  (both visited, skip)
```

#### 3. **Breadth-First Search (BFS)**

**Strategy:** Explore all vertices at distance k before vertices at distance k+1

**Algorithm:**
1. Add start vertex to queue
2. While queue not empty:
   - Dequeue vertex, mark visited
   - Enqueue all unvisited neighbors

**Applications:**
- Shortest path in unweighted graphs
- Level-order traversal
- Social network analysis
- Web crawler

**Complexity:**
- Time: O(V + E)
- Space: O(V) for queue

**Example Trace (same graph):**
```
Queue: [0]
Process 0: neighbors [1,2] → Queue: [1,2]
Process 1: neighbors [0,3] → Queue: [2,3] (0 visited)
Process 2: neighbors [0,3] → Queue: [3] (0,3 visited)
Process 3: neighbors [1,2] → Queue: [] (both visited)
Result: 0, 1, 2, 3
```

#### 4. **Connected Components**

**Definition:** Maximal set of vertices where every pair has a path between them

**Algorithm:**
- Use DFS/BFS from each unvisited vertex
- Each iteration finds one component

**Real-world Examples:**
- Social network clusters
- Computer network segments
- Molecule structures

#### 5. **Cycle Detection**

**Undirected Graph:**
- Use parent tracking in DFS
- Back edge (edge to visited ancestor) = cycle
- O(V + E) time

**Directed Graph:**
- Use 3-state DFS: unvisited, visiting, visited
- Back edge to "visiting" vertex = cycle
- O(V + E) time

#### 6. **Dijkstra's Algorithm (Shortest Path)**

**Problem:** Find shortest path from source to all vertices in weighted graph

**Requirements:**
- Non-negative edge weights
- Connected graph (otherwise unreachable vertices)

**Algorithm:**
1. Initialize distance[source] = 0, others = ∞
2. Use min-heap to always process nearest unvisited vertex
3. For each vertex, relax all outgoing edges:
   - If distance[u] + weight < distance[v], update distance[v]
4. Continue until all vertices processed

**Complexity:**
- Time: O((V + E) log V) with binary heap
- Space: O(V)

**Example:**
```
Graph:
  0 --4-- 1
  |   \    |
  2    1   5
  |    \   |
  8 ----3--8 --- 2 ---- 4
               |
               3 ---- 5

From vertex 0:
0: 0
1: 4
2: 2
3: 10 (via 0→1 or 0→2→1)
4: 12 (via 0→2→1→3)
5: 15 (via 0→2→1→3→4)
```

## Design Decisions

### 1. Adjacency List Representation
```java
List<Integer>[] adjacencyList;
```

**Why:**
- Efficient for sparse graphs (most real-world cases)
- Fast neighbor iteration
- Natural for DFS/BFS algorithms

### 2. Separate Classes for Weighted/Unweighted
- `Graph`: for DFS, BFS, cycle detection
- `WeightedGraph`: for Dijkstra's algorithm

**Advantage:** Clear separation of concerns

### 3. Generic Visited Array
```java
boolean[] visited = new boolean[vertices];
```

**Alternative:** HashSet for non-sequential vertices
- Choose based on vertex numbering scheme

### 4. Priority Queue for Dijkstra's
```java
PriorityQueue<Node> minHeap;
```

**Why:** O(log V) insertion and extraction
- Ensures we always process nearest vertex
- Correct greedy choice

### 5. State-based Cycle Detection (Directed)
```java
int[] state = new int[vertices]; // 0: white, 1: gray, 2: black
```

**Why:**
- Distinguishes between visited nodes
- Detects back edges (gray to gray)
- More efficient than separate arrays

## Complexity Analysis

### Search Algorithms

| Algorithm | Time | Space | Notes |
|-----------|------|-------|-------|
| DFS | O(V+E) | O(V) | Recursion stack |
| BFS | O(V+E) | O(V) | Queue storage |
| Connected Components | O(V+E) | O(V) | Multiple DFS |

### Cycle Detection

| Type | Algorithm | Time | Space |
|------|-----------|------|-------|
| Undirected | DFS + Parent | O(V+E) | O(V) |
| Directed | 3-state DFS | O(V+E) | O(V) |

### Shortest Path

| Algorithm | Time (Worst) | Space | Weights |
|-----------|--------------|-------|---------|
| BFS | O(V+E) | O(V) | Unit only |
| Dijkstra | O((V+E)logV) | O(V) | Non-negative |
| Bellman-Ford | O(VE) | O(V) | Any weights |

## Sample Input/Output

### Running the Program

```powershell
javac GraphProblems.java
java GraphProblems
```

### Expected Output

```
╔════════════════════════════════════════════════════════╗
║            GRAPH PROBLEMS IMPLEMENTATION             ║
╚════════════════════════════════════════════════════════╝

========== DFS AND BFS ==========
Graph Adjacency List:
Vertex 0: 1 2
Vertex 1: 0 3
Vertex 2: 0 3
Vertex 3: 1 2
Vertex 4: 3
Vertex 5: 4

DFS from vertex 0: 0 1 3 2 4 5
BFS from vertex 0: 0 1 2 3 4 5

Starting from different vertex:
DFS from vertex 2: 2 0 1 3 4 5
BFS from vertex 2: 2 0 3 1 4 5

========== CONNECTED COMPONENTS ==========
Connected Components:
Component 1: 0 1 2 3 4 5
Component 2: 6
Total components: 2

========== CYCLE DETECTION - UNDIRECTED GRAPH ==========
Graph 1 has cycle: false
Graph 2 has cycle: true

========== CYCLE DETECTION - DIRECTED GRAPH ==========
Directed Graph 1 has cycle: false
Directed Graph 2 has cycle: true

========== DIJKSTRA'S SHORTEST PATH ==========
Graph edges:
0-1: 4, 0-2: 2, 1-2: 1, 1-3: 5, 2-3: 8, 2-4: 10, 3-4: 2, 4-5: 3

Shortest distances from vertex 0:
Vertex 0: 0
Vertex 1: 3
Vertex 2: 2
Vertex 3: 8
Vertex 4: 10
Vertex 5: 13

--- From vertex 1 ---
Shortest distances from vertex 1:
Vertex 0: 4
Vertex 1: 0
Vertex 2: 1
Vertex 3: 5
Vertex 4: 7
Vertex 5: 10

=======================================================
All demonstrations completed successfully!
=======================================================
```

## Real-World Applications

### 1. **Social Networks**
- DFS/BFS: Find connected friend groups
- Shortest path: Degrees of separation
- Cycle detection: Detect cliques

### 2. **GPS Navigation**
- Dijkstra's: Find shortest route
- A* algorithm: Optimize with heuristics
- Connected components: Route existence

### 3. **Web Crawling**
- BFS: Crawl all pages level by level
- Cycle detection: Detect infinite crawls
- DFS: Deep exploration of single path

### 4. **Network Routing**
- Dijkstra's: Minimize latency
- Connected components: Network partitions
- Cycle detection: Loop prevention

### 5. **Game Development**
- BFS: AI pathfinding (equal-cost terrain)
- DFS: Maze generation
- Cycle detection: Deadlock prevention

## Variations and Challenges

### Challenge 1: Topological Sorting (DAG)
Order vertices such that edges go from earlier to later.

```java
public void topologicalSort() {
    // Use DFS with post-order processing
    // Add vertex to result when all descendants processed
}
```

### Challenge 2: Strongly Connected Components
Find groups where every vertex reachable from every other.

```
Kosaraju's Algorithm:
1. DFS on original, track finish times
2. DFS on reversed graph in finish order
3. Each DFS tree is an SCC
```

### Challenge 3: All Pairs Shortest Path (Floyd-Warshall)
Find shortest paths between all vertex pairs.

```java
Time: O(V³)
Space: O(V²)
Handles negative weights (no negative cycles)
```

### Challenge 4: Minimum Spanning Tree (Kruskal's/Prim's)
Connect all vertices with minimum total edge weight.

```java
Kruskal: Sort edges, union-find, O(E log E)
Prim's: Similar to Dijkstra's, O((V+E)logV)
```

### Challenge 5: Bipartite Graph Check
Determine if vertices can be split into two groups with edges only between groups.

```java
public boolean isBipartite() {
    // BFS with 2-coloring
    // If any vertex has same-color neighbor, not bipartite
}
```

### Challenge 6: Word Ladder Problem
Find shortest transformation path between two words (classic BFS problem).

```
Example: "hit" → "hot" → "dot" → "dog" → "log" → "cog"
Edges exist between words differing by one letter
```

## Interview Tips

### When Discussing Graph Problems

1. **Clarify Graph Properties:**
   - Directed or undirected?
   - Weighted or unweighted?
   - Cyclic or acyclic?
   - Connected or disconnected?

2. **Choose Right Algorithm:**
   - Shortest path? Use Dijkstra's or BFS
   - Connectivity? Use DFS/BFS or Union-Find
   - Order? Use topological sort
   - All pairs? Use Floyd-Warshall

3. **Discuss Space-Time Trade-offs:**
   - DFS: Recursion vs. explicit stack
   - BFS: Queue overhead but clear level-order
   - Dijkstra's: Heap improves time but needs extra space

4. **Handle Edge Cases:**
   - Single vertex graph
   - Disconnected components
   - Self-loops
   - Multiple edges between vertices

### Common Questions

**Q: DFS vs. BFS - when to use which?**
A: DFS for connectivity/cycles, BFS for shortest path unweighted.

**Q: Why does Dijkstra's require non-negative weights?**
A: Greedy approach assumes nearest unvisited is optimal (fails with negatives).

**Q: How to detect cycle in directed graph efficiently?**
A: Use 3-state DFS with coloring (white/gray/black).

**Q: Space complexity of adjacency list?**
A: O(V + E) - includes all vertices and edges.

## Key Learnings

1. **Graph representation** choice impacts algorithm efficiency
2. **DFS/BFS** are building blocks for many algorithms
3. **Dijkstra's algorithm** crucial for weighted shortest path
4. **Cycle detection** varies between directed/undirected
5. **Connected components** analysis reveals structure

## Further Exploration

1. **Advanced Algorithms:** Bellman-Ford, Floyd-Warshall, A*
2. **Tree Algorithms:** MST (Kruskal's, Prim's)
3. **Flow Algorithms:** Max flow, min cut
4. **Matching:** Bipartite matching, Hungarian algorithm
5. **Graph Properties:** Planarity, coloring, cliques

---

**Compilation**: `javac GraphProblems.java`
**Execution**: `java GraphProblems`
**Difficulty**: Intermediate to Advanced
**Topics**: Graph Algorithms, DFS, BFS, Shortest Path, Dijkstra's
**Prerequisites**: Basic Java, Collections Framework, Algorithm basics
