package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code SequentialSearchST} class repersents an unordered
 * symbol table of generic key-value pairs.
 */
public class SequentialSearchST<Key, Value> {
    private int size;
    private Node first;

    // a helper linked list type.
    private class Node {
        Key key;
        Value value;
        Node next;
        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    /**
     * Initialize an empty symbol table.
     */
    public SequentialSearchST() {
        size = 0;
        first = null;
    }

    /**
     * Insert specified key-value pair into the symbol table. Overwrite 
     * the old value if the specified key already in the symbol table.
     * Delete the specified key from the symbol table
     * if the specified value is {@code null}.
     *
     * @param key the key.
     * @param value the value.
     * @throws IllegalArgumentException if the {@code key} is null.
     */ 
    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null.");

        if (value == null) {
            delete(key);
            return;
        }

        for(Node t = first; t != null; t = t.next) {
            if (key.equals(t.key)) {
                t.value = value;
                return;
            }
        } 
        first = new Node(key, value, first);
        size++;
    }

    /**
     * Delete the specified key from the symbol table.
     *
     * @param key the key.
     * @throws IllegalArgumentException if the {@code key} is null.
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null.");
        first = delete(first, key);
    }


    private Node delete(Node n, Key key) {
        if (n == null) return null;
        if (n.key.equals(key)) {
            size--;
            return n.next;
        }
        n.next = delete(n.next, key);
        return n;
    } 
    
    /**
     * Returns the number of key-value pairs in the symbol table.
     * 
     * @return the number of the key-value pairs in the symbol table.
     */
    public int size() {
        return size;
    }

    /**
     * Is the symbol empty?
     *
     * @returns {@code true} if the symbol is empty or otherwise.
     */
    public boolean isEmpty() {
        return (first == null);
    }

    /**
     * Does the symbol table contain the given key.
     *
     * @return {code true} if the given {@code key} is in the symbol table.
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key
     * if the key is in the symbol table or {@code null} 
     * if the key is not in the symbol table.
     *
     * @param key the key.
     * @return the associated value of the given key.
     */
    public Value get(Key key) {
        if (key == null) return null;

        Node t = first;
        while(t != null) {
            if (t.key.equals(key)) return t.value;
            t = t.next;
        }

        return null;
    }

    /**
     * Returns all keys in the symbol as an {@code Iterable}.
     *
     * @return all keys in the symbol table.
     */
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for(Node t = first; t != null; t = t.next) {
            queue.enqueue(t.key);
        }

        return queue;
    }

    // Unit test code.
    public static void main(String[] args) {
        SequentialSearchST<String, Integer> st = new SequentialSearchST<String, Integer>();
        String key = null;
        int value = 0;

        while(!StdIn.isEmpty()) {
            key = StdIn.readString();
            if (key.startsWith("-")) {
                key = key.substring(1, key.length());
                int v = st.get(key);
                st.delete(key);
                StdOut.printf("key: %s, value: %d deleted.\n", key, v);
            }
            else {
                int v = value++;
                st.put(key, v);
                StdOut.printf("key: %s, value: %d put.\n", key, v);
            }
        }

        StdOut.println(st.size() + " item(s) in the symbol table.");
        for(String k : st.keys()) {
            if (st.contains(k)) {
                StdOut.printf("key: %s, value: %d.\n", k, st.get(k));
            }
        }
    }
}
