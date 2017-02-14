package me.alivecode.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code Graph} class represents an undirected graph of vertices
 * named 0 through <em>V</em>-1.
 */
public class Graph {
    private static final String NEWLINE = System.getProperty("line.separator");
    private final int V; // number of vertices.
    private int E; // number of edges.
    private Bag<Integer>[] adj; // vertice and edge list;

    /** 
     * Initialize a new graph with {@code V} vertices and 0 edge.
     *
     * @param V number of vertices.
     */
    public Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for(int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    /**
     * Intialize a new graph that is a deep copy of {@code G}.
     *
     * @param G graph to copy.
     */
    public Graph(Graph G) {
        this(G.V());
        this.E = G.E();
        for(int v = 0; v < G.V(); v++) {
            Stack<Integer> reverse = new Stack<Integer>();
            for(int w : G.adj[v]) {
                reverse.push(w);
            }
            
            for(int w : reverse) {
                adj[v].put(w);
            }
        }
    }

    /**
     * Initialize a graph from the sepciafied input steam.
     * The format is <em>V</em> vertices,
     * followed by <em>E</em> edges,
     * followed by <em>E</em> pairs of vertices, with each entry separated by withspace.
     *
     * @param in the input steam.
     */
    public Graph(In in) {
        try {
            V = in.readInt();
            if (V < 0) throw new IllegalArgumentException("number of vertices must >= 0");
            adj = (Bag<Integer>[]) new Bag[V];
            for(int v = 0; v < V; v++) {
               adj[v] = new Bag<Integer>();
            }
            
            int E = in.readInt();
            //StdOut.println("E: " + E);
            if (E < 0) throw new IllegalArgumentException("number of edges must >= 0");
            for(int i = 0; i < E; i++) {
                //StdOut.print("E: " + E + " i: " + i + " " + (E==i));
                int v = in.readInt();
                int w = in.readInt();
                //StdOut.println(" i: " + i + " v: " + v + ", w: " + w);
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Invalid input format in Graph constructor", e);
        }
    }
    
    private void validateVertex(int v) {
        if (v < 0 || v > V) 
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Returns number of edges in the graph.
     *
     * @return number of edges.
     */
    public int E() {
        return E;
    }

    /**
     * Returns numbers of vertices in the graph.
     *
     * @return numbers of vertices.
     */
    public int V() {
        return V;
    }

    /**
     * Adds the undirected v-w edge to this graph.
     *
     * @param v one vertex in the edge.
     * @param w the other vertex in the edge.
     */ 
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].put(w);
        adj[w].put(v);
        E++;
    }

    /**
     * Returns the degree of vertex {@code v}.
     * degree means number of other vertices that connect to the {@code v}.
     *
     * @param v the vertex.
     * @return the degree of vertex {@code v}.
     * @throws IllegalArgumentException unless {@code v} is between 0 and <em>V</em>-1.
     */ 
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * Returns the vertices adjacent to the vertex {@code v}.
     *
     * @param v the vertex.
     * @throws IllegalArgumentException if {@code v < 0 || v >= V()}.
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * Returns a string represents this graph.
     * 
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     * followed by <em>V</em> adjacency lists.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges." + NEWLINE);
        for(int v = 0; v < V; v++) {
            s.append(v + ": ");
            for(int w : adj[v]) {
               s.append(w + " ");
            }
            s.append(NEWLINE);
        }

        return s.toString();
    }


    // unit test code.
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        Graph G1 = new Graph(G);
        StdOut.println(G1);
    }
}



