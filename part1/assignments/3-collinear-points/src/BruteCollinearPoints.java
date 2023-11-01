import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private Point[] points;
    private ArrayList<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        // If points is null
        if (points == null) {
            throw new IllegalArgumentException();
        }

        // Copy points array to instance variable
        // and also check if any of the points are null
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            this.points[i] = points[i];
        }

        // Sort all the points by y (and x in case of ties)
        Merge.sort(this.points);

        // Throw error for duplicate points
        // Check adjacent points after sorting
        for (int i = 0; i < this.points.length - 1; i++) {
            if (this.points[i].slopeTo(this.points[i + 1]) == Double.NEGATIVE_INFINITY)
                throw new IllegalArgumentException();
        }

        // Create new array list to store the segments
        this.segments = new ArrayList<>();

        // Find all the collinear segments
        findSegments();
    }

    private void findSegments() {
        // (All points are distinct and in increasing order)
        // Take all distinct possible 4-point permutations
        // and see if each one of them is a collinear segment
        // and if yes, we add it to the segments list
        for (int pIdx = 0; pIdx < points.length; pIdx++) {
            for (int qIdx = pIdx + 1; qIdx < points.length; qIdx++) {
                for (int rIdx = qIdx + 1; rIdx < points.length; rIdx++) {
                    for (int sIdx = rIdx + 1; sIdx < points.length; sIdx++) {
                        Point p = this.points[pIdx];
                        Point q = this.points[qIdx];
                        Point r = this.points[rIdx];
                        Point s = this.points[sIdx];

                        double pqSlope = p.slopeTo(q);
                        double qrSlope = q.slopeTo(r);
                        double rsSlope = r.slopeTo(s);
                        if (pqSlope == qrSlope && qrSlope == rsSlope) {
                            segments.add(new LineSegment(p, s));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segmentsArray = new LineSegment[segments.size()];
        for (int i = 0; i < segmentsArray.length; i++) {
            segmentsArray[i] = segments.get(i);
        }
        return segmentsArray;
    }

    public static void main(String[] args) {
        // read the n points from a file
        // String filename = args[0];
        String filename = "data/equidistant.txt";
        In in = new In(filename);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}