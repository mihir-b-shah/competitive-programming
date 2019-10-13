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
 TASK: wormhole

 */
public class wormholeCC {
    
    public static int COUNTER = 0;

    public static class Pairing {
        
        public Coordinate c1;
        public Coordinate c2;
        
        public Pairing(Coordinate c1, Coordinate c2)
        {
            this.c1 = c1;
            this.c2 = c2;
        }
        
        @Override
        public String toString()
        {
            return " [" + c1.toString() + "," + c2.toString() + "] ";
        }
        
    }
    
    public static class Coordinate {
        
        public int X;
        public int Y;
        
        public Coordinate(int x, int y)
        {
            X = x;
            Y = y;
        }
        
        public Coordinate(String s)
        {
            X = Integer.parseInt(s.substring(0, s.indexOf(" ")));
            Y = Integer.parseInt(s.substring(s.indexOf(" ") + 1));
        }
        
        public boolean checkSame(Coordinate c)
        {
            if(c.X == X && c.Y == Y)
                return true;
            else
                return false;
        }
        
        public void advance()
        {
            X += 1;
        }
        
        @Override
        public String toString()
        {
            return "(" + X + "," + Y + ")";
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\nnOUTPUT.txt"

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\wormholeOUT.txt")));
        BufferedReader f = new BufferedReader(new FileReader("C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\wormholeIN.txt"));

        int numHoles = Integer.parseInt(f.readLine());
        ArrayList<Coordinate> wormHoles = new ArrayList<>();
        
        for (int i = 0; i < numHoles; i++) {
            
            String s = f.readLine();
            wormHoles.add(new Coordinate(s));
        }
        
        ArrayList<Pairing> pairings = new ArrayList<>();
        generate(wormHoles.size()/2, wormHoles, pairings);
        
        for(int i = 0; i<numHoles/2 + 1; i++)
            COUNTER /= 2;
        
        out.println(COUNTER);
        out.close();
        
    }
    
    //divide all possible solutions by Math.pow(2, wormholes.size()/2)
    
    public static void generate(int level, ArrayList<Coordinate> wormHoles, ArrayList<Pairing> pairings)
    {
        if(level > 0)
        {
            for(int i = 0; i<wormHoles.size(); i++)
            {
                for(int j = 0; j<wormHoles.size(); j++)
                {
                    if(i != j)
                    {
                        ArrayList<Coordinate> newHoles = (ArrayList<Coordinate>) wormHoles.clone();
                        ArrayList<Pairing> pairings2 = (ArrayList<Pairing>) pairings.clone();
                        
                        Coordinate c1 = newHoles.remove(i);
                        Coordinate c2 = null;
                        
                        if(i > j)
                        {
                            c2 = newHoles.remove(j);
                        } else {
                            c2 = newHoles.remove(j-1);
                        }
                        
                        pairings2.add(new Pairing(c1, c2));
                        generate(level - 1, newHoles, pairings2);
                    }
                }
            }
        } else {
            
            if(level == 0 && determineInfinite(pairings))
            {
                COUNTER++;
            }
                
        }
    }
    
    public static boolean determineInfinite(ArrayList<Pairing> pairings)
    {
        
        
        
        return false;
    }
    

}
