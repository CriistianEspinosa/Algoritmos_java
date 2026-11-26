public class Activity1 {

     public static void main(String[] args) {
        int[] sizes = {10, 100, 1000, 10000};

        System.out.println("ALGORITHM A - Sum (expected: n operations)");
        for (int n : sizes) {
            long ops = countSum(new int[n]);
            System.out.println("n = " + n + "  ->  operations = " + ops);
        }

        System.out.println();
        System.out.println("ALGORITHM B - Comparisons (expected: n x (n-1) operations)");
        for (int n : sizes) {
            long ops = countComparisons(new int[n]);
            System.out.println("n = " + n + "  ->  operations = " + ops
                    + "  (formula: " + (long) n * (n - 1) + ")");
        }

        System.out.println();
        System.out.println("ALGORITHM C - Divisions by 2 (expected: about log2(n) operations)");
        long[] numbers = {10, 100, 1000, 10000, 1000000};
        for (long n : numbers) {
            long ops = countDivisions(n);
            System.out.println("n = " + n + "  ->  operations = " + ops);
        }
    }

    public static long countSum(int[] array) {
        long counter = 0;
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum = sum + array[i];
            counter++;
        }
        return counter;
    }

    public static long countComparisons(int[] array) {
        long counter = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (i != j) {
                    boolean equal = (array[i] == array[j]);
                    counter++;
                }
            }
        }
        return counter;
    }

    public static long countDivisions(long number) {
        long counter = 0;
        while (number > 1) {
            number = number / 2;
            counter++;
        }
        return counter;
    }

}
