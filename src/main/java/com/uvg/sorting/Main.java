package com.uvg.sorting;

import com.uvg.sorting.algorithms.InsertionSort;
import com.uvg.sorting.algorithms.RadixSort;
import com.uvg.sorting.algorithms.SelectionSort;
import com.uvg.sorting.utils.FileHandler;
import com.uvg.sorting.utils.Timer;
import com.uvg.sorting.utils.WebServer;

public class Main {
    private static final int[] SIZES = {10, 100, 500, 1000, 2000, 3000};
    
    private final Timer timer;
    private final FileHandler fileHandler;
    private final SelectionSort selectionSort;
    private final InsertionSort insertionSort;
    private final RadixSort radixSort;

    public Main() {
        this.fileHandler = new FileHandler();
        this.timer = new Timer(fileHandler);
        this.selectionSort = new SelectionSort();
        this.insertionSort = new InsertionSort();
        this.radixSort = new RadixSort();
    }

    public void runTests() {
        for (int size : SIZES) {
            System.out.println("Testing with size: " + size);
            int[] numbers = fileHandler.generateAndSaveNumbers(size);
            
            // Test each sorting algorithm with proper method references
            timer.measureSortingTime("Selection Sort", size, () -> selectionSort.sort(numbers.clone()));
            timer.measureSortingTime("Insertion Sort", size, () -> insertionSort.sort(numbers.clone()));
            timer.measureSortingTime("Radix Sort", size, () -> radixSort.sort(numbers.clone()));
        }

        WebServer.startServer();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.runTests();
    }
}