
import java.util.Arrays;
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mihir
 */
public class L56 {   
     
     public static int lowerBound(int[][] intervals, int start, int end, int key) {
        int low = start;
        int high = end-1;
        while(low < high) {
            int mid = (low + high) >>> 1;
            int val = intervals[mid][0];
            
            if(low == mid) {
                break;
            }
            
            if(val < key) {
                low = mid;
            } else {
                high = mid-1;
            }
        }
        return low+1;
    }
     
     public static void main(String []args){
        int[][] array = {{1,1},{1,4},{4,5}};
        for(int i = 0; i<10; ++i) {
            System.out.printf("Thr: %d, V=%d\n", i, lowerBound(array, 0, 3, i+1));
        }
        /*
        int[][] xs = merge(array);
        for(int[] x: xs) {
            System.out.printf("%d %d\n", x[0], x[1]);
        }*/
     }
     public static int[][] merge(int[][] intervals) {
         Comparator<int[]> comp = (a1,a2)->(a1[0]==a2[0] ? a1[1]-a2[1] : a1[0]-a2[0]);
         Arrays.sort(intervals, comp);
         int ptr = 0;
         int ctr = 0;
         while(ptr < intervals.length) {
             int find = lowerBound(intervals, ptr, intervals.length, intervals[ptr][1]+1);
             System.out.printf("Find: %d\n", find);
             if(find == 0) {
                 ++ptr;
                 ++ctr;
                 continue;
             } else if(find == intervals.length) {
                 ++ctr;
                 break;
             } else {
                 int aux = find;
                 while(aux < intervals.length && intervals[aux] == intervals[find]) {
                     ++aux;
                 }
                 find = aux;
             }
             // O(m) merging cost
             int min = 1_000_000_000; int max = 0;
             for(int i = ptr; i<find; ++i){
                 min = Math.min(min, intervals[i][0]);
                 max = Math.max(max, intervals[i][1]);
                 intervals[i][0] = -1; intervals[i][1] = -1;
             }
             intervals[ptr][0] = min; intervals[ptr][1] = max;
             ++ctr;
             ptr = find;
         }
         int[][] out = new int[ctr][2];
         int iterOut = 0;
         int iterOrig = 0;
         while(iterOut < ctr && iterOrig < intervals.length) {
             while(iterOrig < intervals.length && intervals[iterOrig][0] == -1) {
                 ++iterOrig;
             }
             if(iterOrig == intervals.length) {
                 break;
             }
             out[iterOut][0] = intervals[iterOrig][0];
             out[iterOut][1] = intervals[iterOrig][1];
             ++iterOut;
             ++iterOrig;
         }
         return out;
     }
}
