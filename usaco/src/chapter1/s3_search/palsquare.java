package chapter1.s3_search;


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
TASK: palsquare

 */

public class palsquare {

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        // "C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\nnOUTPUT.txt"
        
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("palsquare.out")));
        BufferedReader f = new BufferedReader(new FileReader("palsquare.in"));
        
        int BASE = Integer.parseInt(f.readLine());
        
        for(int i = 1; i<=300; i++)
        {
            String pet = baseConverter(BASE, (int) Math.pow(i, 2));
            if(isPalindrome(pet))
            {
                out.println(baseConverter(BASE, i) + " " + pet);
            }
        }

        out.close();
    }
    
    
    public static String baseConverter(int base, int num)
    {
        int N = (int) (Math.log(num)/Math.log(base));
        String sum = "";

        while(num > 0)
        {
            int add = num / (int) Math.pow(base, N);
            
            if(add > 9)
            {
                sum += (char) (add+55);
                
            } else {
                sum += add;
            }

            num -= add*Math.pow(base, N);
            N--;
        }
        
        while(N > -1)
        {
            sum += "0";
            N--;
        }
        
        return sum;
    }

    public static boolean isPalindrome(String pet) {
        
        for(int i = 0; i<pet.length()/2; i++)
        {
            if(pet.charAt(i) != pet.charAt(pet.length() - 1 - i))
            {
                return false;
            }
        }
        
        return true;
        
    }
}