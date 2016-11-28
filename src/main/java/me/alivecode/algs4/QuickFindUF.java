package me.alivecode.algs4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class QuickFindUF {
    int[] id;
    int count;
    
	public QuickFindUF(int n) {
		id = new int[n];
		for(int i = 0; i < n; i++) {
			id[i] = i;
		}	
		count = n;
	}

	private void validate(int p){
		int n = id.length;
		if (p < 0 || p >= n) {
			throw new IndexOutOfBoundsException("p must between 0 and " + (n-1) + ".");
		}	
	}

	public int find(int p) {
		validate(p);
		return id[p];
	}

	public boolean connected(int p, int q) {
		validate(p);
		validate(q);
		return id[p] == id[q];
	}
	
	public void union(int p, int q) {
		validate(p);
		validate(q);
		int pID = id[p];
		int qID = id[q];

		if (pID == qID) return;
		
		for(int i = 0; i < id.length; i++) {
			if (id[i] == pID) {
				id[i] = qID;
			}	
		}
		count--;
	}

	public int count() { return count; } 

	public static void main(String[] args) {
		int n = StdIn.readInt();
		QuickFindUF uf = new QuickFindUF(n);

		while(! StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();	
			if (uf.connected(p, q)) { continue; }	
			uf.union(p, q);	
			StdOut.println(p + " + " + q);
		}

		StdOut.println(uf.count() + " components.");
		
	}
}
