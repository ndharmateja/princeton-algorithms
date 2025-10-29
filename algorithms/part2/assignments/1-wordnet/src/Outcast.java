import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        // We store the distances in the 2d array so that
        // we don't do duplicate computations
        int n = nouns.length;

        // Base case
        if (n == 1 || n == 2)
            return nouns[0];

        // For each vertex, loop over all the remaining vertices
        // and find the sum of the pairwise distances
        // Find the index with the max outcast distance
        int outcast = -1;
        int maxOutcastDistance = 0;
        for (int v = 0; v < n; v++) {
            int distance = 0;
            for (int i = 0; i < n; i++) {
                distance += this.wordNet.distance(nouns[v], nouns[i]);
            }
            if (distance > maxOutcastDistance) {
                outcast = v;
                maxOutcastDistance = distance;
            }
        }

        // Return the outcast noun with the max outcast distance
        return nouns[outcast];
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
