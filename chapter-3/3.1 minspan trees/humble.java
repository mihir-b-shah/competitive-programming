package oldprojects;


/*
ID: mihirsh1
TASK: humble
LANG: JAVA
*/

import java.util.*;
import java.io.*;

public class humble {
    
    public static void main(String[] args) throws Exception {
    
        Scanner f = new Scanner(new File("humble.in"));
        PrintWriter out = new PrintWriter("humble.out");
        
        int K = f.nextInt();
        int N = f.nextInt();
        
        int[] primes = new int[K];
        
        for(int i = 0; i<K; i++)
            primes[i] = f.nextInt();

        int[] numbers = new int[N+1];
        int[] prime_index = new int[K];
        numbers[0] = 1;
        
        for(int i = 1; i<=N; i++) {
            int min = Integer.MAX_VALUE;
            for(int j = 0; j<K; j++) {
                int p = primes[j];
                int start = prime_index[j];
                while(p*numbers[start] <= numbers[i-1])
                    start++;
                min = Math.min(min, p*numbers[start]);
                prime_index[j] = start;
            }
            numbers[i] = min;
        }
        
        out.println(numbers[N]);
        out.flush();
        out.close();
        f.close();
    }
    
}
