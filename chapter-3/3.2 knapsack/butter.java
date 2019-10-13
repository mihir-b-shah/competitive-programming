package oldprojects;

/*
 ID: mihirsh1
 TASK: butter
 LANG: JAVA
 */

import java.io.*;
import java.util.*;

public class butter {

    public static final int INFINITY = 1000000000;

    public static class VP implements Comparable<VP> {

        public int num;
        public int wt;

        public VP(int num, int wt) {
            this.num = num;
            this.wt = wt;
        }

        @Override
        public int compareTo(VP o) {
            return wt - o.wt;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("butter.in"));
        PrintWriter out = new PrintWriter("butter.out");

        int N = f.nextInt();
        int P = f.nextInt();
        int C = f.nextInt();

        int[] cow_wt = new int[P];

        ArrayList<ArrayList<VP>> vect = new ArrayList<>();

        for (int i = 0; i < P; i++) {
            vect.add(new ArrayList<VP>());
        }

        for (int i = 0; i < N; i++) {
            cow_wt[f.nextInt() - 1]++;
        }

        for (int i = 0; i < C; i++) {
            int src = f.nextInt() - 1;
            int dest = f.nextInt() - 1;
            int wt = f.nextInt();

            vect.get(src).add(new VP(dest, wt));
            vect.get(dest).add(new VP(src, wt));
        }

        int[][] wts = new int[P][P];

        for (int i = 0; i < P; i++) 
            wts[i] = dijkstra(vect, i);
        
        int opt_val = INFINITY;

        for (int i = 0; i < P; i++) {
            int loc_val = 0;

            for (int j = 0; j < P; j++) {
                loc_val += wts[i][j] * cow_wt[j];
            }

            opt_val = Math.min(opt_val, loc_val);
        }

        out.println(opt_val);
        out.flush();
        out.close();
        f.close();
    }

    public static int[] dijkstra(ArrayList<ArrayList<VP>> vect, int src) {
        boolean[] visited = new boolean[vect.size()];
        int[] wt = new int[vect.size()];
        Arrays.fill(wt, INFINITY);
        wt[src] = 0;
        
        PriorityQueue<VP> pq = new PriorityQueue<>(vect.size());
        pq.offer(new VP(src, 0));

        while (!pq.isEmpty()) {
            VP adj = pq.poll();
            visited[adj.num] = true;
            ArrayList<VP> next = vect.get(adj.num);

            for (VP v : next) {
                if (!visited[v.num]) {
                    if (wt[v.num] > wt[adj.num] + v.wt) {
                        wt[v.num] = wt[adj.num] + v.wt;
                        pq.offer(new VP(v.num, wt[v.num]));
                    }
                }
            }
        }

        return wt;
    }
}
