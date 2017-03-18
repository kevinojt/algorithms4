package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.zip.DeflaterOutputStream;

/**
 * The {@code DijkstraSP} class implements the Dijkstra Algorithm
 * for determining the shortest paths from source vertex to every
 * other vertices in the edge-weighted digraph.
 */
public class DijkstraSP {
    private IndexMinPQ<Double> pq;
    private double[] distTo;
    private DirectedEdge[] edgeTo;

    /**
     * Determines the shortest paths from vertex {@code s} to
     * every other vertices in the edge-weighted digraph {@code G}.
     *
     * @param G the edge-weighted digraph
     * @param s the source vertex
     */
    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        for(DirectedEdge e : G.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException(e + " has negative weight");
        }

        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];

        validateVertex(s);

        for(int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<>(G.V());
        pq.insert(s, distTo[s]);
        while(!pq.isEmpty()) {
            int v = pq.delMin();
            for(DirectedEdge e : G.adj(v)) {
                relax(e);
            }
        }

        assert check(G, s);
    }

    // update the path from source vertex to w
    // if edge e has less weight
    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) { pq.decreaseKey(w, distTo[w]); }
            else { pq.insert(w, distTo[w]); }
        }
    }

    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException(v + " is not between 0 and " + (V-1));
        }
    }

    /**
     * Is there any path from source vertex to vertex {@code v}?
     *
     * @param v the target vertex
     * @return {@code true} if there is a path from source vertex to v,
     * {@code false} otherwise
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v]  < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns the distance from source vertex to {@code v}.
     * @param v the target vertex
     * @return the distance from source vertex to {@code v}
     */
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * Returns the directed edges from the source vertex to {@code v}
     * if there is a path between the source vertex and {@code v}.
     * @param v the target vertex
     * @return the directed edges  from the source vertex to {@code v}
     * if there is a path between them, {@code null} otherwise
     */
    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> stack = new Stack<>();
        for(DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            stack.push(e);
        }
        return stack;
    }

    // check optimality conditions:
    // (i) for all edges e:            distTo[e.to()] <= distTo[e.from()] + e.weight()
    // (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
    private boolean check(EdgeWeightedDigraph G, int s) {
        // check that distTo[v] and edgeTo[v] are consistent
        if (edgeTo[s] != null || distTo[s] != 0.0) {
            System.err.println("edgeTo[s] and edge[s] are inconsistent");
            return false;
        }
        for(int v = 0; v < G.V(); v++) {
            if (v == s) continue;;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.printf("edgeTo[%d] and edgeTo[%d] are inconsistent\n", v, v);
                return false;
            }
        }

        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
        for(int v = 0; v < G.V(); v++) {
            for(DirectedEdge e : G.adj(v)) {
                int w = e.to();
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.V(); w++) {
            if (edgeTo[w] == null) continue;
            DirectedEdge e = edgeTo[w];
            int v = e.from();
            if (w != e.to()) return false;
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }

    // unit test code
    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = Integer.parseInt(args[1]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        DijkstraSP sp = new DijkstraSP(G, s);
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
