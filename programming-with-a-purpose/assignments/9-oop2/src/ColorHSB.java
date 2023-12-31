/******************************************************************************
 * % java ColorHSB <h> <s> <b> < web.txt
 ******************************************************************************/

public class ColorHSB {
    private int hue;
    private int saturation;
    private int brightness;

    private static void validateRange(int val, int min, int max) {
        if (val < min || val > max)
            throw new IllegalArgumentException();
    }

    private static int square(int val) {
        return val * val;
    }

    // Creates a color with hue h, saturation s, and brightness b.
    public ColorHSB(int h, int s, int b) {
        validateRange(h, 0, 359);
        validateRange(s, 0, 100);
        validateRange(b, 0, 100);
        this.hue = h;
        this.saturation = s;
        this.brightness = b;
    }

    // Returns a string representation of this color, using the format (h, s, b).
    public String toString() {
        return "(" + this.hue + ", " + this.saturation + ", " + this.brightness + ")";
    }

    // Is this color a shade of gray?
    public boolean isGrayscale() {
        return this.saturation == 0 || this.brightness == 0;
    }

    // Returns the squared distance between the two colors.
    public int distanceSquaredTo(ColorHSB that) {
        if (that == null)
            throw new IllegalArgumentException();

        int result = Math.min(square(this.hue - that.hue), square(360 - Math.abs(this.hue - that.hue)));
        result += square(this.saturation - that.saturation);
        result += square(this.brightness - that.brightness);

        return result;
    }

    // Sample client (see below).
    public static void main(String[] args) {
        int h, s, b;

        if (args.length >= 3) {
            h = Integer.parseInt(args[0]);
            s = Integer.parseInt(args[1]);
            b = Integer.parseInt(args[2]);
        } else {
            h = 25;
            s = 84;
            b = 97;
        }

        ColorHSB c = new ColorHSB(h, s, b);

        String nearestColorString = null;
        ColorHSB nearestColor = null;
        int nearestDist = Integer.MAX_VALUE;
        while (!StdIn.isEmpty()) {
            String currColorString = StdIn.readString();
            ColorHSB curr = new ColorHSB(StdIn.readInt(), StdIn.readInt(),
                    StdIn.readInt());
            int currDist = c.distanceSquaredTo(curr);
            if (currDist < nearestDist) {
                nearestColorString = currColorString;
                nearestColor = curr;
                nearestDist = currDist;
            }
        }

        StdOut.println(nearestColorString + " " + nearestColor);
    }
}