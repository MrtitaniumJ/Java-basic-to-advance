# Greedy Algorithms

## Overview

Greedy algorithms represent a fundamental algorithmic paradigm where decisions are made by selecting the locally optimal choice at each step with the hope of finding a global optimum. Unlike dynamic programming, which explores multiple paths and caches results, greedy algorithms commit to choices immediately without reconsideration. This approach trades optimality guarantees for efficiency, making greedy algorithms invaluable for specific problem classes.

## Fundamentals

### What is a Greedy Algorithm?

A greedy algorithm builds solutions incrementally by making a sequence of choices, where each choice appears optimal at the moment of decision. The algorithm never backtracks or reconsiders previous choices. This simplicity enables elegant solutions with excellent time complexity for problems where greedy selection leads to global optimality.

### The Greedy Paradigm

```
1. Identify a selection criterion (which element to pick next)
2. Make a greedy choice based on this criterion
3. Reduce the problem to a smaller subproblem
4. Recursively solve the subproblem
5. Combine choices to form the final solution
```

## Key Properties

### 1. Greedy Choice Property

A problem exhibits the **greedy choice property** if a globally optimal solution can be constructed by making locally optimal (greedy) choices. This property is crucial and must be proven for any greedy algorithm's correctness.

**Example**: In the Activity Selection problem, choosing the activity that ends earliest leaves the maximum time for remaining activities—a provably optimal choice.

### 2. Optimal Substructure

Optimal substructure means that an optimal solution to the problem contains optimal solutions to subproblems. When you make a greedy choice, the remaining problem must have the same structure.

**Proof Strategy**: Assume an optimal solution exists that doesn't use your greedy choice. Show that you can replace the non-greedy part with the greedy choice without worsening the solution.

## When Greedy Works vs. Fails

### Successful Greedy Problems
- **Activity Selection**: Choosing earliest-ending activities is optimal
- **Huffman Coding**: Building trees bottom-up with minimum-weight items is optimal
- **Fractional Knapsack**: Selecting items by value-to-weight ratio is optimal
- **Dijkstra's Algorithm**: Selecting the nearest unvisited vertex extends shortest paths
- **Minimum Spanning Trees** (Prim's, Kruskal's): Adding minimum-weight edges maintains optimality

### Where Greedy Fails
- **0/1 Knapsack**: Choosing items by value-to-weight ratio can miss optimal solutions
- **Coin Change**: Greedy coin selection doesn't always minimize coin count (e.g., coins {1,3,4} with amount 6)
- **Job Sequencing with Deadlines**: Simple greedy choices may violate deadline constraints
- **Traveling Salesman Problem**: Nearest neighbor heuristic often produces suboptimal tours

---

## Classic Greedy Problems with Java Implementations

### 1. Activity Selection Problem

**Problem**: Select the maximum number of non-overlapping activities from a list of activities with start and end times.

**Greedy Insight**: Always select the activity that ends earliest, as it leaves maximum time for remaining activities.

```java
import java.util.*;

public class ActivitySelection {
    static class Activity {
        int start, end;
        
        Activity(int start, int end) {
            this.start = start;
            this.end = end;
        }
        
        public String toString() {
            return "[" + start + "-" + end + "]";
        }
    }
    
    public static List<Activity> selectActivities(Activity[] activities) {
        // Sort by end time
        Arrays.sort(activities, (a, b) -> Integer.compare(a.end, b.end));
        
        List<Activity> selected = new ArrayList<>();
        selected.add(activities[0]);
        int lastEnd = activities[0].end;
        
        for (int i = 1; i < activities.length; i++) {
            if (activities[i].start >= lastEnd) {
                selected.add(activities[i]);
                lastEnd = activities[i].end;
            }
        }
        return selected;
    }
    
    public static void main(String[] args) {
        Activity[] activities = {
            new Activity(1, 3),
            new Activity(2, 5),
            new Activity(4, 6),
            new Activity(6, 7),
            new Activity(5, 8),
            new Activity(8, 9)
        };
        
        List<Activity> result = selectActivities(activities);
        System.out.println("Selected activities: " + result);
        System.out.println("Maximum activities: " + result.size());
    }
}
```

**Complexity**: O(n log n) for sorting + O(n) for selection = **O(n log n)**  
**Proof**: For any optimal solution, we can replace the activity that ends latest with our earliest-ending choice without reducing the count.

---

### 2. Huffman Coding

**Problem**: Build a binary tree for optimal data compression by assigning variable-length codes to characters based on frequency.

