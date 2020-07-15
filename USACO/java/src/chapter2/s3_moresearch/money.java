package chapter2.s3_moresearch;


/*

ID: mihirsh1 
LANG: JAVA 
TASK: money

 */

import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class money {

    public static long[][] data;
    public static int V, N;
    public static int[] coins;

    public static void main(String[] args) throws Exception {

        Scanner f = new Scanner(new FileReader("money.in"));
        PrintWriter out = new PrintWriter(new FileWriter("money.out"));

        N = f.nextInt();
        V = f.nextInt();

        coins = new int[N];

        for (int i = 0; i < coins.length; i++) {
            coins[i] = f.nextInt();
        }
        Arrays.sort(coins);

        data = new long[N][V + 1];
        out.println(solve());
        //print2DArray(data, 4);

        out.flush();
        out.close();
        f.close();
    }

    public static long solve() {

        for (int i = 0; i < data.length; i++) {
            data[i][0] = 1;
        }

        for (int j = 0; j < data.length; j++) {
            for (int i = 1; i < data[0].length; i++) {

                // print2DArray(data, 4);
                if (i % coins[j] != 0 && j == 0) {
                    data[j][i] = 0;
                } else if (j == 0) {
                    data[j][i] = 1;
                } else if (coins[j] > i) {
                    data[j][i] = data[j - 1][i];
                } else {
                    data[j][i] = data[j][i - coins[j]] + data[j - 1][i];
                }

            }
        }

        return data[N - 1][V];
    }

    public static void print2DArray(long[][] data, int jf) {

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                System.out.printf("%" + jf + "d", data[i][j]);
            }
            System.out.println();
        }

        System.out.println();
    }

}
