public class PercolationStats {

    private final double [] ps;
    private final int N;
    private final double gridSize;


    private double mean;
    private double stddev;
    private double conHi;
    private double conLo;

    /**
     *
     * @param N
     * @param T
     */
    public PercolationStats(final int N, final int T) {    
        if (N < 1 || T < 1) {
            throw new IllegalArgumentException();
        }
        // perform T independent computational experiments on an N-by-N grid
        this.N = N;
        this.gridSize = N * N;
        ps = new double[T];
        for (int i = 0; i < T; i++) {
            double p = runExperiment();
            ps[i] = p;
        }
        this.mean = StdStats.mean(ps);
        this.stddev = StdStats.stddev(ps);
        double con = (1.96 * stddev)/ Math.sqrt(T);
        this.conHi = mean + con;
        this.conLo = mean - con;
    }

    private double runExperiment() {
        Percolation percolation = new Percolation(N);
        int numberOfOpenSites = 0;
        do {
            numberOfOpenSites++;
            int i = StdRandom.uniform(N) + 1;
            int j = StdRandom.uniform(N) + 1;
            percolation.open(i, j);
        } while(!percolation.percolates());


        return numberOfOpenSites/ gridSize;

    }


    public double mean() {                   
        // sample mean of percolation threshold
        return mean;
    }
    public double stddev() {                 
        // sample standard deviation of percolation threshold
        return stddev;
    }
    public double confidenceLo() {
        return conLo;
    }
    public double confidenceHi() {         
        // returns upper bound of the 95% confidence interval
        return conHi;
    }
    public static void main(String[] args) {   // test client, described below
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(N, T);
        System.out.println(" N = " + N + " , T = " + T);

        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        String msg = stats.confidenceLo() + "," + stats.confidenceHi();
        System.out.println("95% confidence interval = " + msg);

    }
}
