package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class UF {
    private int[] parent;
    private int[] rank;
    private int count;

    public UF(int n) {
        parent = new int[n];
        rank = new int[n];
        for(int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
        count = n;
    }

    public int count () { return count; }

    public void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IndexOutOfBoundsException("Index " + p + " is not between 0 and " + (n-1) + ".");
        }
    }

    public int find(int p) {
        validate(p);
        while(p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }
    // Print the structure for testing reason.
    void printStructure(){
        int n = parent.length;
        StdOut.printf("%-8s", "site:");
        for(int i = 0; i < n; i++) {
            StdOut.printf("%4d", i);
        }
        StdOut.println();
        StdOut.printf("%-8s", "linked:");
        for(int i = 0; i < n; i++) {
            StdOut.printf("%4d", parent[i]);
        }
        StdOut.println();
        StdOut.printf("%-8s", "rank:");
        for(int i = 0; i < n; i++) {
            StdOut.printf("%4d", rank[i]);
        }
        StdOut.println();
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q){
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) { return; }
        if (rank[pRoot] > rank[qRoot]) {
            parent[qRoot] = pRoot;
        }
        else if (rank[pRoot] < rank[qRoot]) {
            parent[pRoot] = qRoot;
        }
        else {
            parent[pRoot] = qRoot;
            rank[qRoot]++;
        }
        count--;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        UF uf = new UF(n);
        uf.printStructure();
        while(!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) { continue; }
            uf.union(p, q);
            uf.printStructure();
            StdOut.println(p + " + " + q);
        }
        uf.printStructure();
        StdOut.println(uf.count() + " components.");
    }
}

   
