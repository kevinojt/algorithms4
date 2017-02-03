package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code CC} class represents a data type for 
 * determining connected components in an undirected graph.
 */
public class CC {
    private boolean[] marked; // marked[v] = did v has been visisted?
    private int[] id; // id[v] = the id of the component that vertex v belongs to
    private int[] size; // size[id[v]] = size of the component id[v]
    private int count; // number of components in the graph

    /**
     * Computes connected components in the undirected graph {@code G}
     */
    public CC(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        size = new int[G.V()];
        count = 0;
        for(int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    } 

    // depth first search
    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        size[count]++;
        for(int w: G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    /**
     * Returns the number of connected components in the graph.
     *
     */
    public int count() {
        return count;
    }

    /**
     * Returns the size of the connected component that vertex {@code v} belongs to.
     */
    public int size(int v) {
        validateVertex(v);
        return size[id[v]];
    }

    /**
     * Returns the id of the connected component that vertex {@code v} belongs to.
     */
    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    /**
     * Is there a v-w path?
     */
    public boolean connected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id[v] == id[w];
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) 
            throw new IllegalArgumentException("Vertex " + v + " is not between 0 and " + (V-1));
    }

    // unit test code
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        CC cc = new CC(G);
        StdOut.println(cc.count() + " component(s) in the graph");
        for(int id = 0; id < cc.count(); id++) {
            StdOut.print("component " + id + ":");
            for(int v = 0; v < G.V(); v++) {
                if (cc.id(v) == id) {
                    StdOut.print(" " + v);
                }
            }
            StdOut.println();
        }
    }
}



