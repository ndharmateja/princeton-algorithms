public class Base {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int nCopy = n;
        int base = Integer.parseInt(args[1]);

        int highestPower = (int) (Math.log(n) / Math.log(base));
        String output = "";

        if (nCopy == 0) {
            output = "0";
        } else {
            for (int i = highestPower; i >= 0; i--) {
                int currDigit = nCopy / ((int) Math.pow(base, i));
                if (currDigit < 10)
                    output += currDigit;
                else if (currDigit == 10)
                    output += 'A';
                else if (currDigit == 11)
                    output += 'B';
                else if (currDigit == 12)
                    output += 'C';
                else if (currDigit == 13)
                    output += 'D';
                else if (currDigit == 14)
                    output += 'E';
                else if (currDigit == 15)
                    output += 'F';

                nCopy = nCopy % ((int) Math.pow(base, i));
            }
        }

        System.out.println(n + " base 10 " + " = " + output + " base " + base);
    }
}
