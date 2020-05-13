package fx.multiset;
import java.util.*;
public class MultisetUtil {
    public static Multiset<Integer> stringToMultiSet(String[] values) {
        Multiset<Integer> integers = new HashMultiset<>();
        for (String value : values) {
            
            integers.add(Integer.valueOf(value));
        }
        return integers;
    }

    public static void printMultiSet(Multiset<Integer> integers) {
        Set<Integer> set = integers.toSet();
        for (Integer val : set) {
            for(int i = 0; i < integers.getMultiplicity(val); i++) {
                
                System.out.print(val +" ");
            }
        }
    }
   
}