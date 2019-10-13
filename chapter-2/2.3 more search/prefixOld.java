package oldprojects;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

/*

 ID: mihirsh1
 LANG: JAVA
 TASK: prefix

 */

public class prefixOld {

    public static PrintWriter out;
    public static String seq;
    public static HashSet<String> prims;
    public static int MAX_LEN;
    public static int[] storage;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        out = new PrintWriter("prefix.out");
        BufferedReader f = new BufferedReader(new FileReader("prefix.in"));
        
        prims = new HashSet<>();
        StringBuilder prim = new StringBuilder();
        int ch = -2;

        do {

            while ((ch = f.read()) != 46 && ch != 32 && ch != 10 && ch != 13) {
                prim.append((char) ch);
            }

            String s = "";
            if (prim.length() != 0) {
                s = prim.toString();
                prims.add(s);
            }

            if (s.length() > MAX_LEN) {
                MAX_LEN = s.length();
            }

            prim.setLength(0);
        } while (ch != 46);

        f.skip(1);
        
        StringBuilder seqOrig = new StringBuilder();
        
        do {
            
            while ((ch = f.read()) != -1 && ch != 13 && ch != 10) {
                seqOrig.append((char) ch);
            }
            
        } while (ch != -1);
        
        seq = seqOrig.toString();
        
        storage = new int[seq.length() + 1];
        
        int max = findIndex(0);
        out.println(max);
               
        f.close();
        out.flush();
        out.close();
    }

    public static int findIndex(int start) {
        
        int[] max = new int[3];
        int counter = -1;
        
        for (int i = 1; i <= MAX_LEN; i++) {
            if(storage[start] == 0) {
                if (start + i <= seq.length() && prims.contains(seq.substring(start, start + i))) {
                    counter++;
                    max[counter] = findIndex(start + i);
                }
            } else {
                return storage[start];
            }        
        }
        
        return superMax(max, start);
    }
    
    public static int superMax(int[] nums, int start)
    {
        if(nums[0] == 0) {
            return start;
        } else if(nums[1] == 0) {
            return nums[0];
        } else if(nums[2] == 0) {
            return Math.max(nums[0], nums[1]);
        } else {
            return Math.max(Math.max(nums[0], nums[1]), nums[2]);
        }
    }
}
