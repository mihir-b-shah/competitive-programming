package oldprojects;

import java.io.*;
import java.util.*;

public class msquare {

    public static class Item {
        public int val;
        public ArrayList<String> states;
        public String sb;
        
        public Item(int val, String newchar, Item it) {
            this.val = val;
            states = new ArrayList<>();
            if(it != null) states.addAll(it.states);
            sb = (it != null ? it.sb : "")+ newchar;
            states.add(String.format("%08o", val));
        } 
        
        @Override
        public String toString() {
            return String.format("%08o", val);
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("msquare.in"));
        PrintWriter out = new PrintWriter("msquare.out");

        int tgt = 0;
        int orig = 0b000001010011111110101100;
        
        for(int i = 0; i<8; i++) {
            tgt <<= 3; 
            tgt += (f.nextByte()-1);
        }
        
        // BFS with pruning
        // There are only 40320 possible states.
        
        Queue<Item> qe = new ArrayDeque<>();
        Item dummy = new Item(0, "", null);
        
        HashSet<Integer> vis = new HashSet<>();
        qe.offer(new Item(orig, "", dummy));
        vis.add(orig);
        int ctr = 0;
        
        while(!qe.isEmpty()) {
            //System.out.println(qe.size());
            Item res = qe.poll();
            if(res.val == tgt) {
                System.err.println(res.states);
                out.println(res.sb);
            }
            
            int a = tA(res.val);
            int b = tB(res.val);
            int c = tC(res.val);
            
            if(!vis.contains(a)) {
                qe.offer(new Item(a, "A", res));
                vis.add(a);
            }
            
            if(!vis.contains(b)) {
                qe.offer(new Item(b, "B", res));
                vis.add(b);
            }
            
            if(!vis.contains(c)) {
                qe.offer(new Item(c, "C", res));
                vis.add(c);
            }
            
            ctr++;
        }
        
        out.flush();
        out.close();
        f.close();
    }

    public static int tA(int a) {
        return ((a & 4095) << 12) + (a >> 12);
    }
    
    public static int tB(int b) {
        return ((b & 511 << 3) >> 3) + ((b & 7) << 9) + ((b & 511 << 15) >> 3) + ((b & 7 << 12) << 9);
    }
    
    public static int tC(int c) {
        return (c & 7) + (c & 63 << 9) + (c & 7 << 21) + 
               ((c & 7 << 3) << 3) + ((c & 7 << 6) << 12) + ((c & 7 << 18) >> 3) + ((c & 7 << 15) >> 12);
    }
}