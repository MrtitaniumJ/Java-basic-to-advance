# B-Tree

## Overview
A B-Tree is a self-balancing tree data structure that maintains sorted data and allows searches, sequential access, insertions, and deletions in logarithmic time. It's optimized for systems that read and write large blocks of data and is the foundation of modern database indexing.

## What is a B-Tree?
A B-Tree of order m is a tree where:
- Every node has at most m children
- Every node (except root) has at least ⌈m/2⌉ children
- Root has at least 2 children (unless it's a leaf)
- All leaves are at the same level
- Each non-leaf node has k keys where k = number_of_children - 1
- Keys are stored in sorted order

## Internal Structure & Properties

### B-Tree Structure Diagram (Order 3)
```
          [15, 30]
         /    |    \
    [5, 10] [20] [35, 40, 50]
    
Keys per node: 1-2 (for order 3)
Max children: 3
Min children: 2 (except root)
```

### Key Properties:
1. **Balanced**: All leaves at same depth
2. **Ordered**: Keys in sorted order within nodes
3. **Minimal height**: log(n) height for n keys
4. **Predictable I/O**: Fixed branching factor
5. **Key capacity**: Between m/2 and m-1 keys per node

## Operations

### Search Operation:
1. Start from root, compare key with node keys
2. Navigate to appropriate child
3. Repeat until key found or leaf reached
4. Time: O(log n)

### Insertion Process:
1. Find leaf node where key belongs
2. Insert key in sorted order
3. If node has m keys (overflow), split:
   - Move median to parent
   - Split remaining keys into two nodes
4. Propagate splits up if needed

### Deletion Process:
1. Find key in tree
2. If in leaf: remove directly
3. If in internal node: replace with successor/predecessor or merge
4. Handle underflow cases (too few keys)
5. Merge/redistribute as needed

## Performance Analysis

| Operation | Time Complexity | Space Complexity |
|-----------|-----------------|------------------|
| Search    | O(log n)        | O(1)             |
| Insert    | O(log n)        | O(log n)         |
| Delete    | O(log n)        | O(log n)         |
| Range Scan| O(k + log n)    | O(log n)         |

Where k = number of elements in range

## Use Cases
- **Database indexing** (B+ Trees in most databases)
- **File systems** (NTFS, ext4, HFS+ use B-Trees)
- **Search engines** indexing
- **Time-series databases**
- **Multi-level storage** systems
- **Range queries** optimization

## Real-World Applications
- **MySQL, PostgreSQL, SQLite** use B-Trees/B+ variants
- **NTFS, HFS+, ext4** file systems
- **MongoDB** database indexing
- **Elasticsearch** lucene indices
- **Google BigTable**, **Bigtable** databases
- **Key-value stores** like LevelDB

---

## Java Implementation

```java
// B-Tree Node class
class BNode {
    int t; // Minimum degree
    int[] keys;
    BNode[] children;
    int keyCount;
    boolean isLeaf;
    
    BNode(int t, boolean isLeaf) {
        this.t = t;
        this.isLeaf = isLeaf;
        this.keys = new int[2 * t - 1];
        this.children = new BNode[2 * t];
        this.keyCount = 0;
    }
}

// B-Tree Implementation
public class BTree {
    private BNode root;
    private int t; // Minimum degree
    
    public BTree(int t) {
        this.t = t;
        this.root = new BNode(t, true);
    }
    
    // Search operation
    public boolean search(int key) {
        return searchHelper(root, key);
    }
    
    private boolean searchHelper(BNode node, int key) {
        int i = 0;
        
        // Find first key greater than or equal to key
        while (i < node.keyCount && key > node.keys[i]) {
            i++;
        }
        
        // Check if key is found
        if (i < node.keyCount && key == node.keys[i]) {
            return true;
        }
        
        // If leaf node, key not in tree
        if (node.isLeaf) {
            return false;
        }
        
        // Go to appropriate child
        return searchHelper(node.children[i], key);
    }
    
    // Insert operation
    public void insert(int key) {
        if (root.keyCount == 2 * t - 1) {
            // Root is full, create new root
            BNode newRoot = new BNode(t, false);
            newRoot.children[0] = root;
            splitChild(newRoot, 0);
            root = newRoot;
        }
        insertNonFull(root, key);
    }
    
    private void insertNonFull(BNode node, int key) {
        int i = node.keyCount - 1;
        
        if (node.isLeaf) {
            // Insert key in leaf node
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.keyCount++;
        } else {
            // Find child where key should go
            while (i >= 0 && key < node.keys[i]) {
                i--;
            }
            i++;
            
            // Check if child is full
            if (node.children[i].keyCount == 2 * t - 1) {
                splitChild(node, i);
                if (key > node.keys[i]) {
                    i++;
                }
            }
            insertNonFull(node.children[i], key);
        }
    }
    
    // Split child at index idx
    private void splitChild(BNode parent, int idx) {
        BNode fullChild = parent.children[idx];
        BNode newChild = new BNode(t, fullChild.isLeaf);
        
        // Copy second half of keys
        int mid = t - 1;
        newChild.keyCount = t - 1;
        
        for (int i = 0; i < t - 1; i++) {
            newChild.keys[i] = fullChild.keys[i + t];
        }
        
        // Copy children if not leaf
        if (!fullChild.isLeaf) {
            for (int i = 0; i < t; i++) {
                newChild.children[i] = fullChild.children[i + t];
            }
        }
        
        fullChild.keyCount = t - 1;
        
        // Move middle key to parent
        for (int i = parent.keyCount; i > idx; i--) {
            parent.children[i + 1] = parent.children[i];
        }
        parent.children[idx + 1] = newChild;
        
        for (int i = parent.keyCount - 1; i >= idx; i--) {
            parent.keys[i + 1] = parent.keys[i];
        }
        parent.keys[idx] = fullChild.keys[t - 1];
        parent.keyCount++;
    }
    
    // Delete operation
    public void delete(int key) {
        deleteHelper(root, key);
        
        // If root is empty, make its only child as new root
        if (root.keyCount == 0) {
            if (!root.isLeaf && root.children[0] != null) {
                root = root.children[0];
            }
        }
    }
    
    private void deleteHelper(BNode node, int key) {
        int i = 0;
        
        // Find index of key
        while (i < node.keyCount && key > node.keys[i]) {
            i++;
        }
        
        if (i < node.keyCount && key == node.keys[i]) {
            // Key found in this node
            if (node.isLeaf) {
                deleteFromLeaf(node, i);
            } else {
                deleteFromNonLeaf(node, i);
            }
        } else if (!node.isLeaf) {
            // Key is in subtree
            boolean isInSubtree = (i == node.keyCount);
            
            if (node.children[i].keyCount < t) {
                fill(node, i);
            }
            
            if (isInSubtree && i > node.keyCount) {
                deleteHelper(node.children[i - 1], key);
            } else {
                deleteHelper(node.children[i], key);
            }
        }
    }
    
    private void deleteFromLeaf(BNode node, int idx) {
        for (int i = idx; i < node.keyCount - 1; i++) {
            node.keys[i] = node.keys[i + 1];
        }
        node.keyCount--;
    }
    
    private void deleteFromNonLeaf(BNode node, int idx) {
        int key = node.keys[idx];
        
        if (node.children[idx].keyCount >= t) {
            // Get predecessor
            int predecessor = getPredecessor(node, idx);
            node.keys[idx] = predecessor;
            deleteHelper(node.children[idx], predecessor);
        } else if (node.children[idx + 1].keyCount >= t) {
            // Get successor
            int successor = getSuccessor(node, idx);
            node.keys[idx] = successor;
            deleteHelper(node.children[idx + 1], successor);
        } else {
            // Merge with sibling
            merge(node, idx);
            deleteHelper(node.children[idx], key);
        }
    }
    
    private int getPredecessor(BNode node, int idx) {
        BNode current = node.children[idx];
        while (!current.isLeaf) {
            current = current.children[current.keyCount];
        }
        return current.keys[current.keyCount - 1];
    }
    
    private int getSuccessor(BNode node, int idx) {
        BNode current = node.children[idx + 1];
        while (!current.isLeaf) {
            current = current.children[0];
        }
        return current.keys[0];
    }
    
    private void fill(BNode node, int idx) {
        // If previous sibling has at least t keys
        if (idx != 0 && node.children[idx - 1].keyCount >= t) {
            borrowFromPrev(node, idx);
        }
        // If next sibling has at least t keys
        else if (idx != node.keyCount && 
                 node.children[idx + 1].keyCount >= t) {
            borrowFromNext(node, idx);
        }
        // Merge with sibling
        else {
            if (idx != node.keyCount) {
                merge(node, idx);
            } else {
                merge(node, idx - 1);
            }
        }
    }
    
    private void borrowFromPrev(BNode node, int childIdx) {
        BNode child = node.children[childIdx];
        BNode sibling = node.children[childIdx - 1];
        
        // Move key from parent to child
        for (int i = child.keyCount - 1; i >= 0; i--) {
            child.keys[i + 1] = child.keys[i];
        }
        
        child.keys[0] = node.keys[childIdx - 1];
        
        // Move key from sibling to parent
        node.keys[childIdx - 1] = sibling.keys[sibling.keyCount - 1];
        
        // Move child pointer if not leaf
        if (!child.isLeaf) {
            for (int i = child.keyCount; i >= 0; i--) {
                child.children[i + 1] = child.children[i];
            }
            child.children[0] = 
                sibling.children[sibling.keyCount];
        }
        
        child.keyCount++;
        sibling.keyCount--;
    }
    
    private void borrowFromNext(BNode node, int childIdx) {
        BNode child = node.children[childIdx];
        BNode sibling = node.children[childIdx + 1];
        
        // Move key from parent to child
        child.keys[child.keyCount] = node.keys[childIdx];
        
        // Move key from sibling to parent
        node.keys[childIdx] = sibling.keys[0];
        
        // Move child pointer if not leaf
        if (!child.isLeaf) {
            child.children[child.keyCount + 1] = 
                sibling.children[0];
        }
        
        // Move all keys in sibling one step behind
        for (int i = 0; i < sibling.keyCount - 1; i++) {
            sibling.keys[i] = sibling.keys[i + 1];
        }
        
        // Move child pointers
        if (!sibling.isLeaf) {
            for (int i = 0; i < sibling.keyCount; i++) {
                sibling.children[i] = sibling.children[i + 1];
            }
        }
        
        child.keyCount++;
        sibling.keyCount--;
    }
    
    private void merge(BNode node, int idx) {
        BNode child = node.children[idx];
        BNode sibling = node.children[idx + 1];
        
        // Move key from current node to child
        child.keys[t - 1] = node.keys[idx];
        
        // Copy keys from sibling to child
        for (int i = 0; i < sibling.keyCount; i++) {
            child.keys[i + t] = sibling.keys[i];
        }
        
        // Copy children if not leaf
        if (!child.isLeaf) {
            for (int i = 0; i <= sibling.keyCount; i++) {
                child.children[i + t] = sibling.children[i];
            }
        }
        
        // Move keys in current node
        for (int i = idx; i < node.keyCount - 1; i++) {
            node.keys[i] = node.keys[i + 1];
        }
        
        // Move children in current node
        for (int i = idx + 1; i < node.keyCount; i++) {
            node.children[i] = node.children[i + 1];
        }
        
        child.keyCount += sibling.keyCount + 1;
        node.keyCount--;
    }
    
    // Traverse and print
    public void traverse() {
        traverseHelper(root);
        System.out.println();
    }
    
    private void traverseHelper(BNode node) {
        int i;
        for (i = 0; i < node.keyCount; i++) {
            if (!node.isLeaf) {
                traverseHelper(node.children[i]);
            }
            System.out.print(node.keys[i] + " ");
        }
        if (!node.isLeaf) {
            traverseHelper(node.children[i]);
        }
    }
}

// Test class
public class BTreeTest {
    public static void main(String[] args) {
        BTree btree = new BTree(3); // Order 3
        
        // Insert values
        int[] values = {10, 20, 5, 6, 12, 30, 7, 17};
        for (int val : values) {
            btree.insert(val);
        }
        
        System.out.println("B-Tree traversal (sorted):");
        btree.traverse();
        
        System.out.println("\nSearch operations:");
        System.out.println("Search 6: " + btree.search(6));
        System.out.println("Search 15: " + btree.search(15));
        System.out.println("Search 30: " + btree.search(30));
        
        // Delete operations
        System.out.println("\nDeleting key 6:");
        btree.delete(6);
        btree.traverse();
        
        System.out.println("\nDeleting key 13 (not exists):");
        btree.delete(13);
        btree.traverse();
        
        System.out.println("\nInserting more values: 25, 35");
        btree.insert(25);
        btree.insert(35);
        btree.traverse();
    }
}
```

## When to Use B-Trees
✓ When working with external storage (disk-based data)  
✓ For database indexing and file systems  
✓ When you need range queries efficiently  
✓ When minimizing I/O operations is critical  
✓ For sorted data with frequent insertions/deletions  

## Advantages
- Excellent for disk-based storage
- Maintains sorted order naturally
- Efficient range queries
- Balances automatically
- Minimizes tree height
- Few cache misses in practice

## Disadvantages
- Complex implementation
- Requires minimum degree parameter tuning
- More overhead than simple BSTs
- Memory fragmentation on disk
- Not ideal for in-memory-only applications
