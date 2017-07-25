package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import sun.security.krb5.internal.rcache.DflCache;

/**
 * The {@code DepthFirstOrder} class represents a data type
 * for computing the order of visiting vertices in the specified directed graph.
 */
public class DepthFirstOrder {
    private boolean[] marked;
    private int[] pre; // see preCount method
    private int[] post; // see postCount method
    private Queue<Integer> preOrder; // see preOrder method
    private Queue<Integer> postOrder; // see post order method
    private int preOrderCount; // see dfs
    private int postOrderCount; // see dfs

    /**
     * Computes the order of visiting vertices in the specified digraph.
     *
     * @param G the directed graph.
     */
    public DepthFirstOrder(Digraph G) {
        marked = new boolean[G.V()];
        pre = new int[G.V()];
        post = new int[G.V()];
        preOrder = new Queue<>();
        postOrder = new Queue<>();

        for(int v = 0; v < G.V(); v++) {
            if(!marked[v]) {
                dfs(G, v);
            }
        }
    }

    /**
     * Computes the order of visiting vertices in the specified edge-weighted digraph.
     *
     * @param G the edge-weighted digraph
     */
    public DepthFirstOrder(EdgeWeightedDigraph G) {
        marked = new boolean[G.V()];
        pre = new int[G.V()];
        post = new int[G.V()];
        preOrder = new Queue<>();
        postOrder = new Queue<>();

        for(int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(Digraph DG, int v) {
        marked[v] = true;
        pre[v] = preOrderCount++;
        preOrder.enqueue(v);

        for(int w: DG.adj(v)) {
            if(!marked[w]) {
                dfs(DG, w);
            }
        }

        post[v] = postOrderCount++;
        postOrder.enqueue(v);
    }

    private void dfs(EdgeWeightedDigraph G, int v) {
        marked[v] = true;
        pre[v] = preOrderCount++;
        preOrder.enqueue(v);

        for(DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if(!marked[w]) {
                dfs(G, w);
            }
        }

        post[v] = postOrderCount++;
        postOrder.enqueue(v);
    }

    /**
    * Returns the number of vertices being visited before vertex v.
    *
    * @param v the vertex
    * @return the number of vertices being visited before vertex v
    */
    public int preCount(int v) {
        validateVertex(v);
        return pre[v];
    }

    /**
     * Returns the number of vertices being visited after vertex v.
     *
     * @param v the vertex
     * @return the number of vertices being visited after vertex v
     */
    public int postCount(int v) {
        validateVertex(v);
        return post[v];
    }

    /**
    * Returns the vertices in order of
    * before visiting the vertices in the specified directed graph.
    *
    * @return the vertices in order of
    * before visiting the vertices in the specified directed graph.
    */
    public Iterable<Integer> preOrder() {
        return preOrder;
    }

    /**
    * Returns the vertices in order of
    * post visiting the vertices in the specified directed graph.
    *
    * @return the vertices in order of
    * post visiting the vertices in the specified directed graph.
    */
    public Iterable<Integer> postOrder() {
        return postOrder;
    }

    /**
     * Returns the vertices in reverse order of
     * post visiting the vertices in the specified directed graph.
     *
     * @return the vertices in reverse order of
     * post visiting the vertices in the specified directed graph.
     */
    public Iterable<Integer> reversePostOrder() {
        Stack<Integer> stack = new Stack<>();
        for(int v : postOrder) {
            stack.push(v);
        }
        return stack;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= marked.length) throw new IllegalArgumentException();
    }

    // unit test code
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph DG = new Digraph(in);
        DepthFirstOrder dfo = new DepthFirstOrder(DG);

        StdOut.printf("%4s%10s%10s\n", "v", "preCount", "postCount");
        StdOut.println("------------------------");
        for(int v = 0; v < DG.V(); v++) {
            StdOut.printf("%4d%10d%10d\n", v, dfo.preCount(v), dfo.postCount(v));
        }

        StdOut.print("pre order:");
        for(int v: dfo.preOrder()) {
            StdOut.print(" " + v);
        }
        StdOut.println();

        StdOut.print("post order:");
        for(int v: dfo.postOrder()) {
            StdOut.print(" " + v);
        }
        StdOut.println();

        StdOut.print("reverse post order:");
        for(int v: dfo.reversePostOrder()) {
            StdOut.print(" " + v);
        }
        StdOut.println();

    }
}
