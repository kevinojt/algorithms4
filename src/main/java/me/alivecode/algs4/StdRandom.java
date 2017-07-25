package me.alivecode.algs4;

import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class StdRandom {
    private static Random random;
    private static long seed;

    private StdRandom() {}

    static {
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    public static void shuffle(Object[] a) {
        int N = a.length;
        for(int i = 0; i < N; i++) {
            int r = random.nextInt(i + 1);
            SortUtil.exch(a, i, r);
        }
    }

    /**
     * Returns a random integer uniformly in [0, n].
     * @param n an integer in [0, n]
     * @return a random integer uniformly in [0, n]
     */
    public static int uniform(int n) {
        if (n <= 0) throw new IllegalArgumentException("argument must >= 0");
        return random.nextInt(n);
    }

    /**
     * Returns a random real number uniformly in [0.0, n].
     * @param n a real number in [0.0, n]
     * @return a real number uniformly in [0.0, n]
     */
    public static double uniform(double n) {
        if (n <= 0) throw new IllegalArgumentException("argument must >= 0.0");
        return random.nextDouble() * n;
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
