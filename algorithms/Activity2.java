import java.util.Arrays;
import java.util.function.LongSupplier;

public class Activity2 {

    static long operations = 0;           // operation counter
    static int testsPassed = 0;
    static int testsFailed = 0;

    static final int FACTORIAL_MAX = 20;  // 21! overflows long

    // ===================== VALIDATIONS =====================
    static void validateFactorial(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Factorial is not defined for negative numbers: " + n);
        if (n > FACTORIAL_MAX)
            throw new IllegalArgumentException("n=" + n + " overflows long. Maximum allowed: " + FACTORIAL_MAX);
    }

    static void validateArray(int[] a) {
        if (a == null)
            throw new IllegalArgumentException("The array is null");
        if (a.length == 0)
            throw new IllegalArgumentException("The array is empty");
    }

    // ===================== ALGORITHM 1: FACTORIAL =====================
    // Iterative
    public static long factorialIterative(int n) {
        validateFactorial(n);
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
            operations++;
        }
        return result;
    }

    // Recursive
    public static long factorialRecursive(int n) {
        validateFactorial(n);
        return factorialRec(n);
    }

    private static long factorialRec(int n) {
        if (n <= 1) return 1;             // base case
        operations++;
        return n * factorialRec(n - 1);   // n decreases by 1 toward the base case
    }

    // ===================== ALGORITHM 2: SUM =====================
    // Iterative
    public static long sumIterative(int[] a) {
        validateArray(a);
        long sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
            operations++;
        }
        return sum;
    }

    // Recursive
    public static long sumRecursive(int[] a) {
        validateArray(a);
        return sumRec(a, 0);
    }

    private static long sumRec(int[] a, int index) {
        if (index == a.length) return 0;  // base case: no elements left
        operations++;
        return a[index] + sumRec(a, index + 1);
    }

    // ===================== ALGORITHM 3: MAXIMUM =====================
    // Iterative
    public static int maxIterative(int[] a) {
        validateArray(a);
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            operations++;
            if (a[i] > max) max = a[i];
        }
        return max;
    }

    // Recursive
    public static int maxRecursive(int[] a) {
        validateArray(a);
        return maxRec(a, 0);
    }

    private static int maxRec(int[] a, int index) {
        if (index == a.length - 1) return a[index];  // base case: last element
        int maxOfRest = maxRec(a, index + 1);
        operations++;
        return Math.max(a[index], maxOfRest);
    }

    // ===================== TEST UTILITIES =====================
    /** Runs the function, measures time and operations, and returns the result. */
    static long[] measure(LongSupplier f) {
        operations = 0;
        long start = System.nanoTime();
        long result = f.getAsLong();
        long time = System.nanoTime() - start;
        return new long[]{result, operations, time};
    }

    static void verify(String test, long actual, long expected) {
        boolean ok = actual == expected;
        if (ok) testsPassed++; else testsFailed++;
        System.out.printf("  %-40s actual=%-20d expected=%-20d %s%n",
                test, actual, expected, ok ? "OK" : "FAIL");
    }

    static void expectException(String test, Runnable r) {
        try {
            r.run();
            testsFailed++;
            System.out.printf("  %-40s FAIL (no exception thrown)%n", test);
        } catch (IllegalArgumentException e) {
            testsPassed++;
            System.out.printf("  %-40s OK (%s)%n", test, e.getMessage());
        }
    }

    static void compare(String name, LongSupplier iter, LongSupplier rec, long expected) {
        long[] i = measure(iter);
        long[] r = measure(rec);
        boolean ok = i[0] == expected && r[0] == expected;
        if (ok) testsPassed++; else testsFailed++;
        System.out.printf("  %-10s | iterative: res=%d ops=%d t=%d ns | recursive: res=%d ops=%d t=%d ns | %s%n",
                name, i[0], i[1], i[2], r[0], r[1], r[2], ok ? "MATCH" : "DIFFER");
    }

    // ===================== TESTS BY ALGORITHM =====================
    static void factorialIterativeTests() {
        System.out.println("\n=== ALGORITHM 1 | ITERATIVE FACTORIAL ===");
        verify("factorialIterative(5)", factorialIterative(5), 120);
        verify("factorialIterative(0)", factorialIterative(0), 1);
        verify("factorialIterative(1)", factorialIterative(1), 1);
        verify("factorialIterative(2)", factorialIterative(2), 2);
        verify("factorialIterative(20) [max long]", factorialIterative(20), 2432902008176640000L);
        expectException("factorialIterative(-1)", () -> factorialIterative(-1));
        expectException("factorialIterative(21) [overflow]", () -> factorialIterative(21));
    }

    static void factorialRecursiveTests() {
        System.out.println("\n=== ALGORITHM 1 | RECURSIVE FACTORIAL ===");
        verify("factorialRecursive(5)", factorialRecursive(5), 120);
        verify("factorialRecursive(0)", factorialRecursive(0), 1);
        verify("factorialRecursive(1)", factorialRecursive(1), 1);
        verify("factorialRecursive(2)", factorialRecursive(2), 2);
        verify("factorialRecursive(20) [max long]", factorialRecursive(20), 2432902008176640000L);
        expectException("factorialRecursive(-1)", () -> factorialRecursive(-1));
        expectException("factorialRecursive(21) [overflow]", () -> factorialRecursive(21));
    }

    static void sumIterativeTests() {
        System.out.println("\n=== ALGORITHM 2 | ITERATIVE SUM ===");
        verify("sumIterative({8,3,10,2,7})", sumIterative(new int[]{8, 3, 10, 2, 7}), 30);
        verify("sumIterative({7})", sumIterative(new int[]{7}), 7);
        verify("sumIterative({-5,5})", sumIterative(new int[]{-5, 5}), 0);
        verify("sumIterative({-1,-2,-3})", sumIterative(new int[]{-1, -2, -3}), -6);
        verify("sumIterative({0,0,0})", sumIterative(new int[]{0, 0, 0}), 0);
        expectException("sumIterative(null)", () -> sumIterative(null));
        expectException("sumIterative({})", () -> sumIterative(new int[]{}));
    }

    static void sumRecursiveTests() {
        System.out.println("\n=== ALGORITHM 2 | RECURSIVE SUM ===");
        verify("sumRecursive({8,3,10,2,7})", sumRecursive(new int[]{8, 3, 10, 2, 7}), 30);
        verify("sumRecursive({7})", sumRecursive(new int[]{7}), 7);
        verify("sumRecursive({-5,5})", sumRecursive(new int[]{-5, 5}), 0);
        verify("sumRecursive({-1,-2,-3})", sumRecursive(new int[]{-1, -2, -3}), -6);
        verify("sumRecursive({0,0,0})", sumRecursive(new int[]{0, 0, 0}), 0);
        expectException("sumRecursive(null)", () -> sumRecursive(null));
        expectException("sumRecursive({})", () -> sumRecursive(new int[]{}));
    }

    static void maxIterativeTests() {
        System.out.println("\n=== ALGORITHM 3 | ITERATIVE MAXIMUM ===");
        verify("maxIterative({8,3,10,2,7})", maxIterative(new int[]{8, 3, 10, 2, 7}), 10);
        verify("maxIterative({4})", maxIterative(new int[]{4}), 4);
        verify("maxIterative({9,1,2}) [start]", maxIterative(new int[]{9, 1, 2}), 9);
        verify("maxIterative({1,2,9}) [end]", maxIterative(new int[]{1, 2, 9}), 9);
        verify("maxIterative({5,5,5})", maxIterative(new int[]{5, 5, 5}), 5);
        verify("maxIterative({-8,-3,-10})", maxIterative(new int[]{-8, -3, -10}), -3);
        expectException("maxIterative(null)", () -> maxIterative(null));
        expectException("maxIterative({})", () -> maxIterative(new int[]{}));
    }

    static void maxRecursiveTests() {
        System.out.println("\n=== ALGORITHM 3 | RECURSIVE MAXIMUM ===");
        verify("maxRecursive({8,3,10,2,7})", maxRecursive(new int[]{8, 3, 10, 2, 7}), 10);
        verify("maxRecursive({4})", maxRecursive(new int[]{4}), 4);
        verify("maxRecursive({9,1,2}) [start]", maxRecursive(new int[]{9, 1, 2}), 9);
        verify("maxRecursive({1,2,9}) [end]", maxRecursive(new int[]{1, 2, 9}), 9);
        verify("maxRecursive({5,5,5})", maxRecursive(new int[]{5, 5, 5}), 5);
        verify("maxRecursive({-8,-3,-10})", maxRecursive(new int[]{-8, -3, -10}), -3);
        expectException("maxRecursive(null)", () -> maxRecursive(null));
        expectException("maxRecursive({})", () -> maxRecursive(new int[]{}));
    }

    // ===================== COMPARISON: TIME AND OPERATIONS =====================
    static void minimumCaseComparison() {
        System.out.println("\n=== ITERATIVE vs RECURSIVE COMPARISON (minimum case) ===");
        int[] values = {8, 3, 10, 2, 7};
        int number = 5;
        compare("Factorial", () -> factorialIterative(number), () -> factorialRecursive(number), 120);
        compare("Sum", () -> sumIterative(values), () -> sumRecursive(values), 30);
        compare("Maximum", () -> maxIterative(values), () -> maxRecursive(values), 10);
    }

    static void largeArrayComparison() {
        System.out.println("\n=== COMPARISON WITH A 5000-ELEMENT ARRAY ===");
        int[] large = new int[5000];
        for (int i = 0; i < large.length; i++) large[i] = i + 1;
        long expectedSum = 5000L * 5001 / 2;
        compare("Sum", () -> sumIterative(large), () -> sumRecursive(large), expectedSum);
        compare("Maximum", () -> maxIterative(large), () -> maxRecursive(large), 5000);
    }

    // ===================== STACK OVERFLOW RISK =====================
    static void stackOverflowDemo() {
        System.out.println("\n=== STACK OVERFLOW (1,000,000-element array) ===");
        int[] huge = new int[1_000_000];
        Arrays.fill(huge, 1);

        System.out.println("  sumIterative : " + sumIterative(huge) + " (no problem)");
        try {
            System.out.println("  sumRecursive : " + sumRecursive(huge));
            System.out.println("  (This JVM has a large stack and did not overflow; with -Xss256k it does)");
        } catch (StackOverflowError e) {
            System.out.println("  sumRecursive : StackOverflowError (the stack was exhausted)");
        }
    }

    // ===================== MAIN =====================
    public static void main(String[] args) {
        factorialIterativeTests();
        factorialRecursiveTests();
        sumIterativeTests();
        sumRecursiveTests();
        maxIterativeTests();
        maxRecursiveTests();

        minimumCaseComparison();
        largeArrayComparison();
        stackOverflowDemo();

        System.out.println("\n=== SUMMARY ===");
        System.out.println("Tests passed: " + testsPassed);
        System.out.println("Tests failed: " + testsFailed);
    }
}