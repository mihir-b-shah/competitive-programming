package chapter4.s3_bignums;

import java.util.*;
import java.io.*;

/*
ID: mihirsh1
TASK: race3
LANG: JAVA
 */
public class race3 {

    public static class Node {

        public long mem;
        public int id;

        public Node(int id) {
            this.id = id;
            mem = 1L;
        }

        public Node(int id, Node pr) {
            this.id = id;
            mem = (1 << id) + pr.mem;
        }

        public boolean det(int i) {
            return (mem & 1 << i) != 0;
        }
    }

    public static boolean cycle(int start, ArrayList<ArrayList<Integer>> vect) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        long mem = 0L;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            mem += (1L << node);

            ArrayList<Integer> next = vect.get(node);
            for (int i : next) {
                if (i == start) {
                    return true;
                }
                if ((mem & 1L << i) == 0) {
                    queue.offer(i);
                }
            }
        }

        return false;
    }

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("race3.in"));
        PrintWriter out = new PrintWriter("race3.out");

        ArrayList<ArrayList<Integer>> vect = new ArrayList<>();
        outer:
        while (true) {
            vect.add(new ArrayList<>());
            inner:
            while (true) {
                int v = f.nextInt();
                switch (v) {
                    case -2:
                        break inner;
                    case -1:
                        break outer;
                    default:
                        vect.get(vect.size() - 1).add(v);
                }
            }
        }
        vect.remove(vect.size() - 1);

        Queue<Node> q = new ArrayDeque<>();
        q.add(new Node(0));
        int d = vect.size() - 1;
        long agr = (1 << vect.size()) - 1;

        while (!q.isEmpty()) {
            Node n = q.poll();
            if (n.id == d) {
                agr &= n.mem;
            }

            ArrayList<Integer> next = vect.get(n.id);
            for (int i : next) {
                if (!n.det(i)) {
                    q.add(new Node(i, n));
                }
            }
        }

        ArrayList<Integer> bridge = new ArrayList<>();

        StringBuilder split = new StringBuilder();
        StringBuilder out1 = new StringBuilder();

        int ctr = 0;
        int ctr_sp = 0;

        for (int i = 1; i < vect.size() - 1; i++) {
            if ((agr & 1 << i) != 0) {
                bridge.add(i);
                out1.append(i);
                out1.append(' ');
                ++ctr;
            }
        }

        // does a cycle in the graph exists back to this point?
        // naive: do a bfs from here 
        for (int node : bridge) {
            if (!cycle(node, vect)) {
                split.append(node);
                split.append(' ');
                ++ctr_sp;
            }
        }

        if (ctr > 0) {
            out1.deleteCharAt(out1.length() - 1);
            String line1 = String.format("%d %s", ctr, out1.toString());
            out.println(line1);
        } else {
            out.println(ctr);
        }

        if (ctr_sp > 0) {
            split.deleteCharAt(split.length() - 1);
            String line2 = String.format("%d %s", ctr_sp, split.toString());
            out.println(line2);
        } else {
            out.println(ctr_sp);
        }

        out.close();
        f.close();
    }
}