**Greedy Insight**: Repeatedly merge the two nodes with smallest frequencies to build the encoding tree.

```java
import java.util.*;

public class HuffmanCoding {
    static class Node implements Comparable<Node> {
        char ch;
        int freq;
        Node left, right;
        
        Node(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
            this.left = this.right = null;
        }
        
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.freq, other.freq);
        }
    }
    
    public static Node buildHuffmanTree(Map<Character, Integer> frequencies) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            pq.offer(new Node(entry.getKey(), entry.getValue()));
        }
        
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            
            Node parent = new Node('\0', left.freq + right.freq);
            parent.left = left;
            parent.right = right;
            pq.offer(parent);
        }
        
        return pq.poll();
    }
    
    public static void generateCodes(Node root, String code, 
                                     Map<Character, String> codes) {
        if (root == null) return;
        
        if (root.left == null && root.right == null) {
            codes.put(root.ch, code.isEmpty() ? "0" : code);
            return;
        }
        
        generateCodes(root.left, code + "0", codes);
        generateCodes(root.right, code + "1", codes);
    }
    
    public static void main(String[] args) {
        Map<Character, Integer> frequencies = new HashMap<>();
        String text = "ABRACADABRA";
        for (char ch : text.toCharArray()) {
            frequencies.put(ch, frequencies.getOrDefault(ch, 0) + 1);
        }
        
        Node root = buildHuffmanTree(frequencies);
        Map<Character, String> codes = new HashMap<>();
        generateCodes(root, "", codes);
        
        System.out.println("Huffman Codes:");
        codes.forEach((ch, code) -> 
            System.out.println(ch + ": " + code + " (freq: " + 
                             frequencies.get(ch) + ")")
        );
    }
}
```

**Complexity**: O(n log n) where n is number of unique characters  
**Optimality**: Huffman codes achieve minimum average codeword length—proven by contradiction using the greedy property.

---

### 3. Fractional Knapsack

**Problem**: Maximize value in a knapsack of capacity W, allowing fractional items.

**Greedy Insight**: Sort items by value-to-weight ratio and greedily select items.

```java
import java.util.*;

public class FractionalKnapsack {
    static class Item implements Comparable<Item> {
        int value, weight;
        
        Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }
        
        double ratio() {
            return (double) value / weight;
        }
        
        @Override
        public int compareTo(Item other) {
            return Double.compare(other.ratio(), this.ratio());
        }
    }
    
    public static double maxValue(Item[] items, int capacity) {
        Arrays.sort(items);
        
        double totalValue = 0;
        int remainingCapacity = capacity;
        
        for (Item item : items) {
            if (remainingCapacity <= 0) break;
            
            if (item.weight <= remainingCapacity) {
                totalValue += item.value;
                remainingCapacity -= item.weight;
            } else {
                totalValue += item.ratio() * remainingCapacity;
                remainingCapacity = 0;
            }
        }
        
        return totalValue;
    }
    
    public static void main(String[] args) {
        Item[] items = {
            new Item(60, 10),
            new Item(100, 20),
            new Item(120, 30)
        };
        
        System.out.println("Maximum value: " + maxValue(items, 50));
    }
}
```

**Complexity**: O(n log n)  
**Why Greedy Works**: At each step, selecting the highest ratio-per-unit weight maximizes value density. For fractional items, this leads to global optimality.

---

### 4. Dijkstra's Shortest Path Algorithm

**Problem**: Find shortest paths from a source to all vertices in a weighted graph.

**Greedy Insight**: Always extend the path from the nearest unvisited vertex.

```java
import java.util.*;

public class DijkstraAlgorithm {
    static class Edge {
        int to, weight;
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
    
    public static int[] dijkstra(List<Edge>[] graph, int source) {
        int n = graph.length;
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> 
            Integer.compare(a[0], b[0])
        );
        pq.offer(new int[]{0, source});
        
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int d = current[0], u = current[1];
            
            if (visited[u]) continue;
            visited[u] = true;
            
            for (Edge edge : graph[u]) {
                int v = edge.to;
                int weight = edge.weight;
                
                if (!visited[v] && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.offer(new int[]{dist[v], v});
                }
            }
        }
        
        return dist;
    }
    
    public static void main(String[] args) {
        int n = 5;
        @SuppressWarnings("unchecked")
        List<Edge>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        
        graph[0].add(new Edge(1, 4));
        graph[0].add(new Edge(2, 1));
        graph[2].add(new Edge(1, 2));
        graph[1].add(new Edge(3, 1));
        graph[2].add(new Edge(3, 5));
        
        int[] distances = dijkstra(graph, 0);
        System.out.println("Shortest distances from 0: " + 
                         Arrays.toString(distances));
    }
}
```

