package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code SeparateChainingHashST} class represents a key-value pair 
 * symbol table with separate chaining hash method.
 */ 
public class SeparateChainingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    private SequentialSearchST<Key, Value>[] st; 
    private int m;
    private int n;

    /**
     * Initialize an empty symbol table.
     */
    public SeparateChainingHashST() {
        this(INIT_CAPACITY);
    }

    /**
     * Intialize an empty symbol that has @{cdoe m} keys.
     */
    public SeparateChainingHashST(int m) {
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[m];
        for(int i = 0; i < m; i++) {
            st[i] = new SequentialSearchST<Key, Value>();
        }
        n = 0;
        this.m = m;
    }

    // hash value between 0 & m-1;
    private int hash(Key key) {
        return (key.hashCode() & 0x7FFFFFFF) % m;
    }

    /**
     * Insert key-value pair into the symbol table.
     * @param key the key.
     * @param value the value.
     * @throws IllegalArgumentException if the {@code key} is {@code null}.
     */
    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null.");

        if (value == null) {
            delete(key);
            return;
        }

        // double table size if average list length >= 10.
        if (n >= 10*m) {
            resize(2 * m);
        }

        int i = hash(key);
        if (!st[i].contains(key)) {
            n++;
        }
        st[i].put(key, value);
    }
    
    /**
     * Remove the specified key and its associated value from the symbol table.
     *
     * @param key the key.
     * @throws IllegalArgumentException if the {@code key} is {@code null}.
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("arguemnt to delete() is null.");
        
        int i = hash(key);
        if (st[i].contains(key)) {
            st[i].delete(key);
            n--;
        }
        
        if (m > INIT_CAPACITY && n <= 2*m) {
            resize(m / 2);
        }

    }

    // Resize the hash table to have the given number of chains.
    // Rehash all keys.
    private void resize(int chains) {
        SeparateChainingHashST<Key, Value> tmp = new SeparateChainingHashST<Key, Value>(chains);
        for(int i = 0; i < m; i++) {
            for(Key k : st[i].keys()) {
                tmp.put(k, st[i].get(k));
            }
        }

        this.m = tmp.m;
        this.n = tmp.n;
        this.st = tmp.st;
    }

    /**
     * Returns the value associcated with the given key.
     *
     * @param key the key.
     * @return the value associated with the given key.
     * @throws IllegalArgumentException if the {@code key} is {@code null}.
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null.");

        int i = hash(key);
        return st[i].get(key);
    }

    /**
     * Returns the number of key-value pairs in the symbol table.
     * 
     * @return the number of the key-value pairs in the symbol table.
     */
    public int size() {
        return n;
    }

    /**
     * Is the symbol empty.
     *
     * @return {@code true} if the symbol table is empty
     * or {@code false} if not.
     */
    public boolean isEmpty() {
        return (size() == 0);
    }
    
    /**
     * Does the symbol table have the given key in it?
     *
     * @return {@code true} if the {@code key} is in the symbol table
     * or {@code false} if not.
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains is null.");

        return get(key) != null;
    }

    /**
     * Returns all keys in the symbol table.
     *
     * @return all keys in the symbol table.
     */
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++) {
            for(Key k : st[i].keys()) {
                queue.enqueue(k);
            }
        }

        return queue;
    }

    // Unit test code.
    public static void main(String[] args) {
        String key = null;
        int value = 0;

        SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();

        while (!StdIn.isEmpty()) {
            key = StdIn.readString();
            if (key.startsWith("-")) {
                key = key.substring(1, key.length());
                int v = st.get(key);
                st.delete(key);
                StdOut.printf("key: %s, value: %d deleted.\n", key, v);
            }
            else {
                st.put(key, value++);
                StdOut.printf("key: %s, value: %d put.\n", key, st.get(key));
            }
        }

        StdOut.println(st.size() + " item(s) in the symbol table.");
        for(String k : st.keys()) {
            StdOut.printf("key: %s, value: %d.\n", k, st.get(k));
        }
    }

}

