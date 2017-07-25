package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code FordFulkerson} class implements Ford Fulkerson algorithm
 * for computing max-flow and finding min-cut in {@link FlowNetwork}.
 */
public class FordFulkerson {
    private static final double FLOATING_POINT_EPSILON = 1E-11;
    private boolean[] marked;   // marked[v] = is v in min-cut?
    private FlowEdge[] edgeTo;  // edgeTo[v] = edge points to v
    private double value;       // max-flow
    private final int V;        // number of vertices in the given FlowNetwork

    /**
     * Compute max-flow and find min-cut.
     * @param G the flow-network
     * @param s the source
     * @param t the sink
     */
    public FordFulkerson(FlowNetwork G, int s, int t) {
        V = G.V();
        validateVertex(s);
        validateVertex(t);

        if (s == t) throw new IllegalArgumentException("Source equals sink");
        if (!isFeasible(G, s, t)) throw new IllegalArgumentException("Initial flow is infeasible");

        value = excess(G, s);
        while (hasAugmentingPath(G, s, t)) {
            double bottle = Double.POSITIVE_INFINITY;
            for(int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }

            for(int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottle);
            }

            value += bottle;
        }

        assert check(G, s, t);
    }

    /**
     * Is {@code v} in min-cut?
     * @param v the vertex
     * @return {@code true} if {@code v} is in min-cut,
     * {@code false} otherwise
     */
    public boolean inCut(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns max-flow of the given flow-network.
     * @return max-flow of the given flow-network
     */
    public double value() {
        return value;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException(v + " is not between 0 and " + (V-1));
        }
    }

    // BFS to find shortest augmenting path on s to t
    // last call to hasAugmentingPath will find vertices in min-cut
    // vertices point to full-load edge and vertices on path to these vertices
    // are in min-cut
    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];

        Queue<Integer> queue = new Queue<>();
        marked[s] = true;
        queue.enqueue(s);
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for(FlowEdge e: G.adj(v)) {
                int w = e.to();
                // Is there any point still has residual capacity?
                if (e.residualCapacityTo(w) > 0.0 && !marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = e;
                    queue.enqueue(w);
                }
            }
        }

        // Do we find a path from s to t?
        // all vertices which marked[v] == true are in min-cut if marked[t] == false
        return marked[t];
    }

    // excess of source point is negative or zero, and equals -value
    // excess of sink point is nonnegative and equals value
    // excess of other points are zero
    private double excess(FlowNetwork G, int v) {
        double excess = 0.0;
        for(FlowEdge e : G.adj(v)) {
            if (e.from() == v) excess -= e.flow();
            else excess += e.flow();
        }
        return excess;
    }

    private boolean isFeasible(FlowNetwork G, int s, int t) {
        // out go flow must less than or equals to capacity
        for (int v = 0; v < G.V(); v++) {
            for(FlowEdge e : G.adj(v)) {
                if (e.flow() < -FLOATING_POINT_EPSILON ||
                        e.flow() > e.capacity() + FLOATING_POINT_EPSILON) {
                    System.err.println("edge does not satisfy capacity constraint: " + e);
                    return false;
                }
            }
        }
        // excess of source point is zero or negative, and equals -value
        if (Math.abs(value + excess(G, s)) > FLOATING_POINT_EPSILON) {
            System.err.println("excess at source " + excess(G, s));
            System.err.println("max flow         " + value);
            return false;
        }
        // excess of sink point is nonnegative and equals value
        if (Math.abs(value - excess(G, t)) > FLOATING_POINT_EPSILON) {
            System.err.println("excess at sink " + excess(G, t));
            System.err.println("max flow       " + value);
            return false;
        }
        // excess of other points are equal zero
        for(int v = 0; v < G.V(); v++) {
            if (v == s || v == t) continue;
            if (Math.abs(excess(G, v)) > FLOATING_POINT_EPSILON) {
                System.err.println("net flow ouf of " + v + " dose not equal to zero");
                return false;
            }
        }

        return true;
    }

    private boolean check(FlowNetwork G, int s, int t) {
        if (!isFeasible(G, s, t)) {
            return false;
        }

        if (!inCut(s)) {
            System.err.println( s + " is not on source side of min cut");
            return false;
        }
        if (inCut(t)) {
            System.err.println(t + " is on source size of min cut");
        }

        // check if min cut value equals max flow
        double mincutValue = 0.0;
        for(int v = 0; v < G.V(); v ++) {
            for(FlowEdge e: G.adj(v)) {
                if (v == e.from() && inCut(e.from()) && !inCut(e.to())) {
                    mincutValue += e.capacity();
                }
            }
        }

        if (Math.abs(mincutValue - value) > FLOATING_POINT_EPSILON) {
            System.err.println("max flow: " + value + ", min cut value: " + mincutValue);
            return false;
        }

        return true;
    }

    // unit test code
    public static void main(String[] args) {
        // create flow network with V vertices and E edges
        int V = 5;//Integer.parseInt(args[0]);
        int E = 10;//Integer.parseInt(args[1]);
        In in = new In(args[0]);
        FlowNetwork G = new FlowNetwork(in);
        int s = 0, t = G.V()-1;

        StdOut.println(G);

        // compute maximum flow and minimum cut
        FordFulkerson maxflow = new FordFulkerson(G, s, t);
        StdOut.println("Max flow from " + s + " to " + t);
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if ((v == e.from()) && e.flow() > 0)
                    StdOut.println("   " + e);
            }
        }

        // print min-cut
        StdOut.print("Min cut: ");
        for (int v = 0; v < G.V(); v++) {
            if (maxflow.inCut(v)) StdOut.print(v + " ");
        }
        StdOut.println();

        StdOut.println("Max flow value = " +  maxflow.value());
    }
}
