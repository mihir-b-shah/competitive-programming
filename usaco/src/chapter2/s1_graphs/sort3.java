package chapter2.s1_graphs;


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
 TASK: sort3

 */
public class sort3 {

    public static PrintWriter out;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\ariprogOUT.txt"

        out = new PrintWriter(new BufferedWriter(new FileWriter("sort3.out")));
        BufferedReader f = new BufferedReader(new FileReader("sort3.in"));
        
        int toCome = Integer.parseInt(f.readLine());
        int[] sequence  = new int[toCome];
        int numFlips = 0;
        
        for(int i = 0; i<toCome; i++)
            sequence[i] = Integer.parseInt(f.readLine());
        
        int breakpoint_1_2 = count(sequence, 1);
        int breakpoint_2_3 = count(sequence, 2) + breakpoint_1_2;
        
        for(int i = breakpoint_2_3; i<sequence.length; i++)
        {
            if(sequence[i] == 1)
            {
                int j = breakpoint_1_2 - 1;
                
                while(j>= 0 && sequence[j] != 3)
                    j--;
                
                if(j == -1)
                {
                    j = breakpoint_2_3 - 1;
                
                    while(sequence[j] != 3)
                        j--;
                }
                
                int temp = sequence[j];
                sequence[j] = sequence[i];
                sequence[i] = temp;
                
                numFlips++;
                
            } else if(sequence[i] == 2) {
                
                int j = breakpoint_2_3 - 1;
                
                while(sequence[j] != 3)
                    j--;
                
                int temp = sequence[j];
                sequence[j] = sequence[i];
                sequence[i] = temp;
                numFlips++;
            }
        }
        
        for(int i = breakpoint_1_2; i<breakpoint_2_3; i++)
        {
            if(sequence[i] == 1)
            {
                int j = breakpoint_1_2 - 1;
                
                while(j>= 0 && sequence[j] != 2)
                    j--;
                
                int temp = sequence[j];
                sequence[j] = sequence[i];
                sequence[i] = temp;
                numFlips++;
            }
        }
        
        out.println(numFlips);
        out.close();
    }
    
    
    
    public static int count(int[] sequence, int number)
    {
        int counter = 0;
        
        for(int i: sequence)
            if(i == number)
                counter++;
        
        return counter;
    }
}