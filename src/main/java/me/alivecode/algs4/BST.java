package me.alivecode.algs4;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * A binary search tree.
 */
public class BST<Key extends Comparable<Key>, Value> {
    Node root;

    private class Node {
        Key key;
        Value value;
        int size;
        Node left;
        Node right;
        public Node(Key key, Value value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
            left = null;
            right = null;
        }
    }

    public BST() {
        root = null;
    }

    /**
     * Put new node to symbol table if there is no key exists. 
     * Update value if key already exists.
     * Delete key if key already exists and value is null.
     *
     * @param key the key.
     * @param value the value. 
     * @throws IllegalArgumentException if {@code key} is null.
     */
    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null.");
        if (value == null) {
            delete(key);
            return;
        }
        root = put(root, key, value);
        assert check();
    }

    private Node put(Node x, Key key, Value value) {
        if (x == null) return new Node (key, value, 1); 
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, value);
        }
        else if (cmp > 0) {
            x.right = put(x.right, key, value);
        }
        else {
            x.value = value;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key) {
        if (key == null) return;
        if (root == null) return;
        root = delete(root, key);
        assert check();
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        }
        else if (cmp > 0) {
            x.right = delete(x.right, key);
        } 
        else { // cmp == 0, key matched
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;
            Node t = x;
            // make x's right smallest child as the new root of this subtree.
            x = min(t.right);
            // remove x's smallest child
            // and make x's right subtree as new root's right subtree. 
            x.right = deleteMin(t.right);
            // make x's left subtree as new root's left subtree.
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    } 

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table.");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("called max() whit empty symbol table.");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow.");
        root = deleteMin(root);
        assert check();
    }
    // delete smallest key of x and its subtree.
    private Node deleteMin(Node x) {
        // smallest node found.
        // replace it as its right node for deleting it.
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow.");
        root = deleteMax(root);
        assert check();
    }

    // Delete the largest key of x and its subtree.
    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    } 

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null.");
        return get(key) != null;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null.");
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        }
        else if (cmp > 0) {
            return get(x.right, key);
        }
        else {
            return x.value;
        }
    }

    public int size() {
        if (root == null) return 0;
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Return all keys in symbol in accend order.
     */
    public Iterable<Key> keys() {
        return keys(min(), max());
    } 

    /**
     * Return all keys between {@code lo} and {@code hi} in symbol table.
     *
     * @param lo lower bound of key. Must larger than or equal to {@code min()}.
     * @param hi upper bound of key. Must less than or equal to {@code max()}.
     * @throws IllegalArgumentException if lo or hi is null.
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null.");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null.");
        Queue<Key> queue = new Queue<Key>();
        keys(queue, root, lo, hi);
        return queue;
    }

    private void keys(Queue<Key> queue, Node x, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) {
            keys(queue, x.left, lo, hi);
        }
        if (cmplo <= 0 && cmphi >= 0) {
           queue.enqueue(x.key);
        }
        if (cmphi > 0) {
            keys(queue, x.right, lo, hi);
        }
    }
        

    /**
     * Return kth smallest key in symbol table.
     *
     * @param k the order statics.
     * @return the kth smallest key in symbol table.
     * @throws IllegalArgumentException if {@code k} not between 0 and size of symble table.
     */
    public Key select(int k) {
        if (k < 0 || k >= size()) throw new IllegalArgumentException("argument to select() must between 0 and size()-1.");
        return select(root, k).key;
    }

    private Node select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        if (t > k) { return select(x.left, k); }
        else if (t < k) { return select(x.right, k-t-1); }
        else return x;
    }

    /**
     * Return the number of keys in the symbol table strictly less than {@code key}.
     *
     * @param key the key
     * @return the number of keys in the symbol table strickly less than {@code key}.
     * @throws IllegalArgumentException if {@code kye} is {@code null}.
     */
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null.");
        return rank(root, key);
    }

    private int rank(Node x, Key key) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return rank(x.left, key);
        }
        else if (cmp > 0) {
            return 1 + size(x.left) + rank(x.right, key);
        }
        else {
            return size(x.left);
        }
    }

    /** 
     * Return largest key that smaller than or equal to the {@code key}.
     *
     * @param key the key.
     * @return largest key that smaller than or equal to {@code key}.
     * @throws IllegalArgumentException if {@code key} is {@code null}.
     * @throws NoSuchElementException if symbol table is null.
     */
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("arguement to floor() is null.");
        if (isEmpty()) throw new NoSuchElementException("call floor() with empty symbol table.");
        Node t = floor(root, key);
        if (t == null) return null;
        return t.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null; // there is no key smaller than the key.
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key); // x is larger than the key, go left.
        Node t = floor(x.right, key); // x is smaller than the key. Is it another key larger than x and smaller than the key?
        if (t == null) return x; // There is no key larger than key and smaller key, we got x.
        else return t; // t is the largest key larger than x and smaller than the key.
    }

    /**
     * Return smallest key that larger than or equal to the {@code key}.
     *
     */
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null.");
        if (isEmpty()) throw new NoSuchElementException("call ceiling() with symbol table.");
        Node t = ceiling(root, key);
        if (t == null) return null;
        return t.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) { // x is larger than the key.
            Node t = ceiling(x.left, key); // Is it another key smaller than x?
            if (t == null) return x; // t is not smaller than x, we got x.
            else return t;
        }
        return ceiling(x.right, key);
    }

    private boolean check() {
        if (!isBST(root)) {
            StdOut.println("Not in symmtric order.");
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
        return true;
    }

    // Is symbol table in symmtric order?
    private boolean isBST(Node x) {
        if (x == null) return true;
        if (x.left != null  && x.key.compareTo(x.left.key) <= 0) return false;
        if (x.right != null && x.key.compareTo(x.right.key) >= 0) return false;
        return isBST(x.left) && isBST(x.right);
    }
    // oujt: Bob Dondero's solution from algorithm 4th edtion.
    // ref to http://algs4.cs.princeton.edu/code/
    /*
    private boolean isBST(Node x, Key max, Key min) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }
    */    
    
    // Are the size fields correct?
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }
    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (x.size != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }
   
    // Check that ranks are consistent.  
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i))) return false;
        }
        for (Key key : keys()) {
            if (key.compareTo(select(rank(key))) != 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        BST<String, String> bst = new BST<String, String>();

        String key = null;
        String value = null;

        while(!StdIn.isEmpty()) {
            if (key == null) {
                key = StdIn.readString();
                if (key.startsWith("-")) {
                    key = key.substring(1, key.length());
                    StdOut.printf("delete node, key: %s, value: %s.\n", key, bst.get(key));
                    bst.delete(key);
                    key = null;
                }
            }
            else {
                value = StdIn.readString();
                StdOut.printf("put new node, key: %s, value: %s.\n", key, value);
                bst.put(key, value);
                key = null;
                value = null;
            }
        }        
        StdOut.println(bst.size() + " item(s).");
        for(String k : bst.keys()) {
            StdOut.printf("key: %s, value: %s.\n", k, bst.get(k));
        }
    }

}

             
