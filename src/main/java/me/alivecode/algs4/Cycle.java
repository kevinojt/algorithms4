package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code Cycle} represents a data type
 * for detecting if there are any cycles in
 * the specified graph.
 */
public class Cycle {
    private boolean[] marked;
    private Stack<Integer> cycle;
    private int[] edgeTo;

    public Cycle(Graph G) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];

        if (hasSelfLoop(G)) return;
        if (hasParallelPath(G)) return;
        for(int s = 0; s < G.V(); s++) {
            if (!marked[s]) {
                dfs(G, s, -1);
            }
        }
    }

    // v: v w1...wn represents self loop
    private boolean hasSelfLoop(Graph G) {
        for(int v = 0; v < G.V(); v++) {
            for(int w: G.adj(v)) {
                if (w == v) {
                    cycle = new Stack<Integer>();
                    cycle.push(v);
                    cycle.push(v);
                    return true;
                }
            }
        }
        return false;
    }

    // v: w1 w1 w2 ... wn represents parallel path
    private boolean hasParallelPath(Graph G) {
        for(int v = 0; v < G.V(); v++) {
            for(int w: G.adj(v)) {
                if (marked[w]) {
                    cycle = new Stack<Integer>();
                    cycle.push(v);
                    cycle.push(w);
                    cycle.push(v);
                    return false;
                }
            }

            for(int w: G.adj(v)) {
                marked[w] = false;
            }
        }

        return false;
    }

    private void dfs(Graph G, int v, int s) {
        marked[v] = true;
        for(int w : G.adj(v)) {
            if (cycle != null) return;
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w, v);
            }
            else if (w != s) {
                cycle = new Stack<Integer>();
                int x;
                for(x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(x);
                cycle.push(v);
            }
        }
    }

    /**
     * Does the specified graph have cycle?
     *
     * @return {@code true} if the graph has cycle or {@code false} otherwise.
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Returns the first detected cycle in the graph.
     *
     * @return the first detected cycle in the graph.
     */
    public Stack<Integer> cycle() {
        return cycle;
    }

    // unit test code
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        Cycle c = new Cycle(G);
        if (c.hasCycle()) {
            for(int x: c.cycle()) {
                StdOut.print(x + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("no cycle in the graph");
        }
    }
}


