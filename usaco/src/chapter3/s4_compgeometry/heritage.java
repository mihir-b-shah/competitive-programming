package chapter3.s4_compgeometry;

/*
ID: mihirsh1
TASK: heritage
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class heritage {

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("heritage.in"));
        PrintWriter out = new PrintWriter("heritage.out");

        String in = f.nextLine();
        String pre = f.nextLine();
        
        String result = recur(in, pre);
        
        out.println(result);
        out.flush();
        out.close();
        f.close();
    }

    public static String recur(String in, String pre) {       
        if(in.length() > 1) {
            String root = pre.substring(0,1);
            int piv = in.indexOf(root);
            return recur(in.substring(0, piv), pre.substring(1, piv+1)) + 
                   recur(in.substring(piv+1, in.length()), pre.substring(piv+1,pre.length())) + root;
        } else return in;
    }
    
}
