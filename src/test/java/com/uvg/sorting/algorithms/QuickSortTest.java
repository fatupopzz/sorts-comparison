package com.uvg.sorting.algorithms;

/*
 * Universidad del Valle de Guatemala
 * Autora: Tiffany Salazar Suarez
 * Carnet: 24630
 * Fecha de inicio: 5/02/2025
 * Fecha de finalización: 5/02/2025
 * Descripción: La clase QuickSort se encarga de ordenar listas de números
*/

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest{

    //Test para verificar el comportamiento con un arreglo desordenado
    @Test
    public void testQuickSort(){
        QuickSort quickSort = new QuickSort();
        int[] arrayL = {12, 4, 7, 9, 3, 5, 2, 8, 6};

        quickSort.quick_sort(arrayL, 0, arrayL.length - 1);

        //Comprobación de que el arreglo esté ordenado
        int[] expected = {2, 3, 4, 5, 6, 7, 8, 9, 12};
        assertarrayLEquals(expected, arrayL, "El arreglo no está ordenado correctamente.");
    }

    //Test para verificar un arreglo ya ordenado
    @Test
    public void testAlreadySorted(){
        QuickSort quickSort = new QuickSort();
        int[] arrayL = {1, 2, 3, 4, 5};

        quickSort.quick_sort(arrayL, 0, arrayL.length - 1);

        //Comprobación de que el arreglo esté ordenado
        int[] expected = {1, 2, 3, 4, 5};
        assertarrayLEquals(expected, arrayL, "El arreglo ya estaba ordenado.");
    }

    //Test para verificar si partition funciona correctamente
    @Test
    public void testPartition(){
        QuickSort quickSort = new QuickSort();
        int[] arrayL = {12, 4, 7, 9, 3, 5, 2, 8, 6};
        
        int pivotIndex = quickSort.partition(arrayL, 0, arrayL.length - 1);

        //El índice de partición debería ser el lugar correcto para el pivote
        assertTrue(pivotIndex >= 0 && pivotIndex < arrayL.length, "El índice del pivote está fuera de rango.");
    }
}
