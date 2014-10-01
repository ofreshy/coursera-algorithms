package uf;


public class QuickFind implements UnionFind {

	private final int[] id;

	
	public QuickFind(int N) {
		id  = new int[N];
		for ( int i=0; i<N; i++){
			id[i] = i;
		}
	}

	@Override
	public void union(int p, int q) {
		if ( !connected(p,q) ){
			int pid = id[p];
			int qid = id[q];
			for ( int i=0; i<id.length; i++){
				if (pid == id[i]){
					id[i] = qid;
				}
			}
		}
	}

	@Override
	public boolean connected(int p, int q) {
		return id[p] == id[q];
	}
	
	@Override
	public String toString(){
	    StringBuilder sb = new StringBuilder("");
	    for ( int i : id ){
	        sb.append(i).append(' ');
	    }
	    return sb.toString();
	}

}
