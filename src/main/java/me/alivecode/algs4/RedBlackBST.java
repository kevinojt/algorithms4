package me.alivecode.algs4;

import java.util.NoSuchElementException;

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

    private int size(Node h) {
        if (h == null) return 0;
        return h.size;
    }

    /**
     * Returns the numbers of key-value pairs in the symbol table.
     * @return the number of key-value pairs in the symbol table.
     */
    public int size() {
        return size(root);
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
        if (isRed(h.right) && !isRed(h.left)) { rotateLeft(h); }
        if (isRed(h.left) && isRed(h.left.left)) { rotateRight(h); }
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

    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null.");

        if (value == null) {
            delete(key);
            return;
        }

        root = put(root, key, value);
    }

    private Node put(Node h, Key key, Value value) {
        if (h == null) return new Node(key, value, RED, 1);

        int cmp = key.compareTo(h.key);
        if (cmp > 0) {
            h = put(h.right, key, value);
        }
        else if (cmp < 0) {
            h = put(h.left, key, value);
        }
        else {
            h.value = value;
        }
    
        if (isRed(h.right) && !isRed(h.left)) { rotateLeft(h); }
        if (isRed(h.left) && isRed(h.left.left)) { rotateRight(h); }
        if (isRed(h.left) && isRed(h.right)) { filpColors(h); }
        h.size = 1 + size(h.left) + size(h.right);

        return h;
 
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
    }

    // Remove the smallest key-value pair rooted at h.
    private Node deleteMin(Node h) {
        if (h.left == null) return null;

        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }

        h.left = deleteMin(h.left);
        h = balance(h);
        return h;
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



} 
