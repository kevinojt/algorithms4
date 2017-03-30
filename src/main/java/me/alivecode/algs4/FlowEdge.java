package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code FLowEdge} class represents a capacitated edge
 * in a {@link FlowNetwork}.
 */
public class FlowEdge {
    // to deal with floating point round-off errors
    private static final double FLOATING_POINT_EPSILON = 1E-10;
    private final int v;            // from
    private final int w;            // to
    private final double capacity;  // capacity
    private double flow;            // flow

    /**
     * Initializes a FlowEdge.
     * @param v the head vertex
     * @param w the tail vertex
     * @param capacity the capacity
     */
    public FlowEdge(int v, int w, double capacity) {
        this(v, w, capacity, 0.0);
    }

    /**
     * Initializes a FlowEdge
     * @param v the head vertex
     * @param w the tail vertex
     * @param capacity the capacity
     * @param flow the flow
     */
    public FlowEdge(int v, int w, double capacity, double flow) {
        if (v < 0) throw new IllegalArgumentException("vertex must be nonnegative integer");
        if (w < 0) throw new IllegalArgumentException("vertex must be nonnegative integer");
        if (capacity < 0.0) throw new IllegalArgumentException("capacity must be nonnegative");
        if (flow < 0.0) throw new IllegalArgumentException("flow must bue nonnegative");
        if (flow > capacity) throw new IllegalArgumentException("flow exceeds capacity");

        this.v = v;
        this.w = w;
        this.capacity = capacity;
        this.flow = flow;
    }

    /**
     * Initializes a FlowEdge that is a deep copy of {@code e}.
     * @param e the original FlowEdge
     */
    public FlowEdge(FlowEdge e) {
        this(e.v, e.w, e.capacity, e.flow);
    }

    /**
     * Returns the head vertex.
     * @return the head vertex
     */
    public int from() {
        return v;
    }

    /**
     * Returns the tail vertex.
     * @return the tail vertex
     */
    public int to() {
        return w;
    }

    /**
     * Returns the capacity.
     * @return the capacity
     */
    public double capacity() {
        return capacity;
    }

    /**
     * Returns the other vertex of the given vertex {@code v}
     * @param v one of the endpoints of the edge
     * @return the other vertex of the given vertex {@code v}
     */
    public int other(int v) {
        if (v == this.v) {
            return w;
        }
        else if (v == w) {
            return this.v;
        }
        else {
            throw new IllegalArgumentException(v + " is not one of " + this.v + " and " + w);
        }
    }

    /**
     * Returns the flow.
     * @return the flow
     */
    public double flow() {
        return flow;
    }

    /**
     * Returns the residual capacity of the edge in the direction
     *  to the given {@code vertex}.
     * @param vertex one endpoint of the edge
     * @return the residual capacity of the edge in the direction to the given vertex
     *   If {@code vertex} is the tail vertex, the residual capacity equals
     *   {@code capacity() - flow()}; if {@code vertex} is the head vertex, the
     *   residual capacity equals {@code flow()}.
     */
    public double residualCapacityTo(int vertex) {
        if (vertex == v) {
            return flow;
        }
        else if (vertex == w) {
            return capacity - flow;
        }
        else {
            throw new IllegalArgumentException(vertex + " is not head:" + v + " or tail:" + w);
        }
    }

    /**
     * Increases the flow one the edge in the direction to the {@code v}.
     * @param vertex one of the endpoints of the edge
     * @param delta amount of which to flow
     */
    public void addResidualFlowTo(int vertex, double delta) {
        if (vertex == v) {
            flow -= delta;
        }
        else if (vertex == w) {
            flow += delta;
        }
        else {
            throw new IllegalArgumentException(vertex + " is not head:" + v + "or tail:" + w);
        }

        if (Math.abs(flow) <= FLOATING_POINT_EPSILON) {
            flow = 0;
        }
        if (Math.abs(capacity - flow) <= FLOATING_POINT_EPSILON) {
            flow = capacity;
        }

        if (flow < 0.0) throw new IllegalArgumentException("flow is negative");
        if (flow > capacity) throw new IllegalArgumentException("flow exceeds capacity");
    }

    /**
     * Returns a string that represents the flow edge.
     * @return a string that represents the flow edge
     */
    public String toString() {
        return v + "->" + w + " " + flow + "/" + capacity;
    }

    public static void main(String[] args) {
        FlowEdge fe = new FlowEdge(1, 2, 10, 9);
        StdOut.println(fe);
    }
}
