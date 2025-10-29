import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {
    // synsetIdToNounsMap
    // key: synset id (integer)
    // value: set of strings that belong to this id
    private Map<Integer, Set<String>> synsetIdToNounsMap;

    // synsetIdToStringMap
    // key: synset id (integer)
    // value: string of nouns stored as is
    private Map<Integer, String> synsetIdToStringMap;

    // nounToSynsetIdsMap
    // key: noun
    // value: set of integers to whose id this noun belongs to
    // a noun can belong to multiple synset ids
    private Map<String, Set<Integer>> nounToSynsetIdsMap;

    // Stores the directed graph of the wordnet
    private Digraph wordnetGraph;

    // The SAP object to answer shortest ancestor path queries
    private SAP wordnetSap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        // Create the hash map objects
        synsetIdToNounsMap = new HashMap<>();
        synsetIdToStringMap = new HashMap<>();
        nounToSynsetIdsMap = new HashMap<>();

        // Read all the synsets line by line
        In in = new In(synsets);
        String line;
        while (in.hasNextLine()) {
            line = in.readLine();

            // fields = {"<synset id>", "<synset string>", "<synset gloss>"}
            String[] fields = line.split(",");
            int synsetId = Integer.parseInt(fields[0]);
            String synsetNounsString = fields[1];

            // Put the (<synset id>, <synset string>) pair in the synsetIdToStringMap
            synsetIdToStringMap.put(synsetId, synsetNounsString);

            // Create a set of strings to store the nound related to the current id
            Set<String> synsetNounsSet = new HashSet<>();

            for (String noun : synsetNounsString.split(" ")) {
                // Go over each noun and add it to the synsetNounsSet
                synsetNounsSet.add(noun);

                // Add each (<synset noun>, set<synset id>) to nounToSynsetIdsMap
                // If there is no corresponding set to the current noun, create one
                if (!nounToSynsetIdsMap.containsKey(noun)) {
                    nounToSynsetIdsMap.put(noun, new HashSet<>());
                }
                nounToSynsetIdsMap.get(noun).add(synsetId);
            }

            // Put the (<synset id>, set<synset noun>) pair in the synsetIdToNounsMap
            synsetIdToNounsMap.put(synsetId, synsetNounsSet);
        }

        // Now that we have the number of vertices, we can create the wordnet graph
        int numSynsets = synsetIdToNounsMap.size();
        wordnetGraph = new Digraph(numSynsets);

        // Read the hypernyms file and add the edges
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            line = in.readLine();

            // fields is
            // {"<synset_start_vertex>", "<synset_end_vertex1>", "<synset_end_vertex2>",.. }
            String[] fields = line.split(",");

            // Add all the edges to the graph
            int u = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                int v = Integer.parseInt(fields[i]);
                wordnetGraph.addEdge(u, v);
            }
        }

        // Check cycle and multiple roots
        // If there is a cycle or more than one root, it is not allowed
        RootedDagValidator validator = new RootedDagValidator(wordnetGraph);
        if (!validator.isValid())
            throw new IllegalArgumentException();

        // Create an SAP object from the graph
        wordnetSap = new SAP(wordnetGraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return this.nounToSynsetIdsMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();
        return this.nounToSynsetIdsMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();
        return this.wordnetSap.length(this.nounToSynsetIdsMap.get(nounA), this.nounToSynsetIdsMap.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA
    // and nounB in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();
        int ancestorId = this.wordnetSap.ancestor(this.nounToSynsetIdsMap.get(nounA),
                this.nounToSynsetIdsMap.get(nounB));
        return this.synsetIdToStringMap.get(ancestorId);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        // WordNet wordNet = new WordNet("data/synsets.txt", "data/hypernyms.txt");
        // BreadthFirstDirectedPaths bfs = new
        // BreadthFirstDirectedPaths(wordNet.wordnetGraph, 21);
        // System.out.println(bfs.pathTo(1591));
        // System.out.println(bfs.pathTo(23112));

        // bfs = new BreadthFirstDirectedPaths(wordNet.wordnetGraph, 1591);
        // System.out.println(bfs.pathTo(21));

        // bfs = new BreadthFirstDirectedPaths(wordNet.wordnetGraph, 23112);
        // System.out.println(bfs.pathTo(21));

    }
}