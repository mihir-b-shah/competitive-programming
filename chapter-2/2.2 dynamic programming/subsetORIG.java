
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
 TASK: subset

 */
public class subsetORIG {

    public static PrintWriter out;
    public static int LIMIT;
    public static int SUM;
    public static ArrayList<Integer>[] data;


    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\ariprogOUT.txt"

        out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\subsetOUT.txt")));
        BufferedReader f = new BufferedReader(new FileReader("C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\subsetIN.txt"));
        
        long t1 = System.currentTimeMillis();
        
        LIMIT = Integer.parseInt(f.readLine());
        SUM = LIMIT * (LIMIT + 1) / 4;
        
        if(LIMIT % 4 == 0 || (LIMIT + 1) % 4 == 0)
        {
            data = new ArrayList[LIMIT + 1];

            for(int level = data.length - 1; level>=0; level--)
            {
                data[level] = new ArrayList<>();
                data[level].add(level);

                if(level < LIMIT)
                    for(int k = level + 1; k<=LIMIT; k++)
                        for(Integer x: data[k])
                        {
                            data[level].add(x + k - 1);
                        }

                if(level + 1 == LIMIT)
                    data[level].add(data[level + 1].get(0));
            }

            int counter = 0;

            for (int i = 2; i <= LIMIT; i++) {

                if(!data[i].isEmpty())
                {
                    for(Integer x: data[i])
                        if(SUM == x)
                            counter++;
                }
            }
            out.println(counter);
        } else {
            out.println(0);
        }
        
        System.out.println(t1);
        System.out.println(System.currentTimeMillis());
        out.close();
    }
}

/*
public class subset {

    public static PrintWriter out;
    public static int LIMIT;
    public static int SUM;
    public static ArrayList<Integer>[] data;
    public static int counter = 0;


    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\ariprogOUT.txt"

        out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\subsetOUT.txt")));
        BufferedReader f = new BufferedReader(new FileReader("C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\subsetIN.txt"));

        LIMIT = Integer.parseInt(f.readLine());
        SUM = LIMIT * (LIMIT + 1) / 4;
        ArrayList<Integer> origSet = new ArrayList<>();
        
        for(int i = 0; i<LIMIT; i++)
            origSet.add(i + 1);

        if(LIMIT % 4 == 0 || (LIMIT + 1) % 4 == 0)
        {
            recur(SUM, origSet);
            out.println(counter);
        }
        
        out.close();
    }
    
    public static void recur(int target, ArrayList<Integer> set)
    {
        if(target != 0)
            for(int i = 0; i<set.size(); i++)
            {
                ArrayList<Integer> newSet = (ArrayList<Integer>) set.clone();
                int x = newSet.remove(i);

                recur(target - x, newSet);
            }
        else
            System.out.println(counter + " " + set);
    }
}

public class subset {

    public static PrintWriter out;
    public static int counter = 0;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\ariprogOUT.txt"

        out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\subsetOUT.txt")));
        BufferedReader f = new BufferedReader(new FileReader("C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\subsetIN.txt"));
        final int LIMIT = Integer.parseInt(f.readLine());
        final int SUM = LIMIT * (LIMIT + 1) / 4;

        int[] data = new int[LIMIT + 1];
        
        recur(SUM, 0, LIMIT, 1);
        
        out.println(counter);
        out.close();
    }

    public static void recur(int target, int prior, int LIMIT, int sum) {
        for (int i = prior + 1; i < LIMIT; i++) {
            
            if(target == sum + 1 + i)
                counter++;
            
            recur(target, i, LIMIT, sum + (i + 1));
        }
    }
}


*/
