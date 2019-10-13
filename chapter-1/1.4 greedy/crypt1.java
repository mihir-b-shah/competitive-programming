package oldprojects;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*

 ID: mihirsh1
 LANG: JAVA
 TASK: crypt1

 */
public class crypt1 {

    public static int COUNTER = 0;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\nnOUTPUT.txt"

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("crypt1.out")));
        BufferedReader f = new BufferedReader(new FileReader("crypt1.in"));

        int toCome = Integer.parseInt(f.readLine());
        int[] digits = new int[toCome];
        
        for(int i = 0; i<toCome; i++)
        {
            digits[i] = Character.getNumericValue((char)f.read());
            f.read();
        }

        int[] indices = new int[5];
        permutate(digits, 5, indices);
        
        out.println(COUNTER);
        out.close();
        
    }
    
    public static void permutate(int[] digits, int level, int[] indices)
    {              
        if(level > 0)
        {
            for(int i = 0; i<digits.length; i++)
            {
                indices[5 - level] = digits[i];
                permutate(digits, level - 1, indices);
            }
        } else {
            multiply(indices, digits);
        }
    }
    
    public static void multiply(int[] indices, int[] digits)
    {
        int top = indices[0]*100 + indices[1]*10 + indices[2];
        int bottom = indices[3]*10 + indices[4];
        
        if(extractDigits(top * indices[4], digits, true) && extractDigits(top * indices[3], digits, true) && extractDigits(top*bottom, digits, false))
        {
            COUNTER++;
        }
    }
    
    public static boolean extractDigits(int value, int[] digits, boolean pp)
    {
        if(value > 1000 && pp)
            return false;
        
        while(value > 0)
        {
            int digit = value % 10;

            if(!containsDigit(digit, digits))
            {
                return false;
            }
            
            value /= 10;
        }
        
        return true;
    }
    
    public static boolean containsDigit(int digit, int[] digits)
    {
        for(int i: digits)
            if(i == digit)
                return true;
        
        return false;
    }

}