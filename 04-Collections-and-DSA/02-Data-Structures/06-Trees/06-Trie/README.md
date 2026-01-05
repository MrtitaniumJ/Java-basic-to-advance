# Trie (Prefix Tree)

## Overview
A Trie (pronounced "try") is a tree-like data structure used for efficient retrieval of keys stored as strings. Each node represents a character, and paths from root to nodes represent strings. It's optimal for prefix-based searches, autocomplete, and spell-checking applications.

## What is a Trie?
A Trie is a specialized tree where:
- Each node contains a reference to child nodes (one for each character)
- Each node can be marked as end-of-word
- Root node is typically empty (represents empty string)
- Keys (strings) are stored along paths from root to marked nodes
- No key is stored in a node; keys are paths
- Searching is by following character chain

## Internal Structure & Properties

### Trie Structure Diagram
```
        root(empty)
       /    |    \
      c     d     h
      |     |     |
      a     o     e
      |     |     |
      t     g     l
      |           |
      s           l
      
Words: cat, cats, dog, hell
```

### Key Properties:
1. **Prefix-based**: All nodes with common prefix share path
2. **Ordered**: Children ordered by character (A-Z)
3. **Memory**: O(alphabet_size × tree_length) per node
4. **No Collisions**: Unlike hash tables
5. **Prefix Matching**: Natural support for prefix queries

## Operations

### Search Operation:
1. Start from root
2. Follow path matching each character
3. If path completes and node marked as word, found
4. Time: O(m) where m is key length

### Insertion Process:
1. Start from root
2. For each character:
   - If child exists, move to child
   - If child doesn't exist, create new node
3. Mark final node as end-of-word
4. Time: O(m)

### Deletion Process:
1. Find the word path
2. Unmark end-of-word flag
3. Remove nodes with no other paths (backtrack)
4. Time: O(m)

### Autocomplete/Prefix Search:
1. Find node representing prefix
2. DFS from that node to collect all words
3. Time: O(n) where n is number of nodes

## Performance Analysis

| Operation | Time Complexity | Space Complexity |
|-----------|-----------------|------------------|
| Insert    | O(m)            | O(alphabet × m)  |
| Search    | O(m)            | O(1)             |
| Delete    | O(m)            | O(1)             |
| Prefix    | O(n)            | O(n)             |
| Autocomplete| O(n)          | O(n)             |

Where m = key length, n = tree nodes

## Use Cases
- **Autocomplete** (search engines, IDEs)
- **Spell checking** and suggestion
- **IP routing** (longest prefix matching)
- **Dictionary implementations**
- **Word games** (Scrabble, crossword)
- **Regex matching**
- **T9 predictive text**
- **Domain name servers (DNS)**

## Real-World Applications
- **Google Search** autocomplete
- **IDE/Code editors** (VS Code, IntelliJ autocomplete)
- **Mobile phones** T9 text input
- **Git** command completion
- **Database systems** prefix indices
- **Search engines** query suggestions
- **DNA sequence** matching
- **File systems** pathname storage

---

## Java Implementation

