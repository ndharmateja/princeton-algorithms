package main.chapter5.p_5_1_12;

import java.util.Arrays;

public class Alphabet {
    private static final int NUM_CHARS_ASCII = 128;
    private static final int NUM_CHARS_EXTENDED_ASCII = 256;
    private static final int NUM_CHARS_UNICODE16 = 65536;

    public static final Alphabet BINARY = new Alphabet("01", NUM_CHARS_ASCII);
    public static final Alphabet DNA = new Alphabet("ACTG", NUM_CHARS_ASCII);
    public static final Alphabet OCTAL = new Alphabet("01234567", NUM_CHARS_ASCII);
    public static final Alphabet DECIMAL = new Alphabet("0123456789", NUM_CHARS_ASCII);
    public static final Alphabet HEXADECIMAL = new Alphabet("0123456789ABCDEF", NUM_CHARS_ASCII);
    public static final Alphabet PROTEIN = new Alphabet("ACDEFGHIKLMNPQRSTVWY", NUM_CHARS_ASCII);
    public static final Alphabet LOWERCASE = new Alphabet("abcdefghijklmnopqrstuvwxyz", NUM_CHARS_ASCII);
    public static final Alphabet UPPERCASE = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ", NUM_CHARS_ASCII);
    public static final Alphabet BASE64 = new Alphabet(
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",
            NUM_CHARS_ASCII);
    public static final Alphabet ASCII = new Alphabet(NUM_CHARS_ASCII);
    public static final Alphabet EXTENDED_ASCII = new Alphabet(NUM_CHARS_EXTENDED_ASCII);
    public static final Alphabet UNICODE16 = new Alphabet(NUM_CHARS_UNICODE16);

    private final char[] chars;
    private final int[] inverse;
    private final int R;
    private final int lgR;

    // create a new alphabet from chars in s
    public Alphabet(String s) {
        this(s, NUM_CHARS_UNICODE16);
    }

    private Alphabet(String s, int maxChars) {
        this.R = s.length();
        this.lgR = computeLgR(R);
        this.chars = new char[R];

        this.inverse = new int[maxChars];
        Arrays.fill(inverse, -1);

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            this.chars[i] = c;

            // check for duplicate chars in the string s
            if (this.inverse[c] != -1)
                throw new IllegalArgumentException("No duplicates allowed in the string.");
            this.inverse[c] = i;
        }
    }

    // Create Alphabet from all the chars corresponding to 0, 1, 2, ..., maxChars-1
    // For example, if maxChars is 128, it would create Alphabet from all the ASCII
    // characters
    private Alphabet(int maxChars) {
        this.R = maxChars;
        this.lgR = computeLgR(R);
        this.chars = new char[R];
        this.inverse = new int[maxChars];

        for (int i = 0; i < this.R; i++) {
            this.chars[i] = (char) i;
            this.inverse[i] = i;
        }
    }

    private static int computeLgR(int R) {
        int lgR = 0;
        for (int t = R - 1; t >= 1; t >>= 1) {
            lgR++;
        }
        return lgR;
    }

    // convert index to corresponding alphabet char
    char toChar(int index) {
        if (index < 0 || index >= this.R)
            throw new IllegalArgumentException("index should be between 0 and R-1 (inclusive).");
        return this.chars[index];
    }

    // convert c to an index between 0 and R-1
    int toIndex(char c) {
        if (!contains(c))
            throw new IllegalArgumentException("given character is not part of the alphabet.");
        return this.inverse[c];
    }

    // is c in the alphabet?
    boolean contains(char c) {
        if (c >= this.inverse.length)
            return false;
        return inverse[c] != -1;
    }

    // radix (number of characters in alphabet) {}//
    int R() {
        return this.R;
    }

    // number of bits to represent an index
    int lgR() {
        return this.lgR;
    }

    // convert s to base-R integer
    int[] toIndices(String s) {
        int[] sIndices = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            sIndices[i] = this.toIndex(s.charAt(i));
        }
        return sIndices;
    }

    // convert base-R integer to string over this alphabet
    String toChars(int[] indices) {
        char[] indicesChars = new char[indices.length];
        for (int i = 0; i < indicesChars.length; i++) {
            indicesChars[i] = this.toChar(indices[i]);
        }
        return new String(indicesChars);
    }
}
