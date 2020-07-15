package chapter3.s2_knapsack;


/*
ID: mihirsh1
TASK: fact4
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class fact4 {

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("fact4.in"));
        PrintWriter out = new PrintWriter("fact4.out");

        int N = f.nextInt();
        
        if(N == 0)
            out.println(1);
        else if(N == 1)
            out.println(1);
        else if(N == 2) 
            out.println(2);
        else if(N == 3)
            out.println(6);
        else if(N == 4)
            out.println(4);
        else {
        
            int[][] prime_table = new int[N+1][];
            int[] reverse = new int[N];
            int index = 0;
            int[] sum = new int[N];
            HashMap<Integer, Integer> prime_hash = new HashMap<>();

            for (int i = 2; i <= N; i++) {
                boolean prime = true;

                for (int j = 2; j <= Math.sqrt(i); j++) {
                    if (i % j == 0) {
                        int[] fact = prime_table[i/j];
                        int max_prime = reverse[fact.length-1];
                        int[] new_factors;
                        if(j > max_prime) {
                            new_factors = new int[fact.length+1];
                            System.arraycopy(fact, 0, new_factors, 0, fact.length);
                            new_factors[fact.length]++;
                        } else {
                            new_factors = new int[fact.length];
                            System.arraycopy(fact, 0, new_factors, 0, fact.length);
                            new_factors[prime_hash.get(j)]++;
                        }

                        prime_table[i] = new_factors;
                        prime = false;
                        break; 
                    }
                }

                if (prime) {
                    prime_hash.put(i, index++);
                    reverse[index - 1] = i;
                    prime_table[i] = new int[index];
                    prime_table[i][index-1]++;
                }

                for(int j = 0; j<prime_table[i].length; j++) {
                    sum[j] += prime_table[i][j];
                }

            }

            // Adjust for leading zeros

            int adj = Math.min(sum[prime_hash.get(2)], sum[prime_hash.get(5)]);
            sum[prime_hash.get(2)] -= adj;
            sum[prime_hash.get(5)] -= adj;

            int i = sum.length-1;
            for(; i>=0; i--) {
                if(sum[i] != 0)
                    break;
            }

            int[][] powers = new int[10][];
            fill(powers);

            int prod = 1;
            for(int j = 0; j<=i; j++) {
                if(sum[j] == 0)
                    continue;
                prod *= powers[reverse[j] % 10][(sum[j]-1)%powers[reverse[j] % 10].length];
                prod %= 10;
            }

            out.println(prod);
        }
        
        out.flush();
        out.close();
        f.close();
    }

    public static void fill(int[][] powers) {
        powers[1] = new int[1];
        powers[1][0] = 1;
        powers[2] = new int[4];
        powers[2][0] = 2; powers[2][1] = 4; powers[2][2] = 8; powers[2][3] = 6;
        powers[3] = new int[4];
        powers[3][0] = 3; powers[3][1] = 9; powers[3][2] = 7; powers[3][3] = 1;
        powers[5] = new int[1];
        powers[5][0] = 5;
        powers[7] = new int[4];
        powers[7][0] = 7; powers[7][1] = 9; powers[7][2] = 3; powers[7][3] = 1;
        powers[9] = new int[2];
        powers[9][0] = 9; powers[9][1] = 1;
    }
    
}
