/* *****************************************************************************
 *  Name: Laila
 *  Date: 1/20/2019
 *  Description: HW 1: Percolation
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int N;
    private boolean[] ifopen;
    private int numberOfOpen;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf_backwash;


    public Percolation(int N) {// create N-by-N grid, with all sites blocked
        if (N <= 0) throw new IllegalArgumentException("N is <= 0");
        this.N = N;
        int i;
        uf = new WeightedQuickUnionUF((N + 1) * (N) + N + 1);
        uf_backwash = new WeightedQuickUnionUF(N * N + N + 1);
        ifopen = new boolean[(N + 1) * (N) + N + 1];
        numberOfOpen = 0;
        for (i = 1; i <= N; i++) {
            uf.union(1, i);
            uf_backwash.union(1, i);
            ifopen[i] = true;
            uf.union((N + 1) * N + 1, (N + 1) * N + i);
            ifopen[(N + 1) * N + i] = true;
        }
    }

    public void open(int row, int col) {// open site (row, column) if it is not already
        if (row < 1 || row > N) throw new IllegalArgumentException("row index out of bounds");
        if (col < 1 || col > N) throw new IllegalArgumentException("column index out of bounds");

        if (ifopen[row * N + col]) return;
        ifopen[row * N + col] = true;
        numberOfOpen++;

        if (ifopen[(row - 1) * N + col]) {
            uf.union(row * N + col, (row - 1) * N + col);
            uf_backwash.union(row * N + col, (row - 1) * N + col);
        }
        if (ifopen[(row + 1) * N + col]) {
            uf.union(row * N + col, (row + 1) * N + col);
            if (row != N) {
                uf_backwash.union(row * N + col, (row + 1) * N + col);
            }
        }
        if (col != 1 && ifopen[row * N + col - 1]) {
            uf.union(row * N + col, row * N + col - 1);
            uf_backwash.union(row * N + col, row * N + col - 1);
        }
        if (col != N && ifopen[row * N + col + 1]) {
            uf.union(row * N + col, row * N + col + 1);
            uf_backwash.union(row * N + col, row * N + col + 1);
        }

    }

    public boolean isOpen(int row, int col) {// is site (row, column) open?
        if (row < 1 || row > N) throw new IllegalArgumentException("row index out of bounds");
        if (col < 1 || col > N) throw new IllegalArgumentException("column index out of bounds");

        return ifopen[row * N + col];
    }

    public boolean isFull(int row, int col) {// is site (row, column) full?
        if (row < 1 || row > N) throw new IllegalArgumentException("row index out of bounds");
        if (col < 1 || col > N) throw new IllegalArgumentException("column index out of bounds");
        return uf_backwash.connected(row * N + col, 1) && ifopen[row * N + col];
    }

    public boolean percolates() {// does the system percolate?
        return uf.connected(1, (N + 1) * N + 1);
    }

    public int numberOfOpenSites() {
        return numberOfOpen;
    }

}
