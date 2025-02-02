package com.uvg.sorting.algorithms;

import java.io.*;
import java.util.*;

public class Sort {
    private static final String FILE_NAME = "numbers.txt";
    private static final int NUM_COUNT = 3000;
    
    public static void main(String[] args) {
        generateRandomNumbers();
        int[] numbers = readNumbersFromFile();
        
        if (numbers != null && numbers.length > 0) {
            int[] insertionSorted = numbers.clone();
            int[] radixSorted = numbers.clone();
            
            insertionSort(insertionSorted);
            radixSort(radixSorted);
            
            System.out.println("Numbers sorted successfully!");
        } else {
            System.out.println("No numbers found in the file.");
        }
    }
    
    private static void generateRandomNumbers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            Random rand = new Random();
            for (int i = 0; i < NUM_COUNT; i++) {
                writer.write(rand.nextInt(10000) + "");
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    private static int[] readNumbersFromFile() {
        List<Integer> numbersList = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                numbersList.add(Integer.parseInt(line));
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading from file: " + e.getMessage());
            return new int[0];
        }
        
        return numbersList.stream().mapToInt(i -> i).toArray();
    }
    
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    public static void radixSort(int[] arr) {
        if (arr.length == 0) return;
        int max = Arrays.stream(arr).max().orElse(0);
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp);
        }
    }
    
    private static void countingSortByDigit(int[] arr, int exp) {
        int[] output = new int[arr.length];
        int[] count = new int[10];
        
        for (int num : arr) {
            count[(num / exp) % 10]++;
        }
        
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        for (int i = arr.length - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        
        System.arraycopy(output, 0, arr, 0, arr.length);
    }
    
    public void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }
}