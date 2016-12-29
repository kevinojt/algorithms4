package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdIn;

public class Quick3way {
    
    private Quick3way() {}

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int i = lo + 1; 
        int lt = lo, gt = hi;
        Comparable v = a[lo];

        while(i <= gt) {
            int cmp = a[i].compareTo(v);
            // lt always points to the left most item valued 'v'. 
            // Move smaller items to the left side of 'v'.
            // This operation inserted an item to the left side of lt and i, 
            // increase lt and i to give one slot to that item.
            if (cmp < 0) { SortUtil.exch(a, lt++, i++); } 
            // Move larger items to the right side of 'v' 
            else if (cmp > 0) { SortUtil.exch(a, i, gt--); }
            // current item equal to 'v', move to the next item. 
            else { i++; }
        }

        sort(a, lo, lt-1);
        sort(a, gt+1, hi);

        assert SortUtil.isSorted(a, lo, hi);
    } 

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
        assert SortUtil.isSorted(a);
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        SortUtil.show(a);
        sort(a);
        SortUtil.show(a);
    }
}
