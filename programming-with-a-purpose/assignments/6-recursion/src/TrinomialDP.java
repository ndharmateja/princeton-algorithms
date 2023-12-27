public class TrinomialDP {
    // Returns the trinomial coefficient T(n, k).
    public static long trinomial(int n, int k) {
        // T(n, -k) = T(n, k)
        k = Math.abs(k);

        // Base cases for which no computation is required
        // if n == k (which also covers n == 0 and k == 0)
        if (n == k)
            return 1;
        // if |k| > n
        if (k > n)
            return 0;

        // DP matrix
        // rows direction - used for n
        // cols direction - used for k
        long[][] dp = new long[n + 1][n + 1];

        // Diagonal elements are all 1
        // And all elements to the right of principal diagonal are 0
        // as k > n
        for (int i = 0; i < n + 1; i++) {
            dp[i][i] = 1;
        }

        // For each row from i = 1
        // As in row 0, dp[0][0] is 1 as principal diagonal
        // and we don't need to compute any more values in row 0 as k > n
        for (int i = 1; i < n + 1; i++) {
            // We only need to go till the principal diagonal (without having to compute
            // the principal diagonal element)
            for (int j = 0; j < i; j++) {
                // If j is not zero
                // the value is sum of above 3 values in i-1 row
                // that is dp[i-1][j-1] + dp[i-1][j] + dp[i-1][j+1]
                if (j != 0) {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j] + dp[i - 1][j + 1];
                }
                // If j is zero
                // j-1 = -1 will be out of bounds
                // so we can use the dp[i-1][+1] instead of dp[i-1][-1] as
                // T(n, k) = T(n, -k)
                else {
                    dp[i][j] = dp[i - 1][0] + 2 * dp[i - 1][1];
                }

                // If we compute the value at n, k
                // we can stop here as we calculated the required answer
                if (i == n && j == k)
                    break;
            }
        }

        return dp[n][k];
    }

    // Takes two integer command-line arguments n and k and prints T(n, k).
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);
        System.out.println(trinomial(n, k));
    }
}
