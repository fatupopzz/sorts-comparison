

/*
 * Universidad del Valle de Guatemala
 * Autora: Tiffany Salazar Suarez
 * Carnet: 24630
 * Fecha de inicio: 5/02/2025
 * Fecha de finalización: 5/02/2025
 * Descripción: La clase QuickSort se encarga de ordenar listas de números
*/

package com.uvg.sorting.algorithms;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class QuickSortTest {
    
    @Test
    public void testEmptyArray() {
        QuickSort sorter = new QuickSort();
        int[] arr = {};
        sorter.sort(arr);
        assertArrayEquals(new int[]{}, arr);
    }
    
    @Test
    public void testSingleElement() {
        QuickSort sorter = new QuickSort();
        int[] arr = {1};
        sorter.sort(arr);
        assertArrayEquals(new int[]{1}, arr);
    }
    
    @Test
    public void testAlreadySorted() {
        QuickSort sorter = new QuickSort();
        int[] arr = {1, 2, 3, 4, 5};
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }
    
    @Test
    public void testReverseSorted() {
        QuickSort sorter = new QuickSort();
        int[] arr = {5, 4, 3, 2, 1};
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }
    
    @Test
    public void testDuplicateElements() {
        QuickSort sorter = new QuickSort();
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3, 4, 5, 5, 6, 9}, arr);
    }
    
    @Test
    public void testRandomArray() {
        QuickSort sorter = new QuickSort();
        int[] arr = {7, 2, 9, 4, 1, 8, 3, 6, 5};
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, arr);
    }
}