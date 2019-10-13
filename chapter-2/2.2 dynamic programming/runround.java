package oldprojects;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/*

 ID: mihirsh1
 LANG: JAVA
 TASK: runround

 */

public class runround {
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        
        // C:\Users\mihir\Documents\Backup\Stuff\NetBeansProjects\USACO\src
        
        BufferedReader f = new BufferedReader(new FileReader("runround.in"));
        PrintWriter out = new PrintWriter("runround.out");
        
        int lBound = Integer.parseInt(f.readLine());
        int num = lBound + 1;
        
        // Condition: not unique OR not runaround
        
        while(!isUnique(num) || !isRunAround(num))
            num++;
        
        out.println(num);
        
        out.flush();
        out.close();
        f.close();
        System.exit(0);

    }
    
    public static boolean isRunAround(int number)
    {
        int x = number;
        int len = (int) Math.log10(number) + 1;
        
        long visited = 0;
        
        int shift = getDigit(len-1, x);
        int last = len - 1;
        int counter = 0;
        int first = len - 1;
        
        //81362
        
        do {
            counter++;
            shift = returnMod(shift, len);
            last -= shift;
            last = returnMod(last, len);
            shift = getDigit(last, number);
            visited += 1 << last;
            
            if(first == last && visited < (int) Math.pow(2, len) - 1)
                return false;
                
        } while((first != last || visited < (int) Math.pow(2, len) - 1) && counter <= len + 1);
        
        return first == last;
       
    }
  
    public static int getDigit(int pos, int num)
    {
        if(num / (int) Math.pow(10, pos + 1) != 0)
            return (num / (int) Math.pow(10, pos)) % 10;
        else
            return (num / (int) Math.pow(10, pos));
    }        
    
    public static int returnMod(int num, int base)
    {
        if(num < 0)
            return base*((base - 1 - num)/base)+num;
        else
            return num % base;
        
    }

    public static boolean isUnique(int num) {
        
        short data = 0;
        int digit;
        
        while(num != 0)
        {
            digit = num % 10;
            
            if(digit == 0)
                return false;
            
            if((data & (1 << digit)) != 0)
                return false;
            else
                data += (1 << digit);
            
            num /= 10;
        }    
            
        return true;
    }

}
