# Huffman Coding - Optimal Prefix-Free Code Generation

## Table of Contents
1. [Problem Statement](#problem-statement)
2. [Greedy Choice Property](#greedy-choice-property)
3. [Proof of Correctness](#proof-of-correctness)
4. [Algorithm Design](#algorithm-design)
5. [Java Implementation](#java-implementation)
6. [Complexity Analysis](#complexity-analysis)
7. [Examples and Test Cases](#examples-and-test-cases)
8. [Key Insights](#key-insights)

## Problem Statement

**Huffman Coding** is a lossless data compression algorithm that uses a greedy approach to construct an optimal binary prefix-free code. It assigns variable-length codes to characters based on their frequency of occurrence, with more frequent characters receiving shorter codes.

### Formal Definition

Given:
- A set of characters $C = \{c_1, c_2, ..., c_n\}$
- Frequency of each character $f(c_i)$ representing the count of occurrences
- Total message length $L = \sum_{i=1}^{n} f(c_i)$

Objective: Find a prefix-free binary code that minimizes the total encoding length:
$$\text{Minimize: } \sum_{i=1}^{n} f(c_i) \cdot d(c_i)$$

where $d(c_i)$ is the depth (code length) of character $c_i$ in the binary tree.

### Key Concepts

**Prefix-Free Code**: No code word is a prefix of another code word
- Example: {0, 10, 11} is prefix-free
- Example: {0, 01, 10} is NOT prefix-free (0 is prefix of 01)
- Advantage: Unambiguous decoding without delimiters

**Binary Code**: Each character is represented as a sequence of 0s and 1s
- Left traversal = 0, Right traversal = 1
- Represented as a binary tree structure

**Variable-Length Code**: Different characters have different code lengths
- Frequent characters: Shorter codes
- Rare characters: Longer codes

### Real-World Applications

- **File Compression**: DEFLATE, JPEG, MP3 use Huffman-based compression
- **Data Transmission**: Optimal encoding for communication systems
- **Memory Storage**: Reducing storage requirements for large datasets
- **Network Protocols**: Efficient data representation in limited bandwidth
- **Image Compression**: Compressing grayscale images
- **Text Compression**: Lossless compression of text files
- **Hierarchical Clustering**: Building optimal decision trees

## Greedy Choice Property

### The Greedy Strategy

The optimal prefix-free code tree can be constructed using the following greedy choice: **Always combine the two characters (or sub-trees) with the smallest frequencies to create a new node with combined frequency.**

### Intuition Behind the Strategy

1. **Minimize Total Encoding Cost**: Frequently used characters should be closer to the root (shorter paths), while rarely used characters can be deeper.

2. **Local Optimal Choice**: Merging the two smallest-frequency nodes ensures that these smaller frequencies contribute less to the overall encoding length.

3. **Bubble-Up Effect**: By repeatedly merging smallest frequencies, larger frequencies naturally bubble up toward the root.

4. **Optimal Substructure**: The Huffman tree for any subset of characters forms an optimal tree for that subset.

### Why This Works: Mathematical Perspective

For two characters with frequencies $f_1$ and $f_2$ where $f_1 \leq f_2$:
- Merging them creates a cost of: $f_1 + f_2$
- This cost is added to parent in the tree, increasing all ancestor codes by 1
- Putting low-frequency characters deeper adds less total cost

### Why Other Strategies Fail

- **Random Merging**: No guarantee of minimizing total code length
- **Alphabetical Order**: Ignores frequency information entirely
- **Highest Frequency First**: Places frequent items too deep (reverse of optimal)
- **Balanced Tree**: Equal depth for all characters ignores frequency differences

## Proof of Correctness

### Theorem
The Huffman algorithm produces an optimal prefix-free binary code that minimizes the total encoding length.

### Proof by Greedy Matroid Exchange

**Lemma 1 - Optimal Substructure Property:**
If $T$ is an optimal Huffman tree for set $S$, and $a, b$ are two nodes combined into parent $p$, then $T'$ (the tree with $a, b$ replaced by $p$) is optimal for set $S' = (S \setminus \{a, b\}) \cup \{p\}$.

**Proof:**
If $T'$ were not optimal for $S'$, we could construct a better tree $T'_{opt}$ for $S'$. Then we could reconstruct a tree for $S$ from $T'_{opt}$ that's better than $T$, contradicting the optimality of $T$.

**Lemma 2 - Greedy Choice Property:**
In an optimal tree, the two characters with minimum frequencies must be siblings at maximum depth.

**Proof by Contradiction:**
- Let $a$ and $b$ be characters with minimum frequencies
- Suppose in optimal tree $T$, they are not siblings
- Let $x$ and $y$ be two sibling nodes at maximum depth with frequencies $f_x \geq f_a$ and $f_y \geq f_b$
- Swap $a$ with $x$: New cost = Old cost $- (f_x - f_a) \cdot d(x)$
- This reduces total cost, contradicting optimality
- Therefore, $a$ and $b$ must be siblings

**Main Theorem:**
The Huffman algorithm produces an optimal code by:
1. Choosing the two minimum-frequency nodes (Lemma 2)
2. Solving optimally for the reduced problem (Lemma 1)
3. Repeating until one tree remains

## Algorithm Design

### High-Level Algorithm

```
HUFFMAN(Characters C with frequencies f):
    1. Create leaf nodes for each character with its frequency
    2. Initialize priority queue with all leaf nodes
    3. While priority queue has more than one node:
        a. Extract two minimum-frequency nodes (z, y)
        b. Create new internal node p with frequency f[z] + f[y]
        c. Set p.left = z, p.right = y
        d. Insert p back into priority queue
    4. Return root of final tree
    
ENCODE(text, root):
    1. Create code table by DFS traversal of tree
    2. For each character in text:
        Output its code from the table
    
DECODE(encoded_bits, root):
    1. Start at root
    2. For each bit in encoded stream:
        If 0, go left; if 1, go right
        If leaf reached, output character and return to root
    3. Continue until all bits consumed
```

### Algorithm Characteristics

- **Greedy Choice**: Merge minimum-frequency nodes
- **Optimal Substructure**: Optimal subtrees form larger optimal tree
- **Correctness**: Proven by greedy matroid property and exchange argument
- **Efficiency**: Uses priority queue for efficient minimum extraction

## Java Implementation

### Complete Implementation with Classes

```java
import java.util.*;

/**
 * Node in the Huffman tree
 */
class HuffmanNode implements Comparable<HuffmanNode> {
    private Character character;      // Character (null for internal nodes)
    private int frequency;            // Frequency of the character/subtree
    private HuffmanNode left;         // Left child (bit 0)
    private HuffmanNode right;        // Right child (bit 1)
    
    /**
     * Constructor for leaf node
     * @param character the character this node represents
     * @param frequency the frequency of this character
     */
    public HuffmanNode(Character character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }
    
    /**
     * Constructor for internal node
     * @param frequency combined frequency
     * @param left left subtree
     * @param right right subtree
     */
    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.character = null;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
    
    /**
     * Check if this is a leaf node
     * @return true if leaf, false if internal
     */
    public boolean isLeaf() {
        return character != null;
    }
    
    /**
     * Get the character
     * @return the character (null for internal nodes)
     */
    public Character getCharacter() {
        return character;
    }
    
    /**
     * Get the frequency
     * @return the frequency value
     */
    public int getFrequency() {
        return frequency;
    }
    
    /**
     * Get left child
     * @return left subtree root
     */
    public HuffmanNode getLeft() {
        return left;
    }
    
    /**
     * Get right child
     * @return right subtree root
     */
    public HuffmanNode getRight() {
        return right;
    }
    
    /**
     * Compare nodes by frequency for priority queue ordering
     * @param other the other node to compare
     * @return comparison result (min-heap property)
     */
    @Override
    public int compareTo(HuffmanNode other) {
        return Integer.compare(this.frequency, other.frequency);
    }
}

/**
 * Huffman encoder/decoder class
 */
class HuffmanCoder {
    private HuffmanNode root;
    private Map<Character, String> encodingTable;
    
    /**
     * Constructor initializes coding tables
     */
    public HuffmanCoder() {
        this.encodingTable = new HashMap<>();
    }
    
    /**
     * Builds the Huffman tree from character frequencies
     * Time Complexity: O(n log n) where n is number of unique characters
     * Space Complexity: O(n) for tree structure
     * 
     * @param frequencies map of characters to their frequencies
     */
    public void buildHuffmanTree(Map<Character, Integer> frequencies) {
        if (frequencies == null || frequencies.isEmpty()) {
            throw new IllegalArgumentException("Frequencies map cannot be empty");
        }
        
        // Special case: single character
        if (frequencies.size() == 1) {
            Character singleChar = frequencies.keySet().iterator().next();
            root = new HuffmanNode(singleChar, frequencies.get(singleChar));
            encodingTable.put(singleChar, "0");
            return;
        }
        
        // Create priority queue (min-heap) of leaf nodes
        PriorityQueue<HuffmanNode> minHeap = new PriorityQueue<>();
        
        // Step 1: Add all leaf nodes to priority queue
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            minHeap.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
        
        // Step 2: Build tree by merging minimum nodes
        while (minHeap.size() > 1) {
            // Extract two minimum frequency nodes
            HuffmanNode left = minHeap.poll();
            HuffmanNode right = minHeap.poll();
            
            // Create internal node with combined frequency
            int combinedFreq = left.getFrequency() + right.getFrequency();
            HuffmanNode parent = new HuffmanNode(combinedFreq, left, right);
            
            // Insert back into heap
            minHeap.offer(parent);
        }
        
        // Step 3: Root is the last remaining node
        root = minHeap.poll();
        
        // Step 4: Generate encoding table
        encodingTable.clear();
        generateEncodingTable(root, "");
    }
    
    /**
     * Generates encoding table through DFS traversal
     * @param node current node
     * @param code current code path
     */
    private void generateEncodingTable(HuffmanNode node, String code) {
        if (node == null) {
            return;
        }
        
        // Leaf node: store the code for this character
        if (node.isLeaf()) {
            // Special case: root is a leaf (single character)
            if (code.isEmpty()) {
                code = "0";
            }
            encodingTable.put(node.getCharacter(), code);
            return;
        }
        
        // Traverse left (0) and right (1)
        generateEncodingTable(node.getLeft(), code + "0");
        generateEncodingTable(node.getRight(), code + "1");
    }
    
    /**
     * Encodes a text string using the Huffman codes
     * Time Complexity: O(m) where m is the length of text
     * Space Complexity: O(k) where k is the output size
     * 
     * @param text the text to encode
     * @return encoded bit string
     */
    public String encode(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        
        StringBuilder encoded = new StringBuilder();
        
        for (char c : text.toCharArray()) {
            if (!encodingTable.containsKey(c)) {
                throw new IllegalArgumentException("Character '" + c + 
                    "' not in encoding table");
            }
            encoded.append(encodingTable.get(c));
        }
        
        return encoded.toString();
    }
    
    /**
     * Decodes a bit string using the Huffman tree
     * Time Complexity: O(k) where k is the length of encoded string
     * Space Complexity: O(m) where m is the output text length
     * 
     * @param encoded the encoded bit string
     * @return decoded text
     */
    public String decode(String encoded) {
        if (encoded == null || encoded.isEmpty()) {
            return "";
        }
        
        StringBuilder decoded = new StringBuilder();
        HuffmanNode current = root;
        
        // Special case: single character tree
        if (root.isLeaf()) {
            for (int i = 0; i < encoded.length(); i++) {
                decoded.append(root.getCharacter());
            }
            return decoded.toString();
        }
        
        // Traverse tree based on bits
        for (char bit : encoded.toCharArray()) {
            if (bit == '0') {
                current = current.getLeft();
            } else if (bit == '1') {
                current = current.getRight();
            } else {
                throw new IllegalArgumentException("Invalid bit: " + bit);
            }
            
            // Reached a leaf node
            if (current.isLeaf()) {
                decoded.append(current.getCharacter());
                current = root; // Return to root
            }
        }
        
        // Verify we ended at a leaf
        if (!current.isLeaf()) {
            throw new IllegalArgumentException("Incomplete code at end of input");
        }
        
        return decoded.toString();
    }
    
    /**
     * Gets the encoding table
     * @return map of characters to their Huffman codes
     */
    public Map<Character, String> getEncodingTable() {
        return new HashMap<>(encodingTable);
    }
    
    /**
     * Calculates compression statistics
     * @param originalText the original text
     * @param encodedLength the length of encoded bits
     * @return statistics map
     */
    public Map<String, Object> getCompressionStats(String originalText, int encodedLength) {
        Map<String, Object> stats = new LinkedHashMap<>();
        
        int originalBits = originalText.length() * 8; // 8 bits per character (ASCII)
        double compressionRatio = (1.0 - (double) encodedLength / originalBits) * 100;
        
        stats.put("Original Size (bits)", originalBits);
        stats.put("Encoded Size (bits)", encodedLength);
        stats.put("Compression Ratio", String.format("%.2f%%", compressionRatio));
        stats.put("Space Saved", originalBits - encodedLength + " bits");
        stats.put("Average Code Length", 
            String.format("%.4f", (double) encodedLength / originalText.length()));
        
        return stats;
    }
    
    /**
     * Prints the Huffman tree structure
     * @param node current node
     * @param indent indentation string
     */
    public void printTree(HuffmanNode node, String indent) {
        if (node == null) {
            return;
        }
        
        if (node.isLeaf()) {
            System.out.println(indent + "'" + node.getCharacter() + 
                "' (" + node.getFrequency() + ")");
        } else {
            System.out.println(indent + "Node (" + node.getFrequency() + ")");
            System.out.println(indent + "├─ Left:");
            printTree(node.getLeft(), indent + "│  ");
            System.out.println(indent + "└─ Right:");
            printTree(node.getRight(), indent + "   ");
        }
    }
}

/**
 * Demo and testing class for Huffman Coding
 */
public class HuffmanCodingDemo {
    
    /**
     * Example 1: Basic Huffman coding with simple text
     */
    public static void example1() {
        System.out.println("=== Example 1: Basic Huffman Coding ===\n");
        
        String text = "ABRACADABRA";
        System.out.println("Original Text: " + text);
        System.out.println("Length: " + text.length() + " characters\n");
        
        // Calculate frequencies
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        
        System.out.println("Character Frequencies:");
        frequencies.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .forEach(e -> System.out.println("  '" + e.getKey() + "': " + e.getValue()));
        System.out.println();
        
        // Build Huffman tree and encode
        HuffmanCoder coder = new HuffmanCoder();
        coder.buildHuffmanTree(frequencies);
        
        System.out.println("Huffman Codes:");
        coder.getEncodingTable().entrySet().stream()
            .sorted((a, b) -> a.getKey().compareTo(b.getKey()))
            .forEach(e -> System.out.println("  '" + e.getKey() + "': " + e.getValue()));
        System.out.println();
        
        String encoded = coder.encode(text);
        String decoded = coder.decode(encoded);
        
        System.out.println("Original:  " + text);
        System.out.println("Encoded:   " + encoded);
        System.out.println("Decoded:   " + decoded);
        System.out.println("Encoding Correct: " + text.equals(decoded) + "\n");
        
        // Print statistics
        Map<String, Object> stats = coder.getCompressionStats(text, encoded.length());
        System.out.println("Compression Statistics:");
        stats.forEach((key, value) -> System.out.println("  " + key + ": " + value));
        System.out.println();
    }
    
    /**
     * Example 2: Complex text with varied frequencies
     */
    public static void example2() {
        System.out.println("=== Example 2: Complex Text ===\n");
        
        String text = "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG";
        System.out.println("Original Text: " + text);
        System.out.println("Length: " + text.length() + " characters\n");
        
        // Calculate frequencies
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            if (c != ' ') { // Ignore spaces for this example
                frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
            }
        }
        
        // Build Huffman tree and encode
        HuffmanCoder coder = new HuffmanCoder();
        coder.buildHuffmanTree(frequencies);
        
        String encoded = coder.encode(text.replaceAll(" ", ""));
        String decoded = coder.decode(encoded);
        
        System.out.println("Encoding Table:");
        coder.getEncodingTable().entrySet().stream()
            .sorted((a, b) -> a.getKey().compareTo(b.getKey()))
            .limit(5)
            .forEach(e -> System.out.println("  '" + e.getKey() + "': " + e.getValue()));
        System.out.println("  ... and more\n");
        
        System.out.println("Encoded Length: " + encoded.length() + " bits");
        System.out.println("Decoding Correct: " + text.replaceAll(" ", "").equals(decoded) + "\n");
    }
    
    /**
     * Example 3: Edge cases
     */
    public static void example3() {
        System.out.println("=== Example 3: Edge Cases ===\n");
        
        // Single character
        System.out.println("Case 1: Single Character");
        Map<Character, Integer> freq1 = new HashMap<>();
        freq1.put('A', 5);
        
        HuffmanCoder coder1 = new HuffmanCoder();
        coder1.buildHuffmanTree(freq1);
        
        String text1 = "AAAAA";
        String encoded1 = coder1.encode(text1);
        String decoded1 = coder1.decode(encoded1);
        
        System.out.println("Text: " + text1);
        System.out.println("Encoded: " + encoded1);
        System.out.println("Decoded: " + decoded1);
        System.out.println("Correct: " + text1.equals(decoded1) + "\n");
        
        // Two characters with equal frequency
        System.out.println("Case 2: Equal Frequencies");
        Map<Character, Integer> freq2 = new HashMap<>();
        freq2.put('A', 5);
        freq2.put('B', 5);
        
        HuffmanCoder coder2 = new HuffmanCoder();
        coder2.buildHuffmanTree(freq2);
        
        String text2 = "AABABB";
        String encoded2 = coder2.encode(text2);
        String decoded2 = coder2.decode(encoded2);
        
        System.out.println("Text: " + text2);
        System.out.println("Encoded: " + encoded2);
        System.out.println("Decoded: " + decoded2);
        System.out.println("Correct: " + text2.equals(decoded2) + "\n");
    }
    
    /**
     * Example 4: Large text analysis
     */
    public static void example4() {
        System.out.println("=== Example 4: Large Text Analysis ===\n");
        
        // Simulate Shakespeare text
        String text = "TO BE OR NOT TO BE THAT IS THE QUESTION " +
                     "WHETHER TIS NOBLER IN THE MIND TO SUFFER " +
                     "THE SLINGS AND ARROWS OF OUTRAGEOUS FORTUNE";
        
        System.out.println("Text Length: " + text.length() + " characters");
        System.out.println("Unique Characters: " + 
            text.chars().distinct().count() + "\n");
        
        // Calculate frequencies
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        
        // Build tree and encode
        HuffmanCoder coder = new HuffmanCoder();
        coder.buildHuffmanTree(frequencies);
        
        String encoded = coder.encode(text);
        String decoded = coder.decode(encoded);
        
        System.out.println("Encoding Table Size: " + coder.getEncodingTable().size());
        System.out.println("Encoded String Length: " + encoded.length() + " bits");
        System.out.println("Decoding Correct: " + text.equals(decoded) + "\n");
        
        // Print statistics
        Map<String, Object> stats = coder.getCompressionStats(text, encoded.length());
        System.out.println("Statistics:");
        stats.forEach((key, value) -> System.out.println("  " + key + ": " + value));
        System.out.println();
    }
    
    /**
     * Example 5: Printing Huffman tree structure
     */
    public static void example5() {
        System.out.println("=== Example 5: Huffman Tree Structure ===\n");
        
        String text = "MISSISSIPPI";
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        
        HuffmanCoder coder = new HuffmanCoder();
        coder.buildHuffmanTree(frequencies);
        
        System.out.println("Text: " + text + "\n");
        System.out.println("Huffman Tree Structure:");
        // Using reflection to get root for printing
        try {
            java.lang.reflect.Field field = HuffmanCoder.class.getDeclaredField("root");
            field.setAccessible(true);
            HuffmanNode root = (HuffmanNode) field.get(coder);
            coder.printTree(root, "");
        } catch (Exception e) {
            System.out.println("(Tree structure visualization unavailable)");
        }
        
        System.out.println("\nEncoding Table:");
        coder.getEncodingTable().entrySet().stream()
            .sorted((a, b) -> a.getKey().compareTo(b.getKey()))
            .forEach(e -> System.out.println("  '" + e.getKey() + "': " + e.getValue()));
        System.out.println();
    }
    
    /**
     * Performance test with large alphabet
     */
    public static void performanceTest() {
        System.out.println("=== Performance Test ===\n");
        
        // Create text with all ASCII printable characters
        StringBuilder textBuilder = new StringBuilder();
        Random rand = new Random(42);
        
        for (int i = 0; i < 10000; i++) {
            textBuilder.append((char) (32 + rand.nextInt(95)));
        }
        
        String text = textBuilder.toString();
        
        // Calculate frequencies
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        
        // Time the Huffman coding process
        long startTime = System.nanoTime();
        
        HuffmanCoder coder = new HuffmanCoder();
        coder.buildHuffmanTree(frequencies);
        String encoded = coder.encode(text);
        String decoded = coder.decode(encoded);
        
        long endTime = System.nanoTime();
        
        System.out.println("Text Length: " + text.length() + " characters");
        System.out.println("Unique Characters: " + frequencies.size());
        System.out.println("Encoded Length: " + encoded.length() + " bits");
        System.out.println("Processing Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Decoding Correct: " + text.equals(decoded) + "\n");
        
        Map<String, Object> stats = coder.getCompressionStats(text, encoded.length());
        System.out.println("Compression Results:");
        stats.forEach((key, value) -> System.out.println("  " + key + ": " + value));
        System.out.println();
    }
    
    /**
     * Main method to run all examples
     */
    public static void main(String[] args) {
        example1();
        example2();
        example3();
        example4();
        example5();
        performanceTest();
    }
}
```

## Complexity Analysis

### Time Complexity

$$T(n) = O(n \log n)$$

**Breaking down the components:**

- **Building Frequency Table**: $O(m)$ where $m$ is text length
- **Creating Leaf Nodes**: $O(n)$ where $n$ is unique characters
- **Building Huffman Tree**: 
  - $n-1$ iterations (each merges two nodes)
  - Each iteration: $O(\log n)$ for heap operations (extract min, insert)
  - Total: $O(n \log n)$
- **Generating Encoding Table**: $O(n)$ - DFS traversal
- **Encoding Text**: $O(m)$ - one pass through text
- **Decoding Text**: $O(k)$ where $k$ is encoded length
- **Overall Dominant**: $O(n \log n)$ from tree building

### Space Complexity

$$S(n) = O(n)$$

**Space usage:**

- **Huffman Tree**: $O(n)$ - at most $2n-1$ nodes for $n$ characters
- **Priority Queue**: $O(n)$ - maximum size at construction
- **Encoding Table**: $O(n)$ - one entry per character
- **Recursion Stack**: $O(n)$ - maximum depth of tree
- **Overall**: $O(n)$

### Compression Efficiency

$$\text{Average Code Length} = \frac{\sum_{i=1}^{n} f_i \cdot d_i}{M}$$

where:
- $f_i$ = frequency of character $i$
- $d_i$ = depth of character $i$ in Huffman tree
- $M$ = total message length

The average code length is always between $H(X)$ and $H(X) + 1$, where $H(X)$ is the entropy.

| Scenario | Compression Ratio |
|----------|------------------|
| **Highly Skewed** (few frequent chars) | 20-40% |
| **Uniform** (equal frequencies) | 0-5% |
| **Natural Text** (English) | 40-60% |
| **Random Data** | ~0% |

## Examples and Test Cases

### Test Case 1: Classic Example
- **Text**: "ABRACADABRA"
- **Frequencies**: A(5), B(2), R(2), C(1), D(1)
- **Expected Tree**: A at depth 1, others deeper
- **Compression**: From 88 bits → ~25-30 bits

### Test Case 2: Edge Case - Single Character
- **Text**: "AAAAA"
- **Expected**: Special handling (code "0" for single char)
- **Result**: Encoding works correctly without tree ambiguity

### Test Case 3: Equal Frequencies
- **Text**: "AABB"
- **Expected**: Any valid balanced tree is optimal
- **Result**: Both characters get depth 1 codes

### Test Case 4: Large Alphabet
- **Text**: 10,000 characters from ASCII 32-126
- **Expected**: Logarithmic growth in code lengths
- **Performance**: Completes in milliseconds

### Test Case 5: Natural Language
- **Text**: English paragraph
- **Expected Compression**: 40-60% reduction
- **Practical Application**: Real-world data compression

## Key Insights

1. **Optimality Without Dynamic Programming**: Huffman coding achieves optimal compression using a greedy algorithm, unlike many other optimization problems that require DP.

2. **Frequency-Based Optimization**: The algorithm demonstrates how incorporating frequency information leads to significant space savings in data compression.

3. **Information Theory Connection**: Huffman codes approach Shannon's entropy bound, representing a fundamental limit on compression rates for a given character set.

4. **Practical Importance**: Variations of Huffman coding are used in JPEG, DEFLATE, and other widely-used compression standards, making it one of the most important greedy algorithms in practice.

5. **Tree Construction Insight**: The key insight is that building from bottom-up (smallest frequencies) naturally places frequent characters closer to the root.

6. **Prefix-Free Requirement**: The binary tree structure automatically ensures prefix-free property, enabling unambiguous decodability.

7. **Adaptability**: While this implementation uses static frequencies, adaptive Huffman coding adjusts codes based on running statistics, further improving compression.

## Related Problems and Extensions

- **Adaptive Huffman Coding**: Dynamically adjust codes as frequencies change
- **Canonical Huffman Codes**: Standardized representation for efficient implementation
- **Arithmetic Coding**: More efficient alternative achieving closer to entropy
- **Lempel-Ziv Algorithms**: Dictionary-based compression (used in ZIP, GIF)
- **Entropy Encoding**: General principle behind many compression algorithms
- **Weighted Path Length Problem**: Generalizes Huffman coding to other weight scenarios
- **Optimal Binary Search Trees**: Similar problem with different constraints
