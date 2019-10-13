package oldprojects;


/*
ID: mihirsh1
TASK: fence
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class fence {

    public static class Edge implements Comparable<Edge> {
        public int dest;
        public int num;
        
        public Edge(int d) {
            dest = d;
            num = 1;
        }
        
        @Override
        public String toString() {
            return String.format("%d %d", dest, num);
        }
        
        @Override
        public int compareTo(Edge o) {
            return dest - o.dest;
        }
    }
    
    public static ArrayList<ArrayList<Edge>> vect;
    public static ArrayDeque<Integer> tour;
    public static int[][] index_map;
    public static int[] degree_list;
    public static int[] orig_degree;

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("fence.in"));
        PrintWriter out = new PrintWriter("fence.out");

        int F = f.nextInt();
        vect = new ArrayList<>();

        ArrayDeque<Integer> qe = new ArrayDeque<>();        
        int max = 0;
        
        for(int i = 0; i<F; i++) {
            int src = f.nextInt();
            int dest = f.nextInt();
            
            if(max < src)
                max = src;
            
            if(max < dest)
                max = dest;
            
            qe.offer(src);
            qe.offer(dest);
        }

        for(int i = 0; i<max; i++)
            vect.add(new ArrayList<Edge>());
        
        index_map = new int[max][max];
        orig_degree = new int[max];
        
        for (int i = 0; i<F; i++) {
            int src = qe.poll()-1;
            int dest = qe.poll()-1;

            if(index_map[dest][src] == 0) {
                vect.get(dest).add(new Edge(src));
                index_map[dest][src] = vect.get(dest).size();
            } else
                vect.get(dest).get(index_map[dest][src]-1).num++;
            
            if(index_map[src][dest] == 0) {
                vect.get(src).add(new Edge(dest));
                index_map[src][dest] = vect.get(src).size();
            } else
                vect.get(src).get(index_map[src][dest]-1).num++;
        }

        for(int i = 0; i<max; i++) 
            for(int j = 0; j<vect.get(i).size(); j++)
                orig_degree[i] += vect.get(i).get(j).num;
        
        degree_list = new int[max];
        tour = new ArrayDeque<>();

        for(int i = 0; i<max; i++) {
            Collections.sort(vect.get(i));
            count_sort(max, index_map[i]);
        }

        recur(findOddDegree());
        for(int e: tour)
            out.println(e+1);

        out.flush();
        out.close();
        f.close();
    }

    public static void recur(int st) {
        ArrayList<Edge> list = vect.get(st);

        for(int i = 0; i<list.size(); i++) 
            if(list.get(i).num > 0) {
                int e = list.get(i).dest;
                list.get(i).num--;
                degree_list[e]++;
                degree_list[st]++;
                vect.get(e).get(index_map[e][st]-1).num--;
                recur(e);
            }
        
        if (degree_list[st] == orig_degree[st]) 
            tour.push(st);
    }
    
    public static int findOddDegree() {
        
        for(int i = 0; i<vect.size(); i++)
            if((calcSize(vect.get(i)) & 1) == 1) 
                return i;
            
        return 0;
    }
    
    public static int calcSize(ArrayList<Edge> vect) {
        int sum = 0;
        for(Edge e: vect)
            sum += e.num;
        return sum;
    }
    
    public static void count_sort(int max, int[] list) {
        boolean[] aux = new boolean[max];

        for(int e: list)
            if(e != 0)
                aux[e] = true;

        int aux_ptr = 0;
        int list_ptr = 0;
        
        while(aux_ptr < max && list_ptr < list.length) {
            while(list_ptr < list.length && list[list_ptr] == 0)
                list_ptr++;
            
            while(aux_ptr < max && !aux[aux_ptr])
                aux_ptr++;
            
            if(list_ptr < list.length)
                list[list_ptr] = aux_ptr;
            
            list_ptr++;
            aux_ptr++;
        }
    }
}
