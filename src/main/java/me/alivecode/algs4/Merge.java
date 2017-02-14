package me.alivecode.algs4;

import java.util.ArrayList;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Merge {
    private Merge() {}

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert SortUtil.isSorted(a, lo, mid);
        assert SortUtil.isSorted(a, mid + 1, hi);
        // copy a to aux
        for(int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo;
        int j = mid + 1;

        for(int k = lo; k <= hi; k++) {
            if (i > mid) { a[k] = aux[j++]; }
            else if (j > hi) { a[k] = aux[i++]; }
            else if (SortUtil.less(aux[i], aux[j])) { a[k] = aux[i++]; }
            else { a[k] = aux[j++]; }
        }

        assert SortUtil.isSorted(a, lo, hi);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }


    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        while(!StdIn.isEmpty()) {
            list.add(StdIn.readString());
        }
        String[] a = new String[list.size()];
        list.toArray(a);
        sort(a);
        SortUtil.show(a);
    }
}
        



