public class ShannonEntropy {
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int[] freqs = new int[m + 1];
        int numVals = 0;

        // Read input values and update freqs array
        while (!StdIn.isEmpty()) {
            int val = StdIn.readInt();
            freqs[val]++;
            numVals++;
        }

        // Compute answer
        double answer = 0;
        for (int i = 1; i < freqs.length; i++) {
            if (freqs[i] != 0) {
                double p = freqs[i] / (numVals * 1.0);
                answer += -(p * Math.log(p) / Math.log(2));
            }
        }

        StdOut.printf("%.4f\n", answer);
    }
}
