package me.alivecode.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class ResizingArrayStack<Item> implements Iterable<Item> {
    private int size;
    private Item[] arr;

    public ResizingArrayStack() {
        arr = (Item[])new Object[2];
        size = 0; 
    }

    public int size() { return size; }

    public Item pop() {
        if (isEmpty()) { throw new NoSuchElementException("Stack Underflow"); }

        Item temp = arr[size-1];
        arr[size-1] = null; // to avoid loitering
        size--;
        
        if (size > 0 && size == arr.length / 4) { resize(arr.length / 2); }
        return temp;
    }

    public void push(Item item) {
        if (size == arr.length) { resize(arr.length * 2); }
        arr[size] = item;
        size++;
    }

    public Item peek() {
        if (isEmpty()) { throw new NoSuchElementException("Stack Underflow."); }

        return arr[size-1];
    }

    private void resize(int capacity) {
        assert capacity > size;

        Item[] temp = (Item[]) new Object[capacity];
        // Copy items to new the new array. 
        for(int i = 0; i < size; i++) {
            temp[i] = arr[i];
        }
        arr = temp;
    }

    public boolean isEmpty() { return size == 0; }

    public Iterator<Item> iterator() { return new ArrayIterator(); }

    private class ArrayIterator implements Iterator<Item> {
        private int current = size - 1;

        public boolean hasNext() { return current >= 0; }

        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException("Stack Underflow"); }
            Item temp = arr[current];
            current--;
            return temp;
        }

        public void remove() { throw new UnsupportedOperationException(); }
    }



    public static void main(String[] args) {
        ResizingArrayStack<String> stack = new ResizingArrayStack<String>();
        while(!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("-")) {
                StdOut.println(stack.pop());
            }
            else {
                stack.push(item);
            }
        }
        StdOut.println(stack.size() + " item(s) in the stack.");
        for(String item: stack) {
            StdOut.println(item);
        }
    }
 }
