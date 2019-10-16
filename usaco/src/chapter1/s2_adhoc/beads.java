package chapter1.s2_adhoc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**

ID: mihirsh1
LANG: JAVA
TASK: beads

 */

public class beads {
    
    public static String necklace = "";
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        // "C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\beadsINPUT3.txt"
        
        BufferedReader f = new BufferedReader(new FileReader("beads.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("beads.out")));
    
        f.readLine();
        necklace = f.readLine();
        necklace += necklace;
        
        int i = 0;
        int total = 0;
           
        // Problem: regression
        
        
        while(i < necklace.length()/2)
        {
            int newX = countNum(i);
            
            if(newX > total)
                total = newX;
            
            if(newX == necklace.length())
            {
                total /= 2;
                break;
            }
            
            if(total == necklace.length())
                break;
            
            i++;
        }
        
        out.println(total);
        out.close();
    }
    
    public static int countNum(int i)
    {
        int partOne = seek(i, 0);
        int partTwo = seek(i + partOne, 0);
        
        if(partOne + partTwo == necklace.length())
        {
            return (partOne + partTwo)/2;
        } else if (partOne + partTwo > necklace.length()/2) {
            return 0;
        }
      
        return partOne + partTwo;
    }
    
    //Takes care of test cases such: <r,b> ..................... <b,r>
    
    public static int seek(int i, int more)
    {
        int index = 0;
        i += more;

        try {

            char term = necklace.charAt(i);
            char opp = getOpp(term);

            while(necklace.charAt(i + index) != opp)
                index++;
            
            int total = index + more;
            return total;
            
        } catch (StringIndexOutOfBoundsException s) {
            
            return index + more;
        
        } catch (IllegalArgumentException e) {
            
            int advance = wHandler(i + index);
            return seek(i, advance);
        }    
    }
    
    public static int wHandler(int i)
    {
        int index = 0;
        
        try {
            
            while(necklace.charAt(i + index) == 'w')
                index++;

            return index;
        
        } catch (StringIndexOutOfBoundsException s) {
            
            return index;
        }
    }
    
    public static char getOpp(char c)
    {
        if(c == 'r')
            return 'b';
        else if(c == 'b')
            return 'r';
        else
            throw new IllegalArgumentException();
    }
    
}
