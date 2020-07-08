
import java.util.ArrayList;

public class UFDS {
    static class Node {
        private int rank;
        private final int data;
        private Node parent;
        
        Node(int data) {
            this.data = data;
            parent = this;
        }
    }
    
    private final ArrayList<Node> nodes;

    public UFDS() {
        nodes = new ArrayList<>();
    }

    public void make(int v) {
        nodes.add(new Node(v));
    }

    public Node find(int idx) {
        Node start = nodes.get(idx);
        Node aux = start;
        while(start.parent != start) {
            start = start.parent;
        }
        // path compression
        aux.parent = start;
        return start;
    }

    public void union(int u, int v) {
        Node uTree = find(u);
        Node vTree = find(v);

        // assume that rank(uTree) > rank(vTree)
        if(uTree.rank < vTree.rank) {
            uTree.parent = vTree;
            vTree.rank = 1+uTree.rank;
        } else {
            vTree.parent = uTree;
            uTree.rank = 1+vTree.rank;
        }
    }
}