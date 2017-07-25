package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code NonrecursiceDFS} class represents a data type
 * for determining if the specified vertex connected to any other
 * vertices in the specified graph using nonrecursive method.
 */
public class NonrecursiveDFS {
    private boolean[] marked;

    /**
     * Finds vertices connected to source vertex {@code s}.
     *
     * @param G the graph.
     * @param s the source vertex.
     */
    public NonrecursiveDFS(Graph G, int s) {
        marked = new boolean[G.V()];

        dfs(G, s);
    }

    private void dfs(Graph G, int s) {
        marked[s] = true;
        Stack<Integer> stack = new Stack<>();
        stack.push(s);

        while(!stack.isEmpty()) {
            int v = stack.pop();
            for(int w: G.adj(v)) {
                if(!marked[w]) {
                    marked[w] = true;
                    stack.push(w);
                }
            }
        }
    }

    /**
     * Is vertex {@code v} connected to the source vertex.
     *
     * @param v the vertex
     * @return {@code true} if {@code v} connected the source vertex,
     * {@code false} otherwise
     */
    public boolean connected(int v) {
        validateVertex(v);
        return marked[v];
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException();
        }
    }

    // unit test code
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        int s = Integer.parseInt(args[1]);
        NonrecursiveDFS dfs = new NonrecursiveDFS(G, s);

        for(int v = 0; v < G.V(); v++) {
            if(dfs.connected(v)) {
                StdOut.print(v + " ");
            }
        }
        StdOut.println();

    }
}
