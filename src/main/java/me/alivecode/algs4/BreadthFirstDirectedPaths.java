package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code BreadthFirstDirectedPaths} represents a data type 
 * for finding paths from source vertex to every other vetices in digraph.
 */
public class BreadthFirstDirectedPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;
    private int[] distTo;
    private int[] edgeTo;

    /**
     * Computes paths and distance from vertex {@code s} to every
     * other vertices in digraph {@code DG}.
     */
    public BreadthFirstDirectedPaths(Digraph DG, int s) {
        marked = new boolean[DG.V()];
        distTo = new int[DG.V()];
        for(int v = 0; v < DG.V(); v++) {
            distTo[v] = INFINITY;
        }
        edgeTo = new int[DG.V()];
        validateVertex(s);
        bfs(DG, s);
    }

    private void bfs(Digraph DG, int s) {
        validateVertex(s);

        marked[s] = true;
        distTo[s] = 0;

        Queue<Integer> q = new Queue<Integer>();
        q.enqueue(s);
        while(!q.isEmpty()) {
            int v = q.dequeue();
            for(int w: DG.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    q.enqueue(w);
                }
            }
        }
    }
                
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) 
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Is there a s to v path ?
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns the shortest path from s to v.
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Integer> stack = new Stack<Integer>();
        int x;
        for(x = v; distTo[x] != 0 ; x = edgeTo[x]) {
            stack.push(x);
        }
        stack.push(x); // x = srouce vertex
        return stack;
    }

    /**
     * Returns number of vertices on s-v path.
     */
    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    // unit test code
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph DG = new Digraph(in);
        int s = Integer.parseInt(args[1]);
        BreadthFirstDirectedPaths path = new BreadthFirstDirectedPaths(DG, s);
        
        for(int v = 0; v < DG.V(); v++) {
            if (path.hasPathTo(v)) {
                StdOut.print(s + " to " + v + "(" + path.distTo(v) + "):");
                for(int w : path.pathTo(v)) {
                    if (path.distTo(w) == 0) { // source vertex
                        StdOut.print(w);
                    }
                    else {
                        StdOut.print("->" + w);     
                    }
                }
                StdOut.println();
            }
            else {
                StdOut.println(s + " to " + v + "(-): not connected");
            }
        }
    }
}

