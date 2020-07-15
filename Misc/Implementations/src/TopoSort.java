import java.util.*; 
  
class Graph { 
    int V; // No. of vertices 
  
    List<Integer> adjListArray[]; 
  
    public Graph(int V) { 
  
        this.V = V; 
  
        @SuppressWarnings("unchecked") 
        List<Integer> adjListArray[] = new LinkedList[V]; 
  
        this.adjListArray = adjListArray; 
  
        for (int i = 0; i < V; i++) { 
            adjListArray[i] = new LinkedList<>(); 
        } 
    } 
    // Utility function to add edge 
    public void addEdge(int src, int dest) { 
  
        this.adjListArray[src].add(dest); 
  
    } 
      
    // Main recursive function to print all possible 
    // topological sorts 
    private void allTopologicalSortsUtil(boolean[] visited,  
                        int[] indegree, ArrayList<Integer> stack) { 
        // To indicate whether all topological are found 
        // or not 
        boolean flag = false; 
  
        for (int i = 0; i < this.V; i++) { 
            // If indegree is 0 and not yet visited then 
            // only choose that vertex 
            if (!visited[i] && indegree[i] == 0) { 
                  
                // including in result 
                visited[i] = true; 
                stack.add(i); 
                for (int adjacent : this.adjListArray[i]) { 
                    indegree[adjacent]--; 
                } 
                allTopologicalSortsUtil(visited, indegree, stack); 
                  
                // resetting visited, res and indegree for 
                // backtracking 
                visited[i] = false; 
                stack.remove(stack.size() - 1); 
                for (int adjacent : this.adjListArray[i]) { 
                    indegree[adjacent]++; 
                } 
  
                flag = true; 
            } 
        } 
        // We reach here if all vertices are visited. 
        // So we print the solution here 
        if (!flag) { 
            stack.forEach(i -> System.out.print(i + " ")); 
            System.out.println(); 
        } 
  
    } 
      
    // The function does all Topological Sort. 
    // It uses recursive alltopologicalSortUtil() 
    public void allTopologicalSorts() { 
        // Mark all the vertices as not visited 
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
      
    // Driver code 
    public static void main(String[] args) { 
  
        // Create a graph given in the above diagram 
        Graph graph = new Graph(4); 
        graph.addEdge(1,2);
        graph.addEdge(2,3);
        graph.addEdge(2,0);
  
        System.out.println("All Topological sorts"); 
        graph.allTopologicalSorts(); 
    } 
}