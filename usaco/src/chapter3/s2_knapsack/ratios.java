package chapter3.s2_knapsack;


/*
ID: mihirsh1
TASK: ratios
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class ratios {
   
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("ratios.in"));
        PrintWriter out = new PrintWriter("ratios.out");

        int[] goal = {f.nextInt(), f.nextInt(), f.nextInt()};
        int[][] mixes = new int[3][3];
        String output = null;
        
        for(int i = 0; i<3; i++)
            for(int j = 0; j<3; j++)
                mixes[i][j] = f.nextInt();
        
        boolean done = false;
        
        for(int i = 0; i<=100; i++) {
            for(int j = 0; j<=100; j++) {
                for(int k = 0; k<=100; k++) {
                    int a = i*mixes[0][0] + j*mixes[1][0] + k*mixes[2][0];
                    int b = i*mixes[0][1] + j*mixes[1][1] + k*mixes[2][1];
                    int c = i*mixes[0][2] + j*mixes[1][2] + k*mixes[2][2];
                    
                    // Find whether (a,b,c) is an integer multiple of (L,M,N)
                    
                    if(goal[1] == 0 && b == 0 || goal[0] == 0 && a == 0 || b/goal[1] == a/goal[0] && b % goal[1] == 0 && a % goal[0] == 0)
                        if(goal[2] == 0 && c == 0 || c/goal[2] == b/goal[1] && c % goal[2] == 0) {
                            if(!(i == 0 && j == 0 && k == 0) || (i == 0 && j == 0 && k == 0) && goal[0] == 0 && goal[1] == 0 && goal[2] == 0) {
                                output = String.format("%d %d %d %d", i, j, k, 
                                       goal[0] == 0 ? goal[1] == 0 ? goal[2] == 0 ? 0 : c/goal[2] : b/goal[1] : a/goal[0]);
                                done = true;
                            }
                        }
                    
                    if(done)
                        break;
                }
                
                if(done)
                    break;
            }
            
            if(done)
                break;
        }
        
        out.println(output == null ? "NONE" : output);
        out.flush();
        out.close();
        f.close();
    }

    
    
}
