package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code TrieST} represents a symbol table
 * of string key and generic value.
 * It uses Trie data structure to store strings and search.
 */
public class TrieST {
    // extended ASCII
    private static final int R = 256;
    private Node root = null;
    private int size = 0;

    /**
     * Is the symbol table empty?
     * @return {@code true} if the symbol table is empty,
     * {@code false} otherwise.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the size of the symbol table.
     * @return the size of the symbol table
     */
    public int size() {
        return size;
    }

    /**
     * Add {@code key}-{@code val} pair to the symbol table.
     * @param key the key
     * @param val the value
     */
    public void put(String key, Object val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) throw new IllegalArgumentException("second argument to put() is null");
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Object val, int d) {
        if (x == null) { x = new Node(); };
        if (key.length() == d) {
            if (x.val == null) size ++;
            x.val = val;
            return x;
        }
        int c = charAt(key, d);
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }

    /**
     * Dose the symbol table contain the {@code key}?
     * @param key the key
     * @return {@code true} if the symbol table contains the given key,
     * {@code false} otherwise.
     */
    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /**
     * Returns the value associates with the {@code key}.
     * @param key the key
     * @return the value associates with the given key,
     * {@code null} if the symbol table doesn't contain the given key.
     */
    public Object get(String key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        Node x = get(root, key, 0);
        return x == null ? null : x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (key.length() == d) {
            return x;
        }
        int c = charAt(key, d);
        return get(x.next[c], key, d+1);
    }

    /**
     * Removes the key-value pair from the symbol table.
     * @param key the key.
     */
    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        root =  delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (key.length() == d) {
            if (x.val != null) size--;
            x.val = null;
        }
        else {
            int c = charAt(key, d);
            x.next[c] = delete(x.next[c], key, d + 1);
        }

        if (x.val != null) return x;
        for(int r = 0; r < R; r++) {
            if (x.next[r] != null) {
                return x;
            }
        }
        return null;
    }

    private int charAt(String s, int d) {
        if (d < s.length()) {
            return s.charAt(d);
        }
        return -1;
    }

    private static class Node {
        Object val;
        Node[] next = new Node[R];
    }

    // test code
    public static void main(String[] args) {
        TrieST trie = new TrieST();

        /*
        while (!StdIn.isEmpty()) {
            String key = StdIn.readString();
            if (trie.contains(key)) {
                StdOut.printf("key: %s, old value: %s\n", key, trie.get(key));
                Object value = StdIn.readString();
                trie.put(key ,value);
                StdOut.printf("key: %s, new value: %s\n", key, trie.get(key));
            }
            else {
                Object value = StdIn.readString();
                trie.put(key, value);
                StdOut.printf("key: %s, value: %s\n", key, trie.get(key));
            }
        }
        */

        trie.put("sea", "sea");
        trie.put("seashell","seashell");

        trie.delete("seashells");
        StdOut.printf("key: %s, value: %s\n", "sea", trie.get("sea"));

        trie.delete("sea");
        StdOut.printf("key: %s, value: %s\n", "sea", trie.get("sea"));
        StdOut.printf("key: %s, value: %s\n", "seashell", trie.get("seashell"));

        trie.put("sea", "sea");
        trie.delete("seashell");
        StdOut.printf("key: %s, value: %s\n", "sea", trie.get("sea"));
        StdOut.printf("key: %s, value: %s\n", "seashell", trie.get("seashell"));


    }

}
