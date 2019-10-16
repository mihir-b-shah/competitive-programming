package chapter4.s2_netflow;


import java.util.*;
import java.io.*;

/*
ID: mihirsh1
TASK: job2
LANG: JAVA
 */
public class job2 {

    public static final int INF = 1_000_000_000;
    public static Deque<Integer>[] A_jobs;
    public static Deque<Integer>[] B_jobs;
    public static int[] m1;
    public static int[] m2;

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("job2.in"));
        PrintWriter out = new PrintWriter("job2.out");

        int N = f.nextInt();
        int M1 = f.nextInt();
        int M2 = f.nextInt();
        A_jobs = new ArrayDeque[M1];
        B_jobs = new ArrayDeque[M2];

        m1 = new int[M1];
        m2 = new int[M2];

        for (int i = 0; i < M1; i++) {
            m1[i] = f.nextInt();
            A_jobs[i] = new ArrayDeque<>();
        }
         
        for (int i = 0; i < M2; i++) {
            m2[i] = f.nextInt();
            B_jobs[i] = new ArrayDeque<>();
        }
        
        Arrays.sort(m1);
        Arrays.sort(m2);
        
        int A = N;
        int I = 0;
        int T = 0;
        int B = 0;
        int out_A = -1;
        int out_B = -1;
        
        populateA(N);
        while(B < N) {   
            B += finish(T);            
            I = convAB(T);   
            A -= I;
            
            System.out.printf("T = %d, A = %d, I = %d, B= %d%nQ_A: %s, Q_B: %s%n", T, A, I, B,
                    Arrays.toString(A_jobs), Arrays.toString(B_jobs));
            
            if(A == 0 && out_A == -1)
                out_A = T;
            T++;       
            
            if(I > 0) populateB(T, I);      
            if(I != 0 && out_B == -1)
                out_B = T;
        }

        out.printf("%d %d%n", out_A, T-out_B);
        out.flush();
        out.close();
        f.close();
    }
    
    public static int convAB(int T) {
        int ret = 0;
        for(Deque<Integer> q: A_jobs) 
            while(!q.isEmpty() && q.peek() == T) {
                q.poll();
                ret++;
            }
        return ret;
    }
    
    public static int finish(int T) {
        int ret = 0;
        for(Deque<Integer> q: B_jobs) 
            while(!q.isEmpty() && q.peek() == T) {
                q.poll();
                ret++;
            }
        return ret;
    }
    
    public static void populateA(int N) {
        int[] aux = new int[m1.length];
        while (N > 0) {
            int min = INF;
            int index = -1;
            for (int i = 0; i < m1.length; i++) {
                if (min > m1[i] * (aux[i] + 1)) {
                    index = i;
                    min = m1[i] * (aux[i] + 1);
                }
            }
            aux[index]++;
            A_jobs[index].offer(m1[index] + (!A_jobs[index].isEmpty() ? A_jobs[index].peekLast() : 0));
            N--;
        }
    }
    
    public static void populateB(int T, int N) {
        int[] aux = new int[m2.length];
        while (N > 0) {
            int min = INF;
            int index = -1;
            for (int i = 0; i < m2.length; i++) {
                if (min > m2[i] * (aux[i] + 1)) {
                    index = i;
                    min = m2[i] * (aux[i] + 1);
                }
            }
            aux[index]++;
            B_jobs[index].offer(m2[index] + Math.max(T, (!B_jobs[index].isEmpty() ? B_jobs[index].peekLast() : 0)));
            N--;
        }
    }
}
