package me.alivecode.algs4;

import java.util.ArrayList;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Insertion {
    private Insertion() {}

    public static void sort(Comparable[] a) {
        for (int i = 0; i < a.length; i++)
            for(int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                exch(a, j, j-1);
            }
    }

    private static boolean less(Comparable a, Comparable b) {
        return (a.compareTo(b) < 0);
    }

    public static void exch(Comparable a[], int i, int j) {
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
        
        StdOut.println("Before being sorted");
        for(String s : a) {
            StdOut.println(s);
        }
        StdOut.println("After being sorted");
        Insertion.sort(a);
        for(String s : a) {
            StdOut.println(s);
        }
    }

}


