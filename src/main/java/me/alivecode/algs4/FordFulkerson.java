package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdOut;

/**
 * Created by oujt on 17-3-28.
 */
public class FordFulkerson {
    private static final double FLOATING_POINT_EPSILON = 1E-11;
    private boolean[] marked;
    private FlowEdge[] edgeTo;
    private double value;
    private final int V;

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

    public boolean inCut(int v) {
        validateVertex(v);
        return marked[v];
    }

    public double value() {
        return value;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException(v + " is not between 0 and " + (V-1));
        }
    }

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
                if (e.residualCapacityTo(w) > 0.0 && !marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = e;
                    queue.enqueue(w);
                }
            }
        }

        return marked[t];
    }

    private double excess(FlowNetwork G, int v) {
        double excess = 0.0;
        for(FlowEdge e : G.adj(v)) {
            if (e.from() == v) excess -= e.flow();
            else excess += e.flow();
        }
        return excess;
    }

    private boolean isFeasible(FlowNetwork G, int s, int t) {
        for (int v = 0; v < G.V(); v++) {
            for(FlowEdge e : G.adj(v)) {
                if (e.flow() < -FLOATING_POINT_EPSILON ||
                        e.flow() > FLOATING_POINT_EPSILON + e.capacity()) {
                    System.err.println("edge does not satisfy capacity constraint: " + e);
                    return false;
                }
            }
        }

        if (Math.abs(value + excess(G, s)) > FLOATING_POINT_EPSILON) {
            System.err.println("excess at source " + excess(G, s));
            System.err.println("max flow         " + value);
            return false;
        }
        if (Math.abs(value - excess(G, t)) > FLOATING_POINT_EPSILON) {
            System.err.println("excess at sink " + excess(G, t));
            System.err.println("max flow       " + value);
            return false;
        }
        for(int v = 0; v < G.V(); v++) {
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

    public static void main(String[] args) {
        // create flow network with V vertices and E edges
        int V = 3;//Integer.parseInt(args[0]);
        int E = 2;//Integer.parseInt(args[1]);
        int s = 0, t = V-1;
        FlowNetwork G = new FlowNetwork(V, E);
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
