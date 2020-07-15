
import java.util.*;

public class L44 {
    
    class Pair {
        final int s0;
        final int p0;
        
        Pair(int s0, int p0) {
            this.s0 = s0;
            this.p0 = p0;
        }
        
        @Override
        public int hashCode(){
            return s0^p0;
        }
        
        @Override
        public boolean equals(Object o){
            if(o instanceof Pair) {
                Pair p = (Pair) o;
                return p.s0 == s0 && p.p0 == p0;
            } return false;
        }
        
        @Override
        public String toString() {
            return String.format("(%d,%d)",s0,p0);
        }
    }
    
    public HashMap<Pair,Boolean> set;
    
    public boolean isMatch(String s, int s0, String p, int p0) {
        boolean retVal = false;
        if(s0 == s.length() && p0 == p.length()){
            retVal = true;
        }
        if(!retVal) {
            if(s0 == s.length() && p0 < p.length()) {
                while(p0 < p.length() && p.charAt(p0) == '*') {
                    ++p0;
                }
                retVal = p0 == p.length();
            }
            if(!retVal) {
                if(s0 >= s.length() || p0 >= p.length()) {
                    retVal = true;
                }
                if(!retVal) {
                    switch(p.charAt(p0)){
                        case '?':
                            retVal = isMatch(s, s0+1, p, p0+1);
                            break;
                        case '*':
                            for(int i = s.length(); i>=s0; --i){
                                boolean res = isMatch(s, i, p, p0+1);
                                if(res) {
                                    retVal = res;
                                    break;
                                } else {
                                    continue;
                                }
                            }
                            break;
                        default:
                            if(s.charAt(s0) == p.charAt(p0)) {
                                retVal = isMatch(s, s0+1, p, p0+1);
                            }
                            break;
                    }
                }
            }
        }
        Pair pair = new Pair(s0,p0);
        set.put(pair, retVal);
        return retVal;
    }
    
    public boolean isMatch(String s, String p) {
        set = new HashMap<>();
        return isMatch(s, 0, p, 0);
    }
    
    public static void main(String[] args) {
        L44 s = new L44();
        String str = "bbbbabbbaabaaaaaaabbabbaaaaabbaaabaabbbbbbbabbaaabbabaababbbabaabababababaababbaaaababbaaabaababbbbaaabbabaaababbbababbaabaaaabaaaaaabbababbbbababaaaaaabaabbbbbbabbbaaaabaaaabbbbbabaaaabababaabbbbabba";
        String pat = "*bababa*b*ba*abab****a**b**baa****a*aabb**b****ab**a***a*bb*b*b**bb*b**b*aab**babaaa**ab*babbba***baba";
        
        //String str = "abcdecdegf";
        //String pat = "ab*c*de*f";
        
        System.out.println("Strlen: " + str.length());
        System.out.println("Patlen: " + pat.length());
        
        System.out.println(s.isMatch(str, pat));
        HashMap<Pair,Boolean> set = s.set;
        
        for(Map.Entry<Pair, Boolean> entry: set.entrySet()) {
            System.out.println(entry);
        }
    }
}