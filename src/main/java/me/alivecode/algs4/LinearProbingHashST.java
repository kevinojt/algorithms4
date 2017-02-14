package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code LinearProbingHashST} class represents 
 * a linear probing hash key-value pair symbol table.
 */
public class LinearProbingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;
    private int m; // number of hash keys.
    private int n; // number of key-value pairs.
    private Key[] keys;
    private Value[] values;

    /**
     * Initialize an empty symbol table.
     */
    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    /**
     * Initialize an empty symbol with capacity of m.
     *
     * @param m inintialize capacity.
     */ 
    public LinearProbingHashST(int m) {
        keys = (Key[]) new Object[m];
        values = (Value[]) new Object[m];
        this.m = m;
        this.n = 0;
    }

    /**
     * Returns the number of key-value pairs in the symbol table.
     *
     * @return the number of key-value pairs.
     */
    public int size() {
        return n;
    }

    /**
     * Does the symbol have the given key.
     *
     * @return {@code true} if the given is in the symbol table
     * or {@code false} if not.
     * @throws IllegalArgumentException if the {@code key} is {@code null}.
     */ 
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains is null.");
        return get(key) != null;
    }

    /**
     * Is the symbol empty?
     *
     * @return {@code true} if the symbol table is empty 
     * or {@code false} if not.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the value associated with the specified key if the key is in the symbol table.
     * Or null if the key is not in the symbol table.
     *
     * @param key the key.
     * @return the value associated with the specified key.
     * throws IllegalArgumentException if the {@code key} is {@code null}.
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null.");
        
        for(int i = hash(key); keys[i] != null; i = (i+1)%m) {
            if (key.equals(keys[i])) return values[i];
        }
        return null;
    }
    
    /**
     * Insert the key-value pair into the symbol table if the specified key
     * is not in the symbol table. 
     * Update the associated value if the key is already in the symbol table.
     * Delete the key and associated value from the symbol table if the key is {@code null}.
     *
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

        if (n >= m/2) resize(2*m);
        
        int i = hash(key);
        // finds the existing key and update its associated value.
        for(; keys[i] != null; i = (i+1) % m) {
            if (key.equals(keys[i])) {
                values[i] = value;
                return;
            }
        }

        // the given key is not in the hash table
        // insert it and its associated key into hash table.
        keys[i] = key;
        values[i] = value;
        n++;
    }

    /**
     * Delete the key and associated value from the symbol table.
     *
     * @param key the key.
     * @throws IllegalArgumentException if the {@code key} is {@code null}. 
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null.");

        if (!contains(key)) return;

        int i = hash(key);
        while(!key.equals(keys[i])) {
            i = (i+1) % m;
        }

        keys[i] = null;
        values[i] = null;
        n --;

        i = (i+1) % m;
        while(keys[i] != null) {
            Key keyToRehash = keys[i];
            Value valueToRehash = values[i];
            keys[i] = null;
            values[i] = null;
            n--;
            put(keyToRehash, valueToRehash);
            i = (i+1) % m;
        }

        if (n <= m/8) {
            resize(m/2);
        }

        assert check();
    }

    /** 
     * Returns all keys in the symbol table.
     *
     * @return all keys in the symbol table.
     */
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();

        for (int i=0; i < m; i++) {
            if (keys[i] != null) {
                queue.enqueue(keys[i]);
            }
        }
        return queue;
    }
 
    
    // resizes the hash table to the given capacity and rehashes all keys.
    private void resize(int capacity) {
        LinearProbingHashST<Key, Value> st = new LinearProbingHashST<Key, Value>(capacity);

        for(int i = 0; i < m; i++) {
            if (keys[i] != null) {
            st.put(keys[i], values[i]);
            }
        }

        this.m = st.m;
        this.n = st.n;
        this.keys = st.keys;
        this.values = st.values;
    }

    private boolean check() {
        if (n > m/2) {
            StdOut.println("Hash table m = " + m + ". Array size n = " + n + ".");
            return false;
        }

        for(int i = 0; i < m; i++) {
            if (keys[i] == null) continue;
            if (!values[i].equals(get(keys[i]))) {
                StdOut.println("Get " + keys[i] + " = " + get(keys[i]) + "; values[i] = " + values[i]);
                return false;
            }
        }
        
        return true;
    }

    // hash function for keys. 
    // Returns value between 1 ~ m-1.
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }
    
    // unit test code.
    public static void main(String[] args) {
        LinearProbingHashST<String, String> st = new LinearProbingHashST<String, String>();
        String key;
        String value;

        while(!StdIn.isEmpty()) {
            key = StdIn.readString();
            if (key.startsWith("-")) {
                key = key.substring(1, key.length());
                value = st.get(key);
                st.delete(key);
                StdOut.printf("key: %s, value: %s deleted.\n", key, value);
            }
            else {
                value = key;
                st.put(key, value);
                StdOut.printf("key: %s, value: %s put.\n", key, value);
            }
        }

        StdOut.println(st.size() + " item(s) in the hash table.");
        for(String k : st.keys()) {
            StdOut.printf("key: %s, value: %s.\n", k, st.get(k));
        }
    }

}    


