package com.uvg.sorting;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.uvg.sorting.algorithms.*;
import com.uvg.sorting.utils.Regression;
import com.uvg.sorting.utils.Regression.Point;

public class Main {
    public static void main(String[] args) {
        // Generar tamaños uniformemente distribuidos en escala logarítmica
        int[] sizes = generateSizes(10, 3000, 12); // 12 puntos entre 10 y 3000
        List<SortResult> results = new ArrayList<>();
        
        // Listas para los puntos de regresión
        List<Point> insertionPoints = new ArrayList<>();
        List<Point> mergePoints = new ArrayList<>();
        List<Point> quickPoints = new ArrayList<>();
        List<Point> radixPoints = new ArrayList<>();
        List<Point> selectionPoints = new ArrayList<>();
        
        // Ejecutar múltiples veces para obtener un promedio
        int repetitions = 5;
        
        for (int size : sizes) {
            System.out.println("Testing with size: " + size);
            
            // Variables para almacenar los promedios
            long avgInsertionTime = 0;
            long avgMergeTime = 0;
            long avgQuickTime = 0;
            long avgRadixTime = 0;
            long avgSelectionTime = 0;
            
            for (int rep = 0; rep < repetitions; rep++) {
                int[] array = generateRandomArray(size);
                
                // Medir tiempo para cada algoritmo
                avgInsertionTime += measureSort(new InsertionSort(), array.clone());
                avgMergeTime += measureSort(new MergeSort(), array.clone());
                avgQuickTime += measureSort(new QuickSort(), array.clone());
                avgRadixTime += measureSort(new RadixSort(), array.clone());
                avgSelectionTime += measureSort(new SelectionSort(), array.clone());
            }
            
            // Calcular promedios
            avgInsertionTime /= repetitions;
            avgMergeTime /= repetitions;
            avgQuickTime /= repetitions;
            avgRadixTime /= repetitions;
            avgSelectionTime /= repetitions;
            
            // Imprimir resultados
            System.out.println("Insertion Sort (size " + size + "): " + avgInsertionTime + " ns");
            System.out.println("Merge Sort (size " + size + "): " + avgMergeTime + " ns");
            System.out.println("Quick Sort (size " + size + "): " + avgQuickTime + " ns");
            System.out.println("Radix Sort (size " + size + "): " + avgRadixTime + " ns");
            System.out.println("Selection Sort (size " + size + "): " + avgSelectionTime + " ns");
            
            // Convertir a milisegundos y guardar puntos para regresión
            insertionPoints.add(new Point(size, avgInsertionTime / 1_000_000.0));
            mergePoints.add(new Point(size, avgMergeTime / 1_000_000.0));
            quickPoints.add(new Point(size, avgQuickTime / 1_000_000.0));
            radixPoints.add(new Point(size, avgRadixTime / 1_000_000.0));
            selectionPoints.add(new Point(size, avgSelectionTime / 1_000_000.0));
            
            // Guardar resultados
            results.add(new SortResult(size, avgInsertionTime, avgMergeTime, avgQuickTime, avgRadixTime, avgSelectionTime));
        }
        
        // Calcular regresiones
        List<Point> insertionRegression = Regression.calculateRegression(insertionPoints, "quadratic");
        List<Point> mergeRegression = Regression.calculateRegression(mergePoints, "nlogn");
        List<Point> quickRegression = Regression.calculateRegression(quickPoints, "nlogn");
        List<Point> radixRegression = Regression.calculateRegression(radixPoints, "linear");
        List<Point> selectionRegression = Regression.calculateRegression(selectionPoints, "quadratic");

        // Crear objeto JSON con datos y regresiones
        JsonObject finalData = new JsonObject();
        finalData.add("rawData", new Gson().toJsonTree(results));
        
        JsonObject regressionsObj = new JsonObject();
        regressionsObj.add("insertion", new Gson().toJsonTree(insertionRegression));
        regressionsObj.add("merge", new Gson().toJsonTree(mergeRegression));
        regressionsObj.add("quick", new Gson().toJsonTree(quickRegression));
        regressionsObj.add("radix", new Gson().toJsonTree(radixRegression));
        regressionsObj.add("selection", new Gson().toJsonTree(selectionRegression));
        finalData.add("regressions", regressionsObj);

        // Guardar en JSON
        try (FileWriter writer = new FileWriter("src/main/webapp/sorting_data.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(finalData, writer);
            System.out.println("Resultados y regresiones guardados en sorting_data.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static int[] generateSizes(int min, int max, int count) {
        int[] sizes = new int[count];
        double logMin = Math.log(min);
        double logMax = Math.log(max);
        double interval = (logMax - logMin) / (count - 1);
        
        for (int i = 0; i < count; i++) {
            sizes[i] = (int) Math.round(Math.exp(logMin + interval * i));
        }
        return sizes;
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
        } else if (sorter instanceof MergeSort) {
            ((MergeSort) sorter).sort(arr);
        } else if (sorter instanceof QuickSort) {
            ((QuickSort) sorter).sort(arr);
        } else if (sorter instanceof RadixSort) {
            ((RadixSort) sorter).sort(arr);
        } else if (sorter instanceof SelectionSort) {
            ((SelectionSort) sorter).sort(arr);
        }
        return System.nanoTime() - startTime;
    }
    
    static class SortResult {
        private final int size;
        private final long insertionTime;
        private final long mergeTime;
        private final long quickTime;
        private final long radixTime;
        private final long selectionTime;
        
        public SortResult(int size, long insertionTime, long mergeTime, long quickTime, long radixTime, long selectionTime) {
            this.size = size;
            this.insertionTime = insertionTime;
            this.mergeTime = mergeTime;
            this.quickTime = quickTime;
            this.radixTime = radixTime;
            this.selectionTime = selectionTime;
        }
    }
}