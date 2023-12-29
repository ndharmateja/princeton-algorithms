@SuppressWarnings("unused")
public class MaximumSquareSubmatrix {
    private static int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    // O(n^2) time and O(1) space solution
    // DP
    // Mutates the input array
    private static int solution1(int[][] a) {
        // Base case
        int n = a.length;
        if (n == 1)
            return a[0][0];

        // We can use the original array itself
        // as the DP array
        // DP:
        // 1. Each value at (r, c) will signify the size of the
        // max square submatrix that ends at (r, c)
        // 2. For first row and first col, the values will be
        // the original array values, as if the value is 1
        // then the max square subsize ending at that cell is 1
        // and if it is 0, the dp value is also going to be 0
        // 3. dp[r][c] = 0 if a[r][c] = 0
        // 4. dp[r][c] = 1 + min(dp[r - 1][c - 1], dp[r - 1][c], dp[r][c - 1])
        // 5. We can use the original array itself as the dp array
        // as we would not need the original value of an array cell
        // once it's dp value is calculated
        int max = 0;
        for (int r = 1; r < n; r++) {
            for (int c = 1; c < n; c++) {
                a[r][c] = a[r][c] == 0
                        ? 0
                        : 1 + min(a[r - 1][c - 1], a[r - 1][c], a[r][c - 1]);

                // Update max
                max = Math.max(max, a[r][c]);
            }
        }

        return max;
    }

    // O(n^2) time and O(n^2) space solution
    // DP
    // Does not mutate the input array
    private static int solution2(int[][] a) {
        // Base case
        int n = a.length;
        if (n == 1)
            return a[0][0];

        // DP:
        // 1. Each value at (r, c) will signify the size of the
        // max square submatrix that ends at (r, c)
        // 2. For first row and first col, the values will be
        // the original array values, as if the value is 1
        // then the max square subsize ending at that cell is 1
        // and if it is 0, the dp value is also going to be 0
        // 3. dp[r][c] = 0 if a[r][c] = 0
        // 4. dp[r][c] = 1 + min(dp[r - 1][c - 1], dp[r - 1][c], dp[r][c - 1])
        int max = 0;
        int[][] dp = new int[n][n];
        for (int r = 0; r < n; r++) {
            dp[r][0] = a[r][0];
            max = Math.max(max, dp[r][0]);
        }
        for (int c = 0; c < n; c++) {
            dp[0][c] = a[0][c];
            max = Math.max(max, dp[0][c]);
        }
        for (int r = 1; r < n; r++) {
            for (int c = 1; c < n; c++) {
                dp[r][c] = a[r][c] == 0
                        ? 0
                        : 1 + min(dp[r - 1][c - 1], dp[r - 1][c], dp[r][c - 1]);

                // Update max
                max = Math.max(max, dp[r][c]);
            }
        }

        return max;
    }

    // Returns the size of the largest contiguous square submatrix
    // of a[][] containing only 1s.
    public static int size(int[][] a) {
        return solution2(a);
    }

    // Reads an n-by-n matrix of 0s and 1s from standard input
    // and prints the size of the largest contiguous square submatrix
    // containing only 1s.
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int[][] array = new int[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                array[r][c] = StdIn.readInt();
            }
        }
        StdOut.println(size(array));
    }
}