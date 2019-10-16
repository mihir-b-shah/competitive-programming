package chapter1.s4_greedy;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*

 ID: mihirsh1
 LANG: JAVA
 TASK: combo

 */
public class combo {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\nnOUTPUT.txt"

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("combo.out")));
        BufferedReader f = new BufferedReader(new FileReader("combo.in"));

        int numClicks = Integer.parseInt(f.readLine());

        int[] johnCombo = new int[3];
        int[] masterCombo = new int[3];
        String[] combo = f.readLine().split(" ");

        for (int i = 0; i < 3; i++) {
            johnCombo[i] = Integer.parseInt(combo[i]);
        }

        combo = f.readLine().split(" ");

        for (int i = 0; i < 3; i++) {
            masterCombo[i] = Integer.parseInt(combo[i]);
        }

        int counter = 250;
        int product = 1;
        
        int[][] results = corrector(johnCombo, masterCombo, numClicks);

        for (int i = 0; i < 3; i++) {
            int m = masterCombo[i];
            int j = johnCombo[i];

            int[] mRange = {refinedMod(m - 2, numClicks), refinedMod(m + 2, numClicks)};
            int[] jRange = {refinedMod(j - 2, numClicks), refinedMod(j + 2, numClicks)};
            
            if(mRange[1] == mRange[0] && numClicks > 2)
                mRange[1] += numClicks;
            
            if(jRange[1] == jRange[0] && numClicks > 2)
                jRange[1] += numClicks;

            product *= intersectRange(jRange, mRange, numClicks);
        }
        
        if ((Math.abs(johnCombo[0] - masterCombo[0]) >= 4 || Math.abs(johnCombo[0] - masterCombo[0]) <= numClicks - 4) && (Math.abs(johnCombo[1] - masterCombo[1]) >= 4 || Math.abs(johnCombo[1] - masterCombo[1]) <= numClicks - 4) && (Math.abs(johnCombo[2] - masterCombo[2]) >= 4 || Math.abs(johnCombo[2] - masterCombo[2]) <= numClicks - 4)) {
            out.println(counter - product);
        } else {
            out.println(product);
        }
        
        out.close();
    }

    
    public static int intersectRange(int[] r1, int[] r2, int mod) {

        int output = 1;
        
        if(r1[0] == r2[0] && r1[1] == r2[1] && (r1[0] + 4 == r1[1] + mod))
            return r1[1] + mod - r1[0] + 1;
        
        if(r1[1] >= r2[0])
        {
            output = r1[1] - r2[0] + 1;
        } else {
            output = 0;
        }
            
        if(output == 0)
        {
            
            if(r1[0] >= r2[1])
            {
                if(r1[0] == r2[1])
                {
                    output = r1[0] - r2[1] + 1;
                } else {
                    output = Math.abs(r1[0] - r2[1] + 1-mod);
                }
            } else {
                output = 0;
            }
            
        }
        
        
        
        return output;
    }
    
    public static int refinedMod(int x, int mod) {
    
        int output = 0;

        if((x == mod || x < 0) && mod > 2)
        {
            output = x;
        } else {
            output = x % mod;
        }
        
        return output;
    }
    
    public static int[][] corrector(int[] r1, int[] r2, int mod)
    {
        for(int i = 0; i<3; i++)
        {
            if(simpleMod(r1[i], mod) > simpleMod(r2[i], mod))
            {
                if(r2[i] + mod - r1[i] >= mod/2)
                {
                    int temp = r1[i];
                    r1[i] = r2[i];
                    r2[i] = temp;
                }
            }
        }
        
        int[][] array = new int[2][3];
        array[0] = r1;
        array[1] = r2;
        
        return array;
    }
    
    public static int simpleMod(int num, int mod)
    {
        if(num == mod)
            return num;
        else
            return num % mod;
            
    }
    
    
}
