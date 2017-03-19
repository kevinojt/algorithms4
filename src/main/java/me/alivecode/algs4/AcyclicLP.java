package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code AcyclicLP} class determines the longest path
 * in acyclic edge-weighted digraphs.
 *
 */
public class AcyclicLP {
    private final DirectedEdge[] edgeTo;
    private final double[] distTo;

    /**
     * Determines longest path in the specified digraph {@code G}.
     *
     * @param G the digraph
     * @param s the source vertex
     */
    public AcyclicLP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        for(int v = 0; v < G.V(); v++) {
            distTo[v] = Double.NEGATIVE_INFINITY;
        }
        validateVertex(s);
        distTo[s] = 0.0;

        Topological topological = new Topological(G);
        if (!topological.hasOrder()) {
            throw new IllegalArgumentException("the specified edge-weighted digraph is not acyclic");
        }
        // relax vertices in topological order
        // so that no edges point back to vertices
        // that have been relaxed
        for(int v : topological.order()) {
            for(DirectedEdge e : G.adj(v)) {
                relax(e);
            }
        }

    }

    // update if e has longer distance to w
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[v] + e.weight() > distTo[w]) {
            edgeTo[w] = e;
            distTo[w] = distTo[v] + e.weight();
        }
    }

    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException(v + " is not between 0 and " + (V-1));
        }
    }

    /**
     * Is there any path from the source vertex to {@code v}?
     * @param v the target vertex
     * @return {@code true} if there is a path, {@code null} otherwise
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] > Double.NEGATIVE_INFINITY;
    }

    /**
     * Returns the distance from the source vertex to {@code v}.
     * @param v the target vertex
     * @return the distance from the source vertex to {@code v}
     */
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * Returns the edges on the longest path from source vertex to {@code v}.
     * @param v the target vertex
     * @return the edges on the longest path from source vertex to {@code v}
     * if the path exists, {@code null} otherwise
     */
    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        Stack<DirectedEdge> path = null;
        if (hasPathTo(v)) {
            path = new Stack<>();
            for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
                path.push(e);
            }
        }
        return path;
    }

    // unit test code
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        int s = Integer.parseInt(args[1]);
        AcyclicLP lp = new AcyclicLP(G, s);

        for(int v = 0; v < G.V(); v++) {
            if (lp.hasPathTo(v)) {
                StdOut.printf("%d to %d (%.2f)", s, v, lp.distTo(v));
                for(DirectedEdge e : lp.pathTo(v)) {
                    StdOut.print("  " + e);
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, v);
            }
        }

    }
}