```java
// TrieNode class
class TrieNode {
    TrieNode[] children;
    boolean isEndOfWord;
    String word; // Store word for autocomplete
    
    TrieNode() {
        children = new TrieNode[26]; // 'a' to 'z'
        isEndOfWord = false;
        word = null;
    }
}

// Trie Implementation
public class Trie {
    private TrieNode root;
    
    public Trie() {
        root = new TrieNode();
    }
    
    // Insert a word
    public void insert(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
        
        TrieNode node = root;
        word = word.toLowerCase();
        
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        
        node.isEndOfWord = true;
        node.word = word;
    }
    
    // Search for exact word
    public boolean search(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        
        TrieNode node = findNode(word.toLowerCase());
        return node != null && node.isEndOfWord;
    }
    
    // Find node representing a word/prefix
    private TrieNode findNode(String prefix) {
        TrieNode node = root;
        
        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            
            if (node.children[idx] == null) {
                return null;
            }
            node = node.children[idx];
        }
        
        return node;
    }
    
    // Check if prefix exists
    public boolean startsWith(String prefix) {
        return findNode(prefix.toLowerCase()) != null;
    }
    
    // Delete a word
    public boolean delete(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        
        word = word.toLowerCase();
        
        if (!search(word)) {
            return false;
        }
        
        deleteHelper(root, word, 0);
        return true;
    }
    
    private boolean deleteHelper(TrieNode node, String word, 
                                 int idx) {
        if (idx == word.length()) {
            if (!node.isEndOfWord) {
                return false;
            }
            
            node.isEndOfWord = false;
            node.word = null;
            
            // Return true if node has no children
            return !hasChildren(node);
        }
        
        char c = word.charAt(idx);
        int charIdx = c - 'a';
        TrieNode child = node.children[charIdx];
        
        if (child == null) {
            return false;
        }
        
        boolean shouldDeleteChild = 
            deleteHelper(child, word, idx + 1);
        
        if (shouldDeleteChild) {
            node.children[charIdx] = null;
            
            // Delete current node if:
            // 1. It's not end of another word
            // 2. It has no other children
            return !node.isEndOfWord && !hasChildren(node);
        }
        
        return false;
    }
    
    private boolean hasChildren(TrieNode node) {
        for (TrieNode child : node.children) {
            if (child != null) {
                return true;
            }
        }
        return false;
    }
    
    // Autocomplete - get all words with given prefix
    public java.util.List<String> autocomplete(String prefix) {
        java.util.List<String> results = 
            new java.util.ArrayList<>();
        
        if (prefix == null) {
            return results;
        }
        
        prefix = prefix.toLowerCase();
        TrieNode node = findNode(prefix);
        
        if (node == null) {
            return results;
        }
        
        // DFS to find all words
        dfs(node, results);
        return results;
    }
    
    private void dfs(TrieNode node, 
                     java.util.List<String> results) {
        if (node == null) {
            return;
        }
        
        if (node.isEndOfWord && node.word != null) {
            results.add(node.word);
        }
        
        for (TrieNode child : node.children) {
            dfs(child, results);
        }
    }
    
    // Get all words in Trie
    public java.util.List<String> getAllWords() {
        java.util.List<String> words = 
            new java.util.ArrayList<>();
        dfs(root, words);
        return words;
    }
    
    // Count total words
    public int countWords() {
        return countWordsHelper(root);
    }
    
    private int countWordsHelper(TrieNode node) {
        if (node == null) {
            return 0;
        }
        
        int count = 0;
        if (node.isEndOfWord) {
            count = 1;
        }
        
        for (TrieNode child : node.children) {
            count += countWordsHelper(child);
        }
        
        return count;
    }
    
    // Find longest word with given prefix
    public String longestWord(String prefix) {
        prefix = prefix.toLowerCase();
        TrieNode node = findNode(prefix);
        
        if (node == null) {
            return null;
        }
        
        java.util.List<String> words = 
            new java.util.ArrayList<>();
        dfs(node, words);
        
        if (words.isEmpty()) {
            return null;
        }
        
        String longest = words.get(0);
        for (String word : words) {
            if (word.length() > longest.length()) {
                longest = word;
            }
        }
        
        return longest;
    }
    
    // Find words matching pattern (. matches any char)
    public java.util.List<String> searchPattern(String pattern) {
        java.util.List<String> results = 
            new java.util.ArrayList<>();
        searchPatternHelper(root, pattern, 0, results);
        return results;
    }
    
    private void searchPatternHelper(TrieNode node, 
                                     String pattern, 
                                     int idx,
                                     java.util.List<String> results) {
        if (idx == pattern.length()) {
            if (node != null && node.isEndOfWord && 
                node.word != null) {
                results.add(node.word);
            }
            return;
        }
        
        if (node == null) {
            return;
        }
        
        char c = pattern.charAt(idx);
        
        if (c == '.') {
            // Match any character
            for (TrieNode child : node.children) {
                if (child != null) {
                    searchPatternHelper(child, pattern, 
                                      idx + 1, results);
                }
            }
        } else {
            // Match specific character
            int charIdx = c - 'a';
            searchPatternHelper(node.children[charIdx], 
                              pattern, idx + 1, results);
        }
    }
    
    // Print tree structure
    public void printTrieStructure() {
        System.out.println("Trie Structure:");
        printHelper(root, "", 0);
    }
    
    private void printHelper(TrieNode node, String prefix, 
                           int depth) {
        if (node == null) {
            return;
        }
        
        if (node.isEndOfWord) {
            System.out.println("  ".repeat(depth) + 
                prefix + " (END)");
        }
        
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                char c = (char) ('a' + i);
                printHelper(node.children[i], 
                           prefix + c, depth + 1);
            }
        }
    }
    
    // Get size (number of nodes)
    public int getNodeCount() {
        return countNodes(root);
    }
    
    private int countNodes(TrieNode node) {
        if (node == null) {
            return 0;
        }
        
        int count = 1;
        for (TrieNode child : node.children) {
            count += countNodes(child);
        }
        
        return count;
    }
}

// Test class
public class TrieTest {
    public static void main(String[] args) {
        Trie trie = new Trie();
        
        // Insert words
        String[] words = {"apple", "app", "application", 
                         "apply", "apt", "api", "banana", 
                         "band", "ban", "cat", "car", "card"};
        
        for (String word : words) {
            trie.insert(word);
        }
        
        System.out.println("All words in Trie:");
        System.out.println(trie.getAllWords());
        System.out.println("Total words: " + 
            trie.countWords());
        
        System.out.println("\n--- Search Operations ---");
        System.out.println("Search 'apple': " + 
            trie.search("apple"));
        System.out.println("Search 'app': " + 
            trie.search("app"));
        System.out.println("Search 'appl': " + 
            trie.search("appl"));
        System.out.println("Search 'xyz': " + 
            trie.search("xyz"));
        
        System.out.println("\n--- Prefix Operations ---");
        System.out.println("Starts with 'app': " + 
            trie.startsWith("app"));
        System.out.println("Starts with 'ba': " + 
            trie.startsWith("ba"));
        System.out.println("Starts with 'xy': " + 
            trie.startsWith("xy"));
        
        System.out.println("\n--- Autocomplete ---");
        System.out.println("Autocomplete 'app': " + 
            trie.autocomplete("app"));
        System.out.println("Autocomplete 'ba': " + 
            trie.autocomplete("ba"));
        System.out.println("Autocomplete 'ca': " + 
            trie.autocomplete("ca"));
        
        System.out.println("\n--- Longest Word ---");
        System.out.println("Longest word with prefix 'app': " + 
            trie.longestWord("app"));
        System.out.println("Longest word with prefix 'ba': " + 
            trie.longestWord("ba"));
        
        System.out.println("\n--- Pattern Matching ---");
        System.out.println("Pattern 'a..': " + 
            trie.searchPattern("a.."));
        System.out.println("Pattern 'ban.': " + 
            trie.searchPattern("ban."));
        System.out.println("Pattern '...': " + 
            trie.searchPattern("..."));
        
        System.out.println("\n--- Delete Operations ---");
        System.out.println("Deleting 'app'...");
        trie.delete("app");
        System.out.println("Search 'app' after delete: " + 
            trie.search("app"));
        System.out.println("Autocomplete 'app' after delete: " + 
            trie.autocomplete("app"));
        
        System.out.println("\nNode count: " + 
            trie.getNodeCount());
        System.out.println("Total words: " + 
            trie.countWords());
        
        System.out.println("\n--- Trie Structure ---");
        trie.printTrieStructure();
    }
}
```

## When to Use Tries
✓ For autocomplete and prefix matching  
✓ When you need all words with specific prefix  
✓ For spell checking and suggestions  
✓ When word ordering by prefix is important  
✓ For IP routing with longest prefix match  
✓ When you need to store and search many strings  

## Advantages
- O(m) search time independent of dataset size
- Natural prefix matching capability
- No hash collisions
- Efficient autocomplete
- Can find all words with prefix quickly
- Memory efficient for large dictionaries

## Disadvantages
- Memory intensive for sparse dictionaries
- Slower than hash tables for exact match (constant factor)
- Requires character set knowledge
- Cache-unfriendly due to pointer chasing
- More complex than hash tables
- Poor performance with long infrequent prefixes

## Variants
- **Prefix Tree (regular Trie)**: String matching
- **Suffix Tree**: All suffixes of string
- **Radix Tree**: Compressed version (space-efficient)
- **Patricia Tree**: Optimized variant
- **Ternary Search Tree**: Hybrid of Trie and BST
