package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code CPM} class provides a client that resolves parallel
 * precedence-constrained job scheduling problem via
 * <em>critical path method</em>. It reduces the problem to
 * longest-paths problem in edge-weighted DAGs.
 */
public class CPM {

    private CPM() {}

    /**
     * Reads the precedence constraints from standard input
     * and prints a feasible schedule to standard output.
     * CMP jobsPC.txt
     *
     * Input:
     10
     41.0  3  1 7 9
     51.0  1  2
     50.0  0
     36.0  0
     38.0  0
     45.0  0
     21.0  2  3 8
     32.0  2  3 8
     32.0  1  2
     29.0  2  4 6

     * Output:
      job   start  finish
     --------------------
        0     0.0    41.0
        1    41.0    92.0
        2   123.0   173.0
        3    91.0   127.0
        4    70.0   108.0
        5     0.0    45.0
        6    70.0    91.0
        7    41.0    73.0
        8    91.0   123.0
        9    41.0    70.0
     Finish time:   173.0
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);

        int n = in.readInt();
        int source = 2 * n;
        int sink = 2 * n + 1;

        // build the network
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(2 * n + 2);
        for(int i = 0; i < n; i++) {
            double duration = in.readDouble();
            G.addEdge(new DirectedEdge(i, i+n, duration));
            G.addEdge(new DirectedEdge(source, i, 0.0));
            G.addEdge(new DirectedEdge(i+n, sink, 0.0));

            int m = in.readInt();
            for(int j = 0; j < m; j++) {
                int precedent = in.readInt();
                G.addEdge(new DirectedEdge(i+n, precedent, 0.0));
            }
        }

        AcyclicLP lp = new AcyclicLP(G, source);

        StdOut.println(" job   start  finish");
        StdOut.println("--------------------");
        for(int i = 0; i < n; i++) {
            StdOut.printf("%4d  %6.1f  %6.1f\n", i, lp.distTo(i), lp.distTo(i+n));
        }
        StdOut.printf( "Finish time:  %6.1f\n", lp.distTo(sink));
    }
}
