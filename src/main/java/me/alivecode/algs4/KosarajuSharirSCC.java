package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by oujt on 17-2-21.
 */
public class KosarajuSharirSCC {
    private boolean[] marked;
    private int count;
    private int[] id;

    public KosarajuSharirSCC(Digraph DG) {
        DepthFirstOrder dfo = new DepthFirstOrder(DG.reverse());

        marked = new boolean[DG.V()];
        id = new int[DG.V()];
        for(int v: dfo.reversePostOrder()) {
            if (!marked[v]) {
                dfs(DG, v);
                count++;
            }
        }
    }

    private void dfs(Digraph DG, int v) {
        marked[v] = true;
        id[v] = count;

        for (int w : DG.adj(v)) {
            if (!marked[w]) {
                dfs(DG, w);
            }
        }
    }

    public int count() {
        return count;
    }

    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    public boolean stronglyConnected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id[v] == id[w];
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= marked.length) throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph DG = new Digraph(in);
        KosarajuSharirSCC scc = new KosarajuSharirSCC(DG);

        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[scc.count()];
        for(int i = 0; i < scc.count(); i++) {
            components[i] = new Queue<>();
        }
        for(int v = 0; v < DG.V(); v++) {
            components[scc.id(v)].enqueue(v);
        }

        StdOut.println(scc.count() + " strongly connected components:");
        for(int i = 0; i < scc.count(); i++) {
            for(int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }
}
