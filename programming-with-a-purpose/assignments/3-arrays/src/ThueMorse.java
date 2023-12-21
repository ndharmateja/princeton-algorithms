public class ThueMorse {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        int[] sequence = new int[n];
        int currPowerOf2 = 1;
        sequence[0] = 0;
        int currIndex = 1;
        while (currIndex < n) {
            for (int i = 0; i < currPowerOf2 && currIndex < n; i++) {
                sequence[currIndex] = 1 - sequence[currIndex - currPowerOf2];
                currIndex++;
            }
            currPowerOf2 *= 2;
        }

        boolean[][] output = new boolean[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                output[r][c] = sequence[r] == sequence[c];
            }
        }

        for (boolean[] row : output) {
            for (boolean val : row) {
                if (val)
                    System.out.print("+  ");
                else
                    System.out.print("-  ");
            }
            System.out.println();
        }
    }
}
