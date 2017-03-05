package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code LazyPrimMST} class implements the
 * Lazy Prim algorithm for determining minimum spanning tree
 * in an edge weighted graph.
 */
public class LazyPrimMST {
    private boolean[] marked;
    private Queue<Edge> mst;
    private double weight;

    /**
     * Determines the minimum spanning tree of the
     * specified graph {@code G}.
     *
     * @param G the edge weighted graph.
     */
    public LazyPrimMST(EdgeWeightedGraph G) {
        this.marked = new boolean[G.V()];
        mst = new Queue<>();
        // the priority queue for storing
        // edges, so edges with smaller weight
        // will be picked.
        MinPQ<Edge> pq = new MinPQ<>();

        visit(G, 0, pq);

        while (!pq.isEmpty()) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (marked[v] && marked[w]) continue;
            // put the smallest unvisited edge in mst.
            mst.enqueue(e);
            weight += e.weight;
            // put edges that connected to unvisited vertex in pq.
            if (!marked[v]) visit(G, v, pq);
            if (!marked[w]) visit(G, w, pq);
        }

    }

    private void visit(EdgeWeightedGraph G, int v, MinPQ<Edge> pq) {
        marked[v] = true;
        for(Edge e: G.adj(v)) {
            // put un visited edges in pq.
            if (!marked[e.other(v)]) {
                pq.insert(e);
            }
        }
    }

    /**
     * Returns the weight of mst.
     *
     * @return the weight of mst
     */
    public double weight() {
        return weight;
    }

    /**
     * Returns the minimum spanning tree of the specified graph.
     *
     * @return the edges in the mst
     */
    public Iterable<Edge> edges() {
        return mst;
    }

    // unit test code
    public static void main(String[] args) {
        In in = new In(args[0]) ;
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        LazyPrimMST mst = new LazyPrimMST(G);

        for (Edge e: mst.edges()) {
            StdOut.println(e);
        }
        StdOut.println(mst.weight);

    }

}
