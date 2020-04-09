package math;

import java.util.LinkedList;
import java.util.List;

public class Combination {
    private List<String> combinations;

    public List<String> pick(int n, int k) {
        combinations = new LinkedList<>();
        findCombination(0, k, 0, n);

        return combinations;
    }

    private void findCombination(int at, int leftToPick, int currentCombination, int n) {
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
                findCombination(i + 1, leftToPick - 1, currentCombination, n);
                // Flip off at i
                currentCombination = currentCombination ^ (1 << i);
            }
        }
    }

    public static void main(String[] args) {
        Combination combinationSolver = new Combination();
        List<String> combination = combinationSolver.pick(7, 5);
        System.out.println(String.format("Total %d combinations", combination.size()));
        for (String s : combination) {
            System.out.println(s);
        }
    }
}
