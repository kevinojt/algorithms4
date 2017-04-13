package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdOut;


/**
 * The {@code TST} class represents a data type of key-value pair symbol table.
 * It uses node that has three child nodes data structure to store string keys.
 */
public class TST {
    private Node root;

    /**
     * Add key-value pair to the symbol table
     * @param key the key
     * @param val the value
     */
    public void put(String key, Object val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) throw new IllegalArgumentException("second argument to put() is null");

        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Object val, int d) {
        int c = charAt(key, d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }

        if (c < x.c) {
            x.left = put(x.left, key, val, d);
        }
        else if (c > x.c) {
            x.right = put(x.right, key, val, d);
        }
        else if (d < key.length() - 1) {
            x.mid = put(x.mid, key, val, d + 1);
        }
        else {
            x.val = val;
        }

        return x;
    }

    /**
     * Returns the value associated with the given {@code key}.
     * @param key the key
     * @return the value associated with the given {@code key},
     * {@code null} if the key is not in the symbol table
     */
    public Object get(String key) {
        Node x = get(root, key, 0);
        if (x != null) return x.val;
        else return null;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;

        int c = charAt(key, d);
        if (c < x.c) {
            return get(x.left, key, d);
        }
        else if (c > x.c){
            return get(x.right, key, d);
        }
        else if (d < key.length() - 1) {
            return get(x.mid, key, d + 1);
        }
        else {
            return x;
        }
    }



    /**
     * Removes key-value pair associated with the given {@code key}
     * from the symbol table.
     * @param key the key.
     */
    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;

        int c = charAt(key, d);
        if (c < x.c) {
            x.left = delete(x.left, key, d);
        }
        else if (c > x.c) {
            x.right = delete(x.right, key, d);
        }
        else if (d < key.length() - 1) {
            x.mid = delete(x.mid, key, d + 1);
        }
        else {
            x.val = null;
        }

        if (x.val != null) {
            return x;
        }
        if (x.left  != null ||
            x.right != null ||
            x.mid   != null) {
            return x;
        }

        return null;
    }

    private int charAt(String key, int d) {
        if (d < key.length()) {
            return key.charAt(d);
        }
        return -1;
    }

    private class Node {
        Object val;
        int c;
        Node left, mid, right;
    }

    // test code
    public static void main(String[] args) {
        TST st = new TST();
        String[] a = {"she", "sells", "seashells", "by", "the", "sea", "shore",
                       "the", "shells", "she", "sells", "are", "surely", "seashells"};

        int i =0;
        for(String s : a) {
            st.put(s, i++);
        }

        for(String s : a) {
            StdOut.printf("key: %-10s, value: %-10d\n", s, st.get(s));
        }

        StdOut.println("Delete sea--------------------");
        st.delete("sea");
        StdOut.printf("key: %-10s, value: %-10s\n", "sea", st.get("sea"));
        StdOut.printf("key: %-10s, value: %-10s\n", "seashells", st.get("seashells"));
    }
}
