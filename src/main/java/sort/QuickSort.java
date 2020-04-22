package sort;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class QuickSort {
    private final int[] sorted;
    private final Random random;

    public QuickSort(int[] input) {
        this.sorted = Arrays.copyOf(input, input.length);
        random = new Random();
    }

    public static void main(String[] args) {
        int[] arr = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 12};
        QuickSort quickSort = new QuickSort(arr);
        int[] sorted = quickSort.sort();

        for (int i : sorted) {
            System.out.println(i);
        }
    }

    public int[] sort() {
//        sortRecursive(0, sorted.length - 1);
        sortIterative();
        return sorted;
    }

    private void sortIterative() {
        LinkedList<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, sorted.length - 1});
        while (queue.size() > 0) {
            int[] indices = queue.poll();
            if (indices[1] <= indices[0]) continue;

            int pivot = partition(indices[0], indices[1]);
            queue.add(new int[]{indices[0], pivot - 1});
            queue.add(new int[]{pivot + 1, indices[1]});
        }
    }

    private void sortRecursive(int start, int end) {
        if (end <= start) {
            return;
        }

        int pivot = partition(start, end);
        sortRecursive(start, pivot - 1);
        sortRecursive(pivot + 1, end);
    }

    private int partition(int start, int end) {
        int randomPivot = random.nextInt((end - start) + 1) + start;
        swap(randomPivot, end);
        int pivot = sorted[end];
        int pivotIndex = start;
        for (int i = start; i < end; i++) {
            if (sorted[i] < pivot) {
                swap(i, pivotIndex);
                pivotIndex++;
            }
        }

        swap(end, pivotIndex);

        return pivotIndex;
    }

    private void swap(int indexA, int indexB) {
        int tmp = sorted[indexA];
        sorted[indexA] = sorted[indexB];
        sorted[indexB] = tmp;
    }
}
