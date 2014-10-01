/**
 * 
 * @author offer
 *
 */
public class Percolation {

    /**
     * USe these two sites to connect topRow to topSink, 
     * and bottom row to bottom sink. 
     * This make the use of the UnionFind algorithm much easier and quicker
     */
    private static final int TOP_SINK = 0;
    private final int bottomSink;
    
    private final int N;
    private final boolean[][] openSites;
    private final WeightedQuickUnionUF percolatingUF;
    private final WeightedQuickUnionUF fullUF;

   

    /**
     *  create N-by-N grid, with all sites blocked
     * @param N
     */
    public Percolation(int N) { 
        if (N < 1) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        this.openSites = new boolean[N][N];
        int nSqr = N * N;
        this.bottomSink = nSqr + 1;
        
        this.percolatingUF = new WeightedQuickUnionUF(nSqr + 2);
        this.fullUF = new WeightedQuickUnionUF(nSqr + 1);

    }

    /**
     * // open site (row i, column j) if it is not already
     */
    public void open(int i, int j) {  
        checkIndices(i , j);
        openSites[i-1][j-1] = true;

        int p = ijToIndex(i , j);
        // if cell is a top row cell, then connect it to topSink
        if (i == 1) {
            percolatingUF.union(p, TOP_SINK);
            fullUF.union(p, TOP_SINK);
        }
        // if cell is a bottom row cell, then connect it to bottomSink
        if (i == N) {
            percolatingUF.union(p, bottomSink);
        }

        // if cell up is open too, then connect them
        if (i < N && isOpen(i+1, j)) {
            int index = ijToIndex(i+1 , j);
            percolatingUF.union(p,  index);
            fullUF.union(p, index);
        }
        // if cell down is open too, then connect them
        if (i > 1 && isOpen(i-1, j)) {
            int index = ijToIndex(i-1 , j);
            percolatingUF.union(p,  index);
            fullUF.union(p, index);
        }
        // if cell right is open too, then connect them
        if (j < N && isOpen(i, j+1)) {
            int index = ijToIndex(i , j+1);
            percolatingUF.union(p,  index);
            fullUF.union(p, index);
        }
        // if cell left is open too, then connect them
        if (j > 1 && isOpen(i, j-1)) {
            int index = ijToIndex(i , j-1);
            percolatingUF.union(p,  index);
            fullUF.union(p, index);
        }
    }

    private int ijToIndex(int i, int j) {
        int p = N * (i-1) + j;
        return p;
    }

    /**
     * // is site (row i, column j) open?
     * @param i
     * @param j
     * @return
     */
    public boolean isOpen(int i, int j) {
        checkIndices(i , j);
        return openSites[i-1][j-1];
    }

    /**
     *  // is site (row i, column j) full?
     * @param i
     * @param j
     * @return
     */
    public boolean isFull(int i, int j) { 
        // quick check 1, if site is closed, then it cannot be full
        if (!isOpen(i, j)) {
            return false;
        }
        // long check
        int p = ijToIndex(i, j);
        return fullUF.connected(p, TOP_SINK);
    }

    /**
     *  // does the system percolate?
     * @return
     */
    public boolean percolates() {  
        return percolatingUF.connected(bottomSink, TOP_SINK);
    }

    private void checkIndices(int i, int j) {
        if (i < 1 || j < 1 || i > N || j > N) {
            throw new IndexOutOfBoundsException(); 
        }
    }
}

