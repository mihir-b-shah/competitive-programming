
package chapter1.s6_binary;

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
 TASK: pprime

 */
public class pprime {

    public static PrintWriter out;
    
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
        // "C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\ariprogOUT.txt"
        
        out = new PrintWriter(new BufferedWriter(new FileWriter("pprime.out")));
        BufferedReader f = new BufferedReader(new FileReader("pprime.in"));
        
        String s = f.readLine();
        int lBound = Integer.parseInt(s.substring(0, s.indexOf(" ")));
        int uBound = Integer.parseInt(s.substring(s.indexOf(" ") + 1));
        
        String low = Integer.toString(lBound);
        String upp = Integer.toString(uBound);

        int low_LIM; int upp_LIM;
        
        try {
            low_LIM = Integer.parseInt(low.substring(0, low.length()/2));
        } catch (NumberFormatException e) {
            low_LIM = 0; 
        }
        
        try {
            upp_LIM = Integer.parseInt(upp.substring(0, upp.length()/2));
        } catch (NumberFormatException e) {
            upp_LIM = 0; 
        }
        
        if(low_LIM > upp_LIM)
        {
            int temp = low_LIM;
            low_LIM = upp_LIM;
            upp_LIM = temp;
        }
        
        // 123000 --> 1120000
        
        if(findLen(uBound) - findLen(lBound) == 1)
        {
            low = Integer.toString((int) Math.pow(10, findLen(lBound)));
            
            try {
                low_LIM = Integer.parseInt(low.substring(0, low.length()/2));
            } catch (NumberFormatException e) {
                low_LIM = 0; 
            }
        }

        for(int i = low_LIM; i<=upp_LIM; i++)
        {
            int j = i;
            int NUMBER = i* (int) Math.pow(10, findLen(i));
            int counter = 0;
            
            while(j != 0)
            {
                int x = j % 10;
                counter++;
                NUMBER += Math.pow(10, findLen(i) - counter) * x;
                j /= 10;

            }
            
            if(NUMBER >= lBound && NUMBER <= uBound)
            {
                if(isPrime(NUMBER))
                    out.println(NUMBER);
            }
         
            else if(NUMBER > uBound)
                break;
         
            int numMod = NUMBER % (int) Math.pow(10, findLen(NUMBER)/2);
            int numCopy = NUMBER - numMod;
            numCopy *= 10;
            
            for(int k = 0; k<= 9; k++)
            {
                int numCC = numCopy + k* (int) Math.pow(10, findLen(NUMBER)/2);
                numCC += numMod;
                
                if(numCC >= lBound && numCC <= uBound)
                {
                    if(isPrime(numCC))
                        out.println(numCC);
                }
                else if(numCC < lBound && 2*findLen(numCC)-1 != findLen(lBound))
                    continue;
                else if(numCC > uBound)
                    break;
                
            }
        }
     
        out.close();
        
    }
    
    public static int findLen(int num)
    {
        int counter = 0;
        
        if(num == 0)
            return 1;
        
        while(num != 0)
        {
            num /= 10;
            counter++;
        }    
        
        return counter;
    }
    
    public static boolean isPrime(int num)
    {
        for(int i = 2; i<=Math.sqrt(num); i++)
        {
            if(num % i == 0)
                return false;
        }
        
        return true;
    }
    
}