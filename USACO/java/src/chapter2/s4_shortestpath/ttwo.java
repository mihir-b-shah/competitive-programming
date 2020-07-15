package chapter2.s4_shortestpath;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class ttwo {
    
    public static final int boardSize = 10;
    public static int[][] board;
    public static final int lineSep = 2;
    public static int Fi;
    public static int Fj;
    public static int Ci;
    public static int Cj;
    public static int Fdir;
    public static int Cdir;
    public static int counter;
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader f = new BufferedReader(new FileReader("ttwo.in"));
        PrintWriter out = new PrintWriter("ttwo.out");
        
        board = new int[boardSize][boardSize];
        boolean[][] Cvis = new boolean[boardSize][boardSize];
        
        for(int i = 0; i<boardSize; i++) {
            for(int j = 0; j<boardSize; j++) {
                board[i][j] = f.read();
                
                if(board[i][j] == 70) {
                    Fi = i;
                    Fj = j;
                } else if(board[i][j] == 67) {
                    Ci = i;
                    Cj = j;
                }
                
            }
            
            f.skip(lineSep);
        }
        
        meet();
        
        out.flush();
        out.close();
        f.close();
    } 
    
    public static void meet() {
        
        if(Fi != Ci || Fj != Cj) {
            
            if(Cdir == 0) {
                if(Ci == 0) {
                    Cdir++;
                    Cdir %= 4;
                } else {
                    Ci--;
                    
                }
            } else if(Cdir == 1) {
                if(Cj == boardSize - 1) {
                    Cdir++;
                    Cdir %= 4;
                } else {
                    Cj++;
                    
                }
            } else if(Cdir == 2) { 
                if(Ci == boardSize - 1) {
                    Cdir++;
                    Cdir %= 4;
                } else {
                    Ci++;
                    
                }
            } else {
                if(Cj == 0) {
                    Cdir++;
                    Cdir %= 4;
                } else {
                    Cj--;
                    
                }
            }
            
            if(Fdir == 0) {
                if(Fi == 0) {
                    Fdir++;
                    Fdir %= 4;
                } else {
                    Fi--;
                    
                }
            } else if(Fdir == 1) {
                if(Fj == boardSize - 1) {
                    Fdir++;
                    Fdir %= 4;
                } else {
                    Fj++;
                    
                }
            } else if(Fdir == 2) { 
                if(Fi == boardSize - 1) {
                    Fdir++;
                    Fdir %= 4;
                } else {
                    Fi++;
                    
                }
            } else {
                if(Fj == 0) {
                    Fdir++;
                    Fdir %= 4;
                } else {
                    Fj--;
                    
                }
            }
            
            counter++;  
            meet();
        }        
    }
}
