public class HTree {
    public static void draw(int n, double sz, double x, double y) {
        if (n == 0)
            return;

        double halfSz = sz / 2;
        double x0 = x - halfSz;
        double x1 = x + halfSz;
        double y0 = y - halfSz;
        double y1 = y + halfSz;

        // Draw the H
        StdDraw.line(x0, y, x1, y);
        StdDraw.line(x0, y0, x0, y1);
        StdDraw.line(x1, y0, x1, y1);

        // Recursively draw 4 Hs on 4 corners of this H
        draw(n - 1, halfSz, x0, y0);
        draw(n - 1, halfSz, x0, y1);
        draw(n - 1, halfSz, x1, y0);
        draw(n - 1, halfSz, x1, y1);
    }

    public static void main(String[] args) {
        draw(Integer.parseInt(args[0]), 0.5, 0.5, 0.5);
    }
}
