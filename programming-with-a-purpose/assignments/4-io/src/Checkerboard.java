public class Checkerboard {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        StdDraw.setScale(0, n);
        // Draw boxes from lower row to upper row
        for (int i = 0; i < n; i++) {
            boolean isBlue = i % 2 == 0;
            for (int j = 0; j < n; j++) {
                // Draw the square
                StdDraw.setPenColor(isBlue ? StdDraw.BLUE : StdDraw.LIGHT_GRAY);

                // Square coordinates
                int xStart = j;
                int xEnd = j + 1;
                int yStart = i;
                int yEnd = i + 1;
                double x = (xStart + xEnd) / 2.0;
                double y = (yStart + yEnd) / 2.0;
                StdDraw.filledSquare(x, y, 0.5);

                // Alternate blue in row for each square
                isBlue = !isBlue;
            }
        }
    }
}
