package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdOut;

/**
 * Created by oujt on 17-4-5.
 */
public class LSD {
    private static final int BITS_PER_BYTE = 8;

    private LSD() {}

    public static void sort(String[] a) {
        final int n = a.length;
        final int w = a[0].length();

        for(int i = 1; i < n; i++) {
            if (a[i].length() != w) {
                throw new IllegalArgumentException("All strings in a[] must have same length");
            }
        }

        sort(a, w);
    }

    public static void sort(String[] a, int w) {
        final int n = a.length;
        final int R = 256; // for extended ascii

        for(int i = 0; i < n; i++) {
            if (a[i].length() < w) {
                throw new IllegalArgumentException("Length of strings in a[] must longer than equal to " + w);
            }
        }

        String[] aux = new String[n];

        for(int d = w - 1; d >= 0; d--) {
            int[] count = new int[R+1];

            for(int i = 0; i < n; i++) {
                count[a[i].charAt(d) + 1]++;
            }

            for(int r = 0; r < R; r++) {
                count[r+1] += count[r];
            }

            for(int i = 0; i < n; i++) {
                aux[count[a[i].charAt(d)]++] = a[i];
            }

            for(int i = 0; i < n; i++) {
                a[i] = aux[i];
            }
        }
    }

    public static void sort(int[] a) {
        final int BITS = 32;
        final int w = BITS / BITS_PER_BYTE;
        final int R = 256;
        final int mask = R - 1;
        final int n = a.length;
        int[] aux = new int[n];

        for(int d = 0; d < w; d++) {
            int[] count = new int[R+1];

            for(int i = 0; i < n; i++) {
                int c = (a[i] >> (BITS_PER_BYTE * d)) & mask;
                count[c + 1]++;
            }

            for(int r = 0; r < R; r++) {
                count[r+1] += count[r];
            }

            if (d == w - 1) {
                int shift1 = count[R] - count[R/2];
                int shift2 = count[R/2];

                for(int r = 0; r < R/2; r++) {
                    count[r] += shift1;
                }

                for(int r = R/2; r < R; r++) {
                    count[r] -= shift2;
                }
            }

            for(int i = 0; i < n; i++) {
                int c = (a[i] >> (BITS_PER_BYTE * d)) & mask;
                aux[count[c]++] = a[i];
            }

            for(int i = 0; i < n; i++) {
                a[i] = aux[i];
            }

        }
    }

    public static void main(String[] args) {

        StdOut.println("Sorting integer-------------------------------------");
        int[] a = {0xFFFF, 0xFFFFFFFF, 0x80000000, 0x7FFFFFFF };
        sort(a);
        for(int i : a) {
            StdOut.println(i);
        }

        StdOut.println("Sorting string--------------------------------------");
        String[] as = {"her", "and", "his", "dog", "cat", "hen", "are", "big"};
        sort(as);
        for(String s : as) {
            StdOut.println(s);
        }
    }
}
