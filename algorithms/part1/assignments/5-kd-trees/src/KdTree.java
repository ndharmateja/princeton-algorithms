import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final double POINT_PEN_RADIUS = 0.01;
    private static final double LINE_PEN_RADIUS = POINT_PEN_RADIUS / 5;

    private static class Node {
        Point2D point;
        RectHV boundary;
        Node left;
        Node right;

        public Node(Point2D point) {
            this.point = point;
        }

        @Override
        public String toString() {
            return "Node: " + point;
        }
    }

    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    /*********************
     * INSERT & CONTAINS *
     *********************/

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    private boolean shouldGoLeft(Node node, Point2D p, boolean isEvenLevel) {
        // If even level checks the x-coordinate
        // and sees whether to go left or right
        if (isEvenLevel) {
            return p.x() <= node.point.x();
        }
        // otherwise sees y-coordinate
        else {
            return p.y() <= node.point.y();
        }
    }

    // add the point to the set (if it is not already in the set){}
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();

        root = insert(root, p, true);

        // Assign node's boundary first time
        if (root.boundary == null)
            root.boundary = new RectHV(0, 0, 1, 1);
    }

    private Node insert(Node node, Point2D p, boolean isEvenLevel) {
        if (node == null) {
            size++;
            return new Node(p);
        }

        if (p.equals(node.point))
            return node;

        if (shouldGoLeft(node, p, isEvenLevel)) {
            node.left = insert(node.left, p, !isEvenLevel);

            // Assign node's boundary first time
            if (node.left.boundary == null)
                node.left.boundary = getLeftChildBoundary(node, isEvenLevel);
        } else {
            node.right = insert(node.right, p, !isEvenLevel);

            // Assign node's boundary first time
            if (node.right.boundary == null)
                node.right.boundary = getRightChildBoundary(node, isEvenLevel);
        }

        return node;
    }

    private RectHV getLeftChildBoundary(Node node, boolean isEvenLevel) {
        RectHV leftChildBoundary = isEvenLevel
                ? new RectHV(node.boundary.xmin(), node.boundary.ymin(), node.point.x(), node.boundary.ymax())
                : new RectHV(node.boundary.xmin(), node.boundary.ymin(), node.boundary.xmax(), node.point.y());
        return leftChildBoundary;
    }

    private RectHV getRightChildBoundary(Node node, boolean isEvenLevel) {
        RectHV rightChildBoundary = isEvenLevel
                ? new RectHV(node.point.x(), node.boundary.ymin(), node.boundary.xmax(), node.boundary.ymax())
                : new RectHV(node.boundary.xmin(), node.point.y(), node.boundary.xmax(), node.boundary.ymax());
        return rightChildBoundary;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return contains(root, p, true);
    }

    private boolean contains(Node node, Point2D p, boolean isEvenLevel) {
        if (node == null)
            return false;

        if (p.equals(node.point))
            return true;

        if (shouldGoLeft(node, p, isEvenLevel))
            return contains(node.left, p, !isEvenLevel);
        else
            return contains(node.right, p, !isEvenLevel);
    }

    /***********
     * DRAWING *
     ***********/

    // draw all points to standard draw
    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean isEvenLevel) {
        // If node is null, don't need to do anything
        if (node == null)
            return;

        // Draw left subtree recursively
        // Its boundary will be reduced accordingly
        draw(node.left, !isEvenLevel);

        // Draw the curr point and splitting line
        if (isEvenLevel)
            drawRedVerticalLine(node);
        else
            drawBlueHorizontalLine(node);
        drawPoint(node.point);

        // Draw the right subtree recursively
        // Its boundary will be reduced accordingly
        draw(node.right, !isEvenLevel);
    }

    private void drawPoint(Point2D p) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(POINT_PEN_RADIUS);
        p.draw();
    }

    private void drawRedVerticalLine(Node node) {
        double x = node.point.x();
        double y1 = node.boundary.ymin();
        double y2 = node.boundary.ymax();
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(LINE_PEN_RADIUS);
        StdDraw.line(x, y1, x, y2);
    }

    private void drawBlueHorizontalLine(Node node) {
        double y = node.point.y();
        double x1 = node.boundary.xmin();
        double x2 = node.boundary.xmax();
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(LINE_PEN_RADIUS);
        StdDraw.line(x1, y, x2, y);
    }

    /************************************
     * RANGE SEARCH & NEAREST NEIGHBOUR *
     ************************************/

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV query) {
        if (query == null)
            throw new IllegalArgumentException();
        List<Point2D> output = new ArrayList<>();
        range(root, true, query, output);
        return output;
    }

    private void range(Node node, boolean isEvenLevel, RectHV queryRect, List<Point2D> list) {
        // Base case
        if (node == null)
            return;

        // If the node's boundary doesn't intersect with the query rectangle
        // we don't have to explore this node as there won't be any points
        if (!queryRect.intersects(node.boundary))
            return;

        // If node's point is present in the query rectangle
        // we add it to the list
        if (queryRect.contains(node.point))
            list.add(node.point);

        // Explore left child (without checking if we have to because
        // that check will be done inside the call of the left child)
        range(node.left, !isEvenLevel, queryRect, list);

        // Explore right child (without checking if we have to because
        // that check will be done inside the call of the left child)
        range(node.right, !isEvenLevel, queryRect, list);
    }

    public Point2D nearest(Point2D query) {
        if (query == null)
            throw new IllegalArgumentException();
        if (root == null)
            return null;
        return nearest(root, true, query, root.point);
    }

    private Point2D nearest(Node node, boolean isEvenLevel, Point2D query, Point2D nearestPointSoFar) {
        // base case
        if (node == null)
            return nearestPointSoFar;

        // If point equals the query
        // we return it
        if (node.point.equals(query))
            return query;

        // See if current point is nearer to the query
        // than the nearest point so far
        nearestPointSoFar = getMinimumPoint(nearestPointSoFar, node.point, query);

        Node firstNodeToExplore;
        Node secondNodeToExplore;
        if (shouldGoLeft(node, query, isEvenLevel)) {
            firstNodeToExplore = node.left;
            secondNodeToExplore = node.right;
        } else {
            firstNodeToExplore = node.right;
            secondNodeToExplore = node.left;
        }

        // explore the direction in which query point is there
        // and update the nearest distance accordingly
        Point2D firstNodeNearest = nearest(firstNodeToExplore, !isEvenLevel, query, nearestPointSoFar);
        nearestPointSoFar = getMinimumPoint(nearestPointSoFar, firstNodeNearest, query);

        // Explore the second direction only if there is a possibility
        // of a closer point
        // If not possible, we can return the nearest we found so far
        if (!canBoundaryContainNearest(secondNodeToExplore, nearestPointSoFar, query))
            return nearestPointSoFar;

        // Explore the second direction and update the nearest point accordingly
        Point2D secondNodeNearest = nearest(secondNodeToExplore, !isEvenLevel, query, nearestPointSoFar);
        nearestPointSoFar = getMinimumPoint(nearestPointSoFar, secondNodeNearest, query);

        // Return the nearest point
        return nearestPointSoFar;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    private Point2D getMinimumPoint(Point2D nearestSoFar, Point2D newPoint, Point2D queryPoint) {
        assert nearestSoFar != null;
        assert newPoint != null;

        if (newPoint.distanceSquaredTo(queryPoint) < nearestSoFar.distanceSquaredTo(queryPoint))
            return newPoint;
        return nearestSoFar;
    }

    private boolean canBoundaryContainNearest(Node node, Point2D nearestSoFar, Point2D queryPoint) {
        // because if node is null
        // no points to even see
        if (node == null)
            return false;

        return node.boundary.distanceSquaredTo(queryPoint) < nearestSoFar.distanceSquaredTo(queryPoint);
    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.5, 0.4));
        tree.insert(new Point2D(0.2, 0.3));
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.9, 0.6));
        tree.draw();

        System.out.println(tree.size());
        System.out.println(tree.contains(new Point2D(0.7, 0.2)));
        System.out.println(tree.contains(new Point2D(0.7, 0.3)));
        System.out.println(tree.contains(new Point2D(0.5, 0.4)));

    }
}
