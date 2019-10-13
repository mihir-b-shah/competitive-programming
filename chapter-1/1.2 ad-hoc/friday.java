package oldprojects;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**

ID: mihirsh1
LANG: JAVA
TASK: friday

 */ 

public class friday {
    
    public static final int[] DAY_MONTH = {3,0,3,2,3,2,3,3,2,3,2,3};
    public static final int START_13 = 0;
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        int day = START_13;
        
        //"C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\fridayINPUT.txt"
        
        BufferedReader f = new BufferedReader(new FileReader("friday.in"));
        int numYrs = Integer.parseInt(f.readLine());
        
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("friday.out")));
        int[] count = {1,0,0,0,0,0,0};
        
        
        for(int i = 0; i<numYrs; i++)
        {
            for(int j = 0; j<DAY_MONTH.length; j++)
            {
                boolean x = j == 1 && isLeapYear(i+1900);
                
                if(x)
                    day += DAY_MONTH[1] + 1;
                else    
                    day += DAY_MONTH[j];
                day %= 7;
                
                count[day]++;
            }
        }
        
        count[day]--;
        
        for(int k = 0; k<6; k++)
            out.print(count[k] + " ");
        
        out.print(count[6]);
        out.print("\n");
        out.close();
    }
    
    public static boolean isLeapYear(int i)
    {
        return i%400 == 0 || i%4 == 0 && i%100 != 0;
    }
    
}
