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
 TASK: ariprog

 */
public class ariprog {

    public static PrintWriter out;
    public static ArrayList<int[]> solutions = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\ariprogOUT.txt"
        
        out = new PrintWriter(new BufferedWriter(new FileWriter("ariprog.out")));
        BufferedReader f = new BufferedReader(new FileReader("ariprog.in"));

        int lenProg = Integer.parseInt(f.readLine());
        int uppBound = Integer.parseInt(f.readLine());
        
        boolean[] isBisquare = new boolean[2 * uppBound * uppBound + 1];
        
        for (int i = 0; i <= uppBound; i++) {
            for (int j = i; j <= uppBound; j++) {
                isBisquare[i*i + j*j] = true;
            }
        }

        boolean thingy = true;

        if(uppBound > 84)
            for (int i = 0; i <= isBisquare.length - lenProg; i++) {
                for (int j = i + 84; j <= isBisquare.length - lenProg + 1; j+=84) {
                    thingy = true;

                    if (isBisquare[i] && isBisquare[j]) {
                        int[] sequence = new int[lenProg];

                        sequence[0] = i;
                        sequence[1] = j;

                        int ratio = j - i;
                        int k = j + ratio;

                        for (int m = 0; m < lenProg - 2; m++) {

                            if (k + m*ratio > isBisquare.length - 1 || !isBisquare[k + m*ratio])
                            {
                                thingy = false;
                                break;
                            }

                        }

                        if (thingy) {
                            int[] solution = new int[2];
                            solution[0] = sequence[0];
                            solution[1] = sequence[1] - sequence[0];
                            solutions.add(solution);
                        }
                    }
                }
            }
        else if (uppBound > 4)
            for (int i = 0; i <= isBisquare.length - lenProg; i++) {
                for (int j = i + 4; j <= isBisquare.length - lenProg + 1; j+=4) {
                    thingy = true;

                    if (isBisquare[i] && isBisquare[j]) {
                        int[] sequence = new int[lenProg];

                        sequence[0] = i;
                        sequence[1] = j;

                        int ratio = j - i;
                        int k = j + ratio;

                        for (int m = 0; m < lenProg - 2; m++) {

                            if (k + m*ratio > isBisquare.length - 1 || !isBisquare[k + m*ratio])
                            {
                                thingy = false;
                                break;
                            }

                        }

                        if (thingy) {
                            int[] solution = new int[2];
                            solution[0] = sequence[0];
                            solution[1] = sequence[1] - sequence[0];
                            solutions.add(solution);
                        }
                    }
                }
            }
        else 
            for (int i = 0; i <= isBisquare.length - lenProg; i++) {
                for (int j = i + 1; j <= isBisquare.length - lenProg + 1; j++) {
                    thingy = true;

                    if (isBisquare[i] && isBisquare[j]) {
                        int[] sequence = new int[lenProg];

                        sequence[0] = i;
                        sequence[1] = j;

                        int ratio = j - i;
                        int k = j + ratio;

                        for (int m = 0; m < lenProg - 2; m++) {

                            if (k + m*ratio > isBisquare.length - 1 || !isBisquare[k + m*ratio])
                            {
                                thingy = false;
                                break;
                            }

                        }

                        if (thingy) {
                            int[] solution = new int[2];
                            solution[0] = sequence[0];
                            solution[1] = sequence[1] - sequence[0];
                            solutions.add(solution);
                        }
                    }
                }
            }
        
        for (int i = 0; i < solutions.size(); i++) {
            for (int j = i + 1; j < solutions.size(); j++) {
                if (solutions.get(i)[1] > solutions.get(j)[1]) {
                    int[] temp = solutions.get(i);
                    solutions.set(i, solutions.get(j));
                    solutions.set(j, temp);
                } else if (solutions.get(i)[1] == solutions.get(j)[1] && solutions.get(i)[0] > solutions.get(j)[0]) {
                    int[] temp = solutions.get(i);
                    solutions.set(i, solutions.get(j));
                    solutions.set(j, temp);
                }
            }
        }
        
        for (int[] x : solutions) {
            out.println(x[0] + " " + x[1]);
        }

        if (solutions.size() == 0) {
            out.println("NONE");
        }

        out.close();
    }
}
