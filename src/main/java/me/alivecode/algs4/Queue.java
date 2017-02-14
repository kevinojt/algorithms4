package me.alivecode.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// Common queue structure: first->other nodes->last->null.
// Only one node in the queue: first(last)->null.
// Dequeue when there is only one node in the queue: set first=first.next, set last=null.
// Enqueue when there the queue is empty: set last=new created node, set first=first.
public class Queue<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
    }

    public Queue() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() { return first == null; }

    public int size() { return size; }

    public Item peek() {
        if (isEmpty()) { throw new NoSuchElementException("Empty queue"); }
        return first.item;
    }

    public void enqueue(Item item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) { 
            first = last; // Only one node in the queue now, the first and last node are same. 
        }
        else {
            oldLast.next = last; // Add last added node to the queue.
        }
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) { throw new NoSuchElementException("Empty queue"); }
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null; // The queue is empty, the last node should be null.
        }
        size--;
        return item;
    }

    public Iterator<Item> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null; }

        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException("Empty Queue"); }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Queue<String> queue = new Queue<String>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                queue.enqueue(item);
            }
            else {
                StdOut.println(queue.dequeue());
            }
        }
        StdOut.println(queue.size() + " item(s) in the queue.");
        for(String item: queue) {
            StdOut.println(item);
        }
    }
}
