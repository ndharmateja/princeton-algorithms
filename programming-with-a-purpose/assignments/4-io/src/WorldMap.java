public class WorldMap {
    public static void main(String[] args) {
        int width = StdIn.readInt();
        int height = StdIn.readInt();

        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);

        while (!StdIn.isEmpty()) {
            StdIn.readString();
            int n = StdIn.readInt();

            double[] xs = new double[n];
            double[] ys = new double[n];

            for (int i = 0; i < n; i++) {
                xs[i] = StdIn.readDouble();
                ys[i] = StdIn.readDouble();
            }
            StdDraw.polygon(xs, ys);
        }
    }
}
