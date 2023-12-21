public class RandomWalker {
    public static void main(String[] args) {
        int target = Integer.parseInt(args[0]);
        int x = 0;
        int y = 0;
        int steps = 0;
        int manhattan = 0;
        System.out.println("(" + x + ", " + y + ")");
        while (manhattan < target) {
            double prob = Math.random();
            if (prob < 0.25)
                x += 1;
            else if (prob < 0.5)
                y += 1;
            else if (prob < 0.75)
                x -= 1;
            else
                y -= 1;
            System.out.println("(" + x + ", " + y + ")");
            manhattan = Math.abs(x) + Math.abs(y);
            steps += 1;
        }
        System.out.println("steps = " + steps);
    }
}
