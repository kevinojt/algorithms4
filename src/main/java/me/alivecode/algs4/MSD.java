package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code MSD} class provides static methods for sorting
 * array of extended ASCII strings and integers with MSD radix sort.
 */
public class MSD {
    private static final int BITS_PER_BYTE = 8;
    private static final int BITS_PER_INT = 32;
    private static final int CUTOFF = 15;   // cutoff to insertion sort
    private static final int R = 256;       // extended ASCII alphabet size

    private MSD() {}

    /**
     * Rearranges the array of extended ASCII strings
     * in ascending order.
     * @param a the array to be sorted
     */
    public static void sort(String[] a) {
        String[] aux = new String[a.length];
        sort(a, 0, a.length-1, 0, aux);
    }

    // Likes quick sort, MSD recursively sorts partitions
    // MSD uses R partitions
    private static void sort(String[] a, int lo, int hi, int d, String[] aux) {

        // cutoff to insertion
        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }

        int[] count = new int[R + 2];
        // compute frequency count
        for(int i = lo; i <= hi; i++) {
            count[charAt(a[i], d) + 2]++;
        }

        // transform counts to indicies
        for(int r = 0; r < R+1; r++) {
            count[r+1] += count[r];
        }

        // distribute
        for(int i = lo; i <= hi; i++) {
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        }

        // copy back
        for(int i = lo; i <= hi; i++) {
            a[i] = aux[i - lo];
        }

        // recursively sort each characters (excludes sentinel - 1)
        for(int r = 0; r < R + 1; r++) {
            sort(a, lo + count[r], lo + count[r+1]-1, d + 1, aux);
        }
    }

    // insertion sort
    private static void insertion(String[] a, int lo, int hi, int d) {
        for(int i = lo; i <= hi; i++) {
            for(int j = i; j > lo && less(a[j], a[j-1], d); j--) {
                SortUtil.exch(a, j, j-1);
            }
        }
    }

    // compare two strings from dth character to the end
    private static boolean less(String v, String w, int d) {
        assert v.substring(0, d).equals(w.substring(0, d));
        for(int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (charAt(v, i) < charAt(w, i)) return true;
            if (charAt(v, i) > charAt(w, i)) return false;
        }
        return (v.length() < w.length());
    }

    private static int charAt(String s, int d) {
        if (d < s.length()) {
            return s.charAt(d);
        }
        return  -1;
    }

    /**
     * Rearranges array of integers in ascending order.
     * @param a the array to be sorted
     */
    public static void sort(int[] a) {
        int n = a.length;
        int[] aux = new int[n];
        sort(a, 0, n-1, 0,aux);
    }

    private static void sort(int[] a, int lo, int hi, int d, int[] aux) {

        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }

        // computes frequency counts (R needs to be 256)
        int[] count = new int[R+1];
        int mask = R - 1; // 255;
        int shift = BITS_PER_INT - BITS_PER_BYTE * d - BITS_PER_BYTE;
        for(int i = lo; i <= hi; i++) {
            int c = (a[i] >> shift) & mask;
            count[c+1]++;
        }

        // transforms counts to indicies
        for(int r = 0; r < R; r++) {
            count[r+1] += count[r];
        }

        for(int i = lo; i <= hi; i++) {
            int c = (a[i] >> shift) & mask;
            aux[count[c]++] = a[i];
        }

        for(int i = lo; i <= hi; i++) {
            a[i] = aux[i - lo];
        }

        if (d == 4) return;;

        if (count[0] > 0) {
            sort(a, lo, lo + count[0] - 1, d + 1, aux);
        }
        for (int r = 0; r < R; r++) {
            if (count[r + 1] > count[r]) {
                sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
            }
        }

    }

    private static void insertion(int[] a, int lo, int hi, int d) {
        for(int i = lo; i <= hi; i++) {
            for(int j = i; j > 0 && a[j] < a[j-1]; j--) {
                exch(a, j, j-1);
            }
        }
    }

    private static void exch(int[] a, int v, int w) {
        int tmp = a[v];
        a[v] = a[w];
        a[w] = tmp;
    }

    // unit test code
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

        StdOut.println("Sorting integers-------------------------------------");
        int ai[] = {0xFFFF, 0x80000000, 0x7FFFFFFF, 0xFFFFFFFF};

        sort(ai);

        for(int i : ai) {
            StdOut.println(i);
        }
    }
}
