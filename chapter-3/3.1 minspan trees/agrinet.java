package oldprojects;


/*
ID: mihirsh1
TASK: agrinet
LANG: JAVA
*/

import java.util.*;
import java.io.*;

public class agrinet {
    
    public static final int INFINITY = 1000000000;
    
    public static class Vertex implements Comparable {
        public int v;
        public int w;
        
        public Vertex(int v, int w) {
            this.v = v;
            this.w = w;
        }
        
        public int compareTo(Object o) {
            Vertex ve = (Vertex) o;
            return w - ve.w;
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("agrinet.in"));
        PrintWriter out = new PrintWriter("agrinet.out");

        int M = f.nextInt();
        int[][] mat = new int[M][M];
        
        for(int i = 0; i<M; i++) {
            for(int j = 0; j<M; j++) {
                mat[i][j] = f.nextInt();
            }
        }

        // Prim's algorithm
        
        boolean[] intree = new boolean[M];
        
        int cost = 0;
        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(new Vertex(0, 0));
        
        branch(intree, pq, mat, M, 0);
        
        while(!pq.isEmpty()) {
            Vertex v = pq.poll();
            if(!intree[v.v]) {
                cost += v.w;
                branch(intree, pq, mat, M, v.v);
            }
        }
        
        out.println(cost);
        out.flush();
        out.close();
        f.close();
    }
    
    public static void branch(boolean[] b, PriorityQueue<Vertex> pq, int[][] mat, int M, int curr) {
        b[curr] = true;
        for(int i = 0; i<M; i++) {
            if(i != curr && mat[curr][i] > 0 && mat[curr][i] < INFINITY) {
                pq.offer(new Vertex(i, mat[curr][i]));
            }
        }
    }
}
