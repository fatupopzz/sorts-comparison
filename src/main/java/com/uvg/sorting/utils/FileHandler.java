package com.uvg.sorting.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;



public class FileHandler {
    private static final String NUMBERS_FILE = "numbers.txt";
    private static final String JSON_FILE = "src/main/webapp/sorting_data.json";
    private static final int MAX_NUMBER = 10000;

    // Para almacenar los tiempos de ejecución
    private Map<String, List<TimeEntry>> timingData = new HashMap<>();

    // Clase interna para los datos de tiempo
    private static class TimeEntry {
        String algorithm;
        int arraySize;
        long timeNanos;

        TimeEntry(String algorithm, int arraySize, long timeNanos) {
            this.algorithm = algorithm;
            this.arraySize = arraySize;
            this.timeNanos = timeNanos;
        }
    }

    /**
     * Genera números aleatorios y los guarda en el archivo
     */
    public int[] generateAndSaveNumbers(int size) {
        int[] numbers = new int[size];
        Random rand = new Random();
        
        // Generar números
        for (int i = 0; i < size; i++) {
            numbers[i] = rand.nextInt(MAX_NUMBER);
        }
        
        // Guardar en archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NUMBERS_FILE))) {
            for (int number : numbers) {
                writer.write(String.valueOf(number));
                writer.newLine();
            }
            System.out.println("Números guardados en " + NUMBERS_FILE);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        
        return numbers;
    }

    /**
     * Lee los números del archivo
     */
    public int[] readNumbersFromFile() {
        List<Integer> numbersList = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(NUMBERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                numbersList.add(Integer.parseInt(line.trim()));
            }
            System.out.println("Números leídos de " + NUMBERS_FILE);
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        
        return numbersList.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Guarda los tiempos de ejecución de un algoritmo
     */
    public void saveExecutionTime(String algorithm, int size, long timeNanos) {
        timingData.computeIfAbsent(algorithm, k -> new ArrayList<>())
            .add(new TimeEntry(algorithm, size, timeNanos));
        saveToJson();
    }

    /**
     * Guarda los datos en formato JSON para la visualización
     */
    private void saveToJson() {
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            new Gson().toJson(timingData, writer);
            System.out.println("Datos de rendimiento guardados en " + JSON_FILE);
        } catch (IOException e) {
            System.err.println("Error saving performance data: " + e.getMessage());
        }
    }

    /**
     * Verifica si un array está ordenado
     */
    public boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}