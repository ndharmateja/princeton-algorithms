import edu.princeton.cs.algs4.StdDraw;

public class Draw {
    public static void main(String[] args) {
        boolean isDragging = false;
        double x1, y1;

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.clear();
        StdDraw.show();
        while (true) {
            // user starts to drag a rectangle
            if (StdDraw.isMousePressed() && !isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
                StdDraw.point(x1, y1);
            }
        }
    }
}
