import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private Board initial;
    private SearchNode destination;
    private ArrayList<Board> path;

    // Node class for the priority queue
    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        SearchNode parent;
        int numMoves;
        int priority;

        public SearchNode(Board board, SearchNode parent, int numMoves) {
            this.board = board;
            this.parent = parent;
            this.numMoves = numMoves;
            this.priority = numMoves + board.manhattan();
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        this.initial = initial;
        solve();
    }

    private void solve() {
        // Create priority queue for the actual board
        // and insert the first board
        MinPQ<SearchNode> pq = new MinPQ<>();
        SearchNode initialNode = new SearchNode(initial, null, 0);
        pq.insert(initialNode);

        // Create priority queue for the twin board
        // and insert the first board twin
        Board initialTwin = initial.twin();
        MinPQ<SearchNode> pqTwin = new MinPQ<>();
        SearchNode initialNodeTwin = new SearchNode(initialTwin, null, 0);
        pqTwin.insert(initialNodeTwin);

        // Alternate iterating between the actual board
        // and the twin board one step at a time
        // If the goal is found in the actual board searching,
        // the solution exists and found
        // If the goal is found in the twin board searching,
        // the solution doesn't exist for the actual board and
        // we can stop searching
        boolean twinTurn = false;
        while (!pq.isEmpty()) {
            // Which pq to use for the current iteration
            MinPQ<SearchNode> currPQ = twinTurn ? pqTwin : pq;

            // Remove the min priority node
            SearchNode u = currPQ.delMin();

            // If it is goal, we have to stop searching
            if (u.board.isGoal()) {
                // If it is the actual board search
                // we update the destination node to the curr node
                if (!twinTurn)
                    destination = u;
                break;
            }

            // Add each of its neighbors to respective pq
            // if not already in the respective pq
            // If the grandparent of v is the same as v, it means
            // it already is processed and we don't need to add it
            for (Board v : u.board.neighbors()) {
                if (u.parent == null || !v.equals(u.parent.board)) {
                    currPQ.insert(new SearchNode(v, u, u.numMoves + 1));
                }
            }

            // Alternate turns
            twinTurn = !twinTurn;
        }

        // Construct the path once searching is done
        constructPath();
    }

    private void constructPath() {
        // If not solvable nothing to construct
        if (!isSolvable())
            return;

        // Follow from the destination backwards
        // until we reach the initial board
        this.path = new ArrayList<>();
        SearchNode curr = destination;
        while (curr != null) {
            this.path.add(curr.board);
            curr = curr.parent;
        }

        // Reverse the list because the boards were added
        // in reverse order
        reverseList(path);
    }

    private static void swap(ArrayList<Board> list, int i, int j) {
        Board tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

    private static void reverseList(ArrayList<Board> list) {
        int left = 0, right = list.size() - 1;
        while (left < right) {
            swap(list, left++, right--);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        // If the destination node is null
        // it means it is not reachable
        return destination != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        return destination.numMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        return this.path;
    }

    // test client (see below)
    public static void main(String[] args) {
        String filename = "data/puzzle3x3-07.txt";
        In in = new In(filename);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        // solve the slider puzzle
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        StdOut.println("Board:\n" + initial + "\n");
        if (solver.isSolvable()) {
            StdOut.println("Num moves to solve: " + solver.moves());
            StdOut.println("Solution:");
            for (Board b : solver.solution()) {
                StdOut.println(b);
            }
        } else {
            StdOut.println("The board is not solvable");
        }
    }
}