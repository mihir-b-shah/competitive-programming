
package chapter1.s4_greedy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*

 ID: mihirsh1
 LANG: JAVA
 TASK: skidesign

 */
public class ski2 {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\nnOUTPUT.txt"

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\skiOUT.txt")));
        BufferedReader f = new BufferedReader(new FileReader("C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\skiIN2.txt"));

        int numHills = Integer.parseInt(f.readLine());
        int[] hills = new int[numHills];

        for (int i = 0; i < numHills; i++) {
            hills[i] = Integer.parseInt(f.readLine());
        }

        sort(hills);

        int cost = 0;
        int totCost = Integer.MAX_VALUE;

        for (int k = 0; k <= 100; k++) {
            cost = 0;

            int[] hill2 = hills.clone();

            for (int i = 0; i <= hills.length / 2; i++) {
                try {
                    if (hills[hills.length - 1 - i] - hills[i] > 17) {

                        hill2[i] += k - hills[i] - 8;
                        hill2[hill2.length - 1 - i] -= hills[hills.length - 1 - i] - k - 9;

                        cost += Math.pow(k - hills[i] - 8, 2);
                        cost += Math.pow(hills[hills.length - 1 - i] - k - 9, 2);

                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                    continue;
                }

            }

            if (cost < totCost) {
                totCost = cost;
            }
        }

        out.println(totCost);
        out.close();

    }

    public static void printO2Array(int[][] o) {
        int counter = 0;

        for (int[] a : o) {
            System.out.print(counter + ": ");
            System.out.print(a[0] + " ");
            System.out.print(a[1]);
            System.out.println();
            counter++;
        }
    }

    public static void sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                if ((Integer) array[i] > (Integer) array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

}
