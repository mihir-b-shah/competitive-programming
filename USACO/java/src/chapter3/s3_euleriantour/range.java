package chapter3.s3_euleriantour;


/*
ID: mihirsh1  
TASK: range
LANG: JAVA
 */

import java.io.*;
import java.util.*;

public class range {

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("range.in"));
        PrintWriter out = new PrintWriter("range.out");

        int N = f.nextInt();
        f.nextLine();
        boolean[][] mat = new boolean[N][N];
        boolean[][] temp = new boolean[N-1][N-1];
        
        for(int i = 0; i<N; i++) {
            String next = f.nextLine();
            for(int j = 0; j<next.length(); j++)
                mat[i][j] = next.charAt(j) == '1';
        }
        
        // Build the initial 2x2 prefix sums.
        
        int[] ct = new int[N+1];
        
        for(int k = 2; k<=N; k++) {
            for(int i = 0; i<temp.length; i++) 
                for(int j = 0; j<temp.length; j++) {
                    temp[i][j] = mat[i][j] && mat[i+1][j] && mat[i][j+1] && mat[i+1][j+1]; 
                    if(temp[i][j]) ct[k]++;
                }
            
            mat = temp;
            temp = new boolean[temp.length-1][temp.length-1];
        }
        
        for(int i = 2; i<=N; i++)
            if(ct[i] != 0) out.printf("%d %d%n", i, ct[i]);
        
        out.flush();
        out.close();
        f.close();
    }

}
