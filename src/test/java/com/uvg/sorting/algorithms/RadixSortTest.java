package com.uvg.sorting.algorithms;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;

public class RadixSortTest {
    private final RadixSort radixSort = new RadixSort();

    @Test
    public void testRadixSort() {
        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66}; // Arreglo de prueba
        int[] expected = arr.clone();                   // Se clona el arreglo original
        Arrays.sort(expected);                          // Se ordena con el método estándar de Java
        
        radixSort.sort(arr);                           // Se ejecuta el algoritmo de Radix Sort
        assertArrayEquals(expected, arr);               // Se compara con el resultado esperado
    }
}