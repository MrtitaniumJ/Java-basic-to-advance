/**
 * Graph Problems Implementation
 * 
 * Demonstrates fundamental graph algorithms:
 * - Depth-First Search (DFS)
 * - Breadth-First Search (BFS)
 * - Dijkstra's Shortest Path Algorithm
 * - Connected Components Detection
 * - Graph Cycle Detection
 */

import java.util.*;

public class GraphProblems {
    
    // ==================== GRAPH REPRESENTATION ====================
    /**
     * Graph using Adjacency List representation
     * Time Complexity: O(V + E) for most operations
     * Space Complexity: O(V + E)
     */
    static class Graph {
        private int vertices;
        private List<Integer>[] adjacencyList;
        private boolean isDirected;
        
        @SuppressWarnings("unchecked")
        public Graph(int vertices, boolean isDirected) {
            this.vertices = vertices;
            this.isDirected = isDirected;
            adjacencyList = new ArrayList[vertices];
            for (int i = 0; i < vertices; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
        }
        
        // Add edge to graph
        public void addEdge(int source, int destination) {
            adjacencyList[source].add(destination);
            if (!isDirected) {
                adjacencyList[destination].add(source);
            }
        }
        
        // Print adjacency list representation
        public void printGraph() {
            for (int i = 0; i < vertices; i++) {
                System.out.print("Vertex " + i + ": ");
                for (int neighbor : adjacencyList[i]) {
                    System.out.print(neighbor + " ");
                }
                System.out.println();
            }
        }
        
        // -------- DFS Implementation --------
        /**
         * Depth-First Search - explores graph in depth-first manner
         * Time: O(V + E)
         * Space: O(V) for visited set + O(V) for call stack
         */
        public void dfs(int startVertex) {
            boolean[] visited = new boolean[vertices];
            System.out.print("DFS from vertex " + startVertex + ": ");
            dfsRec(startVertex, visited);
            System.out.println();
        }
        
        private void dfsRec(int vertex, boolean[] visited) {
            visited[vertex] = true;
            System.out.print(vertex + " ");
            
            for (int neighbor : adjacencyList[vertex]) {
                if (!visited[neighbor]) {
                    dfsRec(neighbor, visited);
                }
            }
        }
        
        // -------- BFS Implementation --------
        /**
         * Breadth-First Search - explores graph level by level
         * Time: O(V + E)
         * Space: O(V) for visited set + O(V) for queue
         */
        public void bfs(int startVertex) {
            boolean[] visited = new boolean[vertices];
            Queue<Integer> queue = new LinkedList<>();
            
            visited[startVertex] = true;
            queue.add(startVertex);
            
            System.out.print("BFS from vertex " + startVertex + ": ");
            
            while (!queue.isEmpty()) {
                int vertex = queue.poll();
                System.out.print(vertex + " ");
                
                for (int neighbor : adjacencyList[vertex]) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.add(neighbor);
                    }
                }
            }
            System.out.println();
        }
        
        // -------- Connected Components --------
        /**
         * Find all connected components in undirected graph
         * Time: O(V + E)
         */
        public int findConnectedComponents() {
            boolean[] visited = new boolean[vertices];
            int componentCount = 0;
            
            for (int i = 0; i < vertices; i++) {
                if (!visited[i]) {
                    System.out.print("Component " + (componentCount + 1) + ": ");
                    dfsComponent(i, visited);
                    System.out.println();
                    componentCount++;
                }
            }
            
            return componentCount;
        }
        
        private void dfsComponent(int vertex, boolean[] visited) {
            visited[vertex] = true;
            System.out.print(vertex + " ");
            
            for (int neighbor : adjacencyList[vertex]) {
                if (!visited[neighbor]) {
                    dfsComponent(neighbor, visited);
                }
            }
        }
        
        // -------- Cycle Detection in Undirected Graph --------
        /**
         * Detect if cycle exists in undirected graph
         * Time: O(V + E)
         */
        public boolean hasCycle() {
            boolean[] visited = new boolean[vertices];
            
            for (int i = 0; i < vertices; i++) {
                if (!visited[i]) {
                    if (hasCycleDFS(i, -1, visited)) {
                        return true;
                    }
                }
            }
            return false;
        }
        
        private boolean hasCycleDFS(int vertex, int parent, boolean[] visited) {
            visited[vertex] = true;
            
            for (int neighbor : adjacencyList[vertex]) {
                if (!visited[neighbor]) {
                    if (hasCycleDFS(neighbor, vertex, visited)) {
                        return true;
                    }
                } else if (neighbor != parent) {
                    // Back edge found (indicates cycle)
                    return true;
                }
            }
            return false;
        }
        
        // -------- Cycle Detection in Directed Graph --------
        /**
         * Detect if cycle exists in directed graph
         * Time: O(V + E)
         */
        public boolean hasDirectedCycle() {
            int[] state = new int[vertices]; // 0: unvisited, 1: visiting, 2: visited
            
            for (int i = 0; i < vertices; i++) {
                if (state[i] == 0) {
                    if (hasCycleDFSDirected(i, state)) {
                        return true;
                    }
                }
            }
            return false;
        }
        
