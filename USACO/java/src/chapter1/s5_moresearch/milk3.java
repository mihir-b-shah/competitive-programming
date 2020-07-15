package chapter1.s5_moresearch;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/*

 ID: mihirsh1
 LANG: JAVA
 TASK: milk3

 */
public class milk3 {

    public static PrintWriter out;
    public static ArrayList<Integer> solutions = new ArrayList<>();

    public static class Buckets implements Cloneable {

        public Bucket[] buckets;

        public Buckets(Bucket A, Bucket B, Bucket C) {
            buckets = new Bucket[3];
            buckets[0] = A;
            buckets[1] = B;
            buckets[2] = C;
        }

        @Override
        public Buckets clone() {
            Bucket A_prime = buckets[0].clone();
            Bucket B_prime = buckets[1].clone();
            Bucket C_prime = buckets[2].clone();

            return new Buckets(A_prime, B_prime, C_prime);
        }

        @Override
        public String toString() {
            return buckets[0].toString() + buckets[1].toString() + buckets[2].toString();
        }

        public boolean equals(Buckets other) {
            return buckets[0].equal(other.buckets[0]) && buckets[1].equal(other.buckets[1]) && buckets[2].equal(other.buckets[2]);
        }

    }

    public static class Bucket implements Cloneable {

        public int amount;
        public int limit;

        public Bucket(int limit) {
            this.limit = limit;
            amount = 0;
        }

        public Bucket(int limit, int amount) {
            this.limit = limit;
            this.amount = amount;
        }

        public void pourTo(Bucket other) {
            if (other.limit >= amount + other.amount) {
                other.amount += amount;
                amount = 0;

            } else {

                amount -= (other.limit - other.amount);
                other.amount = other.limit;

            }
        }

        @Override
        public Bucket clone() {
            return new Bucket(this.limit, this.amount);
        }

        @Override
        public String toString() {
            return (amount + " ");
        }

        public boolean equal(Bucket other) {
            return other.limit == this.limit && other.amount == this.amount;
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\ariprogOUT.txt"

        out = new PrintWriter(new BufferedWriter(new FileWriter("milk3.out")));
        BufferedReader f = new BufferedReader(new FileReader("milk3.in"));

        String[] values = f.readLine().split(" ");

        Bucket A = new Bucket(Integer.parseInt(values[0]));
        Bucket B = new Bucket(Integer.parseInt(values[1]));

        int val3 = Integer.parseInt(values[2]);
        Bucket C = new Bucket(val3, val3);

        solutions.add(C.amount);

        Buckets buckets = new Buckets(A, B, C);
        ArrayList<Buckets> priors = new ArrayList<>();

        recur(buckets, priors);

        Collections.sort(solutions);
        HashSet<Integer> solution = new HashSet<>(solutions);

        ArrayList<Integer> newsols = new ArrayList<>(solution);
                
        if(newsols.contains(0) && B.limit < C.limit)
            newsols.remove(newsols.indexOf(0));
        
        for (int i = 0; i < newsols.size() - 1; i++) {
            out.print(newsols.get(i) + " ");
        }

        out.print(newsols.get(newsols.size() - 1));
        out.print("\n");

        out.close();
    }

    public static void recur(Buckets buckets, ArrayList<Buckets> priors) {

        ArrayList<Integer> buckets2 = findEmpty(buckets);
        
        /*

        System.out.println(buckets.toString());
        System.out.println(priors.toString());
        System.out.println();
                
        */

        priors.add(buckets);

        if (buckets2.size() == 1) {
            for (int i = 0; i < 3; i++) {
                if (i != buckets2.get(0)) {
                    Buckets pets = buckets.clone();
                    pets.buckets[i].pourTo(pets.buckets[buckets2.get(0)]);

                    if (pets.buckets[0].amount == 0) {
                        System.out.println(pets);
                        solutions.add(pets.buckets[2].amount);
                    }

                        // System.out.println(pets.toString());
                    if (!contains(priors, pets)) {
                        recur(pets, priors);
                    }
                }
            }
        } else if (buckets2.isEmpty()) {

            for(int k = 0; k<3; k++)
                for (int i = 0; i < 3; i++) {
                    if(k != i)
                    {
                        Buckets pets = buckets.clone();
                        pets.buckets[k].pourTo(pets.buckets[i]);

                        if (pets.buckets[0].amount == 0) {
                            solutions.add(pets.buckets[2].amount);
                            System.out.println(pets);
                        }

                            // System.out.println(pets.toString());
                        if (!contains(priors, pets)) {
                            recur(pets, priors);
                        }
                    }
                }
                
        } else if (buckets2.size() == 2) {

            int e1 = buckets2.get(0);
            int e2 = buckets2.get(1);

            Buckets pets1 = buckets.clone();
            Buckets pets2 = buckets.clone();

            pets1.buckets[findMax(buckets)].pourTo(pets1.buckets[e1]);
            pets2.buckets[findMax(buckets)].pourTo(pets2.buckets[e2]);

            if (pets1.buckets[0].amount == 0) {
                solutions.add(pets1.buckets[2].amount);
                System.out.println(pets1);
            }

            if (pets2.buckets[0].amount == 0) {
                solutions.add(pets2.buckets[2].amount);
                System.out.println(pets2);
            }

                // System.out.println(pets1.toString());
            // System.out.println(pets2.toString());
            if (!contains(priors, pets1)) {
                recur(pets1, priors);
            }

            if (!contains(priors, pets2)) {
                recur(pets2, priors);
            }
        }

    }

    public static ArrayList<Integer> findEmpty(Buckets buckets) {
        ArrayList<Integer> others = new ArrayList<>();

        if (buckets.buckets[0].amount == 0) {
            others.add(0);
        }
        if (buckets.buckets[1].amount == 0) {
            others.add(1);
        }
        if (buckets.buckets[2].amount == 0) {
            others.add(2);
        }

        return others;

    }

    public static int findMax(Buckets buckets) {
        int index = 0;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < 3; i++) {
            if (buckets.buckets[i].amount > max) {
                index = i;
                max = buckets.buckets[i].amount;
            }
        }

        return index;
    }

    public static boolean contains(ArrayList<Buckets> buckets, Buckets element) {
        for (Buckets b : buckets) {
            if (b.equals(element)) {
                return true;
            }
        }

        return false;
    }
}
