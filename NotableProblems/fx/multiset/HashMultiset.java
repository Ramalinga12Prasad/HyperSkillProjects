package fx.multiset;
import java.util.*;
public class HashMultiset<E> implements Multiset<E> {

    private Map<E, Integer> map = new HashMap<>();

    @Override
    public void add(E elem) {
        // implement the method
        int count = map.getOrDefault(elem, 0);
        count++;
        map.put(elem, count);
    }

    @Override
    public void remove(E elem) {
        // implement the method
        int count = map.getOrDefault(elem, 0);
        if (count > 1) {
            map.put(elem, --count);
        } else {
            map.remove(elem);
        }

    }

    @Override
    public void union(Multiset<E> other) {
        // implement the method
        Set<E> set = other.toSet();
        for (E val : set) {
            int currentCount = map.getOrDefault(val, 0);
            int otherCount = other.getMultiplicity(val);
            int commonCount =  Math.max(currentCount, otherCount);
            map.put(val, commonCount);
        }


    }

    @Override
    public void intersect(Multiset<E> other) {
        // implement the method
        Set<E> set = other.toSet();
        for (E val : set) {
            int currentCount = map.getOrDefault(val, 0);
            int otherCount = other.getMultiplicity(val);
            int commonCount =  Math.min(currentCount, otherCount);
            if (commonCount != 0) {
                map.put(val, commonCount);
            }
        }

        Set<E> set1 = map.keySet();
        Set<E> removeSet1 = new HashSet<>();
        for (E val : set1) {
            int currentCount = map.getOrDefault(val, 0);
            int otherCount = other.getMultiplicity(val);
            int commonCount =  Math.min(currentCount, otherCount);
            if (commonCount == 0) {
                removeSet1.add(val);
            } else {
                map.put(val, commonCount);
            }

        }
        for (E val : removeSet1) {
            map.remove(val);
        }
    }

    @Override
    public int getMultiplicity(E elem) {
        // implement the method
        return map.getOrDefault(elem, 0);
    }

    @Override
    public boolean contains(E elem) {
        // implement the method
        return map.containsKey(elem);
    }

    @Override
    public int numberOfUniqueElements() {
        // implement the method
        return map.size();
    }

    @Override
    public int size() {
        int size = 0;
        Collection<Integer> multiplicities = map.values();
        for (Integer a : multiplicities) {
            size += a;
        }
        return size;
    }

    @Override
    public Set<E> toSet() {
        // Creating a new HashSet<> object helps us avoid ConcurrentModificationException.
        // It is thrown when we try to iterate over elements of Map and modify them at the same time
        return new HashSet<>(map.keySet());
    }
}
