import java.util.List;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * SAP (Shortest Ancestral Path) provides methods to compute the length
 * of the shortest ancestral path and the actual common ancestor between
 * two vertices (or between two sets of vertices) in a digraph.
 *
 * <p>
 * Definition: An ancestral path between v and w is a pair of directed
 * paths v->a and w->a that end at a common ancestor a. The length of the
 * ancestral path is the sum of the lengths of the two paths. SAP finds the
 * common ancestor that minimizes this sum (and returns both the ancestor
 * and the length of the optimal ancestral path).
 * </p>
 *
 * <p>
 * This implementation performs two BFS searches (one from each source
 * set) using {@link edu.princeton.cs.algs4.BreadthFirstDirectedPaths} and
 * then scans all vertices to find the vertex reachable from both source
 * sets with minimum combined distance. Time complexity for a single
 * query is O(V + E) dominated by the BFS traversals and the scan of V
 * vertices; space complexity is O(V).
 * </p>
 */
public class SAP {
    private final Digraph graph;

    /**
     * Construct a SAP instance backed by a defensive copy of the provided
     * digraph. The input graph is not modified by this class.
     *
     * @param G the digraph to query for ancestral paths (must not be null)
     * @throws IllegalArgumentException if {@code G} is null
     */
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException("Digraph G must not be null");
        // Defensive copy to ensure immutability of the internal graph
        this.graph = new Digraph(G);
    }

    /**
     * Check whether a single vertex index is valid for the current graph.
     *
     * @param u vertex index to validate
     * @return {@code true} when 0 <= u < V where V is the number of vertices
     *         in the underlying digraph; {@code false} otherwise
     */
    private boolean isVertexValid(int u) {
        return u >= 0 && u < this.graph.V();
    }

    /**
     * Validate an iterable of vertices. This method checks three conditions:
     * <ol>
     * <li>the iterable reference itself is not null,</li>
     * <li>no element inside the iterable is null, and</li>
     * <li>every element is a valid vertex index in the graph.</li>
     * </ol>
     *
     * @param iterable iterable of Integer vertex ids to validate
     * @throws IllegalArgumentException when {@code iterable} is null, contains
     *                                  a null element, or contains an index outside
     *                                  [0, V-1]
     */
    private void validateIterable(Iterable<Integer> iterable) {
        if (iterable == null)
            throw new IllegalArgumentException("Iterable of vertices must not be null");

        for (Integer vertex : iterable)
            if (vertex == null || !isVertexValid(vertex))
                throw new IllegalArgumentException("Iterable contains null or invalid vertex");
    }

    /**
     * Helper to determine whether an iterable contains no elements.
     * Note: calling {@code iterable.iterator().hasNext()} is sufficient
     * and intentionally avoids materializing the contents.
     *
     * @param iterable iterable to test (must not be null)
     * @return {@code true} when the iterable has no elements; otherwise
     *         {@code false}
     */
    private boolean isIterableEmpty(Iterable<Integer> iterable) {
        return !iterable.iterator().hasNext();
    }

    /**
     * Length of shortest ancestral path between single vertices {@code v}
     * and {@code w}.
     *
     * <p>
     * This is a convenience wrapper that delegates to
     * {@link #length(Iterable, Iterable)}
     * after wrapping the two ints into singleton collections.
     * </p>
     *
     * @param v source vertex v
     * @param w source vertex w
     * @return length of shortest ancestral path, or -1 if no ancestral path exists
     * @throws IllegalArgumentException if either vertex index is invalid
     */
    public int length(int v, int w) {
        return length(List.of(v), List.of(w));
    }

    /**
     * A common ancestor of vertices {@code v} and {@code w} that participates
     * in a shortest ancestral path.
     *
     * <p>
     * Returns the ancestor vertex id (the meeting vertex). If there are
     * multiple valid ancestors with the same shortest path length, the one
     * with smallest id discovered during the scan will be returned (this
     * behavior is stable but not required by the specification).
     * </p>
     *
     * @param v source vertex v
     * @param w source vertex w
     * @return vertex id of common ancestor participating in a shortest ancestral
     *         path; -1 if no such path exists
     * @throws IllegalArgumentException if either vertex index is invalid
     */
    public int ancestor(int v, int w) {
        return ancestor(List.of(v), List.of(w));
    }

    /**
     * Length of shortest ancestral path between any vertex in iterable {@code v}
     * and any vertex in iterable {@code w}.
     *
     * <p>
     * Algorithm summary:
     * <ul>
     * <li>Validate inputs (null checks and vertex index checks).</li>
     * <li>Perform breadth-first search from set {@code v} (multi-source BFS).</li>
     * <li>Perform breadth-first search from set {@code w} (multi-source BFS).</li>
     * <li>Scan all vertices a; if both searches reach a, compute combined distance
     * dist_v(a) + dist_w(a) and take the minimum.</li>
     * </ul>
     * </p>
     *
     * @param v iterable of source vertices (must not be null or contain nulls)
     * @param w iterable of source vertices (must not be null or contain nulls)
     * @return length of shortest ancestral path among any pair (x in v, y in w),
     *         or -1 if no ancestral path exists
     * @throws IllegalArgumentException when either iterable is null or contains
     *                                  invalid vertices
     */
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
        // v's collection and w's collection to every single vertex in the graph.
        int shortestPathLength = Integer.MAX_VALUE;
        for (int i = 0; i < graph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int pathLength = bfsV.distTo(i) + bfsW.distTo(i);
                shortestPathLength = pathLength < shortestPathLength ? pathLength : shortestPathLength;
            }
        }

        return shortestPathLength == Integer.MAX_VALUE ? -1 : shortestPathLength;
    }

    /**
     * A common ancestor that participates in a shortest ancestral path between
     * any vertex in {@code v} and any vertex in {@code w}.
     *
     * <p>
     * This method mirrors {@link #length(Iterable, Iterable)} but returns the
     * actual ancestor vertex id that yields the minimum combined distance.
     * If no common ancestor exists, returns -1.
     * </p>
     *
     * @param v iterable of source vertices (must not be null or contain nulls)
     * @param w iterable of source vertices (must not be null or contain nulls)
     * @return vertex id of the common ancestor on a shortest ancestral path, or -1
     *         if no such ancestor exists
     * @throws IllegalArgumentException when either iterable is null or contains
     *                                  invalid vertices
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        // Validations:
        // 1. Iterable can't be null
        // 2. Contents of the iterable can't be null
        // 3. Iterable can't be empty
        validateIterable(v);
        validateIterable(w);
        if (isIterableEmpty(v) || isIterableEmpty(w))
            return -1;

        // Run BFS from both source sets and examine all vertices for reachability
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);

        // Track the best ancestor and the best (minimum) combined distance
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

    /**
     * Unit test client. Reads a digraph from the file {@code data/digraph1.txt}
     * and then reads pairs of integers from standard input. For each pair
     * (v, w) it prints the length of the shortest ancestral path and the
     * corresponding ancestor.
     *
     * Usage example (from project root):
     * 
     * <pre>
     *   java -cp .:algs4.jar SAP
     * </pre>
     * 
     * (standard input should contain pairs of integers)
     */
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
