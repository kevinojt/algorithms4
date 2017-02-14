package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class WeightedQuickUnionUF {
    private int[] parent;
    private int[] size;
    private int count;

    public WeightedQuickUnionUF(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        count = n;
    }

    public int count() {
        return count;
    }

    public void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IndexOutOfBoundsException("Index " + p + " is not between 0 and " + (n - 1) + ".");
        }
    }

    public int find(int p) {
        validate(p);
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) {
            return;
        }
        if (size[pRoot] <= size[qRoot]) {
            parent[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        } else {
            parent[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
        }
        count--;
    }

    // Print the structure for testing reason.
    void printStructure(){
        int n = parent.length;
        StdOut.printf("%-8s", "site:");
        for(int i = 0; i < n; i++) {
            StdOut.printf("%4d", i);
        }
        StdOut.printf("\n%-8s", "linked:");
        for(int i = 0; i < n; i++) {
            StdOut.printf("%4d", parent[i]);
        }
        StdOut.printf("\n%-8s", "size:");
        for(int i = 0; i < n; i++) {
            StdOut.printf("%4d", size[i]);
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);
        uf.printStructure();
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) {
                continue;
            }
            uf.union(p, q);
            uf.printStructure();
            StdOut.println(p + " + " + q);
        }
        uf.printStructure();
        StdOut.println(uf.count() + " components.");
    }
}
