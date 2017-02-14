package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

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
                if (j <= lo) break;  // randundant since a[lo] acts as sentinel. v=a[lo], will never less than a[lo]; 
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
        StdRandom.shuffle(a); // a random array is critical for performance.
        sort(a, 0, a.length-1);
        assert SortUtil.isSorted(a);
    }

    // find the kth smallest item in an unordered array.
    // The array divided into 2 part, a[lo, j-1] < a[j] < a[j+1, hi]
    // and the kth smallest item will fall in one of the two part or it is just a[j].
    public static Comparable selection(Comparable[] a, int k) {
        int lo = 0;
        int hi = a.length - 1;
        StdRandom.shuffle(a);
        while(hi > lo) { 
            int j = partition(a, lo, hi);
            if (j > k) {
                hi = j - 1;
            }
            else if (j < k) {
                lo = j + 1;
            }
            else {
               return a[k]; // a[k] is not in the left part nor right part. It is the kth smallest item.
            }
        }
        return a[k];
    } 

            
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        sort(a);
        SortUtil.show(a);
        int k = Integer.parseInt(args[0]);
        selection(a, k);
        StdOut.printf("The %dth smallest item is %s\n", k, a[k]);

    }
}
