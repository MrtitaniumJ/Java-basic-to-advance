# Trees in Java

## Table of Contents
- [Simple Explanation](#simple-explanation)
- [Professional Definition](#professional-definition)
- [Tree Terminology](#tree-terminology)
- [Types of Trees - Comparison](#types-of-trees---comparison)
- [Binary Tree](#binary-tree)
- [Binary Search Tree (BST)](#binary-search-tree-bst)
- [AVL Tree](#avl-tree)
- [Red-Black Tree](#red-black-tree)
- [B-Tree](#b-tree)
- [Trie (Prefix Tree)](#trie-prefix-tree)
- [Tree Traversals](#tree-traversals)
- [When to Use Each Type](#when-to-use-each-type)
- [Interview Questions](#interview-questions)

---

## Simple Explanation

Think of a **family tree** 🌳:
- At the top, you have your grandparents (the **root**)
- They have children (parents), and those children have their own children (you and your siblings)
- Each person can be connected to multiple people below them, but only to one person above them
- People at the bottom with no children are called **leaves**

Just like a family tree shows relationships between people, a tree data structure shows relationships between data elements. Each "person" in our tree is called a **node**, and the lines connecting them show parent-child relationships.

**Key Insight**: Trees grow downward in computer science! The root is at the top, and leaves are at the bottom.

---

## Professional Definition

A **tree** is a non-linear, hierarchical data structure consisting of nodes connected by edges, where:
- There is exactly one root node (the topmost node)
- Each node has zero or more child nodes
- Each node (except root) has exactly one parent node
- There are no cycles (you can't loop back to a node you've already visited)
- All nodes are reachable from the root

**Formal Properties**:
- A tree with `n` nodes has exactly `n-1` edges
- A tree is a connected acyclic graph
- Trees are recursive structures: each subtree is itself a tree

---

## Tree Terminology

```
         A (Root)              Height = 3
        / \                    Depth of A = 0
       B   C                   Depth of B,C = 1
      / \   \                  Depth of D,E,F = 2
     D   E   F                 Depth of G = 3
        /
       G (Leaf)
```

### Essential Terms

| Term | Definition | Example |
|------|------------|---------|
| **Root** | The topmost node with no parent | Node A |
| **Parent** | A node that has children | B is parent of D and E |
| **Child** | A node connected to a parent above it | D and E are children of B |
| **Leaf** | A node with no children | D, G, F are leaves |
| **Internal Node** | A node with at least one child | A, B, C, E |
| **Sibling** | Nodes with the same parent | B and C are siblings |
| **Ancestor** | All nodes on the path from root to that node | A, B are ancestors of D |
| **Descendant** | All nodes reachable from a node going down | D, E, G are descendants of B |
| **Subtree** | A tree formed by a node and all its descendants | Subtree rooted at B: B, D, E, G |
| **Height** | Longest path from node to a leaf (edges count) | Height of tree = 3 |
| **Depth** | Number of edges from root to node | Depth of E = 2 |
| **Level** | Depth + 1 | Level of E = 3 |
| **Degree** | Number of children a node has | Degree of B = 2, Degree of C = 1 |

### Important Formulas

- **Height of Tree** = Height of root = Max depth of any leaf
- **Maximum nodes at level L** = 2^L (for binary trees)
- **Maximum nodes in tree of height H** = 2^(H+1) - 1 (for binary trees)
- **Minimum height for N nodes** = ⌈log₂(N+1)⌉ - 1

---

## Types of Trees - Comparison

| Tree Type | Balanced | Ordering | Max Children | Search Time | Insert Time | Use Case |
|-----------|----------|----------|--------------|-------------|-------------|----------|
| **Binary Tree** | No | None | 2 | O(n) | O(1) | General hierarchy |
| **BST** | No | Left < Root < Right | 2 | O(n) worst, O(log n) avg | O(n) worst, O(log n) avg | Sorted data, searching |
| **AVL Tree** | Yes (Strict) | Left < Root < Right | 2 | O(log n) | O(log n) | Frequent searches, less inserts |
| **Red-Black Tree** | Yes (Relaxed) | Left < Root < Right | 2 | O(log n) | O(log n) | Balanced searches/inserts |
| **B-Tree** | Yes | Sorted within nodes | Many (m) | O(log n) | O(log n) | Databases, file systems |
| **Trie** | N/A | Prefix-based | 26+ (alphabet) | O(m) m=key length | O(m) | Autocomplete, dictionaries |

---

## Binary Tree

### Definition
A tree where each node has **at most 2 children** (left child and right child).

### Basic Node Structure
```java
class TreeNode {
    int data;
    TreeNode left;
    TreeNode right;
    
    // Constructor
    TreeNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
```

### Complete Binary Tree Example
```java
public class BinaryTree {
    TreeNode root;
    
    // Constructor
    public BinaryTree() {
        this.root = null;
    }
    
    // Insert nodes (level-order insertion for complete binary tree)
    public void insert(int data) {
        TreeNode newNode = new TreeNode(data);
        
        if (root == null) {
            root = newNode;
            return;
        }
        
        // Use queue for level-order insertion
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            
            if (current.left == null) {
                current.left = newNode;
                return;
            } else {
                queue.add(current.left);
            }
            
            if (current.right == null) {
                current.right = newNode;
                return;
            } else {
                queue.add(current.right);
            }
        }
    }
    
    // Get height of tree
    public int height(TreeNode node) {
        if (node == null) {
            return -1;  // Height of empty tree is -1
        }
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    // Count total nodes
    public int countNodes(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
    
    // Check if tree is full (every node has 0 or 2 children)
    public boolean isFull(TreeNode node) {
        if (node == null) {
            return true;
        }
        
        // If leaf node
        if (node.left == null && node.right == null) {
            return true;
        }
        
        // If both children exist, check recursively
        if (node.left != null && node.right != null) {
            return isFull(node.left) && isFull(node.right);
        }
        
        return false;  // One child exists, other doesn't
    }
    
    // Main method for testing
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        
        System.out.println("Height: " + tree.height(tree.root));
        System.out.println("Total Nodes: " + tree.countNodes(tree.root));
        System.out.println("Is Full: " + tree.isFull(tree.root));
    }
}
```

---

## Binary Search Tree (BST)

### Definition
A binary tree with the **BST property**: For every node:
- All values in the **left subtree** are **less than** the node's value
- All values in the **right subtree** are **greater than** the node's value

### Complete BST Implementation
```java
public class BinarySearchTree {
    TreeNode root;
    
    public BinarySearchTree() {
        this.root = null;
    }
    
    // Insert a value
    public void insert(int data) {
        root = insertRec(root, data);
    }
    
    private TreeNode insertRec(TreeNode root, int data) {
        // Base case: empty tree
        if (root == null) {
            return new TreeNode(data);
        }
        
        // Recursive insertion
        if (data < root.data) {
            root.left = insertRec(root.left, data);
        } else if (data > root.data) {
            root.right = insertRec(root.right, data);
        }
        
        return root;  // Return unchanged root
    }
    
    // Search for a value
    public boolean search(int data) {
        return searchRec(root, data);
    }
    
    private boolean searchRec(TreeNode root, int data) {
        // Base cases
        if (root == null) {
            return false;
        }
        if (root.data == data) {
            return true;
        }
        
        // Recursive search
        if (data < root.data) {
            return searchRec(root.left, data);
        } else {
            return searchRec(root.right, data);
        }
    }
    
    // Delete a value
    public void delete(int data) {
        root = deleteRec(root, data);
    }
    
    private TreeNode deleteRec(TreeNode root, int data) {
        // Base case
        if (root == null) {
            return null;
        }
        
        // Find the node to delete
        if (data < root.data) {
            root.left = deleteRec(root.left, data);
        } else if (data > root.data) {
            root.right = deleteRec(root.right, data);
        } else {
            // Node found - handle 3 cases
            
            // Case 1: No children (leaf node)
            if (root.left == null && root.right == null) {
                return null;
            }
            
            // Case 2: One child
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            
            // Case 3: Two children
            // Find inorder successor (smallest in right subtree)
            TreeNode successor = findMin(root.right);
            root.data = successor.data;  // Replace data
            root.right = deleteRec(root.right, successor.data);  // Delete successor
        }
        
        return root;
    }
    
    // Find minimum value node
    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    // Find maximum value
    public int findMax() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
        TreeNode current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.data;
    }
    
    // Validate if tree is a valid BST
    public boolean isValidBST() {
        return isValidBSTRec(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private boolean isValidBSTRec(TreeNode node, int min, int max) {
        if (node == null) {
            return true;
        }
        
        // Check BST property
        if (node.data <= min || node.data >= max) {
            return false;
        }
        
        // Check left and right subtrees
        return isValidBSTRec(node.left, min, node.data) &&
               isValidBSTRec(node.right, node.data, max);
    }
    
    // Main method
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        
        // Insert values
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int val : values) {
            bst.insert(val);
        }
        
        System.out.println("Search 40: " + bst.search(40));
        System.out.println("Search 100: " + bst.search(100));
        System.out.println("Max value: " + bst.findMax());
        System.out.println("Is valid BST: " + bst.isValidBST());
        
        bst.delete(30);
        System.out.println("After deleting 30, search 30: " + bst.search(30));
    }
}
```

---

## AVL Tree

### Definition
A **self-balancing** BST where the height difference between left and right subtrees (balance factor) is **at most 1** for every node.

**Balance Factor** = Height(Left Subtree) - Height(Right Subtree)  
Valid balance factors: -1, 0, 1

### AVL Tree Implementation
```java
class AVLNode {
    int data;
    int height;
    AVLNode left;
    AVLNode right;
    
    AVLNode(int data) {
        this.data = data;
        this.height = 1;  // New node initially at height 1
    }
}

public class AVLTree {
    AVLNode root;
    
    // Get height of node
    private int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }
    
    // Get balance factor
    private int getBalance(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }
    
    // Update height of node
    private void updateHeight(AVLNode node) {
        if (node != null) {
            node.height = Math.max(height(node.left), height(node.right)) + 1;
        }
    }
    
    // Right rotation
    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        
        // Perform rotation
        x.right = y;
        y.left = T2;
        
        // Update heights
        updateHeight(y);
        updateHeight(x);
        
        return x;  // New root
    }
    
    // Left rotation
    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        
        // Perform rotation
        y.left = x;
        x.right = T2;
        
        // Update heights
        updateHeight(x);
        updateHeight(y);
        
        return y;  // New root
    }
    
    // Insert a value
    public void insert(int data) {
        root = insertRec(root, data);
    }
    
    private AVLNode insertRec(AVLNode node, int data) {
        // Standard BST insertion
        if (node == null) {
            return new AVLNode(data);
        }
        
        if (data < node.data) {
            node.left = insertRec(node.left, data);
        } else if (data > node.data) {
            node.right = insertRec(node.right, data);
        } else {
            return node;  // Duplicate values not allowed
        }
        
        // Update height
        updateHeight(node);
        
        // Get balance factor
        int balance = getBalance(node);
        
        // 4 cases for rotations
        
        // Left-Left Case
        if (balance > 1 && data < node.left.data) {
            return rotateRight(node);
        }
        
        // Right-Right Case
        if (balance < -1 && data > node.right.data) {
            return rotateLeft(node);
        }
        
        // Left-Right Case
        if (balance > 1 && data > node.left.data) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        
        // Right-Left Case
        if (balance < -1 && data < node.right.data) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        
        return node;
    }
    
    // Inorder traversal
    public void inorder() {
        inorderRec(root);
        System.out.println();
    }
    
    private void inorderRec(AVLNode node) {
        if (node != null) {
            inorderRec(node.left);
            System.out.print(node.data + " ");
            inorderRec(node.right);
        }
    }
    
    // Main method
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        
        // These insertions would create unbalanced BST
        // AVL tree automatically balances
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(25);
        
        System.out.println("Inorder traversal of AVL tree:");
        tree.inorder();
        
        System.out.println("Tree height: " + tree.height(tree.root));
    }
}
```

---

## Red-Black Tree

### Definition
A **self-balancing** BST with additional color property (red or black) that maintains balance through coloring rules.

### Red-Black Properties
1. Every node is either **red** or **black**
2. Root is always **black**
3. All leaves (null) are **black**
4. Red nodes cannot have red children (no two red nodes in a row)
5. Every path from root to leaf has the **same number of black nodes** (black height)

### Red-Black Tree Implementation
```java
enum Color {
    RED, BLACK
}

class RBNode {
    int data;
    Color color;
    RBNode left, right, parent;
    
    RBNode(int data) {
        this.data = data;
        this.color = Color.RED;  // New nodes are always red
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

public class RedBlackTree {
    private RBNode root;
    private RBNode TNULL;  // Sentinel null node
    
    public RedBlackTree() {
        TNULL = new RBNode(0);
        TNULL.color = Color.BLACK;
        root = TNULL;
    }
    
    // Left rotation
    private void leftRotate(RBNode x) {
        RBNode y = x.right;
        x.right = y.left;
        
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        
        y.parent = x.parent;
        
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        
        y.left = x;
        x.parent = y;
    }
    
    // Right rotation
    private void rightRotate(RBNode x) {
        RBNode y = x.left;
        x.left = y.right;
        
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        
        y.parent = x.parent;
        
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        
        y.right = x;
        x.parent = y;
    }
    
    // Insert a value
    public void insert(int key) {
        RBNode node = new RBNode(key);
        node.left = TNULL;
        node.right = TNULL;
        
        RBNode y = null;
        RBNode x = this.root;
        
        // Standard BST insertion
        while (x != TNULL) {
            y = x;
            if (node.data < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        
        node.parent = y;
        
        if (y == null) {
            root = node;
        } else if (node.data < y.data) {
            y.left = node;
        } else {
            y.right = node;
        }
        
        if (node.parent == null) {
            node.color = Color.BLACK;
            return;
        }
        
        if (node.parent.parent == null) {
            return;
        }
        
        // Fix Red-Black properties
        fixInsert(node);
    }
    
    // Fix violations after insertion
    private void fixInsert(RBNode k) {
        RBNode u;
        
        while (k.parent.color == Color.RED) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;  // Uncle
                
                if (u.color == Color.RED) {
                    // Case 1: Uncle is red
                    u.color = Color.BLACK;
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        // Case 2: Node is left child
                        k = k.parent;
                        rightRotate(k);
                    }
                    // Case 3: Node is right child
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;  // Uncle
                
                if (u.color == Color.RED) {
                    // Case 1: Uncle is red
                    u.color = Color.BLACK;
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        // Case 2: Node is right child
                        k = k.parent;
                        leftRotate(k);
                    }
                    // Case 3: Node is left child
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    rightRotate(k.parent.parent);
                }
            }
            
            if (k == root) {
                break;
            }
        }
        
        root.color = Color.BLACK;
    }
    
    // Inorder traversal
    public void inorder() {
        inorderHelper(this.root);
        System.out.println();
    }
    
    private void inorderHelper(RBNode node) {
        if (node != TNULL) {
            inorderHelper(node.left);
            System.out.print(node.data + "(" + node.color + ") ");
            inorderHelper(node.right);
        }
    }
    
    // Main method
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        
        int[] values = {7, 3, 18, 10, 22, 8, 11, 26};
        for (int val : values) {
            tree.insert(val);
        }
        
        System.out.println("Inorder traversal:");
        tree.inorder();
    }
}
```

---

## B-Tree

### Definition
A self-balancing tree optimized for systems that read/write large blocks of data (databases, file systems). Each node can have **multiple keys** and **multiple children**.

### B-Tree Properties (for order m)
- Every node has at most **m children**
- Every internal node (except root) has at least **⌈m/2⌉ children**
- Root has at least 2 children (unless it's a leaf)
- All leaves are at the same level
- A node with k children has **k-1 keys**

### Simplified B-Tree Implementation (Order 3)
```java
class BTreeNode {
    int[] keys;
    int t;  // Minimum degree (minimum children = t, maximum = 2t)
    BTreeNode[] children;
    int n;  // Current number of keys
    boolean leaf;
    
    BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        this.keys = new int[2 * t - 1];  // Maximum keys
        this.children = new BTreeNode[2 * t];  // Maximum children
        this.n = 0;
    }
    
    // Search for a key
    BTreeNode search(int key) {
        int i = 0;
        while (i < n && key > keys[i]) {
            i++;
        }
        
        if (i < n && keys[i] == key) {
            return this;
        }
        
        if (leaf) {
            return null;
        }
        
        return children[i].search(key);
    }
    
    // Insert a key (assuming node is not full)
    void insertNonFull(int key) {
        int i = n - 1;
        
        if (leaf) {
            // Find location and shift keys
            while (i >= 0 && keys[i] > key) {
                keys[i + 1] = keys[i];
                i--;
            }
            
            keys[i + 1] = key;
            n++;
        } else {
            // Find child to insert
            while (i >= 0 && keys[i] > key) {
                i--;
            }
            i++;
            
            if (children[i].n == 2 * t - 1) {
                // Child is full, split it
                splitChild(i, children[i]);
                
                if (keys[i] < key) {
                    i++;
                }
            }
            
            children[i].insertNonFull(key);
        }
    }
    
    // Split a full child
    void splitChild(int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(y.t, y.leaf);
        z.n = t - 1;
        
        // Copy last (t-1) keys to z
        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t];
        }
        
        // Copy last t children to z
        if (!y.leaf) {
            for (int j = 0; j < t; j++) {
                z.children[j] = y.children[j + t];
            }
        }
        
        y.n = t - 1;
        
        // Shift children to make space
        for (int j = n; j >= i + 1; j--) {
            children[j + 1] = children[j];
        }
        
        children[i + 1] = z;
        
        // Move key from y to this node
        for (int j = n - 1; j >= i; j--) {
            keys[j + 1] = keys[j];
        }
        
        keys[i] = y.keys[t - 1];
        n++;
    }
    
    // Traverse all keys
    void traverse() {
        int i;
        for (i = 0; i < n; i++) {
            if (!leaf) {
                children[i].traverse();
            }
            System.out.print(keys[i] + " ");
        }
        
        if (!leaf) {
            children[i].traverse();
        }
    }
}

public class BTree {
    BTreeNode root;
    int t;  // Minimum degree
    
    public BTree(int t) {
        this.root = null;
        this.t = t;
    }
    
    // Search for a key
    public BTreeNode search(int key) {
        return (root == null) ? null : root.search(key);
    }
    
    // Insert a key
    public void insert(int key) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = key;
            root.n = 1;
        } else {
            // If root is full, split it
            if (root.n == 2 * t - 1) {
                BTreeNode s = new BTreeNode(t, false);
                s.children[0] = root;
                s.splitChild(0, root);
                
                int i = 0;
                if (s.keys[0] < key) {
                    i++;
                }
                s.children[i].insertNonFull(key);
                
                root = s;
            } else {
                root.insertNonFull(key);
            }
        }
    }
    
    // Traverse the tree
    public void traverse() {
        if (root != null) {
            root.traverse();
        }
        System.out.println();
    }
    
    // Main method
    public static void main(String[] args) {
        BTree tree = new BTree(3);  // Minimum degree 3
        
        int[] values = {10, 20, 5, 6, 12, 30, 7, 17};
        for (int val : values) {
            tree.insert(val);
        }
        
        System.out.println("B-Tree traversal:");
        tree.traverse();
        
        int searchKey = 6;
        System.out.println("\nSearch for " + searchKey + ": " + 
                          (tree.search(searchKey) != null ? "Found" : "Not Found"));
    }
}
```

---

## Trie (Prefix Tree)

### Definition
A tree-like data structure used to store strings where each node represents a character. Perfect for **prefix-based searches** like autocomplete.

### Key Features
- Each path from root to a node represents a string
- Common prefixes share the same path
- Each node has up to 26 children (for lowercase English alphabet)
- Space-efficient for storing many strings with common prefixes

### Trie Implementation
```java
class TrieNode {
    TrieNode[] children;
    boolean isEndOfWord;
    
    TrieNode() {
        children = new TrieNode[26];  // For 'a' to 'z'
        isEndOfWord = false;
    }
}

public class Trie {
    private TrieNode root;
    
    public Trie() {
        root = new TrieNode();
    }
    
    // Insert a word into trie
    public void insert(String word) {
        TrieNode current = root;
        
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';  // Convert char to index (0-25)
            
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            
            current = current.children[index];
        }
        
        current.isEndOfWord = true;  // Mark end of word
    }
    
    // Search for a complete word
    public boolean search(String word) {
        TrieNode node = searchNode(word);
        return node != null && node.isEndOfWord;
    }
    
    // Check if any word starts with given prefix
    public boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }
    
    // Helper method to search for a node
    private TrieNode searchNode(String str) {
        TrieNode current = root;
        
        for (char ch : str.toCharArray()) {
            int index = ch - 'a';
            
            if (current.children[index] == null) {
                return null;  // Character not found
            }
            
            current = current.children[index];
        }
        
        return current;
    }
    
    // Delete a word from trie
    public void delete(String word) {
        deleteRec(root, word, 0);
    }
    
    private boolean deleteRec(TrieNode current, String word, int index) {
        if (index == word.length()) {
            // Reached end of word
            if (!current.isEndOfWord) {
                return false;  // Word doesn't exist
            }
            
            current.isEndOfWord = false;
            
            // If node has no children, it can be deleted
            return isEmpty(current);
        }
        
        char ch = word.charAt(index);
        int charIndex = ch - 'a';
        TrieNode node = current.children[charIndex];
        
        if (node == null) {
            return false;  // Word doesn't exist
        }
        
        boolean shouldDeleteCurrentNode = deleteRec(node, word, index + 1);
        
        if (shouldDeleteCurrentNode) {
            current.children[charIndex] = null;
            return !current.isEndOfWord && isEmpty(current);
        }
        
        return false;
    }
    
    // Check if node has any children
    private boolean isEmpty(TrieNode node) {
        for (TrieNode child : node.children) {
            if (child != null) {
                return false;
            }
        }
        return true;
    }
    
    // Print all words with given prefix
    public void printWordsWithPrefix(String prefix) {
        TrieNode node = searchNode(prefix);
        
        if (node == null) {
            System.out.println("No words found with prefix: " + prefix);
            return;
        }
        
        System.out.println("Words with prefix '" + prefix + "':");
        printWordsRec(node, prefix);
    }
    
    private void printWordsRec(TrieNode node, String currentWord) {
        if (node.isEndOfWord) {
            System.out.println(currentWord);
        }
        
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                char ch = (char) (i + 'a');
                printWordsRec(node.children[i], currentWord + ch);
            }
        }
    }
    
    // Count total words in trie
    public int countWords() {
        return countWordsRec(root);
    }
    
    private int countWordsRec(TrieNode node) {
        if (node == null) {
            return 0;
        }
        
        int count = node.isEndOfWord ? 1 : 0;
        
        for (TrieNode child : node.children) {
            count += countWordsRec(child);
        }
        
        return count;
    }
    
    // Main method
    public static void main(String[] args) {
        Trie trie = new Trie();
        
        // Insert words
        String[] words = {"apple", "app", "apricot", "banana", "band", "bandana"};
        for (String word : words) {
            trie.insert(word);
        }
        
        // Search operations
        System.out.println("Search 'apple': " + trie.search("apple"));
        System.out.println("Search 'app': " + trie.search("app"));
        System.out.println("Search 'appl': " + trie.search("appl"));
        
        // Prefix operations
        System.out.println("\nStarts with 'app': " + trie.startsWith("app"));
        System.out.println("Starts with 'ban': " + trie.startsWith("ban"));
        
        // Print words with prefix
        System.out.println();
        trie.printWordsWithPrefix("ban");
        
        System.out.println("\nTotal words: " + trie.countWords());
        
        // Delete and verify
        trie.delete("app");
        System.out.println("After deleting 'app', search 'app': " + trie.search("app"));
        System.out.println("Search 'apple': " + trie.search("apple"));
    }
}
```

---

## Tree Traversals

Tree traversal is the process of visiting each node in the tree exactly once. Different traversal orders serve different purposes.

### 1. Inorder Traversal (Left → Root → Right)
**Use Case**: Get nodes in sorted order for BST

```java
public void inorder(TreeNode root) {
    if (root == null) {
        return;
    }
    
    inorder(root.left);           // Visit left subtree
    System.out.print(root.data + " ");  // Visit root
    inorder(root.right);          // Visit right subtree
}
```

**Output for BST (50, 30, 70, 20, 40, 60, 80)**: 20 30 40 50 60 70 80

---

### 2. Preorder Traversal (Root → Left → Right)
**Use Case**: Create copy of tree, prefix expression evaluation

```java
public void preorder(TreeNode root) {
    if (root == null) {
        return;
    }
    
    System.out.print(root.data + " ");  // Visit root
    preorder(root.left);                // Visit left subtree
    preorder(root.right);               // Visit right subtree
}
```

**Output**: 50 30 20 40 70 60 80

---

### 3. Postorder Traversal (Left → Right → Root)
**Use Case**: Delete tree, postfix expression evaluation

```java
public void postorder(TreeNode root) {
    if (root == null) {
        return;
    }
    
    postorder(root.left);               // Visit left subtree
    postorder(root.right);              // Visit right subtree
    System.out.print(root.data + " ");  // Visit root
}
```

**Output**: 20 40 30 60 80 70 50

---

### 4. Level Order Traversal (Breadth-First)
**Use Case**: Find shortest path, print tree level by level

```java
import java.util.Queue;
import java.util.LinkedList;

public void levelOrder(TreeNode root) {
    if (root == null) {
        return;
    }
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    
    while (!queue.isEmpty()) {
        TreeNode current = queue.poll();
        System.out.print(current.data + " ");
        
        if (current.left != null) {
            queue.add(current.left);
        }
        if (current.right != null) {
            queue.add(current.right);
        }
    }
}
```

**Output**: 50 30 70 20 40 60 80

---

### Complete Traversal Example
```java
public class TreeTraversals {
    
    public static void main(String[] args) {
        // Create tree:
        //       50
        //      /  \
        //     30   70
        //    / \   / \
        //   20 40 60 80
        
        TreeNode root = new TreeNode(50);
        root.left = new TreeNode(30);
        root.right = new TreeNode(70);
        root.left.left = new TreeNode(20);
        root.left.right = new TreeNode(40);
        root.right.left = new TreeNode(60);
        root.right.right = new TreeNode(80);
        
        System.out.print("Inorder: ");
        inorder(root);
        System.out.println();
        
        System.out.print("Preorder: ");
        preorder(root);
        System.out.println();
        
        System.out.print("Postorder: ");
        postorder(root);
        System.out.println();
        
        System.out.print("Level Order: ");
        levelOrder(root);
        System.out.println();
    }
    
    // Traversal methods as shown above...
}
```

---

## When to Use Each Type

### Binary Tree
✅ **Use When**:
- Representing hierarchical data (organization charts, file systems)
- Building expression trees
- Implementing heap data structure
- No specific ordering required

❌ **Don't Use When**:
- Need efficient searching (use BST instead)
- Need guaranteed balance (use AVL/Red-Black)

---

### Binary Search Tree (BST)
✅ **Use When**:
- Need sorted data with efficient search
- Implementing dictionaries or symbol tables
- Range queries (find all values between x and y)
- Dynamic data with insertions and deletions
- Memory is limited (simpler than balanced trees)

❌ **Don't Use When**:
- Data arrives in sorted/nearly sorted order (becomes unbalanced)
- Need guaranteed O(log n) operations (use AVL/Red-Black)

---

### AVL Tree
✅ **Use When**:
- Search operations are **much more frequent** than insertions
- Need **strictly balanced** tree (height difference ≤ 1)
- Read-heavy applications (databases with frequent queries)
- Real-time systems requiring consistent performance

❌ **Don't Use When**:
- Frequent insertions/deletions (high rebalancing cost)
- Can tolerate slight imbalance (use Red-Black instead)

---

### Red-Black Tree
✅ **Use When**:
- Need balance between search and insert/delete
- **Frequent insertions and deletions**
- Implementing language libraries (Java TreeMap, C++ map)
- Relaxed balancing is acceptable (height ≤ 2 * optimal)

❌ **Don't Use When**:
- Need strict balancing (use AVL)
- Simple BST would suffice

**Real-World Usage**: Java's `TreeMap` and `TreeSet` use Red-Black Trees

---

### B-Tree
✅ **Use When**:
- Working with **databases and file systems**
- Data stored on disk (minimize disk I/O)
- Need to read/write large blocks of data
- Each node should store multiple keys
- Balanced tree with large branching factor

❌ **Don't Use When**:
- All data fits in memory (use simpler trees)
- Working with small datasets

**Real-World Usage**: MySQL, PostgreSQL, MongoDB indexes

---

### Trie
✅ **Use When**:
- **Autocomplete** systems
- Spell checkers
- IP routing tables
- Dictionary implementations
- Prefix-based searching
- Many strings with common prefixes

❌ **Don't Use When**:
- Storing numbers or non-string data
- No prefix relationships
- Memory is very limited (can be space-intensive)

**Real-World Usage**: Google search autocomplete, IDE code completion

---

## Interview Questions

### Easy Level

**1. Implement inorder, preorder, and postorder traversals**
```java
// Already covered in Tree Traversals section
```

**2. Find the height of a binary tree**
```java
public int height(TreeNode root) {
    if (root == null) {
        return -1;  // Or return 0, depending on definition
    }
    return 1 + Math.max(height(root.left), height(root.right));
}
```

**3. Count the number of nodes in a binary tree**
```java
public int countNodes(TreeNode root) {
    if (root == null) {
        return 0;
    }
    return 1 + countNodes(root.left) + countNodes(root.right);
}
```

**4. Check if two trees are identical**
```java
public boolean isIdentical(TreeNode root1, TreeNode root2) {
    if (root1 == null && root2 == null) {
        return true;
    }
    
    if (root1 == null || root2 == null) {
        return false;
    }
    
    return (root1.data == root2.data) &&
           isIdentical(root1.left, root2.left) &&
           isIdentical(root1.right, root2.right);
}
```

**5. Find maximum element in a binary tree**
```java
public int findMax(TreeNode root) {
    if (root == null) {
        return Integer.MIN_VALUE;
    }
    
    int max = root.data;
    int leftMax = findMax(root.left);
    int rightMax = findMax(root.right);
    
    return Math.max(max, Math.max(leftMax, rightMax));
}
```

---

### Medium Level

**6. Lowest Common Ancestor (LCA) in BST**
```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null) {
        return null;
    }
    
    // If both nodes are smaller, LCA is in left subtree
    if (p.data < root.data && q.data < root.data) {
        return lowestCommonAncestor(root.left, p, q);
    }
    
    // If both nodes are larger, LCA is in right subtree
    if (p.data > root.data && q.data > root.data) {
        return lowestCommonAncestor(root.right, p, q);
    }
    
    // If one is on left and other on right, root is LCA
    return root;
}
```

**7. Check if a binary tree is balanced**
```java
public boolean isBalanced(TreeNode root) {
    return checkHeight(root) != -1;
}

private int checkHeight(TreeNode root) {
    if (root == null) {
        return 0;
    }
    
    int leftHeight = checkHeight(root.left);
    if (leftHeight == -1) return -1;
    
    int rightHeight = checkHeight(root.right);
    if (rightHeight == -1) return -1;
    
    if (Math.abs(leftHeight - rightHeight) > 1) {
        return -1;  // Not balanced
    }
    
    return 1 + Math.max(leftHeight, rightHeight);
}
```

**8. Convert sorted array to balanced BST**
```java
public TreeNode sortedArrayToBST(int[] nums) {
    return sortedArrayToBST(nums, 0, nums.length - 1);
}

private TreeNode sortedArrayToBST(int[] nums, int left, int right) {
    if (left > right) {
        return null;
    }
    
    int mid = left + (right - left) / 2;
    TreeNode root = new TreeNode(nums[mid]);
    
    root.left = sortedArrayToBST(nums, left, mid - 1);
    root.right = sortedArrayToBST(nums, mid + 1, right);
    
    return root;
}
```

**9. Diameter of binary tree (longest path between any two nodes)**
```java
private int diameter = 0;

public int diameterOfBinaryTree(TreeNode root) {
    height(root);
    return diameter;
}

private int height(TreeNode root) {
    if (root == null) {
        return 0;
    }
    
    int leftHeight = height(root.left);
    int rightHeight = height(root.right);
    
    // Update diameter if path through current node is longer
    diameter = Math.max(diameter, leftHeight + rightHeight);
    
    return 1 + Math.max(leftHeight, rightHeight);
}
```

**10. Zigzag level order traversal**
```java
public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    boolean leftToRight = true;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        List<Integer> level = new ArrayList<>();
        
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            
            if (leftToRight) {
                level.add(node.data);
            } else {
                level.add(0, node.data);  // Add at beginning
            }
            
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        
        result.add(level);
        leftToRight = !leftToRight;
    }
    
    return result;
}
```

---

### Hard Level

**11. Serialize and deserialize a binary tree**
```java
public class Codec {
    // Serialize tree to string
    public String serialize(TreeNode root) {
        if (root == null) {
            return "null";
        }
        
        return root.data + "," + 
               serialize(root.left) + "," + 
               serialize(root.right);
    }
    
    // Deserialize string to tree
    public TreeNode deserialize(String data) {
        Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));
        return deserializeHelper(queue);
    }
    
    private TreeNode deserializeHelper(Queue<String> queue) {
        String val = queue.poll();
        
        if (val.equals("null")) {
            return null;
        }
        
        TreeNode root = new TreeNode(Integer.parseInt(val));
        root.left = deserializeHelper(queue);
        root.right = deserializeHelper(queue);
        
        return root;
    }
}
```

**12. Binary tree maximum path sum**
```java
private int maxSum = Integer.MIN_VALUE;

public int maxPathSum(TreeNode root) {
    maxPathSumHelper(root);
    return maxSum;
}

private int maxPathSumHelper(TreeNode root) {
    if (root == null) {
        return 0;
    }
    
    // Get max path sum from left and right (ignore negative paths)
    int left = Math.max(0, maxPathSumHelper(root.left));
    int right = Math.max(0, maxPathSumHelper(root.right));
    
    // Update global max (path through current node)
    maxSum = Math.max(maxSum, left + right + root.data);
    
    // Return max path sum including current node
    return root.data + Math.max(left, right);
}
```

**13. Recover BST where two nodes are swapped**
```java
TreeNode first = null, second = null, prev = null;

public void recoverTree(TreeNode root) {
    // Find the two swapped nodes using inorder traversal
    inorder(root);
    
    // Swap the values back
    int temp = first.data;
    first.data = second.data;
    second.data = temp;
}

private void inorder(TreeNode root) {
    if (root == null) return;
    
    inorder(root.left);
    
    // Find violations in sorted order
    if (prev != null && prev.data > root.data) {
        if (first == null) {
            first = prev;
        }
        second = root;
    }
    
    prev = root;
    inorder(root.right);
}
```

**14. Vertical order traversal**
```java
public List<List<Integer>> verticalOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
    Map<Integer, List<Integer>> map = new TreeMap<>();
    Queue<TreeNode> nodeQueue = new LinkedList<>();
    Queue<Integer> colQueue = new LinkedList<>();
    
    nodeQueue.add(root);
    colQueue.add(0);
    
    while (!nodeQueue.isEmpty()) {
        TreeNode node = nodeQueue.poll();
        int col = colQueue.poll();
        
        map.putIfAbsent(col, new ArrayList<>());
        map.get(col).add(node.data);
        
        if (node.left != null) {
            nodeQueue.add(node.left);
            colQueue.add(col - 1);
        }
        
        if (node.right != null) {
            nodeQueue.add(node.right);
            colQueue.add(col + 1);
        }
    }
    
    result.addAll(map.values());
    return result;
}
```

**15. Count complete tree nodes (optimized)**
```java
public int countNodes(TreeNode root) {
    if (root == null) return 0;
    
    int leftHeight = getLeftHeight(root);
    int rightHeight = getRightHeight(root);
    
    // If left and right heights are equal, it's a perfect binary tree
    if (leftHeight == rightHeight) {
        return (1 << leftHeight) - 1;  // 2^h - 1
    }
    
    // Otherwise, recursively count
    return 1 + countNodes(root.left) + countNodes(root.right);
}

private int getLeftHeight(TreeNode root) {
    int height = 0;
    while (root != null) {
        height++;
        root = root.left;
    }
    return height;
}

private int getRightHeight(TreeNode root) {
    int height = 0;
    while (root != null) {
        height++;
        root = root.right;
    }
    return height;
}
```

---

### Common Interview Topics

1. **Tree Construction**
   - Build tree from inorder + preorder
   - Build tree from inorder + postorder
   - Convert sorted list to BST

2. **Tree Properties**
   - Check if valid BST
   - Check if balanced
   - Check if symmetric/mirror
   - Find depth/height

3. **Tree Traversal Variations**
   - Morris traversal (O(1) space)
   - Boundary traversal
   - Diagonal traversal
   - Right/Left view of tree

4. **Path Problems**
   - Root to leaf paths
   - Path sum
   - Maximum path sum
   - LCA (Lowest Common Ancestor)

5. **Tree Modification**
   - Flatten tree to linked list
   - Connect nodes at same level
   - Invert/mirror tree
   - Delete nodes

---

## Summary

### Key Takeaways

1. **Trees are hierarchical**: One root, multiple levels, parent-child relationships
2. **Different trees for different needs**: 
   - BST for sorted data
   - AVL for search-heavy operations
   - Red-Black for balanced operations
   - B-Tree for disk storage
   - Trie for prefix searches

3. **Traversals matter**: Choose based on use case
   - Inorder → sorted output (BST)
   - Preorder → copy tree
   - Postorder → delete tree
   - Level-order → shortest path

4. **Time complexity**: Most balanced trees guarantee O(log n) operations

5. **Space complexity**: Recursive calls use O(h) stack space, where h = height

### Practice Recommendations

1. Master basic tree operations first (insert, search, delete, traverse)
2. Understand recursion deeply - trees are naturally recursive
3. Practice both recursive and iterative approaches
4. Learn to visualize trees while solving problems
5. Focus on BST properties and balanced tree rotations
6. Solve problems on platforms like LeetCode, HackerRank

---

**Next Steps**: Practice implementing each tree type from scratch, then solve interview problems to solidify your understanding!
