package oldprojects;


/*
ID: mihirsh1
TASK: game1
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class game1 {

    public static int[] board;
    public static ReturnType[][] dp;
    
    // Use 2d DP to optimize.
    
    public static class ReturnType {
        public int p1;
        public int p2;
        
        public ReturnType(int o, int t) {
            p1 = o;
            p2 = t;
        }
        
        @Override
        public String toString() {
            return String.format("%d %d", p1, p2);
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("game1.in"));
        PrintWriter out = new PrintWriter("game1.out");

        int N = f.nextInt();
        board = new int[N];
        
        dp = new ReturnType[N][N];

        for (int i = 0; i < N; i++) 
            board[i] = f.nextInt();

        ReturnType fin = p1(0, N-1);
        
        out.printf("%d %d%n", fin.p1, fin.p2);
        out.flush();
        out.close();
        f.close();
    }
    
    public static ReturnType p1(int s, int e) {

        ReturnType o = s < e ? dp[s+1][e] != null ? dp[s+1][e] : p2(s+1,e) : null;
        ReturnType t = s < e ? dp[s][e-1] != null ? dp[s][e-1] : p2(s,e-1) : null;
        
        if(s<e) {
            dp[s+1][e] = o;
            dp[s][e-1] = t;
        }
        
        if(o == null && t == null)
            return new ReturnType(board[s], 0);
        else if(o == null) 
            return t;
        else if(t == null)
            return o;
        else if(o.p2 < t.p2)
            return new ReturnType(o.p1 + board[s], o.p2);
        else
            return new ReturnType(t.p1 + board[e], t.p2);
    }
      
    public static ReturnType p2(int s, int e) {
        
        ReturnType o = s < e ? dp[s+1][e] != null ? dp[s+1][e] : p1(s+1,e) : null;
        ReturnType t = s < e ? dp[s][e-1] != null ? dp[s][e-1] : p1(s,e-1) : null;

        if(s<e) {
            dp[s+1][e] = o;
            dp[s][e-1] = t;
        }
        
        if(o == null && t == null)
            return new ReturnType(0, board[s]);
        else if(o == null) 
            return t;
        else if(t == null)
            return o;
        else if(o.p1 < t.p1)
            return new ReturnType(o.p1, o.p2 + board[s]);
        else
            return new ReturnType(t.p1, t.p2 + board[e]);
    }
}
