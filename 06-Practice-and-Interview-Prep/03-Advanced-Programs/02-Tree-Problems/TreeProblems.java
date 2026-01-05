/**
 * Tree Problems Implementation
 * 
 * Demonstrates advanced binary tree operations:
 * - Binary Search Tree (BST) operations
 * - Tree traversals (Inorder, Preorder, Postorder, Level-order)
 * - Path sum problems
 * - BST validation
 * - LCA (Lowest Common Ancestor)
 */

import java.util.*;

public class TreeProblems {
    
    // ==================== TREE NODE ====================
    static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int value) {
            this.value = value;
        }
    }
    
    // ==================== BST CLASS ====================
    /**
     * Binary Search Tree with various operations
     * Time Complexity: O(log n) average, O(n) worst case
     * Space Complexity: O(h) where h is height
     */
    static class BinarySearchTree {
        private TreeNode root;
        
        // -------- Insertion --------
        public void insert(int value) {
            root = insertRec(root, value);
        }
        
        private TreeNode insertRec(TreeNode node, int value) {
            if (node == null) {
                return new TreeNode(value);
            }
            
            if (value < node.value) {
                node.left = insertRec(node.left, value);
            } else if (value > node.value) {
                node.right = insertRec(node.right, value);
            }
            
            return node;
        }
        
        // -------- Search --------
        public boolean search(int value) {
            return searchRec(root, value);
        }
        
        private boolean searchRec(TreeNode node, int value) {
            if (node == null) {
                return false;
            }
            
            if (value == node.value) {
                return true;
            } else if (value < node.value) {
                return searchRec(node.left, value);
            } else {
                return searchRec(node.right, value);
            }
        }
        
        // -------- Deletion --------
        public void delete(int value) {
            root = deleteRec(root, value);
        }
        
        private TreeNode deleteRec(TreeNode node, int value) {
            if (node == null) {
                return null;
            }
            
            if (value < node.value) {
                node.left = deleteRec(node.left, value);
            } else if (value > node.value) {
                node.right = deleteRec(node.right, value);
            } else {
                // Node with only one child or no child
                if (node.left == null) {
                    return node.right;
                } else if (node.right == null) {
                    return node.left;
                }
                
                // Node with two children
                // Find the inorder successor (smallest in right subtree)
                TreeNode minRight = findMin(node.right);
                node.value = minRight.value;
                node.right = deleteRec(node.right, minRight.value);
            }
            
            return node;
        }
        
        private TreeNode findMin(TreeNode node) {
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
        
        // -------- Tree Traversals --------
        public void inorderTraversal() {
            System.out.print("Inorder (Left-Root-Right):  ");
            inorder(root);
            System.out.println();
        }
        
        private void inorder(TreeNode node) {
            if (node == null) return;
            inorder(node.left);
            System.out.print(node.value + " ");
            inorder(node.right);
        }
        
        public void preorderTraversal() {
            System.out.print("Preorder (Root-Left-Right): ");
            preorder(root);
            System.out.println();
        }
        
        private void preorder(TreeNode node) {
            if (node == null) return;
            System.out.print(node.value + " ");
            preorder(node.left);
            preorder(node.right);
        }
        
        public void postorderTraversal() {
            System.out.print("Postorder (Left-Right-Root): ");
            postorder(root);
            System.out.println();
        }
        
        private void postorder(TreeNode node) {
            if (node == null) return;
            postorder(node.left);
            postorder(node.right);
            System.out.print(node.value + " ");
        }
        
        public void levelOrderTraversal() {
            System.out.print("Level Order (BFS):       ");
            if (root == null) {
                System.out.println();
                return;
            }
            
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                System.out.print(node.value + " ");
                
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            System.out.println();
        }
        
        // -------- Path Sum --------
        /**
         * Find if there's a path from root to leaf with given sum
         * Time: O(n) where n is number of nodes
         * Space: O(h) where h is height
         */
        public boolean hasPathSum(int targetSum) {
            return hasPathSumRec(root, targetSum);
        }
        
        private boolean hasPathSumRec(TreeNode node, int sum) {
            if (node == null) {
                return false;
            }
            
            // Leaf node
            if (node.left == null && node.right == null) {
                return node.value == sum;
            }
            
            return hasPathSumRec(node.left, sum - node.value) ||
                   hasPathSumRec(node.right, sum - node.value);
        }
        
        /**
         * Find all paths that sum to target
         */
        public List<List<Integer>> findAllPathsWithSum(int targetSum) {
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> path = new ArrayList<>();
            findPathsRec(root, targetSum, path, result);
            return result;
        }
        
        private void findPathsRec(TreeNode node, int sum, List<Integer> path,
                                  List<List<Integer>> result) {
            if (node == null) {
                return;
            }
            
            path.add(node.value);
            
            if (node.left == null && node.right == null && sum == node.value) {
                result.add(new ArrayList<>(path));
            }
            
            findPathsRec(node.left, sum - node.value, path, result);
            findPathsRec(node.right, sum - node.value, path, result);
            
            path.remove(path.size() - 1);
        }
        
        // -------- Tree Height --------
        public int getHeight() {
            return getHeightRec(root);
        }
        
        private int getHeightRec(TreeNode node) {
            if (node == null) {
                return -1;
            }
            return 1 + Math.max(getHeightRec(node.left), getHeightRec(node.right));
        }
        
        // -------- Count Nodes --------
        public int countNodes() {
            return countNodesRec(root);
        }
        
        private int countNodesRec(TreeNode node) {
            if (node == null) {
                return 0;
            }
            return 1 + countNodesRec(node.left) + countNodesRec(node.right);
        }
        
        // -------- Lowest Common Ancestor --------
        /**
         * Find LCA of two nodes in BST
         * Time: O(log n) on average
         * Space: O(1) if iterative
         */
        public TreeNode findLCA(int value1, int value2) {
            return findLCARec(root, value1, value2);
        }
        
        private TreeNode findLCARec(TreeNode node, int value1, int value2) {
            if (node == null) {
                return null;
            }
            
            if (value1 < node.value && value2 < node.value) {
                return findLCARec(node.left, value1, value2);
            }
            
            if (value1 > node.value && value2 > node.value) {
                return findLCARec(node.right, value1, value2);
            }
            
            return node;
        }
        
        // -------- BST Validation --------
        /**
         * Validate if tree is a valid BST
         */
        public boolean isValidBST() {
            return isValidBSTRec(root, Long.MIN_VALUE, Long.MAX_VALUE);
        }
        
        private boolean isValidBSTRec(TreeNode node, long min, long max) {
            if (node == null) {
                return true;
            }
            
            if (node.value <= min || node.value >= max) {
                return false;
            }
            
            return isValidBSTRec(node.left, min, node.value) &&
                   isValidBSTRec(node.right, node.value, max);
        }
        
        // -------- Mirror/Invert Tree --------
        public void invertTree() {
            root = invertTreeRec(root);
        }
        
        private TreeNode invertTreeRec(TreeNode node) {
            if (node == null) {
                return null;
            }
            
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            
            invertTreeRec(node.left);
            invertTreeRec(node.right);
            
            return node;
        }
        
        // -------- Check if Balanced --------
        /**
         * Check if tree is height-balanced
         * (height difference between left and right <= 1 for all nodes)
         */
        public boolean isBalanced() {
            return isBalancedRec(root) != -1;
        }
        
        private int isBalancedRec(TreeNode node) {
            if (node == null) {
                return 0;
            }
            
            int leftHeight = isBalancedRec(node.left);
            if (leftHeight == -1) return -1;
            
            int rightHeight = isBalancedRec(node.right);
            if (rightHeight == -1) return -1;
            
            if (Math.abs(leftHeight - rightHeight) > 1) {
                return -1;
            }
            
            return 1 + Math.max(leftHeight, rightHeight);
        }
    }
    
    // ==================== DEMONSTRATIONS ====================
    
    public static void demonstrateBSTOperations() {
        System.out.println("\n========== BST OPERATIONS ==========");
        BinarySearchTree bst = new BinarySearchTree();
        
        System.out.println("Inserting values: 50, 30, 70, 20, 40, 60, 80");
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int val : values) {
            bst.insert(val);
        }
        
        System.out.println("\nTree Traversals:");
        bst.inorderTraversal();
        bst.preorderTraversal();
        bst.postorderTraversal();
        bst.levelOrderTraversal();
        
        System.out.println("\nTree Statistics:");
        System.out.println("Height: " + bst.getHeight());
        System.out.println("Node count: " + bst.countNodes());
        System.out.println("Is valid BST: " + bst.isValidBST());
        System.out.println("Is balanced: " + bst.isBalanced());
        
        System.out.println("\nSearch Operations:");
        System.out.println("Search 40: " + bst.search(40));
        System.out.println("Search 99: " + bst.search(99));
        
        System.out.println("\nLowest Common Ancestor of 20 and 40: " +
                bst.findLCA(20, 40).value);
        System.out.println("Lowest Common Ancestor of 60 and 80: " +
                bst.findLCA(60, 80).value);
    }
    
    public static void demonstratePathSum() {
        System.out.println("\n========== PATH SUM PROBLEMS ==========");
        BinarySearchTree bst = new BinarySearchTree();
        
        int[] values = {5, 4, 8, 11, 13, 4, 7};
        for (int val : values) {
            bst.insert(val);
        }
        
        System.out.println("Tree in inorder: ");
        bst.inorderTraversal();
        
        System.out.println("\nHas path with sum 22: " + bst.hasPathSum(22));
        System.out.println("Has path with sum 10: " + bst.hasPathSum(10));
        System.out.println("Has path with sum 27: " + bst.hasPathSum(27));
        
        System.out.println("\nAll paths with sum 22:");
        List<List<Integer>> paths = bst.findAllPathsWithSum(22);
        for (List<Integer> path : paths) {
            System.out.println(path);
        }
    }
    
    public static void demonstrateDeletion() {
        System.out.println("\n========== DELETION OPERATIONS ==========");
        BinarySearchTree bst = new BinarySearchTree();
        
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35};
        System.out.println("Initial values: " + Arrays.toString(values));
        for (int val : values) {
            bst.insert(val);
        }
        
        System.out.println("Initial tree (Inorder): ");
        bst.inorderTraversal();
        
        System.out.println("\nDeleting 20 (node with two children)");
        bst.delete(20);
        bst.inorderTraversal();
        
        System.out.println("\nDeleting 30 (node with two children)");
        bst.delete(30);
        bst.inorderTraversal();
        
        System.out.println("\nDeleting 50 (root node with two children)");
        bst.delete(50);
        bst.inorderTraversal();
    }
    
    public static void demonstrateTreeManipulation() {
        System.out.println("\n========== TREE MANIPULATION ==========");
        BinarySearchTree bst = new BinarySearchTree();
        
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int val : values) {
            bst.insert(val);
        }
        
        System.out.println("Original tree (Inorder): ");
        bst.inorderTraversal();
        
        System.out.println("\nInverting tree...");
        bst.invertTree();
        System.out.println("Inverted tree (Inorder): ");
        bst.inorderTraversal();
    }
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║             TREE PROBLEMS IMPLEMENTATION              ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        demonstrateBSTOperations();
        demonstratePathSum();
        demonstrateDeletion();
        demonstrateTreeManipulation();
        
        System.out.println("\n" + "=".repeat(55));
        System.out.println("All demonstrations completed successfully!");
        System.out.println("=".repeat(55));
    }
}
