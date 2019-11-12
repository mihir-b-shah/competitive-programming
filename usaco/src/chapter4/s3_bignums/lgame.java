package chapter4.s3_bignums;

/*
ID: mihirsh1
TASK: lgame
LANG: JAVA
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class lgame {
    
    private static char[] str;
    private static char[] iter;
    private static HashSet<String> set;
    private static HashSet<String> longs;
    private static PriorityQueue<String> fin;
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("lgame.in"));
        PrintWriter out = new PrintWriter("lgame.out");
        
        str = br.readLine().toCharArray();
        Arrays.sort(str);
        iter = new char[str.length];
        
        br.close();
        
        set = new HashSet<>();
        longs = new HashSet<>();
        fin = new PriorityQueue(4, new Comparator<String>(){
            @Override
            public int compare(String s1, String s2) {
                int ret = score(s2)-score(s1);
                if(ret == 0) {
                    return s1.compareTo(s2);
                } else {
                    return ret;
                }
            }
        });
        
        br = new BufferedReader(new FileReader("lgame.dict"));
        String ref;
        
        while((ref = br.readLine()).charAt(0) != '.') {
            set.add(ref);
        }
        
        br.close();   
        recur(0,0);
        System.out.println(fin);
        
        String pr = fin.poll();
        int score = score(pr);
        out.println(score);
        out.println(pr);
        
        while(!fin.isEmpty() && score(fin.peek()) == score) {
            out.println(fin.poll());
        }
        
        out.close();
    }

    /*
     * Uses str as the base string
     * Uses iter as the temp string
     */
    public static void recur(int b, int l) {
        if(l == str.length) {
            String ref = new String(iter);
            if(set.contains(ref)) {
                if(!longs.contains(ref)) {
                    fin.offer(ref);
                    longs.add(ref);
                }
            }
            for(int i = 1; i<str.length; ++i) {
                String ref2;
                for(int j = i+1; j<=str.length; ++j) {
                    ref2 = ref.substring(0,i)+" "+ref.substring(i,j);
                    if(set.contains(ref.substring(0,i)) && set.contains(ref.substring(i,j))) {
                        if(!longs.contains(ref2) && !longs.contains(ref.substring(i,j)
                                + " " + ref.substring(0,i))) {
                            fin.offer(ref2);
                            longs.add(ref2);
                        }
                    }
                }
                if(set.contains(ref2 = ref.substring(0,i))) {
                    if(!longs.contains(ref2)) {
                        fin.offer(ref2);
                        longs.add(ref2);
                    }
                }
            }
        }
        for(int i = 0; i<str.length; ++i) {
            if((b & 1 << i) == 0) {
                iter[l] = str[i];
                recur(b + (1 << i), l+1);
            }
        }
    }
    
    public static int score(String s) {
        int sc = 0;
        for(int i = 0; i<s.length(); ++i) {
            switch(s.charAt(i)) {
                case 'a': sc += 2; break; 
                case 'b': sc += 5; break; 
                case 'c': sc += 4; break; 
                case 'd': sc += 4; break; 
                case 'e': sc += 1; break; 
                case 'f': sc += 6; break; 
                case 'g': sc += 5; break; 
                case 'h': sc += 5; break; 
                case 'i': sc += 1; break; 
                case 'j': sc += 7; break; 
                case 'k': sc += 6; break; 
                case 'l': sc += 3; break; 
                case 'm': sc += 5; break; 
                case 'n': sc += 2; break; 
                case 'o': sc += 3; break; 
                case 'p': sc += 5; break; 
                case 'q': sc += 7; break; 
                case 'r': sc += 2; break; 
                case 's': sc += 1; break; 
                case 't': sc += 2; break; 
                case 'u': sc += 4; break; 
                case 'v': sc += 6; break; 
                case 'w': sc += 6; break; 
                case 'x': sc += 7; break; 
                case 'y': sc += 5; break; 
                case 'z': sc += 7; break; 
            }
        }
        return sc;
    }
}
