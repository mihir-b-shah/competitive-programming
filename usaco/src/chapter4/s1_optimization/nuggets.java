package chapter4.s1_optimization;


import java.util.*;
import java.io.*;

/*
ID: mihirsh1
TASK: nuggets
LANG: JAVA
*/

public class nuggets {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("nuggets.in"));
        PrintWriter out = new PrintWriter("nuggets.out");

        int N = f.nextInt();
        int min_base = Integer.MAX_VALUE;
        int[] base = new int[N];
        ArrayList<Integer> nums = new ArrayList<>();
        int[] index = new int[N];
        boolean work = false;
        boolean all = true;
        
        for(int i = 0; i<N; i++) {
            base[i] = f.nextInt();
            min_base = Math.min(min_base, base[i]);
            if(base[i] == 1) {
                all = false;
                break;
            }
        }
        
        int val = base[0];
        for(int i = 1; i<N; i++) {
            val = gcd(val, base[i]);
            if(val == 1) {
                work = true;
                break;
            } 
        }
        
        int ctr = 0;
        int i = 1;
        if(work && all) {
            nums.add(0);
            while(true) {                
                int min = Integer.MAX_VALUE;
                for(int j = 0; j<N; j++) {
                    int start = index[j];
                    while(nums.get(start) + base[j] <= nums.get(i-1))
                        start++;
                    index[j] = start;
                    min = Math.min(min, nums.get(start) + base[j]);
                }
                nums.add(min);
                int diff = nums.get(i) - nums.get(i-1);
                if(diff == 1) ctr++; else ctr = 0;
                if(ctr == min_base) {
                    out.println(nums.get(i-min_base)-1);
                    break;
                }
                i++;
            }
        } else out.println(0);

        out.flush();
        out.close();
        f.close();
    }
    
    public static int gcd(int a, int b) {
        if(a == 0)
            return b;
        if(b == 0)
            return a;
        if(a > b)
            return gcd(a%b,b);
        if(b > a)
            return gcd(a,b%a);
        return 1;
    }
}
