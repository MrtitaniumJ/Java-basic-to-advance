# Red-Black Tree

## Overview
A Red-Black Tree is a self-balancing binary search tree where each node has an associated color (red or black). It maintains balance through a set of color properties, ensuring that the tree remains roughly balanced during insertions and deletions, providing guaranteed O(log n) operations.

## What is a Red-Black Tree?
A Red-Black Tree is an extension of a Binary Search Tree with the following color properties:
- Every node is either RED or BLACK
- The root is always BLACK
- All leaf nodes (NIL) are BLACK
- If a node is RED, both its children must be BLACK
- Every path from a node to its descendant NIL nodes has the same number of BLACK nodes

## Internal Structure & Properties

### Tree Structure Diagram
```
        10(B)              
       /     \            
      5(R)   15(B)        
     / \     /  \        
   3(B) 7(B) 12(R) 20(B) 

Legend: (B) = Black, (R) = Red
```

### Key Properties:
1. **Binary Search Property**: Left < Parent < Right
2. **Red Property**: No two consecutive RED nodes on any path
3. **Black Height Property**: All paths have same BLACK node count
4. **Maximum Height**: At most 2 × log(n+1) nodes

## Operations

### Search Operation
Same as BST - O(log n)

### Insertion Process:
1. Insert as regular BST node (colored RED)
2. If parent is BLACK, done
3. If parent is RED, perform rotations and recoloring based on uncle's color:
   - **Uncle is RED**: Recolor parent, uncle, and grandparent
   - **Uncle is BLACK**: Perform rotation and recolor

### Deletion Process:
1. Delete as regular BST node
2. Fix violations using sibling node analysis:
   - Check sibling color and its children
   - Perform rotations and recoloring accordingly

## Performance Analysis

| Operation | Time Complexity | Space Complexity |
|-----------|-----------------|------------------|
| Search    | O(log n)        | O(1)             |
| Insert    | O(log n)        | O(log n)         |
| Delete    | O(log n)        | O(log n)         |
| Traverse  | O(n)            | O(log n)         |

## Use Cases
- **TreeMap/TreeSet** in Java Collections Framework
- **Database indexing** systems
- **File systems** (NTFS, ext4, HFS+)
- **Process scheduler** in operating systems
- **Range queries** and sorting

## Real-World Applications
- Linux kernel CFS scheduler uses Red-Black Trees
- Java's TreeMap implementation
- C++ STL std::map and std::set
- PostgreSQL database indices

---

## Java Implementation

