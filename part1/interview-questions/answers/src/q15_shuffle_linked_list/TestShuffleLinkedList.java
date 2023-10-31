package q15_shuffle_linked_list;

import java.util.HashMap;

import edu.princeton.cs.algs4.StdStats;

public class TestShuffleLinkedList {
    public static <T> String toString(Node<T> head) {
        if (head == null)
            return "";

        StringBuilder builder = new StringBuilder();
        Node<T> curr = head;
        while (curr != null) {
            builder.append(curr.data);
            curr = curr.next;
        }
        return builder.toString();
    }

    public static Node<Integer> buildList(int size) {
        Node<Integer> dummyHead = new Node<>();
        Node<Integer> curr = dummyHead;
        for (int i = 0; i < size; i++) {
            curr.next = new Node<>(i);
            curr = curr.next;
        }
        return dummyHead.next;
    }

    private static int factorial(int n) {
        if (n <= 1)
            return 1;
        return n * factorial(n - 1);
    }

    private static void test() {
        int n = 8;
        int nFact = factorial(n);
        int expected = 100;
        int numTrials = expected * nFact;

        Node<Integer> head = buildList(n);
        HashMap<String, Integer> countsMap = new HashMap<>();
        String repr = null;
        for (int trial = 0; trial < numTrials; trial++) {
            head = ShuffleLinkedList.shuffle(head);
            repr = toString(head);
            countsMap.put(repr, countsMap.getOrDefault(repr, 0) + 1);
        }

        // Frequencies of all permutations
        int[] observedFreqs = countsMap.values().stream().mapToInt(i -> i).toArray();

        // Mean and std dev of the frequencies
        double mean = StdStats.mean(observedFreqs);
        double stdev = StdStats.stddev(observedFreqs);
        System.out.println("Mean: " + mean + ", Std dev: " + stdev);

        // chiSquared value = sigma ((O - E)^2)/E
        double chiSquared = 0.0;
        for (int observedFreq : observedFreqs) {
            chiSquared += ((observedFreq - expected) * (observedFreq - expected)) / (1.0 * expected);
        }
        int dof = nFact - 1;
        System.out.println("Chi^2: " + chiSquared + ", dof: " + dof);
    }

    public static void main(String[] args) {
        test();
    }
}
