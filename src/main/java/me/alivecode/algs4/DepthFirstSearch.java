package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

/**
 * The {@code DepthFirstSearch} class represents a data type for
 * determining the vertices connected the given source vertex.
 */
public class DepthFirstSearch {
    private int count; // number of vertices connnected to s
    private boolean[] marked; // marked[v] = Is there an v-s path?

    /**
     * Computes the vertices in graph {@code G} that
     * are conected to source vertex {@code s}.
     *
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DepthFirstSearch(Graph G, int s) {
        count = 0;
        marked = new boolean[G.V()];
        validateVertex(s);
        dfs(G, s);
    }
    
    // throws IllegalArgumentException unless 0 <= v < V 
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1)); 
    }

    // computes the vertices in graph G that
    // are connected to vertex s
    private void dfs(Graph G, int s) {
        validateVertex(s);
        marked[s] = true;
        count++;
        for(int w : G.adj(s)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    /**
     * Is there a path between source vertex {@code s} and vertex {@code v}?
     *
     * @param v the target vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */ 
    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns number of vertices that are connected to source vertex {@code s}.
     *
     * @returns number of vertices that are connected to source vertex {@code s} 
     */
    public int count() {
        return count;
    }

    // unit text code
    // DepthFirstSearch input.txt v
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        int s = Integer.parseInt(args[1]);
        DepthFirstSearch search = new DepthFirstSearch(G, s);

        for(int v = 0; v < G.V(); v++) {
            if (search.marked(v)) {
                StdOut.print(v + " ");
            }
        }

        StdOut.println();
        if (search.count() != G.V()) {
            StdOut.println("NOT connected.");
        } 
        else {
            StdOut.println("connected.");
        }
    }
}
