package com.uvg.sorting.algorithms;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;

public class InsertionSortTest {
    private final InsertionSort insertionSort = new InsertionSort();

    @Test
    public void testInsertionSort() {
        int[] arr = {5, 2, 9, 1, 5, 6};  // Arreglo de prueba
        int[] expected = arr.clone();     // Se clona el arreglo original
        Arrays.sort(expected);            // Se ordena con el método estándar de Java

        insertionSort.sort(arr);          // Se ejecuta el método de ordenamiento
        assertArrayEquals(expected, arr);  // Se verifica que el resultado sea el esperado
    }
}