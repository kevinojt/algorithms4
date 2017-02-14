package me.alivecode.algs4;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

/*******************************************************************
 * Repersent a left-lean Red-black tree.
 * Original from Algorithm 4th http://alg4.cs.princeton.edu
 ******************************************************************/
public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        Key key;
        Value value;
        Node left, right;
        boolean color;
        int size;

        public Node(Key key, Value value, boolean color, int size) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.size = size;
        }
    }

    /**
     * Initialize a red-black tree.
     */
    public RedBlackBST() {
        root = null;
    }
    /********************************************
     * Node help methods
     *******************************************/
    private boolean isRed(Node h) {
        if (h == null) return false;
        return h.color == RED;
    }

    /**
     * Returns the numbers of key-value pairs in the symbol table.
     * @return the number of key-value pairs in the symbol table.
     */
    public int size() {
        return size(root);
    }

    private int size(Node h) {
        if (h == null) return 0;
        return h.size;
    }

    /**
     * Is this symbol table empty?
     * @return {@code true} if the symbol table is empty or otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /********************************************
     * Standard BST search
     *******************************************/

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key.
     * @return the value associated with the given key if the key is in the symbol table 
     * or {@code null} if the key is not in the symbol table.
     */ 
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null.");
        return get(root, key);
    }

    private Value get(Node h, Key key) {
        while(h != null) {
            int cmp = key.compareTo(h.key);
            if (cmp > 0) h = h.right;
            else if (cmp < 0) h = h.left;
            else return h.value;
        }
        return null;
    }

    /**
     * Is key in the symbol table?
     * @return {@code true} if the given key is in the symbol table or otherwise.
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /********************************************
     * Red black tree helpper methods
     *******************************************/

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = 1 + size(h.left) + size(h.right);
        return x;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = 1 + size(h.left) + size(h.right);
        return x;
    }

    private void filpColors(Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    private Node balance(Node h) {
        if (isRed(h.right) && !isRed(h.left)) { h = rotateLeft(h); }
        if (isRed(h.left) && isRed(h.left.left)) { h = rotateRight(h); }
        if (isRed(h.left) && isRed(h.right)) { filpColors(h); }
        h.size = 1 + size(h.left) + size(h.right);
        return h;
    }

    // TODO: What's moveRedLeft and moveRedRight for?
    private Node moveRedLeft(Node h) {
        filpColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            filpColors(h);
        }
        return h;
    }

    private Node moveRedRight(Node h) {
        filpColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            filpColors(h);
        }
        return h;
    }

    /*********************************************
     * Red black tree insertion
     ********************************************/

    /**
     * Add key-value pair into the symbol table.
     * Remove key and associated value from the symbol table if value is {@code null}.
     *
     * @param key the key.
     * @param value the value.
     * @throws IllegalArgumentException if {@code key} is {@code null}.
     */
    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null.");

        if (value == null) {
            delete(key);
            return;
        }

        root = put(root, key, value);
        root.color = BLACK;
        
        assert check();
    }

    private Node put(Node h, Key key, Value value) {
        if (h == null) return new Node(key, value, RED, 1);

        int cmp = key.compareTo(h.key);
        if (cmp > 0) {
            h.right = put(h.right, key, value);
        }
        else if (cmp < 0) {
            h.left = put(h.left, key, value);
        }
        else {
            h.value = value;
        }
        /*    
        if (isRed(h.right) && !isRed(h.left)) { h = rotateLeft(h); }
        if (isRed(h.left) && isRed(h.left.left)) { h = rotateRight(h); }
        if (isRed(h.left) && isRed(h.right)) { filpColors(h); }
        h.size = 1 + size(h.left) + size(h.right);
        */
        return balance(h);
    }

    /********************************************
     * Red-black tree deletion
     *******************************************/

    /**
     * Remove the smallest key and associated value from the symbol table.
     * @throws NoSuchElementException if the symbol is empty.
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow.");

        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }

        root = deleteMin(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
        assert check();
    }

    // Remove the smallest key-value pair rooted at h.
    private Node deleteMin(Node h) {
        if (h.left == null) return null;

        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }

        h.left = deleteMin(h.left);
        return balance(h);
    }

    /**
     * Remove the largest key and accsociated value for the symbol table.
     * @throws NoSuchElementException if the symbol table is empty.
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("BST is underflow.");

        if (!isRed(root.left) && !isRed(root.right)) {
           root.color = RED;
        }
        
        root = deleteMax(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
        assert check();
    }

    // Remove the largest key-value pair rooted at h.
    private Node deleteMax(Node h) {
        if (isRed(h.left)) {
           h = rotateRight(h);
        }

        if (h.right == null) return null;

        if (!isRed(h.right) && !isRed(h.right.left)) {
            h = moveRedRight(h);
        }

        h.right = deleteMax(h.right);

        return balance(h);
    }

    //TODO: Read to understand.
    /**
     * Remove the key and associated value from the symbol table.
     * @param key the key.
     * @throws IllegalArgumentException if the key is {@code null}.
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null.");
        if (!contains(key)) return;

        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }

        root = delete(root, key);
        if (!isEmpty()) {
            root.color = BLACK;
        }
        assert check();
    }

    private Node delete(Node h, Key key) {
        if (key.compareTo(h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }
            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left)) {
                h = rotateRight(h);
            }
            if (key.compareTo(h.key) == 0 && h.right == null) return null;
            if (!isRed(h.right) && !isRed(h.right.left)) {
                h = moveRedRight(h);
            }
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right); 
                h.key = x.key;
                h.value = x.value;
                h.right = deleteMin(h.right);
            }
            else {
                h.right = delete(h.right, key);
            }
        }
        return balance(h);
    }

    /***********************************************
     * Utility functions.
     **********************************************/

    /**
     * Returns the height of the BST.
     * @return the height of the BST(a 1-node tree has height 0);
     */
    public int height() {
        return height(root);
    }

    private int height(Node h) {
        if (h == null) return -1;
        return 1 + Math.max(height(h.left), height(h.right));
    }
    
    /***********************************************
     * Ordered symbol table methods.
     **********************************************/

    /**
     * Returns the small key in the symbol table.
     * @throws NoSuchElementException if the symbol table is empty.
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("call min() with empty symbol table.");
        return min(root).key;
    }

    private Node min(Node h) {
        if (h.left == null) return h;
        return min(h.left);
    }

    /**
     * Returns the largest key in the symbol table.
     * @throws NoSuchElementException if the symbol table is empty.
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("call max() with empty symbol table.");
        return max(root).key;
    }

    private Node max(Node h) {
        if (h.right == null) return h;
        return max(h.right);
    }

    /**
     * Returns the largest key in the symbol table smaller or equal to the given key.
     * @param key the given key.
     * @throws NoSuchElementException if the symbol table is empty.
     */
    public Key floor(Key key) {
        if (key == null) throw new NoSuchElementException("call floor() with empty symbol table.");
        Node t = floor(root, key);
        if (t != null) {
            return t.key;
        }
        else {
            return null;
        }
    }

    private Node floor(Node h, Key key) {
        if (h == null) return null;
        
        int cmp = key.compareTo(h.key);
        if (cmp == 0) return h;
        if (cmp < 0) return floor(h.left, key);
        Node t = floor(h.right, key);
        if (t != null) {
           return t;
        }
        else {
           return h;
        }
    } 
        
    /**
     * Returns the smallest key  in the symbol table larger or equal to the given key.
     * @param key the given key.
     * throws NoSuchElementException if the symbol table is empty.
     */
    public Key ceiling(Key key) {
        if (key == null) throw new NoSuchElementException("call ceiling() with empty symbol table.");
        Node t = ceiling(root, key);
        if (t != null) {
           return t.key;
        }
        else {
           return null;
        } 
    }

    private Node ceiling(Node h, Key key) {
        if (h == null) return null;

        int cmp = key.compareTo(h.key);
        if (cmp == 0) return h;
        if (cmp > 0) return ceiling(h.right, key);
        Node t = ceiling(h.left, key);
        if (t != null) {
            return t;
        }
        else { 
            return h;
        }
    }        

    /**
     * Returns the kth smallest key in the symbol table.
     * @param k the order statistic.
     * @return the kth smallest key in the symbol table.
     * @throws IllegalArgumentException if unless {@code k} is between 0 and <em>N</em> &minus;1.
     */
    public Key select(int k) {
        if (k < 0 || k >= size()) throw new IllegalArgumentException();
        Node t = select(root, k);
        return t.key;
    }

    private Node select(Node h, int k) {
        int c = size(h.left); 
        if (c > k) return select(h.left, k);
        if (c < k) return select(h.right, k-c-1);
        return h;
    }
    
    /**
     * Return the number of keys int the symbol table strictly smaller than {@code key}.
     * @param key the given key.
     * @returns the number of keys smaller than {@code key}.
     * @throws IllegalArgumentException if {@code key} is {@code null}.
     */
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null.");
        return rank(root, key);
    }

    private int rank(Node h, Key key) {
        if (h == null) return 0;
        int cmp = key.compareTo(h.key);
        if (cmp < 0) return rank(h.left, key);
        if (cmp > 0) return 1 + size(h.left) + rank(h.right, key);
        return size(h.left);
    }

    /**********************************************
     * Range count and range search.
     *********************************************/

    public Iterable<Key> keys() {
        if (isEmpty()) return new Queue<Key>();
        return keys(min(), max());
    }

    // Returns all keys in the symbol table in accendent order.
    private Iterable<Key> keysAscendent() {
        Queue<Key> queue = new Queue<Key>();
        keysAscendent(root, queue); 
        return queue;
    }

    private void keysAscendent(Node h, Queue<Key> queue) {
        if (h == null) return;
        keysAscendent(h.left, queue);
        queue.enqueue(h.key);
        keysAscendent(h.right, queue);
    }
    
    // Returns all keys int the symbol table in decendent order.
    private Iterable<Key> keysDescendent() {
        Queue<Key> queue = new Queue<Key>();
        keysDescendent(root, queue);
        return queue;
    }

    private void keysDescendent(Node h, Queue<Key> queue) {
        if (h == null) return;
        keysDescendent(h.right, queue);
        queue.enqueue(h.key);
        keysDescendent(h.left, queue);
    }

    /**
     * Returns keys in the symbol table between {@code lo} and {@code hi}.
     * @param lo lower bound key.
     * @param hi upper bound key.
     * @returns keys between {@code lo} and {@code hi}. 
     * @throws IllegalArgumentException if {@code lo} or {@code hi} is {@code null}.
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null.");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null.");

        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node h, Queue<Key> queue, Key lo, Key hi) {
        if (h == null) return;

        int cmplo = lo.compareTo(h.key);
        int cmphi = hi.compareTo(h.key);
        if (cmplo < 0) keys(h.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(h.key);
        if (cmphi > 0) keys(h.right, queue, lo, hi);
    }

    /**
     * Returns the number of keys in the symbol table int given range.
     *
     * @param lo minimum endpoint.
     * @param hi maximum ednpoint.
     * @returns the number of keys in the symbol table between {@code lo} and {@code hi}.
     * @throws IllegalArgumentException if {@code lo} or {@code hi} is {@code null}.
     */
    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null.");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null.");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    /********************************************************************************
     * Check integrity of red-black tree data structure.
     *******************************************************************************/ 

    private boolean check() {
        if (!isBST()) {
            StdOut.println("Not in symetric order.");
            return false;
        }

        if (!isSizeConsistent()) {
            StdOut.println("Subtree counts not consistent.");
            return false;
        }

        if (!isRankConsistent()) {
            StdOut.println("Ranks not consistent.");
            return false;
        }

        if (!is23()) {
            StdOut.println("Not a 2-3 tree");
            return false;
        }

        if (!isBalanced()) {
            StdOut.println("Not balanced");
            return false;
        }

        return true;
    }

    private boolean isBST() {
        return isBST(root, null, null);
    }

    // Bob Dondero's elegant solution.
    private boolean isBST(Node h, Key min, Key max) {
        if (h == null) return true;
        if (min != null && h.key.compareTo(min) <= 0) return false;
        if (max != null && h.key.compareTo(max) >= 0) return false;
        return isBST(h.left, min, h.key) && isBST(h.right, h.key, max);
    }

    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node h) {
        if (h == null) return true;
        if (size(h) != size(h.left) + size(h.right) + 1) return false;
        return isSizeConsistent(h.left) && isSizeConsistent(h.right);
    }

    private boolean isRankConsistent() {
        for(int i = 0; i < size(); i++) {
           if (i != rank(select(i))) return false;
        }
        for(Key key : keys()) {
           if (key.compareTo(select(rank(key))) != 0) return false;
        }
        return true;
    }

    // Does the tree have no red right links, and at most on (left)
    // red link in a row on any path?
    private boolean is23() {
        return is23(root);
    }

    private boolean is23(Node h) {
        if (h == null) return true;
        if (isRed(h.right)) return false;
        if (h != root && isRed(h) && isRed(h.left)) return false;
        return is23(h.left) && is23(h.right);
    }

    // Do all paths from root to leaf have same number of black nodes?
    private boolean isBalanced() {
        int black = 0;
        Node x = root;
        while (x != null) {
            if (!isRed(x)) {
                black++;
            }
            x = x.left;
        } 
        return isBalanced(root, black);
    }

    // Does every path from root to leaf have the give number of nodes?
    private boolean isBalanced(Node h, int black) {
        if (h == null) return black == 0;
        if (!isRed(h)) {
            black--;
        }
        return isBalanced(h.left, black) && isBalanced(h.right, black);
    }

    public static void main(String[] args) {
        RedBlackBST<String, String> rbt = new RedBlackBST<String, String>();
        
        String key = null;
        String value = null;
        while(!StdIn.isEmpty()) {
            if (key == null) {
                key = StdIn.readString();
                if (key.startsWith("-")) {
                    key = key.substring(1, key.length());
                    value = rbt.get(key);
                    rbt.delete(key);
                    StdOut.printf("key: %s, value: %s deleted.\n", key, value);
                    key = null;
                    value = null;
                }
            }
            else {
                value = StdIn.readString();
                rbt.put(key, value);
                StdOut.printf("key: %s, value: %s put.\n", key, value);
                key = null;
                value = null;
            }
        }

        StdOut.println(rbt.size() + " item(s) in the symbol table.");
        StdOut.println("Height of the red-black tree is " + rbt.height());
        for(String k : rbt.keys()) {
            StdOut.printf("key: %s, value: %s.\n", k, rbt.get(k));
        }
    }

    
} 
