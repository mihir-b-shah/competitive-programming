
package chapter4.s4_other;

import java.util.*;
import java.io.*;

/*
ID: mihirsh1
TASK: frameup
LANG: JAVA
*/

public class frameup {
    
    public static final int INF = 1_000_000_000;
    public static ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
    
    // geeksforgeeks implementation for toposort
    static class Graph { 
        int V;
        List<Integer> adjListArray[]; 
  
        public Graph(int V) { 
            this.V = V; 
            List<Integer> adjListArray[] = new LinkedList[V]; 
            this.adjListArray = adjListArray; 
            for (int i = 0; i < V; i++) { 
                adjListArray[i] = new LinkedList<>(); 
            } 
        } 

        @Override
        public String toString() {
            return Arrays.toString(adjListArray);
        }
        
        public void addEdge(int src, int dest) { 
            this.adjListArray[src].add(dest); 
        } 

        private void allTopologicalSortsUtil(boolean[] visited,  
                            int[] indegree, ArrayList<Integer> stack) { 
            boolean flag = false; 

            for (int i = 0; i<V; i++) { 
                if (!visited[i] && indegree[i] == 0) { 
                    visited[i] = true; 
                    stack.add(i); 
                    for (int adjacent : this.adjListArray[i]) { 
                        indegree[adjacent]--; 
                    } 
                    allTopologicalSortsUtil(visited, indegree, stack); 
                    visited[i] = false; 
                    stack.remove(stack.size() - 1); 
                    for (int adjacent : this.adjListArray[i]) { 
                        indegree[adjacent]++; 
                    } 

                    flag = true; 
                } 
            } 

            if (!flag) { 
                ArrayList<Integer> solution = new ArrayList();
                stack.forEach(solution::add);
                solutions.add(solution);
            } 

        } 

        public void allTopologicalSorts() { 
            boolean[] visited = new boolean[this.V]; 
            int[] indegree = new int[this.V]; 

            for (int i = 0; i < this.V; i++) { 
                for (int var : this.adjListArray[i]) { 
                    indegree[var]++; 
                } 
            } 

            ArrayList<Integer> stack = new ArrayList<>(); 
            allTopologicalSortsUtil(visited, indegree, stack); 
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("frameup.in"));
        PrintWriter out = new PrintWriter("frameup.out");

        final int H,W;
        H = f.nextInt();
        W = f.nextInt();
        f.nextLine();
        
        char[][] board = new char[H][];
        int ctr = 0;
        HashMap<Character, Integer> forward = new HashMap<>();
        HashMap<Integer, Character> backward = new HashMap<>();
        
        for(int i = 0; i<H; ++i) {
            board[i] = f.nextLine().toCharArray();
            for(int j = 0; j<W; ++j) {
                if(board[i][j] != '.' && !forward.containsKey(board[i][j])) {
                    forward.put(board[i][j], ctr++);
                    backward.put(ctr-1, board[i][j]);
                }
            }
        }
        
        // 0 is minX, 1 is minY, 2 is maxX, 3 is maxY
        int[][] maxmins = new int[forward.size()][4];
        for(int i = 0; i<forward.size(); ++i) {
            maxmins[i][0] = INF;
            maxmins[i][1] = INF;
        }
        
        for(int i = 0; i<H; ++i) {
            for(int j = 0; j<W; ++j) {
                if(board[i][j] != '.') {
                    int[] arr = maxmins[forward.get(board[i][j])];
                    arr[0] = Math.min(arr[0], j); // width
                    arr[1] = Math.min(arr[1], i); // height
                    arr[2] = Math.max(arr[2], j);
                    arr[3] = Math.max(arr[3], i);
                }
            }
        }

        Graph graph = new Graph(forward.size());
        
        byte[][] matrix = new byte[forward.size()][forward.size()];
        for(int i = 0; i<H; ++i) {
            for(int j = 0; j<W; ++j) {
                if(board[i][j] == '.')
                    continue;
                int index = forward.get(board[i][j]);
                for(int k = 0; k<maxmins.length; ++k) {
                    int[] mm = maxmins[k];
                    if((j == mm[0] || j == mm[2]) && i >= mm[1] && i <= mm[3] ||
                       (i == mm[1] || i == mm[3]) && j >= mm[0] && j <= mm[2]) {
                        if(k != index && matrix[k][index] == 0) {
                            graph.addEdge(k, index);
                            matrix[k][index] = 1;
                        }
                    }
                }
            }
        }

        graph.allTopologicalSorts();
        ArrayList<String> buffer = new ArrayList<>();
        
        for(ArrayList<Integer> solution: solutions) {
            StringBuilder sb = new StringBuilder();
            solution.forEach(i->sb.append(backward.get(i)));
            buffer.add(sb.toString());
        }
        Collections.sort(buffer);
        for(String s: buffer) {
            out.println(s);
        }
        out.flush();
        out.close();
    }
}
