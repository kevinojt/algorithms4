package me.alivecode.algs4;

import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class StdRandom {
    private StdRandom() {}

    public static void shuffle(Object[] a) {
        int N = a.length;
        Random random = new Random();
        for(int i = 0; i < N; i++) {
            int r = random.nextInt(i + 1);
            SortUtil.exch(a, i, r);
        }
    }


    public static void main(String[] args) {
        Integer[] a = new Integer[52];
        for(int i = 0; i < a.length; i++) {
            a[i] = i + 1;
        }
        shuffle(a);
        for(int i = 0; i < a.length; i++) {
           StdOut.printf("%4d", a[i]);
        }
        StdOut.println(); 
    }

}
