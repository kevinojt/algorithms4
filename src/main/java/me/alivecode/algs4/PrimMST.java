package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


/**
 * The {@code PrimMST} represents a data type for
 * determining the minimum spanning tree in a edge weighted graph.
 */
public class PrimMST {
    private boolean[] marked;   // marked[v] = true if v on the tree, false otherwise
    private double[] distTo;    // weight of shortest such edge
    private Edge[] edgeTo;      // shortest edge from tree vertex to non-tree vertex
    private IndexMinPQ<Double> pq;
    private Queue<Edge> mst;    // edges of the minimum spanning tree
    private double weight;      // weight of the minimum spanning tree

    /**
     * Determining the minimum spanning tree of the specified graph {@code G}
     *
     * @param G the graph
     */
    public PrimMST(EdgeWeightedGraph G) {
        marked = new boolean[G.V()];
        distTo = new double[G.V()];
        edgeTo = new Edge[G.V()];
        pq = new IndexMinPQ<>(G.V());
        for(int i = 0; i < G.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                // finds the mst of of the connected component that v is in
                // will find all edges of the forest
                // if G has more than one connected components.
                prim(G, v);
            }
        }

        // put all edges in mst queue.
        buildMST();

    }

    private void prim(EdgeWeightedGraph G, int s) {
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(G, v);
        }
    }

    // find shortest edges to v's adjacent vertices
    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for(Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;
            if (e.weight() < distTo[w]) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.decreaseKey(w, e.weight());
                else                pq.insert(w, e.weight());
            }
        }
    }

    private void buildMST() {
        mst = new Queue<>();
        for(int v = 0; v < edgeTo.length; v++) {
            if (edgeTo[v] != null) {
                mst.enqueue(edgeTo[v]);
                weight += distTo[v];
            }
        }
    }

    /**
     * Returns edges in the minimum spanning tree.
     *
     * @return edges in the minimum spanning tree
     */
    public Iterable<Edge> edges() {
        return mst;
    }

    /**
     * Returns the weight of the minimum spanning tree.
     *
     * @return the weight of the minimum spanning tree
     */
    public double weight() {
        return  weight;
    }

    // unit test code
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        PrimMST mst = new PrimMST(G);

        for(Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.println(mst.weight());
    }
}
