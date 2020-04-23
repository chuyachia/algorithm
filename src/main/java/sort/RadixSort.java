package sort;

import java.util.Arrays;

public class RadixSort {
    private int[] sorted;
    private boolean hasDigit;

    public RadixSort(int[] input) {
        this.sorted = Arrays.copyOf(input, input.length);
        this.hasDigit = true;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{170, 45, 75, 90, 802, 24, 2, 66};
        RadixSort radixSort = new RadixSort(arr);
        int[] sorted = radixSort.sort();
        for (int i : sorted) {
            System.out.println(i);
        }
    }

    public int[] sort() {
        int digit = 1;
        while (hasDigit) {
            countSort(digit);
            digit++;
        }

        return sorted;
    }

    private void countSort(int digit) {
        hasDigit = false;
        int[] count = new int[10];
        int[] numberAtDigit = new int[sorted.length];
        for (int i = 0; i < sorted.length; i++) {
            int number = sorted[i];
            if (!hasDigit) {
                hasDigit = number / (int) Math.pow(10, digit) > 0;
            }
            int d = getNumberAtDigit(number, digit);
            numberAtDigit[i] = d;
            count[d] += 1;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        int[] newSorted = new int[sorted.length];
        for (int i = sorted.length - 1; i >= 0; i--) {
            int number = sorted[i];
            int d = numberAtDigit[i];
            int index = count[d] - 1;
            count[d]--;
            newSorted[index] = number;
        }

        sorted = newSorted;
    }

    private int getNumberAtDigit(int number, int digit) {
        return number % (int) Math.pow(10, digit) / (int) Math.pow(10, digit - 1);
    }
}
