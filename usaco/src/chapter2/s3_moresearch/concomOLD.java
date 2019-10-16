package chapter2.s3_moresearch;

/*

ID: mihirsh1 
LANG: JAVA 
TASK: concom

 */

import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class concomOLD {

    public static Company[] companies;
    public static int[] visited;
    public static ArrayList<IntPair> solutions;
    public static HashMap<Integer, Integer> backward;
    
    public static class IntPair implements Comparable {
        
        private int p1;
        private int p2;
        
        public IntPair(int p1, int p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
        
        public int getOne() {return p1;}
        public int getTwo() {return p2;}
        
        @Override
        public int compareTo(Object o) {
            IntPair other = (IntPair) o;
            if(other.getOne() != p1)
                return p1 - other.getOne();
            else
                return p2 - other.getTwo();
        }
        
        @Override
        public String toString() {
            return String.format("%1$d %2$d", p1, p2);
        }
    }
    
    public static class Company {
        
        private int[] holdings;
        private boolean[] owned; 
        
        public Company(int N) {
            holdings = new int[N];
            owned = new boolean[N];
        }
        
        public int[] getHoldings() {
            return holdings;
        }
        
        public boolean[] getOwned() {
            return owned;
        }
        
        public void addCompany(int i, int amt) {
            holdings[i] = amt;
        }
        
        public void addCompany(Company c) {
            boolean[] own = c.getOwned();
            int[] hold = c.getHoldings();
            
            for(int i = 0; i<own.length; i++) {
                owned[i] = owned[i] || own[i];
                holdings[i] += hold[i];
            }
        }
    }
    
    public static void main(String[] args) throws Exception {

        Scanner f = new Scanner(new FileReader("concom.in"));
        PrintWriter out = new PrintWriter(new FileWriter("concom.out"));
        
        // Extract data and match to a lookup file
        
        f.nextInt();
        HashMap<Integer, Integer> forward = new HashMap<>();
        backward = new HashMap<>();
        
        int loc = 0;
        int counter = 0;
        int index = 0;
        
        while(f.hasNextInt()) {
            counter++;
            loc = f.nextInt();
            
            if(counter % 3 != 0) {
                if(!forward.containsKey(loc)) {
                    forward.put(loc, index);
                    backward.put(index, loc);
                    index++;
                }
            }
        }
        
        visited = new int[forward.size()];
        
        for(int i = 0; i<visited.length; i++)
            visited[i] = 1;
        
        f.close();
        f = new Scanner(new FileReader("concom.in"));
        
        int N = f.nextInt();
        companies = new Company[forward.size()];
        
        for(int i = 0; i<forward.size(); i++) {
            companies[i] = new Company(forward.size());
        }
        
        for(int i = 0; i<N; i++) {
            try {
                int compNum = f.nextInt();
                int next = f.nextInt();
                companies[forward.get(compNum)].addCompany(forward.get(next), f.nextInt());
                
                visited[forward.get(compNum)]++;
                visited[forward.get(next)]++;
                
            } catch (NoSuchElementException e) {
                break;
            }
        }
        
        solutions = new ArrayList<>();
        
        for(int i = 0; i<forward.size(); i++)
            recur(i);
        
        for(int i = 0; i<companies.length; i++) 
            for(int j = 0; j<companies[i].getOwned().length; j++) 
                if(companies[i].getOwned()[j])
                    solutions.add(new IntPair(backward.get(i), backward.get(j)));
        
        Collections.sort(solutions);
        
        for(IntPair i: solutions) {
            out.println(i);
        }
        
        out.flush();
        out.close();
        f.close();
    }    
    
    public static void recur(int company) {
        
        System.out.println("Hashed: " + company + " Actual: " + backward.get(company));
        
        update(company);
        boolean[] ref = companies[company].getOwned();
        
        for(int i = 0; i<companies.length; i++) {
            if(company != i) {
                if(ref[i]) {
                    if(visited[i] > 0) {
                        visited[i]--;
                        recur(i);
                        companies[company].addCompany(companies[i]);
                    }
                }
            }
        }
        
        update(company);
    }
    
    public static void update(int company) {
        
        int len = companies[company].getHoldings().length;
        boolean[] owned = companies[company].getOwned();
        int[] holdings = companies[company].getHoldings();
        
        for(int i = 0; i<len; i++) 
            if(holdings[i] > 50)
                owned[i] = true;
    }
}