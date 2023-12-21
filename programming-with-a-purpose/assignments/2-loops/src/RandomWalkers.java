public class RandomWalkers {
    public static void main(String[] args) {
        int r = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        double average = 0;
        if (r != 0) {
            int totalSteps = 0;
            for (int i = 0; i < trials; i++) {
                int x = 0;
                int y = 0;
                int steps = 0;
                while (Math.abs(x) + Math.abs(y) < r) {
                    double prob = Math.random();
                    if (prob < 0.25)
                        x += 1;
                    else if (prob < 0.5)
                        y += 1;
                    else if (prob < 0.75)
                        x -= 1;
                    else
                        y -= 1;
                    steps += 1;
                }
                totalSteps += steps;
            }
            average = (totalSteps * 1.0) / trials;
        }

        System.out.println("average number of steps = " + average);
    }
}
