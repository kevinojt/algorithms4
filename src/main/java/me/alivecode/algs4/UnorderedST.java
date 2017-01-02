package me.alivecode.algs4;

import java.util.NoSuchElementException;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Unordered symbol table.
 */
public class UnorderedST<Key, Value> {
    private int size;
    private Node first;
    
    public UnorderedST () {
        size = 0;
        first = null; 
    }

    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("key must not null.");
        Node tmp = first;
        while(tmp != null && !tmp.key.equals(key)) {
            tmp = tmp.next;
        }
        if (tmp == null) {
            Node n = new Node(key, value);
            n.next = first;
            first = n;
            size++;
        }
        else if (value != null) {
             tmp.value = value;
        }
        else {
            delete(key);
        }
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("key must not null.");
        Node tmp = first;
        while(tmp != null) {
            if (tmp.key.equals(key)) return tmp.value;
            tmp = tmp.next;
        }
        return null;
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("key must not null.");
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow.");
        // Check if there is only one node and its key equals to the given key. 
        if (first != null && first.next == null && first.key.equals(key)) {
            first = null; 
            size = 0;
            return;
        }
        
        Node tmp = first;
        while(tmp != null) {
            if (tmp.next != null && tmp.next.key.equals(key)) {
                tmp.next = tmp.next.next; // delete node if its key equals to the given key.
                size--;
                return;
            }
            tmp = tmp.next;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
 
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("key must not null.");
        Node tmp = first;
        while (tmp != null) {
            if (tmp.key.equals(key)) return true;
            tmp = tmp.next;
        }
        return false;
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        Node tmp = first;
        while(tmp != null) {
            queue.enqueue(tmp.key);
            tmp = tmp.next;
        }
        return queue;
    }

    private class Node {
        Key key;
        Value value;
        Node next;
        
        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.next = null;
        } 
    }

    public static void main(String[] args) {
        UnorderedST<String, String> ust = new UnorderedST<String, String>();

        String key = null;
        String value = null;

        while(!StdIn.isEmpty()) {
            if (key == null) {
                key = StdIn.readString();
                if (key.startsWith("-")) {
                    key = key.substring(1, key.length());
                    StdOut.printf("delete node, key: %s, value: %s.\n", key, ust.get(key));
                    ust.delete(key);
                    key = null;
                }
            }
            else {
                value = StdIn.readString();
                StdOut.printf("put new node, key: %s, value: %s.\n", key, value);
                ust.put(key, value);
                key = null;
                value = null;
            }
        }
        
        StdOut.printf("There are %d item(s).\n", ust.size());
        for(String k : ust.keys()) {
            StdOut.printf("key: %s, value: %s.\n", k, ust.get(k));
        }
    }
}
