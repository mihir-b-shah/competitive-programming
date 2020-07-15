
import java.util.ArrayList;
import java.util.Collections;

public class Kruskal {
    
    public static class Edge implements Comparable<Edge> {
        int src;
        int dest;
        int wt;
        
        Edge(int d, int w) {
            dest = d;
            wt = w;
        }
        
        @Override
        public int compareTo(Edge edge) {
            return wt-edge.wt;
        }
    }
    
    private static ArrayList<Edge> getEdgeList(ArrayList<ArrayList<Edge>> adjList) {
        ArrayList<Edge> edges = new ArrayList<>();
        int i = 0;
        for(ArrayList<Edge> locEdges: adjList) {
            for(Edge edge: locEdges) {
                edge.src = i;
                edges.add(edge);
            }
            ++i;
        }
        return edges;
    }
    
    public static ArrayList<ArrayList<Edge>> getMST(final ArrayList<ArrayList<Edge>> adjList) {
        ArrayList<ArrayList<Edge>> ret = new ArrayList<>();
        for(int i = 0; i<adjList.size(); ++i) {
            ret.add(new ArrayList<>());
        }
        ArrayList<Edge> edges = getEdgeList(adjList);
        Collections.sort(edges);
        UFDS ufds = new UFDS();
        for(int i = 0; i<adjList.size(); ++i) {
            ufds.make(i);
        }
        for(Edge edge: edges) {
            if(ufds.find(edge.src) != ufds.find(edge.dest)) {
                ret.get(edge.src).add(new Edge(edge.dest,edge.wt));
                ufds.union(edge.src, edge.dest);
            }
        }
        
        return ret;
    }
}
