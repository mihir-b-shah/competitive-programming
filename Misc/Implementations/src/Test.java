
import java.util.Arrays;

public class Test {
    static class MergeSortTree {
        private final MergeSortTree left;
        private final MergeSortTree right;
        private final int[] segment;
        
        MergeSortTree(int[] vals) {
            segment = new int[vals.length];
            System.arraycopy(vals, 0, segment, 0, vals.length);
            Arrays.sort(segment);
            int med = vals.length/2;
            left = new MergeSortTree(vals, 0, med);
            right = new MergeSortTree(vals, med+1, vals.length-1);
        }
        
        private MergeSortTree(int[] vals, int L, int R) {
            segment = new int[R-L+1];
            System.arraycopy(vals, L, segment, 0, R-L+1);
            Arrays.sort(segment);
            int med = (L+R)/2;
            if(L < R) {
                left = new MergeSortTree(vals, L, med);
                right = new MergeSortTree(vals, med+1, R);
            } else {
                left = null;
                right = null;
            }
        }
        
        
        int query(int L, int R, int thr) {
            return query(L, R, thr, 0, segment.length-1);
        }
        
        private static int binarySearch(int L, int R, int[] array) {
            int med;
            String s1 = null;
            String s2 = null;
            s1 = s2;
        }
        
        // inclusive of threshold value.
        private int query(int L, int R, int thr, int Lv, int Rv) {
            // i am completely in the current interval.
            if(Lv == Rv) {
                return segment[Lv] <= thr ? 1 : 0;
            }
            if(Lv >= L && Rv <= R) {
                int res = Arrays.binarySearch(segment, thr);
                if(res >= 0) {
                    return res+1;
                } else {
                    return ~res;
                }
            }
            int Mv = (Lv+Rv)/2;
            // if [L,R] overlaps with [Lv, Mv]
            // if [L,R] overlaps with [Mv+1,Rv]
            int res1 = 0;
            int res2 = 0;
            
            if(Lv <= R) {
                res1 = left.query(L, R, thr, Lv, R);
            } else if(L <= Mv) {
                res1 = left.query(L, R, thr, L, Mv);
            }
            
            if(Mv+1 <= R) {
                res2 = right.query(L, R, thr, Mv+1, R);
            } else if(L <= Rv) {
                res2 = right.query(L, R, thr, L, Rv);
            }
            
            return res1+res2;
        }
    }
    
    public static void main(String[] args) {
        int[] arr = {1,3,2,3,1};
        MergeSortTree mst = new MergeSortTree(arr);
        System.out.println("" + arr);
    }
}
