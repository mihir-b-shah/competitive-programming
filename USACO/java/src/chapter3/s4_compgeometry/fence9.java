package chapter3.s4_compgeometry;


/*
ID: mihirsh1
TASK: fence9
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class fence9 {

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("fence9.in"));
        PrintWriter out = new PrintWriter("fence9.out");

        int[][] pts = new int[3][2];
        
        pts[1][0] = f.nextInt();
        pts[1][1] = f.nextInt();
        pts[2][0] = f.nextInt();

        // Use pick's theorem and shoelace, so A = I + B/2 - 1.
        // Find # of boundary points
        
        int B = 0;
        for(int i = 0; i<3; i++) {
            System.out.printf("%d %d %d %d%n", pts[i][0], pts[i][1], pts[(i+1)%3][0], pts[(i+1)%3][1]);
            B += numBPts(pts[i][0], pts[i][1], pts[(i+1)%3][0], pts[(i+1)%3][1]);
        }

        out.println((shoelace(pts)-(B+3))/2 + 1);
        out.flush();
        out.close();
        f.close();
    }

    public static int shoelace(int[][] pts) {
        int area = 0;
        for(int i = 0; i<3; i++) 
            area += pts[i][0]*pts[(i+1)%3][1];
        for(int i = 2; i>=0; i--) 
            area -= pts[(i+1)%3][0]*pts[i][1];
        return Math.abs(area);
    }
    
    public static int numBPts(int x1, int y1, int x2, int y2) {
        return gcd(Math.abs(x2-x1),Math.abs(y2-y1))-1;
    }
    
    public static int gcd(int a, int b) {
        if(a == 0)
            return b;
        else if(b == 0)
            return a;
        
        if(a>b) return gcd(a-b,b);
        else if(b>a) return gcd(a,b-a);
        else return a;
    }
}
