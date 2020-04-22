package chapter4.s4_other;

import java.util.*;
import java.io.*;

/*
ID: mihirsh1
TASK: shuttle
LANG: JAVA
*/
public class shuttle2 {
    static String display(long state) {
        StringBuilder sb = new StringBuilder();
        while(state != 0) {
            switch((int) state & 3) {
                case 1:
                    sb.append('W');
                    break;
                case 2:
                    sb.append('B');
                    break;
                case 3:
                    sb.append(' ');
                    break;
            }
            state >>>= 2;
        }
        sb.reverse();
        return sb.toString();
    }
    
    static long exchgBits(long bits, int p1, int p2) {
        long x = bits >> p1 & 3 ^ bits >> p2 & 3; 
        return bits ^ (x << p1 | x << p2); 
    }

    static class State {
        long state;
        int space;
        int lvl;
        State prior;
        
        State(State pr, long st, int sp, int l) {
            prior = pr;
            state = st;
            space = sp;
            lvl = l;
        }
        
        @Override
        public String toString() {
            return String.format("State: %s, Space=%d, Level=%d", display(state), space/2, lvl);
        }
    }
    
    static String getPath(int N, State entry) {
        State aux = entry;
        StringBuilder sb = new StringBuilder();
        int ctr = 0;
        while(aux != null) {
            sb.append(N-aux.space/2);
            sb.append(' ');
            aux = aux.prior;
            ++ctr;
            if(ctr%20 == 0) {
                sb.deleteCharAt(sb.length()-1);
                sb.append('\n');
            }
        }
        if(ctr != 0) {
            sb.deleteCharAt(sb.length()-1);
        }
        System.out.println(sb);
        return sb.toString();
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("shuttle.in"));
        PrintWriter out = new PrintWriter("shuttle.out");
        
        int N = f.nextInt();
        f.close();
        
        // build the initial mask
        long mask = 0;
        int N2 = 2*N;
        
        final long ON = (1L<<N2)-1;

        for(int i = 0; i<N; ++i) {
            mask |= 2 << 2*i;
        }
        
        mask = ((~mask & ON) << N2+2) | mask;
        long tgt = ~mask & (1L << 2*N2+2) - 1;
        mask |= 3 << N2;
        tgt |= 3 << N2;
        
        HashSet<Long> past = new HashSet<>();  

        Queue<State> queue = new ArrayDeque<>();
        queue.offer(new State(null, mask, N2, 0));
        past.add(mask);
        
        int[] dx = {-4,-2,2,4};
        int solutionLevel = -1;
        String opt = null;
        
        while(!queue.isEmpty()) {
            State entry = queue.poll();
            long state = entry.state;
            int space = entry.space;
            if(tgt == state && solutionLevel == -1) {
                solutionLevel = entry.lvl;
                opt = getPath(N2+1, entry.prior);
                continue;
            } else if(tgt == state && entry.lvl == solutionLevel) {
                String comp = getPath(N2+1, entry.prior);
                if(comp.compareTo(opt) < 0) {
                    opt = comp;
                }
                continue;
            }
            
            long newState;
            for(int i = 0; i<4; ++i) {
                newState = exchgBits(state, space, space+dx[i]);
                if(space+dx[i] > -1 && space+dx[i] < 2+2*N2 && !past.contains(newState)) {
                    past.add(newState);
                    queue.offer(new State(entry, newState, space+dx[i], entry.lvl+1));
                }
            }
        }

        out.println(opt);
        out.close();
    }
}
