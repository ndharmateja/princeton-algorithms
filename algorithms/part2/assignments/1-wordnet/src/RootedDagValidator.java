import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;

public class RootedDagValidator {
    private final Digraph graph;

    public RootedDagValidator(Digraph graph) {
        this.graph = graph;
    }

    /**
     * Checks if the graph doesn't have cycles and has only one root
     * 
     * @return true if no cycle and one root, false otherwise
     */
    public boolean isValid() {
        // Check for a cycle
        DirectedCycle cycle = new DirectedCycle(graph);
        if (cycle.hasCycle())
            return false;

        // A root node has no edges going out from it
        // If there is more than one such node, we return true
        int numRoots = 0;
        for (int v = 0; v < this.graph.V(); v++) {
            if (this.graph.outdegree(v) == 0)
                numRoots++;
            if (numRoots > 1)
                return false;
        }

        // Assuming that there is atleast one root
        return true;
    }
}
