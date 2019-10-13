package oldprojects;


/*

ID: mihirsh1
TASK: fracdec
LANG: JAVA


*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class fracdec {

    public static void main(String[] args) throws Exception {

        BufferedReader f = new BufferedReader(new FileReader("fracdec.in"));
        PrintWriter out = new PrintWriter("fracdec.out");

        int N = nextInt(f);
        int D = nextInt(f);
        int counter = 0;
        int stop = Integer.MAX_VALUE - 1;

        // What is classified as repeating or not ?? 
        
        HashMap<Integer, Integer> digits = new HashMap<>();
        StringBuilder decimal = new StringBuilder();
        int I = N / D;
        int N_orig = N;
        N %= D;

        decimal.append(I);
        decimal.append('.');
        
        int pos = decimal.length();
        
        while (N % D != 0) {

            if (digits.containsKey(N)) {
                stop = digits.get(N);
                break;
            } else {
                digits.put(N, counter);
            }

            N *= 10;
            decimal.append(N/D);
            N %= D;

            counter++;
        }

        if(N_orig % D == 0)
            decimal.append(0);
        
        try {
            decimal.insert(stop + pos, '(');
            decimal.append(')');
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        
        for(int i = 0; i<decimal.length(); i++) {
            if(i > 0 && i % 76 == 0)
                out.print("\n");
            out.print(decimal.charAt(i));
        }
        
        if(decimal.length() % 76 != 0)
            out.print("\n");
    
        out.flush();
        out.close();
        f.close();
        System.exit(0);

    }

    public static int nextInt(BufferedReader f) throws Exception {
        int ch = -2;
        int num = 0;

        do {

            while ((ch = f.read()) != 32 && ch != 10 && ch != 13 && ch != -1) {
                num = num * 10 + (ch - 48);
            }

        } while (ch != 32 && ch != 13 && ch != 10 && ch != -1);

        return num;
    }

}
