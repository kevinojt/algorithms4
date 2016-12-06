package me.alivecode.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Stack<Item> implements Iterable<Item> {
    private Node first;
    private int size;

    private class Node {
        Item item;
        Node next;
    }
    public Stack() {
        first = null;
        size = 0;
    }

    public int size() { return size; }

    public boolean isEmpty() { return first == null; }

    public Item peek() {
        if (isEmpty()) { throw new NoSuchElementException("Stack underflow"); }     
        return first.item; 
    }

    public Item pop() {
        if (isEmpty()) { throw new NoSuchElementException("Stack underflow"); }
        Node oldfirst = first;
        first = first.next;
        size--;
        return oldfirst.item;
    }

    public void push(Item item) {
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = first;
        first = newNode;
        size++; 
    }

    public Iterator<Item> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null; }

        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }
            Item item = current.item;
            current = current.next;
            return item;
        }   
    }
    
    public static void main(String[] args) {
        Stack<String> stack = new Stack<String>();
        while(!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                stack.push(item);
            }
            else {
                StdOut.println(stack.pop() + " ");
            }
        }
        StdOut.println(stack.size() + " item(s) in the stack.");
        for(String item : stack) {
            StdOut.println(item);
        }
        
    }
}
