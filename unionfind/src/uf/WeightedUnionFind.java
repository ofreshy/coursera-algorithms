package uf;

public class WeightedUnionFind implements UnionFind {
	private int[] id;    // id[i] = parent of i
	private int[] sz;    // sz[i] = number of objects in subtree rooted at i
	private int count;   // number of components

	/**
	 * Initializes an empty union-find data structure with N isolated components 0 through N-1.
	 * @throws java.lang.IllegalArgumentException if N < 0
	 * @param N the number of objects
	 */
	public WeightedUnionFind(int N) {
		count = N;
		id = new int[N];
		sz = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
			sz[i] = 1;
		}
	}

	/**
	 * Returns the number of components.
	 * @return the number of components (between 1 and N)
	 */
	public int count() {
		return count;
	}

	/**
	 * Returns the component identifier for the component containing site <tt>p</tt>.
	 * @param p the integer representing one site
	 * @return the component identifier for the component containing site <tt>p</tt>
	 * @throws java.lang.IndexOutOfBoundsException unless 0 <= p < N
	 */
	public int find(int p) {
		while (p != id[p])
			p = id[p];
		return p;
	}

	/**
	 * Are the two sites <tt>p</tt> and <tt>q</tt> in the same component?
	 * @param p the integer representing one site
	 * @param q the integer representing the other site
	 * @return <tt>true</tt> if the two sites <tt>p</tt> and <tt>q</tt>
	 *    are in the same component, and <tt>false</tt> otherwise
	 * @throws java.lang.IndexOutOfBoundsException unless both 0 <= p < N and 0 <= q < N
	 */
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}


	/**
	 * Merges the component containing site<tt>p</tt> with the component
	 * containing site <tt>q</tt>.
	 * @param p the integer representing one site
	 * @param q the integer representing the other site
	 * @throws java.lang.IndexOutOfBoundsException unless both 0 <= p < N and 0 <= q < N
	 */
	public void union(int p, int q) {
		int rootP = find(p);
		int rootQ = find(q);
		if (rootP == rootQ) return;

		// make smaller root point to larger one
		if   (sz[rootP] < sz[rootQ]) { id[rootP] = rootQ; sz[rootQ] += sz[rootP]; }
		else                         { id[rootQ] = rootP; sz[rootP] += sz[rootQ]; }
		count--;
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
