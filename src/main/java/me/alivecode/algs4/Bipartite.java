package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code Bipartite} class represents a data type for
 * determining if the specified graph is bipartite.
 */
public class Bipartite {
    private boolean[] marked;
    private boolean[] color;
    private boolean isBipartite;

    /**
     * Computes if the specified graph is bipartite.
     *
     * @param G the specified graph.
     */
    public Bipartite(Graph G) {
        marked = new boolean[G.V()];
        color = new boolean[G.V()];
        isBipartite = true;

        for(int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;

        for(int w : G.adj(v)) {
            if (!marked[w]) {
                color[w] = !color[v];
                dfs(G, w);
            }
            else if(color[w] == color[v]) {
                isBipartite = false;
            }
        }
    }

    /**
     * Is the specified bipartite?
     *
     * @return {@code true} if the specified is bipartite, {@code false} otherwise.
     */
    public boolean isBipartite() {
        return isBipartite;
    }

    // unit test code.
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        Bipartite bp = new Bipartite(G);
        StdOut.println("The specified graph is" + (bp.isBipartite() ? " " : " not ") + "bipartite.");
    }
}
