package chapter3.s1_minspantree;


/*
ID: mihirsh1
TASK: stamps
LANG: JAVA
*/

import java.util.*;
import java.io.*;

public class stamps {

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("stamps.in"));
        PrintWriter out = new PrintWriter("stamps.out");
        
        int K = f.nextInt();
        int N = f.nextInt();
        final int INFINITY = 1000000000;
        
        int[] stamps = new int[N];
        
        for(int i = 0; i<N; i++) 
            stamps[i] = f.nextInt();
        
        Arrays.sort(stamps);
        int MAX_STAMP = stamps[N-1];
        int[] bounds = new int[N+1];
        
        int[] dp = new int[MAX_STAMP*K+1];
        Arrays.fill(dp, INFINITY);
        dp[0] = 0;

        for(int i = 1; i<=N; i++) {         
            for(int j = 1; j<=MAX_STAMP*K; j++) {
                dp[j] = Math.min(1 + (j >= stamps[i-1] ? dp[j-stamps[i-1]] : INFINITY), dp[j]);
                
                if(bounds[i] == 0 && dp[j] > K) 
                    bounds[i] = j-1; 
            }
            
            if(bounds[i] == 0) {
                bounds[i] = MAX_STAMP*K;
            }
        }

        int max = 0;
        
        for(int i: bounds)
            if(i > max)
                max = i;
        
        out.println(max);
        out.flush();
        out.close();
        f.close();
    }    
}
