
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

/*

 ID: mihirsh1
 LANG: JAVA
 TASK: ariprog

 */

public class ariprogORIG {

    public static PrintWriter out;
    public static ArrayList<int[]> solutions = new ArrayList<>();
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\nnOUTPUT.txt"

        out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\ariprogOUT.txt")));
        BufferedReader f = new BufferedReader(new FileReader("C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\ariprogIN.txt"));

        int lenProg = Integer.parseInt(f.readLine());
        int uppBound = Integer.parseInt(f.readLine());

        ArrayList<Integer> elements = new ArrayList();
        
        for(int i = 0; i<=uppBound; i++)
        {            
            for(int j = 0; j<= i; j++)
            {
                elements.add(i*i + j*j);
                //System.out.println(i*i + j*j);
            }
        }

        int[] sequence = new int[lenProg];
        
        for(int i = 0; i<sequence.length; i++)
            sequence[i] = Integer.MAX_VALUE;
        
        removeDuplicates(elements);
        recursiveFinder(-1, lenProg - 1, sequence, elements);
        
        for(int i = 0; i<solutions.size(); i++)
        {
            for(int j = i + 1; j<solutions.size(); j++)
            {
                if(solutions.get(i)[1] > solutions.get(j)[1])
                {
                    int[] temp = solutions.get(i);
                    solutions.set(i, solutions.get(j));
                    solutions.set(j, temp);
                } else if(solutions.get(i)[1] == solutions.get(j)[1] && solutions.get(i)[0] > solutions.get(j)[0]) {
                    int[] temp = solutions.get(i);
                    solutions.set(i, solutions.get(j));
                    solutions.set(j, temp);
                }
            }
        }
        
        for(int[] x: solutions)
            out.println(x[0] + " " + x[1]);
        
        if(solutions.size() == 0)
            out.println("NONE");
        
        out.close();
    }
    
    public static void recursiveFinder(int START, int level, int[] sequence, ArrayList<Integer> elements)
    {
        if(level >= 0)
        {
            for(int i = START + 1; i<elements.size() - level; i++)
            {                
                int[] newSequence = sequence.clone();
                newSequence[sequence.length - 1 - level] = elements.get(i);
                
                if(!checkArithmeticSequence(newSequence))
                    continue;
                
                recursiveFinder(i, level - 1, newSequence, elements);
            }
            
        } else {

            if(checkArithmeticSequence(sequence))
            {                
                int[] solution = new int[2];
                solution[0] = sequence[0];
                solution[1] = sequence[1] - sequence[0];
                
                solutions.add(solution);
            }
        }
    }
    
    public static void removeDuplicates(ArrayList<Integer> elements)
    {
        LinkedHashSet<Integer> s = new LinkedHashSet<>(elements);
        elements.clear();
        elements.addAll(s);
        
        for(int i = 0; i<elements.size() - 1; i++)
        {
            for(int j = i + 1; j<elements.size(); j++)
            {
                if(elements.get(i) > elements.get(j))
                {
                    int temp = elements.get(i);
                    elements.set(i, elements.get(j));
                    elements.set(j, temp);
                }
            }
        }
        
    }
    
    public static boolean checkArithmeticSequence(int[] sequence)
    {        
        int RATIO = sequence[1] - sequence[0];
        
        for(int i = 2; i<sequence.length; i++)
        {
            if(sequence[i] != Integer.MAX_VALUE && sequence[i-1] != Integer.MAX_VALUE && sequence[i] - sequence[i-1] != RATIO)
                return false;
        }
        
        return true;
    }
}
