import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        // We store the distances in the 2d array so that
        // we don't do duplicate computations
        int n = nouns.length;
        int[][] pairwiseDistances = new int[n][n];

        // This array stores the total outcast distance for each nount
        int[] outcastDistances = new int[n];

        // We go row by row from left to right
        // For a row when r < c, we are on the lower triangular part
        // so [r][c] has already been computed in the [c][r] as it is a symmetric array
        // so we take the value from the symmetric counter part when r < c
        //
        // For each row as we go from left to right we also compute
        // the total distance and store the result in the outcastDistances array
        for (int r = 0; r < n; r++) {
            int rthDistance = 0;
            for (int c = 0; c < n; c++) {
                if (r == c)
                    pairwiseDistances[r][c] = 0;
                else if (r > c)
                    pairwiseDistances[r][c] = pairwiseDistances[c][r];
                else
                    pairwiseDistances[r][c] = this.wordNet.distance(nouns[r], nouns[c]);

                rthDistance += pairwiseDistances[r][c];
            }

            outcastDistances[r] = rthDistance;
        }

        // Find the index with the max outcast distance
        int outcast = 0;
        int maxOutcastDistance = outcastDistances[0];
        for (int i = 1; i < n; i++) {
            if (outcastDistances[i] > maxOutcastDistance) {
                maxOutcastDistance = outcastDistances[i];
                outcast = i;
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
