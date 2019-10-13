package oldprojects;


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
 TASK: hamming

 */
public class hamming {

    public static PrintWriter out;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\ariprogOUT.txt"

        out = new PrintWriter(new BufferedWriter(new FileWriter("hamming.out")));
        BufferedReader f = new BufferedReader(new FileReader("hamming.in"));
        String[] input = f.readLine().split(" ");
        
        int N = Integer.parseInt(input[0]);
        int B = Integer.parseInt(input[1]);
        int D = Integer.parseInt(input[2]);
        
        int LIMIT = (int) Math.pow(2, B);
        
        ArrayList<Integer> solutions = new ArrayList<>();
        solutions.add(0);
        boolean found = false;
        
        for(int i = 1; i<LIMIT; i++)
        {
            found = false;
            
            if(solutions.size() < N) {
                while(checkAll(i, solutions, D) && solutions.size() < N && i<LIMIT)
                {
                    solutions.add(i);
                    i++;                                            
                    found = true;
                }

                if(found)
                    i--;
            }
        }
        
        printAll(solutions);
        out.close();
    }
    
    public static void printAll(ArrayList<Integer> solutions)
    {
        int j = 0;
        int counter = 0;
        for(int i = 0; i<solutions.size()/10 + 1; i++)
        {
            for(j = 0; j<9; j++)
                if(10*i + j < solutions.size() - 1)
                {
                    out.print(solutions.get(10*i + j) + " ");
                    counter++;
                }
            
            if(10*i + 9 < solutions.size())
            {
                out.println(solutions.get(10*i + 9));
                counter++;
            }
            if(counter == solutions.size() - 1)
                out.println(solutions.get(counter));
        }
    }
    
    public static boolean checkAll(int i, ArrayList<Integer> past, int distance)
    {
        for(int pastSol : past)
            if(distance > findDistance(pastSol, i))
                return false;
        
        return true;
    }
    
    public static int findDistance(int a, int b)
    {
        int counter = 0;
        while(a != 0 || b != 0)
        {
            if((a % 2) != (b % 2))
                counter++;
            
            a /= 2;
            b /= 2;
        }
        
        return counter;
    }
}
