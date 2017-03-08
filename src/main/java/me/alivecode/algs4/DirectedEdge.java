package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdOut;

import java.util.DoubleSummaryStatistics;

/**
 * Created by oujt on 17-3-8.
 */
public class DirectedEdge {
    private final int v;
    private final int w;
    private final double weight;

    /**
     * Initializes a directed edge.
     *
     * @param from the start vertex
     * @param to the end vertex
     * @param weight the weight
     */
    public DirectedEdge(int from, int to, double weight) {
        if (from < 0 || to < 0) throw new IllegalArgumentException("vertices must be nonnegative integers");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("weight is NaN");
        v = from;
        w = to;
        this.weight = weight;
    }

    /**
     * Returns the start vertex of this edge.
     *
     * @return the start vertex of this edge
     */
    public int from() {
        return v;
    }

    /**
     * Returns the end vertex of this edge.
     *
     * @return the end vertex of this edge
     */
    public int to() {
        return w;
    }

    /**
     * Returns the weight of this edge.
     *
     * @return the weight of this edge
     */
    public double weight() {
        return weight;
    }

    /**
     * Returns a string represents this directed edge.
     *
     * @return a string represents this directed edge.
     */
    public String toString() {
        String s = String.format("%s %s %5.5f", v, w, weight);
        return s;
    }

    // unit test code
    public static void main(String[] args) {
        DirectedEdge e = new DirectedEdge(1, 2, 0.776);
        StdOut.println(e);
    }
}
