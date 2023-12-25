public class AudioCollage {

    // Returns a new array that rescales a[] by a multiplicative factor of alpha.
    public static double[] amplify(double[] a, double alpha) {
        double[] output = new double[a.length];
        for (int i = 0; i < output.length; i++) {
            output[i] = a[i] * alpha;
        }
        return output;
    }

    // Returns a new array that is the reverse of a[].
    public static double[] reverse(double[] a) {
        double[] output = new double[a.length];
        for (int i = 0; i < output.length; i++) {
            output[i] = a[a.length - i - 1];
        }
        return output;
    }

    // Returns a new array that is the concatenation of a[] and b[].
    public static double[] merge(double[] a, double[] b) {
        double[] output = new double[a.length + b.length];
        for (int i = 0; i < output.length; i++) {
            if (i < a.length)
                output[i] = a[i];
            else
                output[i] = b[i - a.length];
        }
        return output;
    }

    // Returns a new array that is the sum of a[] and b[],
    // padding the shorter arrays with trailing 0s if necessary.
    public static double[] mix(double[] a, double[] b) {
        double[] output = new double[Math.max(a.length, b.length)];
        for (int i = 0; i < a.length; i++) {
            output[i] += a[i];
        }
        for (int i = 0; i < b.length; i++) {
            output[i] += b[i];
        }
        return output;
    }

    // Returns a new array that changes the speed by the given factor.
    public static double[] changeSpeed(double[] a, double alpha) {
        int n = a.length;
        int m = (int) Math.floor(n / alpha);

        double[] output = new double[m];
        for (int i = 0; i < output.length; i++) {
            output[i] = a[(int) Math.floor(i * alpha)];
        }
        return output;
    }

    private static double[] repeat(double[] a, int n) {
        double[] output = new double[n * a.length];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < a.length; j++) {
                output[i * a.length + j] = a[j];
            }
        }
        return output;
    }

    private static double[] normalize(double[] a) {
        double maxAbs = 0;
        for (double val : a) {
            if (Math.abs(val) > maxAbs)
                maxAbs = Math.abs(val);
        }
        if (maxAbs > 1) {
            double alpha = 1 / maxAbs;
            return amplify(a, alpha);
        }
        return a;
    }

    private static double[] trimRight(double[] a, double newDuration) {
        int m = (int) Math.floor(newDuration * 44100);
        double[] output = new double[m];
        for (int i = 0; i < output.length; i++) {
            output[i] = a[i];
        }
        return output;
    }

    private static double[] merge(double[][] parts) {
        double[] output = merge(parts[0], parts[1]);
        for (int i = 2; i < parts.length; i++) {
            output = merge(output, parts[i]);
        }
        return output;
    }

    // Creates an audio collage and plays
    // See below for the requirements.
    public static void main(String[] args) {
        double[] beatbox = StdAudio.read("beatbox.wav");
        double[] cow = StdAudio.read("cow.wav");
        double[] dialup = StdAudio.read("dialup.wav");
        double[] piano = StdAudio.read("piano.wav");
        double[] singer = StdAudio.read("singer.wav");

        // Merge parts and normalize
        double[][] parts = {
                dialup,
                mix(amplify(singer, 2), changeSpeed(repeat(beatbox, 4), 0.75)),
                mix(repeat(trimRight(cow, 2.5), 2), piano),
                reverse(mix(repeat(trimRight(cow, 2.5), 2), piano)),
        };
        double[] finalAudio = normalize(merge(parts));

        // Play
        StdAudio.play(finalAudio);
    }
}