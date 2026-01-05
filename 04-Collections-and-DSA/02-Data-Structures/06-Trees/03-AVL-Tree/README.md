# AVL Tree - Comprehensive Guide

## Table of Contents
1. [Introduction](#introduction)
2. [AVL Tree Properties](#avl-tree-properties)
3. [Balance Factor](#balance-factor)
4. [Rotations](#rotations)
5. [Insertion Process](#insertion-process)
6. [Deletion Process](#deletion-process)
7. [Performance Analysis](#performance-analysis)
8. [Use Cases](#use-cases)
9. [Code Examples](#code-examples)
10. [Comparison with Other Trees](#comparison-with-other-trees)

## Introduction

An **AVL Tree** (Adelson-Velsky and Landis Tree) is a self-balancing binary search tree where the heights of left and right subtrees of any node differ by at most 1. This balancing guarantee ensures O(log n) performance for all operations.

AVL trees maintain strict balance through rotations during insertion and deletion. They are more rigidly balanced than Red-Black trees but provide better search performance with slightly slower insertion/deletion.

## AVL Tree Properties

### Balance Condition

```
For every node N:
  |Height(left subtree) - Height(right subtree)| ≤ 1
```

### Example of Valid AVL Tree

```
        20
       /  \
      10   30
     / \
    5   15
   /
  2
  
Balance factors: 20(0), 10(1), 30(0), 5(1), 15(0), 2(0)
All balanced ✓
```

### Example of Invalid Tree (Not AVL)

```
        20
       /  \
      10   30
     /  \     \
    5   15    40
   /         /
  2         35
  
Balance factors: 20(2), 10(0), 30(2), 5(1), 15(0), 40(2), 35(0)
20 has imbalance of 2 ✗
```

## Balance Factor

The **balance factor** of a node is:
```
Balance Factor = Height(Left Subtree) - Height(Right Subtree)
```

### Valid Range

```
-1 ≤ Balance Factor ≤ 1

Valid:   -1, 0, +1
Invalid: -2, -3, +2, +3, ...
```

### Calculation Example

```
        50 (bf = 0)
       /  \
     30    70 (bf = 1)
    /  \  /
  20   40 60
  
Height(left of 50) = 2
Height(right of 50) = 2
Balance Factor of 50 = 2 - 2 = 0

Height(left of 70) = 1
Height(right of 70) = 0
Balance Factor of 70 = 1 - 0 = 1
```

## Rotations

When balance factor becomes ±2, rotations are needed to restore balance.

### 1. **Left Rotation** (Right-heavy case)

```
Before Left Rotation:    After Left Rotation:
      Z                        Y
     /                        / \
    T1                       Z   T3
      \                     / \
       Y                   T1  T2
      / \
    T2   T3

Example:
     10                    20
       \                   / \
        20        →       10  30
       /  \
      15  30
```

### 2. **Right Rotation** (Left-heavy case)

```
Before Right Rotation:   After Right Rotation:
       Z                      Y
        \                    / \
        T4                  T1   Z
       /                       / \
      Y                       T2  T4
     / \
   T1   T2

Example:
         30              20
        /              /  \
       20        →    10   30
      / \
     10  25
```

### 3. **Left-Right Rotation** (Left-Right case)

```
Step 1: Left rotate on left child
        Z           Z
       /           /
      X     →     Y
        \       /
         Y     X

Step 2: Right rotate on root
        Z                Y
       /                / \
      Y        →       X   Z
     /
    X

Example:
        10            10            20
       /             /             / \
      5     →      8      →       8   10
       \         /
        8       5
```

### 4. **Right-Left Rotation** (Right-Left case)

```
Step 1: Right rotate on right child
      X             X
       \             \
        Z      →      Y
       /               \
      Y                 Z

Step 2: Left rotate on root
      X                Y
       \              / \
        Y    →       X   Z
         \
          Z

Example:
       20           20           15
         \            \         / \
          30    →     15      20  30
         /              \
        15              30
```

## Insertion Process

### Steps
1. Insert node like in regular BST
2. Update balance factors
3. If any node has balance factor = ±2, perform rotation
4. Repeat up the tree

### Example Insertion: Insert 5, 10, 15

```
Step 1: Insert 5
    5

Step 2: Insert 10
    5
     \
     10

Step 3: Insert 15 (causes imbalance at 5)
    5
     \
     10
      \
      15
      
Balance factor of 5 = -2 (right-heavy)
Left Rotation needed:

       10
      / \
     5  15

All balanced ✓
```

## Deletion Process

### Cases

1. **Deleting leaf node**: Simply remove
2. **Deleting node with one child**: Replace with child
3. **Deleting node with two children**: Replace with inorder successor/predecessor, then delete successor

After deletion, check balance factors from deleted node up to root.

### Example Deletion

```
Delete 5 from:
       10
      / \
     5  15

Result:
       10
         \
         15

Still balanced ✓
```

## Performance Analysis

| Operation | Time | Space | Guarantee |
|-----------|------|-------|-----------|
| Search | O(log n) | O(1) | Always |
| Insert | O(log n) | O(1) | Always |
| Delete | O(log n) | O(1) | Always |
| Traversal | O(n) | O(h) | Always |
| Total Space | - | O(n) | Always |

**Key Advantage**: O(log n) is guaranteed, unlike BSTs where worst case is O(n).

### Why O(log n)?

For an AVL tree with n nodes:
```
Height h ≤ 1.44 * log₂(n + 2) - 1

For n = 1,000,000: h ≤ 20
For n = 1,000,000,000: h ≤ 30
```

## Use Cases

1. **Database Indexing**: MySQL, PostgreSQL use B-Trees (AVL variant)
2. **File Systems**: NTFS uses B+ Trees
3. **Compiler Symbol Tables**: Maintaining identifiers
4. **Network Routers**: IP routing tables
5. **Game Development**: AI decision trees
6. **Priority Queues**: Balanced heap implementation
7. **Caching**: LRU cache with fast lookups
8. **Real-time Systems**: Guaranteed response time

## Code Examples

### Complete AVL Tree Implementation

```java
import java.util.*;

class TreeNode {
    int data;
    TreeNode left;
    TreeNode right;
    int height;
    
    TreeNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}

class AVLTree {
    private TreeNode root;
    
    AVLTree() {
        this.root = null;
    }
    
    // Get height of node
    private int getHeight(TreeNode node) {
        return node == null ? 0 : node.height;
    }
    
    // Get balance factor
    private int getBalance(TreeNode node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }
    
    // Update height of node
    private void updateHeight(TreeNode node) {
        if (node != null) {
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }
    }
    
    // Left Rotation
    private TreeNode rotateLeft(TreeNode node) {
        TreeNode rightChild = node.right;
        TreeNode leftGrandchild = rightChild.left;
        
        // Perform rotation
        rightChild.left = node;
        node.right = leftGrandchild;
        
        // Update heights
        updateHeight(node);
        updateHeight(rightChild);
        
        return rightChild;
    }
    
    // Right Rotation
    private TreeNode rotateRight(TreeNode node) {
        TreeNode leftChild = node.left;
        TreeNode rightGrandchild = leftChild.right;
        
        // Perform rotation
        leftChild.right = node;
        node.left = rightGrandchild;
        
        // Update heights
        updateHeight(node);
        updateHeight(leftChild);
        
        return leftChild;
    }
    
    // Insert into AVL Tree
    public void insert(int data) {
        root = insertHelper(root, data);
    }
    
    private TreeNode insertHelper(TreeNode node, int data) {
        // Standard BST insertion
        if (node == null) {
            return new TreeNode(data);
        }
        
        if (data < node.data) {
            node.left = insertHelper(node.left, data);
        } else if (data > node.data) {
            node.right = insertHelper(node.right, data);
        } else {
            return node; // Duplicate
        }
        
        // Update height of current node
        updateHeight(node);
        
        // Get balance factor
        int balance = getBalance(node);
        
        // Left-Left Case
        if (balance > 1 && data < node.left.data) {
            return rotateRight(node);
        }
        
        // Left-Right Case
        if (balance > 1 && data > node.left.data) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        
        // Right-Right Case
        if (balance < -1 && data > node.right.data) {
            return rotateLeft(node);
        }
        
        // Right-Left Case
        if (balance < -1 && data < node.right.data) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        
        return node;
    }
    
    // Search in AVL Tree
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
    
    // Delete from AVL Tree
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
            
            // Case 1 & 2: No child or one child
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            
            // Case 3: Two children
            // Find inorder successor (min in right subtree)
            TreeNode successor = findMin(node.right);
            node.data = successor.data;
            node.right = deleteHelper(node.right, successor.data);
        }
        
        if (node == null) return null;
        
        // Update height
        updateHeight(node);
        
        // Get balance factor
        int balance = getBalance(node);
        
        // Left-Left Case
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }
        
        // Left-Right Case
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        
        // Right-Right Case
        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }
        
        // Right-Left Case
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        
        return node;
    }
    
    // Find minimum node
    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    // Get minimum value
    public int getMin() {
        if (root == null) throw new IllegalStateException("Tree is empty");
        return findMin(root).data;
    }
    
    // Get maximum value
    public int getMax() {
        if (root == null) throw new IllegalStateException("Tree is empty");
        TreeNode current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.data;
    }
    
    // Inorder traversal
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
    
    // Get tree height
    public int getHeight() {
        return getHeight(root);
    }
    
    // Count nodes
    public int countNodes() {
        return countNodesHelper(root);
    }
    
    private int countNodesHelper(TreeNode node) {
        if (node == null) return 0;
        return 1 + countNodesHelper(node.left) + countNodesHelper(node.right);
    }
    
    // Check if tree is balanced (should always be true for AVL)
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
    
    // Print tree structure with balance factors
    public void printTree() {
        System.out.println("\nAVL Tree Structure (with Balance Factors):");
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
        
        int balance = getBalance(node);
        System.out.println(node.data + " (bf=" + balance + ", h=" + node.height + ")");
        
        if (node.left != null || node.right != null) {
            if (node.left != null) {
                printTreeHelper(node.left, indent, node.right == null);
            }
            if (node.right != null) {
                printTreeHelper(node.right, indent, true);
            }
        }
    }
    
    // Main method for testing
    public static void main(String[] args) {
        AVLTree avl = new AVLTree();
        
        System.out.println("AVL Tree Operations Demo");
        System.out.println("========================");
        
        // Insert values - will demonstrate rotations
        int[] values = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 35, 65};
        
        System.out.println("\nInserting: " + Arrays.toString(values));
        for (int val : values) {
            avl.insert(val);
            System.out.println("After inserting " + val + ":");
            avl.printTree();
        }
        
        System.out.println("\nFinal Tree Traversals:");
        avl.inorder();
        avl.preorder();
        avl.postorder();
        avl.levelorder();
        
        System.out.println("\nTree Properties:");
        System.out.println("Height: " + avl.getHeight());
        System.out.println("Total Nodes: " + avl.countNodes());
        System.out.println("Is Balanced: " + avl.isBalanced());
        System.out.println("Min Value: " + avl.getMin());
        System.out.println("Max Value: " + avl.getMax());
        
        System.out.println("\nSearch Operations:");
        System.out.println("Search 30: " + avl.search(30));
        System.out.println("Search 100: " + avl.search(100));
        
        System.out.println("\nDeletion Operations:");
        System.out.println("Deleting 10 (leaf node):");
        avl.delete(10);
        avl.printTree();
        
        System.out.println("\nDeleting 25 (node with two children):");
        avl.delete(25);
        avl.printTree();
        
        System.out.println("\nFinal traversal after deletions:");
        avl.inorder();
    }
}
```

## Comparison with Other Trees

| Feature | AVL | Red-Black | BST | 2-3 Tree |
|---------|-----|-----------|-----|----------|
| Search | O(log n) | O(log n) | O(log n)* | O(log n) |
| Insert | O(log n) | O(log n) | O(log n)* | O(log n) |
| Delete | O(log n) | O(log n) | O(log n)* | O(log n) |
| Balance Strict | Yes | No (relaxed) | No | Yes |
| Rotations/Insert | 0-2 | 0-1 | 0 | Complex |
| Search Speed | Fastest | Slightly slower | Variable | Slower |
| Space | O(n) | O(n) | O(n) | O(n) |

*BST worst case: O(n) in unbalanced cases

## Key Takeaways

1. **Guaranteed Performance**: AVL trees always maintain O(log n) operations
2. **Self-Balancing**: Automatic rebalancing through rotations
3. **Balance Factor**: Critical concept for understanding AVL operations
4. **Rotation Types**: Four rotation cases handle all imbalance scenarios
5. **Trade-off**: Slower insertion/deletion than BST but faster search in worst case
6. **Real-World**: Used in systems requiring guaranteed performance (databases, file systems)
7. **Alternatives**: Red-Black trees offer similar benefits with simpler rotations
