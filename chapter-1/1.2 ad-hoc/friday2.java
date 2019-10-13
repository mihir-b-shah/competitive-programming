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

public class friday2 {
    
    public static int day = 2;
    public static int dayCounter = 0;
    public static final int[] DAY_MONTH = {31,28,31,30,31,30,31,31,30,31,30,31};
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        //"C:\\Users\\mihir\\Documents\\NetBeansProjects\\USACO\\src\\fridayINPUT.txt"
        
        BufferedReader f = new BufferedReader(new FileReader("C:\\Users\\mihir\\Documents\\NetBeansProjects\\USACO\\src\\fridayINPUT.txt"));
        
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\mihir\\Documents\\NetBeansProjects\\USACO\\src\\fridayOUTPUT.txt")));
        
        int numYrs = Integer.parseInt(f.readLine());
        int yrCntr = 0;
        int monthCntr = 0;

        int[] count = new int[7];
        
        while(yrCntr < numYrs)
        {
            monthCntr = 0;
            
            for(int numDays: DAY_MONTH)
            {
                for(int i = 0; i<13; i++)
                {
                    advanceDayFirst();
                }
                
                count[day]++;
                
                for(int i = 13; i<numDays; i++)
                {
                    advanceDaySecond(monthCntr, yrCntr + 1900);
                }
                
                monthCntr++;
            }  
            
            yrCntr++;
        }
        
        for(int i=0; i<6; i++)
            out.print(count[i+1] + " ");
            
        out.print(count[0] + "\n");
        
        out.close();
    }
    
    public static void advanceDayFirst()
    {
        dayCounter += 12;
        
        day += 12;
        day %= 7;
            
    }
    
    public static void advanceDaySecond(int month, int year)
    {
        dayCounter += numDayCounter(month, year) - 12;
        day += numDayCounter(month, year) - 12;
        day %= 7;
    }
    
    public static int numDayCounter(int month, int year)
    {       
        if(month == 1 && isLeapYear(year))
        {
            return 29;
        }
        
        return DAY_MONTH[month];
    }
    
    public static boolean isLeapYear(int year)
    {
        return year % 4 == 0 && !(year % 100 == 0) || year % 400 == 0;            

    }
    
}
