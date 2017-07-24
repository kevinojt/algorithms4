package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by oujt on 15/04/2017.
 */
public class KMP {
    private final int R;
    private int[][] dfa;

    private String pat;
    private char[] pattern;

    public KMP(String pat) {
        this.R = 256; // extended ascii
        this.pat = pat;
        int m = pat.length();

        dfa = new int[R][m];
        dfa[pat.charAt(0)][0] = 1;

        for(int x = 0, j = 1; j < m; j++) {
            for(int c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][x];
            }
            dfa[pat.charAt(j)][j] = j + 1;
            x = dfa[pat.charAt(j)][x];
        }

    }

    public KMP(char[] pattern, int R) {
        this.R = R;
        int m = pattern.length;

        this.pattern = new char[m];
        for(int j = 0; j < m; j++) {
            this.pattern[j] = pattern[j];
        }

        dfa = new int[R][m];
        dfa[pattern[0]][0] = 1;
        for(int x = 0, j = 1; j < m; j++) {
            for(int c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][x];
            }
            dfa[pattern[j]][j] = j + 1;
            x = dfa[pattern[j]][x];
        }

    }

    public int search(String txt) {
        int m = pat.length();
        int n = txt.length();
        int i, j;
        for(i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[txt.charAt(i)][j];
        }
        if (j == m) return i - m;
        else return n;
    }

    public int search(char[] txt) {
        int m = pattern.length;
        int n = txt.length;

        int i, j;
        for(i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[txt[i]][j];
        }

        if (j == m) return i - m;
        else return n;
    }

    public static void main(String[] args) {
        int R = 65536;
        StdOut.print("Please enter the pattern: ");
        String pat = StdIn.readLine();

        KMP kmp = new KMP(pat/*.toCharArray(), R*/);

        do {
            StdOut.println("Please txt to be searched:");
            String txt = StdIn.readLine();
            int n = txt.length();

            if (n == 0) break;

            int l = kmp.search(txt/*.toCharArray()*/);
            if (l == n) {
                StdOut.println("No match");
            }
            else {
                StdOut.printf("found %s at %d\n", pat, l);
            }
        }
        while (true);
    }
}
