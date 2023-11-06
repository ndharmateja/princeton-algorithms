import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int n;
    private double[] probs;
    private int trials;

    private static double CONFIDENCE_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n, trials > 0");
        }
        this.n = n;
        this.probs = new double[trials];
        this.trials = trials;

        for (int i = 0; i < trials; i++) {
            probs[i] = simulate();
        }
    }

    // Do one simulation for one percolation
    private double simulate() {
        Percolation percolation = new Percolation(this.n);
        while (!percolation.percolates()) {
            int row = StdRandom.uniformInt(1, this.n + 1);
            int col = StdRandom.uniformInt(1, this.n + 1);
            percolation.open(row, col);
        }
        return (1.0 * percolation.numberOfOpenSites()) / (this.n * this.n);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.probs);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(probs);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - (CONFIDENCE_95 * this.stddev()) / Math.pow(this.trials, 0.5);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (CONFIDENCE_95 * this.stddev()) / Math.pow(this.trials, 0.5);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}