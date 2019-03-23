import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double staT[];
    private double sta_mean;
    private double sta_stddev;
    private int n;

    public PercolationStats(int N,
                            int T) { // perform T independent computational experiments on an N-by-N grid
        staT = new double[T];
        this.n = N;
        int times = 0;
        if (N <= 0) throw new IllegalArgumentException("Out of range");
        if (T <= 0) throw new IllegalArgumentException("Out of range");
        while (times < T) {
            Percolation p = new Percolation(N);
            int count = 0;
            while (true) {
                count++;
                while (true) {
                    int x = StdRandom.uniform(N) + 1;
                    int y = StdRandom.uniform(N) + 1;
                    if (p.isOpen(x, y)) {
                        continue;
                    }
                    else {
                        p.open(x, y);
                        break;
                    }

                }
                if (p.percolates()) {
                    staT[times] = (double) count / ((double) N * (double) N);
                    break;
                }
            }
            times++;
        }
        this.sta_mean = StdStats.mean(staT);
        this.sta_stddev = StdStats.stddev(staT);
    }

    public double mean() {// sample mean of percolation threshold

        return this.sta_mean;
    }

    public double stddev() {// sample standard deviation of percolation threshold
        return this.sta_stddev;
    }

    public double confidenceLo() {// returns lower bound of the 95% confidence interval

        return this.sta_mean - 1.96 * this.sta_stddev / Math.sqrt(n);
    }

    public double confidenceHi() {// returns upper bound of the 95% confidence interval

        return this.sta_mean + 1.96 * this.sta_stddev / Math.sqrt(n);
    }

    public static void main(String[] args) {

        int N = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats percolationStats = new PercolationStats(N, T);
        StdOut.println("mean = " + percolationStats.mean());
        StdOut.println("stddev = " + percolationStats.stddev());
        StdOut.println("95% confidence interval " + percolationStats.confidenceLo() + ", "
                               + percolationStats.confidenceHi());
    }
}
