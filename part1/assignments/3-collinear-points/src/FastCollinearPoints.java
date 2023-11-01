import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private Point[] points;
    private ArrayList<LineSegment> segments;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
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
        // Aux array that can be used for sorting in different
        // slope orders wrt different points
        Point[] auxPoints = new Point[points.length];
        for (int i = 0; i < auxPoints.length; i++) {
            auxPoints[i] = this.points[i];
        }

        // For each point as starting point
        for (Point startPoint : this.points) {
            // Sort all the points in terms of slope order wrt p
            // that means auxPoints[0] will be startPoint (because
            // of negative infinity slope)
            Arrays.sort(auxPoints, startPoint.slopeOrder());

            // We start from point at index 1 because index 0
            // has startPoint itself
            // Initlialize variables for the iteration
            // Initial values don't matter as all of them will get updated
            // in the first iteration itself (when i = 1)
            //
            // isCurrSameSlopeGroupValid tracks if curr slope group is valid
            // group becomes invalid if any of the points in the same slope group
            // is less than startPoint (because the line segment has to start from the
            // lowest point in the same slope group)
            //
            // endPoint tracks the highest point among the same slope group
            boolean isCurrSameSlopeGroupValid = false;
            double runningSlope = Double.NEGATIVE_INFINITY;
            int numPointsInCurrSameSlopeGroup = 0;
            Point endPoint = startPoint;

            for (int i = 1; i < auxPoints.length; i++) {
                // Get currPoint and slope from startPoint to currPoint
                Point currPoint = auxPoints[i];
                double currSlope = startPoint.slopeTo(currPoint);

                // If currSlope is not equal to running slope
                // it means we are at the start of new same slope group
                if (currSlope != runningSlope) {
                    // Process the previous slope group
                    // by creating a line segment if valid
                    // It is valid if num points in prev slope group is >= 3
                    // and if isCurrentValidLineSegment true
                    if (isCurrSameSlopeGroupValid && numPointsInCurrSameSlopeGroup >= 3)
                        // Line segment will be from the current startpoint to the endpoint
                        this.segments.add(new LineSegment(startPoint, endPoint));

                    // Reset variables
                    // isCurrentGroupValidLineSegment will be true if currPoint is
                    // greater than startPoint
                    // If currPoint < startPoint, then startPoint can't be a valid starting point
                    // for this slope group and this whole group cannot be a valid segment starting
                    // from startPoint
                    isCurrSameSlopeGroupValid = startPoint.compareTo(currPoint) < 0;
                    endPoint = currPoint;
                    runningSlope = currSlope;
                    numPointsInCurrSameSlopeGroup = 1;
                }

                // If curr slope is equal to the running slope
                // curr point is part of the slope group
                else if (currSlope == runningSlope) {
                    // If currPoint < p, then startPoint can't be a valid start point
                    // for this slope group
                    if (currPoint.compareTo(startPoint) < 0) {
                        isCurrSameSlopeGroupValid = false;
                    }

                    // If currPoint > endPoint that means previous endPoint cannot
                    // be a valid endpoint, so we update the endPoint to currPoint
                    else if (currPoint.compareTo(endPoint) > 0) {
                        endPoint = currPoint;
                    }

                    // Increment the count of the curr same slope group
                    // This count is irrelevant if isCurrentGroupValidLineSegment is false
                    numPointsInCurrSameSlopeGroup++;
                }
            }

            // Edge case
            // Process the last same slope group
            // as it is not processed in the last iteration
            if (isCurrSameSlopeGroupValid && numPointsInCurrSameSlopeGroup >= 3) {
                this.segments.add(new LineSegment(startPoint, endPoint));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}