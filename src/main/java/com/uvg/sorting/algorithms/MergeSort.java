package com.uvg.sorting.algorithms;

/*
 * Universidad del Valle de Guatemala
 * Autora: Tiffany Salazar Suarez
 * Carnet: 24630
 * Fecha de inicio: 3/02/2025
 * Fecha de finalización: 4/02/2025
 * Descripción: La clase MergeSort se encarga de ordenar listas de números
*/

public class MergeSort{
    
    //Método que une los dos arreglos
    public void merge(int[] arrayL, int m, int n, int b){
        // Crear los subarreglos L y R
        int[] L = new int[n - m + 1];
        int[] R = new int[b - n];

        //For para dividir el arreglo en dos subarreglos
        for (int i = 0; i < n - m + 1; i++) {
            L[i] = arrayL[m + i];
        }
        for (int j = 0; j < b - n; j++) {
            R[j] = arrayL[n + j + 1];
        }

        //Fusión entre los subarreglos
        int i = 0, j = 0;
        for (int k = m; k <= b; k++) {
            if (i < n - m + 1 && (j >= b - n || L[i] <= R[j])){
                arrayL[k] = L[i++];
            }else{
                arrayL[k] = R[j++];
            }
        }
    }

    //Método de Merge Sort
    public void merge_sort(int[] arrayL, int p, int r){
        if (p < r){
            int q = (p + r) / 2;
            //División del arreglo
            merge_sort(arrayL, p, q);
            merge_sort(arrayL, q + 1, r);
            //Método de fusión
            merge(arrayL, p, q, r);
        }
    }
}
