public class Clock {
    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_DAY = 24;
    private int hours, minutes;

    private static void validateRange(int val, int min, int max) {
        if (val < min || val > max)
            throw new IllegalArgumentException();
    }

    // Creates a clock whose initial time is h hours and m minutes.
    public Clock(int h, int m) {
        validateAndInitialize(h, m);
    }

    // Creates a clock whose initial time is specified as a string, using the format
    // HH:MM.
    public Clock(String s) {
        // Validate string's length
        validateRange(s.length(), 5, 5);

        // Validate char at index 2 is ':'
        if (s.charAt(2) != ':')
            throw new IllegalArgumentException();

        // Validate values of h and m
        // that they are integers and also in valid range
        try {
            int h = Integer.parseInt(s.substring(0, 2));
            int m = Integer.parseInt(s.substring(3));
            validateAndInitialize(h, m);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }

    }

    private void validateAndInitialize(int h, int m) {
        validateRange(h, 0, 23);
        validateRange(m, 0, 59);
        this.hours = h;
        this.minutes = m;
    }

    private static String pad(int val) {
        return (val < 10 ? "0" : "") + val;
    }

    // Returns a string representation of this clock, using the format HH:MM.
    public String toString() {
        String hoursString = pad(this.hours);
        String minutesString = pad(this.minutes);
        return String.format(hoursString + ":" + minutesString);
    }

    // Is the time on this clock earlier than the time on that one?
    public boolean isEarlierThan(Clock that) {
        if (this.hours < that.hours)
            return true;
        if (this.hours == that.hours && this.minutes < that.minutes)
            return true;
        return false;
    }

    private void incrementHours(int numHours) {
        this.hours = (this.hours + numHours) % HOURS_IN_DAY;
    }

    // Adds 1 minute to the time on this clock.
    public void tic() {
        toc(1);
    }

    // Adds Δ minutes to the time on this clock.
    public void toc(int delta) {
        validateRange(delta, 0, Integer.MAX_VALUE);
        int numHours = delta / MINUTES_IN_HOUR;
        int numMinutes = delta % MINUTES_IN_HOUR;

        this.minutes += numMinutes;
        if (this.minutes >= MINUTES_IN_HOUR) {
            numHours++;
            this.minutes = this.minutes % MINUTES_IN_HOUR;
        }

        incrementHours(numHours);
    }

    // Test client (see below).
    public static void main(String[] args) {
        Clock clock = new Clock(23, 59);
        System.out.println(clock);
        clock.toc(59);
        System.out.println(clock);
    }
}