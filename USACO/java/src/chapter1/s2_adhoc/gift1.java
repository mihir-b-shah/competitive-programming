package chapter1.s2_adhoc;


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
TASK: gift1

 */ 

public class gift1 {
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {        
        //C:\\Users\\mihir\\Documents\\NetBeansProjects\\USACO\\src\\gift1INPUT.txt        
        
        BufferedReader f = new BufferedReader(new FileReader("gift1.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("gift1.out")));
        
        int numPersons = Integer.parseInt(f.readLine());
        
        Object[][] balanceSheet = new Object[numPersons][2];
        
        for(int i = 0; i<balanceSheet.length; i++)
        {
            balanceSheet[i][0] = f.readLine();
            balanceSheet[i][1] = 0;
        }
        
        int counter = 0;
        
        while(true)
        {
            try {
               
                Object o = "";
                
                
                o = f.readLine();
                
                if(o == null)
                    break;
                
                System.out.println(o);
                               
                if(o.getClass() == java.lang.String.class)
                {
                    int acctNum = findEntry(balanceSheet, (String) o);
                    String amount = f.readLine();

                    int amt = Integer.parseInt(amount.substring(0, amount.indexOf(" ")));
                    int personCt = Integer.parseInt(amount.substring(amount.indexOf(" ")+1));
                    
                    if(personCt != 0 && amt != 0)
                        amt /= personCt;
                    
                        for(int k = 0; k<personCt; k++)
                        {
                            int x = findEntry(balanceSheet, f.readLine());
                            balanceSheet[x][1] = (Integer) balanceSheet[x][1] + amt;

                        }

                        balanceSheet[acctNum][1] = (Integer) balanceSheet[acctNum][1] - amt*personCt;
                    }
                  
            
            } catch (IOException e) {
                break;
            }
            
            counter++;
        }    
        
        for(int i = 0; i<balanceSheet.length; i++)
            out.println((String) balanceSheet[i][0] + " " + ((Integer) balanceSheet[i][1]).toString());
        
        out.close();
    }
    
    public static int findEntry(Object[][] array, String person)
    {
        for(int i = 0; i<array.length; i++)
            if(array[i][0].equals(person))
                return i;
                        
        return -1;
    }
    
}
