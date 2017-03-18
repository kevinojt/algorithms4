package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code DirectedCycle} class checks
 * if the specified directed graph has any cycle.
 */
public class DirectedCycle {
    private Stack<Integer> cycle;
    private boolean[] marked;
    private int[] edgeTo;
    private boolean[] onStack; // onStack[w] = is w on the path to v?

    /**
     * Find cycle in the specified directed cycle.
     *
     * @param DG the directed graph
     */
    public DirectedCycle(Digraph DG) {
        marked = new boolean[DG.V()];
        edgeTo = new int[DG.V()];
        onStack = new boolean[DG.V()];

        for(int v = 0; v < DG.V(); v++) {
            if (!marked[v]) {
                dfs(DG, v);
            }
        }
    }

    /*
        in the directed graph below has directed cycle 3->1->4->3
        5
        5
        0 2
        1 4
        4 3
        3 1
        4 0

        vertex 0 will be marked first and not in any cycle.
        'onStack' make sure marked vertices like vertex 0
        will not make unclosed cycle.
     */
    private void dfs(Digraph DG, int v) {
        marked[v] = true;
        onStack[v] = true;

        for(int w: DG.adj(v)) {
            if (cycle != null) return;;
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(DG, w);
            }
            else if(onStack[w]) {
                cycle = new Stack<>();
                for(int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
            }

        }
        onStack[v] = false;
    }

    /**
     * Does the directed graph have cycle?
     *
     * @return {@code true} if the specified directed graph has cycle,
     * {@code false} otherwise.
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Returns the first found directed cycle if the directed graph has any cycle,
     * {@code null} otherwise.
     *
     * @return the first found directed cycle if the directed graph has any cycle,
     * {@code null} otherwise.
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }

    // unit test
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph DG = new Digraph(in);
        DirectedCycle dc = new DirectedCycle(DG);
        if (dc.hasCycle()) {
            for (int v: dc.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("The digraph has no cycle.");
        }

    }
}
