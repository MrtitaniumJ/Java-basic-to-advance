# Tree Problems

## Overview

This program demonstrates advanced binary tree operations, specifically focusing on Binary Search Trees (BST) and their applications. Understanding tree problems is essential for:
- Building efficient data structures
- Solving graph-related problems
- Optimizing search and retrieval operations
- Preparing for technical interviews

## Problem Analysis

### What We're Solving

#### 1. **Binary Search Tree (BST) Fundamentals**

A BST is a binary tree with special properties:
- Left child value < Parent value
- Right child value > Parent value
- This property holds recursively for all subtrees

**Key Characteristics:**
- Enables efficient searching
- Maintains sorted order when traversed inorder
- Supports range queries efficiently

**Time Complexity:**
- Insert: O(log n) average, O(n) worst (unbalanced tree)
- Delete: O(log n) average, O(n) worst
- Search: O(log n) average, O(n) worst

#### 2. **Tree Traversal Methods**

**Depth-First Traversals:**

**Inorder (Left-Root-Right):** O(n)
```
For BST: Produces sorted sequence
Example: 20 30 40 50 60 70 80
```

**Preorder (Root-Left-Right):** O(n)
```
Used for: Copying tree, creating expression trees
Example: 50 30 20 40 70 60 80
```

**Postorder (Left-Right-Root):** O(n)
```
Used for: Deleting tree, postfix expressions
Example: 20 40 30 60 80 70 50
```

**Breadth-First Traversal:**

**Level Order (BFS):** O(n)
```
Explores nodes level by level
Example: 50 30 70 20 40 60 80
Uses Queue for implementation
```

#### 3. **Path Sum Problems**

**Problem:** Find root-to-leaf paths that sum to a target value

**Approach:**
- Use DFS from root
- Subtract current node value from target
- When reaching leaf, check if sum equals target
- Backtrack to explore other paths

**Variations:**
- Single path check (boolean return)
- All paths (collect all valid paths)
- Path with specific conditions

#### 4. **Tree Deletion**

**Three Cases:**

**Case 1: Node is leaf (no children)**
- Simply remove the node

**Case 2: Node has one child**
- Replace node with its child

**Case 3: Node has two children**
- Find inorder successor (smallest in right subtree)
- Replace node value with successor value
- Delete the successor node

#### 5. **Lowest Common Ancestor (LCA)**

**Definition:** The deepest node that is ancestor to both nodes

**BST Property:** For BST, LCA can be found efficiently
- If both values < node value: search left
- If both values > node value: search right
- Otherwise: current node is LCA

**Time:** O(log n) on average, O(n) worst case

#### 6. **Additional Operations**

**Tree Inversion/Mirroring:** O(n)
- Swap left and right children recursively
- Application: Creating mirror images

**Balance Checking:** O(n)
- Check if height difference between children ≤ 1
- Useful for AVL tree validation

## Design Decisions

### 1. Recursive Implementation
All traversal and search operations use recursion:
```java
private void inorder(TreeNode node) {
    if (node == null) return;
    inorder(node.left);
    // Process node
    inorder(node.right);
}
```

**Advantages:**
- Natural representation of tree structure
- Easier to understand and implement
- Leverages call stack for path tracking

### 2. Node-Based Structure
Using `TreeNode` class for flexibility:
```java
class TreeNode {
    int value;
    TreeNode left;
    TreeNode right;
}
```

**Advantages:**
- Easy to extend with more data
- Support for parent pointers if needed
- Natural tree representation

### 3. Defensive Parameter Validation
Using `Long.MIN_VALUE` and `Long.MAX_VALUE` for BST validation:
```java
private boolean isValidBSTRec(TreeNode node, long min, long max)
```

**Why:** Handles edge case where Integer.MIN_VALUE is a valid node value

### 4. Queue-Based Level Order Traversal
Using LinkedList as Queue for BFS:
```java
Queue<TreeNode> queue = new LinkedList<>();
```

**Advantages:**
- O(1) enqueue/dequeue operations
- Natural FIFO behavior
- Part of Collections Framework

## Complexity Analysis

### BST Operations

| Operation | Average | Worst Case | Space |
|-----------|---------|------------|-------|
| Insert | O(log n) | O(n) | O(h) |
| Delete | O(log n) | O(n) | O(h) |
| Search | O(log n) | O(n) | O(h) |
| Inorder | O(n) | O(n) | O(h) |

### Tree Properties

| Operation | Time | Space |
|-----------|------|-------|
| Get Height | O(n) | O(h) |
| Count Nodes | O(n) | O(h) |
| Find LCA (BST) | O(log n) | O(h) |
| Invert Tree | O(n) | O(h) |
| Check Balance | O(n) | O(h) |

*h = height of tree, n = number of nodes*

## Sample Input/Output

### Running the Program

```powershell
javac TreeProblems.java
java TreeProblems
```

### Expected Output

