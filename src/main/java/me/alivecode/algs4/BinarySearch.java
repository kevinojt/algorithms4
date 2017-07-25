package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;

public class BinarySearch{
    
    private BinarySearch() {}

    /**
     * Returns the index of the {@code key} if
     * the {@code key} is in the array {@code arr}.
     * @param arr the sorted array
     * @param key the key
     * @return the index of the {@code key}
     */
    public static int indexOf(int[] arr, int key) {
        int lo = 0;
        int hi = arr.length - 1;

        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < arr[mid]) { hi = mid - 1; }
            else if (key > arr[mid]) { lo = mid + 1; }
            else return mid;
        }
        return -1;
    }

    /**
     * Returns the number of elements that are smaller
     * than the {@code key}.
     * @param arr the sorted array
     * @param key the key
     * @return the number of elements that are smaller
     * than the {@code key}
     */
    public static int rank(int[] arr, int key) {
        int lo = 0;
        int hi = arr.length - 1;
        int mid = 0;
        while(lo <= hi) {
            mid = lo + (hi - lo) / 2;
            if (key < arr[mid]) {
                hi = mid - 1;
            }
            else if (key > arr[mid]) {
                lo = mid + 1;
            }
            else {
                // deals with duplicate keys
                while (mid - 1>= 0 && arr[mid-1] == key) {
                    mid--;
                }
                return mid;
            }
        }

        return lo;
    }

    /**
     * Return the number of elements that equals to the {@code key}.
     * @param arr the sorted array
     * @param key the key
     * @return the number of elements that equals to the {@code key}
     */
    public static int count(int[] arr, int key) {
        int i = indexOf(arr, key);
        if (i == -1) return 0;

        // NOTE: with array {0, 1, 1, 1, 2}
        // indexOf returns 2 of key 1
        // arr[1, 2, 3] equal to key 1

        int count = 0;
        int j = i;
        // deal duplicate keys
        do {
            count++;
        }
        while (++j < arr.length && arr[j] == key);

        j = i;
        do {
            count++;
        }
        while (--j >= 0 && arr[j] == key);

        return count-1;
    }

    public static void main(String[] args) {

        // read the integers from a file
        // In in = new In(args[0]);
        int[] whitelist = {0, 0, 2, 2, 2, 4, 6};//in.readAllInts();

        // sort the array
        Arrays.sort(whitelist);

        // read integer key from standard input; print if not in whitelist
        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            int count = BinarySearch.rank(whitelist, key);
            StdOut.println(count);
        }
    }

}

