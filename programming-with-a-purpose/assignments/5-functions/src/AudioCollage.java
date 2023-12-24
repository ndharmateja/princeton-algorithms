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

    // Creates an audio collage and plays it on standard audio.
    // See below for the requirements.
    public static void main(String[] args) {
    }
}