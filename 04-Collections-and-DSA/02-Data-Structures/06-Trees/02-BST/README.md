# Binary Search Tree (BST) - Comprehensive Guide

## Table of Contents
1. [Introduction](#introduction)
2. [BST Properties](#bst-properties)
3. [Tree Structure](#tree-structure)
4. [Node Structure](#node-structure)
5. [Core Operations](#core-operations)
6. [Tree Traversals](#tree-traversals)
7. [Performance Analysis](#performance-analysis)
8. [Use Cases](#use-cases)
9. [Code Examples](#code-examples)
10. [Advantages & Disadvantages](#advantages--disadvantages)
11. [Comparison](#comparison)

## Introduction

A **Binary Search Tree (BST)** is a binary tree data structure where each node satisfies a special property: all values in the left subtree are smaller than the node's value, and all values in the right subtree are larger. This ordering property enables efficient searching, insertion, and deletion operations.

BSTs are fundamental data structures used in databases, file systems, and many algorithmic problems. They provide a good balance between simplicity and efficiency for maintaining sorted data.

## BST Properties

A valid Binary Search Tree must satisfy:

```
For every node N:
  - All values in left subtree < N's value
  - All values in right subtree > N's value
  - Both left and right subtrees are also valid BSTs
```

### Example BST

```
         50
       /    \
      30     70
     / \    / \
    20  40 60  80
   /         \
  10         75
```

- Left subtree of 50: {20, 30, 40, 10} - all < 50 ✓
- Right subtree of 50: {70, 60, 80, 75} - all > 50 ✓
- Property satisfied recursively for all nodes

## Tree Structure

### Visual BST Construction

```
Insert sequence: 50, 30, 70, 20, 40, 60, 80

Step 1: Insert 50
    50

Step 2: Insert 30 (< 50, go left)
    50
   /
  30

Step 3: Insert 70 (> 50, go right)
    50
   /  \
  30   70

Step 4: Insert 20 (< 50, left; < 30, left)
      50
     /  \
    30   70
   /
  20

Step 5: Insert 40 (< 50, left; > 30, right)
       50
      /  \
     30   70
    / \
   20  40

Step 6: Insert 60 (> 50, right; < 70, left)
        50
       /  \
      30   70
     / \  /
    20 40 60

Step 7: Insert 80 (> 50, right; > 70, right)
        50
       /  \
      30   70
     / \  / \
    20 40 60 80
```

## Node Structure

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

## Core Operations

### 1. **Search** - O(log n) average, O(n) worst

```
Algorithm:
1. Start at root
2. Compare key with current node:
   - If equal: found
   - If less: search left subtree
   - If greater: search right subtree
3. Continue until found or reach null
```

### 2. **Insertion** - O(log n) average, O(n) worst

```
Algorithm:
1. Start at root
2. Find correct position by comparing:
   - If less: go left
   - If greater: go right
3. Insert as new leaf node
```

### 3. **Deletion** - Three cases

```
Case 1: Leaf node
  Simply remove

Case 2: One child
  Replace with child

Case 3: Two children
  Find inorder successor (smallest in right subtree)
  Replace node value with successor
  Delete successor recursively
```

## Tree Traversals

### 1. **Inorder** (Left → Root → Right)
- **Important**: Returns sorted sequence
- **Example**: For BST above: 10, 20, 30, 40, 50, 60, 70, 80

### 2. **Preorder** (Root → Left → Right)
- Used for creating copy of tree

### 3. **Postorder** (Left → Right → Root)
- Used for deletion

### 4. **Level-order** (Breadth-First)
- Process by levels

## Performance Analysis

| Operation | Average Case | Worst Case | Notes |
|-----------|--------------|-----------|-------|
| Search | O(log n) | O(n) | Unbalanced if skewed |
| Insert | O(log n) | O(n) | O(n) for sequential input |
| Delete | O(log n) | O(n) | With successor/predecessor |
| Traversal | O(n) | O(n) | All nodes visited |
| Min/Max | O(log n) | O(n) | Find leaf node |
| Space | - | - | O(n) total |

**Worst Case**: Occurs when tree becomes like a linked list (e.g., inserting sorted data)

## Use Cases

1. **Database Indexing**: Primary index structure
2. **File Systems**: Directory trees, inodes
3. **Expression Evaluation**: Mathematical expression trees
4. **Autocomplete**: Prefix search with sorted words
5. **Spell Checkers**: Dictionary with fast lookup
6. **Game Development**: Decision trees
7. **Memory Management**: Free block management
8. **Compression Algorithms**: Huffman coding

## Code Examples

### Complete BST Implementation with All Operations

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

class BinarySearchTree {
    private TreeNode root;
    
    BinarySearchTree() {
        this.root = null;
    }
    
    // Insert a value into BST
    public void insert(int data) {
        root = insertHelper(root, data);
    }
    
    private TreeNode insertHelper(TreeNode node, int data) {
        if (node == null) {
            return new TreeNode(data);
        }
        
        if (data < node.data) {
            node.left = insertHelper(node.left, data);
        } else if (data > node.data) {
            node.right = insertHelper(node.right, data);
        }
        // Ignore duplicate values
        
        return node;
    }
    
    // Search for a value
    public boolean search(int data) {
        return searchHelper(root, data);
    }
    
    private boolean searchHelper(TreeNode node, int data) {
        if (node == null) return false;
        
        if (data == node.data) {
            return true;
        } else if (data < node.data) {
            return searchHelper(node.left, data);
        } else {
            return searchHelper(node.right, data);
        }
    }
    
    // Delete a value from BST
    public void delete(int data) {
        root = deleteHelper(root, data);
    }
    
    private TreeNode deleteHelper(TreeNode node, int data) {
        if (node == null) return null;
        
        if (data < node.data) {
            node.left = deleteHelper(node.left, data);
        } else if (data > node.data) {
            node.right = deleteHelper(node.right, data);
        } else {
            // Node to delete found
            
            // Case 1: Leaf node
            if (node.left == null && node.right == null) {
                return null;
            }
            
            // Case 2: One child
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            
            // Case 3: Two children
            // Find inorder successor (minimum in right subtree)
            TreeNode successor = findMin(node.right);
            node.data = successor.data;
            node.right = deleteHelper(node.right, successor.data);
        }
        
        return node;
    }
    
    // Find minimum value node
    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    // Find minimum value
    public int getMin() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
        return findMin(root).data;
    }
    
    // Find maximum value
    public int getMax() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
        return findMaxHelper(root).data;
    }
    
    private TreeNode findMaxHelper(TreeNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }
    
    // Inorder traversal - returns sorted sequence
    public void inorder() {
        System.out.print("Inorder (sorted): ");
        inorderHelper(root);
        System.out.println();
    }
    
    private void inorderHelper(TreeNode node) {
        if (node == null) return;
        
        inorderHelper(node.left);
        System.out.print(node.data + " ");
        inorderHelper(node.right);
    }
    
    // Preorder traversal
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
    
    // Postorder traversal
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
    
    // Level-order traversal
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
    
    // Get kth smallest element
    public int kthSmallest(int k) {
        List<Integer> result = new ArrayList<>();
        kthSmallestHelper(root, result, k);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("k is out of range");
        }
        return result.get(0);
    }
    
    private void kthSmallestHelper(TreeNode node, List<Integer> result, int k) {
        if (node == null || result.size() > 0) return;
        
        kthSmallestHelper(node.left, result, k);
        
        if (result.size() == 0) {
            k--;
            if (k == 0) {
                result.add(node.data);
                return;
            }
        }
        
        kthSmallestHelper(node.right, result, k);
    }
    
    // Print tree structure
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
    
    // Validate BST property
    public boolean isValidBST() {
        return isValidBSTHelper(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private boolean isValidBSTHelper(TreeNode node, int min, int max) {
        if (node == null) return true;
        
        if (node.data <= min || node.data >= max) {
            return false;
        }
        
        return isValidBSTHelper(node.left, min, node.data) &&
               isValidBSTHelper(node.right, node.data, max);
    }
    
    // Find LCA (Lowest Common Ancestor)
    public Integer findLCA(int x, int y) {
        TreeNode lca = findLCAHelper(root, x, y);
        return lca != null ? lca.data : null;
    }
    
    private TreeNode findLCAHelper(TreeNode node, int x, int y) {
        if (node == null) return null;
        
        if (x < node.data && y < node.data) {
            return findLCAHelper(node.left, x, y);
        } else if (x > node.data && y > node.data) {
            return findLCAHelper(node.right, x, y);
        }
        
        return node;
    }
    
    // Main method for testing
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        
        System.out.println("Binary Search Tree Operations Demo");
        System.out.println("==================================");
        
        // Insert values
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 75};
        for (int val : values) {
            bst.insert(val);
        }
        
        bst.printTree();
        
        System.out.println("\nTraversals:");
        bst.inorder();
        bst.preorder();
        bst.postorder();
        bst.levelorder();
        
        System.out.println("\nTree Properties:");
        System.out.println("Height: " + bst.getHeight());
        System.out.println("Total Nodes: " + bst.countNodes());
        System.out.println("Min Value: " + bst.getMin());
        System.out.println("Max Value: " + bst.getMax());
        System.out.println("Is Valid BST: " + bst.isValidBST());
        
        System.out.println("\nSearch Operations:");
        System.out.println("Search 40: " + bst.search(40));
        System.out.println("Search 100: " + bst.search(100));
        
        System.out.println("\nKth Smallest Elements:");
        for (int k = 1; k <= 3; k++) {
            System.out.println("  " + k + "st/nd smallest: " + bst.kthSmallest(k));
        }
        
        System.out.println("\nLCA (Lowest Common Ancestor):");
        System.out.println("LCA(20, 35): " + bst.findLCA(20, 35));
        System.out.println("LCA(10, 25): " + bst.findLCA(10, 25));
        
        System.out.println("\nDeletion Operations:");
        System.out.println("Deleting 20 (node with one child):");
        bst.delete(20);
        bst.inorder();
        
        System.out.println("\nDeleting 70 (node with two children):");
        bst.delete(70);
        bst.inorder();
        
        System.out.println("\nDeleting 50 (root node):");
        bst.delete(50);
        bst.inorder();
    }
}
```

## Advantages & Disadvantages

### Advantages ✓
- **Ordered access**: Inorder traversal gives sorted sequence
- **Efficient search**: O(log n) in balanced cases
- **Simple structure**: Easy to understand and implement
- **Dynamic sizing**: Flexible for insertions and deletions
- **Memory efficient**: No pre-allocated space wasted

### Disadvantages ✗
- **Unbalanced risk**: O(n) worst case with sequential input
- **Cache unfriendly**: Poor memory locality
- **No direct access**: Must traverse from root
- **Space overhead**: Extra pointers for each node

## Comparison

| Feature | BST | AVL | Red-Black | Array | LinkedList |
|---------|-----|-----|-----------|-------|-----------|
| Search | O(log n) avg | O(log n) | O(log n) | O(n) | O(n) |
| Insert | O(log n) avg | O(log n) | O(log n) | O(n) | O(1) |
| Delete | O(log n) avg | O(log n) | O(log n) | O(n) | O(1) |
| Balanced | No | Yes | Yes | N/A | N/A |
| Space | O(n) | O(n) | O(n) | O(n) | O(n) |

## Key Takeaways

1. **BST Property**: Essential for maintaining ordering and enabling search
2. **Importance of Balance**: Unbalanced BSTs lose efficiency benefits
3. **Inorder Magic**: Returns sorted sequence - very useful property
4. **Deletion Strategy**: Two-children case requires careful handling
5. **Real-world Usage**: Foundation for database systems and file structures
