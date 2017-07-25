package me.alivecode.algs4;

/**
 * The {@code Edge} class represents a weighted edge
 * in an {@link EdgeWeightedGraph}.
 * It contain two endpoints(vertex in a graph) a weight.
 */
public class Edge implements Comparable<Edge>{
    private int v;
    private int w;
    double weight;

    /**
     * Initializes an edge between vertices {@code v} and {@code w}
     * of the given weight {@code weight}.
     *
     * @param v one vertex
     * @param w the the vertex
     * @param weight weight of this edge.
     * @throws IllegalArgumentException if {@code v} is negative.
     * @throws IllegalArgumentException if {@code w} is negative.
     */
    public Edge(int v, int w, double weight) {
        if (v < 0) throw new IllegalArgumentException("vertex must be a nonnegative integer");
        if (w < 0) throw new IllegalArgumentException("vertex must be a nonnegative integer");

        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /**
     * Returns either endpoint of this edge.
     *
     * @return either endpoint of this edge.
     */
    public int either() {
        return v;
    }

    /**
     * Returns other endpoint of the given endpoint {@code vertex}.
     *
     * @param vertex the given point.
     * @return other endpoint of the given endpoint {@code vertex}
     * @throws IllegalArgumentException if the given endpoint
     * is not one of the endpoint of this edge.
     */
    public int other(int vertex) {
        if (vertex == v) return w;
        if (vertex == w) return v;
        throw new IllegalArgumentException("Illegal endpoint.");
    }

    /**
     * Returns the weight of this edge.
     *
     * @return the weight of this edge.
     */
    public double weight() {
        return weight;
    }

    /**
     * Returns a string representation of this edge.
     *
     * @return a string representation of this edge.
     */
    @Override
    public String toString() {
        return String.format("%d-%d %.5f", v, w, weight);
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
    @Override
    public int compareTo(Edge that) {
        //TODO:how to compare floating point number
        return Double.compare(this.weight, that.weight);
    }
}