        private boolean hasCycleDFSDirected(int vertex, int[] state) {
            state[vertex] = 1; // Visiting
            
            for (int neighbor : adjacencyList[vertex]) {
                if (state[neighbor] == 1) {
                    return true; // Back edge in cycle
                }
                if (state[neighbor] == 0) {
                    if (hasCycleDFSDirected(neighbor, state)) {
                        return true;
                    }
                }
            }
            
            state[vertex] = 2; // Visited
            return false;
        }
    }
    
    // ==================== WEIGHTED GRAPH FOR SHORTEST PATH ====================
    static class WeightedGraph {
        private int vertices;
        private List<Edge>[] adjacencyList;
        
        static class Edge {
            int destination;
            int weight;
            
            Edge(int destination, int weight) {
                this.destination = destination;
                this.weight = weight;
            }
        }
        
        @SuppressWarnings("unchecked")
        public WeightedGraph(int vertices) {
            this.vertices = vertices;
            adjacencyList = new ArrayList[vertices];
            for (int i = 0; i < vertices; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
        }
        
        public void addEdge(int source, int destination, int weight) {
            adjacencyList[source].add(new Edge(destination, weight));
        }
        
        // -------- Dijkstra's Algorithm --------
        /**
         * Find shortest path from source to all vertices
         * Time: O((V + E) log V) with min-heap
         * Space: O(V)
         */
        public void dijkstra(int source) {
            int[] distance = new int[vertices];
            boolean[] visited = new boolean[vertices];
            PriorityQueue<Node> minHeap = new PriorityQueue<>();
            
            // Initialize distances
            for (int i = 0; i < vertices; i++) {
                distance[i] = Integer.MAX_VALUE;
            }
            distance[source] = 0;
            
            minHeap.add(new Node(source, 0));
            
            while (!minHeap.isEmpty()) {
                Node current = minHeap.poll();
                int u = current.vertex;
                
                if (visited[u]) continue;
                visited[u] = true;
                
                // Relax edges
                for (Edge edge : adjacencyList[u]) {
                    int v = edge.destination;
                    int weight = edge.weight;
                    
                    if (distance[u] + weight < distance[v]) {
                        distance[v] = distance[u] + weight;
                        minHeap.add(new Node(v, distance[v]));
                    }
                }
            }
            
            printDistances(source, distance);
        }
        
        private void printDistances(int source, int[] distance) {
            System.out.println("\nShortest distances from vertex " + source + ":");
            for (int i = 0; i < vertices; i++) {
                if (distance[i] == Integer.MAX_VALUE) {
                    System.out.println("Vertex " + i + ": Unreachable");
                } else {
                    System.out.println("Vertex " + i + ": " + distance[i]);
                }
            }
        }
        
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
    }
    
    // ==================== DEMONSTRATIONS ====================
    
    public static void demonstrateDFSandBFS() {
        System.out.println("\n========== DFS AND BFS ==========");
        Graph graph = new Graph(6, false);
        
        // Add edges
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        
        System.out.println("Graph Adjacency List:");
        graph.printGraph();
        
        graph.dfs(0);
        graph.bfs(0);
        
        System.out.println("\nStarting from different vertex:");
        graph.dfs(2);
        graph.bfs(2);
    }
    
    public static void demonstrateConnectedComponents() {
        System.out.println("\n========== CONNECTED COMPONENTS ==========");
        Graph graph = new Graph(7, false);
        
        // Component 1
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        
        // Component 2
        graph.addEdge(3, 4);
        
        // Component 3
        graph.addEdge(5, 6);
        
        System.out.println("Connected Components:");
        int components = graph.findConnectedComponents();
        System.out.println("Total components: " + components);
    }
    
    public static void demonstrateCycleDetection() {
        System.out.println("\n========== CYCLE DETECTION - UNDIRECTED GRAPH ==========");
        
        // Graph without cycle
        Graph graph1 = new Graph(4, false);
        graph1.addEdge(0, 1);
        graph1.addEdge(1, 2);
        graph1.addEdge(2, 3);
        System.out.println("Graph 1 has cycle: " + graph1.hasCycle());
        
        // Graph with cycle
        Graph graph2 = new Graph(4, false);
        graph2.addEdge(0, 1);
        graph2.addEdge(1, 2);
        graph2.addEdge(2, 3);
        graph2.addEdge(3, 0);
        System.out.println("Graph 2 has cycle: " + graph2.hasCycle());
        
        System.out.println("\n========== CYCLE DETECTION - DIRECTED GRAPH ==========");
        
        // Directed graph without cycle
        Graph digraph1 = new Graph(4, true);
        digraph1.addEdge(0, 1);
        digraph1.addEdge(1, 2);
        digraph1.addEdge(2, 3);
        System.out.println("Directed Graph 1 has cycle: " + digraph1.hasDirectedCycle());
        
        // Directed graph with cycle
        Graph digraph2 = new Graph(4, true);
        digraph2.addEdge(0, 1);
        digraph2.addEdge(1, 2);
        digraph2.addEdge(2, 3);
        digraph2.addEdge(3, 1);
        System.out.println("Directed Graph 2 has cycle: " + digraph2.hasDirectedCycle());
    }
    
    public static void demonstrateDijkstra() {
        System.out.println("\n========== DIJKSTRA'S SHORTEST PATH ==========");
        WeightedGraph graph = new WeightedGraph(6);
        
        // Add weighted edges
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 8);
        graph.addEdge(2, 4, 10);
        graph.addEdge(3, 4, 2);
        graph.addEdge(4, 5, 3);
        
        System.out.println("Graph edges:");
        System.out.println("0-1: 4, 0-2: 2, 1-2: 1, 1-3: 5, 2-3: 8, 2-4: 10, 3-4: 2, 4-5: 3");
        
        graph.dijkstra(0);
        
        System.out.println("\n--- From vertex 1 ---");
        graph.dijkstra(1);
    }
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║            GRAPH PROBLEMS IMPLEMENTATION             ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        demonstrateDFSandBFS();
        demonstrateConnectedComponents();
        demonstrateCycleDetection();
        demonstrateDijkstra();
        
        System.out.println("\n" + "=".repeat(55));
        System.out.println("All demonstrations completed successfully!");
        System.out.println("=".repeat(55));
    }
}