**Complexity**: O((V + E) log V) with binary heap  
**Correctness**: At each step, the selected vertex has final shortest distance due to non-negative weights.

---

### 5. Kruskal's Minimum Spanning Tree

**Problem**: Find a spanning tree with minimum total edge weight.

**Greedy Insight**: Sort edges by weight and greedily add edges that don't create cycles.

```java
import java.util.*;

public class KruskalMST {
    static class Edge implements Comparable<Edge> {
        int u, v, weight;
        
        Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
        
        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }
    
    static class UnionFind {
        int[] parent, rank;
        
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        
        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }
        
        boolean union(int x, int y) {
            int px = find(x), py = find(y);
            if (px == py) return false;
            
            if (rank[px] < rank[py]) {
                parent[px] = py;
            } else if (rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[py] = px;
                rank[px]++;
            }
            return true;
        }
    }
    
    public static int kruskalMST(int n, List<Edge> edges) {
        Collections.sort(edges);
        UnionFind uf = new UnionFind(n);
        
        int mstWeight = 0;
        int edgeCount = 0;
        
        for (Edge edge : edges) {
            if (uf.union(edge.u, edge.v)) {
                mstWeight += edge.weight;
                edgeCount++;
                if (edgeCount == n - 1) break;
            }
        }
        
        return mstWeight;
    }
    
    public static void main(String[] args) {
        List<Edge> edges = Arrays.asList(
            new Edge(0, 1, 4),
            new Edge(0, 2, 2),
            new Edge(1, 2, 1),
            new Edge(1, 3, 5),
            new Edge(2, 3, 8),
            new Edge(2, 4, 10),
            new Edge(3, 4, 2)
        );
        
        System.out.println("MST Weight: " + kruskalMST(5, edges));
    }
}
```

**Complexity**: O(E log E) dominated by sorting  
**Optimality**: Proved by the "cut property"—any edge of minimum weight crossing a cut belongs to some MST.

---

## Proof of Correctness Techniques

### Exchange Argument
Show that any optimal solution can be transformed into your greedy solution without increasing cost. Repeatedly replace non-greedy choices with greedy ones.

### Structural Induction
Prove the greedy choice is optimal, then show the remaining subproblem also satisfies the property.

### Exchange/Swap Argument (for MST)
If an optimal solution doesn't use edge (u,v) that your algorithm chooses, adding (u,v) creates a cycle. Removing the heaviest edge in this cycle and adding (u,v) preserves or improves optimality.

---

## Real-World Applications

| Application | Algorithm | Use Case |
|---|---|---|
| **Data Compression** | Huffman Coding | Compress files with frequency-based encoding |
| **Navigation Systems** | Dijkstra's Algorithm | Find fastest routes with traffic weights |
| **Network Design** | Kruskal's/Prim's MST | Minimum-cost backbone network topology |
| **Scheduling** | Activity Selection | Maximize resource utilization |
| **Load Balancing** | Greedy Task Assignment | Assign tasks to least-loaded server |
| **Machine Learning** | Greedy Feature Selection | Select informative features incrementally |

---

## Greedy vs. Dynamic Programming

| Aspect | Greedy | Dynamic Programming |
|---|---|---|
| **Approach** | Local optimization at each step | Explore all possibilities with memoization |
| **Time Complexity** | Typically O(n log n) or O(n) | Often O(n²) or worse |
| **Space Complexity** | O(1) to O(n) | O(n²) or more (memoization) |
| **Correctness** | Problem-specific, must prove | Works for optimal substructure |
| **Best For** | Problems with greedy choice property | Overlapping subproblems |
| **Example** | Fractional Knapsack | 0/1 Knapsack |

---

## Counterexamples and When to Avoid Greedy

**0/1 Knapsack**: Greedy by ratio fails for items {(10,5), (9,4), (4,3)} with capacity 8—greedy picks (10,5) leaving 3 units, missing optimal (9,4)+(4,3)=13.

**Coin Change**: With coins {1,3,4} and amount 6, greedy picks 4+1+1=3 coins, but optimal is 3+3=2 coins.

**Always verify**: The greedy choice property before implementing. When in doubt, use DP.

---

## Summary

Greedy algorithms excel at optimization when problems exhibit specific structural properties. Master the five classic problems and their proof techniques to recognize greedy opportunities in new problems. Remember: **prove the greedy choice property before committing to the greedy approach**.

