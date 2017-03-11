package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


/**
 *  The {@code EdgeWeightedDigraph} class represents a edge-weighted
 *  digraph of vertices named 0 through <em>V</em> - 1, where each
 *  directed edge is of type {@link DirectedEdge} and has a real-valued weight.
 */
public class EdgeWeightedDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private Bag<DirectedEdge>[] adj;
    private int V;
    private int E;
    private int[] indegree;

    /**
     * Initializes an edge-weighted digraph containing {@code V} vertices.
     *
     * @param V number of vertices.
     */
    public EdgeWeightedDigraph(int V) {
        if (V < 0) throw new IllegalArgumentException("argument to EdgeWeightedDigraph must be nonnegative");
        this.V = V;
        indegree = new int[V];
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for(int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    /**
     * Initializes an edge-weighted digraph from the specified
     * input stream {@code in}.
     * The format is the number of vertices <em>V</em>
     * followed by the number of edges <em>E</em>
     * followed by <em>E</em> pairs of vertices and edge weights,
     * with each entry separated by whitespace.
     *
     * @param in the input stream.
     */
    public EdgeWeightedDigraph(In in) {
        this.V = in.readInt();
        this.E = in.readInt();
        this.indegree = new int[V];
        this.adj = (Bag<DirectedEdge>[]) new Bag[V];
        for(int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
        for(int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            DirectedEdge e = new DirectedEdge(v, w, weight);
            adj[v].put(e);
            indegree[w]++;
        }
    }

    /**
     * Initializes a deep copy of edge-weighted digraph {@code G}.
     *
     * @param G the sources edge-weighted digraph
     */
    public EdgeWeightedDigraph(EdgeWeightedDigraph G) {
        this(G.V());
        for(int v = 0; v < G.V(); v++) {
            Stack<DirectedEdge> edges = new Stack<>();
            for(DirectedEdge e : G.adj(v)) {
                edges.push(e);
            }

            for(DirectedEdge e : edges) {
                DirectedEdge c = new DirectedEdge(e.from(), e.to(), e.weight());
                addEdge(c);
            }
        }
    }

    /**
     * Adds edge {@code e} to this edge-weighted digraph.
     *
     * @param e the edge.
     */
    public void addEdge(DirectedEdge e) {
        validateVertex(e.from());
        validateVertex(e.to());
        adj[e.from()].put(e);
        E++;
        indegree[e.to()]++;
    }

    /**
     * Returns the directed edges incident from vertex {@code v}.
     *
     * @param v the vertex.
     * @return the directed edges incident from vertex {@code v}
     */
    public Iterable<DirectedEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * Returns the number of vertices in this edge-weighted digraph.
     *
     * @return the number of vertices in this edge-weighted digraph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this edge-weighted digraph.
     *
     * @return the number of edges in this edge-weighted digraph
     */
    public int E() {
        return E;
    }

    /**
     * Returns the number of edges incident to vertex {@code v}.
     *
     * @param v the vertex.
     * @return the number of edges incidents to vertex {@code v}
     */
    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    /**
     * Returns the number of edges incident from vertex {@code v}.
     *
     * @param v the vertex
     * @return the number of edges incident from vertex {@code v}
     */
    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * Returns the string represents this edge-weighted digraph.
     *
     * @return the string represents this edge-weighted digraph
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for(int v = 0; v < V; v++) {
            s.append(v + ":");
            for(DirectedEdge e: adj[v]) {
                s.append("  " + e);
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException(v + " is not between 0 and " + (V-1));
        }
    }

    // unit test code
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        EdgeWeightedDigraph G1 = new EdgeWeightedDigraph(G);
        StdOut.println(G1);
    }
}
