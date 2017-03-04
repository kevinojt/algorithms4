package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code IndexMinPQ} represents a priority queue which its keys associated
 * with index.
 */
public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
    private int[] pq; // 1 based priority queue
    private int[] qp; // reverse of pq - qp[pq[i]] = pq[qp[i]] = i. pq[i] is the index of index i in pq
    private Key[] keys; // key associated with index i. keys[i] = key
    private int n; // number of items in pq
    private int maxN; // maximum number of item in qp

    /**
     * Initializes a priority queue with at most {@code maxN} items.
     *
     * @param maxN maximum number of items in the priority queue.
     */
    public IndexMinPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException();

        this.maxN = maxN;
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];
        for(int i = 0; i <= maxN; i++) {
            qp[i] = -1;
        }
        keys = (Key[]) new Comparable[maxN + 1];
    }

    /**************************************
     *
     *  Helper functions
     *
     *
     *************************************/
    //i, j are indexes of pq. NOT indexes associated with keys.
    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }
    //i, j are indexes of pq. NOT indexes associated with keys.
    private void exch(int i, int j) {
        int tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;

        qp[pq[i]] = i ;
        qp[pq[j]] = j;
    }
    //i, j are indexes of pq. NOT indexes associated with keys.
    private void swim(int k) {
        while(k > 1 && greater(k / 2, k)) {
            exch(k, k/2);
            k = k / 2;
        }
    }
    //k is index of pq. NOT index associated with keys.
    private void sink(int k) {
        while (k * 2 <= n) {
            int j = 2 * k;
            if (j < n && greater(j, j+1)) j++;
            // exchange item k with it's small child.
            if (!greater(k, j)) break;;
            exch(k, j);
            k = j;
        }
    }

    // i index associated with keys
    private void validateIndex(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException("index " + i + " is not between 0 and " + maxN);
    }

    /**
     * Is index {@code i} in the priority queue?
     * @param i the index
     * @return {@code true} if index {@code i} in the priority queue,
     * or {@code false} otherwise
     *
     * @throws IndexOutOfBoundsException if the specified index {@code i}
     * not between 0 and <em>maxN</em>
     */
    public boolean contains(int i) {
        validateIndex(i);
        return qp[i] != -1;
    }

    /**
     * Is the priority empty?
     *
     * @return {@code true} if the priority is empty,
     * or {@code false} otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the size of the priority queue.
     * @return the size of the priority queue.
     */
    public int size() {
        return  n;
    }

    /**
     * Inserts {@code key} associated with {@code i} into the priority queue.
     * @param i the index
     * @param key the key.
     */
    public void insert(int i, Key key) {
        validateIndex(i);
        if (contains(i)) throw new IllegalArgumentException("index " + i + " is already in the priority queue");

        n++;
        keys[i] = key;
        pq[n] = i;
        qp[i] = n;
        swim(n);
    }

    /**
     * Returns the index of the minimum key.
     * @return the index of the minimum key
     */
    public int minIndex() {
        // !contains(pq[1]) equals to n == 0
        if (isEmpty()) throw new NoSuchElementException("priority queue underflow");
        return pq[1];
    }

    /**
     * Returns the minimum key.
     * @return the minimum key
     */
    public Key minKey() {
        if (isEmpty()) throw new NoSuchElementException("priority queue underflow");
        return keys[pq[1]];
    }

    /**
     * Returns the key associated with index {@code i}.
     * @param i the index
     * @return the key associated with {@code i}
     */
    public Key keyOf(int i) {
        validateIndex(i);
        if (!contains(i)) throw new NoSuchElementException("index " + i + " is not in the priority queue");
        return keys[i];
    }

    /**
     * Removes the minimum key from the priority key
     * and returns the index associated with the minimum key.
     *
     * @return the index associated with the minimum key.
     */
    public int delMin() {
        if (isEmpty()) throw new NoSuchElementException("priority queue underflow");

        int min = pq[1];
        exch(1, n--);
        sink(1);
        assert min == pq[n+1];
        pq[n+1] = -1;
        qp[min] = -1;
        keys[min] = null;
        return min;

    }

    /**
     * Removes the key associated with index {@code i} from the priority queue.
     *
     * @param i the index.
     */
    public void delete(int i) {
        validateIndex(i);
        if (!contains(i)) throw new NoSuchElementException("index " + i + " is not in the priority queue");
        int indexOfiInPQ = qp[i]; // qp[i] is the index of index i in the pq.
        exch(indexOfiInPQ, n--); // remove index i from the priority queue
        swim(indexOfiInPQ);
        sink(indexOfiInPQ);
        keys[i] = null;
        qp[i] = -1;
    }

    /**
     * Changes the key associated with index {@code i} to the specified {@code key}.
     * @param i the index
     * @param key the new key
     */
    public void changeKey(int i, Key key) {
        validateIndex(i);
        if (!contains(i)) throw new NoSuchElementException("index " + i + " is not in the priority queue");

        keys[i] = key;
        swim(qp[i]);
        sink(qp[i]);
    }


    /**
     * Decreases the key associated with index {@code i} to the specified value {@code key}.
     * @param i the index
     * @param key the new value. The new value must less than the old value
     */
    public void decreaseKey(int i, Key key) {
        validateIndex(i);
        if (!contains(i)) throw new NoSuchElementException("index " + i + " is not in the priority queue");
        if (keys[i].compareTo(key) <= 0)
            throw new IllegalArgumentException("Calling decreaseKey() with the given key is not strictly decrease the key");

        keys[i] = key;
        swim(qp[i]);
    }

    /**
     * Increase the key associated with index {@code i} to the specified value {@code key}.
     * @param i the index
     * @param key the new value. The new value must greater thn the old value.
     */
    public void increaseKey(int i, Key key) {
        validateIndex(i);
        if (!contains(i)) throw new NoSuchElementException("index " + i + " is not in the priority queue");
        if (keys[i].compareTo(key) >= 0)
            throw new IllegalArgumentException("Calling increaseKey() with the given key is not strictly increase the key");

        keys[i] = key;
        sink(qp[i]);
    }

    /**
     * Returns an iterator that iterates over the keys of
     * the priority queue in ascending order.
     *
     * @return an iterator that iterates over the keys
     * in ascending order.
     */
    @Override
    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Integer> {
        private IndexMinPQ<Key> copy;
        public HeapIterator() {
            copy = new IndexMinPQ<Key>(maxN);
            for (int i = 0; i < maxN; i++) {
                if (qp[i] != -1)
                copy.insert(i, keys[i]);
            }
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public Integer next() {
            return copy.delMin();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit test code;
    public static void main(String[] args) {
        String[] keys = {"It", "is", "a", "good", "day", "to", "code."};
        IndexMinPQ<String> pq = new IndexMinPQ<>(keys.length);

        for(int i = 0; i < keys.length; i++) {
            pq.insert(i, keys[i]);
        }

        for(int j : pq) {
            StdOut.printf("%d %s\n", j, pq.keyOf(j));
        }

        StdOut.println("a->A, It->it");
        pq.decreaseKey(2, "A");
        pq.increaseKey(0, "it");

        for(int j : pq) {
            StdOut.printf("%d %s\n", j, pq.keyOf(j));
        }

        StdOut.println("delete minimum items one by one");
        while (!pq.isEmpty()) {
            int index = pq.minIndex();
            StdOut.printf("%d %s\n", index, pq.keyOf(index));
            pq.delMin();
        }
    }

}
