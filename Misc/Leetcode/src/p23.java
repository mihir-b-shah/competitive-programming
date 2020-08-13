
import java.util.*;

public class p23 {
   static class ListNode {
      int val;
      ListNode next;
      
      ListNode(){}
      ListNode(int val){
         this.val = val;
      }
      ListNode(int val, ListNode next) {
         this.val = val; 
         this.next = next; 
      }
   }
    
   public static ListNode mergeKLists(ListNode[] lists) {
      
   }
    
   public static void printList(ListNode node){
      while(node != null){
         System.out.printf("%d ", node.val);
         node = node.next;
      }
      System.out.println();
   }
    
   public static void main(String[] args){
      ListNode list1 = new ListNode(1, new ListNode(4, new ListNode(5, null)));
      ListNode list2 = new ListNode(1, new ListNode(3, new ListNode(4, null)));
      
      merge(list1, list2);
      printList(list1);
      printList(list2);
   }
}