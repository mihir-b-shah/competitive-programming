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
TASK: dualpal

 */

public class dualpal {

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        // "C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\nnOUTPUT.txt"
        
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dualpal.out")));
        BufferedReader f = new BufferedReader(new FileReader("dualpal.in"));
        
        String s = f.readLine();
        int NUM = Integer.parseInt(s.substring(0, s.indexOf(" ")));
        int START = Integer.parseInt(s.substring(s.indexOf(" ") + 1));
        int found = 0;
        int currNum = START + 1;
        int doubleCt = 0;
        
        while(found < NUM)
        {
            doubleCt = 0;
            
            for(int i = 2; i<11; i++)
            {
                if(isPalindrome(baseConverter(i, currNum)))
                {
                    doubleCt++;
                }
                
                if(doubleCt == 2)
                {
                    found++;
                    out.println(currNum);
                    break;
                }
            }
            
            currNum++;
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