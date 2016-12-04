package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;

public class BinarySearch{
    
    private BinarySearch() {}

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

    public static void main(String[] args) {

        // read the integers from a file
        In in = new In(args[0]);
        int[] whitelist = in.readAllInts();

        // sort the array
        Arrays.sort(whitelist);

        // read integer key from standard input; print if not in whitelist
        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            if (BinarySearch.indexOf(whitelist, key) == -1)
                StdOut.println(key);
        }
    }

}

