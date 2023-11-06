import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        // Do not store the words in an array or list.
        // Instead, use Knuth’s method: when reading the ith word,
        // Do not store the words in an array or list. Instead, use Knuth’s method:
        // when reading the ith word, select it with probability 1/i
        // to be the champion, replacing the previous champion. After reading all of the
        // words, print the surviving champion.
        String word = "";
        int i = 0;
        while (!StdIn.isEmpty()) {
            double prob = 1.0 / ++i;
            String current = StdIn.readString();
            if (StdRandom.bernoulli(prob)) {
                word = current;
            }
        }
        StdOut.println(word);
    }
}
