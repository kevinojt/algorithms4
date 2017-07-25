package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code EdgeWeightedDirectedCycle} class determines
 * cycle in a {@link EdgeWeightedDigraph}.
 */
public class EdgeWeightedDirectedCycle {
    private final boolean[] marked; // marked[v] = has vertex v been marked ?
    private final DirectedEdge[] edgeTo; // edgeTo[v] = previous edge on path to v
    private final boolean[] onStack; // onStack[v] = is v on the path?
    private Stack<DirectedEdge> cycle; // the directed cycle or null if no cycle

    /**
     * Determines the directed cycle in the specified edge-weighted digraph {@code G}.
     *
     * @param G the edge-weighted graph
     */
    public EdgeWeightedDirectedCycle(EdgeWeightedDigraph G) {
        marked = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeTo = new DirectedEdge[G.V()];

        for(int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }


    }

    // see DirectedCycle class for ref
    private void dfs(EdgeWeightedDigraph G, int v) {
        marked[v] = true;
        onStack[v] = true;

        for(DirectedEdge e : G.adj(v)) {
            int w = e.to();

            if (cycle != null) return;

            if (!marked[w]) {
                edgeTo[w] = e;
                dfs(G, w);
            }
            else if (onStack[w]) {
                cycle = new Stack<>();
                DirectedEdge x = e;
                while(x.from() != w) {
                    cycle.push(x);
                    x = edgeTo[x.from()];
                }
                cycle.push(x);
            }

        }
        onStack[v] = false;
    }

    /**
     * Is the specified edge-weighted digraph has any cycle?
     *
     * @return {@code true} if the specified edge-weighted digraph has cycle,
     * {@code false} otherwise
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Returns the directed cycle if the specified edge-weighted digraph
     * has cycle, {@code null} otherwise.
     *
     * @return the directed cycle if the specified edge-weighted
     * has cycle, {@code null} otherwise
     */
    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }

    // unit test code
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);

        if (finder.hasCycle()) {
            for (DirectedEdge e : finder.cycle()) {
                StdOut.print(e + "   ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("no cycle");
        }
    }

}
