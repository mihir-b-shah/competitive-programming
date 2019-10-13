package oldprojects;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/*

 ID: mihirsh1
 LANG: JAVA
 TASK: zerosum

 */

public class zerosum {

    public static ArrayList<String> solutions = new ArrayList<>();
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        BufferedReader f = new BufferedReader(new FileReader("zerosum.in"));
        PrintWriter out = new PrintWriter("zerosum.out");

        int N = Integer.parseInt(f.readLine());
        ArrayList<Integer> array = new ArrayList<>();
        array.add(1);
        
        recur(array, 0, N-1);
        Collections.sort(solutions);
        
        for(String s: solutions)
            out.println(s);
        
        f.close();
        out.close();
    }
    
    public static void recur(ArrayList<Integer> array, int level, int N)
    {
        if(level < N)
        {
            for(int i = -1; i>-4; i--)
            {
                ArrayList<Integer> clone = (ArrayList<Integer>) array.clone();
                clone.add(i);
                clone.add(level + 2);
                recur(clone, level + 1, N); 
            }
        } else {

            if(evaluate(array))
            {
                interpret(array);
            }
        }
    }
    
    public static boolean evaluate(ArrayList<Integer> arr)
    {
        ArrayList<Integer> array = (ArrayList<Integer>) arr.clone();

        int counter = 1;
        boolean enc;
        
        while(counter < array.size() - 1)
        {
            enc = false;
            
            while(counter < array.size() && array.get(counter) == -3)
            {
                enc = true;
                array.set(counter, concat(array.get(counter - 1), array.get(counter + 1)));
                array.remove(counter + 1);
                array.remove(counter - 1);
            }
            
            if(!enc)
                counter++;
        }
        
        int sum = array.get(0);
        
        for(int i = 1; i<array.size(); i+=2)
        {
            if(array.get(i) == -2)
            {
                sum -= array.get(i + 1);
            } else {
                sum += array.get(i + 1);
            }
        }
        
        return sum == 0;
    }
    
    public static int concat(int d1, int d2)
    {
        return 10*d1 + d2;
    }
    
    public static void interpret(ArrayList<Integer> array)
    {
        StringBuilder g = new StringBuilder();
        
        for(int i = 0; i<array.size(); i++)
        {
            if(array.get(i) > 0)
                g.append(array.get(i));
            else if(array.get(i) == -1)
                g.append('+');
            else if(array.get(i) == -2)
                g.append('-');
            else
                g.append(' ');
        }
        
        solutions.add(g.toString());
    }
}
