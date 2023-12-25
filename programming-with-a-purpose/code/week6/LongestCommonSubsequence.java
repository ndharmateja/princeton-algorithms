/******************************************************************************
 * Compilation: javac LongestCommonSubsequence.java
 * Execution: java LongestCommonSubsequence s t
 *
 ******************************************************************************/

public class LongestCommonSubsequence {
    public static String lcs(String x, String y) {
        // Compute length of LCS for all subproblems.
        int m = x.length(), n = y.length();
        int[][] opt = new int[m + 1][n + 1];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (x.charAt(i) == y.charAt(j)) {
                    opt[i][j] = opt[i + 1][j + 1] + 1;
                } else {
                    opt[i][j] = Math.max(opt[i + 1][j], opt[i][j + 1]);
                }
            }
        }

        // Recover LCS itself.
        String lcs = "";
        int i = 0, j = 0;
        while (i < m && j < n) {
            if (x.charAt(i) == y.charAt(j)) {
                lcs += x.charAt(i);
                i++;
                j++;
            } else if (opt[i + 1][j] >= opt[i][j + 1])
                i++;
            else
                j++;
        }
        return lcs;
    }

    public static int Q2(int n) {
        if (n <= 0)
            return 1;
        return 1 + Q2(n - 2) + Q2(n - 3);
    }

    public static void Q3(int n) {
        if (n <= 0)
            return;
        StdOut.println(n);
        Q3(n - 2);
        Q3(n - 3);
        StdOut.println(n);
    }

    public static void Q4(int n) {
        if (n <= 0)
            return;
        StdOut.println(n);
        Q4(n - 2);
        Q4(n - 3);
        StdOut.println(n);
    }

    public static int Q5(int n) {
        int[] b = new int[n + 1];
        b[0] = 1;
        for (int i = 2; i <= n; i++) {
            b[i] = 0;
            for (int j = 0; j < i; j++)
                b[i] += b[j];
        }
        return b[n];
    }

    public static void main(String[] args) {
        // Q4(7);
        System.out.println(Q5(8));
        // String lcs = lcs(args[0], args[1]);
        // StdOut.println(lcs);
    }
}