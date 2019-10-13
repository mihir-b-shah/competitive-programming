package oldprojects;


import java.util.*;
import java.io.*;

/*
ID: mihirsh1
TASK: fence6
LANG: JAVA
 */
public class fence6 {

    public static int INF = 1_000_000_000;
           
    public static class VPair {
        public int id;
        public int wt;
        
        public VPair(int id, int wt) {
            this.id = id; this.wt = wt;
        }
        
        @Override
        public String toString() {
            return String.format("<%d, %d>", id+1, wt);
        }
    }
    
    public static class Vertex {
        public int[] edges;
        private int ctr;
        
        public Vertex(int N) {
            edges = new int[N];
        }
        
        public void add(int i) {
            edges[ctr++] = i;
        }
        
        @Override
        public int hashCode() {
            long hash = 1;
            for(int i = 0; i<ctr; i++)
                hash *= edges[i]+1;
            return (int) (hash%INF);
        }
        
        @Override
        public boolean equals(Object o) {
            Vertex v = (Vertex) o;
            if(o instanceof Vertex) return v.hashCode() == hashCode();
            else return false;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('{');
            for(int i: edges) {
                sb.append(i+1); sb.append(", ");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.deleteCharAt(sb.length()-1);
            sb.append('}');
            return sb.toString();
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("fence6.in"));
        PrintWriter out = new PrintWriter("fence6.out");

        int N = f.nextInt();
        int[][] mat = new int[N][N];
        HashMap<Vertex,Integer> vertices = new HashMap<>();
        int v_num = 0;

        for(int i = 0; i<N; i++)
            for(int j = 0; j<N; j++)
                mat[i][j] = INF;
        
        for(int i = 0; i<N; i++) {
            int id = f.nextInt()-1;
            int len = f.nextInt();
            int J1 = f.nextInt();
            int J2 = f.nextInt();
            
            Vertex v1 = new Vertex(J1+1);
            Vertex v2 = new Vertex(J2+1);
            v1.add(id); v2.add(id);
            
            for(int j = 0; j<J1; j++) 
                v1.add(f.nextInt()-1);
            
            for(int j = 0; j<J2; j++) 
                v2.add(f.nextInt()-1);

            if(!vertices.containsKey(v1)) 
                vertices.put(v1,v_num++);

            if(!vertices.containsKey(v2)) 
                vertices.put(v2,v_num++);
            
            mat[vertices.get(v1)][vertices.get(v2)] = len;
            mat[vertices.get(v2)][vertices.get(v1)] = len;
        }

        int min = INF;
        int[][] mat_old = new int[N][N];
        
        for(int i = 0; i<N; i++)
            System.arraycopy(mat[i], 0, mat_old[i], 0, N);
        
        int V = vertices.size();
        for(int k = 0; k<V; k++)
            for(int i = 0; i<V; i++)
                for(int j = 0; j<V; j++) {
                    if(i<k && j<k && i!=j) min = Math.min(min, (mat_old[k][i] + mat_old[j][k] + mat[i][j] > 0) ? mat_old[k][i] + mat_old[j][k] + mat[i][j] : INF);
                    mat[i][j] = Math.min(mat[i][j], mat[i][k] + mat[k][j]);
                }
        
        for(int[] i: mat)
            System.out.println(Arrays.toString(i));
        
        out.println(min);
        out.flush();
        out.close();
        f.close();
    }
}    
    