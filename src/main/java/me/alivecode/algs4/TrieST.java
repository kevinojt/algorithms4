package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code TrieST} represents a symbol table
 * of string key and generic value.
 * It uses Trie data structure to store strings and search.
 */
public class TrieST<Value> {
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
    public void put(String key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) throw new IllegalArgumentException("second argument to put() is null");
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) { x = new Node(); }
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
    public Value get(String key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        Node x = get(root, key, 0);
        if (x != null) {
            @SuppressWarnings("unchecked")
            Value val = (Value) x.val;
            return val;
        }
        else {
            return null;
        }
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
     * Returns all keys in the symbol table in ascending order.
     * @return all keys in the symbol table in ascending order
     */
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<>();
        collect(root, queue, new StringBuilder());
        return queue;
    }

    /**
     * Returns all keys start with {@code prefix} in the symbol table in ascending order.
     * @param prefix the prefix
     * @return all keys start with {@code prefix} in the ascending order
     */
    public Iterable<String> keysWithPrefix(String prefix) {
        if (prefix == null) throw new IllegalArgumentException("argument to keysWithPrefix() is null");

        Node x = get(root, prefix, 0);
        Queue<String> queue = new Queue<>();
        collect(x, queue, new StringBuilder(prefix));
        return queue;
    }

    private void collect(Node x, Queue<String> queue, StringBuilder prefix) {
        if (x == null) return;

        if (x.val != null) {
            queue.enqueue(prefix.toString());
        }

        for(char r = 0; r < R; r++) {
            prefix.append(r);
            collect(x.next[r], queue, prefix);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    /**
     * Returns key in the symbol table that is the longest prefix of {@code query},
     *
     * @param query
     * @return
     */
    public String longestPrefixOf(String query) {
        if (query == null) throw new IllegalArgumentException("argument to longestPrefixOf() is null");
        int l = search(root, query, 0, -1);
        if (l == -1) return null;
        else return query.substring(0, l);
    }

    private int search(Node x, String query, int d, int length) {
        if (x == null) return length;
        if (x.val != null) length = d;
        if (query.length() == d) return query.length();

        int c = charAt(query, d);
        return search(x.next[c], query, d + 1, length);
    }

    /**
     * Returns all keys that match the given {@code pattern},
     * where the . symbol is treated as wildcard character.
     * @param pattern the pattern
     * @return all keys that match the given {@code pattern}
     */
    public Iterable<String> keysThatMatch(String pattern) {
        if (pattern == null) throw new IllegalArgumentException("argument to keysThatMatch() is null");
        Queue<String> results = new Queue<>();
        collect(root, new StringBuilder(), pattern, results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length() && x.val != null) {
            results.enqueue(prefix.toString());
        }
        if (d == pattern.length()) {
            return;
        }

        char c = pattern.charAt(d);
        if (c == '.') {
            for(char r = 0; r < R; r++) {
                prefix.append(r);
                collect(x.next[r], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        else {
            prefix.append(c);
            collect(x.next[c], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
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
            x.next[c] = delete(x.next[c], key, d+1);
        }

        if (x.val != null) return x;
        for(int r = 0; r < R; r++) {
            if (x.next[r] != null) return x;
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
        private Object val;
        private final Node[] next = new Node[R];
    }

    // test code
    public static void main(String[] args) {
        TrieST<String> trie = new TrieST<>();

        String[] a = {"she", "sells", "seashells", "by", "the", "sea", "shore",
                        "the", "shells", "she", "sells", "are", "surely", "seashells"};

        for(String s : a) {
            trie.put(s, s);
        }

        for(String k : trie.keys()) {
            String v = trie.get(k);
            if (!k.equals(v)) {
                StdOut.printf("value not matches key. key: %s, value: %s\n", k, trie.get(k));
            }
        }

        trie.put("seashell","seashell");

        StdOut.println("----Delete seashells-------------------");
        trie.delete("seashells");
        StdOut.printf("key: %s, value: %s\n", "sea", trie.get("sea"));

        StdOut.println("----Delete sea--------------------------");
        trie.delete("sea");
        StdOut.printf("key: %s, value: %s\n", "sea", trie.get("sea"));
        StdOut.printf("key: %s, value: %s\n", "seashell", trie.get("seashell"));

        StdOut.println("----Delete seashell---------------------");
        trie.put("sea", "sea");
        trie.delete("seashell");
        StdOut.printf("key: %s, value: %s\n", "sea", trie.get("sea"));
        StdOut.printf("key: %s, value: %s\n", "seashell", trie.get("seashell"));


        StdOut.println("----test keysWithPrefix-----------------");
        for (String k : trie.keysWithPrefix("sh")) {
            StdOut.printf("key: %s, value: %s\n", k, trie.get(k));
        }

        StdOut.println("----test longestPrefixof-----------------");
        trie.put("seashells", "seashells");
        StdOut.println(trie.longestPrefixOf("seashore"));

        StdOut.println("----test keysThatMatch-------------------");
        for (String k : trie.keysThatMatch("s...l.")) {
            StdOut.println(trie.get(k));
        }


    }

}
