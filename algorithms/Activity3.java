import java.util.Arrays;

public class Activity3 {

    // Helper class to store sorting metrics cleanly
    static class SortResult {
        int[] sortedArray;
        long comparisons;
        long movements;
        long timeNs;

        SortResult(int[] sortedArray, long comparisons, long movements, long timeNs) {
            this.sortedArray = sortedArray;
            this.comparisons = comparisons;
            this.movements = movements;
            this.timeNs = timeNs;
        }
    }

    // 1. Bubble Sort
    public static SortResult bubbleSort(int[] input) {
        if (input == null) throw new IllegalArgumentException("Array cannot be null");
        int[] arr = Arrays.copyOf(input, input.length);
        long comparisons = 0;
        long movements = 0;

        long startTime = System.nanoTime();
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                comparisons++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    movements++; // 1 swap = 1 movement
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        long endTime = System.nanoTime();

        return new SortResult(arr, comparisons, movements, endTime - startTime);
    }

    // 2. Selection Sort
    public static SortResult selectionSort(int[] input) {
        if (input == null) throw new IllegalArgumentException("Array cannot be null");
        int[] arr = Arrays.copyOf(input, input.length);
        long comparisons = 0;
        long movements = 0;

        long startTime = System.nanoTime();
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
                movements++; // 1 swap = 1 movement
            }
        }
        long endTime = System.nanoTime();

        return new SortResult(arr, comparisons, movements, endTime - startTime);
    }

    // 3. Insertion Sort
    public static SortResult insertionSort(int[] input) {
        if (input == null) throw new IllegalArgumentException("Array cannot be null");
        int[] arr = Arrays.copyOf(input, input.length);
        long comparisons = 0;
        long movements = 0;

        long startTime = System.nanoTime();
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0) {
                comparisons++;
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    movements++; // 1 shift = 1 movement
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
        }
        long endTime = System.nanoTime();

        return new SortResult(arr, comparisons, movements, endTime - startTime);
    }

    // 4. Shell Sort
    public static SortResult shellSort(int[] input) {
        if (input == null) throw new IllegalArgumentException("Array cannot be null");
        int[] arr = Arrays.copyOf(input, input.length);
        long comparisons = 0;
        long movements = 0;

        long startTime = System.nanoTime();
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;
                while (j >= gap) {
                    comparisons++;
                    if (arr[j - gap] > temp) {
                        arr[j] = arr[j - gap];
                        movements++; // 1 gap-shift = 1 movement
                        j -= gap;
                    } else {
                        break;
                    }
                }
                arr[j] = temp;
            }
        }
        long endTime = System.nanoTime();

        return new SortResult(arr, comparisons, movements, endTime - startTime);
    }

    // Helper method to test all 4 algorithms on a given dataset
    public static void runTestCase(String caseName, int[] data) {
        System.out.println("=== TEST CASE: " + caseName + " ===");
        System.out.println("Input: " + Arrays.toString(data));

        SortResult rBubble = bubbleSort(data);
        SortResult rSelection = selectionSort(data);
        SortResult rInsertion = insertionSort(data);
        SortResult rShell = shellSort(data);

        System.out.printf("Bubble Sort    -> Output: %s | Comparisons: %d | Movements: %d | Time: %d ns%n",
                Arrays.toString(rBubble.sortedArray), rBubble.comparisons, rBubble.movements, rBubble.timeNs);
        System.out.printf("Selection Sort -> Output: %s | Comparisons: %d | Movements: %d | Time: %d ns%n",
                Arrays.toString(rSelection.sortedArray), rSelection.comparisons, rSelection.movements, rSelection.timeNs);
        System.out.printf("Insertion Sort -> Output: %s | Comparisons: %d | Movements: %d | Time: %d ns%n",
                Arrays.toString(rInsertion.sortedArray), rInsertion.comparisons, rInsertion.movements, rInsertion.timeNs);
        System.out.printf("Shell Sort     -> Output: %s | Comparisons: %d | Movements: %d | Time: %d ns%n",
                Arrays.toString(rShell.sortedArray), rShell.comparisons, rShell.movements, rShell.timeNs);
        System.out.println();
    }

    public static void main(String[] args) {
        // 1. Required initial dataset
        int[] initialData = {45, 12, 90, 12, 3, 70, 45, 18};
        runTestCase("Initial Dataset", initialData);

        // 2. Edge cases validation
        runTestCase("Empty Array", new int[]{});
        runTestCase("Single Element", new int[]{25});
        runTestCase("Repeated Values", new int[]{7, 7, 7, 7, 7});
        runTestCase("Already Sorted", new int[]{3, 12, 18, 45, 70, 90});
    }
}