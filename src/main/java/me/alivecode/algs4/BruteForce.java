package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by oujt on 14/04/2017.
 */
public class BruteForce {

    public static int search(String pat, String txt) {
        int N = pat.length();
        int M = txt.length();

        for (int i = 0; i <= M - N; i++) {
            int j;
            for (j = 0; j < N; j++) {
                if (txt.charAt(i + j) != pat.charAt(j)) break;
            }
            if (j == N) return i;
        }
        return M;
    }

    public static int search1(String pat, String txt) {
        int j, P = pat.length();
        int i, T = txt.length();

        for(i = 0, j = 0; i < T && j < P; i++) {
            if (txt.charAt(i) == pat.charAt(j)) j++;
            else { i -= j; j = 0; } // explicit backup
        }

        if (j == P) return i - P;
        else return T;
    }

    public static void main(String[] args) {
        String txt = "She sells seashells by the sea shore. The shells she sells are surely seashells.";

        while(!StdIn.isEmpty()) {
            String pat = StdIn.readString();
            int i = search1(pat, txt);
            if (i < txt.length()) {
                StdOut.printf("Index: %d, pattern found: %s\n", i, txt.substring(i, i + pat.length()));
            }
            else {
                StdOut.println("No match");
            }
        }

    }
}
