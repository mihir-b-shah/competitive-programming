package oldprojects;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**

ID: mihirsh1
LANG: JAVA
TASK: milk2

 */

public class milk2 {
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        // "C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\milkINPUT4.txt"
        
        BufferedReader f = new BufferedReader(new FileReader("milk2.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk2.out")));

        int numTimes = Integer.parseInt(f.readLine());
        int[][] storage = new int[numTimes][2];
        
        for(int i = 0; i < storage.length; i++)
        {
            String s = f.readLine();
            
            storage[i][0] = Integer.parseInt(s.substring(0, s.indexOf(" ")));
            storage[i][1] = Integer.parseInt(s.substring(s.indexOf(" ") + 1));
        }
        
        sortArray(storage, 0);
        
        int length = 0;
        int currLen = 0;
        int without = 0;
        int currWo = 0;
        int start = 0;
        int end = 0;
        int lBound = 0;
        int uBound = 0;
        int hardLow = 0;
        
        start = storage[0][0];
        end = storage[0][1];
        
        lBound = start;
        hardLow = start;
        uBound = end;
        
        currLen = uBound - lBound;
            
        if(currLen > length)
            length = currLen;
           
        if(uBound < start)
        {
            currWo = start - uBound;
        }
        
        if(currWo > without)
            without = currWo;
        
        for(int i = 1; i<numTimes; i++)
        {
            start = storage[i][0];
            end = storage[i][1];
            
            if(uBound < start)
            {
                currWo = start - uBound;
                lBound = start;
            }
            
            if(start <= hardLow && end >= uBound)
            {
                without = 0;
                currWo = 0;
            }
            
            if(start < lBound)
                lBound = start;
            
            if(end > uBound )//&& start <= uBound)
                uBound = end;
            
            currLen = uBound - lBound;
            
            if(currLen > length)
                length = currLen;
            
            if(currWo > without)
                without = currWo;
        }
        
        out.println(length + " " + without);
        out.close();
        
        
        
    }
    
    public static void sortArray(int[][] array, int col)
    {
        for(int i = 0; i<array.length; i++)
        {
            for(int j = i; j<array.length; j++)
            {
                if(array[i][col] > array[j][col])
                {
                    int[] temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }
    
    public static void printArray(int[][] array)
    {
        for(int i = 0; i<array.length; i++)
        {
            for(int j = 0; j<array[0].length; j++)
            {
                System.out.print(array[i][j] + " ");
            }
            
            System.out.println("\n");
        }
    }
}   
