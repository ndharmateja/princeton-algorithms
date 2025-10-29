import java.util.List;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.graph = new Digraph(G);
    }

    private boolean isVertexValid(int u) {
        return u >= 0 && u < this.graph.V();
    }

    private void validateIterable(Iterable<Integer> iterable) {
        if (iterable == null)
            throw new IllegalArgumentException();

        for (Integer vertex : iterable)
            if (vertex == null || !isVertexValid(vertex))
                throw new IllegalArgumentException();
    }

    private boolean isIterableEmpty(Iterable<Integer> iterable) {
        return !iterable.iterator().hasNext();
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return length(List.of(v), List.of(w));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path;
    // -1 if no such path
    public int ancestor(int v, int w) {
        return ancestor(List.of(v), List.of(w));
    }

    // length of shortest ancestral path between any vertex in v and any vertex in
    // w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        // Validations:
        // 1. Iterable can't be null
        // 2. Contents of the iterable can't be null
        // 3. Iterable can't be empty
        validateIterable(v);
        validateIterable(w);
        if (isIterableEmpty(v) || isIterableEmpty(w))
            return -1;

        // Run BFS from the first collection of vertices and then
        // independently from the second collection of vertices
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);

        // We find the shortest ancestor by checking the distance from
        // v's collection and u's collection to every single vertex in the graph
        // We will return the shortest path (distance from v's collection to that vertex
        // + w's collection to that vertex)
        int shortestPathLength = Integer.MAX_VALUE;
        for (int i = 0; i < graph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int pathLength = bfsV.distTo(i) + bfsW.distTo(i);
                shortestPathLength = pathLength < shortestPathLength ? pathLength : shortestPathLength;
            }
        }

        return shortestPathLength == Integer.MAX_VALUE ? -1 : shortestPathLength;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such
    // path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        // Validations:
        // 1. Iterable can't be null
        // 2. Contents of the iterable can't be null
        // 3. Iterable can't be empty
        validateIterable(v);
        validateIterable(w);
        if (isIterableEmpty(v) || isIterableEmpty(w))
            return -1;

        // Run BFS from the first collection of vertices and then
        // independently from the second collection of vertices
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);

        // We find the shortest ancestor by checking the distance from
        // v's collection and u's collection to every single vertex in the graph
        // We will return the node for which
        // the shortest path (distance from v's collection to that vertex
        // + w's collection to that vertex)
        int shortestPathAncestor = -1;
        int shortestPathLength = Integer.MAX_VALUE;
        for (int i = 0; i < graph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int pathLength = bfsV.distTo(i) + bfsW.distTo(i);
                if (pathLength < shortestPathLength) {
                    shortestPathLength = pathLength;
                    shortestPathAncestor = i;
                }
            }
        }

        return shortestPathAncestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("data/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
