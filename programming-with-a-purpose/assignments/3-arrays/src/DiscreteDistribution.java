public class DiscreteDistribution {
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = args.length - 1;
        int[] proportions = new int[n];
        proportions[0] = Integer.parseInt(args[1]);
        for (int i = 1; i < proportions.length; i++) {
            proportions[i] = Integer.parseInt(args[i + 1]) + proportions[i - 1];
        }

        int[] output = new int[m];
        int proportionsSum = proportions[proportions.length - 1];
        for (int i = 0; i < output.length; i++) {
            // Random number [0, proportionsSum - 1]
            double r = (int) (Math.random() * proportionsSum);
            for (int j = 0; j < proportions.length; j++) {
                if (r < proportions[j]) {
                    output[i] = j + 1;
                    break;
                }
            }
        }

        for (int val : output)
            System.out.print(val + " ");
        System.out.println();
    }
}
