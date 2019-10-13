package oldprojects;


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
TASK: ride

 */ 

public class ride {

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        BufferedReader f = new BufferedReader(new FileReader("ride.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ride.out")));

        String s1 = f.readLine();
        String s2 = f.readLine();
        
        int one = 1;
        int two = 1;
        
        for(int i = 0; i<s1.length(); i++)
        {
            one *= (int) s1.charAt(i) - 64;
        }
        
        for(int i = 0; i<s2.length(); i++)
            two *= (int) s2.charAt(i) - 64;
        
        
        
        if(one % 47 == two % 47)
            out.println("GO");
        else
            out.println("STAY");

        out.close();                                  
    }
    
}

