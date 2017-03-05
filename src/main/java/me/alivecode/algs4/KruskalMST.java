package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * The {@code KruskalMST} class implements the Kruskal algorithm
 * for determining minimum spanning tree.
 */
public class KruskalMST {
    private Queue<Edge> mst;
    private double weight;

    /**
     * Determines the minimum spanning tree of the specified
     * edge weighted graph {@code G}.
     *
     * @param G the graph.
     */
    public KruskalMST(EdgeWeightedGraph G) {
        mst = new Queue<>();

        //edu.princeton.cs.algs4.MinPQ<Edge> pq = new edu.princeton.cs.algs4.MinPQ<Edge>();
        MinPQ<Edge> pq = new MinPQ<>();
        for(Edge e: G.edges()) {
            pq.insert(e);
        }
        UF uf = new UF(pq.size());
        while(!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) {
                mst.enqueue(e);
                weight += e.weight();
                uf.union(v, w);
            }
        }
    }

    /**
     * Returns the minimum spanning tree.
     *
     * @return the minimum spanning tree
     */
    public Iterable<Edge> edges() {
        return mst;
    }

    /**
     * Returns the weight of the mst.
     *
     * @return the weight of the mst
     */
    public double weight() {
        return weight;
    }

    // unit test code
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        KruskalMST mst = new KruskalMST(G);

        StdOut.println("mst: ");
        for(Edge e: mst.edges()){
            StdOut.println(e);
        }
        StdOut.println(mst.weight());
        //StdOut.printf("%.5f\n", mst.weight());
    }

}
