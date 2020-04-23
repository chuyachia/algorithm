package sort;

import java.util.Arrays;

public class HeapSort {
    private final int[] sorted;
    private int heapEnd;

    public HeapSort(int[] input) {
        this.sorted = Arrays.copyOf(input, input.length);
    }

    public int[] sort() {
        heapEnd = sorted.length - 1;
        while (heapEnd > 0) {
            heapify();
            swap(0, heapEnd);
            heapEnd--;
        }
        return sorted;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{4, 1, 6, 20, 4, 0, 12, 0, 88, 87, 12, -1, 112, 54};
        HeapSort heapSort = new HeapSort(arr);
        int[] sorted = heapSort.sort();
        for (int i : sorted) {
            System.out.println(i);
        }
    }

    private void heapify() {
        int lastNoneLeaf = findParent(heapEnd);
        for (int i = lastNoneLeaf; i >= 0; i--) {
            sink(i);
        }
    }

    private void sink(int index) {
        int currentIndex = index;
        int maxChildIndex = findMaxChild(currentIndex);

        while (maxChildIndex > -1) {
            if (sorted[currentIndex] >= sorted[maxChildIndex]) {
                break;
            }
            swap(currentIndex, maxChildIndex);
            currentIndex = maxChildIndex;
            maxChildIndex = findMaxChild(currentIndex);
        }
    }

    private void swap(int a, int b) {
        int tmp = sorted[a];
        sorted[a] = sorted[b];
        sorted[b] = tmp;
    }

    private int findParent(int index) {
        return (index - 1) / 2;
    }

    private int findMaxChild(int index) {
        int leftChild = index * 2 + 1;
        int rightChild = index * 2 + 2;

        if (rightChild <= heapEnd && leftChild <= heapEnd) {
            return sorted[rightChild] > sorted[leftChild] ? rightChild : leftChild;
        } else if (rightChild <= heapEnd) {
            return rightChild;
        } else if (leftChild <= heapEnd) {
            return leftChild;
        } else {
            return -1;
        }
    }
}
