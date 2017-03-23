package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code BellmanFordSP} class implements the Bellman Ford
 * algorithm for determining shortest path in edge-weighted digraph.
 */
public class BellmanFordSP {
    private DirectedEdge[] edgeTo;          // edgeTo[v] = last edge on shortest s-v path
    private double[] distTo;                // distTo[v] = distance from s to v
    private Queue<Integer> queue;           // queue of vertices to be relaxed
    private boolean[] onQueue;              // onQueue[v] = is v on queue?
    private int cost;                       // number of calls to relax
    private Iterable<DirectedEdge> cycle;   // negative cycle (or null if no such cycle)

    /**
     * Determines shortest paths from {@code s} to
     * every other vertices in edge-weighted digraph {@code G}
     * @param G the edge-weighted digraph
     * @param s the source vertex
     */
    public BellmanFordSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        onQueue = new boolean[G.V()];
        distTo = new double[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        validateVertex(s);
        distTo[s] = 0.0;

        queue = new Queue<>();
        queue.enqueue(s);
        onQueue[s] = true;
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.dequeue();
            onQueue[v] = false;
            relax(G, v);
        }

        assert check(G, s);
    }

    // relax all edges points from v
    private void relax(EdgeWeightedDigraph G, int v) {
        for(DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[v] + e.weight() < distTo[w] ) {
                edgeTo[w] = e;
                distTo[w] = distTo[v] + e.weight();
                // put w to the queue to be relaxed
                if (!onQueue[w]) {
                    queue.enqueue(w);
                    onQueue[w] = true;
                }
            }

            if (cost++ % G.V() == 0) {
                findNegativeCycle();
                if (hasNegativeCycle()) return;
            }
        }

    }

    private void findNegativeCycle() {
        int V = edgeTo.length;
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for(int v = 0; v < V; v++) {
            if (edgeTo[v] != null) {
                spt.addEdge(edgeTo[v]);
            }
        }

        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(spt);
        if (finder.hasCycle()) {
            cycle = finder.cycle();
        }
    }

    /**
     * Does the digraph have negative cycle?
     * @return {@code true} if the digraph has negative cycle,
     * {@code false} otherwise
     */
    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    /**
     * Returns the negative cycle.
     *
     * @return the negative cycle
     * @throws UnsupportedOperationException if there is no such cycle
     */
    public Iterable<DirectedEdge> cycle() {
        if (!hasNegativeCycle()) {
            throw new UnsupportedOperationException("No negative cost cycle exists");
        }

        return cycle;
    }

    private void validateVertex(int v) {
        int V = edgeTo.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException(v + " is not between 0 and " + (V-1));
        }
    }

    /**
     * Is there any path from the source vertex to {@code v}?
     * @param v the target vertex
     * @return {@code true} if there is a path from the source vertex to {@code v},
     * {@code false} otherwise
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns the distance from the source vertex to {@code v}.
     * @param v the target vertex
     * @return the distance from the source vertex to {@code v}
     *
     * @throws UnsupportedOperationException if the digraph has negative cycle
     */
    public double distTo(int v) {
        validateVertex(v);
        if (hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }
        return distTo[v];
    }

    /**
     * Returns the shortest path from source vertex to {@code v}.
     * @param v the target vertex
     * @return the shortest path form source vertex to {@code v}
     * if there is no such path, {@code null} otherwise
     *
     * @throws UnsupportedOperationException if the digraph has negative cycle
     */
    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }
        Stack<DirectedEdge> path = null;
        if (hasPathTo(v)) {
            path = new Stack<>();
            for(DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
                path.push(e);
            }
        }
        return path;
    }

    private boolean check(EdgeWeightedDigraph G, int s) {
        if (hasNegativeCycle()) {
            double weight = 0.0;
            for(DirectedEdge e : cycle) {
                weight += e.weight();
            }
            if (weight >= 0.0) {
                System.err.println("error: weight of negative cycle = " + weight);
                return false;
            }
        }
        else {
            if (edgeTo[s] != null || distTo[s] != 0.0) {
                System.err.println("edgeTo[s] and distTo[s] inconsistent");
                return false;
            }
            for(int v = 0; v < G.V(); v++) {
                if (v == s) continue;
                if (edgeTo[v] != null && distTo[v] != Double.POSITIVE_INFINITY) {
                    System.err.printf("edgeTo[%d] and distTo[%d] inconsistent", v, v);
                    return false;
                }
            }

            // check that all edges v->w satisfy distTo[w] <= distTo[v] + e.weight()
            for(int i = 0; i < G.V(); i++) {
                for(DirectedEdge e : G.adj(i)) {
                    int w = e.to();
                    if (distTo[i] + e.weight() > distTo[w]) {
                        System.err.printf("edge " + e + " not relaxed");
                        return false;
                    }
                }
            }

            // check that all edges v->w on shortest path satisfy
            // distTo[w] = dist[v] + e.weight()
            for(int j = 0; j < G.V(); j++) {
                DirectedEdge e = edgeTo[j];
                if (e == null) continue;
                int w = e.to();
                if (e.from() != j) return false;
                if (distTo[w] != distTo[j] + e.weight()) {
                    System.err.println("edge " + e + " on shortest path not tight");
                }
            }
        }

        return true;
    }

    // unit test code
    public static void  main(String[] args) {
        In in = new In(args[0]);
        int s = Integer.parseInt(args[1]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        BellmanFordSP sp = new BellmanFordSP(G, s);
        if (sp.hasNegativeCycle()) {
            StdOut.println("Has negative cost cycle");
            for(DirectedEdge e : sp.cycle()) {
                StdOut.println(e);
            }
        }
        else {
            for(int v = 0; v < G.V(); v++) {
                if (sp.hasPathTo(v)) {
                    StdOut.printf("%d to %d (%.2f)", s, v, sp.distTo(v));
                    for(DirectedEdge e : sp.pathTo(v)) {
                        StdOut.print("  " + e);
                    }
                    StdOut.println();
                }
                else {
                    StdOut.printf("%d to %d  no path\n", s, v);
                }
            }
        }
    }

}
