package sort;

import java.util.Arrays;

public class MergeSort {
    private final int[] sorted;

    public MergeSort(int[] input) {
        this.sorted = Arrays.copyOf(input, input.length);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{4, 1, 6, 20, 4, 0, 12, 0, 88, 87, 12, -1, 112, 54};
        MergeSort mergeSort = new MergeSort(arr);
        int[] sorted = mergeSort.sort();

        for (int i : sorted) {
            System.out.println(i);
        }
    }

    public int[] sort() {
        sortRecursive(0, sorted.length);
//        sortIterative();
        return sorted;
    }

    private int[] sortRecursive(int from, int to) {
        if (to - from == 1) {
            return new int[]{from, to};
        } else {
            int mid = midPoint(from, to);
            int[] arrA = sortRecursive(from, mid);
            int[] arrB = sortRecursive(mid, to);
            return merge(arrA[0], arrA[1], arrB[0], arrB[1]);
        }
    }

    private void sortIterative() {
        for (int i = 1; i < sorted.length; i *= 2) {
            int j = 0;
            while (j + i < sorted.length) {
                int endIndex = Math.min(sorted.length, j + i * 2);
                merge(j, j + i, j + i, endIndex);
                j = endIndex;
            }
        }
    }

    private int midPoint(int from, int to) {
        return (int) Math.ceil((from + to) / 2);
    }

    private int[] merge(int arrAFrom, int arrATo, int arrBFrom, int arrBTo) {
        int i = arrAFrom;
        int[] arrA = Arrays.copyOfRange(sorted, arrAFrom, arrATo);
        int[] arrB = Arrays.copyOfRange(sorted, arrBFrom, arrBTo);
        int a = 0;
        int b = 0;

        while (a < arrA.length && b < arrB.length) {
            if (arrA[a] < arrB[b]) {
                sorted[i] = arrA[a];
                a++;
            } else {
                sorted[i] = arrB[b];
                b++;
            }
            i++;
        }

        while (a < arrA.length) {
            sorted[i] = arrA[a];
            a++;
            i++;
        }

        while (b < arrB.length) {
            sorted[i] = arrB[b];
            b++;
            i++;
        }

        return new int[]{arrAFrom, arrBTo};
    }
}