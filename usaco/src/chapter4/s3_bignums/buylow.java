package chapter4.s3_bignums;


import java.util.*;
import java.io.*;
import java.math.BigInteger;

/*
ID: mihirsh1
TASK: buylow
LANG: JAVA
 */
public class buylow {

    public static int[] p;
    public static int[] L;
    public static BigInteger[] c;

    public static void main(String[] args) throws Exception {
        long T0 = System.currentTimeMillis();
        Scanner f = new Scanner(new File("buylow.in"));
        PrintWriter out = new PrintWriter("buylow.out");

        int N = f.nextInt();
        p = new int[N];
        L = new int[N];
        c = new BigInteger[N];

        for (int i = 0; i < c.length; i++) {
            c[i] = new BigInteger("1");
        }

        HashSet<Integer> v;

        int max_L = 0;
        BigInteger as_C = new BigInteger("0");

        for (int i = 0; i < N; i++) {
            p[i] = f.nextInt();
        }

        for (int i = N - 1; i >= 0; i--) {
            int max = 0;
            BigInteger ct = new BigInteger("0");
            for (int j = i + 1; j < N; j++) {
                if (p[j] < p[i]) {
                    if (max < L[j]) {
                        max = L[j];
                    }
                }
            }

            v = new HashSet<>(N);

            for (int j = i + 1; j < N; j++) {
                if (p[j] < p[i]) {
                    if (!v.contains(p[j]) && L[j] == max) {
                        ct = ct.add(c[j]);
                        v.add(p[j]);
                    }
                }
            }

            L[i] = max + 1;
            c[i] = c[i].max(ct);

            if (max_L < L[i]) {
                max_L = L[i];
            }
        }

        v = new HashSet<>(N);
        
        for (int i = 0; i < N; i++) {
            if (!v.contains(p[i]) && max_L == L[i]) {
                as_C = as_C.add(c[i]);
                v.add(p[i]);
            }
        }

        out.printf("%d %s%n", max_L, as_C.toString());
        out.flush();
        out.close();
        f.close();
        System.out.println(System.currentTimeMillis()-T0);
    }
}
