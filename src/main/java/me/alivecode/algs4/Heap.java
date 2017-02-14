package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
/**
 * Heap ordered queue for sorting.
 */
public class Heap {
    private Heap() {}

    /* 
     * Heap ordered queue is one-based. 
     * Heap sort uses zero-based array and 
     * SortUtil's helper methods take zero-based array. 
     * So we should off-by-one when passing heap queue's keys to thoes methods. 
     */

    private static void sink(Comparable[] a, int n, int k) {
        while(k*2<= n) {
            int j = k * 2;
            if (j < n && SortUtil.less(a[j-1], a[j])) j++;
            if (!SortUtil.less(a[k-1], a[j-1])) break;
            SortUtil.exch(a, k-1, j-1);
            k = j;
        }
    }

    public static void sort(Comparable[] a) {
        int n = a.length;
        for(int k = n/2; k >= 1; k--) {
            sink(a, n, k);
        }
        while(n > 1) {
            // Move the max key to the end of array.
            SortUtil.exch(a, --n, 0);
            sink(a, n, 1);
        }
        assert SortUtil.isSorted(a);
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        sort(a);
        SortUtil.show(a);
    }
}
