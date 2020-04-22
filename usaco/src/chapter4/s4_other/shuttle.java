
import java.util.*;
import java.io.*;

/*
ID: mihirsh1
TASK: shuttle
LANG: JAVA
*/

public class shuttle {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("shuttle.in"));
        PrintWriter out = new PrintWriter("shuttle.out");

        int N = f.nextInt();
        f.close();
        
        ArrayList<Integer>[] startLists = new ArrayList[N+1];
        ArrayList<Integer>[] endLists = new ArrayList[N+1];
        ArrayList<Integer>[] finalLists = new ArrayList[N+1];
        
        startLists[0] = new ArrayList<>();
        endLists[0] = new ArrayList<>();
        finalLists[0] = new ArrayList<>();
        
        for(int i = 1; i<N+1; ++i) {
            startLists[i] = new ArrayList<>();
            endLists[i] = new ArrayList<>();
            finalLists[i] = new ArrayList<>();
            
            for(int j = 0; j<startLists[i-1].size(); ++j) {
                startLists[i].add(1+startLists[i-1].get(j));
                finalLists[i].add(1+startLists[i-1].get(j));
            }
            if(i%2 == 0) {
                for(int j = 2*i+1; j>0; j-=2) {
                    startLists[i].add(j);
                    endLists[i].add(j);
                    finalLists[i].add(j);
                }
            } else {
                for(int j = 1; j<2*i+2; j+=2) {
                    startLists[i].add(j);
                    endLists[i].add(j);
                    finalLists[i].add(j);
                }
            }
            for(int j = 0; j<endLists[i-1].size(); ++j) {
                endLists[i].add(1+endLists[i-1].get(j));
                finalLists[i].add(1+endLists[i-1].get(j));
            }
            finalLists[i].add(N+1);
        }
        
        StringBuilder sb = new StringBuilder();
        int ctr = 0;
        ArrayList<Integer> ref = finalLists[N];
        while(ctr < ref.size()) {
            sb.append(ref.get(ctr));
            sb.append(' ');
            ++ctr;
            if(ctr%20 == 0) {
                sb.deleteCharAt(sb.length()-1);
                sb.append('\n');
            }
        }
        if(ctr != 0) {
            sb.deleteCharAt(sb.length()-1);
        }
        out.println(sb.toString());
        out.close();
    }
}
