package chapter3.s4_compgeometry;


/*
ID: mihirsh1
TASK: rockers
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class rockers {

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("rockers.in"));
        PrintWriter out = new PrintWriter("rockers.out");

        int N = f.nextInt();
        int T = f.nextInt();
        int M = f.nextInt();

        int[] len = new int[N];
        int[] dp = new int[1 << (N+1)];
        int[] ct = new int[1 << N];
        
        for(int i = 0; i<N; i++)
            len[i] = f.nextInt();
        
        dp[0] = M;
        dp[1] = 0;
        int ctr = -1;
        int max = 0;
        
        for(int i = 1; i<dp.length/2; i++) {
            
            if(i == 1 << (1+ctr))
                ctr++;
            
            int index = (i - (1 << ctr)) << 1;
            int val = i << 1;
            ct[i] = 1 + ct[index >>> 1];

            int l = len[ctr];
            if(l > T) dp[val] = -1;
            else if(dp[index + 1] >= l) {
                dp[val] = dp[index];
                dp[val+1] = dp[index+1]-l;
            } else if(dp[index] > 0) {
                dp[val] = dp[index]-1; dp[val+1] = T-l;
            } else dp[val] = -1; // Not possible.
            
            if(dp[val] != -1) 
                max = Math.max(max, ct[i]);
        }
        
        out.println(max);
        out.flush();
        out.close();
        f.close();
    }

}
