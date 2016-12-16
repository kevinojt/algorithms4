package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdIn;

public class Quick {

    private Quick() {}

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        
        while(true) {
            // find item no lo to swap
            while(SortUtil.less(a[++i], v)) { 
                if (i >= hi) break;
            }
            // find item on hi to swap
            while(SortUtil.less(v, a[--j])) {
                if (j <= lo) break;  // randundant since a[lo] acts as sentinel. BUT WHY?
            }
            
            if (i >= j) break; // check if pointers cross.
            SortUtil.exch(a, i, j);
        }
        SortUtil.exch(a, lo, j);

        return j;
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
        assert SortUtil.isSorted(a, lo, hi);
    }

    public static void sort(Comparable[] a) {
        assert (a != null && a.length > 0);
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
        assert SortUtil.isSorted(a);
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        sort(a);
        SortUtil.show(a);
    }
} 
