package chapter3.s2_knapsack;

/*
ID: mihirsh1
TASK: kimbits
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class kimbits {

    public static int[][] pascal_prefix;
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("kimbits.in"));
        PrintWriter out = new PrintWriter("kimbits.out");

        int N = f.nextInt();
        int K = f.nextInt();
        long I = f.nextLong();
        
        // Construct pascal's triangle as prefix sums
        
        pascal_prefix = new int[N+1][];
        
        for(int i = 0; i<N+1; i++) {
            pascal_prefix[i] = new int[i+2];
            pascal_prefix[i][1] = 1;
           
            for(int j = 2; j<=i; j++) 
                pascal_prefix[i][j] = pascal_prefix[i-1][j] - (j >= 2 ? pascal_prefix[i-1][j-2] : 0) + pascal_prefix[i][j-1];
            
            if(i != 0)
                pascal_prefix[i][i+1] = pascal_prefix[i][i] + 1;
        }
        
        int res = bisect(N, K, I);
        
        for(int i = N-1; i>=0; i--) 
            if((res & 1 << i) != 0)
                out.print('1');
            else
                out.print('0');
        
        out.println();
        out.flush();
        out.close();
        f.close();
    }

    public static int bisect(int N, int K, long I) { 
        
        if(K > N)
            K = N;
        
        if(N == 0)
            return 0;
        
        int sum = 0;
        
        try {
            sum = Csum(N, K);
        } catch (ArrayIndexOutOfBoundsException e) {
            sum = Csum(N, K-1);
        }
        
        if(I > sum) {
            return (1 << N-1) + bisect(N-1,K-1,I-sum);
        } else {
            return bisect(N-1,K,I);
        } 
    }
    
    public static int Csum(int n, int r) {
        return pascal_prefix[n-1][r+1];
    }
}