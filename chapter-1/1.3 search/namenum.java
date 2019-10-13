
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

/**

ID: mihirsh1
LANG: JAVA
TASK: namenum

 */

public class namenum {

    public static PrintWriter out;
    public static int counter = 0;
    public static HashSet<String> cowy;

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        // "C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\nnOUTPUT.txt"
        
        long t1 = System.currentTimeMillis();
        
        cowy = new HashSet<>();
        out = new PrintWriter(new BufferedWriter(new FileWriter("namenum.out")));
        BufferedReader g = new BufferedReader(new FileReader("dict.txt"));

        for(int i = 0; i<4617; i++)
        {
            String s = g.readLine();
            cowy.add(s);
        }
        
        /*
         long t2 = System.currentTimeMillis();
         System.out.println("READ DICT: " + (t2-t1));
        */        
                
        
        BufferedReader f = new BufferedReader(new FileReader("namenum.in"));
        String s = f.readLine();

        char[][] map = {{'A','B','C'}, {'D', 'E', 'F'}, {'G', 'H', 'I'}, {'J', 'K', 'L'}, {'M', 'N', 'O'}, {'P', 'R', 'S'}, {'T', 'U', 'V'}, {'W', 'X', 'Y'}};
        char[][] keys = new char[s.length()][];
        
        for(int i = 0; i < s.length(); i++)
        {
            int digit = Character.getNumericValue(s.charAt(i));
            keys[i] = map[digit - 2];
        }
        /*
        long t3 = System.currentTimeMillis();
         System.out.println("INIT KEYS: " + (t3-t2));
                */
        int[] indices = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
        recur(s.length(), s.length(), keys, indices);  
        
        /*
        long t4 = System.currentTimeMillis();
         System.out.println("AFTER REC: " + (t4-t3));
         */
        if(counter == 0)
            out.println("NONE");
        
        out.close();
        /*
        System.out.println("TOTAL: " + (System.currentTimeMillis()-t1));
                */
    }
    
    public static void recur(int total, int nLoop, char[][] keys, int[] indices) throws IOException 
    {
        if(nLoop > 0)
            for(int k = 0; k<3; k++)
            {
                int[] a1 = indices.clone();
                
                a1[total - nLoop] = k;
                recur(total, nLoop-1, keys, a1);
            }
        else
        {
            String s = "";
            
            for(int i = 0; i<keys.length; i++)
            {
                s += keys[i][indices[i]];
                
            }   
            
            try {

                if(cowy.contains(s))
                {
                    counter++;
                    out.println(s);
                }
                
            } catch (NullPointerException npe) {
                return;
            }
        }     
    }
    
    public static int[][] determineBreakpoints()
    {
        return null;
    }
}