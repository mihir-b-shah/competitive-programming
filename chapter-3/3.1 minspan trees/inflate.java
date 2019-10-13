package oldprojects;


/*
ID: mihirsh1
TASK: inflate
LANG: JAVA
*/

import java.util.*;
import java.io.*;

public class inflate {
    
    public static class Contest implements Comparable {
        public int weight;
        public int val;
        
        public Contest(int v, int w) {
            val = v;
            weight = w;
        }
        
        @Override
        public int compareTo(Object o) {
            return val - ((Contest) o).val;
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("inflate.in"));
        PrintWriter out = new PrintWriter("inflate.out");
        
        int M = f.nextInt();
        int N = f.nextInt();
        
        Contest[] array = new Contest[N];
        
        for(int i = 0; i<N; i++) {
            array[i] = new Contest(f.nextInt(), f.nextInt());
        }
        
        Arrays.sort(array);
        
        int[][] dp = new int[2][M+1];
        
        for(int i = 1; i<=N; i++) {
            for(int j = 1; j<=M; j++) {
                if(array[i-1].weight > j) {
                    dp[1][j] = dp[0][j];
                } else {
                    dp[1][j] = Math.max(dp[0][j], array[i-1].val+dp[1][j-array[i-1].weight]);
                }
            }
            
            dp[0] = dp[1];
        }
        
        out.println(dp[1][M]);
        out.flush();
        out.close();
        f.close();
    }
}
