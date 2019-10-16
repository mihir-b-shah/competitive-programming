package chapter3.s1_minspantree;


/*
ID: mihirsh1
TASK: contact
LANG: JAVA
*/

import java.util.*;
import java.io.*;

public class contact {

    public static class IntPair implements Comparable {

        public String index;
        public int freq;

        public IntPair(String i, int f) {
            index = i;
            freq = f;
        }

        @Override
        public int compareTo(Object o) {
            int diff = ((IntPair) o).freq - freq;
            
            if(diff != 0) {
                return diff;
            } else {
                int len = index.length() - ((IntPair) o).index.length();
                if(len != 0)
                    return len;
                else {
                    return Integer.parseInt(index, 2) - Integer.parseInt(((IntPair) o).index,2);
                }
                
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("contact.in"));
        PrintWriter out = new PrintWriter("contact.out");

        int A = f.nextInt();
        int B = f.nextInt();
        int N = f.nextInt();

        int[] dp = new int[1 << (B + 1)];
        int[] track = new int[B - A + 1];

        Queue<IntPair> pq = new PriorityQueue<>();

        f.nextLine();
        StringBuilder str = new StringBuilder();
        while (f.hasNextLine()) {
            str.append(f.nextLine());
        }

        for (int i = A; i <= Math.min(B, str.length()); i++) {
            int sum = 0;

            for (int j = 0; j < i; j++) {
                sum <<= 1;
                sum += (str.charAt(j) - 48);
            }

            track[i - A] = sum;
            dp[sum + (1 << i)]++;
        }

        for (int i = A; i <= B; i++) {
            for (int j = 1; j < str.length() - i + 1; j++) {
                int num = (track[i - A] << 1) - ((str.charAt(j - 1) - 48) << i) + (str.charAt(j + i - 1) - 48);
                track[i - A] = num;
                dp[num + (1 << i)]++;
            }
        }

        for (int i = 1; i < dp.length; i++) {
            if(dp[i] != 0)
                pq.add(new IntPair(Integer.toBinaryString(i).substring(1), dp[i]));
        }

        int count = 0;
        StringBuilder output = new StringBuilder();
        
        while(!pq.isEmpty() && count < N) {
            
            int internal_count = 0;
            IntPair p = pq.poll();
            
            output.append(p.freq);
            output.append('\n');
            
            output.append(p.index);
            output.append(' ');
            internal_count++;
            
            count++;
            
            if(pq.isEmpty())
                output.deleteCharAt(output.length()-1);
            else {
                while(!pq.isEmpty() && pq.peek().freq == p.freq) {
                    p = pq.poll();
                    output.append(p.index);
                    output.append(' ');
                    internal_count++;
                    
                    if(internal_count % 6 == 0) {
                        output.deleteCharAt(output.length()-1);
                        output.append('\n');
                    }
                } 
            
                output.deleteCharAt(output.length()-1);
            }

            output.append('\n');
        }

        out.print(output);
        out.flush();
        out.close();
        f.close();
    }
}
