import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private WeightedQuickUnionUF weightedQuickUnionUF2;
    private int n;
    private boolean [][] block;
    private int open;
    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.open = 0;
        this.n = n;
        block = new boolean[n][n];
        weightedQuickUnionUF = new WeightedQuickUnionUF(n*n+2);
        weightedQuickUnionUF2 = new WeightedQuickUnionUF(n*n+1);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new java.lang.IllegalArgumentException();
        }

        if (isOpen(row, col)) {
            return;
        }

        block[row-1][col-1] = true;
        open++;

        if (row == 1) {
            weightedQuickUnionUF.union((row-1)*n+col, 0);
            weightedQuickUnionUF2.union((row-1)*n+col, 0);
        }
        if (row == n) {
            weightedQuickUnionUF.union((row-1)*n+col, n*n+1);
        }

        union(row, col, weightedQuickUnionUF);
        union(row, col, weightedQuickUnionUF2);
    }

    private void union(int row, int col, WeightedQuickUnionUF uf) {
        if (col-1 > 0 && block[row-1][col-2] == true) {
            uf.union((row-1)*n + col, (row-1)*n + col-1);
        }

        if (col-1 < n-1 && block[row-1][col] == true) {
            uf.union((row-1)*n + col, (row-1)*n + col+1);
        }

        if (row-1 > 0 && block[row-2][col-1] == true) {
            uf.union((row-1)*n + col, (row-2)*n + col);
        }

        if (row-1 < n-1 && block[row][col-1] == true) {
            uf.union((row-1)*n + col, row*n + col);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new java.lang.IllegalArgumentException();
        }

        return block[row-1][col-1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new java.lang.IllegalArgumentException();
        }

        return isOpen(row, col) && weightedQuickUnionUF2.connected(0, (row-1) * n + col);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.connected(0, n*n+1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        percolation.open(1,5);
        percolation.open(2,5);
        percolation.open(3,5);
        //percolation.open(4,5);
        percolation.open(5,5);

        System.out.println(percolation.isOpen(1,5));
        System.out.println(percolation.isOpen(2,5));
        System.out.println(percolation.isOpen(3,5));
        System.out.println(percolation.isOpen(4,5));
        System.out.println(percolation.isOpen(5,5));

        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.percolates());
    }
}