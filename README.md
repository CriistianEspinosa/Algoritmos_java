# Algoritmos_java
# Algorithmic Complexity, Iteration, Recursion, and Sorting Algorithms in Java

Integrative group assignment (**Part II: Implementation and Practical Application in Java**) covering asymptotic complexity analysis, iterative and recursive paradigms, classic sorting algorithms, and automated experimental benchmarking.


---

## Team Members

* **Espinosa Panchi Cristian David** 
* **Chuquimarca Chacasaguay Gabriela Brithany**
* **Chiluisa Garcia Carlos Joel**
* **Cumbal Cuaspud Paul Alejandro**

---

## Overview by Activity

| Activity | Focus | Core Classes / Components |
| :--- | :--- | :--- |
| **1. Complexity** | Dominant operation counting for linear sum $O(n)$, nested pairwise comparisons $O(n^2)$, and halving reduction $O(\log n)$, comparing theoretical $T(n)$ against measured runs | `countSum`, `countComparisons`, `countDivisions` (`Activity4.java`) |
| **2. Iteration & Recursion** | Factorial, array sum, and array maximum implemented iteratively and recursively with input validation, operation counters, execution times, and `StackOverflowError` tests | `factorialIterative` / `factorialRecursive`, `sumIterative` / `sumRecursive`, `maxIterative` / `maxRecursive` (`Activity4.java`) |
| **3. Sorting Algorithms** | Bubble Sort, Selection Sort, Insertion Sort, and Shell Sort implemented from scratch (without built-in sorting utilities or streams), tracking comparisons, movements, and execution time | `bubbleSort`, `selectionSort`, `insertionSort`, `shellSort`, `SortResult` (`Activity4.java`) |
| **4. Benchmark & Comparison** | Automated runs across `10`, `100`, `1,000`, and `10,000` elements and `5` data distributions (`Ascending`, `Descending`, `Random`, `Repeated`, `Nearly sorted`) with JVM warm-up, `System.nanoTime()`, and result validation | `Benchmark`, `Metrics`, `generateData`, `executeAlgorithm`, `warmUpJVM`, `runBenchmark` (`Activity4.java`) |
| **5. Technical Analysis** | Comparative evaluation across sizes and distributions, answering core complexity, iteration/recursion, and algorithm-selection questions | Included in `Practical Report Part II.pdf`[cite: 13, 41] |

---

## Repository Structure

```text
algoritmos-java/
├── README.md
├── Activity4.java
├── Activity1.docx
├── Activity2.docx
├── Activity3.docx
├── Activity-4.docx
└── Practical Report Part II.pdf
```[cite: 1, 2, 3, 4, 13]

---

## Counting Criteria & Benchmark Methodology

* **Comparisons (Sorting):** Every comparison between two array elements (such as `arr[j] > arr[j + 1]`, `arr[j] < arr[minIndex]`, or `arr[j] > key`).
* **Movements (Sorting & Benchmark):** Swaps and array shifts are tracked consistently to evaluate write/movement effort alongside comparisons.
* **Execution Time:** Measured with `System.nanoTime()` around the sorting/benchmark execution, excluding dataset generation, array copying, and output printing.
* **Reproducibility & Validation:** Fixed random seed (`42`), independent array copies (`.clone()`), JVM warm-up runs prior to timing, multiple repetitions per scenario, and correctness checks verifying ascending order and element preservation[cite: 2, 4, 9, 11].

---

