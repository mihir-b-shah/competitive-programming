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
 TASK: numtri

 */
public class numtri {

    public static PrintWriter out;
    
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
        // "C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\ariprogOUT.txt"

        out = new PrintWriter(new BufferedWriter(new FileWriter("numtri.out")));
        BufferedReader f = new BufferedReader(new FileReader("numtri.in"));
        
        int toCome = Integer.parseInt(f.readLine());
        int[][][] storage = new int[toCome][][];
        
        for(int i = 1; i<=toCome; i++)
        {
            int[][] stuff = new int[i][2];
            String[] split = f.readLine().split(" ");
            
            for(int j = 0; j<i; j++)
            {
                stuff[j][0] = Integer.parseInt(split[j]);
                stuff[j][1] = Integer.MIN_VALUE;
            }
            
            storage[i-1] = stuff;
        }
        
        int max = sumHelper(0, toCome, 0, storage);
        
        out.println(max);
        out.close();
        
    }
    
    public static int sumHelper (int level, int total, int index, int[][][] storage)
    {
        int dfs_val;
        
        if(level < total - 1)
        {
            if(storage[level][index][1] == Integer.MIN_VALUE)
                dfs_val = Math.max(sumHelper(level + 1, total, index, storage), sumHelper(level + 1, total, index + 1, storage)) + storage[level][index][0];
            else
                dfs_val = storage[level][index][1];
                
            storage[level][index][1] = dfs_val;
            return dfs_val;
        
        } else {
            return storage[level][index][0];
        }
    }
}
