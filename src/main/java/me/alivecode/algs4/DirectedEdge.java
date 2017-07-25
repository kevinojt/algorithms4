package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdOut;

import java.util.DoubleSummaryStatistics;

/**
 * The {@code DirectedEdge} class represents a directed weighted edge
 * in a {@link EdgeWeightedDigraph} that containing two vertices that
 * pointing from one to another.
 */
public class DirectedEdge implements Comparable<DirectedEdge> {
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
        String s = String.format("%s->%s %5.2f", v, w, weight);
        return s;
    }

    /**
     * Compares weight of this edge and the given edge.
     *
     * @param that other edge.
     *
     * @return a negative integer, zero, positive integer depending on
     * whether the weight of this edge is less than, equal to, or greater than
     * the weight of other edge.
     */
    public int compareTo(DirectedEdge that) {
        return  Double.compare(this.weight(), that.weight());

    }

    // unit test code
    public static void main(String[] args) {
        DirectedEdge e = new DirectedEdge(1, 2, 0.776);
        StdOut.println(e);
    }
}
