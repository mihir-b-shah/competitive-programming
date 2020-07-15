package chapter2.s1_graphs;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/*

 ID: mihirsh1
 LANG: JAVA
 TASK: frac1

 */
public class frac1 {

    public static PrintWriter out;
    
    public static class Fraction implements Comparable
    {
        public int N;
        public int D;
        
        public Fraction(int N, int D)
        {
            this.N = N;
            this.D = D;
        }
        
        @Override
        public int compareTo(Object o)
        {
            Fraction f = (Fraction) o;
            double value = (double) f.N / (double) f.D - (double) N / (double) D;
            value *= 100000000;
            int new_value = (int) value;
            return -1*new_value;
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\ariprogOUT.txt"

        out = new PrintWriter(new BufferedWriter(new FileWriter("frac1.out")));
        BufferedReader f = new BufferedReader(new FileReader("frac1.in"));
        
        int length = Integer.parseInt(f.readLine());
        ArrayList<Double> values = new ArrayList<>();
        ArrayList<Fraction> solutions = new ArrayList<>();
        
        for(double D = 1; D<=length; D++)
        {
            for(double N = 0; N<=D; N++)
            {
                double value = N/D;
                value *= Math.pow(10, 6);
                value = (int) value;
                value /= (int) Math.pow(10, 6);

                if(!values.contains(value))
                {
                    values.add(value);
                    int N1 = (int) N;
                    int D1 = (int) D;
                    
                    solutions.add(new Fraction(N1, D1));
                }
            }
        }
    
        Collections.sort(solutions);
        
        for(Fraction g: solutions)
        {
            out.println(g.N + "/" + g.D);
        }
        
        out.close();
    }
}