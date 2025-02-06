package com.uvg.sorting.algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    @Test
    void testMerge() {
        MergeSort mergeSort = new MergeSort();
        
        // Caso de prueba con un arreglo de ejemplo
        int[] array = {3, 5, 1, 2, 4, 6};
        int[] expected = {1, 2, 3, 4, 5, 6};  // Resultado esperado después de la fusión

        // Subarreglos L y R para fusionar
        mergeSort.merge(array, 0, 2, 5); // Fusiona los subarreglos [3, 5, 1] y [2, 4, 6]
        
        // Verificar que el arreglo esté correctamente fusionado
        assertArrayEquals(expected, array);
    }

    @Test
    void testMergeSort() {
        MergeSort mergeSort = new MergeSort();
        
        // Caso de prueba con un arreglo desordenado
        int[] array = {3, 5, 1, 2, 4, 6};
        int[] expected = {1, 2, 3, 4, 5, 6};  // Resultado esperado después de ordenar
        
        // Llamada al método merge_sort
        mergeSort.merge_sort(array, 0, array.length - 1);
        
        // Verificar que el arreglo esté ordenado correctamente
        assertArrayEquals(expected, array);
    }

    @Test
    void testMergeSortAlreadySorted() {
        MergeSort mergeSort = new MergeSort();
        
        // Caso de prueba con un arreglo ya ordenado
        int[] array = {1, 2, 3, 4, 5, 6};
        int[] expected = {1, 2, 3, 4, 5, 6};  // El arreglo ya está ordenado
        
        // Llamada al método merge_sort
        mergeSort.merge_sort(array, 0, array.length - 1);
        
        // Verificar que el arreglo siga igual
        assertArrayEquals(expected, array);
    }
}
