package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code Quick3string} class provides method for
 * sorting array of strings with quick 3 way sort.
 */
public class Quick3string {
    // cutoff to insertion
    private final static int CUTOFF = 5;

    private Quick3string() {}

    /**
     * Rearranges array of strings in ascending order.
     * @param a the array to be sorted
     */
    public static void sort(String[] a) {
        sort(a, 0, a.length-1, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        // cutoff to insertion
        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }

        int v = charAt(a[lo], d);
        int i = lo + 1;
        int lt = lo, gt = hi;

        while (i <= gt) {
            int t = charAt(a[i], d);
            if (t < v) SortUtil.exch(a, lt++, i++);
            else if (t > v) SortUtil.exch(a, i, gt--);
            else            i++;
        }

        // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
        // the dth character of strings between lt and gt
        // are equal. sort with dth+1 character.
        sort(a, lo, lt-1, d);
        if (v >= 0) sort(a, lt, gt, d+1);
        sort(a, gt+1, hi, d);

    }

    private static void insertion(String[] a, int lo, int hi, int d) {
        for(int i = lo; i <= hi; i++) {
            for(int j = i; j > lo && less(a[j], a[j-1], d); j--) {
                SortUtil.exch(a, j, j-1);
            }
        }
    }

    private static boolean less(String v, String w, int d) {
        assert v.substring(0, d).equals(w.substring(0, d));
        for(int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (charAt(v, i) < charAt(w, i)) return true;
            if (charAt(v, i) > charAt(w, i)) return false;
        }

        return v.length() < w.length();
    }

    private static int charAt(String s, int d) {
        if (d < s.length()) {
            return s.charAt(d);
        }
        return -1;
    }

    // test code
    public static void main(String[] args) {
        StdOut.println("Sorting strings--------------------------------------");
        String[] a = {
                "she", "sells", "seashells", "by", "the", "sea", "shore",
                "the", "shells", "she", "sells", "are", "surely", "seashells"
        };

        sort(a);

        for(String s : a) {
            StdOut.println(s);
        }
    }

    /*************************************************************************************
     *  For the input of
     *  she sells seashells by the sea shore the shells she sells are surely seashells
     *  After the first been sorted with quick 3 way sort the output is
     *  by are seashells she ... sells sells the the
     *  lt = 2, gt = 11
     */

}
