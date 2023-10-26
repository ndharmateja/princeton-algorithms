package q10_sets_intersection;

import edu.princeton.cs.algs4.Point2D;
import test_utils.TestUtils;

public class TestSetsIntersections {
    public static void main(String[] args) {
        Point2D[] a = new Point2D[10];
        Point2D[] b = new Point2D[10];

        a[0] = new Point2D(2, 7);
        a[1] = new Point2D(8, 1);
        a[2] = new Point2D(4, 4);
        a[3] = new Point2D(0, 6);
        a[4] = new Point2D(9, 5);
        a[5] = new Point2D(8, 4);
        a[6] = new Point2D(8, 7);
        a[7] = new Point2D(1, 5);
        a[8] = new Point2D(2, 4);
        a[9] = new Point2D(6, 0);

        b[0] = new Point2D(2, 1);
        b[1] = new Point2D(9, 8);
        b[2] = new Point2D(1, 3);
        b[3] = new Point2D(5, 7);
        b[4] = new Point2D(2, 4);
        b[5] = new Point2D(4, 3);
        b[6] = new Point2D(7, 0);
        b[7] = new Point2D(6, 6);
        b[8] = new Point2D(8, 1);
        b[9] = new Point2D(2, 2);

        int expected = SetsIntersection.countIntersectionsBruteForce(a, b);
        int actual = SetsIntersection.countIntersections(a, b);
        TestUtils.doAssertion(expected == actual, "expected: " + expected + ", actual : " + actual);

        System.out.println("Tests passed");
    }
}
