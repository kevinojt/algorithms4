package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code AcyclicSP} class determines the shortest path
 * in acyclic edge-weighted digraph.
 */
public class AcyclicSP {
    private final DirectedEdge[] edgeTo; // edgeTo[v] = previous edge to v
    private final double[] distTo; // distTo[v] = distance to v

    public AcyclicSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        validateVertex(s);
        distTo[s] = 0.0;

        Topological topological = new Topological(G);
        if (!topological.hasOrder()) {
            throw new IllegalArgumentException("the specified graph is not acyclic");
        }
        for(int v : topological.order()) {
            for(DirectedEdge e : G.adj(v)) {
                relax(e);
            }
        }
    }

    // relax directed edge e
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[v] + e.weight() < distTo[w]) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    private void validateVertex(int v) {
        int V = edgeTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException(v + " is not between 0 and " + (V-1));
    }

    /**
     * Is there any path from source vertex to v?
     * @param v the target vertex
     * @return {@code true} if the edge-weighted digraph has a path from source vertex to v,
     * {@code false} otherwise
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns the distance if
     * the edge-weighted digraph has a path from vertex to {@code v}.
     *
     * @param v the target vertex
     * @return the distance if the edge-weighted digraph has a path from vertex to v,
     * {@code Double.POSITIVE_INFINITY} otherwise
     */
    public Double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * Returns the edges in order of the source vertex to {@code v}
     * if there is a path between source vertex and {@code v},
     * {@code null} otherwise.
     *
     * @param v the target vertex
     * @return the edges in order of the source vertex to {@code v}
     * if there is a path between source vertex and {@code v},
     * {@code null} otherwise
     */
    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        Stack<DirectedEdge> path = null;
        if (hasPathTo(v)) {
             path = new Stack<>();
             for(DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
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
        AcyclicSP sp = new AcyclicSP(G, s);

        for(int v = 0; v < G.V(); v++) {
            if (sp.hasPathTo(v)) {
                StdOut.printf("%d to %d (%.2f)", s, v, sp.distTo(v));
                for(DirectedEdge e : sp.pathTo(v)) {
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
