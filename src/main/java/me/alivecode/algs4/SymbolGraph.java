package me.alivecode.algs4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code SymbolGraph} represents a data type
 * combine with named vertices and edges.
 */
public class SymbolGraph {
    private BST<String, Integer> st;
    private String[] keys;
    private Graph graph;

    public SymbolGraph(String filePath, String delim) {
        st = new BST<>();
        In in = new In(filePath);
        while(!in.isEmpty()) {
            String[] a = in.readLine().split(delim);
            for (int i = 0; i < a.length; i++) {
                if (!st.contains(a[i])) {
                    st.put(a[i], st.size());
                }
            }
        }

        keys = new String[st.size()];
        for(String s : st.keys()) {
            keys[st.get(s)] = s;
        }

        graph = new Graph(st.size());
        in = new In(filePath);
        while(in.hasNextLine()) {
            String[] a = in.readLine().split(delim);
            for(int i = 1; i < a.length; i++) {
                graph.addEdge(st.get(a[0]), st.get(a[i]));
            }
        }

    }

    public String name(int v) {
        validateVertex(v);
        return keys[v];
    }

    public int index(String name) {
        return st.get(name);
    }

    public boolean contains(String name) {
        return st.contains(name);
    }

    private void validateVertex(int v) {
        int V = keys.length;
        if (v < 0 || v >= V) throw new IllegalArgumentException();
    }

    public Graph graph() {
        return graph;
    }

    public static void main(String[] args) {
        SymbolGraph sg = new SymbolGraph(args[0], args[1]);

        for(int v = 0; v < sg.graph().V(); v++) {
            StdOut.println(sg.name(v));
            for(int w : sg.graph().adj(v)) {
                StdOut.println("    " + sg.name(w));
            }
        }
    }
}
