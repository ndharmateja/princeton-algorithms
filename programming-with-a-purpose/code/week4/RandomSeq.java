import edu.princeton.cs.algs4.StdOut;

public class RandomSeq {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (int i = 0; i < n; i++) {
            StdOut.println(Math.random());
        }
    }
}
