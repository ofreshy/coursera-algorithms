package uf;

public class QuickUnion implements UnionFind {

	private final int[] id;
	
	
	public QuickUnion(int N) {
		id  = new int[N];
		for ( int i=0; i<N; i++){
			id[i] = i;
		}
	}
	
	private int root(int i){
		if ( i == id[i]){
			return i;
		}
		else{
			return root(id[i]);
		}
	}

	@Override
	public void union(int p, int q) {
		int rootP = root(p);
		int rootQ = root(q);
		id[rootP] = rootQ;
	}

	@Override
	public boolean connected(int p, int q) {
		return root(p) == root(q);
	}

}