```java
// Node class for Red-Black Tree
class RBNode {
    int data;
    RBNode left;
    RBNode right;
    RBNode parent;
    Color color;
    
    enum Color { RED, BLACK }
    
    RBNode(int data) {
        this.data = data;
        this.color = Color.RED;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

// Red-Black Tree Implementation
public class RedBlackTree {
    private RBNode root;
    
    public RedBlackTree() {
        root = null;
    }
    
    // Insert operation
    public void insert(int data) {
        if (root == null) {
            root = new RBNode(data);
            root.color = RBNode.Color.BLACK;
            return;
        }
        
        RBNode newNode = insertBST(root, data);
        if (newNode != null) {
            fixInsert(newNode);
        }
    }
    
    private RBNode insertBST(RBNode node, int data) {
        if (data == node.data) {
            return null; // Duplicate
        }
        
        if (data < node.data) {
            if (node.left == null) {
                node.left = new RBNode(data);
                node.left.parent = node;
                return node.left;
            }
            return insertBST(node.left, data);
        } else {
            if (node.right == null) {
                node.right = new RBNode(data);
                node.right.parent = node;
                return node.right;
            }
            return insertBST(node.right, data);
        }
    }
    
    // Fix Red-Black Tree properties after insertion
    private void fixInsert(RBNode node) {
        while (node.parent != null && 
               node.parent.color == RBNode.Color.RED) {
            
            if (node.parent == node.parent.parent.left) {
                RBNode uncle = node.parent.parent.right;
                
                if (uncle != null && 
                    uncle.color == RBNode.Color.RED) {
                    // Case 1: Uncle is RED - Recolor
                    node.parent.color = RBNode.Color.BLACK;
                    uncle.color = RBNode.Color.BLACK;
                    node.parent.parent.color = RBNode.Color.RED;
                    node = node.parent.parent;
                } else {
                    // Case 2 & 3: Uncle is BLACK - Rotate
                    if (node == node.parent.right) {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    node.parent.color = RBNode.Color.BLACK;
                    node.parent.parent.color = RBNode.Color.RED;
                    rotateRight(node.parent.parent);
                }
            } else {
                RBNode uncle = node.parent.parent.left;
                
                if (uncle != null && 
                    uncle.color == RBNode.Color.RED) {
                    node.parent.color = RBNode.Color.BLACK;
                    uncle.color = RBNode.Color.BLACK;
                    node.parent.parent.color = RBNode.Color.RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.color = RBNode.Color.BLACK;
                    node.parent.parent.color = RBNode.Color.RED;
                    rotateLeft(node.parent.parent);
                }
            }
        }
        root.color = RBNode.Color.BLACK;
    }
    
    // Left rotation
    private void rotateLeft(RBNode node) {
        RBNode rightChild = node.right;
        node.right = rightChild.left;
        
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }
        
        rightChild.parent = node.parent;
        
        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }
        
        rightChild.left = node;
        node.parent = rightChild;
    }
    
    // Right rotation
    private void rotateRight(RBNode node) {
        RBNode leftChild = node.left;
        node.left = leftChild.right;
        
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }
        
        leftChild.parent = node.parent;
        
        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.right) {
            node.parent.right = leftChild;
        } else {
            node.parent.left = leftChild;
        }
        
        leftChild.right = node;
        node.parent = leftChild;
    }
    
    // Search operation
    public boolean search(int data) {
        return searchHelper(root, data);
    }
    
    private boolean searchHelper(RBNode node, int data) {
        if (node == null) {
            return false;
        }
        
        if (data == node.data) {
            return true;
        } else if (data < node.data) {
            return searchHelper(node.left, data);
        } else {
            return searchHelper(node.right, data);
        }
    }
    
    // Inorder traversal
    public void inorder() {
        inorderHelper(root);
        System.out.println();
    }
    
    private void inorderHelper(RBNode node) {
        if (node == null) return;
        
        inorderHelper(node.left);
        System.out.print(node.data + "(" + 
            node.color.toString().charAt(0) + ") ");
        inorderHelper(node.right);
    }
    
    // Get tree height
    public int height() {
        return heightHelper(root);
    }
    
    private int heightHelper(RBNode node) {
        if (node == null) return 0;
        return 1 + Math.max(heightHelper(node.left), 
                            heightHelper(node.right));
    }
    
    // Check if tree is valid Red-Black Tree
    public boolean isValidRBTree() {
        if (root != null && root.color != RBNode.Color.BLACK) {
            return false;
        }
        return checkBlackHeight(root) != -1;
    }
    
    private int checkBlackHeight(RBNode node) {
        if (node == null) return 1;
        
        if (node.color == RBNode.Color.RED) {
            if ((node.left != null && 
                 node.left.color == RBNode.Color.RED) ||
                (node.right != null && 
                 node.right.color == RBNode.Color.RED)) {
                return -1;
            }
        }
        
        int leftHeight = checkBlackHeight(node.left);
        if (leftHeight == -1) return -1;
        
        int rightHeight = checkBlackHeight(node.right);
        if (rightHeight == -1) return -1;
        
        if (leftHeight != rightHeight) return -1;
        
        int blackHeight = leftHeight;
        if (node.color == RBNode.Color.BLACK) {
            blackHeight++;
        }
        
        return blackHeight;
    }
}

// Test class
public class RedBlackTreeTest {
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        
        // Insert values
        int[] values = {7, 3, 18, 10, 22, 8, 11, 26, 2, 6, 13};
        for (int val : values) {
            tree.insert(val);
        }
        
        System.out.println("Inorder traversal:");
        tree.inorder();
        
        System.out.println("\nTree height: " + tree.height());
        System.out.println("Is valid RB Tree: " + 
            tree.isValidRBTree());
        
        System.out.println("\nSearch operations:");
        System.out.println("Search 10: " + tree.search(10));
        System.out.println("Search 5: " + tree.search(5));
        System.out.println("Search 26: " + tree.search(26));
        
        // Insert more values
        System.out.println("\nInserting additional values: 15, 4, 20");
        tree.insert(15);
        tree.insert(4);
        tree.insert(20);
        
        System.out.println("After insertion:");
        tree.inorder();
        System.out.println("\nTree height: " + tree.height());
        System.out.println("Is valid RB Tree: " + 
            tree.isValidRBTree());
    }
}
```

## When to Use Red-Black Trees
✓ When you need guaranteed O(log n) operations  
✓ When self-balancing is critical  
✓ When implementing standard collections (TreeMap, TreeSet)  
✓ When building database indices  
✓ When consistent performance is more important than worst-case AVL optimization

## Advantages
- Guaranteed logarithmic time complexity
- Better than AVL trees for frequent insertions/deletions
- Used in Java Collections Framework
- Simpler rebalancing than AVL trees

## Disadvantages
- More complex than simple BSTs
- More memory overhead for color information
- Complex rotation and recoloring logic
- Slower than hash tables for average case lookups
