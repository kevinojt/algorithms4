package me.alivecode.algs4;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code Digraph} class represents a directed graph of vertices
 * named 0 through 0 to <em>V</em>-1.
 */
public class Digraph {
    private static final String NEWLINE = System.getProperty("line.separator");
    private Bag<Integer>[] adj; // adj[v] = adjacency list for v
    private int[] indegree; // indegree[v] = number of edges go to v
    private int E; // number of edges in the graph
    private int V; // number of vertices in the graph

    /**
     * Initializes a directed graph from specified input stream.
     * The input format is number of vertices <em>V</em>, 
     * followed numbers of edges <em>E</em>,
     * followed <em>E</em> pairs of vertices, with each entry separated by whitesapce.
     */
    public Digraph(In in) {
       try {
            V = in.readInt();
            if (V < 0) throw new IllegalArgumentException("number of vertices in a Digraph must nonnegative");
            adj = (Bag<Integer>[]) new Bag[V];
            for(int v = 0; v < V; v++) {
                adj[v] = new Bag<Integer>();
            }
            indegree = new int[V];
            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("number of edges in a Digraph must nonnegative");
            
            for(int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
            }
        }
        catch(NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Digraph constructor", e);
        }
    }

    /**
     * Initializes an empty digraph with <em>V</em> vertices.
     */
    public Digraph(int V) {
        if (V < 0) throw new IllegalArgumentException("number of vertices in a Digraph must nonnegative");
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
        indegree = new int[V];
    }

    /**
     * Initialzes a new digraph that is a deep copy of {@code G}.
     */
    public Digraph(Digraph G) {
        this(G.V());
        for(int v = 0; v < V; v++) {
            Stack<Integer> reverse = new Stack<Integer>();
            for(int w: G.adj(v)) {
                reverse.push(w); 
            }
            for(int w: reverse) {
                addEdge(v, w);
            }
        }
    } 

    private void validateVertex(int v) {
        if (v < 0 || v >= V) 
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Adds the directed edge v→w to this digraph.
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].put(w);
        indegree[w]++;
        E++;
    }

    /**
     * Returns number of vertices in this graph.
     */
    public int V() {
        return V;
    }

    /**
     * Returns number of edges in this graph.
     */
    public int E() {
        return E;
    }

    /**
     * Returns outdegree of vertex {@code v}.
     */
    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * Return indegree of vertex {@code v}.
     */
    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    /**
     * Returns the adjacency list of {@code v}.
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * Returns a new digraph in reverse order of this digraph.
     */
    public Digraph reverse() {
        Digraph reverse = new Digraph(V);
        for(int v = 0; v < V; v++) {
            for(int w : adj(v)) {
                reverse.addEdge(w, v);
            }
        }
        return reverse; 
    }

    /**
     * Represents a digraph.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges" + NEWLINE);
        for(int v = 0; v < V; v++) {
            s.append("v " + v + ":");
            for(int w : adj(v)) {
                s.append(" " + w);
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    // unit test code
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        Digraph G1 = new Digraph(G);
        StdOut.println(G1);
    }
}


