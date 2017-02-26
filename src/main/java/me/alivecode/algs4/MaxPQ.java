package me.alivecode.algs4;

import java.util.Iterator;
import java.util.Comparator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code MaxPQ} represents a heap ordered priority queue.
 */
public class MaxPQ<Key> implements Iterable<Key> {
    Key[] pq;
    int N;
    Comparator cmp;

    public MaxPQ(int capacity, Comparator cmp) {
        pq = (Key[])(new Object[capacity+1]);
        N = 0;
        this.cmp = cmp;
    }

    public MaxPQ() {
        this(1, null);
    }

    public MaxPQ(Comparator cmp) {
        this(1, cmp);
    }

    public MaxPQ(int capacity) {
        this(capacity, null);
    }

    public MaxPQ(Key[] keys) {
        this(keys, null);
    }

    public MaxPQ(Key[] keys, Comparator cmp) {
        N = keys.length;
        this.cmp = cmp;
        pq = (Key[]) new Object[keys.length+1];
        for (int i = 0; i < keys.length; i++) {
            pq[i+1] = keys[i];
        }
        for(int k = N/2; k >= 1; k--) {
           sink(k);
        } 
        assert isMaxHeap();
    }

    /**
     * move kth key up to the right place.
     */
    private void swim(int k) {
        // exchange kth key with its parent if it is larger than its parent.
        // k/2 is its parent.
        while(k > 1 && less(k/2, k)) {
            exch(k/2, k);
            k = k / 2;
        }
    }

    /**
     * move kth key down to the right place.
     */
    private void sink(int k) {
        // exchange kth key with its larger children 
        // k*2 is its left child and k*2+1 is its right child. 
        while(2*k <= N)  {
            int j = 2*k;
            if (j < N && less(j, j+1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
    
    private void exch(int i, int j) {
        Key tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }

    private boolean less(int i, int j) {
        if (cmp != null) return cmp.compare(pq[i], pq[j]) < 0;
        return ((Comparable)pq[i]).compareTo(pq[j]) < 0;
    }

    private boolean isMaxHeap() {
        return isMaxHeap(1);
    }
    
    /**
     * Check if all children's keys are smaller than their parent's key. 
     */
    private boolean isMaxHeap(int k) {
        if (k > N) return true;

        int left = 2 * k;
        int right = 2 * k + 1;
        if (left <= N && less(k, left)) return false;
        if (right <= N && less(k, right)) return false;

        return isMaxHeap(left) && isMaxHeap(right);
    }
    
    private void resize(int capacity) {
        if (capacity <= N - 1) return;
        Key[] tmp = (Key[]) new Object[capacity];
        for(int i = N; i >= 1; i--) {
           tmp[i] = pq[i];
        }
        pq = tmp;
    } 

    /* 
     * Helper class to iterates over keys in this priority.
     */
    private class HeapIterator implements Iterator<Key> {
        private MaxPQ<Key> copy;
        
        public HeapIterator() {
            // Construct copy with arrays is much more faster
            // than by insert key into copy one by one.
            // Copy pg to new arr off-by-one because pg is one-based. 
            Key[] keys = (Key[])new Object[pq.length-1];
            for(int i = 1; i < pq.length; i++) {
                keys[i-1] = pq[i];
            }
            copy = new MaxPQ<Key>(keys, cmp);
        }

        public boolean hasNext() {
            return !copy.isEmpty(); 
        }

        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMax();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /*
     * Return an iterator that iterates over keys on this priority queue
     * in decending order.
     *
     * Did not implements remove method.
     *
     * @return an iterator than iterates over keys in decending order.
     */
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    /* 
     * Insert new key into this priority queue.
     *
     * @param key the key to be added.
     */
    public void insert(Key key) {
        if (N >= pq.length-1) resize(pq.length * 2);  

        pq[++N] = key;
        swim(N);
        assert isMaxHeap();
    }

    /*
     * Remove the max key from this priority queue.
     *
     * @return the max key in this priority queue.
     */
    public Key delMax() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue is underflow.");

        Key key = pq[1];
        exch(1, N);
        pq[N--] = null;
        sink(1);
        if (N == (pq.length-1) / 4 && pq.length > 4) resize(pq.length / 2);   
        assert isMaxHeap();
        return key;
    }

    /* 
     * Check if this priority is empty.
     *
     * @return true if this priority is empty or false if is not empty.
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /*
     * Return the current max key.
     *
     * @return the current max key.
     */ 
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue is underflow.");
        return pq[1];
    }

    /* 
     * Return the current number of keys in this priority queue.
     *
     * @return the size of this priority queue.
     */
    public int size() {
        return N; 
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        MaxPQ<String> pq = new MaxPQ<String>(a);
        while(!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-")) {
                StdOut.println("The max key is " + pq.delMax());
            }
            else {
                pq.insert(s);
            }
        }
        StdOut.printf("%d item(s) in the queue.\n", pq.size());
        for(String s : pq) { 
            StdOut.println(s);
        }
    }
}
            

