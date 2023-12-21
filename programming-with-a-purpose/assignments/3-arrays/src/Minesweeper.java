public class Minesweeper {
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);

        int[][] minesweeper = new int[m][n];
        for (int i = 0; i < k; i++) {
            while (true) {
                int rm = (int) (Math.random() * m);
                int rn = (int) (Math.random() * n);

                if (minesweeper[rm][rn] != -1) {
                    minesweeper[rm][rn] = -1;
                    break;
                }
            }
        }

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (minesweeper[r][c] == -1)
                    continue;
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        if (r + dr >= 0 && r + dr <= m - 1 && c + dc >= 0 && c + dc <= n - 1
                                && minesweeper[r + dr][c + dc] == -1) {
                            minesweeper[r][c]++;
                        }
                    }
                }
            }
        }

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (minesweeper[r][c] == -1)
                    System.out.print("*  ");
                else
                    System.out.print(minesweeper[r][c] + "  ");
            }
            System.out.println();
        }
    }
}
