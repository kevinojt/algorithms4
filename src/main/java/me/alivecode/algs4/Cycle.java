package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Cycle {
    private boolean isCyclic;
    private boolean[] marked;

    public Cycle(Graph G) {
        isCyclic = false;
        marked = new boolean[G.V()];

        for(int s = 0; s < G.V(); s++) {
            if (!marked[s]) {
                dfs(G, s, s);
            }
        }
    }

    private void dfs(Graph G, int v, int s) {
        StdOut.printf("dfs v:%d, s:%d\n", v, s);
        marked[v] = true;
        for(int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w, v);
            }
            else if (w != s) {
                StdOut.printf("v:%d, w:%d, s:%d\n", v, w, s);
                isCyclic = true;
            }
        }
    }

    public boolean isCyclic() {
        return isCyclic;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        Cycle c = new Cycle(G);
        StdOut.println(c.isCyclic());
    }
}


