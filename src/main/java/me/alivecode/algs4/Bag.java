package me.alivecode.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code Bag> class represents a bag (or multiset);
 * It support insertion and iterating over the items in the bag.
 */
public class Bag<Item> implements Iterable<Item> {
    private int n; // number of items.
    private Node first;

    private class Node {
        Item item;
        Node next;
        public Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    /**
     * Initialize an empty bag.
     */
    public Bag() {
        n = 0;
        first = null;
    }

    /**
     * Returns the number of items in the bag.
     *
     * @return the number of items in the bag.
     */
    public int size() {
        return n;
    }

    /**
     * Is the bag empty?
     *
     * @return {@code true} if the bag is empty
     * or {@code false} if not.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Insert item into the bag.
     *
     * @param item the item.
     * @throws IllegalArgumentException if the {@code item} is {@code null}.
     */
    public void put(Item item) {
        if (item == null) throw new IllegalArgumentException("argument to put() is null.");
        first = new Node(item, first);
        n++;
    }

    /**
     * Returns an iterator that iterates over items in the bag in arbitrary order.
     *
     * @return an iterator that iterates over items in the bag in arbitrary order.  
     */
    public Iterator<Item> iterator() {
        return new BagIterator(first);
    }

    // an iterator that iterates over the items in the bag in arbitrary order.
    private class BagIterator implements Iterator<Item> {
        private Node current;

        public BagIterator(Node node) {
            this.current = node;
        }

        public boolean hasNext() {
           return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item t = current.item;
            current = current.next;
            return t;
        }
    }

    // unit test code
    public static void main(String[] args) {
        Bag<String> bag = new Bag<String>();
        while(!StdIn.isEmpty()) {
            String s = StdIn.readString();
            bag.put(s);
        }

        StdOut.println("Size of the bag is " + bag.size() + ".");
        for(String s: bag) {
            StdOut.println(s);
        }
    }
}

