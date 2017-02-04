package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


/**
 * The {@code DirectedDFS} class represents a data type
 * for determining paths from source vertex{@code s} to 
 * every other vertices in digraph {@code G}.
 */
public class DirectedDFS {
    private boolean marked[];
    private int edgeTo[];
    private int s;
    private int count;

    /**
     * Computes the vertices in digraph {@code G} that are reachable from
     * source vertex {@code s}
     */
    public DirectedDFS(Digraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        count = 0;
        this.s = s;
        validateVertex(s);
        dfs(G, s);
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) 
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    private void dfs(Digraph G, int v) {
        validateVertex(v);
        count++;
        marked[v] = true;
        for(int w: G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    /**
     * Is there a s-v path?
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Rerturns vertices on s-v path.
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        int x;
        for(x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(x); // x=s
        return path;
    }

    /**
     * Returns number of vertices reachable from source vertex.
     */
    public int count() {
        return count;
    }

    // unit test code
    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = Integer.parseInt(args[1]);
        Digraph G = new Digraph(in);
        DirectedDFS path = new DirectedDFS(G, s);

        for(int v = 0; v < G.V(); v++) {
            if (path.hasPathTo(v)) {
                StdOut.print(s + " to " + v + ": ");
                for(int w : path.pathTo(v)) {
                    if (w == s) {
                        StdOut.print(w);
                    }
                    else {
                        StdOut.print("-" + w);
                    }
                }
                StdOut.println();
            }
            else {
                StdOut.println(s + " not connected to " + v);
            }
        }
    }
} 

            
