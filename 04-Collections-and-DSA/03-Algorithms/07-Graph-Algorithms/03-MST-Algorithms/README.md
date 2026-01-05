# Minimum Spanning Tree (MST) Algorithms

## Table of Contents
1. [Concept Overview](#concept-overview)
2. [Kruskal's Algorithm](#kruskals-algorithm)
3. [Prim's Algorithm](#prims-algorithm)
4. [Algorithm Comparison](#algorithm-comparison)
5. [Real-World Applications](#real-world-applications)
6. [Complete Java Implementation](#complete-java-implementation)
7. [Complexity Analysis](#complexity-analysis)
8. [Edge Cases](#edge-cases)

## Concept Overview

A **Minimum Spanning Tree (MST)** is a subset of edges in a weighted, connected, undirected graph that connects all vertices with the minimum total edge weight and without forming any cycles. Key characteristics:

- **Acyclic**: Contains no cycles (tree property)
- **Connected**: Spans all vertices in the graph
- **Minimal Weight**: Sum of edge weights is minimized
- **Unique (Usually)**: If all edge weights are distinct, MST is unique
- **n-1 Edges**: For a graph with n vertices, MST contains exactly n-1 edges

### Why MST Matters
MSTs solve optimization problems in network design, including minimal cost wiring, telecommunication networks, and cluster analysis. They represent the most cost-effective way to connect all components.

## Kruskal's Algorithm

### Concept
Kruskal's algorithm builds the MST by greedily selecting the smallest weighted edge that doesn't create a cycle. It uses the **Union-Find (Disjoint Set Union)** data structure to efficiently detect cycles.

### Algorithm Steps
1. Sort all edges in ascending order of weight
2. Initialize a disjoint set with each vertex as its own parent
3. Iterate through sorted edges:
   - Check if adding this edge creates a cycle using Union-Find
   - If endpoints are in different sets, add edge to MST and union the sets
   - If endpoints are in the same set, skip (would create cycle)
4. Continue until MST has n-1 edges

### Why Union-Find?
Union-Find provides O(log n) operations with path compression and union by rank, making cycle detection efficient without building the actual tree structure.

### Complexity
- **Time**: O(E log E) for sorting edges + O(E α(V)) for Union-Find operations ≈ **O(E log E)**
- **Space**: O(V + E) for graph representation and disjoint set structure

### Advantages
- Works well with sparse graphs
- Simple implementation
- Naturally produces deterministic results
- Easy to parallelize edge processing

### Disadvantages
- Requires sorting all edges
- Less cache-friendly than Prim's for some operations

## Prim's Algorithm

### Concept
Prim's algorithm builds the MST by starting from a vertex and greedily expanding to the nearest unvisited vertex. It maintains a growing set of MST vertices.

### Algorithm Steps (Adjacency Matrix)
1. Mark the starting vertex as visited
2. Add all edges from the starting vertex to a priority queue
3. While unvisited vertices remain:
   - Extract the minimum weight edge connecting a visited and unvisited vertex
   - Add the unvisited endpoint to the MST
   - Add all edges from the new vertex to unvisited vertices to the queue

### Complexity Variants
- **Without Priority Queue (Matrix)**: O(V²)
- **With Priority Queue (Adjacency List)**: O(E log V) with Binary Heap

### Advantages
- O(V²) version is simple and efficient for dense graphs
- Works incrementally from one vertex
- Good cache locality for matrix implementation
- Priority queue version efficient for sparse graphs

### Disadvantages
- O(V²) version requires dense adjacency matrix
- Priority queue version has higher constant factors

## Algorithm Comparison

| Aspect | Kruskal's | Prim's (Matrix) | Prim's (Priority Queue) |
|--------|-----------|-----------------|------------------------|
| **Time Complexity** | O(E log E) | O(V²) | O(E log V) |
| **Space Complexity** | O(V + E) | O(V²) | O(E + V log V) |
| **Best For** | Sparse graphs | Dense graphs | Very sparse graphs |
| **Data Structure** | Union-Find | Adjacency Matrix | Adjacency List + Heap |
| **Starting Point** | Edge-based | Vertex-based | Vertex-based |
| **Cycle Detection** | Explicit (Union-Find) | Implicit (visited set) |Implicit (visited set) |
| **Parallelization** | Easier | Harder | Harder |

### Choosing the Right Algorithm
- **Sparse graphs (E << V²)**: Use Prim's with Priority Queue or Kruskal's
- **Dense graphs (E ≈ V²)**: Use Prim's with Adjacency Matrix
- **General purpose**: Kruskal's with Union-Find is reliable and straightforward

## Real-World Applications

### Network Design
Building minimum-cost telecommunications networks where vertices represent cities and edges represent communication lines. MST ensures connectivity with minimal infrastructure investment.

### Power Distribution
Designing electrical grid systems where MST minimizes copper wiring while ensuring all locations receive power. The cost savings scale significantly with network size.

### Road Networks
Planning highways connecting cities with minimum total road construction cost. MST guarantees connectivity while minimizing construction expenditure.

### Cluster Analysis
In machine learning, MST helps identify natural data clusters by removing the longest edges, revealing the underlying structure of data.

### DNA Sequence Analysis
Connecting genes with minimum total distance for phylogenetic tree construction in bioinformatics applications.

## Complete Java Implementation

```java
import java.util.*;

// ============================================
// UNION-FIND (DISJOINT SET UNION) DATA STRUCTURE
// ============================================

class UnionFind {
    private int[] parent;
    private int[] rank;
    private int[] size;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
            size[i] = 1;
        }
    }

    // Path compression: make every node point directly to root
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }

    // Union by rank: attach smaller tree under larger tree
    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return false; // Already in same set (cycle detected)
        }

        // Union by rank for optimization
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
            size[rootY] += size[rootX];
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
            size[rootX] += size[rootY];
        } else {
            parent[rootY] = rootX;
            size[rootX] += size[rootY];
            rank[rootX]++;
        }
        return true;
    }

    public int getSize(int x) {
        return size[find(x)];
    }

    public boolean isConnected(int x, int y) {
        return find(x) == find(y);
    }
}

// ============================================
// EDGE CLASS FOR KRUSKAL'S ALGORITHM
// ============================================

class Edge implements Comparable<Edge> {
    int u;
    int v;
    int weight;

    public Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return String.format("Edge(%d-%d, weight=%d)", u, v, weight);
    }
}

// ============================================
// KRUSKAL'S ALGORITHM IMPLEMENTATION
// ============================================

class KruskalMST {
    private List<Edge> edges;
    private int vertices;
    private int totalWeight;
    private List<Edge> mstEdges;

    public KruskalMST(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
        this.mstEdges = new ArrayList<>();
        this.totalWeight = 0;
    }

    public void addEdge(int u, int v, int weight) {
        if (u >= vertices || v >= vertices || u < 0 || v < 0) {
            throw new IllegalArgumentException("Invalid vertex");
        }
        edges.add(new Edge(u, v, weight));
    }

    public void computeMST() {
        // Step 1: Sort edges by weight
        Collections.sort(edges);

        // Step 2: Initialize Union-Find
        UnionFind uf = new UnionFind(vertices);

        // Step 3: Process edges in order
        mstEdges.clear();
        totalWeight = 0;

        for (Edge edge : edges) {
            // Check if vertices are already connected
            if (!uf.isConnected(edge.u, edge.v)) {
                // Add edge to MST (no cycle)
                mstEdges.add(edge);
                totalWeight += edge.weight;
                uf.union(edge.u, edge.v);

                // Early termination: MST complete with n-1 edges
                if (mstEdges.size() == vertices - 1) {
                    break;
                }
            }
            // else: Vertices already connected; edge creates cycle, skip it
        }

        // Validate: Check if graph is connected
        if (mstEdges.size() != vertices - 1) {
            throw new IllegalStateException("Graph is not connected; cannot form spanning tree");
        }
    }

    public void displayMST() {
        System.out.println("\n=== KRUSKAL'S MST ===");
        System.out.println("MST Edges:");
        for (Edge edge : mstEdges) {
            System.out.println("  " + edge);
        }
        System.out.println("Total Weight: " + totalWeight);
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public List<Edge> getMSTEdges() {
        return new ArrayList<>(mstEdges);
    }
}

// ============================================
// PRIM'S ALGORITHM - ADJACENCY MATRIX VERSION
// ============================================

class PrimMST_Matrix {
    private int[][] adjMatrix;
    private int vertices;
    private int totalWeight;
    private List<Edge> mstEdges;
    private static final int INFINITY = Integer.MAX_VALUE / 2;

    public PrimMST_Matrix(int vertices) {
        this.vertices = vertices;
        this.adjMatrix = new int[vertices][vertices];
        this.mstEdges = new ArrayList<>();
        this.totalWeight = 0;

        // Initialize with infinity (no edges)
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                adjMatrix[i][j] = INFINITY;
            }
        }
    }

    public void addEdge(int u, int v, int weight) {
        if (u >= vertices || v >= vertices || u < 0 || v < 0) {
            throw new IllegalArgumentException("Invalid vertex");
        }
        adjMatrix[u][v] = weight;
        adjMatrix[v][u] = weight; // Undirected graph
    }

    public void computeMST(int startVertex) {
        boolean[] visited = new boolean[vertices];
        int[] key = new int[vertices]; // Minimum weight to add vertex
        int[] parent = new int[vertices]; // Parent in MST

        // Initialize key values and parent
        Arrays.fill(key, INFINITY);
        Arrays.fill(parent, -1);
        key[startVertex] = 0;

        mstEdges.clear();
        totalWeight = 0;

        for (int count = 0; count < vertices; count++) {
            // Find minimum key vertex not yet in MST
            int u = -1;
            for (int v = 0; v < vertices; v++) {
                if (!visited[v] && (u == -1 || key[v] < key[u])) {
                    u = v;
                }
            }

            if (u == -1 || key[u] == INFINITY) {
                throw new IllegalStateException("Graph is not connected");
            }

            visited[u] = true;

            // Add edge to MST (except for start vertex)
            if (parent[u] != -1) {
                Edge edge = new Edge(parent[u], u, key[u]);
                mstEdges.add(edge);
                totalWeight += key[u];
            }

            // Update key values of adjacent vertices
            for (int v = 0; v < vertices; v++) {
                if (adjMatrix[u][v] != INFINITY && !visited[v] && adjMatrix[u][v] < key[v]) {
                    key[v] = adjMatrix[u][v];
                    parent[v] = u;
                }
            }
        }
    }

    public void displayMST() {
        System.out.println("\n=== PRIM'S MST (MATRIX) ===");
        System.out.println("MST Edges:");
        for (Edge edge : mstEdges) {
            System.out.println("  " + edge);
        }
        System.out.println("Total Weight: " + totalWeight);
    }

    public int getTotalWeight() {
        return totalWeight;
    }
}

// ============================================
// PRIM'S ALGORITHM - PRIORITY QUEUE VERSION
// ============================================

class PrimMST_PQ {
    private List<List<Edge>> adjList;
    private int vertices;
    private int totalWeight;
    private List<Edge> mstEdges;

    public PrimMST_PQ(int vertices) {
        this.vertices = vertices;
        this.adjList = new ArrayList<>();
        this.mstEdges = new ArrayList<>();
        this.totalWeight = 0;

        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int weight) {
        if (u >= vertices || v >= vertices || u < 0 || v < 0) {
            throw new IllegalArgumentException("Invalid vertex");
        }
        adjList.get(u).add(new Edge(u, v, weight));
        adjList.get(v).add(new Edge(v, u, weight)); // Undirected
    }

    public void computeMST(int startVertex) {
        boolean[] inMST = new boolean[vertices];
        int[] key = new int[vertices];
        int[] parent = new int[vertices];

        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        key[startVertex] = 0;

        // Min-heap: (weight, vertex)
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        pq.offer(new int[]{0, startVertex});

        mstEdges.clear();
        totalWeight = 0;

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int weight = current[0];
            int u = current[1];

            if (inMST[u]) {
                continue; // Already processed
            }

            inMST[u] = true;

            // Add edge to MST (except start)
            if (parent[u] != -1) {
                Edge edge = new Edge(parent[u], u, key[u]);
                mstEdges.add(edge);
                totalWeight += key[u];
            }

            // Explore adjacent vertices
            for (Edge edge : adjList.get(u)) {
                int v = edge.v;
                if (!inMST[v] && edge.weight < key[v]) {
                    key[v] = edge.weight;
                    parent[v] = u;
                    pq.offer(new int[]{key[v], v});
                }
            }
        }

        if (mstEdges.size() != vertices - 1) {
            throw new IllegalStateException("Graph is not connected");
        }
    }

    public void displayMST() {
        System.out.println("\n=== PRIM'S MST (PRIORITY QUEUE) ===");
        System.out.println("MST Edges:");
        for (Edge edge : mstEdges) {
            System.out.println("  " + edge);
        }
        System.out.println("Total Weight: " + totalWeight);
    }

    public int getTotalWeight() {
        return totalWeight;
    }
}

// ============================================
// TEST AND DEMONSTRATION
// ============================================

public class MSTDemo {
    public static void main(String[] args) {
        System.out.println("=== MINIMUM SPANNING TREE ALGORITHMS ===\n");

        // Create test graph
        int vertices = 6;
        createAndTestGraphs(vertices);

        // Test disconnected graph (edge case)
        System.out.println("\n\n=== EDGE CASE: DISCONNECTED GRAPH ===");
        testDisconnectedGraph();

        // Test single vertex
        System.out.println("\n=== EDGE CASE: SINGLE VERTEX ===");
        testSingleVertex();

        // Performance comparison
        System.out.println("\n\n=== PERFORMANCE CHARACTERISTICS ===");
        demonstratePerformance();
    }

    private static void createAndTestGraphs(int vertices) {
        // Kruskal's Algorithm
        KruskalMST kruskal = new KruskalMST(vertices);
        addEdgesToGraph(kruskal);
        try {
            kruskal.computeMST();
            kruskal.displayMST();
        } catch (IllegalStateException e) {
            System.out.println("Kruskal Error: " + e.getMessage());
        }

        // Prim's Algorithm (Matrix Version)
        PrimMST_Matrix primMatrix = new PrimMST_Matrix(vertices);
        addEdgesToMatrixGraph(primMatrix);
        try {
            primMatrix.computeMST(0);
            primMatrix.displayMST();
        } catch (IllegalStateException e) {
            System.out.println("Prim (Matrix) Error: " + e.getMessage());
        }

        // Prim's Algorithm (Priority Queue Version)
        PrimMST_PQ primPQ = new PrimMST_PQ(vertices);
        addEdgesToGraph(primPQ);
        try {
            primPQ.computeMST(0);
            primPQ.displayMST();
        } catch (IllegalStateException e) {
            System.out.println("Prim (PQ) Error: " + e.getMessage());
        }
    }

    private static void addEdgesToGraph(KruskalMST graph) {
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 8);
        graph.addEdge(2, 4, 10);
        graph.addEdge(3, 4, 2);
        graph.addEdge(3, 5, 6);
        graph.addEdge(4, 5, 3);
    }

    private static void addEdgesToGraph(PrimMST_PQ graph) {
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 8);
        graph.addEdge(2, 4, 10);
        graph.addEdge(3, 4, 2);
        graph.addEdge(3, 5, 6);
        graph.addEdge(4, 5, 3);
    }

    private static void addEdgesToMatrixGraph(PrimMST_Matrix graph) {
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 8);
        graph.addEdge(2, 4, 10);
        graph.addEdge(3, 4, 2);
        graph.addEdge(3, 5, 6);
        graph.addEdge(4, 5, 3);
    }

    private static void testDisconnectedGraph() {
        KruskalMST kruskal = new KruskalMST(4);
        kruskal.addEdge(0, 1, 1);
        kruskal.addEdge(2, 3, 1); // Disconnected component
        
        try {
            kruskal.computeMST();
        } catch (IllegalStateException e) {
            System.out.println("Correctly detected disconnection: " + e.getMessage());
        }
    }

    private static void testSingleVertex() {
        KruskalMST kruskal = new KruskalMST(1);
        kruskal.computeMST();
        System.out.println("Single vertex MST weight: " + kruskal.getTotalWeight());
    }

    private static void demonstratePerformance() {
        System.out.println("Graph Type\t| Kruskal's\t| Prim's (Matrix)\t| Prim's (PQ)");
        System.out.println("-------------|-----------|------------------|----------");
        System.out.println("Sparse\t\t| O(E log E)| O(V²)\t\t| O(E log V)");
        System.out.println("Dense\t\t| O(E log E)| O(V²) ✓\t\t| O(E log V)");
        System.out.println("General\t\t| O(E log E) ✓| O(V²)\t\t| O(E log V)");
    }
}
```

## Complexity Analysis

### Kruskal's Algorithm
- **Time Complexity**: 
  - Sorting: O(E log E)
  - Union-Find operations: O(E α(V)) where α is inverse Ackermann (nearly constant)
  - **Total**: **O(E log E)**
- **Space Complexity**: O(V + E)

### Prim's Algorithm (Adjacency Matrix)
- **Time Complexity**: **O(V²)**
  - Finding minimum: O(V) per iteration
  - V iterations total
- **Space Complexity**: O(V²)
- **Best for**: Dense graphs where E ≈ V²

### Prim's Algorithm (Priority Queue)
- **Time Complexity**: **O(E log V)**
  - Insert/Extract: O(log V) per edge
  - E edge operations maximum
- **Space Complexity**: O(E + V log V)
- **Best for**: Sparse graphs

## Edge Cases

1. **Disconnected Graph**: Both algorithms throw `IllegalStateException` when unable to form spanning tree
2. **Single Vertex**: MST weight is 0 with no edges
3. **Two Vertices**: MST contains one edge of minimum weight
4. **Duplicate Edge Weights**: MST is valid but may differ (both are minimum)
5. **Self-loops**: Should be ignored as they create cycles
6. **Negative Weights**: MST algorithms work with negative weights (unlike shortest path)
7. **Zero-weight Edges**: Valid; algorithms handle correctly
8. **Large Graphs**: Kruskal's excels with sparse graphs; Prim's with dense graphs

## Key Takeaways

✓ **Kruskal's** is reliable, handles sparse graphs well, uses Union-Find efficiently
✓ **Prim's (Matrix)** is optimal for dense graphs with simple O(V²) implementation
✓ **Prim's (PQ)** balances performance across graph densities
✓ Both produce valid MSTs with identical minimum total weight
✓ Union-Find with path compression and union by rank is crucial for Kruskal's efficiency
✓ Choice depends on graph density, implementation constraints, and parallelization needs
