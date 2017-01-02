package me.alivecode.algs4;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

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

    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null.");
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

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("called max() whit empty symbol table.");
        return max(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
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

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow.");
        root = deleteMax(root);
        assert check();
    }

    private Node deleteMax(Node x) {
        if (x == null) return null;
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
    
    // delete x's smallest child node.
    private Node deleteMin(Node x) {
        // smallest node found.
        // replace it as its right node for deleting it.
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
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

    private boolean check() {
        return check(root);
    }

    private boolean check(Node x) {
        if (x == null) return true;
        if (x.left != null && x.right == null) return check(x.left) && x.key.compareTo(x.left.key) > 0;
        if (x.left == null && x.right != null) return check(x.right) && x.key.compareTo(x.right.key) < 0;
        return check(x.left) && check(x.right);
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
    }

}

             
