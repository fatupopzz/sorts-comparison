package com.uvg.sorting.algorithms;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;


public class SelectionSortTest {
    private final SelectionSort selectionSort = new SelectionSort();

    @Test
    public void testSelectionSort() {
        int[] arr = {64, 25, 12, 22, 11}; // Arreglo de prueba
        int[] expected = arr.clone();      // Se clona el arreglo original
        Arrays.sort(expected);             // Se ordena con el método estándar de Java
        
        selectionSort.sort(arr);           // Se ejecuta el algoritmo de Selection Sort
        assertArrayEquals(expected, arr);   // Se compara el resultado con el esperado
    }
}