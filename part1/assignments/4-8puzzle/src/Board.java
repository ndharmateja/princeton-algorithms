import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;

public class Board {
    private int[][] tiles;
    private int n;
    private int zeroRow;
    private int zeroCol;
    private int hamming;
    private int manhattan;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        // Create tiles copy and compute distances
        // and update zero row and col
        // and update manhattan and hamming distances
        n = tiles.length;
        this.tiles = new int[n][n];
        manhattan = 0;
        hamming = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                int tile = tiles[r][c];
                this.tiles[r][c] = tile;
                if (tile == 0) {
                    zeroRow = r;
                    zeroCol = c;
                    continue;
                }
                hamming += tile != goalTile(r, c) ? 1 : 0;
                manhattan += Math.abs(r - getGoalRow(tile));
                manhattan += Math.abs(c - getGoalCol(tile));
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(n);
        builder.append("\n");
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                builder.append(" " + tiles[r][c] + " ");
            }
            builder.append("\n");
        }
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }

    /**
     * Eg: 3x3 goal board
     * 1 2 3
     * 4 5 6
     * 7 8 0
     * 
     * (0, 0) -> 1
     * (0, 2) -> 3
     * (1, 2) -> 6
     * (2, 2) -> 0
     * 
     * @param r
     * @param c
     * @return the expected tile in the goal board of size n
     */
    private int goalTile(int r, int c) {
        if (r == n - 1 && c == n - 1)
            return 0;
        return r * n + c + 1;
    }

    /**
     * Eg: 3x3 goal board
     * 1 2 3
     * 4 5 6
     * 7 8 0
     * 
     * 1 -> 0
     * 3 -> 0
     * 8 -> 2
     * 4 -> 1
     * 
     * @param tile
     * @return
     */
    private int getGoalRow(int tile) {
        if (tile == 0)
            return n - 1;
        return (tile - 1) / n;
    }

    /**
     * Eg: 3x3 goal board
     * 1 2 3
     * 4 5 6
     * 7 8 0
     * 
     * 1 -> 0
     * 3 -> 2
     * 8 -> 1
     * 
     * @param tile
     * @return
     */
    private int getGoalCol(int tile) {
        if (tile == 0)
            return n - 1;
        return (tile - 1) % n;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming == 0;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object obj) {
        // If the objects are the same, they are equal.
        if (this == obj)
            return true;

        // If the object is null or of a different class, they are not equal.
        if (obj == null || getClass() != obj.getClass())
            return false;

        Board that = (Board) obj;
        return this.n == that.n && Arrays.deepEquals(this.tiles, that.tiles);
    }

    // Swap tiles at (r1, c1) and (r2, c2)
    private void swapTiles(int r1, int c1, int r2, int c2) {
        int tmp = tiles[r1][c1];
        tiles[r1][c1] = tiles[r2][c2];
        tiles[r2][c2] = tmp;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        int[][] diffs = new int[][] { { 0, -1 }, { -1, 0 }, { 0, +1 }, { +1, 0 } };
        for (int[] diff : diffs) {
            int dr = diff[0];
            int dc = diff[1];

            int neighborRow = zeroRow + dr;
            int neighborCol = zeroCol + dc;

            if (neighborRow >= 0 && neighborRow < n && neighborCol >= 0 && neighborCol < n) {
                // swap zero with neighbour
                // add neighbour
                // swap back
                swapTiles(zeroRow, zeroCol, neighborRow, neighborCol);
                neighbors.add(new Board(tiles));
                swapTiles(zeroRow, zeroCol, neighborRow, neighborCol);
            }
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles (deterministically)
    // this.equals(this.twin().twin()) should return true
    public Board twin() {
        int r1 = 0;
        int c1 = 0;
        int r2 = 0;
        int c2 = 1;

        // If (0, 0) -> 0, then exchange (1, 0) and (1, 1)
        if (tiles[0][0] == 0) {
            r1 = 1;
            c1 = 0;
            r2 = 1;
            c2 = 1;
        }

        // If (0, 1) -> 0, then exchange (0, 0) and (1, 0)
        else if (tiles[0][1] == 0) {
            r2 = 1;
            c2 = 0;
        }

        // Otherwise exchange (0, 0) and (0, 1)

        // Swap tiles, create twin and swap tiles back
        swapTiles(r1, c1, r2, c2);
        Board twin = new Board(tiles);
        swapTiles(r1, c1, r2, c2);
        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In("data/puzzle09.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        // solve the slider puzzle
        Board board = new Board(tiles);
        Board board2 = new Board(tiles);
        System.out.println(board);
        System.out.println(board == board2);
        System.out.println(board.equals(board2));

        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor);
        }
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor);
        }

        System.out.println(board.twin());
        System.out.println(board.twin());
        System.out.println(board.twin());
    }
}