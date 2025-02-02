package com.uvg.sorting.algorithms;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;

public class SortTest {

    // Instancia de la clase Sort que contiene los algoritmos de ordenamiento
    private final Sort sorter = new Sort();

    /**
     * Prueba unitaria para el método insertionSort.
     * Verifica que el algoritmo ordene correctamente un arreglo de enteros.
     */
    @Test
    public void testInsertionSort() {
        int[] arr = {5, 2, 9, 1, 5, 6};  // Arreglo de prueba
        int[] expected = arr.clone();   // Se clona el arreglo original
        Arrays.sort(expected);          // Se ordena con el método estándar de Java para comparar resultados
        
        Sort.insertionSort(arr);      // Se ejecuta el método de ordenamiento
        assertArrayEquals(expected, arr); // Se verifica que el resultado sea el esperado
    }

    /**
     * Prueba unitaria para el método radixSort.
     * Se evalúa si el algoritmo ordena correctamente un conjunto de números positivos.
     */
    @Test
    public void testRadixSort() {
        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66}; // Arreglo de prueba
        int[] expected = arr.clone();  // Se clona el arreglo original
        Arrays.sort(expected);         // Se ordena con el método estándar de Java
        
        Sort.radixSort(arr);         // Se ejecuta el algoritmo de Radix Sort
        assertArrayEquals(expected, arr); // Se compara con el resultado esperado
    }

    /**
     * Prueba unitaria para el método selectionSort.
     * Se verifica que el algoritmo ordene correctamente un conjunto pequeño de números.
     */
    @Test
    public void testSelectionSort() {
        int[] arr = {64, 25, 12, 22, 11}; // Arreglo de prueba
        int[] expected = arr.clone();    // Se clona el arreglo original
        Arrays.sort(expected);           // Se ordena con el método estándar de Java
        
        sorter.selectionSort(arr);       // Se ejecuta el algoritmo de Selection Sort
        assertArrayEquals(expected, arr); // Se compara el resultado con el esperado
    }
}
