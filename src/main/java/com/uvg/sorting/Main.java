// Main.java
package com.uvg.sorting;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uvg.sorting.algorithms.InsertionSort;
import com.uvg.sorting.algorithms.RadixSort;
import com.uvg.sorting.algorithms.SelectionSort;

public class Main {
    public static void main(String[] args) {
        // Tamaños de arrays a probar
        int[] sizes = {10, 100, 500, 1000, 2000, 3000};
        List<SortResult> results = new ArrayList<>();
        
        for (int size : sizes) {
            System.out.println("Testing with size: " + size);
            int[] array = generateRandomArray(size);
            
            // Medir tiempo para cada algoritmo
            long insertionTime = measureSort(new InsertionSort(), array.clone());
            System.out.println("Insertion Sort (size " + size + "): " + insertionTime + " ns");
            
            long radixTime = measureSort(new RadixSort(), array.clone());
            System.out.println("Radix Sort (size " + size + "): " + radixTime + " ns");
            
            long selectionTime = measureSort(new SelectionSort(), array.clone());
            System.out.println("Selection Sort (size " + size + "): " + selectionTime + " ns");
            
            // Guardar resultados
            results.add(new SortResult(size, insertionTime, radixTime, selectionTime));
            
            // Guardar números en archivo
            saveArrayToFile(array, "numbers.txt");
            System.out.println("Números guardados en numbers.txt");
        }
        
        // Guardar resultados en JSON
        saveResultsToJson(results, "src/main/webapp/sorting_data.json");
        System.out.println("Resultados guardados en sorting_data.json");
    }
    
    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 10000);
        }
        return array;
    }
    
    private static long measureSort(Object sorter, int[] arr) {
        long startTime = System.nanoTime();
        if (sorter instanceof InsertionSort) {
            ((InsertionSort) sorter).sort(arr);
        } else if (sorter instanceof RadixSort) {
            ((RadixSort) sorter).sort(arr);
        } else if (sorter instanceof SelectionSort) {
            ((SelectionSort) sorter).sort(arr);
        }
        return System.nanoTime() - startTime;
    }
    
    private static void saveArrayToFile(int[] array, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int num : array) {
                writer.println(num);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void saveResultsToJson(List<SortResult> results, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(results, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static class SortResult {
        private final int size;
        private final long insertionTime;
        private final long radixTime;
        private final long selectionTime;
        
        public SortResult(int size, long insertionTime, long radixTime, long selectionTime) {
            this.size = size;
            this.insertionTime = insertionTime;
            this.radixTime = radixTime;
            this.selectionTime = selectionTime;
        }
    }
}