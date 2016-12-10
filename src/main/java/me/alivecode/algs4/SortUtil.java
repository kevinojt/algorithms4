package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdOut;

class SortUtil {
    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0; 
    }

    public static void exch(Object[] a, int i, int j) {
        Object tmp = a[i];
        a[i]= a[j];
        a[j] = tmp;
    }

    public static boolean isSorted(Comparable[] a) {
        for(int i = 1; i < a.length; i++) {
            if (less(a[i], a[i-1])) { return false; }
        }
        return true;
    }

    public static void show(Object[] a) {
        for(Object c : a) {
            StdOut.println(c);
        }
    }
}
