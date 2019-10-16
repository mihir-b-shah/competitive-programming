package chapter3.s3_euleriantour;


/*
ID: mihirsh1
TASK: shopping
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class shopping {

    public static State[] offers;
    public static int[][][][][] dp;
    
    public static class State {
        
        public int[] state;
        public State() {
            state = new int[6];
        }

        @Override
        public String toString() {
            return Arrays.toString(state);
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("shopping.in"));
        PrintWriter out = new PrintWriter("shopping.out");

        int[] codemap = new int[1000];
        Arrays.fill(codemap, -1);
        
        int[] price = new int[5];
        int s = f.nextInt();
        
        offers = new State[s];
        
        int ctr = 0;
        for(int i = 0; i<s; i++) {
            int N = f.nextInt();
            offers[i] = new State();
            for(int j = 0; j<N; j++) {
                int c = f.nextInt(); 
                if(codemap[c] == -1) codemap[c] = ctr++;
                offers[i].state[codemap[c]] = f.nextInt();
            }
            offers[i].state[5] = f.nextInt();
        }
        
        State init = new State();
        int b = f.nextInt();
        
        for(int i = 0; i<b; i++) {
            int c = f.nextInt();
            if(codemap[c] == -1) codemap[c] = ctr++;
            init.state[codemap[c]] = f.nextInt();
            price[codemap[c]] = f.nextInt();
        }
  
        dp = new int[6][6][6][6][6];
        final int INFINITY = 1000000000;
        
        for(int v = 0; v<=init.state[0]; v++) 
            for(int w = 0; w<=init.state[1]; w++) 
                for(int x = 0; x<=init.state[2]; x++) 
                    for(int y = 0; y<=init.state[3]; y++) 
                        for(int z = 0; z<=init.state[4]; z++) 
                            if(v != 0 || w != 0 || x != 0 || y != 0 || z != 0) {
                                int pr = v*price[0]+w*price[1]+x*price[2]+y*price[3]+z*price[4];
                                int min_of = INFINITY;
                                
                                for(int k = 0; k<s; k++) 
                                    min_of = Math.min(min_of, check(v,w,x,y,z,offers[k].state) ? offers[k].state[5] 
                                                        + dp[v-offers[k].state[0]][w-offers[k].state[1]][x-offers[k].state[2]]
                                                        [y-offers[k].state[3]][z-offers[k].state[4]] : INFINITY);
                                
                                dp[v][w][x][y][z] = Math.min(pr, min_of);
                            }

        out.println(dp[init.state[0]][init.state[1]][init.state[2]][init.state[3]][init.state[4]]);
        out.flush();
        out.close();
        f.close();
    }
    
    public static boolean check(int v, int w, int x, int y, int z, int[] state) {
        return v >= state[0] && w >= state[1] && x >= state[2] && y >= state[3] && z >= state[4];
    }
}