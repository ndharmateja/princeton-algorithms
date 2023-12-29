public class Huntingtons {
    // Returns the maximum number of consecutive repeats of CAG in the DNA string.
    public static int maxRepeats(String dna) {
        int maxRepeats = 0;
        int currRepeats = 0;
        int i = 0;
        while (i <= dna.length() - 3) {
            // If CAG found, increment repeats count
            // and also go 3 steps forward to see the next codon
            if (dna.substring(i, i + 3).equals("CAG")) {
                currRepeats++;
                i += 3;
                continue;
            }

            // If not CAG and repeats > 0 => we reached the
            // end of a CAG sequence
            // We update the maxRepeats if currRepeats > maxRepeats
            if (currRepeats > 0) {
                maxRepeats = Math.max(currRepeats, maxRepeats);
                currRepeats = 0;
            }

            // If not CAG, see the codon at next i
            i++;
        }

        // Edge case if CAG is at the end of the string
        maxRepeats = Math.max(currRepeats, maxRepeats);

        return maxRepeats;
    }

    // Returns a copy of s, with all whitespace (spaces, tabs, and newlines)
    // removed.
    public static String removeWhitespace(String s) {
        StringBuilder builder = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c != ' ' && c != '\t' && c != '\n')
                builder.append(c);
        }
        return builder.toString();
    }

    // Returns one of these diagnoses corresponding to the maximum number of
    // repeats: "not human", "normal", "high risk", or "Huntington's".
    public static String diagnose(int maxRepeats) {
        if (maxRepeats < 10)
            return "not human";
        if (maxRepeats < 36)
            return "normal";
        if (maxRepeats < 40)
            return "high risk";
        if (maxRepeats < 181)
            return "Huntington's";
        return "not human";
    }

    public static void main(String[] args) {
        String filename = args[0];
        String s = (new In(filename)).readAll();
        int cagRepeats = maxRepeats(removeWhitespace(s));
        StdOut.println("max repeats = " + cagRepeats);
        StdOut.println(diagnose(cagRepeats));
    }
}