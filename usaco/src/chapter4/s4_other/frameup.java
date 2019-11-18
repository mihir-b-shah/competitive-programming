package chapter4.s4_other;

import java.util.*;
import java.io.*;

/*
ID: mihirsh1
TASK: frameup
LANG: JAVA
*/

public class frameup {
    
    public static final int INF = 1_000_000_000;
    public static byte[][] matrix;
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("frameup.in"));
        PrintWriter out = new PrintWriter("frameup.out");
        
        final int H,W;
        H = f.nextInt();
        W = f.nextInt();
        f.nextLine();
        
        char[][] board = new char[H][W];
        char maxchar = 0;
        
        for(int i = 0; i<H; ++i) {
            board[i] = f.nextLine().toCharArray();
            for(int j = 0; j<W; ++j) {
                maxchar = (char) Math.max(maxchar, board[i][j]);
            }
        }
        
        // 0 is minX, 1 is minY, 2 is maxX, 3 is maxY
        final int numframes = maxchar-'A'+1;
        int[][] maxmins = new int[numframes][4];
        for(int i = 0; i<numframes; ++i) {
            maxmins[i][0] = INF;
            maxmins[i][1] = INF;
        }
        
        for(int i = 0; i<H; ++i) {
            for(int j = 0; j<W; ++j) {
                if(board[i][j] != '.') {
                    int[] arr = maxmins[board[i][j]-'A'];
                    arr[0] = Math.min(arr[0], j); // width
                    arr[1] = Math.min(arr[1], i); // height
                    arr[2] = Math.max(arr[2], j);
                    arr[3] = Math.max(arr[3], i);
                }
            }
        }

        matrix = new byte[numframes][numframes];

        for(int i = 0; i<H; ++i) {
            for(int j = 0; j<W; ++j) {
                int index = board[i][j]-'A';
                for(int k = 0; k<numframes; ++k) {
                    int[] mm = maxmins[k];
                    if((j == mm[0] || j == mm[2]) && i >= mm[1] && i <= mm[3] ||
                       (i == mm[1] || i == mm[3]) && j >= mm[0] && j <= mm[2]) {
                        matrix[k][index] = 1;
                        matrix[index][k] = 2;
                    }
                }
            }
        }
        
        for(int i = 0; i<numframes; ++i) {
            for(int j = 0; j<numframes; ++j) {
                if(matrix[i][j] == 0) {
                    fix(i,j);
                }
            }
        }
        
        out.flush();
        out.close();
        f.close();
    }
    
    public static void fix(int i, int j) {
        for
    }
}
