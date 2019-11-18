package chapter4.s4_other;


import java.util.*;
import java.io.*;

/*
ID: mihirsh1
TASK: milk6
LANG: JAVA
*/

public class milk6 {
    
    public static ArrayList<ArrayList<VP>> vect;
    public static int src;
    public static int sink;
    public static boolean[] vis;
    public static boolean eject = false;
    public static int INF = 1_000_000_000;
    public static int curr_flow = 0;
    
    public static class VP implements Comparable<VP> {
        public int id;
        public int flow;
        public int wt;
        public VP twin;
        
        public VP(int id, int wt, VP ref) {
            this.id = id; this.wt = wt; twin=ref;
        }
        
        @Override
        public int compareTo(VP v) {
            return v.wt-wt;
        }
        
        @Override
        public String toString() {
            return String.format("<%d, %d/%d>", id+1, flow, wt);
        }
    }
    
    public static class Pair {
        public int src;
        public int dest;

        public Pair(int s, int d) {
            src = s;
            dest = d;
        }
        
        @Override
        public boolean equals(Object oth) {
            if(oth instanceof Pair) {
                return oth.hashCode() == hashCode();
            } return false;
        }
        
        @Override
        public int hashCode() {
            return src*dest;
        }
    }
    
    public static class FB {
        public int forward;
        public int backward;
        public int src;
        
        public FB(int src) {  
            this.src = src;
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("milk6.in"));
        PrintWriter out = new PrintWriter("milk6.out");

        HashMap<Pair, FB> input = new HashMap<>();
        int N = f.nextInt();
        int M = f.nextInt();
        
        vect = new ArrayList<>();
        for(int i = 0; i<M; i++) 
            vect.add(new ArrayList<>());
        
        for(int i = 0; i<N; i++) {
            int s = f.nextInt()-1;
            int e = f.nextInt()-1;
            int wt = f.nextInt();
                        
            if(s == e) {
                continue;
            }
            
            Pair p;
            if(!input.containsKey(p = new Pair(s,e))) {
                FB fb = new FB(s);
                fb.forward = wt;
                input.put(p, fb);
            } else {
                FB fb = input.get(p);
                if(fb.src == s) {
                    fb.forward += wt;
                } else {
                    fb.backward += wt;
                }
            }
        }
        
        Set<Map.Entry<Pair,FB>> set = input.entrySet();
        for(Map.Entry<Pair,FB> entry: set) {
            Pair p = entry.getKey();
            FB fb = entry.getValue();
            int s = p.src;
            int e = p.dest;
            int forw = fb.forward;
            int back = fb.backward;
            
            VP v1 = new VP(e, forw, null);
            VP v2 = new VP(s, back, v1);
            v1.twin = v2;
            
            vect.get(s).add(v1);
            vect.get(e).add(v2);
        }     
        
        /* edmonds karp algorithm
        1. find path, determine if augmented.
        2. backtrack and change flows-p
        */
        
        src = 0;
        sink = M-1;       
        vis = new boolean[vect.size()];
        int flow = 0;
        
        do {
            eject = false;
            curr_flow = 0;
            Arrays.fill(vis,false);
            recur(src, INF);
            flow += curr_flow;
            //printVect();
            //System.out.println();
        } while(eject);
        
        out.println(flow);
        out.flush();
        out.close();
        f.close();
    }
    
    public static void recur(int s, int f) {
        if(eject) return;
        vis[s] = true;
        if(s == sink) {
            eject = true;
            curr_flow = f;
            //System.out.println(s+1);
            return;
        }
        
        ArrayList<VP> next = vect.get(s);
        for(VP v: next) 
            if(!eject && !vis[v.id] && v.flow<v.wt) {
                recur(v.id, Math.min(f, v.wt-v.flow));
                v.flow += curr_flow;
                v.twin.flow = -v.flow;
            }
        
        //if(eject) System.out.println(s+1);
    }
    
    public static void printVect() {
        for(int i = 0; i<vect.size(); i++) {
            System.out.printf("%d: %s%n", i+1, vect.get(i));
        }
    }
}
