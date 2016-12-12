package me.alivecode.algs4;

import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Shell {
    private Shell() {}

    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N/3) { h = 3*h + 1; }
        while(h >= 1) {
            for(int i = h; i < N; i++) {
                for(int j = i; j >= h && SortUtil.less(a[j], a[j-h]); j-=h) {
                    SortUtil.exch(a, j, j-h);
                }
            }
            h = h/3;
        }
        assert SortUtil.isSorted(a);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] a = in.readAllStrings();
        StdOut.println("Read "+ a.length + " items");
        sort((Comparable[])a);
        StdOut.println("After being sorted.");
        SortUtil.show(a);
    }
}
