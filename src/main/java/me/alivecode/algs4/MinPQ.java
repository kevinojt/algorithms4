package me.alivecode.algs4;

import java.util.*;

/**
 * The {@code MinPQ} class represents an priority queue
 * which is minimum first.
 * For maximum first priority queue see {@link MaxPQ}.
 */
public class MinPQ<Key> implements Iterable<Key>{
    private Key[] pq;
    private int N;
    //private Comparator<Key> comparator;

    /**
     * Initializes an empty minimum priority queue.
     */
    public MinPQ() {
        this(4);
    }

    /**
     * Initializes an empty minimum priority queue
     * with capacity of {@code initCapacity}.
     *
     * @param initCapacity the initialized capacity.
     */
    public MinPQ(int initCapacity) {
        this.pq = (Key[]) new Object[initCapacity + 1];
        N = 0;
    }

    /**
     * Initializes an priority queue with {@code keys}.
     *
     * @param keys the keys.
     */
    public MinPQ(Key[] keys) {
        N = keys.length;
        pq = (Key[]) new Objects[N+1];
        for(int i = 0; i < keys.length; i++) {
            pq[i+1] = keys[i];
        }

        for(int k = N/2; k >= 1; k--) {
            sink(k);
        }
        assert isMinHeap(1);
    }

    /**
     * Inserts the {@code key} to the queue.
     *
     * @param key the key.
     */
    public void insert(Key key) {
        if (N == pq.length - 1) resize(pq.length * 2);
        pq[++N] = key;
        swim(N);
        assert isMinHeap(1);
    }

    /**
     * Returns the smallest key in the queue.
     *
     * @return the smallest key in the queue.
     */
    public Key min(){
        if (isEmpty()) throw new NoSuchElementException("Priority heap underflow");
        return pq[1];
    }

    /**
     * Is the queue empty?
     *
     * @return {@code true} if the queue is empty,
     * {@code false} otherwise.
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Returns the number of keys in the queue.
     *
     * @return the number of keys in the queue.
     */
    public int size() {
        return N;
    }

    /**
     * Remove the smallest key from the queue and returns it.
     *
     * @return the smallest key.
     */
    public Key delMin() {
        if (N == 0) throw new NoSuchElementException("Priority heap underflow");

        exch(1, N);
        Key min = pq[N--];
        sink(1);
        pq[N+1] = null;
        if (N > 0 && N == (pq.length-1) / 4) resize(pq.length/2 + 1);
        return min;
    }

    // move the kth key up to where it is larger than its children and
    // smaller than its parent.
    private void swim(int k) {
        while(k > 1) {
            int j = k / 2;
            if (!greater(j, k)) break;
            exch(k, j);
            k = j;
        }
    }

    // move the kth key down to where it is smaller than its parent
    // and larger than its children.
    private void sink(int k) {
       while(2*k <= N) {
           int j = 2*k;
           // j=left child, j+1=right child.
           if (j < N && greater(j, j+1)) j++;
           if (!greater(k, j)) break;;
           exch(k, j);
           k = j;
       }
    }

    private void exch(int j, int k) {
        Key tmp = pq[j];
        pq[j] = pq[k];
        pq[k] = tmp;
    }

    private boolean greater(int l, int r) {
        if (((Comparable)pq[l]).compareTo(pq[r]) == 1) return true;
        else return false;
    }

    // Check if kth key and its children in heap order.
    private boolean isMinHeap(int k) {
        if (k > N) return true;

        int left = 2*k;
        int right = 2*k + 1;
        if (left <= N && greater(k, left)) return false;
        else if (right <= N && greater(k, right)) return false;
        else return isMinHeap(left) && isMinHeap(right);
    }

    private void resize(int capacity) {
        assert capacity > N;
        Key[] tmp = (Key[])new Object[capacity];

        /*
        for (int i = 1; i <= N; i++) {
            tmp[i] = pq[i];
        }
        */
        System.arraycopy(pq, 1, tmp, 1, N);
        pq = tmp;
    }

    public Iterator<Key> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Key> {
        private MinPQ<Key> copy;

        public ListIterator() {
            Key[] keys = (Key[]) new Object[pq.length-1];
            // pq is one-based
            for(int i = 1; i < pq.length; i++) {
                keys[i-1] = pq[i];
            }
            copy = new MinPQ<Key>(keys);
        }

        public boolean hasNext() {
            return copy.isEmpty();
        }

        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
