import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    private double result[];
    private double mean;
    private double stddev;
    private int trials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n<=0 || trials <=0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.trials = trials;
        result = new double[trials];

        for (int i=0; i<trials; i++) {
            int count = 0;
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int x = StdRandom.uniform(1,n+1);
                int y = StdRandom.uniform(1,n+1);

                if (!percolation.isOpen(x, y)) {
                    percolation.open(x, y);
                    count++;
                }
            }

            result[i] = (double)count / (n*n);
        }

        mean = StdStats.mean(result);
        stddev = StdStats.stddev(result);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - 1.96*stddev/Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + 1.96*stddev/Math.sqrt(trials);
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(200, 100);
        System.out.println(percolationStats.mean());
        System.out.println(percolationStats.stddev());
        System.out.println(percolationStats.confidenceLo());
        System.out.println(percolationStats.confidenceHi());
    }
}