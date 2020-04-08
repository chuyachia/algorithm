package math;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PowerSet<T> {
    private final List<T> originalSet;
    private final Set<Set<T>> powerSet;
    private final boolean solved;

    public PowerSet(List<T> originalSet) {
        this.originalSet = originalSet;
        this.powerSet = new HashSet<>();
        this.solved = false;
    }

    public Set<Set<T>> getPowerSet() {
        if (!solved) {
            int n = originalSet.size();

            for (int i = 0; i < Math.pow(2, n); i++) {
                Set<T> set = new HashSet<>();
                for (int b = 0; b < n; b++) {
                    if ((i & (1 << b)) != 0) {
                        set.add(originalSet.get(b));
                    }
                }
                powerSet.add(set);
            }
        }

        return powerSet;
    }

    public static void main(String[] args) {
        List<String> set = new ArrayList<>();
        set.add("A");
        set.add("B");
        set.add("C");
        set.add("D");

        PowerSet<String> powerSet = new PowerSet<>(set);
        Set<Set<String>> ps = powerSet.getPowerSet();
        for (Set<String> p : ps) {
            for (String s : p) {
                System.out.print(s);
            }
            System.out.println();
        }
    }
}
