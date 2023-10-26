package q10_sets_intersection;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Quick;

public class SetsIntersection {
    /**
     * 
     * @param a contains n distinct 2D Points in the plane
     * @param b contains n distinct 2D Points in the plane
     * @return
     */
    public static int countIntersections(Point2D[] a, Point2D[] b) {
        // Sort arrays a, b based Point2D's distance from origin
        // We just need to setup a total order (doesn't matter which total order)
        // which is already setup in Point2D's compareTo
        // and we will use that order
        Quick.sort(a);
        Quick.sort(b);

        // We run the while loop until
        // one of the pointers reaches the end
        int intersections = 0;
        int i = 0;
        int j = 0;
        while (i < a.length && j < b.length) {
            // If a's point < b's point
            // we increment a's pointer
            if (a[i].compareTo(b[j]) < 0) {
                i++;
            }
            // If b's point < a's point
            // we increment b's pointer
            else if (a[i].compareTo(b[j]) > 0) {
                j++;
            }
            // If both are same, we increment the number of intersections
            // and increment both the pointers
            else {
                i++;
                j++;
                intersections++;
            }
        }

        return intersections;
    }

    public static int countIntersectionsBruteForce(Point2D[] a, Point2D[] b) {
        int intersections = 0;

        for (Point2D p1 : a) {
            for (Point2D p2 : b) {
                if (p1.equals(p2)) {
                    intersections++;
                }
            }
        }

        return intersections;
    }
}
