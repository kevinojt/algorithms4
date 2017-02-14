package me.alivecode.algs4;

import java.util.ArrayList;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


/* 
 * Represent a selection sort algorithm.
 */
public class Selection {
    private Selection() {}

    /**
     * Sort a in accending order.
     *
     * @param a the array to sort.
     */
    public static void sort(Comparable[] a) {
        // find the smallest item in a and move it to the front.
        for(int i = 0; i < a.length; i++) {
            int  min = i;
            for(int j=i+1; j < a.length; j++) {
                if (less(a[j],a[min])) {
                    min = j;
                }
            }         
            exch(a, i, min);
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        while(!StdIn.isEmpty()) {
            list.add(StdIn.readString());
        }
        String[] a = new String[list.size()];
        list.toArray(a);
        StdOut.println("Before being sorted.");
        for(String s: a) {
            StdOut.println(s);
        }
        Selection.sort(a);
        StdOut.println("After being sorted.");
        for(String s: a) {
            StdOut.println(s);
        }
    }
         
}            
