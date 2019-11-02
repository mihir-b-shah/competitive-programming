package oldprojects;


/*

ID: mihirsh1
LANG: JAVA
PROB: cowtour

*/

import java.io.*;
import java.util.*;

public class cowtour {
    
    public static final int INFINITY = 1000000000;
    
    public static class Vertex {
        public int x;
        public int y;
        
        public Vertex(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public String toString() {
            return String.format("(%d, %d)", x, y);
        }
    }
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("cowtour.in"));
        PrintWriter out = new PrintWriter("cowtour.out");
        
        int N = f.nextInt();
        double[][] mat = new double[N][N];
        Vertex[] pts = new Vertex[N];
        
        for(int i = 0; i<N; i++) {
            pts[i] = new Vertex(f.nextInt(), f.nextInt());
        }
        
        f.nextLine();
        
        for(int i = 0; i<N; i++) {
            String line = f.nextLine();
            
            for(int k = 0; k<N; k++) {
                mat[i][k] = i == k ? 0 : line.charAt(k) == '1' ? compute(pts, i,k) : INFINITY;
            }
        }
        
        int[] id = new int[N];
        Queue<Integer> queue = new LinkedList<>();
        int val = 0;
        int count = 1;
        id[0] = count;
        
        while(val != -1) {
            queue.add(val);
            id[val] = count;
            
            while(!queue.isEmpty()) {
                
                int index = queue.poll();
                
                for(int i = 0; i<N; i++) {
                    if(id[i] == 0 && Double.compare(mat[index][i], 0) > 0 && Double.compare(mat[index][i], INFINITY) < 0) {
                        queue.add(i);
                        id[i] = count;
                    }
                }
            }
            
            val = allVisited(id);
            count++;
        }
        
        double[] diam = new double[count-1];
        
        for(int k = 0; k<N; k++) 
            for(int i = 0; i<N; i++) 
                for(int j = 0; j<N; j++)
                    mat[i][j] = Math.min(mat[i][j], mat[i][k]+mat[k][j]);
        
        double min = INFINITY;
        double[] dist = new double[N];
        
        // Precompute farthest points
        
        for(int i = 0; i<N; i++) {
            
            double max = 0;
            
            for(int j = 0; j<N; j++) {
                if(Double.compare(mat[i][j], INFINITY) != 0)
                    max = Math.max(mat[i][j], max);
            }
            
            dist[i] = max;
        }
        
        for(int i = 0; i<N; i++) {
            diam[id[i]-1] = Math.max(diam[id[i]-1], dist[i]);
        }
        
        for(int i = 0; i<N-1; i++) {
            for(int j = i+1; j<N; j++) {
                if(Double.compare(mat[i][j],INFINITY) == 0) {
                    min = Math.min(min, Math.max(compute(pts, i, j)+dist[i]+dist[j], Math.max(diam[id[i]-1],diam[id[j]-1])));
                }  
            }
        }
     
        out.printf("%.6f\n", min);
        out.flush();
        out.close();
        f.close();
    }   
    
    public static double compute(Vertex[] pts, int i, int k) {
            return Math.sqrt(Math.pow(pts[i].x-pts[k].x,2) + Math.pow(pts[i].y-pts[k].y,2));
    } 
    
    public static int allVisited(int[] b) {
        for(int i = 0; i<b.length; i++) {
            if(b[i] == 0)
                return i;
        }
        
        return -1;
    }
    
}
