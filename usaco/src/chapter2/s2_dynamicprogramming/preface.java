package chapter2.s2_dynamicprogramming;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/*

 ID: mihirsh1
 LANG: JAVA
 TASK: preface

 */
public class preface {

    public static PrintWriter out;
    public static int[] romanNumerals;
    public static int[][] memory;
    public static int[] totals;

    static {

        romanNumerals = new int[7];

        romanNumerals[0] = 1000;
        romanNumerals[1] = 500;
        romanNumerals[2] = 100;
        romanNumerals[3] = 50;
        romanNumerals[4] = 10;
        romanNumerals[5] = 5;
        romanNumerals[6] = 1;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\ariprogOUT.txt"

        out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\prefaceOUT.txt")));
        //BufferedReader f = new BufferedReader(new FileReader("preface.in"));

        int LIMIT = 3500;//Integer.parseInt(f.readLine());
        memory = new int[LIMIT][8];
        totals = new int[8];

        try {

            memory[3][0] = 1;
            memory[3][1] = 1;
            memory[3][7] = 1;
            memory[8][0] = 1;
            memory[8][2] = 1;
            memory[8][7] = 1;
            memory[39][2] = 1;
            memory[39][3] = 1;
            memory[39][7] = 1;
            memory[89][2] = 1;
            memory[89][4] = 1;
            memory[89][7] = 1;
            memory[399][4] = 1;
            memory[399][5] = 1;
            memory[399][7] = 1;
            memory[899][4] = 1;
            memory[899][6] = 1;
            memory[899][7] = 1;

        } catch (IndexOutOfBoundsException e) {

        }

        for (int i = 1; i <= LIMIT; i++) {
            int num = i;
            int counter = 0;

            while (num != 0 && counter < 7) {
                int mod = (int) romanNumerals[counter];

                while (num / mod != 0) {

                    int quant = mod * (num / mod) - 1;
                    if (memory[num - 1][7] == 1) {
                        if (num != i) {
                            addArrays(memory[i - 1], memory[num - 1]);
                        }

                        num = 0;
                        break;
                    } else if (quant == 3 || quant == 8 || quant == 39 || quant == 89 || quant == 399 || quant == 899) {
                        addArrays(memory[i - 1], memory[mod * (num / mod) - 1]);
                        num -= mod * (num / mod);
                    } else if (num / 900 != 0 && (num / 1000 == 0)) {
                        memory[i-1][4]++;
                        memory[i-1][6]++;
                        num -= 900;
                    } else if (num / 90 != 0 && (num / 100 == 0)) {
                        memory[i-1][2]++;
                        memory[i-1][4]++;
                        num -= 90;
                    } else if (num / 9 != 0 && (num / 10 == 0)) {
                        memory[i-1][0]++;
                        memory[i-1][2]++;
                        num -= 9;
                    } else {
                        memory[i - 1][6 - counter]++;
                        num -= mod;
                    }
                }

                counter++;
            }

            memory[i - 1][7] = 1;
            System.out.println(Arrays.toString(memory[i-1]));
            addArrays(totals, memory[i - 1]);
        }

        if (totals[0] != 0) {
            out.println("I " + totals[0]);
        }
        if (totals[1] != 0) {
            out.println("V " + totals[1]);
        }
        if (totals[2] != 0) {
            out.println("X " + totals[2]);
        }
        if (totals[3] != 0) {
            out.println("L " + totals[3]);
        }
        if (totals[4] != 0) {
            out.println("C " + totals[4]);
        }
        if (totals[5] != 0) {
            out.println("D " + totals[5]);
        }
        if (totals[6] != 0) {
            out.println("M " + totals[6]);
        }

        out.close();
    }

    public static void addArrays(int[] to, int[] from) {
        for (int i = 0; i < to.length - 1; i++) {
            to[i] += from[i];
        }
    }
}
