package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code DepthFirstPaths} represents a data type for finding paths
 * from source vertex to every other vertices in undirected graph.
 */
public class DepthFirstPaths {
    private int[] edgeTo; // edgeTo[v] = last edge on s-v path.
    private boolean[] marked; // marked[v] = is there a s-v path?
    private final int s; // the source vertex;

    public DepthFirstPaths(Graph G, int s) {
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        this.s = s;
        validateVertex(s);
        dfs(G, s);
    }

    /**
     * computes paths from v to ervery vertices in G
     * <em>the order of vertices in path v-s is same as the order that 
     * initializing the graph G.</em>
     */
    private void dfs(Graph G, int v) {
        validateVertex(v);
        marked[v] = true;
        for(int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) 
            throw new IllegalArgumentException(v + " is not between 0 and " + (V-1));
    }
    
    /**
     * Is there a path from s to v?
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns the path from s to v if s connected to v.
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;

        Stack<Integer> path = new Stack<Integer>();
        for(int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }

    // unit test code
    // Execute: java me.alivecode.algs4.DepthFirstPaths input.txt 0
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        int s = Integer.parseInt(args[1]);
        DepthFirstPaths path = new DepthFirstPaths(G, s);

        for(int v = 0; v < G.V(); v++) {
            if (path.hasPathTo(v)) {
                StdOut.print(s + " to " + v + ": ");
                for(int x : path.pathTo(v)) {
                    if (s == x) {
                        StdOut.print(x);
                    }
                    else {
                        StdOut.print("-" + x);
                    }
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d not connected to %d.\n", s, v);
            }
        }
    }
}
