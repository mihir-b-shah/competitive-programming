
import java.util.Scanner;
import java.util.Arrays;

public class HIndex {
    static class SegTree {
        int[] tree;
        int N;
        
        private int nextPow2(int n){
            n -= 1;
            n = n | (n >> 1);
            n = n | (n >> 2);
            n = n | (n >> 4);
            n = n | (n >> 8);
            n = n | (n >> 16);
            return n+1;
        }
        SegTree(int N) {
            this.N = N;
            tree = new int[2*nextPow2(N)];
        } 
        
        // low, up are inclusive
        void update(int v){
            update(0, 1, N, v);
        }
        
        void update(int idx, int l, int u, int v){
            if(l == u){
                ++tree[idx];
                return;
            }
            int med = (l+u)>>>1;
            if(v > med){
                update(2*idx+2, med+1, u, v);
            } else {
                update(2*idx+1, l, med, v);
            }
            ++tree[idx];
        }
        
        int query(int thr){
            return 0;
        }
        
        void init(){
            Arrays.fill(tree, 0);
        }
    }
    
    public static void main(String[] args){
        Scanner f = new Scanner(System.in);
        
        int T = f.nextInt();
        for(int i = 0; i<T; ++i){
            int N = f.nextInt();
            SegTree tree = new SegTree(7);
            for(int j = 0; j<N; ++j) {
                int v = f.nextInt();
                tree.update(v);
                System.out.println(Arrays.toString(tree.tree));
            }
        }
        
        f.close();
    }
}