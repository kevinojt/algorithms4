package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


/**
 * The {@code EdgeWeightedGraph} class represents
 * a graph containing weighted edges.
 */
public class EdgeWeightedGraph {
    private static final String NEWLINE = System.getProperty("line.separator");
    private Bag<Edge>[] adj; // adj[v]= v's adjacent list.
    private int V;
    private int E;

    /**
     * Initializes an with {@code V} vertices.
     * @param V
     */
    public EdgeWeightedGraph(int V) {
        this.V = V;
        adj = (Bag<Edge>[]) new Bag[V];

        for(int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    /**
     * Initializes an graph for the specified input stream.
     * The format of input steam must be
     * V
     * E
     * edge 1
     * edge 2
     * ...
     * edge E
     * ----------
     * For example:
     * 3
     * 3
     * 0 1 0.1
     * 1 2 0.2
     * 2 0 0.3
     *
     * @param in the input stream.
     */
    public EdgeWeightedGraph(In in) {
        this(in.readInt());
        E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("number of edges must be nonnegative");
        for(int i = 0; i < E; i++) {
           int v = in.readInt();
           int w = in.readInt();
           validateVertex(v);
           validateVertex(w);
           double weight = in.readDouble();
           Edge e = new Edge(v, w, weight);
           adj[v].put(e);
           adj[w].put(e);
        }
    }

    /**
     * Initializes
     * @param G
     */
    public EdgeWeightedGraph(EdgeWeightedGraph G) {
        V = G.V();
        E = G.E();
        adj = (Bag<Edge>[]) new Bag[V];

        for(int v = 0; v < V; v++) {
            Stack<Edge> stack = new Stack<>();
            adj[v] = new Bag<>();

            for(Edge e : G.adj(v)) {
                stack.push(e);
            }

            for(Edge e : stack) {
                adj[v].put(e);
            }
        }
    }

    /**
     * Adds edge to the graph.
     *
     * @param edge the edge
     */
    public void addEdge(Edge edge) {
        int v = edge.either();
        int w = edge.other(v);
        validateVertex(v);
        validateVertex(w);
        adj[v].put(edge);
        adj[w].put(edge);
        E++;
    }

    /**
     * Returns the number of vertices in the graph.
     *
     * @return the number of vertices in the graph.
     */
    public int V() { return V; }

    /**
     * Returns the number of edges in the graph.
     * @return
     */
    public int E() {return E;}

    /**
     * Returns the adjacent list of {@code v}.
     *
     * @param v the vertex.
     * @return the adjacent list of {@code v}
     */
    public Iterable<Edge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * Returns the degree of {@code v}.
     *
     * @param v the vertex
     * @return the degree of {@code v}
     */
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) throw new IllegalArgumentException();
    }

    /**
     * Returns the edges in the graph.
     *
     * @return the edges in the graph.
     */
    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<>();
        for(int v = 0; v < V; v++) {
            int selfLoop = 0;
            for(Edge e: adj[v]) {
                if (e.other(v) > v) {
                    list.put(e);
                }
                else if(e.other(v) == v){
                    // only add copy of each self loop.
                    // v has self loop: v: v v v v
                   if (selfLoop % 2 == 0) {
                       list.put(e);
                   }
                   selfLoop++;
                }
            }
        }

        return list;
    }

    /**
     * Returns a string representing this graph.
     *
     * @return a string representing this graph.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for(int v = 0; v < V; v++) {
            s.append(v + ":");
            for(Edge e: adj[v]) {
                s.append(" " + e);
            }
            s.append(NEWLINE);
        }
        return s.toString();
     }

     // unit test code.
     public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        EdgeWeightedGraph G1 = new EdgeWeightedGraph(G);
        StdOut.println(G1);
     }

}

