
/*
 * Universidad del Valle de Guatemala
 * Autora: Tiffany Salazar Suarez
 * Carnet: 24630
 * Fecha de inicio: 5/02/2025
 * Fecha de finalización: 5/02/2025
 * Descripción: La clase MergeSortTest hace las pruebas unitarias de MergeSort
*/


package com.uvg.sorting.algorithms;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class MergeSortTest {
    
    @Test
    public void testEmptyArray() {
        MergeSort sorter = new MergeSort();
        int[] arr = {};
        sorter.sort(arr);
        assertArrayEquals(new int[]{}, arr);
    }
    
    @Test
    public void testSingleElement() {
        MergeSort sorter = new MergeSort();
        int[] arr = {1};
        sorter.sort(arr);
        assertArrayEquals(new int[]{1}, arr);
    }
    
    @Test
    public void testAlreadySorted() {
        MergeSort sorter = new MergeSort();
        int[] arr = {1, 2, 3, 4, 5};
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }
    
    @Test
    public void testReverseSorted() {
        MergeSort sorter = new MergeSort();
        int[] arr = {5, 4, 3, 2, 1};
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }
    
    @Test
    public void testDuplicateElements() {
        MergeSort sorter = new MergeSort();
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3, 4, 5, 5, 6, 9}, arr);
    }
}
