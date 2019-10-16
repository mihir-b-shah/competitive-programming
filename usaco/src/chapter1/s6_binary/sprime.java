package chapter1.s6_binary;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/*

 ID: mihirsh1
 LANG: JAVA
 TASK: sprime

 */
public class sprime {

    public static PrintWriter out;
    
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
        // "C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\ariprogOUT.txt"
        
        out = new PrintWriter(new BufferedWriter(new FileWriter("sprime.out")));
        BufferedReader f = new BufferedReader(new FileReader("sprime.in"));

        int length = Integer.parseInt(f.readLine());
        ArrayList<Integer> one_digit_primes = generatePrimes(0, 9);
        
        recursiveHelper(1, length, one_digit_primes, 0);
        out.close();
        
    }
    
    public static void recursiveHelper(int level, int total, ArrayList<Integer> primes, int number)
    {
        if(level <= total)
        {
            for(int i: primes)
            {
                recursiveHelper(level + 1, total, generatePrimes(10*i, 10*i + 9), i);
            }
        } else {
            out.println(number);
        }
    }
    
    public static ArrayList<Integer> generatePrimes(int low, int high)
    {
        ArrayList<Integer> primes = new ArrayList<>();
        
        for(int i = low; i<= high; i++)
        {
            if(isPrime(i))
                primes.add(i);
        }
        
        return primes;
    }     
    
    public static boolean isPrime(int num)
    {
        if(num == 0)
            return false;
        if(num == 1)
            return false;
        
        for(int i = 2; i<=Math.sqrt(num); i++)
        {
            if(num % i == 0)
                return false;
        }
        
        return true;
    }
}