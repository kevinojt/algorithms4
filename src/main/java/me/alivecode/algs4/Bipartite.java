package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * The {@code Bipartite} class represents a data type for
 * determining if the specified graph is bipartite.
 */
public class Bipartite {
    private boolean[] marked;
    private boolean[] color;
    private boolean isBipartite;
    private int[] edgeTo;
    private Stack<Integer> oddCycle;
    /**
     * Computes if the specified graph is bipartite.
     *
     * @param G the specified graph.
     */
    public Bipartite(Graph G) {
        marked = new boolean[G.V()];
        color = new boolean[G.V()];
        edgeTo = new int[G.V()];
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

            if (oddCycle != null) return;

            if (!marked[w]) {
                color[w] = !color[v];
                edgeTo[w] = v;
                dfs(G, w);
            }
            else if(color[w] == color[v]) {
                isBipartite = false;
                // The sub connected component containing w
                // must be a cycle with odd edges
                // if it is not bipartite.
                oddCycle = new Stack<>();
                oddCycle.push(w);
                for(int x = v; x != w; x = edgeTo[x]) {
                    oddCycle.push(x);
                }
                oddCycle.push(w);
            }
        }
    }

    /**
     * Returns the cycle if the graph is not bipartite,
     * {@code null} otherwise.
     * The graph must has an cycle containing odd edges
     * if it is not bipartite.
     *
     * @return the odd cycle if the graph is not bipartite,
     * or {@code null} otherwise.
     */
    public Iterable<Integer> cycle() {
        return oddCycle;
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
        if (!bp.isBipartite()) {
            for(int x : bp.cycle()) {
                StdOut.print(x + " ");
            }
            StdOut.println();
        }
    }
}
