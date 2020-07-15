package chapter2.s1_graphs;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingQueue;

/*

 ID: mihirsh1
 LANG: JAVA
 TASK: holstein

 */
public class holsteinCC {

    public static PrintWriter out;
    public static LinkedBlockingQueue<Object[]> bfs_level = new LinkedBlockingQueue<>();
    public static boolean ENCOUNTERED = false; 
    public static int champion = Integer.MAX_VALUE;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\ariprogOUT.txt"

        out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\holsteinOUT.txt")));
        BufferedReader f = new BufferedReader(new FileReader("C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\holsteinIN.txt"));
        
        int vit_types = Integer.parseInt(f.readLine());
        String[] elements = f.readLine().split(" ");
        int[] vit_reqs = new int[vit_types];

        for (int i = 0; i < elements.length; i++) {
            vit_reqs[i] = Integer.parseInt(elements[i]);
        }

        int num_feeds = Integer.parseInt(f.readLine());
        int[][] feeds = new int[num_feeds][vit_types];

        for (int i = 0; i < num_feeds; i++) {
            String[] split = f.readLine().split(" ");

            for (int j = 0; j < split.length; j++) {
                feeds[i][j] = Integer.parseInt(split[j]);
            }
        }

        int sequence = 0;
        recur(vit_reqs, null, feeds, sequence, true);
        
        out.print(findNumBits(champion) + " ");
        int counter = 0;
        
        while(counter < feeds.length - 1)
        {
            int bitShift = 1 << (feeds.length - 1 - counter);

            if((champion & bitShift) != 0)
                out.print((counter + 1) + " ");
            
            counter++;
        }
        
        out.println();
        out.close();

    }

    public static void recur(int[] initial, int[] other, int[][] feeds, int sequence, boolean tail) {
        
        if (isZero(initial)) {
            
            ENCOUNTERED = true;

            if(smallerThan(sequence, champion))
                champion = sequence;
                    
        } else {
            
            if(!ENCOUNTERED)
                for (int i = 0; i < feeds.length; i++) {
                    
                    int x = sequence & (1 << (feeds.length - 1 - i));
                    int newSeq = sequence;
                    
                    if((other == null || !isEqual(other, initial)) && x == 0)
                    {
                        int[] array = initial.clone();

                        int diff = subtractArray(array, feeds[i]);
                        //newSeq.set(0, (int) newSeq.get(0) + diff);
                        newSeq += 1 << (feeds.length - 1 - i);

                        Object[] parameters = new Object[5];
                        parameters[0] = array;
                        parameters[1] = initial;
                        parameters[2] = feeds;
                        parameters[3] = newSeq;
                        parameters[4] = i == feeds.length - 1;

                        bfs_level.add(parameters);
                    }
                }
            
            if(tail)
                emptyQueue();
        }
    }

    public static void emptyQueue()
    {
        try {
            while(!bfs_level.isEmpty())
            {
                Object[] params = bfs_level.poll();
                //System.out.println((ArrayV2) params[2]);
                recur((int[]) params[0], (int[]) params[1], (int[][]) params[2], (int) params[3], (boolean) params[4]);
            }
        } catch (NullPointerException e) {
            
        }
            
    }

    public static int subtractArray(int[] array, int[] transform) {
        int differential = 0;

        for (int i = 0; i < array.length; i++) {
            array[i] -= transform[i];

            if (array[i] < 0) {
                differential -= array[i];
                array[i] = 0;
            }
        }

        return differential;
    }

    public static boolean isZero(int[] array) {
        for (int i : array) {
            if (i > 0) {
                return false;
            }
        }

        return true;
    }
    
    public static boolean isEqual(int[] a1, int[] a2)
    {
        for(int i = 0; i<a1.length; i++)
            if(a1[i] != a2[i])
                return false;
        
        return true;
    }
    
    public static boolean smallerThan(int curr, int that)
    {
        if(findNumBits(curr) < findNumBits(that))
            return true;
        else if(findNumBits(curr) == findNumBits(that) && that < curr)
            return true;

        return false;
    }
    
    public static int findNumBits(int num)
    {
        int counter = 0;
        String s = Integer.toBinaryString(num);
        
        for(int i = 0; i<s.length(); i++)
            if(s.charAt(i) == '1')
                counter++;
                
        return counter;
    }
}
