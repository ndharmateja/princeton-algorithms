import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;

public class PlotFilter {
    public static void main(String[] args) {
        double xmin = StdIn.readDouble();
        double ymin = StdIn.readDouble();
        double xmax = StdIn.readDouble();
        double ymax = StdIn.readDouble();
        StdDraw.setXscale(xmin, xmax);
        StdDraw.setYscale(ymin, ymax);
        StdDraw.show();
        while (!StdIn.isEmpty()) {
            double y = StdIn.readDouble();
            double x = StdIn.readDouble();

            StdDraw.point(x, y);
        }
    }
}
