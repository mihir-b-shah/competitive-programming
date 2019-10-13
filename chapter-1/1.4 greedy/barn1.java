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
 TASK: barn1

 */
public class barn1 {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\nnOUTPUT.txt"

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("barn1.out")));
        BufferedReader f = new BufferedReader(new FileReader("barn1.in"));

        String s = f.readLine();
        String IM = s.substring(s.indexOf(" ") + 1);

        int M = Integer.parseInt(s.substring(0, s.indexOf(" ")));
        int S = Integer.parseInt(IM.substring(0, IM.indexOf(" ")));
        int C = Integer.parseInt(IM.substring(IM.indexOf(" ") + 1));

        int[] cowy = new int[C];

        int counter = 0;

        for (int i = 0; i < C; i++) {
            cowy[i] = Integer.parseInt(f.readLine());
        }
        
        cowy = sort(cowy);
        
        //Determine breakpoints
        int[] breaks = findBreakpoints(cowy, M-1);
        int sum = 0;
        
        if(M > C)
        {
           sum = C;
            
        } else {
        
            int[] breaks2 = new int[M];

            for(int m = 0; m<breaks.length; m++)
                breaks2[m] = breaks[m];

            breaks2[M-1] = cowy.length - 1;


            int start = -1;

            for(int j = 0; j<breaks2.length; j++)
            {
                int diff = cowy[breaks2[j]] - cowy[start+1];
                start = breaks2[j];
                sum += diff + 1;

            }
        
        }
        
        out.println(sum);
        out.close();
        

    }    
    
    
    
    public static int[] findBreakpoints(int[] cowy, int numBreaks)
    {
        int[] differences = findDifferences(cowy);
        int[] breaks = new int[numBreaks];
        
        for(int i = 0; i<numBreaks; i++)
        {
            if(fullMaxes(differences))
            {
                int[] newBreaks = new int[i];
                
                for(int j = 0; j<newBreaks.length; j++)
                {
                    newBreaks[j] = breaks[j];
                }
                
                return newBreaks;
            }
            
            int K = findMaxIndex(differences);
            breaks[i] = K;
            differences[K] = Integer.MIN_VALUE;
        }
        
        return breaks;
    }
    
    public static boolean fullMaxes(int[] cowy)
    {
        for(int i: cowy)
            if(i != Integer.MIN_VALUE)
                return false;
        
        return true;
    }
    
    public static int[] sort(int[] cowy)
    {
        for(int i = 0; i<cowy.length; i++)
        {
            for(int j = i; j<cowy.length; j++)
            {
                if(cowy[j] < cowy[i])
                {
                    int temp = cowy[j];
                    cowy[j] = cowy[i];
                    cowy[i] = temp;
                }
            }
        }
        
        return cowy;
    }
    
    public static int findMaxIndex(int[] cowy)
    {
        int index = 0;
        int max = 0;
        
        for(int i = 0; i<cowy.length; i++)
        {
            if(cowy[i] > max)
            {
                max = cowy[i];
                index = i;
            }
        }
        
        return index;
    }
    
    public static int[] findDifferences(int[] cowy)
    {
        int[] x = new int[cowy.length - 1];
        
        for(int i = 0; i<cowy.length - 1; i++)
        {
            x[i] = cowy[i+1] - cowy[i];
        }
        
        return x;
    }
    
    public static boolean containsValue(int[] cowy, int item)
    {
        for(int i: cowy)
            if(i == item)
                return true;
  
        return false;
    }
}
