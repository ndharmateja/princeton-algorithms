public class Birthday {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        // n + 2 because 0 index will be empty, as count of 0 is not possible
        // and also 'n + 1' is possible as count get to 'n + 1' without
        // any birthday repetitions
        // so we need indices upto (and including) 'n + 1'
        int[] frequencies = new int[n + 2];

        for (int t = 0; t < trials; t++) {
            // initialize boolean birthday array
            // all values initialized to false by default
            boolean[] hasBirthday = new boolean[n];

            int count = 0;
            while (true) {
                // random birthday in [0, n - 1]
                int r = (int) (Math.random() * n);
                count++;

                // if already birthday repeats, stop
                if (hasBirthday[r])
                    break;
                else
                    hasBirthday[r] = true;
            }
            frequencies[count] += 1;
        }

        // calculate proportions for the frequencies
        double[] proportions = new double[frequencies.length];
        proportions[0] = 0.0;
        for (int i = 1; i < proportions.length; i++) {
            proportions[i] = proportions[i - 1] + ((frequencies[i] * 1.0) / trials);
            System.out.println(i + "\t" + frequencies[i] + "\t" + proportions[i]);
            if (proportions[i] >= 0.5)
                break;
        }
    }
}
