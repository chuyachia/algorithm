package math;

import java.util.LinkedList;
import java.util.List;

public class Combination {

    public static List<String> pick(int n, int k) {
        List<String> combinations = new LinkedList<>();
        findCombination(0, k, 0, n, combinations);

        return combinations;
    }

    private static void findCombination(int at, int leftToPick, int currentCombination, int n, List<String> combinations) {
        // Range to pick from (at to n) smaller than the number left to pick
        // combination will not complete
        if ((n - at) < leftToPick) return;

        if (leftToPick == 0) {
            // Combination complete
            String binaryString = Integer.toBinaryString(currentCombination);
            while (binaryString.length() < n) {
                binaryString = "0" + binaryString;
            }
            combinations.add(binaryString);
        } else {
            for (int i = at; i < n; i++) {
                // Flip on at i
                currentCombination = currentCombination | (1 << i);
                findCombination(i + 1, leftToPick - 1, currentCombination, n, combinations);
                // Flip off at i
                currentCombination = currentCombination ^ (1 << i);
            }
        }
    }

    public static void main(String[] args) {
        List<String> combination = Combination.pick(7, 5);
        System.out.println(String.format("Total %d combinations", combination.size()));
        for (String s : combination) {
            System.out.println(s);
        }
    }
}
