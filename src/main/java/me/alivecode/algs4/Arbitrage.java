/*****************************
 input example
 3
 USD 1        6.8890  7.7666
 CNY 0.1452   1       1.2740
 HKD 0.1288   0.8870  1

 Execution: Arbitrage rates.txt 0
 */

package me.alivecode.algs4;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code Arbitrage} class provides a client that finds an arbitrage
 * opportunity in a currency exchange table by constructing a
 * complete-digraph representation of the exchange table and then finding
 * a negative cycle in the digraph.
 */
public class Arbitrage {
    private Arbitrage() {}

    /**
     * Provides a client that find an arbitrage opportunity
     * in a currency exchange.
     * @param args args[0] the exchange table file path
     *             args[1] the vertex representing one currency
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int V = in.readInt();
        String[] names = new String[V];
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);

        for(int v = 0; v < V; v++) {
            names[v] = in.readString();
            for(int w = 0; w < V; w++) {
                Double rate = in.readDouble();
                DirectedEdge e = new DirectedEdge(v, w, -Math.log(rate));
                G.addEdge(e);
            }
        }

        int s = Integer.parseInt(args[1]);
        BellmanFordSP spt = new BellmanFordSP(G, s);

        if (spt.hasNegativeCycle()) {
            StdOut.println("Found arbitrage opportunity");
            double stake = 1000.0;
            for(DirectedEdge e : spt.negativeCycle()) {
                StdOut.printf("%10.6f %s = ", stake, names[e.from()]);
                stake *= Math.exp(-e.weight());
                StdOut.printf("%10.6f %s\n", stake, names[e.to()]);
            }
        }
        else {
            StdOut.println("No arbitrage opportunity");
        }


    }
}
