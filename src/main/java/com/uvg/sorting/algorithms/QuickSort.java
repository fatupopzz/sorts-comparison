package com.uvg.sorting.algorithms;

/*
 * Universidad del Valle de Guatemala
 * Autora: Tiffany Salazar Suarez
 * Carnet: 24630
 * Fecha de inicio: 31/01/2025
 * Fecha de finalización: 5/02/2025
 * Descripción: La clase QuickSort se encarga de ordenar listas de números
*/



public class QuickSort {
    public void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }
    
    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }
}