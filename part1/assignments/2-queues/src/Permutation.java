import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

@SuppressWarnings("unused")
public class Permutation {
    public static void main(String[] args) {
        // Create a randomized queue to insert all the n items
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);

        // Method 1
        // method1(rq, k);

        // Method 2
        method2(rq, k);
    }

    /**
     * O(n) memory as we store all the items in the queue first
     * 
     * Add all 'n' items to the queue and then dequeue
     * 'k' items and print them
     * 
     * @param rq
     * @param k
     */
    private static void method1(RandomizedQueue<String> rq, int k) {
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            rq.enqueue(item);
        }

        // Print the first 'k' elements in the randomized queue
        for (int i = 0; i < k; i++) {
            // We are assured that k <= n
            // so there will be atleast k elements in the queue
            StdOut.println(rq.dequeue());
        }
    }

    /**
     * Ref: https://rosettacode.org/wiki/Knuth%27s_algorithm_S
     * O(k) memory as we only store 'k' items max in the queue
     * 
     * Knuth's algorithm:
     * 1. Select the first 'k' items as they become available
     * 2. For the ith item (i > k), we have a random chance of k/i
     * of keeping it. Succeeding this chance, we replace a randomf
     * item from our existing 'k' items (all elements have same chance
     * 1/k of being replaced)
     * 3. Repeat step 3 till the end of all elements
     * 
     * @param rq
     * @param k
     */
    private static void method2(RandomizedQueue<String> rq, int k) {
        // Put first k items in the queue
        for (int i = 0; i < k; i++) {
            rq.enqueue(StdIn.readString());
        }

        // index tracks the index of the new elements (1 based)
        // the next element's index after the first k are enqueued
        // is 'k + 1'
        int index = k + 1;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();

            // Chance of keeping it
            double chance = 1.0 * k / index++;
            double randomValue = StdRandom.uniformDouble();

            // If we are keeping it, we randomly replace an existing
            // element from the queue
            if (randomValue < chance) {
                rq.dequeue();
                rq.enqueue(item);
            }
        }

        // Print the 'k' items
        for (String item : rq) {
            StdOut.println(item);
        }
    }
}