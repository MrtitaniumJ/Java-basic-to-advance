# Binary Tree - Comprehensive Guide

## Table of Contents
1. [Introduction](#introduction)
2. [Tree Structure](#tree-structure)
3. [Node Structure](#node-structure)
4. [Tree Traversals](#tree-traversals)
5. [Operations](#operations)
6. [Performance Analysis](#performance-analysis)
7. [Use Cases](#use-cases)
8. [Code Examples](#code-examples)
9. [When to Use](#when-to-use)
10. [Comparison](#comparison)

## Introduction

A **Binary Tree** is a hierarchical data structure in which each node has at most two children, referred to as the left child and the right child. Unlike linear data structures (arrays, linked lists), binary trees provide a more efficient way to organize data for searching, sorting, and hierarchical representation.

Binary trees are fundamental to computer science, serving as the foundation for more specialized tree structures like Binary Search Trees (BST), AVL Trees, and Red-Black Trees. They are used extensively in compilers, databases, and graphics programming.

### Key Characteristics
- **Non-linear structure**: Enables hierarchical relationships
- **Two children maximum**: Simplifies implementation and traversal
- **Root node**: Starting point of the tree
- **Leaf nodes**: Nodes with no children
- **Height**: Maximum distance from root to any leaf

## Tree Structure

### Visual Representation

```
          A (Root)
         / \
        B   C
       / \ 
      D  E
    
- A is the root
- B and C are children of A
- D and E are children of B
- D, E, and C are leaf nodes
- Height of tree = 2 (from A to D or E)
```

### Types of Binary Trees

1. **Full Binary Tree**: Every node has 0 or 2 children
2. **Complete Binary Tree**: All levels filled except possibly the last, which is filled left to right
3. **Perfect Binary Tree**: All internal nodes have 2 children and all leaves are at the same level
4. **Balanced Binary Tree**: Heights of left and right subtrees differ by at most 1
5. **Degenerate Tree**: Each node has at most one child (essentially a linked list)

## Node Structure

A tree node contains data and references to left and right children:

```java
class TreeNode {
    int data;
    TreeNode left;
    TreeNode right;
    
    TreeNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
```

## Tree Traversals

Tree traversals visit nodes in a specific order:

### 1. **Inorder Traversal** (Left → Root → Right)
- Output: Sorted sequence in BST
- Use: Getting sorted values from BST

### 2. **Preorder Traversal** (Root → Left → Right)
- Output: Tree copy, expression evaluation
- Use: Creating copy of tree, expression trees

### 3. **Postorder Traversal** (Left → Right → Root)
- Output: Delete tree, memory deallocation
- Use: Deletion, expression evaluation

### 4. **Level-order Traversal** (Breadth-First)
- Output: Level-by-level processing
- Use: Printing tree in levels, BFS problems

## Operations

### 1. **Insertion**
```
- Create new node
- Find appropriate position
- Link to parent node
```

### 2. **Deletion**
```
- Find node to delete
- Handle three cases:
  a) Leaf node: Simply remove
  b) One child: Replace with child
  c) Two children: Complex, depends on tree type
```

### 3. **Searching**
```
- Compare with current node
- Move to left/right subtree
- Continue until found or reach leaf
```

## Performance Analysis

| Operation | Average Case | Worst Case | Space |
|-----------|--------------|-----------|-------|
| Search | O(log n) | O(n) | O(1) |
| Insertion | O(log n) | O(n) | O(1) |
| Deletion | O(log n) | O(n) | O(1) |
| Traversal | O(n) | O(n) | O(h) |
| Space | - | - | O(n) |

**Note**: Worst case occurs in degenerate tree (linear). Balanced trees maintain O(log n) average.

## Use Cases

1. **File Systems**: Directory hierarchies
2. **DOM (HTML)**: Document Object Model in browsers
3. **Expression Trees**: Mathematical expression evaluation
4. **Huffman Coding**: Data compression
5. **Game Development**: Game trees, AI decision making
6. **Database Indexing**: B-Trees and variants
7. **Abstract Syntax Trees**: Compiler design
8. **Organizational Charts**: Hierarchy representation

## Code Examples

### Complete Binary Tree Implementation

```java
import java.util.*;

class TreeNode {
    int data;
    TreeNode left;
    TreeNode right;
    
    TreeNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

class BinaryTree {
    private TreeNode root;
    
    // Constructor
    BinaryTree() {
        this.root = null;
    }
    
    // Insert node in level-order manner
    public void insert(int data) {
        if (root == null) {
            root = new TreeNode(data);
            return;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            
            if (current.left == null) {
                current.left = new TreeNode(data);
                return;
            } else {
                queue.add(current.left);
            }
            
            if (current.right == null) {
                current.right = new TreeNode(data);
                return;
            } else {
                queue.add(current.right);
            }
        }
    }
    
    // Inorder Traversal: Left → Root → Right
    public void inorder() {
        System.out.print("Inorder: ");
        inorderHelper(root);
        System.out.println();
    }
    
    private void inorderHelper(TreeNode node) {
        if (node == null) return;
        
        inorderHelper(node.left);
        System.out.print(node.data + " ");
        inorderHelper(node.right);
    }
    
    // Preorder Traversal: Root → Left → Right
    public void preorder() {
        System.out.print("Preorder: ");
        preorderHelper(root);
        System.out.println();
    }
    
    private void preorderHelper(TreeNode node) {
        if (node == null) return;
        
        System.out.print(node.data + " ");
        preorderHelper(node.left);
        preorderHelper(node.right);
    }
    
    // Postorder Traversal: Left → Right → Root
    public void postorder() {
        System.out.print("Postorder: ");
        postorderHelper(root);
        System.out.println();
    }
    
    private void postorderHelper(TreeNode node) {
        if (node == null) return;
        
        postorderHelper(node.left);
        postorderHelper(node.right);
        System.out.print(node.data + " ");
    }
    
    // Level-order Traversal (BFS)
    public void levelorder() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        
        System.out.print("Level-order: ");
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            System.out.print(current.data + " ");
            
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
        System.out.println();
    }
    
    // Search for a value
    public boolean search(int data) {
        return searchHelper(root, data);
    }
    
    private boolean searchHelper(TreeNode node, int data) {
        if (node == null) return false;
        
        if (node.data == data) return true;
        
        return searchHelper(node.left, data) || 
               searchHelper(node.right, data);
    }
    
    // Get height of tree
    public int getHeight() {
        return getHeightHelper(root);
    }
    
    private int getHeightHelper(TreeNode node) {
        if (node == null) return -1;
        
        return 1 + Math.max(getHeightHelper(node.left),
                           getHeightHelper(node.right));
    }
    
    // Count total nodes
    public int countNodes() {
        return countNodesHelper(root);
    }
    
    private int countNodesHelper(TreeNode node) {
        if (node == null) return 0;
        
        return 1 + countNodesHelper(node.left) + 
               countNodesHelper(node.right);
    }
    
    // Print tree structure visually
    public void printTree() {
        System.out.println("\nTree Structure:");
        printTreeHelper(root, "", true);
    }
    
    private void printTreeHelper(TreeNode node, String indent, boolean last) {
        if (node == null) return;
        
        System.out.print(indent);
        if (last) {
            System.out.print("└─");
            indent += "  ";
        } else {
            System.out.print("├─");
            indent += "│ ";
        }
        System.out.println(node.data);
        
        if (node.left != null || node.right != null) {
            if (node.left != null) {
                printTreeHelper(node.left, indent, node.right == null);
            }
            if (node.right != null) {
                printTreeHelper(node.right, indent, true);
            }
        }
    }
    
    // Delete node (removes first occurrence)
    public void deleteNode(int data) {
        root = deleteNodeHelper(root, data);
    }
    
    private TreeNode deleteNodeHelper(TreeNode node, int data) {
        if (node == null) return null;
        
        if (node.data == data) {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            
            // Both children exist - find inorder successor (min in right subtree)
            TreeNode minRight = findMin(node.right);
            node.data = minRight.data;
            node.right = deleteNodeHelper(node.right, minRight.data);
        } else {
            node.left = deleteNodeHelper(node.left, data);
            node.right = deleteNodeHelper(node.right, data);
        }
        
        return node;
    }
    
    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    // Check if tree is balanced
    public boolean isBalanced() {
        return isBalancedHelper(root) != -1;
    }
    
    private int isBalancedHelper(TreeNode node) {
        if (node == null) return 0;
        
        int leftHeight = isBalancedHelper(node.left);
        if (leftHeight == -1) return -1;
        
        int rightHeight = isBalancedHelper(node.right);
        if (rightHeight == -1) return -1;
        
        if (Math.abs(leftHeight - rightHeight) > 1) return -1;
        
        return 1 + Math.max(leftHeight, rightHeight);
    }
    
    // Main method for testing
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        
        // Insert elements
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int val : values) {
            tree.insert(val);
        }
        
        System.out.println("Binary Tree Operations Demo");
        System.out.println("===========================");
        
        tree.printTree();
        
        System.out.println("\nTraversals:");
        tree.inorder();
        tree.preorder();
        tree.postorder();
        tree.levelorder();
        
        System.out.println("\nTree Properties:");
        System.out.println("Height: " + tree.getHeight());
        System.out.println("Total Nodes: " + tree.countNodes());
        System.out.println("Is Balanced: " + tree.isBalanced());
        
        System.out.println("\nSearch Operations:");
        System.out.println("Search 5: " + tree.search(5));
        System.out.println("Search 10: " + tree.search(10));
        
        System.out.println("\nDeletion:");
        tree.deleteNode(4);
        System.out.println("After deleting 4:");
        tree.levelorder();
    }
}
```

## When to Use

✅ **Use Binary Trees when:**
- You need hierarchical organization of data
- You want efficient search (with balanced variants)
- Implementing expression parsers or compilers
- Building game trees for game AI
- Representing organizational structures

❌ **Avoid when:**
- You need guaranteed O(log n) operations (use BST/AVL instead)
- You need simple sequential access (use arrays/linked lists)
- Data doesn't have hierarchical relationships

## Comparison with Other Structures

| Structure | Search | Insert | Delete | Space | Best For |
|-----------|--------|--------|--------|-------|----------|
| Binary Tree | O(n) | O(1) | O(n) | O(n) | Hierarchies |
| BST | O(log n) | O(log n) | O(log n) | O(n) | Ordered data |
| AVL Tree | O(log n) | O(log n) | O(log n) | O(n) | Self-balancing |
| Linked List | O(n) | O(1) | O(1) | O(n) | Sequential |
| Array | O(n) | O(n) | O(n) | O(n) | Random access |

## Key Takeaways

1. **Flexibility**: Binary trees accommodate various data relationships
2. **Traversals**: Multiple traversal methods suit different purposes
3. **Foundation**: Base for understanding advanced tree structures
4. **Efficiency**: Performance depends on tree balance and type
5. **Applicability**: Used extensively in real-world applications
