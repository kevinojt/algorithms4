package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code FlowNetwork} class represents a capacitated network
 * with vertices named 0 through <em>V</em> - 1, where each directed
 * edge is of type {@link FlowEdge} and has a real-valued capacity
 * and flow.
 */
public class FlowNetwork {
    private static final String NEWLINE = System.getProperty("line.separator");

    private Bag<FlowEdge>[] adj;// adj[v] = edges point from or to v
    private final int V;        // number of vertices
    private int E;              // number of edges

    /**
     * Initializes an empty flow network with {@code V} vertices and 0 edges.
     * @param V the number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public FlowNetwork(int V) {
        if (V < 0) throw new IllegalArgumentException("number of vertices must be nonnegative");

        this.V = V;
        adj = (Bag<FlowEdge>[])new Bag[V];
        for(int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    /**
     * Initializes a random flow network with {@code V} vertices and <em>E</em> edges.
     * The capacities are integers between 0 and 99 and the flow values are zero.
     * @param V the number of vertices
     * @param E the number of edges
     * @throws IllegalArgumentException if {@code V < 0}
     * @throws IllegalArgumentException if {@code E < 0}
     */
    public FlowNetwork(int V, int E) {
        this(V);
        if (E < 0) throw new IllegalArgumentException("number of edges must be nonnegative");
        for(int i = 0; i < E; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double c = StdRandom.uniform(100.0);
            addEdge(new FlowEdge(v, w, c));
        }
    }

    /**
     * Initializes a flow network from an input stream.
     * The format is the number of vertices <em>V</em>,
     * followed by the number of edges <em>E</em>,
     * followed by <em>E</em> pairs of vertices and edge capacities,
     * with each entry separated by whitespace.
     * @param in the input stream
     * @throws IllegalArgumentException if the endpoints of any edge are not in prescribed range
     * @throws IllegalArgumentException if the number of vertices or edges is negative
     */
    public FlowNetwork(In in) {
        V = in.readInt();
        E = in.readInt();
        if (V < 0) throw new IllegalArgumentException("number of vertices must be nonnegative");
        if (E < 0) throw new IllegalArgumentException("number of edges must be nonnegative");

        for(int i = 0; i < V; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double c = in.readDouble();
            addEdge(new FlowEdge(v, w, c));
        }
    }

    /**
     * Add edge {@code e} to the flow-network.
     * @param e the flow-edge
     */
    public void addEdge(FlowEdge e) {
        int v = e.from();
        int w = e.to();
        validateVertex(v);
        validateVertex(w);
        adj[v].put(e);
        adj[w].put(e);
        E++;
    }

    /**
     * Returns all edges points from the given vertex {@code v}.
     *
     * @param v the vertex
     * @return all edges points from the given vertex {@code v}
     */
    public Iterable<FlowEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * Returns all of the edges in the flow-network.
     * @return all of the edges in the flow-network
     */
    public Iterable<FlowEdge> edges() {
        Bag<FlowEdge> list = new Bag<>();
        for(int v = 0; v < V; v++) {
            for(FlowEdge e : adj[v]) {
                if (e.to() != v) {
                    list.put(e);
                }
            }
        }
        return list;
    }

    /**
     * Returns the numbers of vertices in the flow-network.
     *
     * @return the numbers of vertices in the flow-network
     */
    public int V() {
        return V;
    }

    /**
     * Returns the numbers of edges in the flow-network.
     *
     * @return the numbers of edges in the flow-network
     */
    public int E() {
        return E;
    }

    /**
     * Returns a string represents the flow-network.
     *
     * @return a string represents the flow-network
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ":  ");
            for (FlowEdge e : adj[v]) {
                if (e.to() != v) s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException(v + "is not between 0 and " + (V-1));
    }

    // unit test code
    public static void main(String[] args) {
        FlowNetwork fn = new FlowNetwork(10, 30);
        StdOut.println(fn);
    }
}
