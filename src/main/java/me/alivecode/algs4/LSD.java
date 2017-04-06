package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code LSD} class provides static methods for
 * sorting string array and 32-bit integer array with
 * Least-Significant-Digit-first radix sort.
 */
public class LSD {
    private static final int BITS_PER_BYTE = 8;

    private LSD() {}

    /**
     * Rearranges the string array in ascending order.
     * All strings in the given array must have same length.
     * @param a the array to be sorted
     * @throws IllegalArgumentException if strings
     * in the array has different length
     */
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

    /**
     * Rearranges strings in the given array in ascending order.
     * @param a the given string array.
     *          All strings must longer than or equal to w
     * @param w <em>w</em>th to 0 characters to be compared
     */
    public static void sort(String[] a, int w) {
        final int n = a.length;
        final int R = 256; // for extended ascii

        for(int i = 0; i < n; i++) {
            if (a[i].length() < w) {
                throw new IllegalArgumentException("Length of strings in a[] must longer than or equal to " + w);
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

    /**
     * Rearranges the array of 32-bit integer in ascending order.
     * This is about 2x-3x faster than Arrays.sort()
     * @param a the array to be sorted
     */
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

            // 0x8000-0xFFFF is less than 0x0000-0x7FFF in
            // the left most byte of signed integers
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

    // unit test code
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
