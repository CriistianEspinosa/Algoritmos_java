import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Activity4 {

    public record Metrics(
            long comparisons,
            long movements
    ) {}

    static final int[] sizes = {10, 100, 1000, 10000};

    static final String[] dataTypes = {
            "Ascending",
            "Descending",
            "Random",
            "Repeated",
            "Nearly sorted"
    };

    static final String[] algorithms = {
            "Bubble Sort",
            "Selection Sort",
            "Insertion Sort",
            "Shell Sort"
    };

    static final int REPETITIONS = 3;

    public static Metrics bubbleSort(int[] data) {
        long comparisons = 0;
        long movements = 0;

        for (int i = 0; i < data.length - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < data.length - i - 1; j++) {
                comparisons++;

                if (data[j] > data[j + 1]) {
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;

                    movements += 2;
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }

        return new Metrics(comparisons, movements);
    }

    public static Metrics selectionSort(int[] data) {
        long comparisons = 0;
        long movements = 0;

        for (int i = 0; i < data.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < data.length; j++) {
                comparisons++;

                if (data[j] < data[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                int temp = data[i];
                data[i] = data[minIndex];
                data[minIndex] = temp;

                movements += 2;
            }
        }

        return new Metrics(comparisons, movements);
    }

    public static Metrics insertionSort(int[] data) {
        long comparisons = 0;
        long movements = 0;

        for (int i = 1; i < data.length; i++) {
            int current = data[i];
            int j = i - 1;

            while (j >= 0) {
                comparisons++;

                if (data[j] > current) {
                    data[j + 1] = data[j];
                    movements++;

                    j--;
                } else {
                    break;
                }
            }

            data[j + 1] = current;
            movements++;
        }

        return new Metrics(comparisons, movements);
    }

    public static Metrics shellSort(int[] data) {
        long comparisons = 0;
        long movements = 0;

        for (int gap = data.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < data.length; i++) {
                int current = data[i];
                int j = i;

                while (j >= gap) {
                    comparisons++;

                    if (data[j - gap] > current) {
                        data[j] = data[j - gap];
                        movements++;

                        j -= gap;
                    } else {
                        break;
                    }
                }

                data[j] = current;
                movements++;
            }
        }

        return new Metrics(comparisons, movements);
    }

    public static int[] generateData(int n, String type) {
        int[] data = new int[n];
        Random random = new Random(42);

        for (int i = 0; i < n; i++) {
            switch (type) {
                case "Ascending":
                    data[i] = i + 1;
                    break;

                case "Descending":
                    data[i] = n - i;
                    break;

                case "Random":
                    data[i] = random.nextInt(n * 10);
                    break;

                case "Repeated":
                    data[i] = random.nextInt(5);
                    break;

                case "Nearly sorted":
                    data[i] = i + 1;
                    break;

                default:
                    throw new IllegalArgumentException("Invalid data type");
            }
        }

        if (type.equals("Nearly sorted")) {
            for (int i = 0; i < n - 1; i += 20) {
                int temp = data[i];
                data[i] = data[i + 1];
                data[i + 1] = temp;
            }
        }

        return data;
    }

    public static Metrics executeAlgorithm(String algorithm, int[] data) {
        switch (algorithm) {
            case "Bubble Sort":
                return bubbleSort(data);

            case "Selection Sort":
                return selectionSort(data);

            case "Insertion Sort":
                return insertionSort(data);

            case "Shell Sort":
                return shellSort(data);

            default:
                throw new IllegalArgumentException("Unknown algorithm");
        }
    }

    public static boolean isSorted(int[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i] > data[i + 1]) {
                return false;
            }
        }

        return true;
    }

    public static boolean hasSameElements(int[] original, int[] sorted) {
        if (original.length != sorted.length) {
            return false;
        }

        Map<Integer, Integer> frequencies = new HashMap<>();

        for (int value : original) {
            frequencies.merge(value, 1, Integer::sum);
        }

        for (int value : sorted) {
            int count = frequencies.getOrDefault(value, 0);

            if (count == 0) {
                return false;
            }

            frequencies.put(value, count - 1);
        }

        return true;
    }

    public static void warmUpJVM() {
        int[] data = generateData(100, "Random");

        for (String algorithm : algorithms) {
            for (int i = 0; i < 10; i++) {
                int[] copy = data.clone();
                executeAlgorithm(algorithm, copy);
            }
        }
    }

    public static void runBenchmark() {
        warmUpJVM();

        System.out.printf(
                "%-16s %-8s %-16s %-15s %-15s %-15s%n",
                "Algorithm",
                "n",
                "Data Type",
                "Comparisons",
                "Movements",
                "Time (ms)"
        );

        System.out.println("-".repeat(90));

        for (int n : sizes) {
            for (String type : dataTypes) {
                int[] original = generateData(n, type);

                for (String algorithm : algorithms) {
                    long totalTime = 0;
                    long comparisons = 0;
                    long movements = 0;

                    for (int i = 0; i < REPETITIONS; i++) {
                        int[] copy = original.clone();

                        long start = System.nanoTime();
                        Metrics result = executeAlgorithm(algorithm, copy);
                        long end = System.nanoTime();

                        totalTime += end - start;
                        comparisons += result.comparisons();
                        movements += result.movements();

                        if (!isSorted(copy) || !hasSameElements(original, copy)) {
                            throw new IllegalStateException("Sorting error in " + algorithm);
                        }
                    }

                    double averageTime = totalTime / (REPETITIONS * 1000000.0);
                    double averageComparisons = (double) comparisons / REPETITIONS;
                    double averageMovements = (double) movements / REPETITIONS;

                    System.out.printf(
                            Locale.US,
                            "%-16s %-8d %-16s %-15.0f %-15.0f %-15.6f%n",
                            algorithm,
                            n,
                            type,
                            averageComparisons,
                            averageMovements,
                            averageTime
                    );
                }
            }
        }
    }

    public static void main(String[] args) {
        runBenchmark();
    }
}