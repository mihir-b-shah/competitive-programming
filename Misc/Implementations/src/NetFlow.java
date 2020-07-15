
import java.util.*;

public class NetFlow {
    
    static LinkList<Edge>[] graph;
    static LinkList<Edge>[] residual;
    
    static final int INF = 0x7f7f7f7f;
  
    static class Edge {
        int index;
        int src;
        int dest;
        int capacity;
        Edge pair;
        Node contain;
        
        Edge(int s, int d, int c, Edge pair) {
            src = s;
            dest = d;
            capacity = c;
            this.pair = pair;
        }
        
        Edge(int i, int s, int d, int c) {
            this(s, d, c, null);
            index = i;
        }
        
        Edge(int d, int c) {
            this(-1, d, c, null);
        }

        Edge(Edge edge) {
            src = edge.src;
            dest = edge.dest;
            capacity = edge.capacity;
            pair = edge.pair;
            contain = edge.contain;
        }
        
        Edge genReverse() {
            return new Edge(dest, src, 0, this);
        }
        
        long hash() {
            return ((long) src << 32)+dest;
        }
        
        long revHash() {
            return ((long) dest << 32)+src;
        }
        
        @Override
        public String toString() {
            return String.format("<dest=%d, capacity=%d>", dest+1, capacity);
        }
    }
    
    static class EdgeWrap {
        Edge edge;
        EdgeWrap trace;
        int flow;
        
        EdgeWrap(Edge par, EdgeWrap tr) {
            edge = par;
            trace = tr;
            if(tr != null) {
                flow = Math.min(tr.flow, par.capacity);
            } else {
                flow = INF;
            }
        }
    }

    NetFlow(int V, LinkList<Edge> edges) {
        HashMap<Long, Edge> set = new HashMap<>();
        ListIterator<Edge> iterator = edges.listIterator();
        int vertexName = V;
        while(iterator.hasNext()) {
            Edge edge = iterator.next();
            long hash = edge.hash();
            long revHash = edge.revHash();
            if(set.containsKey(hash)) {
                set.get(hash).capacity += edge.capacity; 
            } else if(!set.containsKey(hash) && set.containsKey(revHash)) {
                Edge aux1 = new Edge(edge.index, edge.src, vertexName, edge.capacity);
                Edge aux2 = new Edge(edge.index, vertexName++, edge.dest, edge.capacity);
                iterator.remove();
                iterator.add(aux1);
                iterator.add(aux2);
            } else if(!set.containsKey(hash) && !set.containsKey(revHash)) {
                set.put(hash, edge);
            }
        }
        graph = new LinkList[vertexName];
        for(int i = 0; i<vertexName; ++i) {
            graph[i] = new LinkList<>();
        }
        for(Edge edge: edges) {
            graph[edge.src].add(edge);
        }
    }

    // caches are crying
    int maxFlow(boolean term, int source, int sink) {
        residual = new LinkList[graph.length];
        for(int i = 0; i<residual.length; ++i) {
            residual[i] = new LinkList<>();
            LinkList<Edge> edgeList = graph[i];
            for(Edge e: edgeList) {
                Edge copy = new Edge(e);
                Node insertPtr = residual[i].add(copy);
                copy.contain = insertPtr;
            }
        }
        
        Queue<EdgeWrap> queue = new ArrayDeque<>();
        int[] visited = new int[residual.length];
        
        int iter = 0;
        int maxFlow = 0;
        boolean flag,relaxed;
        
        do {
            queue.offer(new EdgeWrap(new Edge(source, INF), null));
            flag = false;
            relaxed = false;
            while(!queue.isEmpty()) {
                EdgeWrap edgeWrap = queue.poll();
                if(visited[edgeWrap.edge.dest] != iter) {
                    continue;
                }
                ++visited[edgeWrap.edge.dest];
                if(!relaxed && edgeWrap.edge.dest == sink) {
                    // found an augmenting path!
                    flag = true;
                    relaxed = true;
                    EdgeWrap aux = edgeWrap;
                    int flow = aux.flow;
                    maxFlow += flow;
                    while(aux.trace != null) {
                        Edge edge = aux.edge;
                        if(edge.pair == null) {
                            Edge newEdge = edge.genReverse();
                            Node insertPtr = residual[newEdge.src].add(newEdge);
                            newEdge.contain = insertPtr;
                            edge.pair = newEdge;
                        }
                        edge.capacity -= flow;
                        edge.pair.capacity += flow;
                        if(edge.capacity == 0) {
                            residual[edge.src].remove(edge.contain);
                        }
                        aux = aux.trace;
                    }
                }
                LinkList<Edge> next = residual[edgeWrap.edge.dest];
                for(Edge e: next) {
                    if(visited[e.dest] == iter) {
                        queue.offer(new EdgeWrap(e, edgeWrap));
                    }
                }
            }
            ++iter;
        } while(flag);
        return maxFlow;
    }
    
    ArrayList<Integer> findMinCut(int source) {
        boolean[] v = new boolean[residual.length];
        ArrayList<Integer> S = new ArrayList<>();
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(source);
        while(!queue.isEmpty()) {
            int next = queue.poll();
            S.add(next);
            if(v[next]) {
                continue;
            }
            v[next] = true;
            
            LinkList<Edge> neighbors = residual[next];
            for(Edge edge: neighbors) {
                if(!v[edge.dest]) {
                    queue.offer(edge.dest);
                }
            }
        }
        
        ArrayList<Integer> cuts = new ArrayList<>();
        for(int vertex: S) {
            LinkList<Edge> conn = graph[vertex];
            for(Edge edge: conn) {
                if(!v[edge.dest]) {
                    cuts.add(edge.index);
                }
            }
        }
        return cuts;
    }
    
    public static void main(String[] args) {
        Scanner f = new Scanner(System.in);
        int V = f.nextInt();
        int E = f.nextInt();
        
        LinkList<Edge> edges = new LinkList<>();
        for(int i = 0; i<E; ++i) {
            edges.add(new Edge(i+1, f.nextInt()-1, f.nextInt()-1, f.nextInt()));
        }
        NetFlow netFlow = new NetFlow(V, edges);
        int flow = netFlow.maxFlow(false, 0, V-1);
        ArrayList<Integer> cut = netFlow.findMinCut(0);
        Collections.sort(cut);
        
        StringBuilder out = new StringBuilder();
        out.append(flow);
        out.append(' ');
        out.append(cut.size());
        out.append('\n');
        for(int cutEdge: cut) {
            out.append(cutEdge);
            out.append(' ');
        }
        if(out.length() > 0) {
            out.deleteCharAt(out.length()-1);
        }
        out.append('\n');
        System.out.print(out.toString());
    }
}
