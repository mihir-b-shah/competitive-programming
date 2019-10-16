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
TASK: milk

 */

public class milk {

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        // "C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\nnOUTPUT.txt"
        
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk.out")));
        BufferedReader f = new BufferedReader(new FileReader("milk.in"));
                
        String s = f.readLine();      
        
        int milkNeeded = Integer.parseInt(s.substring(0, s.indexOf(" ")));
        int numFarmers = Integer.parseInt(s.substring(s.indexOf(" ") + 1));
        
        int[][] priceMat = new int[numFarmers][2];
        
        for(int i = 0; i < numFarmers; i++)
        {
             s = f.readLine();
             
             priceMat[i][0] = Integer.parseInt(s.substring(0, s.indexOf(" ")));
             priceMat[i][1] = Integer.parseInt(s.substring(s.indexOf(" ") + 1));
        }
        
        int total = 0;
        int price = 0;
        
        while(total < milkNeeded)
        {
            int index = findLowest(priceMat);
            
            if(findLowest(priceMat) >= numFarmers)
                break;
            
            int cost = 0;
            int remainder;
            
            if(total + priceMat[index][1] <= milkNeeded)
            {
                cost = priceMat[index][0] * priceMat[index][1];
                total += priceMat[index][1];
                price += cost;
            } else {
                remainder = priceMat[index][0]*(milkNeeded - total);
                price += remainder;
                total = milkNeeded;
            }
            
            priceMat[index][0] = Integer.MAX_VALUE;
            priceMat[index][1] = 0;
            
        }
        
        out.println(price);
        out.close();
        
    }
    
    public static int findLowest(int[][] priceMat)
    {
        int MIN = Integer.MAX_VALUE;
        int INDEX = 0;
        int k = 0;
        
        for(k = 0; k < priceMat.length; k++)
        {
            if(priceMat[k][0] < MIN)
            {
                MIN = priceMat[k][0];
                INDEX = k;
            }
        }
        
        return INDEX;
    }
}