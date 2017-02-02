package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code BreadthFirstPaths} class represents a data type for
 * finding shortest path from source vertex to erery other vertices in Graph G.
 */
public class BreadthFirstPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked; // marked[v] = is there a s-v path?
    private int[] edgeTo; // edgeTo[v] = previous edge on shortest s-v path.
    private int[] distTo; // distTo[v] = number of edges on shortest s-v path.

    /**
     * Computes shortest path from {@code s} to
     * erery other vertices in Graph {@code G}.
     */
    public BreadthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        distTo = new int[G.V()];
        validateVertex(s);
        bfs(G, s);

        assert check(G, s);
    }
   
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) 
           throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    // finds shortest s-v path by
    // searching s's every adjacent vertices and their adjacent vertices
    // untill all vertices in G been visited.
    private void bfs(Graph G, int s) {
        Queue<Integer> queue = new Queue<Integer>();
        for(int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }
        distTo[s] = 0;
        marked[s] = true;
        queue.enqueue(s);
        
        while(!queue.isEmpty()) {
            int v = queue.dequeue();
            for(int w : G.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    queue.enqueue(w);
                }
            }
        }
    }
    
    private boolean check(Graph G, int s) {
        // check if diskTo[s] = 0;
        if (distTo[s] != 0) return false;

        // check if distTo[v] + 1 <= distTo[w]
        // distTo[v] is distance from s to v
        // distTo[w] is distance from s to w. w is adjance to v
        // Provided v is reachable from s
        for(int v = 0; v < G.V(); v++) {
            for(int w : G.adj(v)) {
                if (hasPathTo(v) != hasPathTo(w)) return false;
                if (hasPathTo(v) && distTo[w] > distTo[v] + 1) return false;
            }
        }

        // check if dist[w] = dist[v] + 1 on path s-...-v-w 
        for(int w = 0; w < G.V(); w++) {
            if (!hasPathTo(w) || w == s) continue;
            int v = edgeTo[w];
            if (distTo[w] != distTo[v] + 1) return false;
        }

        return true;
    }

    /**
     * Is there a s-v path?
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns vertices on s-v path.
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;

        Stack<Integer> stack = new Stack<Integer>();
        int x;
        for(x = v; distTo[x] != 0; x = edgeTo[x]) {
            stack.push(x);
        }
        stack.push(x);
        return stack;
    }

    /**
     * Returns numbers of edges on s-v path.
     */
    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }
    
    // unit test code.
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        int s = Integer.parseInt(args[1]);
        BreadthFirstPaths path = new BreadthFirstPaths(G, s);
        for(int v = 0; v < G.V(); v++) {
            if (path.hasPathTo(v)) {
                StdOut.print(s + " to " + v + ": ");
                for (int w : path.pathTo(v)) {
                    if (s == w) {
                        StdOut.print(w);
                    }
                    else {
                        StdOut.print("-" + w);
                    }
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d is not connected to %d\n", s, v);
            }
        }
    }
}




