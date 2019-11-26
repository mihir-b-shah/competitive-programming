
import java.util.*;
import java.io.*;

/*
ID: mihirsh1
TASK: frameup
LANG: JAVA
*/

public class frameup {
    
    public static final int INF = 1_000_000_000;
    public static byte[][] matrix;
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("frameup.in"));
        PrintWriter out = new PrintWriter("frameup.out");
        
        final int H,W;
        H = f.nextInt();
        W = f.nextInt();
        f.nextLine();
        
        char[][] board = new char[H][W];
        char maxchar = 0;
        
        for(int i = 0; i<H; ++i) {
            board[i] = f.nextLine().toCharArray();
            for(int j = 0; j<W; ++j) {
                maxchar = (char) Math.max(maxchar, board[i][j]);
            }
        }
        
        // 0 is minX, 1 is minY, 2 is maxX, 3 is maxY
        final int numframes = maxchar-'A'+1;
        int[][] maxmins = new int[numframes][4];
        for(int i = 0; i<numframes; ++i) {
            maxmins[i][0] = INF;
            maxmins[i][1] = INF;
        }
        
        for(int i = 0; i<H; ++i) {
            for(int j = 0; j<W; ++j) {
                if(board[i][j] != '.') {
                    int[] arr = maxmins[board[i][j]-'A'];
                    arr[0] = Math.min(arr[0], j); // width
                    arr[1] = Math.min(arr[1], i); // height
                    arr[2] = Math.max(arr[2], j);
                    arr[3] = Math.max(arr[3], i);
                }
            }
        }

        matrix = new byte[numframes][numframes];

        for(int i = 0; i<H; ++i) {
            for(int j = 0; j<W; ++j) {
                int index = board[i][j]-'A';
                for(int k = 0; k<numframes; ++k) {
                    int[] mm = maxmins[k];
                    if((j == mm[0] || j == mm[2]) && i >= mm[1] && i <= mm[3] ||
                       (i == mm[1] || i == mm[3]) && j >= mm[0] && j <= mm[2]) {
                        matrix[k][index] = 1;
                        matrix[index][k] = 2;
                    }
                }
            }
        }
        TreeMap<Byte, Integer> sort = new TreeMap<>();
        for(byte[] b: matrix) {
            System.out.println(Arrays.toString(b));
        }
        
        
        for(int i = 0; i<numframes; ++i) {
            byte ctr = 0;
            for(int j = 0; j<numframes; ++j) {
                if(matrix[i][j] == 0) {
                    fix(i,j);
                }
                if(matrix[i][j] == 2) {
                    ++ctr;
                }
            }
            sort.put(ctr,i);
        }
        
        for(byte[] b: matrix) {
            System.out.println(Arrays.toString(b));
        }
        
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<Byte, Integer>> set = sort.entrySet();
        set.forEach((entry) -> {
            sb.append((char) (entry.getValue()+'A'));
        });
        String print = sb.toString();
        for(int i = 0; i<matrix.length-1; ++i) {
            for(int j = i+1; j<matrix.length; ++j) {
                if(matrix[i][j] == 0)
                    System.out.printf("%c->%c%n", 'A'+i, 'A'+j);
            }
        }

        out.println(print);
        out.flush();
        out.close();
        f.close();
    }
    
    /*
     * Determines whether i > j.
     */
    public static byte fix(int i, int j) {
        System.out.printf("%d,%d%n", i, j);
        if(matrix[i][j] != 0) {
            return matrix[i][j];
        } else {
            byte b;
            for(int k = 0; k<matrix.length; ++k) {
                if(k != i && k != j && (b = fix(i,k)) == fix(k,j)) {
                    matrix[i][j] = b;
                    return b;
                }
            }
        }
        return 0;
    }
}
