import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufBackwash;
    private int virtualTopSite;
    private int virtualBottomSite;
    private int n;
    private boolean[][] gridOpen;
    private int numOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n has to be positive");
        }

        // number of sites for the UF = n * n + 2 (for the two virtual sites)
        this.n = n;
        this.uf = new WeightedQuickUnionUF(n * n + 2);

        // Backwash UF required isFull() could return a false positive
        // when the site is indirectly connected to the virtual top site
        // through the virtual bottom site
        // So we create a new UF that only connects to the top site
        this.ufBackwash = new WeightedQuickUnionUF(n * n + 1);

        // Initialize num of open sites
        this.numOpenSites = 0;

        // Initialize virtual sites
        this.virtualTopSite = n * n;
        this.virtualBottomSite = n * n + 1;

        // Connect virtual top site to the top row and virtual bottom site to the bottom
        // row for the UF
        // Connect virtual top site to the top row of the Backwash UF
        int firstRow = 1;
        int lastRow = n;
        for (int c = 1; c <= n; c++) {
            this.ufBackwash.union(this.virtualTopSite, getCellIndex(firstRow, c));
            this.uf.union(this.virtualTopSite, getCellIndex(firstRow, c));
            this.uf.union(this.virtualBottomSite, getCellIndex(lastRow, c));
        }

        // Mark all cells as not open
        this.gridOpen = new boolean[this.n][this.n];
        for (int r = 0; r < this.n; r++) {
            for (int c = 0; c < this.n; c++) {
                this.gridOpen[r][c] = false;
            }
        }
    }

    /**
     * 1-based row, col
     * converts each pair of (row, col) to a unique integer
     * 
     * @param row
     * @param col
     * @return
     */
    private int getCellIndex(int row, int col) {
        // convert to 0-index
        return (row - 1) * n + (col - 1);
    }

    /**
     * 1-based row, col
     * 
     * @param row
     * @param col
     */
    private void validateRC(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException("1 <= row, col <= n");
        }
    }

    /**
     * opens the site (row, col) if it is not open already
     * 1-based row, col
     * 
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        validateRC(row, col);

        // If already open, don't do anything
        if (this.isOpen(row, col))
            return;

        // Mark open and increment num open sites
        this.gridOpen[row - 1][col - 1] = true;
        this.numOpenSites++;

        // Connect to the 4 sites around this site (connect to
        // only open sites)
        int currCellIndex = getCellIndex(row, col);
        int[][] diffs = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        for (int[] diff : diffs) {
            int dr = diff[0];
            int dc = diff[1];
            int newRow = row + dr;
            int newCol = col + dc;
            int newCellIndex = getCellIndex(newRow, newCol);
            if (newRow >= 1
                    && newRow <= this.n
                    && newCol >= 1
                    && newCol <= this.n
                    && isOpen(newRow, newCol)) {
                this.uf.union(currCellIndex, newCellIndex);
                this.ufBackwash.union(currCellIndex, newCellIndex);
            }
        }
    }

    /**
     * is the site (row, col) open?
     * 1-based row, col
     * 
     * @param row
     * @param col
     */
    public boolean isOpen(int row, int col) {
        validateRC(row, col);
        return this.gridOpen[row - 1][col - 1];
    }

    /**
     * is the site (row, col) full?
     * 1-based row, col
     * 
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) {
        validateRC(row, col);

        // The site is full if it is directly connected to the virtual
        // top site
        // We can know that using the backwash UF
        int currCellIndex = getCellIndex(row, col);
        return this.isOpen(row, col) && (this.ufBackwash.find(currCellIndex) == this.ufBackwash.find(virtualTopSite));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // Corner cases
        if (n == 1) {
            return this.gridOpen[0][0];
        }
        if (n == 2) {
            return (this.gridOpen[0][0] && this.gridOpen[1][0]) || (this.gridOpen[0][1] && this.gridOpen[1][1]);
        }

        // See if the virtual top site is connected to the virtual bottom site
        return this.uf.find(this.virtualTopSite) == this.uf.find(this.virtualBottomSite);
    }
}
