# Tree Traversal Patterns

## Table of Contents
- [Introduction](#introduction)
- [Tree Traversal Types](#tree-traversal-types)
- [Depth-First Search (DFS) Traversals](#depth-first-search-dfs-traversals)
- [Breadth-First Search (BFS) Traversals](#breadth-first-search-bfs-traversals)
- [Morris Traversal](#morris-traversal)
- [Advanced Traversal Patterns](#advanced-traversal-patterns)
- [Complete Java Implementation](#complete-java-implementation)
- [Complexity Analysis](#complexity-analysis)
- [Common Problems](#common-problems)

## Introduction

Tree traversal is a fundamental concept in computer science that involves visiting all nodes in a tree data structure in a specific order. Understanding different traversal techniques is crucial for solving various tree-related problems, from simple node counting to complex tree transformations and pattern matching.

Tree traversal algorithms can be broadly categorized into two main types: **Depth-First Search (DFS)** and **Breadth-First Search (BFS)**. DFS explores as far as possible along each branch before backtracking, while BFS explores all nodes at the current depth before moving to the next level.

## Tree Traversal Types

### Overview

1. **Depth-First Search (DFS)**
   - Inorder Traversal (Left → Root → Right)
   - Preorder Traversal (Root → Left → Right)
   - Postorder Traversal (Left → Right → Root)

2. **Breadth-First Search (BFS)**
   - Level Order Traversal
   - Zigzag Level Order Traversal
   - Vertical Order Traversal

3. **Space-Optimized**
   - Morris Traversal (O(1) space)

## Depth-First Search (DFS) Traversals

### Inorder Traversal

Inorder traversal visits nodes in the order: **Left → Root → Right**. For binary search trees, this traversal visits nodes in ascending order, making it extremely useful for sorted data retrieval.

**Use Cases:**
- Retrieving sorted data from BST
- Expression tree evaluation
- Finding kth smallest element in BST

### Preorder Traversal

Preorder traversal visits nodes in the order: **Root → Left → Right**. This traversal is particularly useful when you need to create a copy of the tree or when you need to process the parent before processing children.

**Use Cases:**
- Creating a copy of the tree
- Prefix expression evaluation
- Serializing a tree

### Postorder Traversal

Postorder traversal visits nodes in the order: **Left → Right → Root**. This is ideal when you need to process children before the parent, such as in deletion operations or calculating tree properties.

**Use Cases:**
- Deleting a tree (delete children first)
- Postfix expression evaluation
- Calculating directory sizes in file systems

## Breadth-First Search (BFS) Traversals

### Level Order Traversal

Level order traversal visits all nodes at each level before moving to the next level. It uses a queue data structure to keep track of nodes at each level. This traversal provides a breadth-first view of the tree structure.

**Use Cases:**
- Finding the shortest path in unweighted trees
- Level-wise processing
- Finding nodes at a specific level

### Zigzag Traversal

Zigzag traversal is a variation of level order traversal where the direction alternates at each level - left to right at one level, then right to left at the next level. This pattern creates a zigzag or serpentine path through the tree.

**Use Cases:**
- Specific visualization requirements
- Algorithmic puzzles
- Spiral matrix problems

### Vertical Order Traversal

Vertical order traversal groups nodes by their vertical distance from the root. Nodes that lie on the same vertical line are grouped together. This requires tracking horizontal distance from the root.

**Use Cases:**
- Tree visualization in vertical columns
- Finding vertical sum in binary trees
- Specific geometric tree problems

## Morris Traversal

Morris traversal is an elegant algorithm that performs tree traversal without using recursion or additional stack space. It achieves O(1) space complexity by temporarily modifying the tree structure using threaded binary tree concepts, then restoring it during traversal.

**Key Concepts:**
- Uses threaded binary tree concept
- Temporarily creates links to successor nodes
- Space complexity: O(1)
- Time complexity: O(n)

**Algorithm:**
1. Initialize current node as root
2. While current is not NULL:
   - If current has no left child, process current and move to right child
   - If current has left child, find inorder predecessor
   - If predecessor's right is NULL, create thread and go left
   - If predecessor's right points to current, remove thread, process current, go right

## Advanced Traversal Patterns

### Iterative Approaches

While recursion provides elegant solutions for tree traversals, iterative approaches using explicit stacks are often preferred in production code to avoid stack overflow issues with deep trees. Iterative implementations also provide better control over the traversal process.

**Benefits:**
- Avoid stack overflow for deep trees
- Better memory management
- Easier to modify and extend
- Can be paused and resumed

### Boundary Traversal

Boundary traversal visits the boundary nodes of the tree in anti-clockwise direction: left boundary, leaf nodes (left to right), and right boundary (bottom to top).

### Diagonal Traversal

Diagonal traversal groups nodes that lie on the same diagonal line. Nodes are processed from top-left to bottom-right diagonals.

## Complete Java Implementation

```java
import java.util.*;

/**
 * TreeNode class representing a node in a binary tree
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    
    TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

/**
 * Pair class for storing node and horizontal distance (used in vertical order)
 */
class Pair {
    TreeNode node;
    int hd; // horizontal distance
    
    Pair(TreeNode node, int hd) {
        this.node = node;
        this.hd = hd;
    }
}

/**
 * QueueNode for level order traversal with level tracking
 */
class QueueNode {
    TreeNode node;
    int level;
    
    QueueNode(TreeNode node, int level) {
        this.node = node;
        this.level = level;
    }
}

/**
 * Comprehensive Tree Traversal Implementation
 */
public class TreeTraversal {
    
    // ==================== RECURSIVE DFS TRAVERSALS ====================
    
    /**
     * Inorder Traversal (Recursive): Left -> Root -> Right
     * Time: O(n), Space: O(h) where h is height
     */
    public List<Integer> inorderRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }
    
    private void inorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.val);
        inorderHelper(node.right, result);
    }
    
    /**
     * Preorder Traversal (Recursive): Root -> Left -> Right
     * Time: O(n), Space: O(h)
     */
    public List<Integer> preorderRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }
    
    private void preorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);
        preorderHelper(node.left, result);
        preorderHelper(node.right, result);
    }
    
    /**
     * Postorder Traversal (Recursive): Left -> Right -> Root
     * Time: O(n), Space: O(h)
     */
    public List<Integer> postorderRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }
    
    private void postorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        postorderHelper(node.left, result);
        postorderHelper(node.right, result);
        result.add(node.val);
    }
    
    // ==================== ITERATIVE DFS TRAVERSALS ====================
    
    /**
     * Inorder Traversal (Iterative using Stack)
     * Time: O(n), Space: O(h)
     */
    public List<Integer> inorderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        
        while (current != null || !stack.isEmpty()) {
            // Go to the leftmost node
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            // Current must be null at this point
            current = stack.pop();
            result.add(current.val);
            
            // Visit the right subtree
            current = current.right;
        }
        
        return result;
    }
    
    /**
     * Preorder Traversal (Iterative using Stack)
     * Time: O(n), Space: O(h)
     */
    public List<Integer> preorderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            
            // Push right first so left is processed first
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
        
        return result;
    }
    
    /**
     * Postorder Traversal (Iterative using Two Stacks)
     * Time: O(n), Space: O(h)
     */
    public List<Integer> postorderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        
        stack1.push(root);
        
        // Push nodes to stack2 in reverse postorder
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            
            if (node.left != null) stack1.push(node.left);
            if (node.right != null) stack1.push(node.right);
        }
        
        // Pop from stack2 to get postorder
        while (!stack2.isEmpty()) {
            result.add(stack2.pop().val);
        }
        
        return result;
    }
    
    /**
     * Postorder Traversal (Iterative using Single Stack)
     * Time: O(n), Space: O(h)
     */
    public List<Integer> postorderIterativeSingleStack(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        TreeNode lastVisited = null;
        
        while (!stack.isEmpty() || current != null) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                TreeNode peekNode = stack.peek();
                
                // If right child exists and not visited yet
                if (peekNode.right != null && lastVisited != peekNode.right) {
                    current = peekNode.right;
                } else {
                    result.add(peekNode.val);
                    lastVisited = stack.pop();
                }
            }
        }
        
        return result;
    }
    
    // ==================== MORRIS TRAVERSAL ====================
    
    /**
     * Morris Inorder Traversal (O(1) Space)
     * Time: O(n), Space: O(1)
     */
    public List<Integer> morrisInorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;
        
        while (current != null) {
            if (current.left == null) {
                // No left child, visit current and go right
                result.add(current.val);
                current = current.right;
            } else {
                // Find inorder predecessor
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                
                if (predecessor.right == null) {
                    // Create thread
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // Remove thread, visit current
                    predecessor.right = null;
                    result.add(current.val);
                    current = current.right;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Morris Preorder Traversal (O(1) Space)
     * Time: O(n), Space: O(1)
     */
    public List<Integer> morrisPreorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;
        
        while (current != null) {
            if (current.left == null) {
                result.add(current.val);
                current = current.right;
            } else {
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                
                if (predecessor.right == null) {
                    result.add(current.val); // Visit before going left
                    predecessor.right = current;
                    current = current.left;
                } else {
                    predecessor.right = null;
                    current = current.right;
                }
            }
        }
        
        return result;
    }
    
    // ==================== BFS TRAVERSALS ====================
    
    /**
     * Level Order Traversal (BFS)
     * Time: O(n), Space: O(w) where w is max width
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            result.add(currentLevel);
        }
        
        return result;
    }
    
    /**
     * Zigzag Level Order Traversal
     * Time: O(n), Space: O(w)
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            // Reverse if right to left
            if (!leftToRight) {
                Collections.reverse(currentLevel);
            }
            
            result.add(currentLevel);
            leftToRight = !leftToRight;
        }
        
        return result;
    }
    
    /**
     * Vertical Order Traversal
     * Time: O(n log n), Space: O(n)
     */
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        // TreeMap to maintain sorted order of horizontal distances
        TreeMap<Integer, List<Integer>> map = new TreeMap<>();
        Queue<Pair> queue = new LinkedList<>();
        
        queue.offer(new Pair(root, 0));
        
        while (!queue.isEmpty()) {
            Pair pair = queue.poll();
            TreeNode node = pair.node;
            int hd = pair.hd;
            
            // Add node value to the corresponding vertical line
            map.computeIfAbsent(hd, k -> new ArrayList<>()).add(node.val);
            
            if (node.left != null) {
                queue.offer(new Pair(node.left, hd - 1));
            }
            if (node.right != null) {
                queue.offer(new Pair(node.right, hd + 1));
            }
        }
        
        // Convert map values to result list
        for (List<Integer> values : map.values()) {
            result.add(values);
        }
        
        return result;
    }
    
    /**
     * Right Side View of Binary Tree
     * Time: O(n), Space: O(h)
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        rightSideViewHelper(root, 0, result);
        return result;
    }
    
    private void rightSideViewHelper(TreeNode node, int level, List<Integer> result) {
        if (node == null) return;
        
        // Add the first node of each level (rightmost)
        if (level == result.size()) {
            result.add(node.val);
        }
        
        // Visit right first, then left
        rightSideViewHelper(node.right, level + 1, result);
        rightSideViewHelper(node.left, level + 1, result);
    }
    
    /**
     * Boundary Traversal of Binary Tree
     * Time: O(n), Space: O(h)
     */
    public List<Integer> boundaryTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        // Add root
        if (!isLeaf(root)) result.add(root.val);
        
        // Add left boundary (top to bottom, excluding leaves)
        addLeftBoundary(root.left, result);
        
        // Add leaves (left to right)
        addLeaves(root, result);
        
        // Add right boundary (bottom to top, excluding leaves)
        addRightBoundary(root.right, result);
        
        return result;
    }
    
    private boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }
    
    private void addLeftBoundary(TreeNode node, List<Integer> result) {
        while (node != null) {
            if (!isLeaf(node)) result.add(node.val);
            node = (node.left != null) ? node.left : node.right;
        }
    }
    
    private void addLeaves(TreeNode node, List<Integer> result) {
        if (node == null) return;
        if (isLeaf(node)) {
            result.add(node.val);
            return;
        }
        addLeaves(node.left, result);
        addLeaves(node.right, result);
    }
    
    private void addRightBoundary(TreeNode node, List<Integer> result) {
        List<Integer> temp = new ArrayList<>();
        while (node != null) {
            if (!isLeaf(node)) temp.add(node.val);
            node = (node.right != null) ? node.right : node.left;
        }
        // Add in reverse order
        for (int i = temp.size() - 1; i >= 0; i--) {
            result.add(temp.get(i));
        }
    }
    
    // ==================== UTILITY METHODS ====================
    
    /**
     * Build a sample binary tree for testing
     */
    public static TreeNode buildSampleTree() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        return root;
    }
    
    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        TreeTraversal traversal = new TreeTraversal();
        TreeNode root = buildSampleTree();
        
        System.out.println("=== Recursive Traversals ===");
        System.out.println("Inorder (Recursive): " + traversal.inorderRecursive(root));
        System.out.println("Preorder (Recursive): " + traversal.preorderRecursive(root));
        System.out.println("Postorder (Recursive): " + traversal.postorderRecursive(root));
        
        System.out.println("\n=== Iterative Traversals ===");
        System.out.println("Inorder (Iterative): " + traversal.inorderIterative(root));
        System.out.println("Preorder (Iterative): " + traversal.preorderIterative(root));
        System.out.println("Postorder (Iterative): " + traversal.postorderIterative(root));
        
        System.out.println("\n=== Morris Traversals ===");
        System.out.println("Morris Inorder: " + traversal.morrisInorder(root));
        System.out.println("Morris Preorder: " + traversal.morrisPreorder(root));
        
        System.out.println("\n=== BFS Traversals ===");
        System.out.println("Level Order: " + traversal.levelOrder(root));
        System.out.println("Zigzag Level Order: " + traversal.zigzagLevelOrder(root));
        System.out.println("Vertical Order: " + traversal.verticalOrder(root));
        
        System.out.println("\n=== Advanced Traversals ===");
        System.out.println("Right Side View: " + traversal.rightSideView(root));
        System.out.println("Boundary Traversal: " + traversal.boundaryTraversal(root));
    }
}
```

## Complexity Analysis

### Time Complexity

| Traversal Type | Recursive | Iterative | Morris | Notes |
|---------------|-----------|-----------|--------|-------|
| Inorder | O(n) | O(n) | O(n) | Visit each node once |
| Preorder | O(n) | O(n) | O(n) | Visit each node once |
| Postorder | O(n) | O(n) | O(n) | Visit each node once |
| Level Order | O(n) | O(n) | N/A | BFS approach |
| Zigzag | O(n) | O(n) | N/A | Level order with reversal |
| Vertical Order | O(n log n) | O(n log n) | N/A | TreeMap operations |
| Boundary | O(n) | O(n) | N/A | Three separate traversals |

### Space Complexity

| Traversal Type | Recursive | Iterative | Morris | Notes |
|---------------|-----------|-----------|--------|-------|
| Inorder | O(h) | O(h) | O(1) | Recursion/stack depth |
| Preorder | O(h) | O(h) | O(1) | Recursion/stack depth |
| Postorder | O(h) | O(h) | O(1) | Recursion/stack depth |
| Level Order | O(w) | O(w) | N/A | Queue width |
| Zigzag | O(w) | O(w) | N/A | Queue width |
| Vertical Order | O(n) | O(n) | N/A | Map storage |
| Boundary | O(h) | O(h) | N/A | Recursion depth |

Where:
- **n** = number of nodes
- **h** = height of tree (O(log n) for balanced, O(n) for skewed)
- **w** = maximum width of tree (O(n) in worst case)

## Common Problems

### 1. Binary Tree Inorder Traversal
Given a binary tree, return the inorder traversal of its nodes' values.

### 2. Binary Tree Level Order Traversal
Return the level order traversal of a tree's nodes' values (level by level, from left to right).

### 3. Binary Tree Zigzag Level Order Traversal
Return the zigzag level order traversal of nodes' values (alternate direction at each level).

### 4. Vertical Order Traversal
Return the vertical order traversal from top to bottom, column by column.

### 5. Binary Tree Right Side View
Return the values of nodes visible from the right side of the tree.

### 6. Boundary of Binary Tree
Return the boundary nodes in anti-clockwise direction starting from root.

### 7. Serialize and Deserialize Binary Tree
Design an algorithm to serialize and deserialize a binary tree using traversals.

### 8. Flatten Binary Tree to Linked List
Flatten a binary tree to a linked list in-place using preorder traversal.

### 9. Construct Binary Tree from Traversals
Given inorder and preorder/postorder traversal, construct the binary tree.

### 10. Lowest Common Ancestor
Find the lowest common ancestor of two nodes using tree traversal techniques.

## Key Takeaways

1. **Choose the Right Traversal**: Different traversals suit different problems - inorder for BST operations, preorder for tree copying, postorder for deletion, level order for shortest paths.

2. **Space-Time Tradeoffs**: Recursive solutions are elegant but use O(h) space; Morris traversal achieves O(1) space but with more complex logic.

3. **Iterative vs Recursive**: Iterative approaches avoid stack overflow and provide better control but may be less intuitive initially.

4. **BFS Applications**: Level order traversal is essential for problems involving levels, shortest paths, or layer-wise processing.

5. **Practice Variations**: Master the basic patterns first, then extend to variations like zigzag, vertical order, and boundary traversal.

Understanding these traversal patterns is fundamental to solving complex tree problems efficiently. Practice implementing each approach to build strong problem-solving skills.

---

**Previous:** [Backtracking](../05-Backtracking/README.md) | **Next:** [Dynamic Programming Patterns](../07-Dynamic-Programming/README.md)