```
╔════════════════════════════════════════════════════════╗
║             TREE PROBLEMS IMPLEMENTATION              ║
╚════════════════════════════════════════════════════════╝

========== BST OPERATIONS ==========
Inserting values: 50, 30, 70, 20, 40, 60, 80

Tree Traversals:
Inorder (Left-Root-Right):  20 30 40 50 60 70 80
Preorder (Root-Left-Right): 50 30 20 40 70 60 80
Postorder (Left-Right-Root): 20 40 30 60 80 70 50
Level Order (BFS):       50 30 70 20 40 60 80

Tree Statistics:
Height: 2
Node count: 7
Is valid BST: true
Is balanced: true

Search Operations:
Search 40: true
Search 99: false

Lowest Common Ancestor of 20 and 40: 30
Lowest Common Ancestor of 60 and 80: 70

========== PATH SUM PROBLEMS ==========
Tree in inorder:
4 5 7 8 11 13

Has path with sum 22: true
Has path with sum 10: false
Has path with sum 27: true

All paths with sum 22:
[5, 4, 13]
[5, 8, 4, 5]

========== DELETION OPERATIONS ==========
Initial values: [50, 30, 70, 20, 40, 60, 80, 10, 25, 35]
Initial tree (Inorder):
10 20 25 30 35 40 50 60 70 80

Deleting 20 (node with two children)
10 25 30 35 40 50 60 70 80

Deleting 30 (node with two children)
10 25 35 40 50 60 70 80

Deleting 50 (root node with two children)
10 25 35 40 60 70 80

========== TREE MANIPULATION ==========
Original tree (Inorder):
20 30 40 50 60 70 80

Inverting tree...
Inverted tree (Inorder):
80 70 60 50 40 30 20

=======================================================
All demonstrations completed successfully!
=======================================================
```

## Real-World Applications

### 1. **Database Indexing**
B-Trees and BSTs are used in database indexing for fast retrieval.

### 2. **Expression Parsing**
Preorder and postorder traversals used in compiler design.

### 3. **File System Hierarchies**
Tree structure models directories and files.

### 4. **Autocomplete Systems**
Trie (specialized tree) for efficient prefix matching.

### 5. **Game Development**
Game trees for AI decision making (minimax algorithm).

## Variations and Challenges

### Challenge 1: AVL Tree (Self-Balancing BST)
Implement tree rotations to maintain balance.

**Key Methods:**
- Left rotate: Prevents right-heavy imbalance
- Right rotate: Prevents left-heavy imbalance
- Maintain balance factor (-1, 0, 1)

### Challenge 2: Range Sum Query BST
Find sum of values in range [low, high].

```java
public int rangeSum(int low, int high) {
    return rangeSumRec(root, low, high);
}
```

### Challenge 3: Serialize and Deserialize Tree
Convert tree to string and back.

**Format:** Pre-order traversal with null markers
```
Example: 1,2,null,null,3,null,null
```

### Challenge 4: Diameter of Binary Tree
Find longest path between any two nodes.

```java
int diameter = longestPath - 1;
```

### Challenge 5: Vertical Order Traversal
Print tree nodes in vertical lines.

```java
Use HashMap<column, List<values>>
```

### Challenge 6: Maximum Path Sum
Find path with maximum sum (not necessarily root to leaf).

**Approach:** 
- Consider paths through each node
- Path can go through node's children

## Interview Tips

### When Discussing Tree Problems

1. **Clarify Requirements:**
   - Is it a BST or generic binary tree?
   - Need root-to-leaf paths or any paths?
   - Can modify tree or must preserve it?

2. **Choose Appropriate Traversal:**
   - Inorder for sorted output
   - Preorder for tree copy
   - Postorder for deletion
   - Level-order for breadth-first processing

3. **Discuss Space-Time Trade-offs:**
   - Recursive uses O(h) stack space
   - Iterative uses explicit stack/queue
   - Can optimize using parent pointers

4. **Handle Edge Cases:**
   - Single node tree
   - All left or all right skewed tree
   - Null nodes and empty trees
   - Duplicate values in tree

### Common Questions

**Q: What's the difference between preorder and level order?**
A: Preorder is DFS (depth-first), level-order is BFS (breadth-first).

**Q: Why use recursion for tree problems?**
A: Recursion mirrors tree structure naturally, but watch stack overflow on deep trees.

**Q: How to optimize BST search?**
A: Use AVL or Red-Black trees for guaranteed O(log n) operations.

**Q: What's the time complexity of finding path sum?**
A: O(n) because we may need to visit all nodes in worst case.

## Key Learnings

1. **Tree structure** naturally suits recursive solutions
2. **Traversal order** matters for specific applications
3. **BST property** enables efficient operations
4. **Path problems** need careful backtracking
5. **Balance** is crucial for performance guarantees

## Further Exploration

1. **Self-Balancing Trees:** AVL, Red-Black, 2-3 trees
2. **Advanced Trees:** Segment trees, Fenwick trees
3. **Graph Extensions:** Convert tree problems to graph
4. **Optimization:** Memoization for overlapping subproblems
5. **Concurrency:** Thread-safe tree implementations

---

**Compilation**: `javac TreeProblems.java`
**Execution**: `java TreeProblems`
**Difficulty**: Intermediate to Advanced
**Topics**: Binary Trees, BST, Recursion, Graph Algorithms
**Prerequisites**: Basic Java, Recursion, Collections Framework
