package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;

import java.util.Objects;

/**
 * Created by oujt on 17-3-9.
 */
public class EdgeWeightedDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private Bag<DirectedEdge>[] adj;
    private int V;
    private int E;
    private int[] indegree;

    public EdgeWeightedDigraph(int V) {
        if (V < 0) throw new IllegalArgumentException("argument to EdgeWeightedDigraph must be nonnegative");
        this.V = V;
        indegree = new int[V];
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for(int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

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

    public void addEdge(DirectedEdge e) {
        validateVertex(e.from());
        validateVertex(e.to());
        adj[e.from()].put(e);
        E++;
        indegree[e.to()]++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        return "";
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException(v + " is not between 0 and " + (V-1));
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        EdgeWeightedDigraph G1 = new EdgeWeightedDigraph(G);
    }
}
