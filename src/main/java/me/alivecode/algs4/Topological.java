package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code Topological} class a data type for
 * computing the topological order of a directed acyclic graph.
 */
public class Topological {
    private Iterable<Integer> order;
    private int[] rank; // rank[v] = topological order of v

    /**
     * Computes the topological order of the specified directed graph.
     *
     * @param G the directed graph.
     */
    public Topological(Digraph G) {
        DirectedCycle cycle = new DirectedCycle(G);

        if (!cycle.hasCycle()) {
            DepthFirstOrder finder = new DepthFirstOrder(G);
            order = finder.reversePostOrder();
            int i = 0;
            rank = new int[G.V()];
            for(int v: order) {
                rank[v] = i++;
            }
        }
    }

    /**
     * Computes the topological order of the specified EDAG.
     *
     * @param G the acyclic edge-weighted digraph
     */
    public Topological(EdgeWeightedDigraph G) {
        EdgeWeightedDirectedCycle cycle = new EdgeWeightedDirectedCycle(G);

        if (!cycle.hasCycle()) {
            DepthFirstOrder dfo = new DepthFirstOrder(G);
            order = dfo.reversePostOrder();
        }
    }

    /**
     * Returns the order of the specified vertex {@code v}.
     *
     * @param v the vertex.
     * @return the order of vertex {@code v} if the specified
     * directed graph is acyclic, or -1 otherwise
     */
    public int rank(int v) {
        validateVertex(v);
        if (hasOrder()) {
            return rank[v];
        }
        return -1;
    }

    private void validateVertex(int v) {
        if (!hasOrder()) return; // make sure rank has been initialized.
        if (v < 0 || v >= rank.length) throw new IllegalArgumentException();
    }

    /**
     * Is the specified directed graph has topological order?
     *
     * @return {@code true} if the specified directed graph has topological order,
     * or {@code false} otherwise.
     */
    public boolean hasOrder() {
        return order != null;
    }

    /**
     * Returns vertices in topological order.
     *
     * @return vertices in topological order
     * if the specified directed graph has topological order,
     * or {@code null} otherwise.
     */
    public Iterable<Integer> order() {
        return order;
    }

    // unit test code.
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph dag = new Digraph(in);
        Topological order = new Topological(dag);
        if (order.hasOrder()) {
            StdOut.print("topological order:");
            for(int v : order.order()) {
                StdOut.print(" " + v);
            }
            StdOut.println();
        }
        else {
            StdOut.println("has no order");
        }

    }
}
